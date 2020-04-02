package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchMap<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final int bufferSize;
  final boolean delayErrors;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  
  public FlowableSwitchMap(Flowable<T> paramFlowable, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.bufferSize = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, paramSubscriber, this.mapper)) {
      return;
    }
    this.source.subscribe(new SwitchMapSubscriber(paramSubscriber, this.mapper, this.bufferSize, this.delayErrors));
  }
  
  static final class SwitchMapInnerSubscriber<T, R>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<R>
  {
    private static final long serialVersionUID = 3837284832786408377L;
    final int bufferSize;
    volatile boolean done;
    int fusionMode;
    final long index;
    final FlowableSwitchMap.SwitchMapSubscriber<T, R> parent;
    volatile SimpleQueue<R> queue;
    
    SwitchMapInnerSubscriber(FlowableSwitchMap.SwitchMapSubscriber<T, R> paramSwitchMapSubscriber, long paramLong, int paramInt)
    {
      this.parent = paramSwitchMapSubscriber;
      this.index = paramLong;
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      FlowableSwitchMap.SwitchMapSubscriber localSwitchMapSubscriber = this.parent;
      if (this.index == localSwitchMapSubscriber.unique)
      {
        this.done = true;
        localSwitchMapSubscriber.drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      FlowableSwitchMap.SwitchMapSubscriber localSwitchMapSubscriber = this.parent;
      if ((this.index == localSwitchMapSubscriber.unique) && (localSwitchMapSubscriber.error.addThrowable(paramThrowable)))
      {
        if (!localSwitchMapSubscriber.delayErrors) {
          localSwitchMapSubscriber.upstream.cancel();
        }
        this.done = true;
        localSwitchMapSubscriber.drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(R paramR)
    {
      FlowableSwitchMap.SwitchMapSubscriber localSwitchMapSubscriber = this.parent;
      if (this.index == localSwitchMapSubscriber.unique)
      {
        if ((this.fusionMode == 0) && (!this.queue.offer(paramR)))
        {
          onError(new MissingBackpressureException("Queue full?!"));
          return;
        }
        localSwitchMapSubscriber.drain();
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
            paramSubscription.request(this.bufferSize);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.bufferSize);
        paramSubscription.request(this.bufferSize);
      }
    }
  }
  
  static final class SwitchMapSubscriber<T, R>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    static final FlowableSwitchMap.SwitchMapInnerSubscriber<Object, Object> CANCELLED;
    private static final long serialVersionUID = -3491074160481096299L;
    final AtomicReference<FlowableSwitchMap.SwitchMapInnerSubscriber<T, R>> active = new AtomicReference();
    final int bufferSize;
    volatile boolean cancelled;
    final boolean delayErrors;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    final AtomicThrowable error;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final AtomicLong requested = new AtomicLong();
    volatile long unique;
    Subscription upstream;
    
    static
    {
      FlowableSwitchMap.SwitchMapInnerSubscriber localSwitchMapInnerSubscriber = new FlowableSwitchMap.SwitchMapInnerSubscriber(null, -1L, 1);
      CANCELLED = localSwitchMapInnerSubscriber;
      localSwitchMapInnerSubscriber.cancel();
    }
    
    SwitchMapSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.bufferSize = paramInt;
      this.delayErrors = paramBoolean;
      this.error = new AtomicThrowable();
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        disposeInner();
      }
    }
    
    void disposeInner()
    {
      FlowableSwitchMap.SwitchMapInnerSubscriber localSwitchMapInnerSubscriber1 = (FlowableSwitchMap.SwitchMapInnerSubscriber)this.active.get();
      FlowableSwitchMap.SwitchMapInnerSubscriber localSwitchMapInnerSubscriber2 = CANCELLED;
      if (localSwitchMapInnerSubscriber1 != localSwitchMapInnerSubscriber2)
      {
        localSwitchMapInnerSubscriber1 = (FlowableSwitchMap.SwitchMapInnerSubscriber)this.active.getAndSet(localSwitchMapInnerSubscriber2);
        if ((localSwitchMapInnerSubscriber1 != CANCELLED) && (localSwitchMapInnerSubscriber1 != null)) {
          localSwitchMapInnerSubscriber1.cancel();
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 106	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 72	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   12: astore_1
      //   13: iconst_1
      //   14: istore_2
      //   15: aload_0
      //   16: getfield 87	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:cancelled	Z
      //   19: ifeq +12 -> 31
      //   22: aload_0
      //   23: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   26: aconst_null
      //   27: invokevirtual 110	java/util/concurrent/atomic/AtomicReference:lazySet	(Ljava/lang/Object;)V
      //   30: return
      //   31: aload_0
      //   32: getfield 112	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:done	Z
      //   35: ifeq +104 -> 139
      //   38: aload_0
      //   39: getfield 78	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:delayErrors	Z
      //   42: ifeq +49 -> 91
      //   45: aload_0
      //   46: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   49: invokevirtual 97	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   52: ifnonnull +87 -> 139
      //   55: aload_0
      //   56: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   59: invokevirtual 113	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   62: checkcast 115	java/lang/Throwable
      //   65: ifnull +19 -> 84
      //   68: aload_1
      //   69: aload_0
      //   70: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   73: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   76: invokeinterface 125 2 0
      //   81: goto +9 -> 90
      //   84: aload_1
      //   85: invokeinterface 128 1 0
      //   90: return
      //   91: aload_0
      //   92: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   95: invokevirtual 113	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   98: checkcast 115	java/lang/Throwable
      //   101: ifnull +21 -> 122
      //   104: aload_0
      //   105: invokevirtual 93	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:disposeInner	()V
      //   108: aload_1
      //   109: aload_0
      //   110: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   113: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   116: invokeinterface 125 2 0
      //   121: return
      //   122: aload_0
      //   123: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   126: invokevirtual 97	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   129: ifnonnull +10 -> 139
      //   132: aload_1
      //   133: invokeinterface 128 1 0
      //   138: return
      //   139: aload_0
      //   140: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   143: invokevirtual 97	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   146: checkcast 45	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber
      //   149: astore_3
      //   150: aload_3
      //   151: ifnull +12 -> 163
      //   154: aload_3
      //   155: getfield 132	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   158: astore 4
      //   160: goto +6 -> 166
      //   163: aconst_null
      //   164: astore 4
      //   166: aload 4
      //   168: ifnull +365 -> 533
      //   171: aload_3
      //   172: getfield 133	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber:done	Z
      //   175: ifeq +87 -> 262
      //   178: aload_0
      //   179: getfield 78	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:delayErrors	Z
      //   182: ifne +57 -> 239
      //   185: aload_0
      //   186: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   189: invokevirtual 113	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   192: checkcast 115	java/lang/Throwable
      //   195: ifnull +21 -> 216
      //   198: aload_0
      //   199: invokevirtual 93	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:disposeInner	()V
      //   202: aload_1
      //   203: aload_0
      //   204: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   207: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   210: invokeinterface 125 2 0
      //   215: return
      //   216: aload 4
      //   218: invokeinterface 139 1 0
      //   223: ifeq +39 -> 262
      //   226: aload_0
      //   227: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   230: aload_3
      //   231: aconst_null
      //   232: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   235: pop
      //   236: goto -221 -> 15
      //   239: aload 4
      //   241: invokeinterface 139 1 0
      //   246: ifeq +16 -> 262
      //   249: aload_0
      //   250: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   253: aload_3
      //   254: aconst_null
      //   255: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   258: pop
      //   259: goto -244 -> 15
      //   262: aload_0
      //   263: getfield 70	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   266: invokevirtual 146	java/util/concurrent/atomic/AtomicLong:get	()J
      //   269: lstore 5
      //   271: lconst_0
      //   272: lstore 7
      //   274: iconst_0
      //   275: istore 9
      //   277: iload 9
      //   279: istore 10
      //   281: lload 7
      //   283: lload 5
      //   285: lcmp
      //   286: ifeq +191 -> 477
      //   289: aload_0
      //   290: getfield 87	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:cancelled	Z
      //   293: ifeq +4 -> 297
      //   296: return
      //   297: aload_3
      //   298: getfield 133	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber:done	Z
      //   301: istore 11
      //   303: aload 4
      //   305: invokeinterface 149 1 0
      //   310: astore 12
      //   312: goto +30 -> 342
      //   315: astore 12
      //   317: aload 12
      //   319: invokestatic 154	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   322: aload_3
      //   323: invokevirtual 56	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber:cancel	()V
      //   326: aload_0
      //   327: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   330: aload 12
      //   332: invokevirtual 158	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   335: pop
      //   336: aconst_null
      //   337: astore 12
      //   339: iconst_1
      //   340: istore 11
      //   342: aload 12
      //   344: ifnonnull +9 -> 353
      //   347: iconst_1
      //   348: istore 10
      //   350: goto +6 -> 356
      //   353: iconst_0
      //   354: istore 10
      //   356: aload_3
      //   357: aload_0
      //   358: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   361: invokevirtual 97	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   364: if_acmpeq +9 -> 373
      //   367: iconst_1
      //   368: istore 10
      //   370: goto +107 -> 477
      //   373: iload 11
      //   375: ifeq +73 -> 448
      //   378: aload_0
      //   379: getfield 78	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:delayErrors	Z
      //   382: ifne +48 -> 430
      //   385: aload_0
      //   386: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   389: invokevirtual 113	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   392: checkcast 115	java/lang/Throwable
      //   395: ifnull +17 -> 412
      //   398: aload_1
      //   399: aload_0
      //   400: getfield 83	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   403: invokevirtual 119	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   406: invokeinterface 125 2 0
      //   411: return
      //   412: iload 10
      //   414: ifeq +34 -> 448
      //   417: aload_0
      //   418: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   421: aload_3
      //   422: aconst_null
      //   423: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   426: pop
      //   427: goto -60 -> 367
      //   430: iload 10
      //   432: ifeq +16 -> 448
      //   435: aload_0
      //   436: getfield 65	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:active	Ljava/util/concurrent/atomic/AtomicReference;
      //   439: aload_3
      //   440: aconst_null
      //   441: invokevirtual 143	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   444: pop
      //   445: goto -78 -> 367
      //   448: iload 10
      //   450: ifeq +10 -> 460
      //   453: iload 9
      //   455: istore 10
      //   457: goto +20 -> 477
      //   460: aload_1
      //   461: aload 12
      //   463: invokeinterface 161 2 0
      //   468: lload 7
      //   470: lconst_1
      //   471: ladd
      //   472: lstore 7
      //   474: goto -200 -> 274
      //   477: lload 7
      //   479: lconst_0
      //   480: lcmp
      //   481: ifeq +44 -> 525
      //   484: aload_0
      //   485: getfield 87	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:cancelled	Z
      //   488: ifne +37 -> 525
      //   491: lload 5
      //   493: ldc2_w 162
      //   496: lcmp
      //   497: ifeq +14 -> 511
      //   500: aload_0
      //   501: getfield 70	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   504: lload 7
      //   506: lneg
      //   507: invokevirtual 167	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   510: pop2
      //   511: aload_3
      //   512: invokevirtual 168	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapInnerSubscriber:get	()Ljava/lang/Object;
      //   515: checkcast 9	org/reactivestreams/Subscription
      //   518: lload 7
      //   520: invokeinterface 172 3 0
      //   525: iload 10
      //   527: ifeq +6 -> 533
      //   530: goto -515 -> 15
      //   533: aload_0
      //   534: iload_2
      //   535: ineg
      //   536: invokevirtual 175	io/reactivex/internal/operators/flowable/FlowableSwitchMap$SwitchMapSubscriber:addAndGet	(I)I
      //   539: istore 10
      //   541: iload 10
      //   543: istore_2
      //   544: iload 10
      //   546: ifne -531 -> 15
      //   549: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	550	0	this	SwitchMapSubscriber
      //   12	449	1	localSubscriber	Subscriber
      //   14	530	2	i	int
      //   149	363	3	localSwitchMapInnerSubscriber	FlowableSwitchMap.SwitchMapInnerSubscriber
      //   158	146	4	localSimpleQueue	SimpleQueue
      //   269	223	5	l1	long
      //   272	247	7	l2	long
      //   275	179	9	j	int
      //   279	266	10	k	int
      //   301	73	11	bool	boolean
      //   310	1	12	localObject1	Object
      //   315	16	12	localThrowable	Throwable
      //   337	125	12	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   303	312	315	finally
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
      if ((!this.done) && (this.error.addThrowable(paramThrowable)))
      {
        if (!this.delayErrors) {
          disposeInner();
        }
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
      if (this.done) {
        return;
      }
      long l = this.unique + 1L;
      this.unique = l;
      FlowableSwitchMap.SwitchMapInnerSubscriber localSwitchMapInnerSubscriber1 = (FlowableSwitchMap.SwitchMapInnerSubscriber)this.active.get();
      if (localSwitchMapInnerSubscriber1 != null) {
        localSwitchMapInnerSubscriber1.cancel();
      }
      try
      {
        paramT = (Publisher)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The publisher returned is null");
        localSwitchMapInnerSubscriber1 = new FlowableSwitchMap.SwitchMapInnerSubscriber(this, l, this.bufferSize);
        FlowableSwitchMap.SwitchMapInnerSubscriber localSwitchMapInnerSubscriber2;
        do
        {
          localSwitchMapInnerSubscriber2 = (FlowableSwitchMap.SwitchMapInnerSubscriber)this.active.get();
          if (localSwitchMapInnerSubscriber2 == CANCELLED) {
            break;
          }
        } while (!this.active.compareAndSet(localSwitchMapInnerSubscriber2, localSwitchMapInnerSubscriber1));
        paramT.subscribe(localSwitchMapInnerSubscriber1);
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
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        if (this.unique == 0L) {
          this.upstream.request(Long.MAX_VALUE);
        } else {
          drain();
        }
      }
    }
  }
}
