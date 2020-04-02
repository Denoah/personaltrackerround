package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAnySingle<T>
  extends Single<Boolean>
  implements FuseToObservable<Boolean>
{
  final Predicate<? super T> predicate;
  final ObservableSource<T> source;
  
  public ObservableAnySingle(ObservableSource<T> paramObservableSource, Predicate<? super T> paramPredicate)
  {
    this.source = paramObservableSource;
    this.predicate = paramPredicate;
  }
  
  public Observable<Boolean> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableAny(this.source, this.predicate));
  }
  
  protected void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    this.source.subscribe(new AnyObserver(paramSingleObserver, this.predicate));
  }
  
  static final class AnyObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final SingleObserver<? super Boolean> downstream;
    final Predicate<? super T> predicate;
    Disposable upstream;
    
    AnyObserver(SingleObserver<? super Boolean> paramSingleObserver, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramSingleObserver;
      this.predicate = paramPredicate;
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
        this.downstream.onSuccess(Boolean.valueOf(false));
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
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (bool)
        {
          this.done = true;
          this.upstream.dispose();
          this.downstream.onSuccess(Boolean.valueOf(true));
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
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
