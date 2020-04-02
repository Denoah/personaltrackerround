package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableHide<T>
  extends AbstractObservableWithUpstream<T, T>
{
  public ObservableHide(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new HideDisposable(paramObserver));
  }
  
  static final class HideDisposable<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    Disposable upstream;
    
    HideDisposable(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
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
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
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
