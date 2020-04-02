package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableCount<T>
  extends AbstractObservableWithUpstream<T, Long>
{
  public ObservableCount(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  public void subscribeActual(Observer<? super Long> paramObserver)
  {
    this.source.subscribe(new CountObserver(paramObserver));
  }
  
  static final class CountObserver
    implements Observer<Object>, Disposable
  {
    long count;
    final Observer<? super Long> downstream;
    Disposable upstream;
    
    CountObserver(Observer<? super Long> paramObserver)
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
      this.downstream.onNext(Long.valueOf(this.count));
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.count += 1L;
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
