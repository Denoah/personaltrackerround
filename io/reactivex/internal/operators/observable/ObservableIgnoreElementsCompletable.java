package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableIgnoreElementsCompletable<T>
  extends Completable
  implements FuseToObservable<T>
{
  final ObservableSource<T> source;
  
  public ObservableIgnoreElementsCompletable(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  public Observable<T> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this.source));
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new IgnoreObservable(paramCompletableObserver));
  }
  
  static final class IgnoreObservable<T>
    implements Observer<T>, Disposable
  {
    final CompletableObserver downstream;
    Disposable upstream;
    
    IgnoreObservable(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
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
