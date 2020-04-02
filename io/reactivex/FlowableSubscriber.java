package io.reactivex;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract interface FlowableSubscriber<T>
  extends Subscriber<T>
{
  public abstract void onSubscribe(Subscription paramSubscription);
}
