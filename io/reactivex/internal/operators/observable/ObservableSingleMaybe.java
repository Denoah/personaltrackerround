package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableSingleMaybe<T>
  extends Maybe<T>
{
  final ObservableSource<T> source;
  
  public ObservableSingleMaybe(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  public void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new SingleElementObserver(paramMaybeObserver));
  }
  
  static final class SingleElementObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final MaybeObserver<? super T> downstream;
    Disposable upstream;
    T value;
    
    SingleElementObserver(MaybeObserver<? super T> paramMaybeObserver)
    {
      this.downstream = paramMaybeObserver;
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
      Object localObject = this.value;
      this.value = null;
      if (localObject == null) {
        this.downstream.onComplete();
      } else {
        this.downstream.onSuccess(localObject);
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
