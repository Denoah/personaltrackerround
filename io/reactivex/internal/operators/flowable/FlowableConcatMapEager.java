package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMapEager<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final ErrorMode errorMode;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final int maxConcurrency;
  final int prefetch;
  
  public FlowableConcatMapEager(Flowable<T> paramFlowable, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt1, int paramInt2, ErrorMode paramErrorMode)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.maxConcurrency = paramInt1;
    this.prefetch = paramInt2;
    this.errorMode = paramErrorMode;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new ConcatMapEagerDelayErrorSubscriber(paramSubscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
  }
  
  static final class ConcatMapEagerDelayErrorSubscriber<T, R>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R>
  {
    private static final long serialVersionUID = -4255299542215038287L;
    volatile boolean cancelled;
    volatile InnerQueuedSubscriber<R> current;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    final ErrorMode errorMode;
    final AtomicThrowable errors;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;
    final AtomicLong requested;
    final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
    Subscription upstream;
    
    ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt1, int paramInt2, ErrorMode paramErrorMode)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.maxConcurrency = paramInt1;
      this.prefetch = paramInt2;
      this.errorMode = paramErrorMode;
      this.subscribers = new SpscLinkedArrayQueue(Math.min(paramInt2, paramInt1));
      this.errors = new AtomicThrowable();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (this.cancelled) {
        return;
      }
      this.cancelled = true;
      this.upstream.cancel();
      drainAndCancel();
    }
    
    void cancelAll()
    {
      InnerQueuedSubscriber localInnerQueuedSubscriber = this.current;
      this.current = null;
      if (localInnerQueuedSubscriber != null) {
        localInnerQueuedSubscriber.cancel();
      }
      for (;;)
      {
        localInnerQueuedSubscriber = (InnerQueuedSubscriber)this.subscribers.poll();
        if (localInnerQueuedSubscriber == null) {
          break;
        }
        localInnerQueuedSubscriber.cancel();
      }
    }
    
    /* Error */
    public void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 110	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 51	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 59	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errorMode	Lio/reactivex/internal/util/ErrorMode;
      //   22: astore_3
      //   23: iconst_1
      //   24: istore 4
      //   26: aload_0
      //   27: getfield 82	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   30: invokevirtual 114	java/util/concurrent/atomic/AtomicLong:get	()J
      //   33: lstore 5
      //   35: aload_1
      //   36: ifnonnull +118 -> 154
      //   39: aload_3
      //   40: getstatic 119	io/reactivex/internal/util/ErrorMode:END	Lio/reactivex/internal/util/ErrorMode;
      //   43: if_acmpeq +34 -> 77
      //   46: aload_0
      //   47: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   50: invokevirtual 121	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   53: checkcast 123	java/lang/Throwable
      //   56: ifnull +21 -> 77
      //   59: aload_0
      //   60: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   63: aload_2
      //   64: aload_0
      //   65: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   68: invokevirtual 129	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   71: invokeinterface 135 2 0
      //   76: return
      //   77: aload_0
      //   78: getfield 137	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:done	Z
      //   81: istore 7
      //   83: aload_0
      //   84: getfield 72	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:subscribers	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   87: invokevirtual 105	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   90: checkcast 100	io/reactivex/internal/subscribers/InnerQueuedSubscriber
      //   93: astore 8
      //   95: iload 7
      //   97: ifeq +37 -> 134
      //   100: aload 8
      //   102: ifnonnull +32 -> 134
      //   105: aload_0
      //   106: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   109: invokevirtual 129	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   112: astore_1
      //   113: aload_1
      //   114: ifnull +13 -> 127
      //   117: aload_2
      //   118: aload_1
      //   119: invokeinterface 135 2 0
      //   124: goto +9 -> 133
      //   127: aload_2
      //   128: invokeinterface 140 1 0
      //   133: return
      //   134: aload 8
      //   136: astore_1
      //   137: aload 8
      //   139: ifnull +15 -> 154
      //   142: aload_0
      //   143: aload 8
      //   145: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   148: aload 8
      //   150: astore_1
      //   151: goto +3 -> 154
      //   154: aload_1
      //   155: ifnull +348 -> 503
      //   158: aload_1
      //   159: invokevirtual 144	io/reactivex/internal/subscribers/InnerQueuedSubscriber:queue	()Lio/reactivex/internal/fuseable/SimpleQueue;
      //   162: astore 9
      //   164: aload 9
      //   166: ifnull +337 -> 503
      //   169: lconst_0
      //   170: lstore 10
      //   172: lload 10
      //   174: lload 5
      //   176: lcmp
      //   177: istore 12
      //   179: iload 12
      //   181: ifeq +182 -> 363
      //   184: aload_0
      //   185: getfield 88	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelled	Z
      //   188: ifeq +8 -> 196
      //   191: aload_0
      //   192: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   195: return
      //   196: aload_3
      //   197: getstatic 147	io/reactivex/internal/util/ErrorMode:IMMEDIATE	Lio/reactivex/internal/util/ErrorMode;
      //   200: if_acmpne +43 -> 243
      //   203: aload_0
      //   204: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   207: invokevirtual 121	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   210: checkcast 123	java/lang/Throwable
      //   213: ifnull +30 -> 243
      //   216: aload_0
      //   217: aconst_null
      //   218: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   221: aload_1
      //   222: invokevirtual 101	io/reactivex/internal/subscribers/InnerQueuedSubscriber:cancel	()V
      //   225: aload_0
      //   226: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   229: aload_2
      //   230: aload_0
      //   231: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   234: invokevirtual 129	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   237: invokeinterface 135 2 0
      //   242: return
      //   243: aload_1
      //   244: invokevirtual 151	io/reactivex/internal/subscribers/InnerQueuedSubscriber:isDone	()Z
      //   247: istore 7
      //   249: aload 9
      //   251: invokeinterface 154 1 0
      //   256: astore 8
      //   258: aload 8
      //   260: ifnonnull +9 -> 269
      //   263: iconst_1
      //   264: istore 13
      //   266: goto +6 -> 272
      //   269: iconst_0
      //   270: istore 13
      //   272: iload 7
      //   274: ifeq +31 -> 305
      //   277: iload 13
      //   279: ifeq +26 -> 305
      //   282: aload_0
      //   283: aconst_null
      //   284: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   287: aload_0
      //   288: getfield 90	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   291: lconst_1
      //   292: invokeinterface 158 3 0
      //   297: aconst_null
      //   298: astore_1
      //   299: iconst_1
      //   300: istore 14
      //   302: goto +64 -> 366
      //   305: iload 13
      //   307: ifeq +6 -> 313
      //   310: goto +53 -> 363
      //   313: aload_2
      //   314: aload 8
      //   316: invokeinterface 162 2 0
      //   321: lload 10
      //   323: lconst_1
      //   324: ladd
      //   325: lstore 10
      //   327: aload_1
      //   328: invokevirtual 165	io/reactivex/internal/subscribers/InnerQueuedSubscriber:requestOne	()V
      //   331: goto -159 -> 172
      //   334: astore 8
      //   336: aload 8
      //   338: invokestatic 170	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   341: aload_0
      //   342: aconst_null
      //   343: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   346: aload_1
      //   347: invokevirtual 101	io/reactivex/internal/subscribers/InnerQueuedSubscriber:cancel	()V
      //   350: aload_0
      //   351: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   354: aload_2
      //   355: aload 8
      //   357: invokeinterface 135 2 0
      //   362: return
      //   363: iconst_0
      //   364: istore 14
      //   366: iload 14
      //   368: istore 13
      //   370: aload_1
      //   371: astore 8
      //   373: iload 12
      //   375: ifne +122 -> 497
      //   378: aload_0
      //   379: getfield 88	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelled	Z
      //   382: ifeq +8 -> 390
      //   385: aload_0
      //   386: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   389: return
      //   390: aload_3
      //   391: getstatic 147	io/reactivex/internal/util/ErrorMode:IMMEDIATE	Lio/reactivex/internal/util/ErrorMode;
      //   394: if_acmpne +43 -> 437
      //   397: aload_0
      //   398: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   401: invokevirtual 121	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   404: checkcast 123	java/lang/Throwable
      //   407: ifnull +30 -> 437
      //   410: aload_0
      //   411: aconst_null
      //   412: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   415: aload_1
      //   416: invokevirtual 101	io/reactivex/internal/subscribers/InnerQueuedSubscriber:cancel	()V
      //   419: aload_0
      //   420: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:cancelAll	()V
      //   423: aload_2
      //   424: aload_0
      //   425: getfield 77	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   428: invokevirtual 129	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   431: invokeinterface 135 2 0
      //   436: return
      //   437: aload_1
      //   438: invokevirtual 151	io/reactivex/internal/subscribers/InnerQueuedSubscriber:isDone	()Z
      //   441: istore 15
      //   443: aload 9
      //   445: invokeinterface 173 1 0
      //   450: istore 7
      //   452: iload 14
      //   454: istore 13
      //   456: aload_1
      //   457: astore 8
      //   459: iload 15
      //   461: ifeq +36 -> 497
      //   464: iload 14
      //   466: istore 13
      //   468: aload_1
      //   469: astore 8
      //   471: iload 7
      //   473: ifeq +24 -> 497
      //   476: aload_0
      //   477: aconst_null
      //   478: putfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:current	Lio/reactivex/internal/subscribers/InnerQueuedSubscriber;
      //   481: aload_0
      //   482: getfield 90	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   485: lconst_1
      //   486: invokeinterface 158 3 0
      //   491: aconst_null
      //   492: astore 8
      //   494: iconst_1
      //   495: istore 13
      //   497: aload 8
      //   499: astore_1
      //   500: goto +9 -> 509
      //   503: iconst_0
      //   504: istore 13
      //   506: lconst_0
      //   507: lstore 10
      //   509: lload 10
      //   511: lconst_0
      //   512: lcmp
      //   513: ifeq +23 -> 536
      //   516: lload 5
      //   518: ldc2_w 174
      //   521: lcmp
      //   522: ifeq +14 -> 536
      //   525: aload_0
      //   526: getfield 82	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   529: lload 10
      //   531: lneg
      //   532: invokevirtual 179	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   535: pop2
      //   536: iload 13
      //   538: ifeq +6 -> 544
      //   541: goto -515 -> 26
      //   544: aload_0
      //   545: iload 4
      //   547: ineg
      //   548: invokevirtual 182	io/reactivex/internal/operators/flowable/FlowableConcatMapEager$ConcatMapEagerDelayErrorSubscriber:addAndGet	(I)I
      //   551: istore 4
      //   553: iload 4
      //   555: ifne +4 -> 559
      //   558: return
      //   559: goto -533 -> 26
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	562	0	this	ConcatMapEagerDelayErrorSubscriber
      //   12	488	1	localObject1	Object
      //   17	407	2	localSubscriber	Subscriber
      //   22	369	3	localErrorMode	ErrorMode
      //   24	530	4	i	int
      //   33	484	5	l1	long
      //   81	391	7	bool1	boolean
      //   93	222	8	localObject2	Object
      //   334	22	8	localThrowable	Throwable
      //   371	127	8	localObject3	Object
      //   162	282	9	localSimpleQueue	SimpleQueue
      //   170	360	10	l2	long
      //   177	197	12	bool2	boolean
      //   264	273	13	j	int
      //   300	165	14	k	int
      //   441	19	15	bool3	boolean
      // Exception table:
      //   from	to	target	type
      //   249	258	334	finally
    }
    
    void drainAndCancel()
    {
      if (getAndIncrement() == 0) {
        do
        {
          cancelAll();
        } while (decrementAndGet() != 0);
      }
    }
    
    public void innerComplete(InnerQueuedSubscriber<R> paramInnerQueuedSubscriber)
    {
      paramInnerQueuedSubscriber.setDone();
      drain();
    }
    
    public void innerError(InnerQueuedSubscriber<R> paramInnerQueuedSubscriber, Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        paramInnerQueuedSubscriber.setDone();
        if (this.errorMode != ErrorMode.END) {
          this.upstream.cancel();
        }
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerNext(InnerQueuedSubscriber<R> paramInnerQueuedSubscriber, R paramR)
    {
      if (paramInnerQueuedSubscriber.queue().offer(paramR))
      {
        drain();
      }
      else
      {
        paramInnerQueuedSubscriber.cancel();
        innerError(paramInnerQueuedSubscriber, new MissingBackpressureException());
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        this.done = true;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        paramT = (Publisher)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null Publisher");
        InnerQueuedSubscriber localInnerQueuedSubscriber = new InnerQueuedSubscriber(this, this.prefetch);
        if (this.cancelled) {
          return;
        }
        this.subscribers.offer(localInnerQueuedSubscriber);
        paramT.subscribe(localInnerQueuedSubscriber);
        if (this.cancelled)
        {
          localInnerQueuedSubscriber.cancel();
          drainAndCancel();
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        int i = this.maxConcurrency;
        long l;
        if (i == Integer.MAX_VALUE) {
          l = Long.MAX_VALUE;
        } else {
          l = i;
        }
        paramSubscription.request(l);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
}
