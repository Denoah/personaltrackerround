package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableThrottleFirstTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public FlowableThrottleFirstTimed(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    super(paramFlowable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new DebounceTimedSubscriber(new SerializedSubscriber(paramSubscriber), this.timeout, this.unit, this.scheduler.createWorker()));
  }
  
  static final class DebounceTimedSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = -9102637559663639004L;
    boolean done;
    final Subscriber<? super T> downstream;
    volatile boolean gate;
    final long timeout;
    final SequentialDisposable timer = new SequentialDisposable();
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker worker;
    
    DebounceTimedSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      this.downstream = paramSubscriber;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      this.worker.dispose();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
      this.worker.dispose();
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
      this.worker.dispose();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (!this.gate)
      {
        this.gate = true;
        if (get() != 0L)
        {
          this.downstream.onNext(paramT);
          BackpressureHelper.produced(this, 1L);
          paramT = (Disposable)this.timer.get();
          if (paramT != null) {
            paramT.dispose();
          }
          this.timer.replace(this.worker.schedule(this, this.timeout, this.unit));
        }
        else
        {
          this.done = true;
          cancel();
          this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
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
    
    public void run()
    {
      this.gate = false;
    }
  }
}
