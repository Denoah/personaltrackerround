package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableIntervalRange
  extends Flowable<Long>
{
  final long end;
  final long initialDelay;
  final long period;
  final Scheduler scheduler;
  final long start;
  final TimeUnit unit;
  
  public FlowableIntervalRange(long paramLong1, long paramLong2, long paramLong3, long paramLong4, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.initialDelay = paramLong3;
    this.period = paramLong4;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.start = paramLong1;
    this.end = paramLong2;
  }
  
  public void subscribeActual(Subscriber<? super Long> paramSubscriber)
  {
    IntervalRangeSubscriber localIntervalRangeSubscriber = new IntervalRangeSubscriber(paramSubscriber, this.start, this.end);
    paramSubscriber.onSubscribe(localIntervalRangeSubscriber);
    paramSubscriber = this.scheduler;
    if ((paramSubscriber instanceof TrampolineScheduler))
    {
      paramSubscriber = paramSubscriber.createWorker();
      localIntervalRangeSubscriber.setResource(paramSubscriber);
      paramSubscriber.schedulePeriodically(localIntervalRangeSubscriber, this.initialDelay, this.period, this.unit);
    }
    else
    {
      localIntervalRangeSubscriber.setResource(paramSubscriber.schedulePeriodicallyDirect(localIntervalRangeSubscriber, this.initialDelay, this.period, this.unit));
    }
  }
  
  static final class IntervalRangeSubscriber
    extends AtomicLong
    implements Subscription, Runnable
  {
    private static final long serialVersionUID = -2809475196591179431L;
    long count;
    final Subscriber<? super Long> downstream;
    final long end;
    final AtomicReference<Disposable> resource = new AtomicReference();
    
    IntervalRangeSubscriber(Subscriber<? super Long> paramSubscriber, long paramLong1, long paramLong2)
    {
      this.downstream = paramSubscriber;
      this.count = paramLong1;
      this.end = paramLong2;
    }
    
    public void cancel()
    {
      DisposableHelper.dispose(this.resource);
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this, paramLong);
      }
    }
    
    public void run()
    {
      if (this.resource.get() != DisposableHelper.DISPOSED)
      {
        long l1 = get();
        if (l1 != 0L)
        {
          long l2 = this.count;
          this.downstream.onNext(Long.valueOf(l2));
          if (l2 == this.end)
          {
            if (this.resource.get() != DisposableHelper.DISPOSED) {
              this.downstream.onComplete();
            }
            DisposableHelper.dispose(this.resource);
            return;
          }
          this.count = (l2 + 1L);
          if (l1 != Long.MAX_VALUE) {
            decrementAndGet();
          }
        }
        else
        {
          Subscriber localSubscriber = this.downstream;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Can't deliver value ");
          localStringBuilder.append(this.count);
          localStringBuilder.append(" due to lack of requests");
          localSubscriber.onError(new MissingBackpressureException(localStringBuilder.toString()));
          DisposableHelper.dispose(this.resource);
        }
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.resource, paramDisposable);
    }
  }
}
