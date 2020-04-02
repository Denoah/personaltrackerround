package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class ObservableIgnoreElements<T>
  extends AbstractObservableWithUpstream<T, T>
{
  public ObservableIgnoreElements(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new IgnoreObservable(paramObserver));
  }
  
  static final class IgnoreObservable<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    Disposable upstream;
    
    IgnoreObservable(Observer<? super T> paramObserver)
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
    
    public void onNext(T paramT) {}
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.upstream = paramDisposable;
      this.downstream.onSubscribe(this);
    }
  }
}
