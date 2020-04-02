package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCombineLatest<T, R>
  extends Flowable<R>
{
  final Publisher<? extends T>[] array;
  final int bufferSize;
  final Function<? super Object[], ? extends R> combiner;
  final boolean delayErrors;
  final Iterable<? extends Publisher<? extends T>> iterable;
  
  public FlowableCombineLatest(Iterable<? extends Publisher<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
  {
    this.array = null;
    this.iterable = paramIterable;
    this.combiner = paramFunction;
    this.bufferSize = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  public FlowableCombineLatest(Publisher<? extends T>[] paramArrayOfPublisher, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
  {
    this.array = paramArrayOfPublisher;
    this.iterable = null;
    this.combiner = paramFunction;
    this.bufferSize = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  /* Error */
  public void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 34	io/reactivex/internal/operators/flowable/FlowableCombineLatest:array	[Lorg/reactivestreams/Publisher;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnonnull +143 -> 149
    //   9: bipush 8
    //   11: anewarray 51	org/reactivestreams/Publisher
    //   14: astore_2
    //   15: aload_0
    //   16: getfield 36	io/reactivex/internal/operators/flowable/FlowableCombineLatest:iterable	Ljava/lang/Iterable;
    //   19: invokeinterface 57 1 0
    //   24: ldc 59
    //   26: invokestatic 65	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   29: checkcast 67	java/util/Iterator
    //   32: astore_3
    //   33: iconst_0
    //   34: istore 4
    //   36: aload_3
    //   37: invokeinterface 71 1 0
    //   42: istore 5
    //   44: iload 5
    //   46: ifne +6 -> 52
    //   49: goto +104 -> 153
    //   52: aload_3
    //   53: invokeinterface 75 1 0
    //   58: ldc 77
    //   60: invokestatic 65	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   63: checkcast 51	org/reactivestreams/Publisher
    //   66: astore 6
    //   68: aload_2
    //   69: astore 7
    //   71: iload 4
    //   73: aload_2
    //   74: arraylength
    //   75: if_icmpne +25 -> 100
    //   78: iload 4
    //   80: iconst_2
    //   81: ishr
    //   82: iload 4
    //   84: iadd
    //   85: anewarray 51	org/reactivestreams/Publisher
    //   88: astore 7
    //   90: aload_2
    //   91: iconst_0
    //   92: aload 7
    //   94: iconst_0
    //   95: iload 4
    //   97: invokestatic 83	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   100: aload 7
    //   102: iload 4
    //   104: aload 6
    //   106: aastore
    //   107: iinc 4 1
    //   110: aload 7
    //   112: astore_2
    //   113: goto -77 -> 36
    //   116: astore_2
    //   117: aload_2
    //   118: invokestatic 89	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   121: aload_2
    //   122: aload_1
    //   123: invokestatic 95	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   126: return
    //   127: astore_2
    //   128: aload_2
    //   129: invokestatic 89	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   132: aload_2
    //   133: aload_1
    //   134: invokestatic 95	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   137: return
    //   138: astore_2
    //   139: aload_2
    //   140: invokestatic 89	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   143: aload_2
    //   144: aload_1
    //   145: invokestatic 95	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   148: return
    //   149: aload_2
    //   150: arraylength
    //   151: istore 4
    //   153: iload 4
    //   155: ifne +8 -> 163
    //   158: aload_1
    //   159: invokestatic 98	io/reactivex/internal/subscriptions/EmptySubscription:complete	(Lorg/reactivestreams/Subscriber;)V
    //   162: return
    //   163: iload 4
    //   165: iconst_1
    //   166: if_icmpne +28 -> 194
    //   169: aload_2
    //   170: iconst_0
    //   171: aaload
    //   172: new 100	io/reactivex/internal/operators/flowable/FlowableMap$MapSubscriber
    //   175: dup
    //   176: aload_1
    //   177: new 13	io/reactivex/internal/operators/flowable/FlowableCombineLatest$SingletonArrayFunc
    //   180: dup
    //   181: aload_0
    //   182: invokespecial 103	io/reactivex/internal/operators/flowable/FlowableCombineLatest$SingletonArrayFunc:<init>	(Lio/reactivex/internal/operators/flowable/FlowableCombineLatest;)V
    //   185: invokespecial 106	io/reactivex/internal/operators/flowable/FlowableMap$MapSubscriber:<init>	(Lorg/reactivestreams/Subscriber;Lio/reactivex/functions/Function;)V
    //   188: invokeinterface 109 2 0
    //   193: return
    //   194: new 7	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator
    //   197: dup
    //   198: aload_1
    //   199: aload_0
    //   200: getfield 38	io/reactivex/internal/operators/flowable/FlowableCombineLatest:combiner	Lio/reactivex/functions/Function;
    //   203: iload 4
    //   205: aload_0
    //   206: getfield 40	io/reactivex/internal/operators/flowable/FlowableCombineLatest:bufferSize	I
    //   209: aload_0
    //   210: getfield 42	io/reactivex/internal/operators/flowable/FlowableCombineLatest:delayErrors	Z
    //   213: invokespecial 112	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:<init>	(Lorg/reactivestreams/Subscriber;Lio/reactivex/functions/Function;IIZ)V
    //   216: astore 7
    //   218: aload_1
    //   219: aload 7
    //   221: invokeinterface 118 2 0
    //   226: aload 7
    //   228: aload_2
    //   229: iload 4
    //   231: invokevirtual 121	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:subscribe	([Lorg/reactivestreams/Publisher;I)V
    //   234: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	this	FlowableCombineLatest
    //   0	235	1	paramSubscriber	Subscriber<? super R>
    //   4	109	2	localObject1	Object
    //   116	6	2	localThrowable1	Throwable
    //   127	6	2	localThrowable2	Throwable
    //   138	91	2	localThrowable3	Throwable
    //   32	21	3	localIterator	java.util.Iterator
    //   34	196	4	i	int
    //   42	3	5	bool	boolean
    //   66	39	6	localPublisher	Publisher
    //   69	158	7	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   52	68	116	finally
    //   36	44	127	finally
    //   15	33	138	finally
  }
  
  static final class CombineLatestCoordinator<T, R>
    extends BasicIntQueueSubscription<R>
  {
    private static final long serialVersionUID = -5082275438355852221L;
    volatile boolean cancelled;
    final Function<? super Object[], ? extends R> combiner;
    int completedSources;
    final boolean delayErrors;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    final AtomicReference<Throwable> error;
    final Object[] latest;
    int nonEmptySources;
    boolean outputFused;
    final SpscLinkedArrayQueue<Object> queue;
    final AtomicLong requested;
    final FlowableCombineLatest.CombineLatestInnerSubscriber<T>[] subscribers;
    
    CombineLatestCoordinator(Subscriber<? super R> paramSubscriber, Function<? super Object[], ? extends R> paramFunction, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.combiner = paramFunction;
      paramSubscriber = new FlowableCombineLatest.CombineLatestInnerSubscriber[paramInt1];
      for (int i = 0; i < paramInt1; i++) {
        paramSubscriber[i] = new FlowableCombineLatest.CombineLatestInnerSubscriber(this, i, paramInt2);
      }
      this.subscribers = paramSubscriber;
      this.latest = new Object[paramInt1];
      this.queue = new SpscLinkedArrayQueue(paramInt2);
      this.requested = new AtomicLong();
      this.error = new AtomicReference();
      this.delayErrors = paramBoolean;
    }
    
    public void cancel()
    {
      this.cancelled = true;
      cancelAll();
    }
    
    void cancelAll()
    {
      FlowableCombineLatest.CombineLatestInnerSubscriber[] arrayOfCombineLatestInnerSubscriber = this.subscribers;
      int i = arrayOfCombineLatestInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfCombineLatestInnerSubscriber[j].cancel();
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, SpscLinkedArrayQueue<?> paramSpscLinkedArrayQueue)
    {
      if (this.cancelled)
      {
        cancelAll();
        paramSpscLinkedArrayQueue.clear();
        return true;
      }
      if (paramBoolean1) {
        if (this.delayErrors)
        {
          if (paramBoolean2)
          {
            cancelAll();
            paramSpscLinkedArrayQueue = ExceptionHelper.terminate(this.error);
            if ((paramSpscLinkedArrayQueue != null) && (paramSpscLinkedArrayQueue != ExceptionHelper.TERMINATED)) {
              paramSubscriber.onError(paramSpscLinkedArrayQueue);
            } else {
              paramSubscriber.onComplete();
            }
            return true;
          }
        }
        else
        {
          Throwable localThrowable = ExceptionHelper.terminate(this.error);
          if ((localThrowable != null) && (localThrowable != ExceptionHelper.TERMINATED))
          {
            cancelAll();
            paramSpscLinkedArrayQueue.clear();
            paramSubscriber.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            cancelAll();
            paramSubscriber.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.queue.clear();
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      if (this.outputFused) {
        drainOutput();
      } else {
        drainAsync();
      }
    }
    
    /* Error */
    void drainAsync()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:downstream	Lorg/reactivestreams/Subscriber;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 66	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   9: astore_2
      //   10: iconst_1
      //   11: istore_3
      //   12: aload_0
      //   13: getfield 71	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   16: invokevirtual 131	java/util/concurrent/atomic/AtomicLong:get	()J
      //   19: lstore 4
      //   21: lconst_0
      //   22: lstore 6
      //   24: lload 6
      //   26: lload 4
      //   28: lcmp
      //   29: istore 8
      //   31: iload 8
      //   33: ifeq +138 -> 171
      //   36: aload_0
      //   37: getfield 133	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:done	Z
      //   40: istore 9
      //   42: aload_2
      //   43: invokevirtual 137	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   46: astore 10
      //   48: aload 10
      //   50: ifnonnull +9 -> 59
      //   53: iconst_1
      //   54: istore 11
      //   56: goto +6 -> 62
      //   59: iconst_0
      //   60: istore 11
      //   62: aload_0
      //   63: iload 9
      //   65: iload 11
      //   67: aload_1
      //   68: aload_2
      //   69: invokevirtual 139	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)Z
      //   72: ifeq +4 -> 76
      //   75: return
      //   76: iload 11
      //   78: ifeq +6 -> 84
      //   81: goto +90 -> 171
      //   84: aload_2
      //   85: invokevirtual 137	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   88: checkcast 140	[Ljava/lang/Object;
      //   91: astore 12
      //   93: aload_0
      //   94: getfield 48	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:combiner	Lio/reactivex/functions/Function;
      //   97: aload 12
      //   99: invokeinterface 146 2 0
      //   104: ldc -108
      //   106: invokestatic 154	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   109: astore 12
      //   111: aload_1
      //   112: aload 12
      //   114: invokeinterface 158 2 0
      //   119: aload 10
      //   121: checkcast 50	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestInnerSubscriber
      //   124: invokevirtual 161	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestInnerSubscriber:requestOne	()V
      //   127: lload 6
      //   129: lconst_1
      //   130: ladd
      //   131: lstore 6
      //   133: goto -109 -> 24
      //   136: astore 10
      //   138: aload 10
      //   140: invokestatic 166	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   143: aload_0
      //   144: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:cancelAll	()V
      //   147: aload_0
      //   148: getfield 76	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   151: aload 10
      //   153: invokestatic 170	io/reactivex/internal/util/ExceptionHelper:addThrowable	(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Throwable;)Z
      //   156: pop
      //   157: aload_1
      //   158: aload_0
      //   159: getfield 76	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:error	Ljava/util/concurrent/atomic/AtomicReference;
      //   162: invokestatic 100	io/reactivex/internal/util/ExceptionHelper:terminate	(Ljava/util/concurrent/atomic/AtomicReference;)Ljava/lang/Throwable;
      //   165: invokeinterface 110 2 0
      //   170: return
      //   171: iload 8
      //   173: ifne +21 -> 194
      //   176: aload_0
      //   177: aload_0
      //   178: getfield 133	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:done	Z
      //   181: aload_2
      //   182: invokevirtual 174	io/reactivex/internal/queue/SpscLinkedArrayQueue:isEmpty	()Z
      //   185: aload_1
      //   186: aload_2
      //   187: invokevirtual 139	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:checkTerminated	(ZZLorg/reactivestreams/Subscriber;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)Z
      //   190: ifeq +4 -> 194
      //   193: return
      //   194: lload 6
      //   196: lconst_0
      //   197: lcmp
      //   198: ifeq +23 -> 221
      //   201: lload 4
      //   203: ldc2_w 175
      //   206: lcmp
      //   207: ifeq +14 -> 221
      //   210: aload_0
      //   211: getfield 71	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:requested	Ljava/util/concurrent/atomic/AtomicLong;
      //   214: lload 6
      //   216: lneg
      //   217: invokevirtual 180	java/util/concurrent/atomic/AtomicLong:addAndGet	(J)J
      //   220: pop2
      //   221: aload_0
      //   222: iload_3
      //   223: ineg
      //   224: invokevirtual 183	io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator:addAndGet	(I)I
      //   227: istore 8
      //   229: iload 8
      //   231: istore_3
      //   232: iload 8
      //   234: ifne -222 -> 12
      //   237: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	238	0	this	CombineLatestCoordinator
      //   4	182	1	localSubscriber	Subscriber
      //   9	178	2	localSpscLinkedArrayQueue	SpscLinkedArrayQueue
      //   11	221	3	i	int
      //   19	183	4	l1	long
      //   22	193	6	l2	long
      //   29	143	8	bool1	boolean
      //   227	6	8	j	int
      //   40	24	9	bool2	boolean
      //   46	74	10	localObject1	Object
      //   136	16	10	localThrowable	Throwable
      //   54	23	11	bool3	boolean
      //   91	22	12	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   93	111	136	finally
    }
    
    void drainOutput()
    {
      Subscriber localSubscriber = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      int i = 1;
      int j;
      do
      {
        if (this.cancelled)
        {
          localSpscLinkedArrayQueue.clear();
          return;
        }
        Throwable localThrowable = (Throwable)this.error.get();
        if (localThrowable != null)
        {
          localSpscLinkedArrayQueue.clear();
          localSubscriber.onError(localThrowable);
          return;
        }
        boolean bool1 = this.done;
        boolean bool2 = localSpscLinkedArrayQueue.isEmpty();
        if (!bool2) {
          localSubscriber.onNext(null);
        }
        if ((bool1) && (bool2))
        {
          localSubscriber.onComplete();
          return;
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    void innerComplete(int paramInt)
    {
      try
      {
        Object[] arrayOfObject = this.latest;
        if (arrayOfObject[paramInt] != null)
        {
          paramInt = this.completedSources + 1;
          if (paramInt == arrayOfObject.length) {
            this.done = true;
          } else {
            this.completedSources = paramInt;
          }
        }
        else
        {
          this.done = true;
        }
        drain();
        return;
      }
      finally {}
    }
    
    void innerError(int paramInt, Throwable paramThrowable)
    {
      if (ExceptionHelper.addThrowable(this.error, paramThrowable))
      {
        if (!this.delayErrors)
        {
          cancelAll();
          this.done = true;
          drain();
        }
        else
        {
          innerComplete(paramInt);
        }
      }
      else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerValue(int paramInt, T paramT)
    {
      try
      {
        Object[] arrayOfObject = this.latest;
        int i = this.nonEmptySources;
        int j = i;
        if (arrayOfObject[paramInt] == null)
        {
          j = i + 1;
          this.nonEmptySources = j;
        }
        arrayOfObject[paramInt] = paramT;
        if (arrayOfObject.length == j)
        {
          this.queue.offer(this.subscribers[paramInt], arrayOfObject.clone());
          j = 0;
        }
        else
        {
          j = 1;
        }
        if (j != 0) {
          this.subscribers[paramInt].requestOne();
        } else {
          drain();
        }
        return;
      }
      finally {}
    }
    
    public boolean isEmpty()
    {
      return this.queue.isEmpty();
    }
    
    public R poll()
      throws Exception
    {
      Object localObject1 = this.queue.poll();
      if (localObject1 == null) {
        return null;
      }
      Object localObject2 = (Object[])this.queue.poll();
      localObject2 = ObjectHelper.requireNonNull(this.combiner.apply(localObject2), "The combiner returned a null value");
      ((FlowableCombineLatest.CombineLatestInnerSubscriber)localObject1).requestOne();
      return localObject2;
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
      boolean bool = false;
      if ((paramInt & 0x4) != 0) {
        return 0;
      }
      paramInt &= 0x2;
      if (paramInt != 0) {
        bool = true;
      }
      this.outputFused = bool;
      return paramInt;
    }
    
    void subscribe(Publisher<? extends T>[] paramArrayOfPublisher, int paramInt)
    {
      FlowableCombineLatest.CombineLatestInnerSubscriber[] arrayOfCombineLatestInnerSubscriber = this.subscribers;
      for (int i = 0; (i < paramInt) && (!this.done) && (!this.cancelled); i++) {
        paramArrayOfPublisher[i].subscribe(arrayOfCombineLatestInnerSubscriber[i]);
      }
    }
  }
  
  static final class CombineLatestInnerSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -8730235182291002949L;
    final int index;
    final int limit;
    final FlowableCombineLatest.CombineLatestCoordinator<T, ?> parent;
    final int prefetch;
    int produced;
    
    CombineLatestInnerSubscriber(FlowableCombineLatest.CombineLatestCoordinator<T, ?> paramCombineLatestCoordinator, int paramInt1, int paramInt2)
    {
      this.parent = paramCombineLatestCoordinator;
      this.index = paramInt1;
      this.prefetch = paramInt2;
      this.limit = (paramInt2 - (paramInt2 >> 2));
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      this.parent.innerComplete(this.index);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(this.index, paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.parent.innerValue(this.index, paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, this.prefetch);
    }
    
    public void requestOne()
    {
      int i = this.produced + 1;
      if (i == this.limit)
      {
        this.produced = 0;
        ((Subscription)get()).request(i);
      }
      else
      {
        this.produced = i;
      }
    }
  }
  
  final class SingletonArrayFunc
    implements Function<T, R>
  {
    SingletonArrayFunc() {}
    
    public R apply(T paramT)
      throws Exception
    {
      return FlowableCombineLatest.this.combiner.apply(new Object[] { paramT });
    }
  }
}
