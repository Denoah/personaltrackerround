package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSampleTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean emitLast;
  final long period;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public FlowableSampleTimed(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    super(paramFlowable);
    this.period = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.emitLast = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    paramSubscriber = new SerializedSubscriber(paramSubscriber);
    if (this.emitLast) {
      this.source.subscribe(new SampleTimedEmitLast(paramSubscriber, this.period, this.unit, this.scheduler));
    } else {
      this.source.subscribe(new SampleTimedNoLast(paramSubscriber, this.period, this.unit, this.scheduler));
    }
  }
  
  static final class SampleTimedEmitLast<T>
    extends FlowableSampleTimed.SampleTimedSubscriber<T>
  {
    private static final long serialVersionUID = -7139995637533111443L;
    final AtomicInteger wip = new AtomicInteger(1);
    
    SampleTimedEmitLast(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(paramLong, paramTimeUnit, paramScheduler);
    }
    
    void complete()
    {
      emit();
      if (this.wip.decrementAndGet() == 0) {
        this.downstream.onComplete();
      }
    }
    
    public void run()
    {
      if (this.wip.incrementAndGet() == 2)
      {
        emit();
        if (this.wip.decrementAndGet() == 0) {
          this.downstream.onComplete();
        }
      }
    }
  }
  
  static final class SampleTimedNoLast<T>
    extends FlowableSampleTimed.SampleTimedSubscriber<T>
  {
    private static final long serialVersionUID = -7139995637533111443L;
    
    SampleTimedNoLast(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(paramLong, paramTimeUnit, paramScheduler);
    }
    
    void complete()
    {
      this.downstream.onComplete();
    }
    
    public void run()
    {
      emit();
    }
  }
  
  static abstract class SampleTimedSubscriber<T>
    extends AtomicReference<T>
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    private static final long serialVersionUID = -3517602651313910099L;
    final Subscriber<? super T> downstream;
    final long period;
    final AtomicLong requested = new AtomicLong();
    final Scheduler scheduler;
    final SequentialDisposable timer = new SequentialDisposable();
    final TimeUnit unit;
    Subscription upstream;
    
    SampleTimedSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.downstream = paramSubscriber;
      this.period = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    public void cancel()
    {
      cancelTimer();
      this.upstream.cancel();
    }
    
    void cancelTimer()
    {
      DisposableHelper.dispose(this.timer);
    }
    
    abstract void complete();
    
    void emit()
    {
      Object localObject = getAndSet(null);
      if (localObject != null) {
        if (this.requested.get() != 0L)
        {
          this.downstream.onNext(localObject);
          BackpressureHelper.produced(this.requested, 1L);
        }
        else
        {
          cancel();
          this.downstream.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
      }
    }
    
    public void onComplete()
    {
      cancelTimer();
      complete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      cancelTimer();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      lazySet(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        SequentialDisposable localSequentialDisposable = this.timer;
        Scheduler localScheduler = this.scheduler;
        long l = this.period;
        localSequentialDisposable.replace(localScheduler.schedulePeriodicallyDirect(this, l, l, this.unit));
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this.requested, paramLong);
      }
    }
  }
}
