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

public final class FlowableInterval
  extends Flowable<Long>
{
  final long initialDelay;
  final long period;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public FlowableInterval(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.initialDelay = paramLong1;
    this.period = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Subscriber<? super Long> paramSubscriber)
  {
    IntervalSubscriber localIntervalSubscriber = new IntervalSubscriber(paramSubscriber);
    paramSubscriber.onSubscribe(localIntervalSubscriber);
    paramSubscriber = this.scheduler;
    if ((paramSubscriber instanceof TrampolineScheduler))
    {
      paramSubscriber = paramSubscriber.createWorker();
      localIntervalSubscriber.setResource(paramSubscriber);
      paramSubscriber.schedulePeriodically(localIntervalSubscriber, this.initialDelay, this.period, this.unit);
    }
    else
    {
      localIntervalSubscriber.setResource(paramSubscriber.schedulePeriodicallyDirect(localIntervalSubscriber, this.initialDelay, this.period, this.unit));
    }
  }
  
  static final class IntervalSubscriber
    extends AtomicLong
    implements Subscription, Runnable
  {
    private static final long serialVersionUID = -2809475196591179431L;
    long count;
    final Subscriber<? super Long> downstream;
    final AtomicReference<Disposable> resource = new AtomicReference();
    
    IntervalSubscriber(Subscriber<? super Long> paramSubscriber)
    {
      this.downstream = paramSubscriber;
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
        Object localObject;
        if (get() != 0L)
        {
          localObject = this.downstream;
          long l = this.count;
          this.count = (l + 1L);
          ((Subscriber)localObject).onNext(Long.valueOf(l));
          BackpressureHelper.produced(this, 1L);
        }
        else
        {
          Subscriber localSubscriber = this.downstream;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Can't deliver value ");
          ((StringBuilder)localObject).append(this.count);
          ((StringBuilder)localObject).append(" due to lack of requests");
          localSubscriber.onError(new MissingBackpressureException(((StringBuilder)localObject).toString()));
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
