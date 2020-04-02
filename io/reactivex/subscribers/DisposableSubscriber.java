package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public abstract class DisposableSubscriber<T>
  implements FlowableSubscriber<T>, Disposable
{
  final AtomicReference<Subscription> upstream = new AtomicReference();
  
  public DisposableSubscriber() {}
  
  protected final void cancel()
  {
    dispose();
  }
  
  public final void dispose()
  {
    SubscriptionHelper.cancel(this.upstream);
  }
  
  public final boolean isDisposed()
  {
    boolean bool;
    if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void onStart()
  {
    ((Subscription)this.upstream.get()).request(Long.MAX_VALUE);
  }
  
  public final void onSubscribe(Subscription paramSubscription)
  {
    if (EndConsumerHelper.setOnce(this.upstream, paramSubscription, getClass())) {
      onStart();
    }
  }
  
  protected final void request(long paramLong)
  {
    ((Subscription)this.upstream.get()).request(paramLong);
  }
}
