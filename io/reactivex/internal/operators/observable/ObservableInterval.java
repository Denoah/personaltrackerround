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

public final class ObservableInterval
  extends Observable<Long>
{
  final long initialDelay;
  final long period;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public ObservableInterval(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.initialDelay = paramLong1;
    this.period = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Observer<? super Long> paramObserver)
  {
    IntervalObserver localIntervalObserver = new IntervalObserver(paramObserver);
    paramObserver.onSubscribe(localIntervalObserver);
    paramObserver = this.scheduler;
    if ((paramObserver instanceof TrampolineScheduler))
    {
      paramObserver = paramObserver.createWorker();
      localIntervalObserver.setResource(paramObserver);
      paramObserver.schedulePeriodically(localIntervalObserver, this.initialDelay, this.period, this.unit);
    }
    else
    {
      localIntervalObserver.setResource(paramObserver.schedulePeriodicallyDirect(localIntervalObserver, this.initialDelay, this.period, this.unit));
    }
  }
  
  static final class IntervalObserver
    extends AtomicReference<Disposable>
    implements Disposable, Runnable
  {
    private static final long serialVersionUID = 346773832286157679L;
    long count;
    final Observer<? super Long> downstream;
    
    IntervalObserver(Observer<? super Long> paramObserver)
    {
      this.downstream = paramObserver;
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
      if (get() != DisposableHelper.DISPOSED)
      {
        Observer localObserver = this.downstream;
        long l = this.count;
        this.count = (1L + l);
        localObserver.onNext(Long.valueOf(l));
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
