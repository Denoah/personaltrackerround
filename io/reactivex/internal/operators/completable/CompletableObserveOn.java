package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableObserveOn
  extends Completable
{
  final Scheduler scheduler;
  final CompletableSource source;
  
  public CompletableObserveOn(CompletableSource paramCompletableSource, Scheduler paramScheduler)
  {
    this.source = paramCompletableSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new ObserveOnCompletableObserver(paramCompletableObserver, this.scheduler));
  }
  
  static final class ObserveOnCompletableObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable, Runnable
  {
    private static final long serialVersionUID = 8571289934935992137L;
    final CompletableObserver downstream;
    Throwable error;
    final Scheduler scheduler;
    
    ObserveOnCompletableObserver(CompletableObserver paramCompletableObserver, Scheduler paramScheduler)
    {
      this.downstream = paramCompletableObserver;
      this.scheduler = paramScheduler;
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
      DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
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
      if (localThrowable != null)
      {
        this.error = null;
        this.downstream.onError(localThrowable);
      }
      else
      {
        this.downstream.onComplete();
      }
    }
  }
}
