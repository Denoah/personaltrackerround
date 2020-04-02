package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import org.reactivestreams.Subscription;

public abstract class BlockingBaseSubscriber<T>
  extends CountDownLatch
  implements FlowableSubscriber<T>
{
  volatile boolean cancelled;
  Throwable error;
  Subscription upstream;
  T value;
  
  public BlockingBaseSubscriber()
  {
    super(1);
  }
  
  public final T blockingGet()
  {
    if (getCount() != 0L) {
      try
      {
        BlockingHelper.verifyNonBlocking();
        await();
      }
      catch (InterruptedException localInterruptedException)
      {
        Subscription localSubscription = this.upstream;
        this.upstream = SubscriptionHelper.CANCELLED;
        if (localSubscription != null) {
          localSubscription.cancel();
        }
        throw ExceptionHelper.wrapOrThrow(localInterruptedException);
      }
    }
    Throwable localThrowable = this.error;
    if (localThrowable == null) {
      return this.value;
    }
    throw ExceptionHelper.wrapOrThrow(localThrowable);
  }
  
  public final void onComplete()
  {
    countDown();
  }
  
  public final void onSubscribe(Subscription paramSubscription)
  {
    if (SubscriptionHelper.validate(this.upstream, paramSubscription))
    {
      this.upstream = paramSubscription;
      if (!this.cancelled)
      {
        paramSubscription.request(Long.MAX_VALUE);
        if (this.cancelled)
        {
          this.upstream = SubscriptionHelper.CANCELLED;
          paramSubscription.cancel();
        }
      }
    }
  }
}
