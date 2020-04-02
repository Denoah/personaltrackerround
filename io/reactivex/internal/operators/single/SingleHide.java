package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleHide<T>
  extends Single<T>
{
  final SingleSource<? extends T> source;
  
  public SingleHide(SingleSource<? extends T> paramSingleSource)
  {
    this.source = paramSingleSource;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new HideSingleObserver(paramSingleObserver));
  }
  
  static final class HideSingleObserver<T>
    implements SingleObserver<T>, Disposable
  {
    final SingleObserver<? super T> downstream;
    Disposable upstream;
    
    HideSingleObserver(SingleObserver<? super T> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
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
    
    public void onSuccess(T paramT)
    {
      this.downstream.onSuccess(paramT);
    }
  }
}
