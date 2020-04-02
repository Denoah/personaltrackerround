package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScan<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final BiFunction<T, T, T> accumulator;
  
  public FlowableScan(Flowable<T> paramFlowable, BiFunction<T, T, T> paramBiFunction)
  {
    super(paramFlowable);
    this.accumulator = paramBiFunction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ScanSubscriber(paramSubscriber, this.accumulator));
  }
  
  static final class ScanSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final BiFunction<T, T, T> accumulator;
    boolean done;
    final Subscriber<? super T> downstream;
    Subscription upstream;
    T value;
    
    ScanSubscriber(Subscriber<? super T> paramSubscriber, BiFunction<T, T, T> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.accumulator = paramBiFunction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
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
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      Object localObject = this.value;
      if (localObject == null)
      {
        this.value = paramT;
        localSubscriber.onNext(paramT);
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.accumulator.apply(localObject, paramT), "The value returned by the accumulator is null");
        this.value = paramT;
        localSubscriber.onNext(paramT);
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
      this.upstream.request(paramLong);
    }
  }
}
