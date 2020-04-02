package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableTake<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final long limit;
  
  public ObservableTake(ObservableSource<T> paramObservableSource, long paramLong)
  {
    super(paramObservableSource);
    this.limit = paramLong;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeObserver(paramObserver, this.limit));
  }
  
  static final class TakeObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super T> downstream;
    long remaining;
    Disposable upstream;
    
    TakeObserver(Observer<? super T> paramObserver, long paramLong)
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
      if (!this.done)
      {
        this.done = true;
        this.upstream.dispose();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.upstream.dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (!this.done)
      {
        long l1 = this.remaining;
        long l2 = l1 - 1L;
        this.remaining = l2;
        if (l1 > 0L)
        {
          int i;
          if (l2 == 0L) {
            i = 1;
          } else {
            i = 0;
          }
          this.downstream.onNext(paramT);
          if (i != 0) {
            onComplete();
          }
        }
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        if (this.remaining == 0L)
        {
          this.done = true;
          paramDisposable.dispose();
          EmptyDisposable.complete(this.downstream);
        }
        else
        {
          this.downstream.onSubscribe(this);
        }
      }
    }
  }
}
