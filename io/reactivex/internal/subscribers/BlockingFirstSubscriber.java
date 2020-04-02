package io.reactivex.internal.subscribers;

import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class BlockingFirstSubscriber<T>
  extends BlockingBaseSubscriber<T>
{
  public BlockingFirstSubscriber() {}
  
  public void onError(Throwable paramThrowable)
  {
    if (this.value == null) {
      this.error = paramThrowable;
    } else {
      RxJavaPlugins.onError(paramThrowable);
    }
    countDown();
  }
  
  public void onNext(T paramT)
  {
    if (this.value == null)
    {
      this.value = paramT;
      this.upstream.cancel();
      countDown();
    }
  }
}
