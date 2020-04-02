package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableUnsubscribeOn<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Scheduler scheduler;
  
  public FlowableUnsubscribeOn(Flowable<T> paramFlowable, Scheduler paramScheduler)
  {
    super(paramFlowable);
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new UnsubscribeSubscriber(paramSubscriber, this.scheduler));
  }
  
  static final class UnsubscribeSubscriber<T>
    extends AtomicBoolean
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 1015244841293359600L;
    final Subscriber<? super T> downstream;
    final Scheduler scheduler;
    Subscription upstream;
    
    UnsubscribeSubscriber(Subscriber<? super T> paramSubscriber, Scheduler paramScheduler)
    {
      this.downstream = paramSubscriber;
      this.scheduler = paramScheduler;
    }
    
    public void cancel()
    {
      if (compareAndSet(false, true)) {
        this.scheduler.scheduleDirect(new Cancellation());
      }
    }
    
    public void onComplete()
    {
      if (!get()) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get())
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (!get()) {
        this.downstream.onNext(paramT);
      }
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
    
    final class Cancellation
      implements Runnable
    {
      Cancellation() {}
      
      public void run()
      {
        FlowableUnsubscribeOn.UnsubscribeSubscriber.this.upstream.cancel();
      }
    }
  }
}
