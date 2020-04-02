package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableLastMaybe<T>
  extends Maybe<T>
{
  final ObservableSource<T> source;
  
  public ObservableLastMaybe(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new LastObserver(paramMaybeObserver));
  }
  
  static final class LastObserver<T>
    implements Observer<T>, Disposable
  {
    final MaybeObserver<? super T> downstream;
    T item;
    Disposable upstream;
    
    LastObserver(MaybeObserver<? super T> paramMaybeObserver)
    {
      this.downstream = paramMaybeObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.upstream == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      this.upstream = DisposableHelper.DISPOSED;
      Object localObject = this.item;
      if (localObject != null)
      {
        this.item = null;
        this.downstream.onSuccess(localObject);
      }
      else
      {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.item = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.item = paramT;
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
