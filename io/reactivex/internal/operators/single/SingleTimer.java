package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleTimer
  extends Single<Long>
{
  final long delay;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public SingleTimer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(SingleObserver<? super Long> paramSingleObserver)
  {
    TimerDisposable localTimerDisposable = new TimerDisposable(paramSingleObserver);
    paramSingleObserver.onSubscribe(localTimerDisposable);
    localTimerDisposable.setFuture(this.scheduler.scheduleDirect(localTimerDisposable, this.delay, this.unit));
  }
  
  static final class TimerDisposable
    extends AtomicReference<Disposable>
    implements Disposable, Runnable
  {
    private static final long serialVersionUID = 8465401857522493082L;
    final SingleObserver<? super Long> downstream;
    
    TimerDisposable(SingleObserver<? super Long> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
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
      this.downstream.onSuccess(Long.valueOf(0L));
    }
    
    void setFuture(Disposable paramDisposable)
    {
      DisposableHelper.replace(this, paramDisposable);
    }
  }
}
