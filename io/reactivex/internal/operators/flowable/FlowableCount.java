package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCount<T>
  extends AbstractFlowableWithUpstream<T, Long>
{
  public FlowableCount(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super Long> paramSubscriber)
  {
    this.source.subscribe(new CountSubscriber(paramSubscriber));
  }
  
  static final class CountSubscriber
    extends DeferredScalarSubscription<Long>
    implements FlowableSubscriber<Object>
  {
    private static final long serialVersionUID = 4973004223787171406L;
    long count;
    Subscription upstream;
    
    CountSubscriber(Subscriber<? super Long> paramSubscriber)
    {
      super();
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      complete(Long.valueOf(this.count));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.count += 1L;
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
