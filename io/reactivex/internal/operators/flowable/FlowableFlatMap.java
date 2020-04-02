package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMap<T, U>
  extends AbstractFlowableWithUpstream<T, U>
{
  final int bufferSize;
  final boolean delayErrors;
  final Function<? super T, ? extends Publisher<? extends U>> mapper;
  final int maxConcurrency;
  
  public FlowableFlatMap(Flowable<T> paramFlowable, Function<? super T, ? extends Publisher<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt1;
    this.bufferSize = paramInt2;
  }
  
  public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> paramSubscriber, Function<? super T, ? extends Publisher<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    return new MergeSubscriber(paramSubscriber, paramFunction, paramBoolean, paramInt1, paramInt2);
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, paramSubscriber, this.mapper)) {
      return;
    }
    this.source.subscribe(subscribe(paramSubscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
  }
  
  static final class InnerSubscriber<T, U>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<U>, Disposable
  {
    private static final long serialVersionUID = -4606175640614850599L;
    final int bufferSize;
    volatile boolean done;
    int fusionMode;
    final long id;
    final int limit;
    final FlowableFlatMap.MergeSubscriber<T, U> parent;
    long produced;
    volatile SimpleQueue<U> queue;
    
    InnerSubscriber(FlowableFlatMap.MergeSubscriber<T, U> paramMergeSubscriber, long paramLong)
    {
      this.id = paramLong;
      this.parent = paramMergeSubscriber;
      int i = paramMergeSubscriber.bufferSize;
      this.bufferSize = i;
      this.limit = (i >> 2);
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      lazySet(SubscriptionHelper.CANCELLED);
      this.parent.innerError(this, paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      if (this.fusionMode != 2) {
        this.parent.tryEmit(paramU, this);
      } else {
        this.parent.drain();
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this, paramSubscription))
      {
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(7);
          if (i == 1)
          {
            this.fusionMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            this.parent.drain();
            return;
          }
          if (i == 2)
          {
            this.fusionMode = i;
            this.queue = localQueueSubscription;
          }
        }
        paramSubscription.request(this.bufferSize);
      }
    }
    
    void requestMore(long paramLong)
    {
      if (this.fusionMode != 1)
      {
        paramLong = this.produced + paramLong;
        if (paramLong >= this.limit)
        {
          this.produced = 0L;
          ((Subscription)get()).request(paramLong);
        }
        else
        {
          this.produced = paramLong;
        }
      }
    }
  }
  
  static final class MergeSubscriber<T, U>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    static final FlowableFlatMap.InnerSubscriber<?, ?>[] CANCELLED = new FlowableFlatMap.InnerSubscriber[0];
    static final FlowableFlatMap.InnerSubscriber<?, ?>[] EMPTY = new FlowableFlatMap.InnerSubscriber[0];
    private static final long serialVersionUID = -2117620485640801370L;
    final int bufferSize;
    volatile boolean cancelled;
    final boolean delayErrors;
    volatile boolean done;
    final Subscriber<? super U> downstream;
    final AtomicThrowable errs = new AtomicThrowable();
    long lastId;
    int lastIndex;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;
    volatile SimplePlainQueue<U> queue;
    final AtomicLong requested = new AtomicLong();
    int scalarEmitted;
    final int scalarLimit;
    final AtomicReference<FlowableFlatMap.InnerSubscriber<?, ?>[]> subscribers = new AtomicReference();
    long uniqueId;
    Subscription upstream;
    
    MergeSubscriber(Subscriber<? super U> paramSubscriber, Function<? super T, ? extends Publisher<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.maxConcurrency = paramInt1;
      this.bufferSize = paramInt2;
      this.scalarLimit = Math.max(1, paramInt1 >> 1);
      this.subscribers.lazySet(EMPTY);
    }
    
    boolean addInner(FlowableFlatMap.InnerSubscriber<T, U> paramInnerSubscriber)
    {
      FlowableFlatMap.InnerSubscriber[] arrayOfInnerSubscriber1;
      FlowableFlatMap.InnerSubscriber[] arrayOfInnerSubscriber2;
      do
      {
        arrayOfInnerSubscriber1 = (FlowableFlatMap.InnerSubscriber[])this.subscribers.get();
        if (arrayOfInnerSubscriber1 == CANCELLED)
        {
          paramInnerSubscriber.dispose();
          return false;
        }
        int i = arrayOfInnerSubscriber1.length;
        arrayOfInnerSubscriber2 = new FlowableFlatMap.InnerSubscriber[i + 1];
        System.arraycopy(arrayOfInnerSubscriber1, 0, arrayOfInnerSubscriber2, 0, i);
        arrayOfInnerSubscriber2[i] = paramInnerSubscriber;
      } while (!this.subscribers.compareAndSet(arrayOfInnerSubscriber1, arrayOfInnerSubscriber2));
      return true;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        disposeAll();
        if (getAndIncrement() == 0)
        {
          SimplePlainQueue localSimplePlainQueue = this.queue;
          if (localSimplePlainQueue != null) {
            localSimplePlainQueue.clear();
          }
        }
      }
    }
    
    boolean checkTerminate()
    {
      if (this.cancelled)
      {
        clearScalarQueue();
        return true;
      }
      if ((!this.delayErrors) && (this.errs.get() != null))
      {
        clearScalarQueue();
        Throwable localThrowable = this.errs.terminate();
        if (localThrowable != ExceptionHelper.TERMINATED) {
          this.downstream.onError(localThrowable);
        }
        return true;
      }
      return false;
    }
    
    void clearScalarQueue()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      if (localSimplePlainQueue != null) {
        localSimplePlainQueue.clear();
      }
    }
    
    void disposeAll()
    {
      FlowableFlatMap.InnerSubscriber[] arrayOfInnerSubscriber = (FlowableFlatMap.InnerSubscriber[])this.subscribers.get();
      Object localObject = CANCELLED;
      if (arrayOfInnerSubscriber != localObject)
      {
        localObject = (FlowableFlatMap.InnerSubscriber[])this.subscribers.getAndSet(localObject);
        if (localObject != CANCELLED)
        {
          int i = localObject.length;
          for (int j = 0; j < i; j++) {
            localObject[j].dispose();
          }
          localObject = this.errs.terminate();
          if ((localObject != null) && (localObject != ExceptionHelper.TERMINATED)) {
            RxJavaPlugins.onError((Throwable)localObject);
          }
        }
      }
    }
    
    void drain()
    {
      if (getAndIncrement() == 0) {
        drainLoop();
      }
    }
    
    /* Error */
    void drainLoop()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 80	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   4: astore_1
      //   5: iconst_1
      //   6: istore_2
      //   7: aload_0
      //   8: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   11: ifeq +4 -> 15
      //   14: return
      //   15: aload_0
      //   16: getfield 139	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:queue	Lio/reactivex/internal/fuseable/SimplePlainQueue;
      //   19: astore_3
      //   20: aload_0
      //   21: getfield 78	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   24: invokevirtual 182	java/util/concurrent/atomic/AtomicLong:get	()J
      //   27: lstore 4
      //   29: lload 4
      //   31: ldc2_w 183
      //   34: lcmp
      //   35: ifne +9 -> 44
      //   38: iconst_1
      //   39: istore 6
      //   41: goto +6 -> 47
      //   44: iconst_0
      //   45: istore 6
      //   47: lconst_0
      //   48: lstore 7
      //   50: lload 4
      //   52: lstore 9
      //   54: lload 7
      //   56: lstore 11
      //   58: aload_3
      //   59: ifnull +135 -> 194
      //   62: lconst_0
      //   63: lstore 11
      //   65: aconst_null
      //   66: astore 13
      //   68: lload 4
      //   70: lconst_0
      //   71: lcmp
      //   72: ifeq +56 -> 128
      //   75: aload_3
      //   76: invokeinterface 189 1 0
      //   81: astore 13
      //   83: aload_0
      //   84: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   87: ifeq +4 -> 91
      //   90: return
      //   91: aload 13
      //   93: ifnonnull +6 -> 99
      //   96: goto +32 -> 128
      //   99: aload_1
      //   100: aload 13
      //   102: invokeinterface 192 2 0
      //   107: lload 7
      //   109: lconst_1
      //   110: ladd
      //   111: lstore 7
      //   113: lload 11
      //   115: lconst_1
      //   116: ladd
      //   117: lstore 11
      //   119: lload 4
      //   121: lconst_1
      //   122: lsub
      //   123: lstore 4
      //   125: goto -57 -> 68
      //   128: lload 11
      //   130: lconst_0
      //   131: lcmp
      //   132: ifeq +28 -> 160
      //   135: iload 6
      //   137: ifeq +11 -> 148
      //   140: ldc2_w 183
      //   143: lstore 4
      //   145: goto +15 -> 160
      //   148: aload_0
      //   149: getfield 78	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   152: lload 11
      //   154: lneg
      //   155: invokevirtual 196	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   158: lstore 4
      //   160: lload 4
      //   162: lstore 9
      //   164: lload 7
      //   166: lstore 11
      //   168: lload 4
      //   170: lconst_0
      //   171: lcmp
      //   172: ifeq +22 -> 194
      //   175: aload 13
      //   177: ifnonnull +14 -> 191
      //   180: lload 4
      //   182: lstore 9
      //   184: lload 7
      //   186: lstore 11
      //   188: goto +6 -> 194
      //   191: goto -129 -> 62
      //   194: aload_0
      //   195: getfield 198	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:done	Z
      //   198: istore 14
      //   200: aload_0
      //   201: getfield 139	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:queue	Lio/reactivex/internal/fuseable/SimplePlainQueue;
      //   204: astore 13
      //   206: aload_0
      //   207: getfield 73	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:subscribers	Ljava/util/concurrent/atomic/AtomicReference;
      //   210: invokevirtual 108	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   213: checkcast 109	[Lio/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber;
      //   216: astore_3
      //   217: aload_3
      //   218: arraylength
      //   219: istore 15
      //   221: iload 14
      //   223: ifeq +63 -> 286
      //   226: aload 13
      //   228: ifnull +13 -> 241
      //   231: aload 13
      //   233: invokeinterface 201 1 0
      //   238: ifeq +48 -> 286
      //   241: iload 15
      //   243: ifne +43 -> 286
      //   246: aload_0
      //   247: getfield 68	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:errs	Lio/reactivex/internal/util/AtomicThrowable;
      //   250: invokevirtual 154	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   253: astore 13
      //   255: aload 13
      //   257: getstatic 160	io/reactivex/internal/util/ExceptionHelper:TERMINATED	Ljava/lang/Throwable;
      //   260: if_acmpeq +25 -> 285
      //   263: aload 13
      //   265: ifnonnull +12 -> 277
      //   268: aload_1
      //   269: invokeinterface 204 1 0
      //   274: goto +11 -> 285
      //   277: aload_1
      //   278: aload 13
      //   280: invokeinterface 166 2 0
      //   285: return
      //   286: iload 15
      //   288: ifeq +517 -> 805
      //   291: aload_0
      //   292: getfield 206	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastId	J
      //   295: lstore 4
      //   297: aload_0
      //   298: getfield 208	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastIndex	I
      //   301: istore 16
      //   303: iload 15
      //   305: iload 16
      //   307: if_icmple +20 -> 327
      //   310: iload 16
      //   312: istore 17
      //   314: aload_3
      //   315: iload 16
      //   317: aaload
      //   318: getfield 211	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:id	J
      //   321: lload 4
      //   323: lcmp
      //   324: ifeq +86 -> 410
      //   327: iload 16
      //   329: istore 17
      //   331: iload 15
      //   333: iload 16
      //   335: if_icmpgt +6 -> 341
      //   338: iconst_0
      //   339: istore 17
      //   341: iconst_0
      //   342: istore 16
      //   344: iload 16
      //   346: iload 15
      //   348: if_icmpge +45 -> 393
      //   351: aload_3
      //   352: iload 17
      //   354: aaload
      //   355: getfield 211	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:id	J
      //   358: lload 4
      //   360: lcmp
      //   361: ifne +6 -> 367
      //   364: goto +29 -> 393
      //   367: iload 17
      //   369: iconst_1
      //   370: iadd
      //   371: istore 18
      //   373: iload 18
      //   375: istore 17
      //   377: iload 18
      //   379: iload 15
      //   381: if_icmpne +6 -> 387
      //   384: iconst_0
      //   385: istore 17
      //   387: iinc 16 1
      //   390: goto -46 -> 344
      //   393: aload_0
      //   394: iload 17
      //   396: putfield 208	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastIndex	I
      //   399: aload_0
      //   400: aload_3
      //   401: iload 17
      //   403: aaload
      //   404: getfield 211	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:id	J
      //   407: putfield 206	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastId	J
      //   410: iconst_0
      //   411: istore 19
      //   413: iconst_0
      //   414: istore 18
      //   416: lload 11
      //   418: lstore 4
      //   420: iload 15
      //   422: istore 16
      //   424: lload 9
      //   426: lstore 11
      //   428: iload 18
      //   430: istore 15
      //   432: iload 17
      //   434: istore 18
      //   436: iload 19
      //   438: istore 17
      //   440: iload 15
      //   442: iload 16
      //   444: if_icmpge +341 -> 785
      //   447: aload_0
      //   448: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   451: ifeq +4 -> 455
      //   454: return
      //   455: aload_3
      //   456: iload 18
      //   458: aaload
      //   459: astore 20
      //   461: aconst_null
      //   462: astore 13
      //   464: aload_0
      //   465: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   468: ifeq +4 -> 472
      //   471: return
      //   472: aload 20
      //   474: getfield 214	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   477: astore 21
      //   479: aload 21
      //   481: ifnonnull +10 -> 491
      //   484: lload 11
      //   486: lstore 7
      //   488: goto +197 -> 685
      //   491: lconst_0
      //   492: lstore 7
      //   494: lload 11
      //   496: lconst_0
      //   497: lcmp
      //   498: ifeq +112 -> 610
      //   501: aload 21
      //   503: invokeinterface 215 1 0
      //   508: astore 13
      //   510: aload 13
      //   512: ifnonnull +6 -> 518
      //   515: goto +95 -> 610
      //   518: aload_1
      //   519: aload 13
      //   521: invokeinterface 192 2 0
      //   526: aload_0
      //   527: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   530: ifeq +4 -> 534
      //   533: return
      //   534: lload 11
      //   536: lconst_1
      //   537: lsub
      //   538: lstore 11
      //   540: lload 7
      //   542: lconst_1
      //   543: ladd
      //   544: lstore 7
      //   546: goto -52 -> 494
      //   549: astore 13
      //   551: aload 13
      //   553: invokestatic 220	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   556: aload 20
      //   558: invokevirtual 112	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:dispose	()V
      //   561: aload_0
      //   562: getfield 68	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:errs	Lio/reactivex/internal/util/AtomicThrowable;
      //   565: aload 13
      //   567: invokevirtual 224	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   570: pop
      //   571: aload_0
      //   572: getfield 84	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:delayErrors	Z
      //   575: ifne +12 -> 587
      //   578: aload_0
      //   579: getfield 128	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   582: invokeinterface 130 1 0
      //   587: aload_0
      //   588: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   591: ifeq +4 -> 595
      //   594: return
      //   595: aload_0
      //   596: aload 20
      //   598: invokevirtual 228	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:removeInner	(Lio/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber;)V
      //   601: iinc 15 1
      //   604: iconst_1
      //   605: istore 17
      //   607: goto +172 -> 779
      //   610: lload 7
      //   612: lconst_0
      //   613: lcmp
      //   614: ifeq +41 -> 655
      //   617: iload 6
      //   619: ifne +18 -> 637
      //   622: aload_0
      //   623: getfield 78	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   626: lload 7
      //   628: lneg
      //   629: invokevirtual 196	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   632: lstore 11
      //   634: goto +8 -> 642
      //   637: ldc2_w 183
      //   640: lstore 11
      //   642: aload 20
      //   644: lload 7
      //   646: invokevirtual 232	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:requestMore	(J)V
      //   649: lconst_0
      //   650: lstore 9
      //   652: goto +6 -> 658
      //   655: lconst_0
      //   656: lstore 9
      //   658: lload 11
      //   660: lstore 7
      //   662: lload 11
      //   664: lload 9
      //   666: lcmp
      //   667: ifeq +18 -> 685
      //   670: aload 13
      //   672: ifnonnull +10 -> 682
      //   675: lload 11
      //   677: lstore 7
      //   679: goto +6 -> 685
      //   682: goto -218 -> 464
      //   685: aload 20
      //   687: getfield 233	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:done	Z
      //   690: istore 14
      //   692: aload 20
      //   694: getfield 214	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   697: astore 13
      //   699: iload 14
      //   701: ifeq +44 -> 745
      //   704: aload 13
      //   706: ifnull +13 -> 719
      //   709: aload 13
      //   711: invokeinterface 234 1 0
      //   716: ifeq +29 -> 745
      //   719: aload_0
      //   720: aload 20
      //   722: invokevirtual 228	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:removeInner	(Lio/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber;)V
      //   725: aload_0
      //   726: invokevirtual 179	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:checkTerminate	()Z
      //   729: ifeq +4 -> 733
      //   732: return
      //   733: lload 4
      //   735: lconst_1
      //   736: ladd
      //   737: lstore 4
      //   739: iconst_1
      //   740: istore 17
      //   742: goto +3 -> 745
      //   745: lload 7
      //   747: lconst_0
      //   748: lcmp
      //   749: ifne +6 -> 755
      //   752: goto +33 -> 785
      //   755: iload 18
      //   757: iconst_1
      //   758: iadd
      //   759: istore 19
      //   761: iload 19
      //   763: istore 18
      //   765: iload 19
      //   767: iload 16
      //   769: if_icmpne +6 -> 775
      //   772: iconst_0
      //   773: istore 18
      //   775: lload 7
      //   777: lstore 11
      //   779: iinc 15 1
      //   782: goto -342 -> 440
      //   785: aload_0
      //   786: iload 18
      //   788: putfield 208	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastIndex	I
      //   791: aload_0
      //   792: aload_3
      //   793: iload 18
      //   795: aaload
      //   796: getfield 211	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:id	J
      //   799: putfield 206	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:lastId	J
      //   802: goto +10 -> 812
      //   805: lload 11
      //   807: lstore 4
      //   809: iconst_0
      //   810: istore 17
      //   812: lload 4
      //   814: lconst_0
      //   815: lcmp
      //   816: ifeq +21 -> 837
      //   819: aload_0
      //   820: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:cancelled	Z
      //   823: ifne +14 -> 837
      //   826: aload_0
      //   827: getfield 128	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   830: lload 4
      //   832: invokeinterface 237 3 0
      //   837: iload 17
      //   839: ifeq +6 -> 845
      //   842: goto -835 -> 7
      //   845: aload_0
      //   846: iload_2
      //   847: ineg
      //   848: invokevirtual 240	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:addAndGet	(I)I
      //   851: istore 17
      //   853: iload 17
      //   855: istore_2
      //   856: iload 17
      //   858: ifne -851 -> 7
      //   861: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	862	0	this	MergeSubscriber
      //   4	515	1	localSubscriber	Subscriber
      //   6	850	2	i	int
      //   19	774	3	localObject1	Object
      //   27	804	4	l1	long
      //   39	579	6	j	int
      //   48	728	7	l2	long
      //   52	613	9	l3	long
      //   56	750	11	l4	long
      //   66	454	13	localObject2	Object
      //   549	122	13	localThrowable	Throwable
      //   697	13	13	localSimpleQueue1	SimpleQueue
      //   198	502	14	bool	boolean
      //   219	561	15	k	int
      //   301	469	16	m	int
      //   312	545	17	n	int
      //   371	423	18	i1	int
      //   411	359	19	i2	int
      //   459	262	20	localInnerSubscriber	FlowableFlatMap.InnerSubscriber
      //   477	25	21	localSimpleQueue2	SimpleQueue
      // Exception table:
      //   from	to	target	type
      //   501	510	549	finally
    }
    
    SimpleQueue<U> getInnerQueue(FlowableFlatMap.InnerSubscriber<T, U> paramInnerSubscriber)
    {
      SimpleQueue localSimpleQueue = paramInnerSubscriber.queue;
      Object localObject = localSimpleQueue;
      if (localSimpleQueue == null)
      {
        localObject = new SpscArrayQueue(this.bufferSize);
        paramInnerSubscriber.queue = ((SimpleQueue)localObject);
      }
      return localObject;
    }
    
    SimpleQueue<U> getMainQueue()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Object localObject = localSimplePlainQueue;
      if (localSimplePlainQueue == null)
      {
        if (this.maxConcurrency == Integer.MAX_VALUE) {
          localObject = new SpscLinkedArrayQueue(this.bufferSize);
        } else {
          localObject = new SpscArrayQueue(this.maxConcurrency);
        }
        this.queue = ((SimplePlainQueue)localObject);
      }
      return localObject;
    }
    
    void innerError(FlowableFlatMap.InnerSubscriber<T, U> paramInnerSubscriber, Throwable paramThrowable)
    {
      if (this.errs.addThrowable(paramThrowable))
      {
        paramInnerSubscriber.done = true;
        if (!this.delayErrors)
        {
          this.upstream.cancel();
          paramInnerSubscriber = (FlowableFlatMap.InnerSubscriber[])this.subscribers.getAndSet(CANCELLED);
          int i = paramInnerSubscriber.length;
          for (int j = 0; j < i; j++) {
            paramInnerSubscriber[j].dispose();
          }
        }
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      if (this.errs.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 198	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 82	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:mapper	Lio/reactivex/functions/Function;
      //   12: aload_1
      //   13: invokeinterface 265 2 0
      //   18: ldc_w 267
      //   21: invokestatic 273	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   24: checkcast 275	org/reactivestreams/Publisher
      //   27: astore_1
      //   28: aload_1
      //   29: instanceof 277
      //   32: ifeq +101 -> 133
      //   35: aload_1
      //   36: checkcast 277	java/util/concurrent/Callable
      //   39: invokeinterface 280 1 0
      //   44: astore_1
      //   45: aload_1
      //   46: ifnull +11 -> 57
      //   49: aload_0
      //   50: aload_1
      //   51: invokevirtual 283	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:tryEmitScalar	(Ljava/lang/Object;)V
      //   54: goto +122 -> 176
      //   57: aload_0
      //   58: getfield 86	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:maxConcurrency	I
      //   61: ldc -5
      //   63: if_icmpeq +113 -> 176
      //   66: aload_0
      //   67: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:cancelled	Z
      //   70: ifne +106 -> 176
      //   73: aload_0
      //   74: getfield 285	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:scalarEmitted	I
      //   77: iconst_1
      //   78: iadd
      //   79: istore_2
      //   80: aload_0
      //   81: iload_2
      //   82: putfield 285	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:scalarEmitted	I
      //   85: aload_0
      //   86: getfield 96	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:scalarLimit	I
      //   89: istore_3
      //   90: iload_2
      //   91: iload_3
      //   92: if_icmpne +84 -> 176
      //   95: aload_0
      //   96: iconst_0
      //   97: putfield 285	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:scalarEmitted	I
      //   100: aload_0
      //   101: getfield 128	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   104: iload_3
      //   105: i2l
      //   106: invokeinterface 237 3 0
      //   111: goto +65 -> 176
      //   114: astore_1
      //   115: aload_1
      //   116: invokestatic 220	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   119: aload_0
      //   120: getfield 68	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:errs	Lio/reactivex/internal/util/AtomicThrowable;
      //   123: aload_1
      //   124: invokevirtual 224	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   127: pop
      //   128: aload_0
      //   129: invokevirtual 259	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:drain	()V
      //   132: return
      //   133: aload_0
      //   134: getfield 287	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:uniqueId	J
      //   137: lstore 4
      //   139: aload_0
      //   140: lconst_1
      //   141: lload 4
      //   143: ladd
      //   144: putfield 287	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:uniqueId	J
      //   147: new 54	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber
      //   150: dup
      //   151: aload_0
      //   152: lload 4
      //   154: invokespecial 290	io/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber;J)V
      //   157: astore 6
      //   159: aload_0
      //   160: aload 6
      //   162: invokevirtual 292	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:addInner	(Lio/reactivex/internal/operators/flowable/FlowableFlatMap$InnerSubscriber;)Z
      //   165: ifeq +11 -> 176
      //   168: aload_1
      //   169: aload 6
      //   171: invokeinterface 296 2 0
      //   176: return
      //   177: astore_1
      //   178: aload_1
      //   179: invokestatic 220	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   182: aload_0
      //   183: getfield 128	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   186: invokeinterface 130 1 0
      //   191: aload_0
      //   192: aload_1
      //   193: invokevirtual 297	io/reactivex/internal/operators/flowable/FlowableFlatMap$MergeSubscriber:onError	(Ljava/lang/Throwable;)V
      //   196: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	197	0	this	MergeSubscriber
      //   0	197	1	paramT	T
      //   79	14	2	i	int
      //   89	16	3	j	int
      //   137	16	4	l	long
      //   157	13	6	localInnerSubscriber	FlowableFlatMap.InnerSubscriber
      // Exception table:
      //   from	to	target	type
      //   35	45	114	finally
      //   8	28	177	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        if (!this.cancelled)
        {
          int i = this.maxConcurrency;
          if (i == Integer.MAX_VALUE) {
            paramSubscription.request(Long.MAX_VALUE);
          } else {
            paramSubscription.request(i);
          }
        }
      }
    }
    
    void removeInner(FlowableFlatMap.InnerSubscriber<T, U> paramInnerSubscriber)
    {
      FlowableFlatMap.InnerSubscriber[] arrayOfInnerSubscriber1;
      FlowableFlatMap.InnerSubscriber[] arrayOfInnerSubscriber2;
      do
      {
        arrayOfInnerSubscriber1 = (FlowableFlatMap.InnerSubscriber[])this.subscribers.get();
        int i = arrayOfInnerSubscriber1.length;
        if (i == 0) {
          return;
        }
        int j = -1;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= i) {
            break;
          }
          if (arrayOfInnerSubscriber1[k] == paramInnerSubscriber)
          {
            m = k;
            break;
          }
        }
        if (m < 0) {
          return;
        }
        if (i == 1)
        {
          arrayOfInnerSubscriber2 = EMPTY;
        }
        else
        {
          arrayOfInnerSubscriber2 = new FlowableFlatMap.InnerSubscriber[i - 1];
          System.arraycopy(arrayOfInnerSubscriber1, 0, arrayOfInnerSubscriber2, 0, m);
          System.arraycopy(arrayOfInnerSubscriber1, m + 1, arrayOfInnerSubscriber2, m, i - m - 1);
        }
      } while (!this.subscribers.compareAndSet(arrayOfInnerSubscriber1, arrayOfInnerSubscriber2));
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    void tryEmit(U paramU, FlowableFlatMap.InnerSubscriber<T, U> paramInnerSubscriber)
    {
      SimpleQueue localSimpleQueue;
      Object localObject;
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        long l = this.requested.get();
        localSimpleQueue = paramInnerSubscriber.queue;
        if ((l != 0L) && ((localSimpleQueue == null) || (localSimpleQueue.isEmpty())))
        {
          this.downstream.onNext(paramU);
          if (l != Long.MAX_VALUE) {
            this.requested.decrementAndGet();
          }
          paramInnerSubscriber.requestMore(1L);
        }
        else
        {
          localObject = localSimpleQueue;
          if (localSimpleQueue == null) {
            localObject = getInnerQueue(paramInnerSubscriber);
          }
          if (!((SimpleQueue)localObject).offer(paramU))
          {
            onError(new MissingBackpressureException("Inner queue full?!"));
            return;
          }
        }
        if (decrementAndGet() != 0) {}
      }
      else
      {
        localSimpleQueue = paramInnerSubscriber.queue;
        localObject = localSimpleQueue;
        if (localSimpleQueue == null)
        {
          localObject = new SpscArrayQueue(this.bufferSize);
          paramInnerSubscriber.queue = ((SimpleQueue)localObject);
        }
        if (!((SimpleQueue)localObject).offer(paramU))
        {
          onError(new MissingBackpressureException("Inner queue full?!"));
          return;
        }
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    void tryEmitScalar(U paramU)
    {
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        long l = this.requested.get();
        SimplePlainQueue localSimplePlainQueue = this.queue;
        if ((l != 0L) && ((localSimplePlainQueue == null) || (localSimplePlainQueue.isEmpty())))
        {
          this.downstream.onNext(paramU);
          if (l != Long.MAX_VALUE) {
            this.requested.decrementAndGet();
          }
          if ((this.maxConcurrency != Integer.MAX_VALUE) && (!this.cancelled))
          {
            int i = this.scalarEmitted + 1;
            this.scalarEmitted = i;
            int j = this.scalarLimit;
            if (i == j)
            {
              this.scalarEmitted = 0;
              this.upstream.request(j);
            }
          }
        }
        else
        {
          Object localObject = localSimplePlainQueue;
          if (localSimplePlainQueue == null) {
            localObject = getMainQueue();
          }
          if (!((SimpleQueue)localObject).offer(paramU))
          {
            onError(new IllegalStateException("Scalar queue full?!"));
            return;
          }
        }
        if (decrementAndGet() != 0) {}
      }
      else
      {
        if (!getMainQueue().offer(paramU))
        {
          onError(new IllegalStateException("Scalar queue full?!"));
          return;
        }
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
  }
}
