package io.reactivex;

import io.reactivex.disposables.Disposable;

public abstract interface CompletableObserver
{
  public abstract void onComplete();
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onSubscribe(Disposable paramDisposable);
}
