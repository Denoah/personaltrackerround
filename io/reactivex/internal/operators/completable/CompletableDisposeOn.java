package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableDisposeOn
  extends Completable
{
  final Scheduler scheduler;
  final CompletableSource source;
  
  public CompletableDisposeOn(CompletableSource paramCompletableSource, Scheduler paramScheduler)
  {
    this.source = paramCompletableSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new DisposeOnObserver(paramCompletableObserver, this.scheduler));
  }
  
  static final class DisposeOnObserver
    implements CompletableObserver, Disposable, Runnable
  {
    volatile boolean disposed;
    final CompletableObserver downstream;
    final Scheduler scheduler;
    Disposable upstream;
    
    DisposeOnObserver(CompletableObserver paramCompletableObserver, Scheduler paramScheduler)
    {
      this.downstream = paramCompletableObserver;
      this.scheduler = paramScheduler;
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.scheduler.scheduleDirect(this);
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public void onComplete()
    {
      if (this.disposed) {
        return;
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.disposed)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void run()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
  }
}
