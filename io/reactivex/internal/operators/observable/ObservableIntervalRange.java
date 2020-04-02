package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableIntervalRange
  extends Observable<Long>
{
  final long end;
  final long initialDelay;
  final long period;
  final Scheduler scheduler;
  final long start;
  final TimeUnit unit;
  
  public ObservableIntervalRange(long paramLong1, long paramLong2, long paramLong3, long paramLong4, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.initialDelay = paramLong3;
    this.period = paramLong4;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.start = paramLong1;
    this.end = paramLong2;
  }
  
  public void subscribeActual(Observer<? super Long> paramObserver)
  {
    IntervalRangeObserver localIntervalRangeObserver = new IntervalRangeObserver(paramObserver, this.start, this.end);
    paramObserver.onSubscribe(localIntervalRangeObserver);
    paramObserver = this.scheduler;
    if ((paramObserver instanceof TrampolineScheduler))
    {
      paramObserver = paramObserver.createWorker();
      localIntervalRangeObserver.setResource(paramObserver);
      paramObserver.schedulePeriodically(localIntervalRangeObserver, this.initialDelay, this.period, this.unit);
    }
    else
    {
      localIntervalRangeObserver.setResource(paramObserver.schedulePeriodicallyDirect(localIntervalRangeObserver, this.initialDelay, this.period, this.unit));
    }
  }
  
  static final class IntervalRangeObserver
    extends AtomicReference<Disposable>
    implements Disposable, Runnable
  {
    private static final long serialVersionUID = 1891866368734007884L;
    long count;
    final Observer<? super Long> downstream;
    final long end;
    
    IntervalRangeObserver(Observer<? super Long> paramObserver, long paramLong1, long paramLong2)
    {
      this.downstream = paramObserver;
      this.count = paramLong1;
      this.end = paramLong2;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void run()
    {
      if (!isDisposed())
      {
        long l = this.count;
        this.downstream.onNext(Long.valueOf(l));
        if (l == this.end)
        {
          DisposableHelper.dispose(this);
          this.downstream.onComplete();
          return;
        }
        this.count = (l + 1L);
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
