package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelCollect<T, C>
  extends ParallelFlowable<C>
{
  final BiConsumer<? super C, ? super T> collector;
  final Callable<? extends C> initialCollection;
  final ParallelFlowable<? extends T> source;
  
  public ParallelCollect(ParallelFlowable<? extends T> paramParallelFlowable, Callable<? extends C> paramCallable, BiConsumer<? super C, ? super T> paramBiConsumer)
  {
    this.source = paramParallelFlowable;
    this.initialCollection = paramCallable;
    this.collector = paramBiConsumer;
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
  public void subscribe(Subscriber<? super C>[] paramArrayOfSubscriber)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 50	io/reactivex/internal/operators/parallel/ParallelCollect:validate	([Lorg/reactivestreams/Subscriber;)Z
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
    //   23: if_icmpge +61 -> 84
    //   26: aload_0
    //   27: getfield 26	io/reactivex/internal/operators/parallel/ParallelCollect:initialCollection	Ljava/util/concurrent/Callable;
    //   30: invokeinterface 58 1 0
    //   35: ldc 60
    //   37: invokestatic 66	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   40: astore 5
    //   42: aload_3
    //   43: iload 4
    //   45: new 7	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber
    //   48: dup
    //   49: aload_1
    //   50: iload 4
    //   52: aaload
    //   53: aload 5
    //   55: aload_0
    //   56: getfield 28	io/reactivex/internal/operators/parallel/ParallelCollect:collector	Lio/reactivex/functions/BiConsumer;
    //   59: invokespecial 69	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:<init>	(Lorg/reactivestreams/Subscriber;Ljava/lang/Object;Lio/reactivex/functions/BiConsumer;)V
    //   62: aastore
    //   63: iinc 4 1
    //   66: goto -46 -> 20
    //   69: astore 5
    //   71: aload 5
    //   73: invokestatic 75	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   76: aload_0
    //   77: aload_1
    //   78: aload 5
    //   80: invokevirtual 77	io/reactivex/internal/operators/parallel/ParallelCollect:reportError	([Lorg/reactivestreams/Subscriber;Ljava/lang/Throwable;)V
    //   83: return
    //   84: aload_0
    //   85: getfield 24	io/reactivex/internal/operators/parallel/ParallelCollect:source	Lio/reactivex/parallel/ParallelFlowable;
    //   88: aload_3
    //   89: invokevirtual 79	io/reactivex/parallel/ParallelFlowable:subscribe	([Lorg/reactivestreams/Subscriber;)V
    //   92: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	93	0	this	ParallelCollect
    //   0	93	1	paramArrayOfSubscriber	Subscriber<? super C>[]
    //   11	13	2	i	int
    //   16	73	3	arrayOfSubscriber	Subscriber[]
    //   18	46	4	j	int
    //   40	14	5	localObject	Object
    //   69	10	5	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   26	42	69	finally
  }
  
  static final class ParallelCollectSubscriber<T, C>
    extends DeferredScalarSubscriber<T, C>
  {
    private static final long serialVersionUID = -4767392946044436228L;
    C collection;
    final BiConsumer<? super C, ? super T> collector;
    boolean done;
    
    ParallelCollectSubscriber(Subscriber<? super C> paramSubscriber, C paramC, BiConsumer<? super C, ? super T> paramBiConsumer)
    {
      super();
      this.collection = paramC;
      this.collector = paramBiConsumer;
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject = this.collection;
      this.collection = null;
      complete(localObject);
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.collection = null;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 29	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:collector	Lio/reactivex/functions/BiConsumer;
      //   12: aload_0
      //   13: getfield 27	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:collection	Ljava/lang/Object;
      //   16: aload_1
      //   17: invokeinterface 70 3 0
      //   22: goto +17 -> 39
      //   25: astore_1
      //   26: aload_1
      //   27: invokestatic 75	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   30: aload_0
      //   31: invokevirtual 76	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:cancel	()V
      //   34: aload_0
      //   35: aload_1
      //   36: invokevirtual 77	io/reactivex/internal/operators/parallel/ParallelCollect$ParallelCollectSubscriber:onError	(Ljava/lang/Throwable;)V
      //   39: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	40	0	this	ParallelCollectSubscriber
      //   0	40	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   8	22	25	finally
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
