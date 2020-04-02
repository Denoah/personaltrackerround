package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleDetach<T>
  extends Single<T>
{
  final SingleSource<T> source;
  
  public SingleDetach(SingleSource<T> paramSingleSource)
  {
    this.source = paramSingleSource;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new DetachSingleObserver(paramSingleObserver));
  }
  
  static final class DetachSingleObserver<T>
    implements SingleObserver<T>, Disposable
  {
    SingleObserver<? super T> downstream;
    Disposable upstream;
    
    DetachSingleObserver(SingleObserver<? super T> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
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
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      SingleObserver localSingleObserver = this.downstream;
      if (localSingleObserver != null)
      {
        this.downstream = null;
        localSingleObserver.onError(paramThrowable);
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
    
    public void onSuccess(T paramT)
    {
      this.upstream = DisposableHelper.DISPOSED;
      SingleObserver localSingleObserver = this.downstream;
      if (localSingleObserver != null)
      {
        this.downstream = null;
        localSingleObserver.onSuccess(paramT);
      }
    }
  }
}
