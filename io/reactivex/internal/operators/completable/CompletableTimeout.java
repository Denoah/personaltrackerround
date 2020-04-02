package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CompletableTimeout
  extends Completable
{
  final CompletableSource other;
  final Scheduler scheduler;
  final CompletableSource source;
  final long timeout;
  final TimeUnit unit;
  
  public CompletableTimeout(CompletableSource paramCompletableSource1, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, CompletableSource paramCompletableSource2)
  {
    this.source = paramCompletableSource1;
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.other = paramCompletableSource2;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    CompositeDisposable localCompositeDisposable = new CompositeDisposable();
    paramCompletableObserver.onSubscribe(localCompositeDisposable);
    AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    localCompositeDisposable.add(this.scheduler.scheduleDirect(new DisposeTask(localAtomicBoolean, localCompositeDisposable, paramCompletableObserver), this.timeout, this.unit));
    this.source.subscribe(new TimeOutObserver(localCompositeDisposable, localAtomicBoolean, paramCompletableObserver));
  }
  
  final class DisposeTask
    implements Runnable
  {
    final CompletableObserver downstream;
    private final AtomicBoolean once;
    final CompositeDisposable set;
    
    DisposeTask(AtomicBoolean paramAtomicBoolean, CompositeDisposable paramCompositeDisposable, CompletableObserver paramCompletableObserver)
    {
      this.once = paramAtomicBoolean;
      this.set = paramCompositeDisposable;
      this.downstream = paramCompletableObserver;
    }
    
    public void run()
    {
      if (this.once.compareAndSet(false, true))
      {
        this.set.clear();
        if (CompletableTimeout.this.other == null) {
          this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(CompletableTimeout.this.timeout, CompletableTimeout.this.unit)));
        } else {
          CompletableTimeout.this.other.subscribe(new DisposeObserver());
        }
      }
    }
    
    final class DisposeObserver
      implements CompletableObserver
    {
      DisposeObserver() {}
      
      public void onComplete()
      {
        CompletableTimeout.DisposeTask.this.set.dispose();
        CompletableTimeout.DisposeTask.this.downstream.onComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        CompletableTimeout.DisposeTask.this.set.dispose();
        CompletableTimeout.DisposeTask.this.downstream.onError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        CompletableTimeout.DisposeTask.this.set.add(paramDisposable);
      }
    }
  }
  
  static final class TimeOutObserver
    implements CompletableObserver
  {
    private final CompletableObserver downstream;
    private final AtomicBoolean once;
    private final CompositeDisposable set;
    
    TimeOutObserver(CompositeDisposable paramCompositeDisposable, AtomicBoolean paramAtomicBoolean, CompletableObserver paramCompletableObserver)
    {
      this.set = paramCompositeDisposable;
      this.once = paramAtomicBoolean;
      this.downstream = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      if (this.once.compareAndSet(false, true))
      {
        this.set.dispose();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        this.set.dispose();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.set.add(paramDisposable);
    }
  }
}
