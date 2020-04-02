package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimer
  extends Flowable<Long>
{
  final long delay;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public FlowableTimer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Subscriber<? super Long> paramSubscriber)
  {
    TimerSubscriber localTimerSubscriber = new TimerSubscriber(paramSubscriber);
    paramSubscriber.onSubscribe(localTimerSubscriber);
    localTimerSubscriber.setResource(this.scheduler.scheduleDirect(localTimerSubscriber, this.delay, this.unit));
  }
  
  static final class TimerSubscriber
    extends AtomicReference<Disposable>
    implements Subscription, Runnable
  {
    private static final long serialVersionUID = -2809475196591179431L;
    final Subscriber<? super Long> downstream;
    volatile boolean requested;
    
    TimerSubscriber(Subscriber<? super Long> paramSubscriber)
    {
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      DisposableHelper.dispose(this);
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        this.requested = true;
      }
    }
    
    public void run()
    {
      if (get() != DisposableHelper.DISPOSED) {
        if (this.requested)
        {
          this.downstream.onNext(Long.valueOf(0L));
          lazySet(EmptyDisposable.INSTANCE);
          this.downstream.onComplete();
        }
        else
        {
          lazySet(EmptyDisposable.INSTANCE);
          this.downstream.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
        }
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.trySet(this, paramDisposable);
    }
  }
}
