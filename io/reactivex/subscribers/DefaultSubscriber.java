package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

public abstract class DefaultSubscriber<T>
  implements FlowableSubscriber<T>
{
  Subscription upstream;
  
  public DefaultSubscriber() {}
  
  protected final void cancel()
  {
    Subscription localSubscription = this.upstream;
    this.upstream = SubscriptionHelper.CANCELLED;
    localSubscription.cancel();
  }
  
  protected void onStart()
  {
    request(Long.MAX_VALUE);
  }
  
  public final void onSubscribe(Subscription paramSubscription)
  {
    if (EndConsumerHelper.validate(this.upstream, paramSubscription, getClass()))
    {
      this.upstream = paramSubscription;
      onStart();
    }
  }
  
  protected final void request(long paramLong)
  {
    Subscription localSubscription = this.upstream;
    if (localSubscription != null) {
      localSubscription.request(paramLong);
    }
  }
}
