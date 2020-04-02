package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableTimer
  extends Completable
{
  final long delay;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public CompletableTimer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    TimerDisposable localTimerDisposable = new TimerDisposable(paramCompletableObserver);
    paramCompletableObserver.onSubscribe(localTimerDisposable);
    localTimerDisposable.setFuture(this.scheduler.scheduleDirect(localTimerDisposable, this.delay, this.unit));
  }
  
  static final class TimerDisposable
    extends AtomicReference<Disposable>
    implements Disposable, Runnable
  {
    private static final long serialVersionUID = 3167244060586201109L;
    final CompletableObserver downstream;
    
    TimerDisposable(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void run()
    {
      this.downstream.onComplete();
    }
    
    void setFuture(Disposable paramDisposable)
    {
      DisposableHelper.replace(this, paramDisposable);
    }
  }
}
