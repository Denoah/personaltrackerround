package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public abstract interface FlowableEmitter<T>
  extends Emitter<T>
{
  public abstract boolean isCancelled();
  
  public abstract long requested();
  
  public abstract FlowableEmitter<T> serialize();
  
  public abstract void setCancellable(Cancellable paramCancellable);
  
  public abstract void setDisposable(Disposable paramDisposable);
  
  public abstract boolean tryOnError(Throwable paramThrowable);
}
