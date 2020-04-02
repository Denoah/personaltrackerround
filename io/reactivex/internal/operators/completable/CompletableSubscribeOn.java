package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableSubscribeOn
  extends Completable
{
  final Scheduler scheduler;
  final CompletableSource source;
  
  public CompletableSubscribeOn(CompletableSource paramCompletableSource, Scheduler paramScheduler)
  {
    this.source = paramCompletableSource;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    SubscribeOnObserver localSubscribeOnObserver = new SubscribeOnObserver(paramCompletableObserver, this.source);
    paramCompletableObserver.onSubscribe(localSubscribeOnObserver);
    paramCompletableObserver = this.scheduler.scheduleDirect(localSubscribeOnObserver);
    localSubscribeOnObserver.task.replace(paramCompletableObserver);
  }
  
  static final class SubscribeOnObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable, Runnable
  {
    private static final long serialVersionUID = 7000911171163930287L;
    final CompletableObserver downstream;
    final CompletableSource source;
    final SequentialDisposable task;
    
    SubscribeOnObserver(CompletableObserver paramCompletableObserver, CompletableSource paramCompletableSource)
    {
      this.downstream = paramCompletableObserver;
      this.source = paramCompletableSource;
      this.task = new SequentialDisposable();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
      this.task.dispose();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    public void run()
    {
      this.source.subscribe(this);
    }
  }
}
