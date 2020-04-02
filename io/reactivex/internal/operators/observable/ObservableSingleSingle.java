package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableSingleSingle<T>
  extends Single<T>
{
  final T defaultValue;
  final ObservableSource<? extends T> source;
  
  public ObservableSingleSingle(ObservableSource<? extends T> paramObservableSource, T paramT)
  {
    this.source = paramObservableSource;
    this.defaultValue = paramT;
  }
  
  public void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new SingleElementObserver(paramSingleObserver, this.defaultValue));
  }
  
  static final class SingleElementObserver<T>
    implements Observer<T>, Disposable
  {
    final T defaultValue;
    boolean done;
    final SingleObserver<? super T> downstream;
    Disposable upstream;
    T value;
    
    SingleElementObserver(SingleObserver<? super T> paramSingleObserver, T paramT)
    {
      this.downstream = paramSingleObserver;
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
      if (this.done) {
        return;
      }
      this.done = true;
      Object localObject1 = this.value;
      this.value = null;
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = this.defaultValue;
      }
      if (localObject2 != null) {
        this.downstream.onSuccess(localObject2);
      } else {
        this.downstream.onError(new NoSuchElementException());
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
      if (this.value != null)
      {
        this.done = true;
        this.upstream.dispose();
        this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
        return;
      }
      this.value = paramT;
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
