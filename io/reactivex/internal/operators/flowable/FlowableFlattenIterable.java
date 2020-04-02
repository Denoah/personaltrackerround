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
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlattenIterable<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final Function<? super T, ? extends Iterable<? extends R>> mapper;
  final int prefetch;
  
  public FlowableFlattenIterable(Flowable<T> paramFlowable, Function<? super T, ? extends Iterable<? extends R>> paramFunction, int paramInt)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.prefetch = paramInt;
  }
  
  public void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    if ((this.source instanceof Callable)) {
      try
      {
        Object localObject = ((Callable)this.source).call();
        if (localObject == null)
        {
          EmptySubscription.complete(paramSubscriber);
          return;
        }
        try
        {
          localObject = ((Iterable)this.mapper.apply(localObject)).iterator();
          FlowableFromIterable.subscribe(paramSubscriber, (Iterator)localObject);
          return;
        }
        finally {}
        this.source.subscribe(new FlattenIterableSubscriber(paramSubscriber, this.mapper, this.prefetch));
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable2);
        EmptySubscription.error(localThrowable2, paramSubscriber);
        return;
      }
    }
  }
  
  static final class FlattenIterableSubscriber<T, R>
    extends BasicIntQueueSubscription<R>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -3096000382929934955L;
    volatile boolean cancelled;
    int consumed;
    Iterator<? extends R> current;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    final AtomicReference<Throwable> error;
    int fusionMode;
    final int limit;
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    SimpleQueue<T> queue;
    final AtomicLong requested;
    Subscription upstream;
    
    FlattenIterableSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Iterable<? extends R>> paramFunction, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      this.error = new AtomicReference();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, SimpleQueue<?> paramSimpleQueue)
    {
      if (this.cancelled)
      {
        this.current = null;
        paramSimpleQueue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        if ((Throwable)this.error.get() != null)
        {
          Throwable localThrowable = ExceptionHelper.terminate(this.error);
          this.current = null;
          paramSimpleQueue.clear();
          paramSubscriber.onError(localThrowable);
          return true;
        }
        if (paramBoolean2)
        {
          paramSubscriber.onComplete();
          return true;
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.current = null;
      this.queue.clear();
    }
    
    void consumedOne(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        int i = this.consumed + 1;
        if (i == this.limit)
        {
          this.consumed = 0;
          this.upstream.request(i);
        }
        else
        {
          this.consumed = i;
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 80	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 48	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 82	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 124	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:fusionMode	I
      //   22: iconst_1
      //   23: if_icmpeq +8 -> 31
      //   26: iconst_1
      //   27: istore_3
      //   28: goto +5 -> 33
      //   31: iconst_0
      //   32: istore_3
      //   33: aload_0
      //   34: getfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   37: astore 4
      //   39: iconst_1
      //   40: istore 5
      //   42: aload 4
      //   44: astore 6
      //   46: aload 4
      //   48: ifnonnull +204 -> 252
      //   51: aload_0
      //   52: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:done	Z
      //   55: istore 7
      //   57: aload_2
      //   58: invokeinterface 129 1 0
      //   63: astore 8
      //   65: aload 8
      //   67: ifnonnull +9 -> 76
      //   70: iconst_1
      //   71: istore 9
      //   73: goto +6 -> 79
      //   76: iconst_0
      //   77: istore 9
      //   79: aload_0
      //   80: iload 7
      //   82: iload 9
      //   84: aload_1
      //   85: aload_2
      //   86: invokevirtual 131	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)Z
      //   89: ifeq +4 -> 93
      //   92: return
      //   93: aload 4
      //   95: astore 6
      //   97: aload 8
      //   99: ifnull +153 -> 252
      //   102: aload_0
      //   103: getfield 50	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:mapper	Lio/reactivex/functions/Function;
      //   106: aload 8
      //   108: invokeinterface 137 2 0
      //   113: checkcast 139	java/lang/Iterable
      //   116: invokeinterface 143 1 0
      //   121: astore 6
      //   123: aload 6
      //   125: invokeinterface 149 1 0
      //   130: istore 9
      //   132: iload 9
      //   134: ifne +14 -> 148
      //   137: aload_0
      //   138: iload_3
      //   139: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:consumedOne	(Z)V
      //   142: aconst_null
      //   143: astore 4
      //   145: goto -103 -> 42
      //   148: aload_0
      //   149: aload 6
      //   151: putfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   154: goto +98 -> 252
      //   157: astore 6
      //   159: aload 6
      //   161: invokestatic 156	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   164: aload_0
      //   165: getfield 72	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   168: invokeinterface 76 1 0
      //   173: aload_0
      //   174: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   177: aload 6
      //   179: invokestatic 160	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   182: pop
      //   183: aload_1
      //   184: aload_0
      //   185: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   188: invokestatic 103	io/reactivex/internal/util/ExceptionHelper:terminate	(Ljava/util/concurrent/atomic/AtomicReference;)Ljava/lang/Throwable;
      //   191: invokeinterface 109 2 0
      //   196: return
      //   197: astore 6
      //   199: aload 6
      //   201: invokestatic 156	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   204: aload_0
      //   205: getfield 72	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   208: invokeinterface 76 1 0
      //   213: aload_0
      //   214: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   217: aload 6
      //   219: invokestatic 160	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   222: pop
      //   223: aload_0
      //   224: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   227: invokestatic 103	io/reactivex/internal/util/ExceptionHelper:terminate	(Ljava/util/concurrent/atomic/AtomicReference;)Ljava/lang/Throwable;
      //   230: astore 6
      //   232: aload_0
      //   233: aconst_null
      //   234: putfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   237: aload_2
      //   238: invokeinterface 87 1 0
      //   243: aload_1
      //   244: aload 6
      //   246: invokeinterface 109 2 0
      //   251: return
      //   252: aload 6
      //   254: astore 8
      //   256: aload 6
      //   258: ifnull +306 -> 564
      //   261: aload_0
      //   262: getfield 64	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   265: invokevirtual 163	java/util/concurrent/atomic/AtomicLong:get	()J
      //   268: lstore 10
      //   270: lconst_0
      //   271: lstore 12
      //   273: aload 6
      //   275: astore 4
      //   277: lload 12
      //   279: lstore 14
      //   281: lload 12
      //   283: lload 10
      //   285: lcmp
      //   286: ifeq +188 -> 474
      //   289: aload_0
      //   290: aload_0
      //   291: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:done	Z
      //   294: iconst_0
      //   295: aload_1
      //   296: aload_2
      //   297: invokevirtual 131	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)Z
      //   300: ifeq +4 -> 304
      //   303: return
      //   304: aload 6
      //   306: invokeinterface 166 1 0
      //   311: ldc -88
      //   313: invokestatic 174	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   316: astore 4
      //   318: aload_1
      //   319: aload 4
      //   321: invokeinterface 178 2 0
      //   326: aload_0
      //   327: aload_0
      //   328: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:done	Z
      //   331: iconst_0
      //   332: aload_1
      //   333: aload_2
      //   334: invokevirtual 131	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)Z
      //   337: ifeq +4 -> 341
      //   340: return
      //   341: lload 12
      //   343: lconst_1
      //   344: ladd
      //   345: lstore 12
      //   347: aload 6
      //   349: invokeinterface 149 1 0
      //   354: istore 9
      //   356: iload 9
      //   358: ifne +23 -> 381
      //   361: aload_0
      //   362: iload_3
      //   363: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:consumedOne	(Z)V
      //   366: aload_0
      //   367: aconst_null
      //   368: putfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   371: aconst_null
      //   372: astore 4
      //   374: lload 12
      //   376: lstore 14
      //   378: goto +96 -> 474
      //   381: goto -108 -> 273
      //   384: astore 6
      //   386: aload 6
      //   388: invokestatic 156	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   391: aload_0
      //   392: aconst_null
      //   393: putfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   396: aload_0
      //   397: getfield 72	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   400: invokeinterface 76 1 0
      //   405: aload_0
      //   406: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   409: aload 6
      //   411: invokestatic 160	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   414: pop
      //   415: aload_1
      //   416: aload_0
      //   417: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   420: invokestatic 103	io/reactivex/internal/util/ExceptionHelper:terminate	(Ljava/util/concurrent/atomic/AtomicReference;)Ljava/lang/Throwable;
      //   423: invokeinterface 109 2 0
      //   428: return
      //   429: astore 6
      //   431: aload 6
      //   433: invokestatic 156	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   436: aload_0
      //   437: aconst_null
      //   438: putfield 91	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:current	Ljava/util/Iterator;
      //   441: aload_0
      //   442: getfield 72	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   445: invokeinterface 76 1 0
      //   450: aload_0
      //   451: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   454: aload 6
      //   456: invokestatic 160	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   459: pop
      //   460: aload_1
      //   461: aload_0
      //   462: getfield 59	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   465: invokestatic 103	io/reactivex/internal/util/ExceptionHelper:terminate	(Ljava/util/concurrent/atomic/AtomicReference;)Ljava/lang/Throwable;
      //   468: invokeinterface 109 2 0
      //   473: return
      //   474: lload 14
      //   476: lload 10
      //   478: lcmp
      //   479: ifne +46 -> 525
      //   482: aload_0
      //   483: getfield 126	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:done	Z
      //   486: istore 7
      //   488: aload_2
      //   489: invokeinterface 181 1 0
      //   494: ifeq +14 -> 508
      //   497: aload 4
      //   499: ifnonnull +9 -> 508
      //   502: iconst_1
      //   503: istore 9
      //   505: goto +6 -> 511
      //   508: iconst_0
      //   509: istore 9
      //   511: aload_0
      //   512: iload 7
      //   514: iload 9
      //   516: aload_1
      //   517: aload_2
      //   518: invokevirtual 131	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/fuseable/SimpleQueue;)Z
      //   521: ifeq +4 -> 525
      //   524: return
      //   525: lload 14
      //   527: lconst_0
      //   528: lcmp
      //   529: ifeq +23 -> 552
      //   532: lload 10
      //   534: ldc2_w 182
      //   537: lcmp
      //   538: ifeq +14 -> 552
      //   541: aload_0
      //   542: getfield 64	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   545: lload 14
      //   547: lneg
      //   548: invokevirtual 187	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   551: pop2
      //   552: aload 4
      //   554: astore 8
      //   556: aload 4
      //   558: ifnonnull +6 -> 564
      //   561: goto +26 -> 587
      //   564: aload_0
      //   565: iload 5
      //   567: ineg
      //   568: invokevirtual 190	io/reactivex/internal/operators/flowable/FlowableFlattenIterable$FlattenIterableSubscriber:addAndGet	(I)I
      //   571: istore 16
      //   573: aload 8
      //   575: astore 4
      //   577: iload 16
      //   579: istore 5
      //   581: iload 16
      //   583: ifne +4 -> 587
      //   586: return
      //   587: goto -545 -> 42
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	590	0	this	FlattenIterableSubscriber
      //   12	505	1	localSubscriber	Subscriber
      //   17	501	2	localSimpleQueue	SimpleQueue
      //   27	336	3	bool1	boolean
      //   37	539	4	localObject1	Object
      //   40	540	5	i	int
      //   44	106	6	localObject2	Object
      //   157	21	6	localThrowable1	Throwable
      //   197	21	6	localThrowable2	Throwable
      //   230	118	6	localThrowable3	Throwable
      //   384	26	6	localThrowable4	Throwable
      //   429	26	6	localThrowable5	Throwable
      //   55	458	7	bool2	boolean
      //   63	511	8	localObject3	Object
      //   71	444	9	bool3	boolean
      //   268	265	10	l1	long
      //   271	104	12	l2	long
      //   279	267	14	l3	long
      //   571	11	16	j	int
      // Exception table:
      //   from	to	target	type
      //   102	132	157	finally
      //   57	65	197	finally
      //   347	356	384	finally
      //   304	318	429	finally
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if ((this.current == null) && (this.queue.isEmpty())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
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
      if ((!this.done) && (ExceptionHelper.addThrowable(this.error, paramThrowable)))
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
      if (this.done) {
        return;
      }
      if ((this.fusionMode == 0) && (!this.queue.offer(paramT)))
      {
        onError(new MissingBackpressureException("Queue is full?!"));
        return;
      }
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(3);
          if (i == 1)
          {
            this.fusionMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            this.downstream.onSubscribe(this);
            return;
          }
          if (i == 2)
          {
            this.fusionMode = i;
            this.queue = localQueueSubscription;
            this.downstream.onSubscribe(this);
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public R poll()
      throws Exception
    {
      Object localObject2;
      for (Object localObject1 = this.current;; localObject1 = null)
      {
        localObject2 = localObject1;
        if (localObject1 != null) {
          break label65;
        }
        localObject1 = this.queue.poll();
        if (localObject1 == null) {
          return null;
        }
        localObject2 = ((Iterable)this.mapper.apply(localObject1)).iterator();
        if (((Iterator)localObject2).hasNext()) {
          break;
        }
      }
      this.current = ((Iterator)localObject2);
      label65:
      localObject1 = ObjectHelper.requireNonNull(((Iterator)localObject2).next(), "The iterator returned a null value");
      if (!((Iterator)localObject2).hasNext()) {
        this.current = null;
      }
      return localObject1;
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    public int requestFusion(int paramInt)
    {
      if (((paramInt & 0x1) != 0) && (this.fusionMode == 1)) {
        return 1;
      }
      return 0;
    }
  }
}
