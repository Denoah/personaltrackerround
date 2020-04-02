package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class CompletableDetach
  extends Completable
{
  final CompletableSource source;
  
  public CompletableDetach(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new DetachCompletableObserver(paramCompletableObserver));
  }
  
  static final class DetachCompletableObserver
    implements CompletableObserver, Disposable
  {
    CompletableObserver downstream;
    Disposable upstream;
    
    DetachCompletableObserver(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
    }
    
    public void dispose()
    {
      this.downstream = null;
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      this.upstream = DisposableHelper.DISPOSED;
      CompletableObserver localCompletableObserver = this.downstream;
      if (localCompletableObserver != null)
      {
        this.downstream = null;
        localCompletableObserver.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      CompletableObserver localCompletableObserver = this.downstream;
      if (localCompletableObserver != null)
      {
        this.downstream = null;
        localCompletableObserver.onError(paramThrowable);
      }
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
