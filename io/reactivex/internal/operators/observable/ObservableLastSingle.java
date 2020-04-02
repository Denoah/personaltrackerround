package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.NoSuchElementException;

public final class ObservableLastSingle<T>
  extends Single<T>
{
  final T defaultItem;
  final ObservableSource<T> source;
  
  public ObservableLastSingle(ObservableSource<T> paramObservableSource, T paramT)
  {
    this.source = paramObservableSource;
    this.defaultItem = paramT;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new LastObserver(paramSingleObserver, this.defaultItem));
  }
  
  static final class LastObserver<T>
    implements Observer<T>, Disposable
  {
    final T defaultItem;
    final SingleObserver<? super T> downstream;
    T item;
    Disposable upstream;
    
    LastObserver(SingleObserver<? super T> paramSingleObserver, T paramT)
    {
      this.downstream = paramSingleObserver;
      this.defaultItem = paramT;
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
        localObject = this.defaultItem;
        if (localObject != null) {
          this.downstream.onSuccess(localObject);
        } else {
          this.downstream.onError(new NoSuchElementException());
        }
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
