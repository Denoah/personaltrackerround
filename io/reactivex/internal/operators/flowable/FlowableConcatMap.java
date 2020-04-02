package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMap<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final ErrorMode errorMode;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final int prefetch;
  
  public FlowableConcatMap(Flowable<T> paramFlowable, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, ErrorMode paramErrorMode)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.prefetch = paramInt;
    this.errorMode = paramErrorMode;
  }
  
  public static <T, R> Subscriber<T> subscribe(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, ErrorMode paramErrorMode)
  {
    int i = 1.$SwitchMap$io$reactivex$internal$util$ErrorMode[paramErrorMode.ordinal()];
    if (i != 1)
    {
      if (i != 2) {
        return new ConcatMapImmediate(paramSubscriber, paramFunction, paramInt);
      }
      return new ConcatMapDelayed(paramSubscriber, paramFunction, paramInt, true);
    }
    return new ConcatMapDelayed(paramSubscriber, paramFunction, paramInt, false);
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, paramSubscriber, this.mapper)) {
      return;
    }
    this.source.subscribe(subscribe(paramSubscriber, this.mapper, this.prefetch, this.errorMode));
  }
  
  static abstract class BaseConcatMapSubscriber<T, R>
    extends AtomicInteger
    implements FlowableSubscriber<T>, FlowableConcatMap.ConcatMapSupport<R>, Subscription
  {
    private static final long serialVersionUID = -3511336836796789179L;
    volatile boolean active;
    volatile boolean cancelled;
    int consumed;
    volatile boolean done;
    final AtomicThrowable errors;
    final FlowableConcatMap.ConcatMapInner<R> inner;
    final int limit;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int prefetch;
    SimpleQueue<T> queue;
    int sourceMode;
    Subscription upstream;
    
    BaseConcatMapSubscriber(Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt)
    {
      this.mapper = paramFunction;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      this.inner = new FlowableConcatMap.ConcatMapInner(this);
      this.errors = new AtomicThrowable();
    }
    
    abstract void drain();
    
    public final void innerComplete()
    {
      this.active = false;
      drain();
    }
    
    public final void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public final void onNext(T paramT)
    {
      if ((this.sourceMode != 2) && (!this.queue.offer(paramT)))
      {
        this.upstream.cancel();
        onError(new IllegalStateException("Queue full?!"));
        return;
      }
      drain();
    }
    
    public final void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(7);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            subscribeActual();
            drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            subscribeActual();
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        subscribeActual();
        paramSubscription.request(this.prefetch);
      }
    }
    
    abstract void subscribeActual();
  }
  
  static final class ConcatMapDelayed<T, R>
    extends FlowableConcatMap.BaseConcatMapSubscriber<T, R>
  {
    private static final long serialVersionUID = -2945777694260521066L;
    final Subscriber<? super R> downstream;
    final boolean veryEnd;
    
    ConcatMapDelayed(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
    {
      super(paramInt);
      this.downstream = paramSubscriber;
      this.veryEnd = paramBoolean;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.inner.cancel();
        this.upstream.cancel();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 54	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:getAndIncrement	()I
      //   4: ifne +419 -> 423
      //   7: aload_0
      //   8: getfield 34	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:cancelled	Z
      //   11: ifeq +4 -> 15
      //   14: return
      //   15: aload_0
      //   16: getfield 57	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:active	Z
      //   19: ifne +397 -> 416
      //   22: aload_0
      //   23: getfield 60	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:done	Z
      //   26: istore_1
      //   27: iload_1
      //   28: ifeq +40 -> 68
      //   31: aload_0
      //   32: getfield 26	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:veryEnd	Z
      //   35: ifne +33 -> 68
      //   38: aload_0
      //   39: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   42: invokevirtual 70	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   45: checkcast 72	java/lang/Throwable
      //   48: ifnull +20 -> 68
      //   51: aload_0
      //   52: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   55: aload_0
      //   56: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   59: invokevirtual 76	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   62: invokeinterface 82 2 0
      //   67: return
      //   68: aload_0
      //   69: getfield 86	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   72: invokeinterface 91 1 0
      //   77: astore_2
      //   78: aload_2
      //   79: ifnonnull +8 -> 87
      //   82: iconst_1
      //   83: istore_3
      //   84: goto +5 -> 89
      //   87: iconst_0
      //   88: istore_3
      //   89: iload_1
      //   90: ifeq +42 -> 132
      //   93: iload_3
      //   94: ifeq +38 -> 132
      //   97: aload_0
      //   98: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   101: invokevirtual 76	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   104: astore_2
      //   105: aload_2
      //   106: ifnull +16 -> 122
      //   109: aload_0
      //   110: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   113: aload_2
      //   114: invokeinterface 82 2 0
      //   119: goto +12 -> 131
      //   122: aload_0
      //   123: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   126: invokeinterface 94 1 0
      //   131: return
      //   132: iload_3
      //   133: ifne +283 -> 416
      //   136: aload_0
      //   137: getfield 98	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:mapper	Lio/reactivex/functions/Function;
      //   140: aload_2
      //   141: invokeinterface 104 2 0
      //   146: ldc 106
      //   148: invokestatic 112	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   151: checkcast 114	org/reactivestreams/Publisher
      //   154: astore_2
      //   155: aload_0
      //   156: getfield 118	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:sourceMode	I
      //   159: iconst_1
      //   160: if_icmpeq +42 -> 202
      //   163: aload_0
      //   164: getfield 121	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:consumed	I
      //   167: iconst_1
      //   168: iadd
      //   169: istore_3
      //   170: iload_3
      //   171: aload_0
      //   172: getfield 124	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:limit	I
      //   175: if_icmpne +22 -> 197
      //   178: aload_0
      //   179: iconst_0
      //   180: putfield 121	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:consumed	I
      //   183: aload_0
      //   184: getfield 46	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:upstream	Lorg/reactivestreams/Subscription;
      //   187: iload_3
      //   188: i2l
      //   189: invokeinterface 128 3 0
      //   194: goto +8 -> 202
      //   197: aload_0
      //   198: iload_3
      //   199: putfield 121	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:consumed	I
      //   202: aload_2
      //   203: instanceof 130
      //   206: ifeq +112 -> 318
      //   209: aload_2
      //   210: checkcast 130	java/util/concurrent/Callable
      //   213: astore_2
      //   214: aload_2
      //   215: invokeinterface 133 1 0
      //   220: astore_2
      //   221: aload_2
      //   222: ifnonnull +6 -> 228
      //   225: goto -218 -> 7
      //   228: aload_0
      //   229: getfield 38	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   232: invokevirtual 137	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner:isUnbounded	()Z
      //   235: ifeq +16 -> 251
      //   238: aload_0
      //   239: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   242: aload_2
      //   243: invokeinterface 141 2 0
      //   248: goto -241 -> 7
      //   251: aload_0
      //   252: iconst_1
      //   253: putfield 57	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:active	Z
      //   256: aload_0
      //   257: getfield 38	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   260: new 143	io/reactivex/internal/operators/flowable/FlowableConcatMap$WeakScalarSubscription
      //   263: dup
      //   264: aload_2
      //   265: aload_0
      //   266: getfield 38	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   269: invokespecial 146	io/reactivex/internal/operators/flowable/FlowableConcatMap$WeakScalarSubscription:<init>	(Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)V
      //   272: invokevirtual 150	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner:setSubscription	(Lorg/reactivestreams/Subscription;)V
      //   275: goto +141 -> 416
      //   278: astore_2
      //   279: aload_2
      //   280: invokestatic 155	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   283: aload_0
      //   284: getfield 46	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:upstream	Lorg/reactivestreams/Subscription;
      //   287: invokeinterface 49 1 0
      //   292: aload_0
      //   293: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   296: aload_2
      //   297: invokevirtual 159	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   300: pop
      //   301: aload_0
      //   302: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   305: aload_0
      //   306: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   309: invokevirtual 76	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   312: invokeinterface 82 2 0
      //   317: return
      //   318: aload_0
      //   319: iconst_1
      //   320: putfield 57	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:active	Z
      //   323: aload_2
      //   324: aload_0
      //   325: getfield 38	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   328: invokeinterface 163 2 0
      //   333: goto +83 -> 416
      //   336: astore_2
      //   337: aload_2
      //   338: invokestatic 155	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   341: aload_0
      //   342: getfield 46	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:upstream	Lorg/reactivestreams/Subscription;
      //   345: invokeinterface 49 1 0
      //   350: aload_0
      //   351: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   354: aload_2
      //   355: invokevirtual 159	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   358: pop
      //   359: aload_0
      //   360: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   363: aload_0
      //   364: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   367: invokevirtual 76	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   370: invokeinterface 82 2 0
      //   375: return
      //   376: astore_2
      //   377: aload_2
      //   378: invokestatic 155	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   381: aload_0
      //   382: getfield 46	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:upstream	Lorg/reactivestreams/Subscription;
      //   385: invokeinterface 49 1 0
      //   390: aload_0
      //   391: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   394: aload_2
      //   395: invokevirtual 159	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   398: pop
      //   399: aload_0
      //   400: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:downstream	Lorg/reactivestreams/Subscriber;
      //   403: aload_0
      //   404: getfield 64	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   407: invokevirtual 76	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   410: invokeinterface 82 2 0
      //   415: return
      //   416: aload_0
      //   417: invokevirtual 166	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapDelayed:decrementAndGet	()I
      //   420: ifne -413 -> 7
      //   423: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	424	0	this	ConcatMapDelayed
      //   26	64	1	bool	boolean
      //   77	188	2	localObject	Object
      //   278	46	2	localThrowable1	Throwable
      //   336	19	2	localThrowable2	Throwable
      //   376	19	2	localThrowable3	Throwable
      //   83	116	3	i	int
      // Exception table:
      //   from	to	target	type
      //   214	221	278	finally
      //   136	155	336	finally
      //   68	78	376	finally
    }
    
    public void innerError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        if (!this.veryEnd)
        {
          this.upstream.cancel();
          this.done = true;
        }
        this.active = false;
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerNext(R paramR)
    {
      this.downstream.onNext(paramR);
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
    
    public void request(long paramLong)
    {
      this.inner.request(paramLong);
    }
    
    void subscribeActual()
    {
      this.downstream.onSubscribe(this);
    }
  }
  
  static final class ConcatMapImmediate<T, R>
    extends FlowableConcatMap.BaseConcatMapSubscriber<T, R>
  {
    private static final long serialVersionUID = 7898995095634264146L;
    final Subscriber<? super R> downstream;
    final AtomicInteger wip;
    
    ConcatMapImmediate(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt)
    {
      super(paramInt);
      this.downstream = paramSubscriber;
      this.wip = new AtomicInteger();
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.inner.cancel();
        this.upstream.cancel();
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   4: invokevirtual 59	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
      //   7: ifne +395 -> 402
      //   10: aload_0
      //   11: getfield 39	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:cancelled	Z
      //   14: ifeq +4 -> 18
      //   17: return
      //   18: aload_0
      //   19: getfield 62	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:active	Z
      //   22: ifne +370 -> 392
      //   25: aload_0
      //   26: getfield 65	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:done	Z
      //   29: istore_1
      //   30: aload_0
      //   31: getfield 69	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   34: invokeinterface 75 1 0
      //   39: astore_2
      //   40: aload_2
      //   41: ifnonnull +8 -> 49
      //   44: iconst_1
      //   45: istore_3
      //   46: goto +5 -> 51
      //   49: iconst_0
      //   50: istore_3
      //   51: iload_1
      //   52: ifeq +17 -> 69
      //   55: iload_3
      //   56: ifeq +13 -> 69
      //   59: aload_0
      //   60: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   63: invokeinterface 80 1 0
      //   68: return
      //   69: iload_3
      //   70: ifne +322 -> 392
      //   73: aload_0
      //   74: getfield 84	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:mapper	Lio/reactivex/functions/Function;
      //   77: aload_2
      //   78: invokeinterface 90 2 0
      //   83: ldc 92
      //   85: invokestatic 98	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   88: checkcast 100	org/reactivestreams/Publisher
      //   91: astore_2
      //   92: aload_0
      //   93: getfield 104	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:sourceMode	I
      //   96: iconst_1
      //   97: if_icmpeq +42 -> 139
      //   100: aload_0
      //   101: getfield 107	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:consumed	I
      //   104: iconst_1
      //   105: iadd
      //   106: istore_3
      //   107: iload_3
      //   108: aload_0
      //   109: getfield 110	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:limit	I
      //   112: if_icmpne +22 -> 134
      //   115: aload_0
      //   116: iconst_0
      //   117: putfield 107	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:consumed	I
      //   120: aload_0
      //   121: getfield 51	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:upstream	Lorg/reactivestreams/Subscription;
      //   124: iload_3
      //   125: i2l
      //   126: invokeinterface 114 3 0
      //   131: goto +8 -> 139
      //   134: aload_0
      //   135: iload_3
      //   136: putfield 107	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:consumed	I
      //   139: aload_2
      //   140: instanceof 116
      //   143: ifeq +151 -> 294
      //   146: aload_2
      //   147: checkcast 116	java/util/concurrent/Callable
      //   150: astore_2
      //   151: aload_2
      //   152: invokeinterface 119 1 0
      //   157: astore_2
      //   158: aload_2
      //   159: ifnonnull +6 -> 165
      //   162: goto -152 -> 10
      //   165: aload_0
      //   166: getfield 43	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   169: invokevirtual 123	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner:isUnbounded	()Z
      //   172: ifeq +55 -> 227
      //   175: aload_0
      //   176: invokevirtual 126	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:get	()I
      //   179: ifne -169 -> 10
      //   182: aload_0
      //   183: iconst_0
      //   184: iconst_1
      //   185: invokevirtual 130	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:compareAndSet	(II)Z
      //   188: ifeq -178 -> 10
      //   191: aload_0
      //   192: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   195: aload_2
      //   196: invokeinterface 134 2 0
      //   201: aload_0
      //   202: iconst_1
      //   203: iconst_0
      //   204: invokevirtual 130	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:compareAndSet	(II)Z
      //   207: ifne -197 -> 10
      //   210: aload_0
      //   211: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   214: aload_0
      //   215: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   218: invokevirtual 144	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   221: invokeinterface 148 2 0
      //   226: return
      //   227: aload_0
      //   228: iconst_1
      //   229: putfield 62	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:active	Z
      //   232: aload_0
      //   233: getfield 43	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   236: new 150	io/reactivex/internal/operators/flowable/FlowableConcatMap$WeakScalarSubscription
      //   239: dup
      //   240: aload_2
      //   241: aload_0
      //   242: getfield 43	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   245: invokespecial 153	io/reactivex/internal/operators/flowable/FlowableConcatMap$WeakScalarSubscription:<init>	(Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)V
      //   248: invokevirtual 157	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner:setSubscription	(Lorg/reactivestreams/Subscription;)V
      //   251: goto +141 -> 392
      //   254: astore_2
      //   255: aload_2
      //   256: invokestatic 162	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   259: aload_0
      //   260: getfield 51	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:upstream	Lorg/reactivestreams/Subscription;
      //   263: invokeinterface 54 1 0
      //   268: aload_0
      //   269: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   272: aload_2
      //   273: invokevirtual 166	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   276: pop
      //   277: aload_0
      //   278: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   281: aload_0
      //   282: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   285: invokevirtual 144	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   288: invokeinterface 148 2 0
      //   293: return
      //   294: aload_0
      //   295: iconst_1
      //   296: putfield 62	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:active	Z
      //   299: aload_2
      //   300: aload_0
      //   301: getfield 43	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:inner	Lio/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapInner;
      //   304: invokeinterface 170 2 0
      //   309: goto +83 -> 392
      //   312: astore_2
      //   313: aload_2
      //   314: invokestatic 162	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   317: aload_0
      //   318: getfield 51	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:upstream	Lorg/reactivestreams/Subscription;
      //   321: invokeinterface 54 1 0
      //   326: aload_0
      //   327: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   330: aload_2
      //   331: invokevirtual 166	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   334: pop
      //   335: aload_0
      //   336: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   339: aload_0
      //   340: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   343: invokevirtual 144	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   346: invokeinterface 148 2 0
      //   351: return
      //   352: astore_2
      //   353: aload_2
      //   354: invokestatic 162	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   357: aload_0
      //   358: getfield 51	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:upstream	Lorg/reactivestreams/Subscription;
      //   361: invokeinterface 54 1 0
      //   366: aload_0
      //   367: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   370: aload_2
      //   371: invokevirtual 166	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   374: pop
      //   375: aload_0
      //   376: getfield 24	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:downstream	Lorg/reactivestreams/Subscriber;
      //   379: aload_0
      //   380: getfield 138	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   383: invokevirtual 144	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   386: invokeinterface 148 2 0
      //   391: return
      //   392: aload_0
      //   393: getfield 31	io/reactivex/internal/operators/flowable/FlowableConcatMap$ConcatMapImmediate:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   396: invokevirtual 173	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
      //   399: ifne -389 -> 10
      //   402: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	403	0	this	ConcatMapImmediate
      //   29	23	1	bool	boolean
      //   39	202	2	localObject	Object
      //   254	46	2	localThrowable1	Throwable
      //   312	19	2	localThrowable2	Throwable
      //   352	19	2	localThrowable3	Throwable
      //   45	91	3	i	int
      // Exception table:
      //   from	to	target	type
      //   151	158	254	finally
      //   73	92	312	finally
      //   30	40	352	finally
    }
    
    public void innerError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.downstream.onError(this.errors.terminate());
        }
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void innerNext(R paramR)
    {
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        this.downstream.onNext(paramR);
        if (compareAndSet(1, 0)) {
          return;
        }
        this.downstream.onError(this.errors.terminate());
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        this.inner.cancel();
        if (getAndIncrement() == 0) {
          this.downstream.onError(this.errors.terminate());
        }
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void request(long paramLong)
    {
      this.inner.request(paramLong);
    }
    
    void subscribeActual()
    {
      this.downstream.onSubscribe(this);
    }
  }
  
  static final class ConcatMapInner<R>
    extends SubscriptionArbiter
    implements FlowableSubscriber<R>
  {
    private static final long serialVersionUID = 897683679971470653L;
    final FlowableConcatMap.ConcatMapSupport<R> parent;
    long produced;
    
    ConcatMapInner(FlowableConcatMap.ConcatMapSupport<R> paramConcatMapSupport)
    {
      super();
      this.parent = paramConcatMapSupport;
    }
    
    public void onComplete()
    {
      long l = this.produced;
      if (l != 0L)
      {
        this.produced = 0L;
        produced(l);
      }
      this.parent.innerComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      long l = this.produced;
      if (l != 0L)
      {
        this.produced = 0L;
        produced(l);
      }
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(R paramR)
    {
      this.produced += 1L;
      this.parent.innerNext(paramR);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      setSubscription(paramSubscription);
    }
  }
  
  static abstract interface ConcatMapSupport<T>
  {
    public abstract void innerComplete();
    
    public abstract void innerError(Throwable paramThrowable);
    
    public abstract void innerNext(T paramT);
  }
  
  static final class WeakScalarSubscription<T>
    implements Subscription
  {
    final Subscriber<? super T> downstream;
    boolean once;
    final T value;
    
    WeakScalarSubscription(T paramT, Subscriber<? super T> paramSubscriber)
    {
      this.value = paramT;
      this.downstream = paramSubscriber;
    }
    
    public void cancel() {}
    
    public void request(long paramLong)
    {
      if ((paramLong > 0L) && (!this.once))
      {
        this.once = true;
        Subscriber localSubscriber = this.downstream;
        localSubscriber.onNext(this.value);
        localSubscriber.onComplete();
      }
    }
  }
}
