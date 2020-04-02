package io.reactivex.internal.observers;

public abstract interface InnerQueuedObserverSupport<T>
{
  public abstract void drain();
  
  public abstract void innerComplete(InnerQueuedObserver<T> paramInnerQueuedObserver);
  
  public abstract void innerError(InnerQueuedObserver<T> paramInnerQueuedObserver, Throwable paramThrowable);
  
  public abstract void innerNext(InnerQueuedObserver<T> paramInnerQueuedObserver, T paramT);
}
