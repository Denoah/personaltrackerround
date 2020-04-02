package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeInterval<T>
  extends AbstractFlowableWithUpstream<T, Timed<T>>
{
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public FlowableTimeInterval(Flowable<T> paramFlowable, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    super(paramFlowable);
    this.scheduler = paramScheduler;
    this.unit = paramTimeUnit;
  }
  
  protected void subscribeActual(Subscriber<? super Timed<T>> paramSubscriber)
  {
    this.source.subscribe(new TimeIntervalSubscriber(paramSubscriber, this.unit, this.scheduler));
  }
  
  static final class TimeIntervalSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final Subscriber<? super Timed<T>> downstream;
    long lastTime;
    final Scheduler scheduler;
    final TimeUnit unit;
    Subscription upstream;
    
    TimeIntervalSubscriber(Subscriber<? super Timed<T>> paramSubscriber, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.downstream = paramSubscriber;
      this.scheduler = paramScheduler;
      this.unit = paramTimeUnit;
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
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.lastTime;
      this.lastTime = l1;
      this.downstream.onNext(new Timed(paramT, l1 - l2, this.unit));
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.lastTime = this.scheduler.now(this.unit);
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
