package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimer
  extends Observable<Long>
{
  final long delay;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public ObservableTimer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void subscribeActual(Observer<? super Long> paramObserver)
  {
    TimerObserver localTimerObserver = new TimerObserver(paramObserver);
    paramObserver.onSubscribe(localTimerObserver);
    localTimerObserver.setResource(this.scheduler.scheduleDirect(localTimerObserver, this.delay, this.unit));
  }
  
  static final class TimerObserver
    extends AtomicReference<Disposable>
    implements Disposable, Runnable
  {
    private static final long serialVersionUID = -2809475196591179431L;
    final Observer<? super Long> downstream;
    
    TimerObserver(Observer<? super Long> paramObserver)
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
      if (!isDisposed())
      {
        this.downstream.onNext(Long.valueOf(0L));
        lazySet(EmptyDisposable.INSTANCE);
        this.downstream.onComplete();
      }
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.trySet(this, paramDisposable);
    }
  }
}
