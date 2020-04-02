package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class CompletableHide
  extends Completable
{
  final CompletableSource source;
  
  public CompletableHide(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new HideCompletableObserver(paramCompletableObserver));
  }
  
  static final class HideCompletableObserver
    implements CompletableObserver, Disposable
  {
    final CompletableObserver downstream;
    Disposable upstream;
    
    HideCompletableObserver(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
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
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
