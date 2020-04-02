package io.reactivex.internal.subscriptions;

import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscription;

public final class BooleanSubscription
  extends AtomicBoolean
  implements Subscription
{
  private static final long serialVersionUID = -8127758972444290902L;
  
  public BooleanSubscription() {}
  
  public void cancel()
  {
    lazySet(true);
  }
  
  public boolean isCancelled()
  {
    return get();
  }
  
  public void request(long paramLong)
  {
    SubscriptionHelper.validate(paramLong);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("BooleanSubscription(cancelled=");
    localStringBuilder.append(get());
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
