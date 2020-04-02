package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableTakeUntilCompletable
  extends Completable
{
  final CompletableSource other;
  final Completable source;
  
  public CompletableTakeUntilCompletable(Completable paramCompletable, CompletableSource paramCompletableSource)
  {
    this.source = paramCompletable;
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    TakeUntilMainObserver localTakeUntilMainObserver = new TakeUntilMainObserver(paramCompletableObserver);
    paramCompletableObserver.onSubscribe(localTakeUntilMainObserver);
    this.other.subscribe(localTakeUntilMainObserver.other);
    this.source.subscribe(localTakeUntilMainObserver);
  }
  
  static final class TakeUntilMainObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable
  {
    private static final long serialVersionUID = 3533011714830024923L;
    final CompletableObserver downstream;
    final AtomicBoolean once;
    final OtherObserver other;
    
    TakeUntilMainObserver(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
      this.other = new OtherObserver(this);
      this.once = new AtomicBoolean();
    }
    
    public void dispose()
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this);
        DisposableHelper.dispose(this.other);
      }
    }
    
    void innerComplete()
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this);
        this.downstream.onComplete();
      }
    }
    
    void innerError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public boolean isDisposed()
    {
      return this.once.get();
    }
    
    public void onComplete()
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this.other);
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this.other);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    static final class OtherObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver
    {
      private static final long serialVersionUID = 5176264485428790318L;
      final CompletableTakeUntilCompletable.TakeUntilMainObserver parent;
      
      OtherObserver(CompletableTakeUntilCompletable.TakeUntilMainObserver paramTakeUntilMainObserver)
      {
        this.parent = paramTakeUntilMainObserver;
      }
      
      public void onComplete()
      {
        this.parent.innerComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.innerError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
