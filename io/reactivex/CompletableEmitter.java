package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public abstract interface CompletableEmitter
{
  public abstract boolean isDisposed();
  
  public abstract void onComplete();
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void setCancellable(Cancellable paramCancellable);
  
  public abstract void setDisposable(Disposable paramDisposable);
  
  public abstract boolean tryOnError(Throwable paramThrowable);
}
