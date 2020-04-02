package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceMaybe<T>
  extends Maybe<T>
  implements HasUpstreamPublisher<T>, FuseToFlowable<T>
{
  final BiFunction<T, T, T> reducer;
  final Flowable<T> source;
  
  public FlowableReduceMaybe(Flowable<T> paramFlowable, BiFunction<T, T, T> paramBiFunction)
  {
    this.source = paramFlowable;
    this.reducer = paramBiFunction;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableReduce(this.source, this.reducer));
  }
  
  public Publisher<T> source()
  {
    return this.source;
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new ReduceSubscriber(paramMaybeObserver, this.reducer));
  }
  
  static final class ReduceSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    boolean done;
    final MaybeObserver<? super T> downstream;
    final BiFunction<T, T, T> reducer;
    Subscription upstream;
    T value;
    
    ReduceSubscriber(MaybeObserver<? super T> paramMaybeObserver, BiFunction<T, T, T> paramBiFunction)
    {
      this.downstream = paramMaybeObserver;
      this.reducer = paramBiFunction;
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      this.done = true;
    }
    
    public boolean isDisposed()
    {
      return this.done;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject = this.value;
      if (localObject != null) {
        this.downstream.onSuccess(localObject);
      } else {
        this.downstream.onComplete();
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
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 47	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 52	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:value	Ljava/lang/Object;
      //   12: astore_2
      //   13: aload_2
      //   14: ifnonnull +11 -> 25
      //   17: aload_0
      //   18: aload_1
      //   19: putfield 52	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:value	Ljava/lang/Object;
      //   22: goto +45 -> 67
      //   25: aload_0
      //   26: aload_0
      //   27: getfield 34	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:reducer	Lio/reactivex/functions/BiFunction;
      //   30: aload_2
      //   31: aload_1
      //   32: invokeinterface 74 3 0
      //   37: ldc 76
      //   39: invokestatic 82	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   42: putfield 52	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:value	Ljava/lang/Object;
      //   45: goto +22 -> 67
      //   48: astore_1
      //   49: aload_1
      //   50: invokestatic 87	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   53: aload_0
      //   54: getfield 40	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   57: invokeinterface 45 1 0
      //   62: aload_0
      //   63: aload_1
      //   64: invokevirtual 88	io/reactivex/internal/operators/flowable/FlowableReduceMaybe$ReduceSubscriber:onError	(Ljava/lang/Throwable;)V
      //   67: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	68	0	this	ReduceSubscriber
      //   0	68	1	paramT	T
      //   12	19	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   25	45	48	finally
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
