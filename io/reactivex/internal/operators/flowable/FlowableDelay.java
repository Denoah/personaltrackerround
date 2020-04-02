package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelay<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long delay;
  final boolean delayError;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public FlowableDelay(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramFlowable);
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if (!this.delayError) {
      paramSubscriber = new SerializedSubscriber(paramSubscriber);
    }
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    this.source.subscribe(new DelaySubscriber(paramSubscriber, this.delay, this.unit, localWorker, this.delayError));
  }
  
  static final class DelaySubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final long delay;
    final boolean delayError;
    final Subscriber<? super T> downstream;
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker w;
    
    DelaySubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.delay = paramLong;
      this.unit = paramTimeUnit;
      this.w = paramWorker;
      this.delayError = paramBoolean;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      this.w.dispose();
    }
    
    public void onComplete()
    {
      this.w.schedule(new OnComplete(), this.delay, this.unit);
    }
    
    public void onError(Throwable paramThrowable)
    {
      Scheduler.Worker localWorker = this.w;
      paramThrowable = new OnError(paramThrowable);
      long l;
      if (this.delayError) {
        l = this.delay;
      } else {
        l = 0L;
      }
      localWorker.schedule(paramThrowable, l, this.unit);
    }
    
    public void onNext(T paramT)
    {
      this.w.schedule(new OnNext(paramT), this.delay, this.unit);
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
    
    final class OnComplete
      implements Runnable
    {
      OnComplete() {}
      
      public void run()
      {
        try
        {
          FlowableDelay.DelaySubscriber.this.downstream.onComplete();
          return;
        }
        finally
        {
          FlowableDelay.DelaySubscriber.this.w.dispose();
        }
      }
    }
    
    final class OnError
      implements Runnable
    {
      private final Throwable t;
      
      OnError(Throwable paramThrowable)
      {
        this.t = paramThrowable;
      }
      
      public void run()
      {
        try
        {
          FlowableDelay.DelaySubscriber.this.downstream.onError(this.t);
          return;
        }
        finally
        {
          FlowableDelay.DelaySubscriber.this.w.dispose();
        }
      }
    }
    
    final class OnNext
      implements Runnable
    {
      private final T t;
      
      OnNext()
      {
        Object localObject;
        this.t = localObject;
      }
      
      public void run()
      {
        FlowableDelay.DelaySubscriber.this.downstream.onNext(this.t);
      }
    }
  }
}
