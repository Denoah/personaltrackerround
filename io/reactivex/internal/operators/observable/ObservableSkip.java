package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableSkip<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final long n;
  
  public ObservableSkip(ObservableSource<T> paramObservableSource, long paramLong)
  {
    super(paramObservableSource);
    this.n = paramLong;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new SkipObserver(paramObserver, this.n));
  }
  
  static final class SkipObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    long remaining;
    Disposable upstream;
    
    SkipObserver(Observer<? super T> paramObserver, long paramLong)
    {
      this.downstream = paramObserver;
      this.remaining = paramLong;
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
      long l = this.remaining;
      if (l != 0L) {
        this.remaining = (l - 1L);
      } else {
        this.downstream.onNext(paramT);
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
