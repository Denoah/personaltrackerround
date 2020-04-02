package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EmptyComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDetach<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableDetach(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new DetachSubscriber(paramSubscriber));
  }
  
  static final class DetachSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    Subscriber<? super T> downstream;
    Subscription upstream;
    
    DetachSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      Subscription localSubscription = this.upstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asSubscriber();
      localSubscription.cancel();
    }
    
    public void onComplete()
    {
      Subscriber localSubscriber = this.downstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asSubscriber();
      localSubscriber.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      Subscriber localSubscriber = this.downstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asSubscriber();
      localSubscriber.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
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
