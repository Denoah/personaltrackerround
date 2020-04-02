package io.reactivex;

import io.reactivex.disposables.Disposable;

public abstract interface SingleObserver<T>
{
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onSubscribe(Disposable paramDisposable);
  
  public abstract void onSuccess(T paramT);
}
