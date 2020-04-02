package io.reactivex.internal.subscribers;

public abstract interface InnerQueuedSubscriberSupport<T>
{
  public abstract void drain();
  
  public abstract void innerComplete(InnerQueuedSubscriber<T> paramInnerQueuedSubscriber);
  
  public abstract void innerError(InnerQueuedSubscriber<T> paramInnerQueuedSubscriber, Throwable paramThrowable);
  
  public abstract void innerNext(InnerQueuedSubscriber<T> paramInnerQueuedSubscriber, T paramT);
}
