package io.reactivex.internal.observers;

import io.reactivex.disposables.Disposable;

public final class BlockingFirstObserver<T>
  extends BlockingBaseObserver<T>
{
  public BlockingFirstObserver() {}
  
  public void onError(Throwable paramThrowable)
  {
    if (this.value == null) {
      this.error = paramThrowable;
    }
    countDown();
  }
  
  public void onNext(T paramT)
  {
    if (this.value == null)
    {
      this.value = paramT;
      this.upstream.dispose();
      countDown();
    }
  }
}
