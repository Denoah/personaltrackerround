package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceSeedSingle<T, R>
  extends Single<R>
{
  final BiFunction<R, ? super T, R> reducer;
  final R seed;
  final Publisher<T> source;
  
  public FlowableReduceSeedSingle(Publisher<T> paramPublisher, R paramR, BiFunction<R, ? super T, R> paramBiFunction)
  {
    this.source = paramPublisher;
    this.seed = paramR;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    this.source.subscribe(new ReduceSeedObserver(paramSingleObserver, this.reducer, this.seed));
  }
  
  static final class ReduceSeedObserver<T, R>
    implements FlowableSubscriber<T>, Disposable
  {
    final SingleObserver<? super R> downstream;
    final BiFunction<R, ? super T, R> reducer;
    Subscription upstream;
    R value;
    
    ReduceSeedObserver(SingleObserver<? super R> paramSingleObserver, BiFunction<R, ? super T, R> paramBiFunction, R paramR)
    {
      this.downstream = paramSingleObserver;
      this.value = paramR;
      this.reducer = paramBiFunction;
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      this.upstream = SubscriptionHelper.CANCELLED;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.upstream == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      Object localObject = this.value;
      if (localObject != null)
      {
        this.value = null;
        this.upstream = SubscriptionHelper.CANCELLED;
        this.downstream.onSuccess(localObject);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.value != null)
      {
        this.value = null;
        this.upstream = SubscriptionHelper.CANCELLED;
        this.downstream.onError(paramThrowable);
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
      //   1: getfield 32	io/reactivex/internal/operators/flowable/FlowableReduceSeedSingle$ReduceSeedObserver:value	Ljava/lang/Object;
      //   4: astore_2
      //   5: aload_2
      //   6: ifnull +45 -> 51
      //   9: aload_0
      //   10: aload_0
      //   11: getfield 34	io/reactivex/internal/operators/flowable/FlowableReduceSeedSingle$ReduceSeedObserver:reducer	Lio/reactivex/functions/BiFunction;
      //   14: aload_2
      //   15: aload_1
      //   16: invokeinterface 74 3 0
      //   21: ldc 76
      //   23: invokestatic 82	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   26: putfield 32	io/reactivex/internal/operators/flowable/FlowableReduceSeedSingle$ReduceSeedObserver:value	Ljava/lang/Object;
      //   29: goto +22 -> 51
      //   32: astore_1
      //   33: aload_1
      //   34: invokestatic 87	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 40	io/reactivex/internal/operators/flowable/FlowableReduceSeedSingle$ReduceSeedObserver:upstream	Lorg/reactivestreams/Subscription;
      //   41: invokeinterface 45 1 0
      //   46: aload_0
      //   47: aload_1
      //   48: invokevirtual 88	io/reactivex/internal/operators/flowable/FlowableReduceSeedSingle$ReduceSeedObserver:onError	(Ljava/lang/Throwable;)V
      //   51: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	52	0	this	ReduceSeedObserver
      //   0	52	1	paramT	T
      //   4	11	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   9	29	32	finally
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
