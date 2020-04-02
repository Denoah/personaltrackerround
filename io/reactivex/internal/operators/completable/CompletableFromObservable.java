package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class CompletableFromObservable<T>
  extends Completable
{
  final ObservableSource<T> observable;
  
  public CompletableFromObservable(ObservableSource<T> paramObservableSource)
  {
    this.observable = paramObservableSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.observable.subscribe(new CompletableFromObservableObserver(paramCompletableObserver));
  }
  
  static final class CompletableFromObservableObserver<T>
    implements Observer<T>
  {
    final CompletableObserver co;
    
    CompletableFromObservableObserver(CompletableObserver paramCompletableObserver)
    {
      this.co = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      this.co.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.co.onError(paramThrowable);
    }
    
    public void onNext(T paramT) {}
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.co.onSubscribe(paramDisposable);
    }
  }
}
