package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import org.reactivestreams.Subscriber;

public enum EmptySubscription
  implements QueueSubscription<Object>
{
  static
  {
    EmptySubscription localEmptySubscription = new EmptySubscription("INSTANCE", 0);
    INSTANCE = localEmptySubscription;
    $VALUES = new EmptySubscription[] { localEmptySubscription };
  }
  
  private EmptySubscription() {}
  
  public static void complete(Subscriber<?> paramSubscriber)
  {
    paramSubscriber.onSubscribe(INSTANCE);
    paramSubscriber.onComplete();
  }
  
  public static void error(Throwable paramThrowable, Subscriber<?> paramSubscriber)
  {
    paramSubscriber.onSubscribe(INSTANCE);
    paramSubscriber.onError(paramThrowable);
  }
  
  public void cancel() {}
  
  public void clear() {}
  
  public boolean isEmpty()
  {
    return true;
  }
  
  public boolean offer(Object paramObject)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
  
  public boolean offer(Object paramObject1, Object paramObject2)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
  
  public Object poll()
  {
    return null;
  }
  
  public void request(long paramLong)
  {
    SubscriptionHelper.validate(paramLong);
  }
  
  public int requestFusion(int paramInt)
  {
    return paramInt & 0x2;
  }
  
  public String toString()
  {
    return "EmptySubscription";
  }
}
