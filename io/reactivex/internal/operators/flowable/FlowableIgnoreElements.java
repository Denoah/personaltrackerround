package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableIgnoreElements<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableIgnoreElements(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new IgnoreElementsSubscriber(paramSubscriber));
  }
  
  static final class IgnoreElementsSubscriber<T>
    implements FlowableSubscriber<T>, QueueSubscription<T>
  {
    final Subscriber<? super T> downstream;
    Subscription upstream;
    
    IgnoreElementsSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void clear() {}
    
    public boolean isEmpty()
    {
      return true;
    }
    
    public boolean offer(T paramT)
    {
      throw new UnsupportedOperationException("Should not be called!");
    }
    
    public boolean offer(T paramT1, T paramT2)
    {
      throw new UnsupportedOperationException("Should not be called!");
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT) {}
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public T poll()
    {
      return null;
    }
    
    public void request(long paramLong) {}
    
    public int requestFusion(int paramInt)
    {
      return paramInt & 0x2;
    }
  }
}
