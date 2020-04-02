package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;

public final class SingleToObservable<T>
  extends Observable<T>
{
  final SingleSource<? extends T> source;
  
  public SingleToObservable(SingleSource<? extends T> paramSingleSource)
  {
    this.source = paramSingleSource;
  }
  
  public static <T> SingleObserver<T> create(Observer<? super T> paramObserver)
  {
    return new SingleToObservableObserver(paramObserver);
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(create(paramObserver));
  }
  
  static final class SingleToObservableObserver<T>
    extends DeferredScalarDisposable<T>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = 3786543492451018833L;
    Disposable upstream;
    
    SingleToObservableObserver(Observer<? super T> paramObserver)
    {
      super();
    }
    
    public void dispose()
    {
      super.dispose();
      this.upstream.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      error(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      complete(paramT);
    }
  }
}
