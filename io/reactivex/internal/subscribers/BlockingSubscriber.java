package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class BlockingSubscriber<T>
  extends AtomicReference<Subscription>
  implements FlowableSubscriber<T>, Subscription
{
  public static final Object TERMINATED = new Object();
  private static final long serialVersionUID = -4875965440900746268L;
  final Queue<Object> queue;
  
  public BlockingSubscriber(Queue<Object> paramQueue)
  {
    this.queue = paramQueue;
  }
  
  public void cancel()
  {
    if (SubscriptionHelper.cancel(this)) {
      this.queue.offer(TERMINATED);
    }
  }
  
  public boolean isCancelled()
  {
    boolean bool;
    if (get() == SubscriptionHelper.CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    this.queue.offer(NotificationLite.complete());
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.queue.offer(NotificationLite.error(paramThrowable));
  }
  
  public void onNext(T paramT)
  {
    this.queue.offer(NotificationLite.next(paramT));
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (SubscriptionHelper.setOnce(this, paramSubscription)) {
      this.queue.offer(NotificationLite.subscription(this));
    }
  }
  
  public void request(long paramLong)
  {
    ((Subscription)get()).request(paramLong);
  }
}
