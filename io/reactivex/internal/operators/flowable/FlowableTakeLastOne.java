package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeLastOne<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableTakeLastOne(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new TakeLastOneSubscriber(paramSubscriber));
  }
  
  static final class TakeLastOneSubscriber<T>
    extends DeferredScalarSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -5467847744262967226L;
    Subscription upstream;
    
    TakeLastOneSubscriber(Subscriber<? super T> paramSubscriber)
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
      Object localObject = this.value;
      if (localObject != null) {
        complete(localObject);
      } else {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.value = paramT;
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
