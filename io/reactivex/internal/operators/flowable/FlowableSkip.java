package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkip<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long n;
  
  public FlowableSkip(Flowable<T> paramFlowable, long paramLong)
  {
    super(paramFlowable);
    this.n = paramLong;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SkipSubscriber(paramSubscriber, this.n));
  }
  
  static final class SkipSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final Subscriber<? super T> downstream;
    long remaining;
    Subscription upstream;
    
    SkipSubscriber(Subscriber<? super T> paramSubscriber, long paramLong)
    {
      this.downstream = paramSubscriber;
      this.remaining = paramLong;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      long l = this.remaining;
      if (l != 0L) {
        this.remaining = (l - 1L);
      } else {
        this.downstream.onNext(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        long l = this.remaining;
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(l);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
