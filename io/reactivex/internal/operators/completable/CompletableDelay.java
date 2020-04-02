package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableDelay
  extends Completable
{
  final long delay;
  final boolean delayError;
  final Scheduler scheduler;
  final CompletableSource source;
  final TimeUnit unit;
  
  public CompletableDelay(CompletableSource paramCompletableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    this.source = paramCompletableSource;
    this.delay = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new Delay(paramCompletableObserver, this.delay, this.unit, this.scheduler, this.delayError));
  }
  
  static final class Delay
    extends AtomicReference<Disposable>
    implements CompletableObserver, Runnable, Disposable
  {
    private static final long serialVersionUID = 465972761105851022L;
    final long delay;
    final boolean delayError;
    final CompletableObserver downstream;
    Throwable error;
    final Scheduler scheduler;
    final TimeUnit unit;
    
    Delay(CompletableObserver paramCompletableObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
    {
      this.downstream = paramCompletableObserver;
      this.delay = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.delayError = paramBoolean;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, this.delay, this.unit));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      paramThrowable = this.scheduler;
      long l;
      if (this.delayError) {
        l = this.delay;
      } else {
        l = 0L;
      }
      DisposableHelper.replace(this, paramThrowable.scheduleDirect(this, l, this.unit));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void run()
    {
      Throwable localThrowable = this.error;
      this.error = null;
      if (localThrowable != null) {
        this.downstream.onError(localThrowable);
      } else {
        this.downstream.onComplete();
      }
    }
  }
}
