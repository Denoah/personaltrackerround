package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DeferredScalarSubscriber<T, R>
  extends DeferredScalarSubscription<R>
  implements FlowableSubscriber<T>
{
  private static final long serialVersionUID = 2984505488220891551L;
  protected boolean hasValue;
  protected Subscription upstream;
  
  public DeferredScalarSubscriber(Subscriber<? super R> paramSubscriber)
  {
    super(paramSubscriber);
  }
  
  public void cancel()
  {
    super.cancel();
    this.upstream.cancel();
  }
  
  public void onComplete()
  {
    if (this.hasValue) {
      complete(this.value);
    } else {
      this.downstream.onComplete();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.value = null;
    this.downstream.onError(paramThrowable);
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
