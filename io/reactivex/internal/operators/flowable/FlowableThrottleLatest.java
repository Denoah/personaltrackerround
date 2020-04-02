package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableThrottleLatest<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean emitLast;
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public FlowableThrottleLatest(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramFlowable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.emitLast = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ThrottleLatestSubscriber(paramSubscriber, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast));
  }
  
  static final class ThrottleLatestSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = -8296689127439125014L;
    volatile boolean cancelled;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    final boolean emitLast;
    long emitted;
    Throwable error;
    final AtomicReference<T> latest;
    final AtomicLong requested;
    final long timeout;
    volatile boolean timerFired;
    boolean timerRunning;
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker worker;
    
    ThrottleLatestSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.emitLast = paramBoolean;
      this.latest = new AtomicReference();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      this.worker.dispose();
      if (getAndIncrement() == 0) {
        this.latest.lazySet(null);
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Object localObject1 = this.latest;
      AtomicLong localAtomicLong = this.requested;
      Subscriber localSubscriber = this.downstream;
      int i = 1;
      for (;;)
      {
        if (this.cancelled)
        {
          ((AtomicReference)localObject1).lazySet(null);
          return;
        }
        boolean bool = this.done;
        if ((bool) && (this.error != null))
        {
          ((AtomicReference)localObject1).lazySet(null);
          localSubscriber.onError(this.error);
          this.worker.dispose();
          return;
        }
        if (((AtomicReference)localObject1).get() == null) {
          j = 1;
        } else {
          j = 0;
        }
        long l;
        if (bool)
        {
          if ((j == 0) && (this.emitLast))
          {
            localObject1 = ((AtomicReference)localObject1).getAndSet(null);
            l = this.emitted;
            if (l != localAtomicLong.get())
            {
              this.emitted = (l + 1L);
              localSubscriber.onNext(localObject1);
              localSubscriber.onComplete();
            }
            else
            {
              localSubscriber.onError(new MissingBackpressureException("Could not emit final value due to lack of requests"));
            }
          }
          else
          {
            ((AtomicReference)localObject1).lazySet(null);
            localSubscriber.onComplete();
          }
          this.worker.dispose();
          return;
        }
        if (j != 0)
        {
          if (this.timerFired)
          {
            this.timerRunning = false;
            this.timerFired = false;
          }
        }
        else {
          if ((!this.timerRunning) || (this.timerFired)) {
            break label257;
          }
        }
        int j = addAndGet(-i);
        i = j;
        if (j == 0)
        {
          return;
          label257:
          Object localObject2 = ((AtomicReference)localObject1).getAndSet(null);
          l = this.emitted;
          if (l == localAtomicLong.get()) {
            break;
          }
          localSubscriber.onNext(localObject2);
          this.emitted = (l + 1L);
          this.timerFired = false;
          this.timerRunning = true;
          this.worker.schedule(this, this.timeout, this.unit);
        }
      }
      this.upstream.cancel();
      localSubscriber.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
      this.worker.dispose();
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      this.latest.set(paramT);
      drain();
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
        BackpressureHelper.add(this.requested, paramLong);
      }
    }
    
    public void run()
    {
      this.timerFired = true;
      drain();
    }
  }
}
