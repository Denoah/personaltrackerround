package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BasicIntQueueSubscription<T>
  extends AtomicInteger
  implements QueueSubscription<T>
{
  private static final long serialVersionUID = -6671519529404341862L;
  
  public BasicIntQueueSubscription() {}
  
  public final boolean offer(T paramT)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
  
  public final boolean offer(T paramT1, T paramT2)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
}
