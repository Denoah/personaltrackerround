package io.reactivex.internal.subscribers;

public final class BlockingLastSubscriber<T>
  extends BlockingBaseSubscriber<T>
{
  public BlockingLastSubscriber() {}
  
  public void onError(Throwable paramThrowable)
  {
    this.value = null;
    this.error = paramThrowable;
    countDown();
  }
  
  public void onNext(T paramT)
  {
    this.value = paramT;
  }
}
