package io.reactivex;

import io.reactivex.disposables.Disposable;

public abstract interface MaybeObserver<T>
{
  public abstract void onComplete();
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onSubscribe(Disposable paramDisposable);
  
  public abstract void onSuccess(T paramT);
}
