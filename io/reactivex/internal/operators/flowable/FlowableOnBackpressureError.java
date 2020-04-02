package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureError<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableOnBackpressureError(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new BackpressureErrorSubscriber(paramSubscriber));
  }
  
  static final class BackpressureErrorSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -3176480756392482682L;
    boolean done;
    final Subscriber<? super T> downstream;
    Subscription upstream;
    
    BackpressureErrorSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
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
      if (get() != 0L)
      {
        this.downstream.onNext(paramT);
        BackpressureHelper.produced(this, 1L);
      }
      else
      {
        onError(new MissingBackpressureException("could not emit value due to lack of requests"));
      }
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
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this, paramLong);
      }
    }
  }
}
