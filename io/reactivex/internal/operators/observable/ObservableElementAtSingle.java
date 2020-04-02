package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAtSingle<T>
  extends Single<T>
  implements FuseToObservable<T>
{
  final T defaultValue;
  final long index;
  final ObservableSource<T> source;
  
  public ObservableElementAtSingle(ObservableSource<T> paramObservableSource, long paramLong, T paramT)
  {
    this.source = paramObservableSource;
    this.index = paramLong;
    this.defaultValue = paramT;
  }
  
  public Observable<T> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableElementAt(this.source, this.index, this.defaultValue, true));
  }
  
  public void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new ElementAtObserver(paramSingleObserver, this.index, this.defaultValue));
  }
  
  static final class ElementAtObserver<T>
    implements Observer<T>, Disposable
  {
    long count;
    final T defaultValue;
    boolean done;
    final SingleObserver<? super T> downstream;
    final long index;
    Disposable upstream;
    
    ElementAtObserver(SingleObserver<? super T> paramSingleObserver, long paramLong, T paramT)
    {
      this.downstream = paramSingleObserver;
      this.index = paramLong;
      this.defaultValue = paramT;
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
        Object localObject = this.defaultValue;
        if (localObject != null) {
          this.downstream.onSuccess(localObject);
        } else {
          this.downstream.onError(new NoSuchElementException());
        }
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
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.count;
      if (l == this.index)
      {
        this.done = true;
        this.upstream.dispose();
        this.downstream.onSuccess(paramT);
        return;
      }
      this.count = (l + 1L);
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
