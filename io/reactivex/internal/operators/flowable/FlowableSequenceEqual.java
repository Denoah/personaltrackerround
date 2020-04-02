package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSequenceEqual<T>
  extends Flowable<Boolean>
{
  final BiPredicate<? super T, ? super T> comparer;
  final Publisher<? extends T> first;
  final int prefetch;
  final Publisher<? extends T> second;
  
  public FlowableSequenceEqual(Publisher<? extends T> paramPublisher1, Publisher<? extends T> paramPublisher2, BiPredicate<? super T, ? super T> paramBiPredicate, int paramInt)
  {
    this.first = paramPublisher1;
    this.second = paramPublisher2;
    this.comparer = paramBiPredicate;
    this.prefetch = paramInt;
  }
  
  public void subscribeActual(Subscriber<? super Boolean> paramSubscriber)
  {
    EqualCoordinator localEqualCoordinator = new EqualCoordinator(paramSubscriber, this.prefetch, this.comparer);
    paramSubscriber.onSubscribe(localEqualCoordinator);
    localEqualCoordinator.subscribe(this.first, this.second);
  }
  
  static final class EqualCoordinator<T>
    extends DeferredScalarSubscription<Boolean>
    implements FlowableSequenceEqual.EqualCoordinatorHelper
  {
    private static final long serialVersionUID = -6178010334400373240L;
    final BiPredicate<? super T, ? super T> comparer;
    final AtomicThrowable error;
    final FlowableSequenceEqual.EqualSubscriber<T> first;
    final FlowableSequenceEqual.EqualSubscriber<T> second;
    T v1;
    T v2;
    final AtomicInteger wip;
    
    EqualCoordinator(Subscriber<? super Boolean> paramSubscriber, int paramInt, BiPredicate<? super T, ? super T> paramBiPredicate)
    {
      super();
      this.comparer = paramBiPredicate;
      this.wip = new AtomicInteger();
      this.first = new FlowableSequenceEqual.EqualSubscriber(this, paramInt);
      this.second = new FlowableSequenceEqual.EqualSubscriber(this, paramInt);
      this.error = new AtomicThrowable();
    }
    
    public void cancel()
    {
      super.cancel();
      this.first.cancel();
      this.second.cancel();
      if (this.wip.getAndIncrement() == 0)
      {
        this.first.clear();
        this.second.clear();
      }
    }
    
    void cancelAndClear()
    {
      this.first.cancel();
      this.first.clear();
      this.second.cancel();
      this.second.clear();
    }
    
    /* Error */
    public void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 43	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   4: invokevirtual 68	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
      //   7: ifeq +4 -> 11
      //   10: return
      //   11: iconst_1
      //   12: istore_1
      //   13: aload_0
      //   14: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   17: getfield 77	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   20: astore_2
      //   21: aload_0
      //   22: getfield 52	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   25: getfield 77	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   28: astore_3
      //   29: aload_2
      //   30: ifnull +419 -> 449
      //   33: aload_3
      //   34: ifnull +415 -> 449
      //   37: aload_0
      //   38: invokevirtual 81	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:isCancelled	()Z
      //   41: ifeq +18 -> 59
      //   44: aload_0
      //   45: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   48: invokevirtual 71	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   51: aload_0
      //   52: getfield 52	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   55: invokevirtual 71	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   58: return
      //   59: aload_0
      //   60: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   63: invokevirtual 85	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   66: checkcast 87	java/lang/Throwable
      //   69: ifnull +24 -> 93
      //   72: aload_0
      //   73: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   76: aload_0
      //   77: getfield 93	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   80: aload_0
      //   81: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   84: invokevirtual 97	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   87: invokeinterface 103 2 0
      //   92: return
      //   93: aload_0
      //   94: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   97: getfield 107	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:done	Z
      //   100: istore 4
      //   102: aload_0
      //   103: getfield 109	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   106: astore 5
      //   108: aload 5
      //   110: astore 6
      //   112: aload 5
      //   114: ifnonnull +58 -> 172
      //   117: aload_2
      //   118: invokeinterface 114 1 0
      //   123: astore 6
      //   125: aload_0
      //   126: aload 6
      //   128: putfield 109	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   131: goto +41 -> 172
      //   134: astore 6
      //   136: aload 6
      //   138: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   141: aload_0
      //   142: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   145: aload_0
      //   146: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   149: aload 6
      //   151: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   154: pop
      //   155: aload_0
      //   156: getfield 93	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   159: aload_0
      //   160: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   163: invokevirtual 97	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   166: invokeinterface 103 2 0
      //   171: return
      //   172: aload 6
      //   174: ifnonnull +9 -> 183
      //   177: iconst_1
      //   178: istore 7
      //   180: goto +6 -> 186
      //   183: iconst_0
      //   184: istore 7
      //   186: aload_0
      //   187: getfield 52	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   190: getfield 107	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:done	Z
      //   193: istore 8
      //   195: aload_0
      //   196: getfield 125	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   199: astore 9
      //   201: aload 9
      //   203: astore 5
      //   205: aload 9
      //   207: ifnonnull +58 -> 265
      //   210: aload_3
      //   211: invokeinterface 114 1 0
      //   216: astore 5
      //   218: aload_0
      //   219: aload 5
      //   221: putfield 125	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   224: goto +41 -> 265
      //   227: astore 6
      //   229: aload 6
      //   231: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   234: aload_0
      //   235: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   238: aload_0
      //   239: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   242: aload 6
      //   244: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   247: pop
      //   248: aload_0
      //   249: getfield 93	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   252: aload_0
      //   253: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   256: invokevirtual 97	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   259: invokeinterface 103 2 0
      //   264: return
      //   265: aload 5
      //   267: ifnonnull +9 -> 276
      //   270: iconst_1
      //   271: istore 10
      //   273: goto +6 -> 279
      //   276: iconst_0
      //   277: istore 10
      //   279: iload 4
      //   281: ifeq +27 -> 308
      //   284: iload 8
      //   286: ifeq +22 -> 308
      //   289: iload 7
      //   291: ifeq +17 -> 308
      //   294: iload 10
      //   296: ifeq +12 -> 308
      //   299: aload_0
      //   300: iconst_1
      //   301: invokestatic 131	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   304: invokevirtual 135	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:complete	(Ljava/lang/Object;)V
      //   307: return
      //   308: iload 4
      //   310: ifeq +28 -> 338
      //   313: iload 8
      //   315: ifeq +23 -> 338
      //   318: iload 7
      //   320: iload 10
      //   322: if_icmpeq +16 -> 338
      //   325: aload_0
      //   326: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   329: aload_0
      //   330: iconst_0
      //   331: invokestatic 131	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   334: invokevirtual 135	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:complete	(Ljava/lang/Object;)V
      //   337: return
      //   338: iload 7
      //   340: ifne +165 -> 505
      //   343: iload 10
      //   345: ifeq +6 -> 351
      //   348: goto +157 -> 505
      //   351: aload_0
      //   352: getfield 36	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:comparer	Lio/reactivex/functions/BiPredicate;
      //   355: aload 6
      //   357: aload 5
      //   359: invokeinterface 141 3 0
      //   364: istore 8
      //   366: iload 8
      //   368: ifne +16 -> 384
      //   371: aload_0
      //   372: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   375: aload_0
      //   376: iconst_0
      //   377: invokestatic 131	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   380: invokevirtual 135	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:complete	(Ljava/lang/Object;)V
      //   383: return
      //   384: aload_0
      //   385: aconst_null
      //   386: putfield 109	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   389: aload_0
      //   390: aconst_null
      //   391: putfield 125	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   394: aload_0
      //   395: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   398: invokevirtual 144	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:request	()V
      //   401: aload_0
      //   402: getfield 52	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   405: invokevirtual 144	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:request	()V
      //   408: goto -371 -> 37
      //   411: astore 6
      //   413: aload 6
      //   415: invokestatic 119	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   418: aload_0
      //   419: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   422: aload_0
      //   423: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   426: aload 6
      //   428: invokevirtual 123	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   431: pop
      //   432: aload_0
      //   433: getfield 93	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   436: aload_0
      //   437: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   440: invokevirtual 97	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   443: invokeinterface 103 2 0
      //   448: return
      //   449: aload_0
      //   450: invokevirtual 81	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:isCancelled	()Z
      //   453: ifeq +18 -> 471
      //   456: aload_0
      //   457: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   460: invokevirtual 71	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   463: aload_0
      //   464: getfield 52	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   467: invokevirtual 71	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   470: return
      //   471: aload_0
      //   472: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   475: invokevirtual 85	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   478: checkcast 87	java/lang/Throwable
      //   481: ifnull +24 -> 505
      //   484: aload_0
      //   485: invokevirtual 89	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:cancelAndClear	()V
      //   488: aload_0
      //   489: getfield 93	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   492: aload_0
      //   493: getfield 57	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   496: invokevirtual 97	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   499: invokeinterface 103 2 0
      //   504: return
      //   505: aload_0
      //   506: getfield 43	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualCoordinator:wip	Ljava/util/concurrent/atomic/AtomicInteger;
      //   509: iload_1
      //   510: ineg
      //   511: invokevirtual 148	java/util/concurrent/atomic/AtomicInteger:addAndGet	(I)I
      //   514: istore 7
      //   516: iload 7
      //   518: istore_1
      //   519: iload 7
      //   521: ifne -508 -> 13
      //   524: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	525	0	this	EqualCoordinator
      //   12	507	1	i	int
      //   20	98	2	localSimpleQueue1	SimpleQueue
      //   28	183	3	localSimpleQueue2	SimpleQueue
      //   100	209	4	bool1	boolean
      //   106	252	5	localObject1	Object
      //   110	17	6	localObject2	Object
      //   134	39	6	localThrowable1	Throwable
      //   227	129	6	localThrowable2	Throwable
      //   411	16	6	localThrowable3	Throwable
      //   178	342	7	j	int
      //   193	174	8	bool2	boolean
      //   199	7	9	localObject3	Object
      //   271	73	10	k	int
      // Exception table:
      //   from	to	target	type
      //   117	125	134	finally
      //   210	218	227	finally
      //   351	366	411	finally
    }
    
    public void innerError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable)) {
        drain();
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void subscribe(Publisher<? extends T> paramPublisher1, Publisher<? extends T> paramPublisher2)
    {
      paramPublisher1.subscribe(this.first);
      paramPublisher2.subscribe(this.second);
    }
  }
  
  static abstract interface EqualCoordinatorHelper
  {
    public abstract void drain();
    
    public abstract void innerError(Throwable paramThrowable);
  }
  
  static final class EqualSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 4804128302091633067L;
    volatile boolean done;
    final int limit;
    final FlowableSequenceEqual.EqualCoordinatorHelper parent;
    final int prefetch;
    long produced;
    volatile SimpleQueue<T> queue;
    int sourceMode;
    
    EqualSubscriber(FlowableSequenceEqual.EqualCoordinatorHelper paramEqualCoordinatorHelper, int paramInt)
    {
      this.parent = paramEqualCoordinatorHelper;
      this.limit = (paramInt - (paramInt >> 2));
      this.prefetch = paramInt;
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    void clear()
    {
      SimpleQueue localSimpleQueue = this.queue;
      if (localSimpleQueue != null) {
        localSimpleQueue.clear();
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if ((this.sourceMode == 0) && (!this.queue.offer(paramT)))
      {
        onError(new MissingBackpressureException());
        return;
      }
      this.parent.drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this, paramSubscription))
      {
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          int i = localQueueSubscription.requestFusion(3);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            this.done = true;
            this.parent.drain();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = localQueueSubscription;
            paramSubscription.request(this.prefetch);
            return;
          }
        }
        this.queue = new SpscArrayQueue(this.prefetch);
        paramSubscription.request(this.prefetch);
      }
    }
    
    public void request()
    {
      if (this.sourceMode != 1)
      {
        long l = this.produced + 1L;
        if (l >= this.limit)
        {
          this.produced = 0L;
          ((Subscription)get()).request(l);
        }
        else
        {
          this.produced = l;
        }
      }
    }
  }
}
