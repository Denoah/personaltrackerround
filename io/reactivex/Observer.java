package io.reactivex;

import io.reactivex.disposables.Disposable;

public abstract interface Observer<T>
{
  public abstract void onComplete();
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onNext(T paramT);
  
  public abstract void onSubscribe(Disposable paramDisposable);
}
