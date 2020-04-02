package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.BiFunction;
import io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduce<T, R>
  extends ParallelFlowable<R>
{
  final Callable<R> initialSupplier;
  final BiFunction<R, ? super T, R> reducer;
  final ParallelFlowable<? extends T> source;
  
  public ParallelReduce(ParallelFlowable<? extends T> paramParallelFlowable, Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    this.source = paramParallelFlowable;
    this.initialSupplier = paramCallable;
    this.reducer = paramBiFunction;
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  void reportError(Subscriber<?>[] paramArrayOfSubscriber, Throwable paramThrowable)
  {
    int i = paramArrayOfSubscriber.length;
    for (int j = 0; j < i; j++) {
      EmptySubscription.error(paramThrowable, paramArrayOfSubscriber[j]);
    }
  }
  
  /* Error */
  public void subscribe(Subscriber<? super R>[] paramArrayOfSubscriber)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 50	io/reactivex/internal/operators/parallel/ParallelReduce:validate	([Lorg/reactivestreams/Subscriber;)Z
    //   5: ifne +4 -> 9
    //   8: return
    //   9: aload_1
    //   10: arraylength
    //   11: istore_2
    //   12: iload_2
    //   13: anewarray 52	org/reactivestreams/Subscriber
    //   16: astore_3
    //   17: iconst_0
    //   18: istore 4
    //   20: iload 4
    //   22: iload_2
    //   23: if_icmpge +58 -> 81
    //   26: aload_0
    //   27: getfield 26	io/reactivex/internal/operators/parallel/ParallelReduce:initialSupplier	Ljava/util/concurrent/Callable;
    //   30: invokeinterface 58 1 0
    //   35: ldc 60
    //   37: invokestatic 66	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   40: astore 5
    //   42: aload_3
    //   43: iload 4
    //   45: new 7	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber
    //   48: dup
    //   49: aload_1
    //   50: iload 4
    //   52: aaload
    //   53: aload 5
    //   55: aload_0
    //   56: getfield 28	io/reactivex/internal/operators/parallel/ParallelReduce:reducer	Lio/reactivex/functions/BiFunction;
    //   59: invokespecial 69	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:<init>	(Lorg/reactivestreams/Subscriber;Ljava/lang/Object;Lio/reactivex/functions/BiFunction;)V
    //   62: aastore
    //   63: iinc 4 1
    //   66: goto -46 -> 20
    //   69: astore_3
    //   70: aload_3
    //   71: invokestatic 75	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   74: aload_0
    //   75: aload_1
    //   76: aload_3
    //   77: invokevirtual 77	io/reactivex/internal/operators/parallel/ParallelReduce:reportError	([Lorg/reactivestreams/Subscriber;Ljava/lang/Throwable;)V
    //   80: return
    //   81: aload_0
    //   82: getfield 24	io/reactivex/internal/operators/parallel/ParallelReduce:source	Lio/reactivex/parallel/ParallelFlowable;
    //   85: aload_3
    //   86: invokevirtual 79	io/reactivex/parallel/ParallelFlowable:subscribe	([Lorg/reactivestreams/Subscriber;)V
    //   89: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	ParallelReduce
    //   0	90	1	paramArrayOfSubscriber	Subscriber<? super R>[]
    //   11	13	2	i	int
    //   16	27	3	arrayOfSubscriber	Subscriber[]
    //   69	17	3	localThrowable	Throwable
    //   18	46	4	j	int
    //   40	14	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   26	42	69	finally
  }
  
  static final class ParallelReduceSubscriber<T, R>
    extends DeferredScalarSubscriber<T, R>
  {
    private static final long serialVersionUID = 8200530050639449080L;
    R accumulator;
    boolean done;
    final BiFunction<R, ? super T, R> reducer;
    
    ParallelReduceSubscriber(Subscriber<? super R> paramSubscriber, R paramR, BiFunction<R, ? super T, R> paramBiFunction)
    {
      super();
      this.accumulator = paramR;
      this.reducer = paramBiFunction;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        Object localObject = this.accumulator;
        this.accumulator = null;
        complete(localObject);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.accumulator = null;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:done	Z
      //   4: ifne +45 -> 49
      //   7: aload_0
      //   8: getfield 29	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:reducer	Lio/reactivex/functions/BiFunction;
      //   11: aload_0
      //   12: getfield 27	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:accumulator	Ljava/lang/Object;
      //   15: aload_1
      //   16: invokeinterface 70 3 0
      //   21: ldc 72
      //   23: invokestatic 78	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   26: astore_1
      //   27: aload_0
      //   28: aload_1
      //   29: putfield 27	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:accumulator	Ljava/lang/Object;
      //   32: goto +17 -> 49
      //   35: astore_1
      //   36: aload_1
      //   37: invokestatic 83	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   40: aload_0
      //   41: invokevirtual 84	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:cancel	()V
      //   44: aload_0
      //   45: aload_1
      //   46: invokevirtual 85	io/reactivex/internal/operators/parallel/ParallelReduce$ParallelReduceSubscriber:onError	(Ljava/lang/Throwable;)V
      //   49: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	50	0	this	ParallelReduceSubscriber
      //   0	50	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   7	27	35	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
  }
}
