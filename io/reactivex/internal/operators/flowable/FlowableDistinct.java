package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDistinct<T, K>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Callable<? extends Collection<? super K>> collectionSupplier;
  final Function<? super T, K> keySelector;
  
  public FlowableDistinct(Flowable<T> paramFlowable, Function<? super T, K> paramFunction, Callable<? extends Collection<? super K>> paramCallable)
  {
    super(paramFlowable);
    this.keySelector = paramFunction;
    this.collectionSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
      this.source.subscribe(new DistinctSubscriber(paramSubscriber, this.keySelector, localCollection));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class DistinctSubscriber<T, K>
    extends BasicFuseableSubscriber<T, T>
  {
    final Collection<? super K> collection;
    final Function<? super T, K> keySelector;
    
    DistinctSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, K> paramFunction, Collection<? super K> paramCollection)
    {
      super();
      this.keySelector = paramFunction;
      this.collection = paramCollection;
    }
    
    public void clear()
    {
      this.collection.clear();
      super.clear();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.collection.clear();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
      }
      else
      {
        this.done = true;
        this.collection.clear();
        this.downstream.onError(paramThrowable);
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 38	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 59	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:sourceMode	I
      //   12: ifne +67 -> 79
      //   15: aload_0
      //   16: getfield 21	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:keySelector	Lio/reactivex/functions/Function;
      //   19: aload_1
      //   20: invokeinterface 65 2 0
      //   25: ldc 67
      //   27: invokestatic 73	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: astore_2
      //   31: aload_0
      //   32: getfield 23	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:collection	Ljava/util/Collection;
      //   35: aload_2
      //   36: invokeinterface 77 2 0
      //   41: istore_3
      //   42: iload_3
      //   43: ifeq +16 -> 59
      //   46: aload_0
      //   47: getfield 42	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   50: aload_1
      //   51: invokeinterface 79 2 0
      //   56: goto +33 -> 89
      //   59: aload_0
      //   60: getfield 83	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   63: lconst_1
      //   64: invokeinterface 89 3 0
      //   69: goto +20 -> 89
      //   72: astore_1
      //   73: aload_0
      //   74: aload_1
      //   75: invokevirtual 92	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:fail	(Ljava/lang/Throwable;)V
      //   78: return
      //   79: aload_0
      //   80: getfield 42	io/reactivex/internal/operators/flowable/FlowableDistinct$DistinctSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   83: aconst_null
      //   84: invokeinterface 79 2 0
      //   89: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	90	0	this	DistinctSubscriber
      //   0	90	1	paramT	T
      //   30	6	2	localObject	Object
      //   41	2	3	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   15	42	72	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject;
      for (;;)
      {
        localObject = this.qs.poll();
        if ((localObject == null) || (this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply(localObject), "The keySelector returned a null key")))) {
          break;
        }
        if (this.sourceMode == 2) {
          this.upstream.request(1L);
        }
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
