package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscription;

public final class ArrayCompositeSubscription
  extends AtomicReferenceArray<Subscription>
  implements Disposable
{
  private static final long serialVersionUID = 2746389416410565408L;
  
  public ArrayCompositeSubscription(int paramInt)
  {
    super(paramInt);
  }
  
  public void dispose()
  {
    int i = 0;
    if (get(0) != SubscriptionHelper.CANCELLED)
    {
      int j = length();
      while (i < j)
      {
        if ((Subscription)get(i) != SubscriptionHelper.CANCELLED)
        {
          Subscription localSubscription = (Subscription)getAndSet(i, SubscriptionHelper.CANCELLED);
          if ((localSubscription != SubscriptionHelper.CANCELLED) && (localSubscription != null)) {
            localSubscription.cancel();
          }
        }
        i++;
      }
    }
  }
  
  public boolean isDisposed()
  {
    boolean bool = false;
    if (get(0) == SubscriptionHelper.CANCELLED) {
      bool = true;
    }
    return bool;
  }
  
  public Subscription replaceResource(int paramInt, Subscription paramSubscription)
  {
    Subscription localSubscription;
    do
    {
      localSubscription = (Subscription)get(paramInt);
      if (localSubscription == SubscriptionHelper.CANCELLED)
      {
        if (paramSubscription != null) {
          paramSubscription.cancel();
        }
        return null;
      }
    } while (!compareAndSet(paramInt, localSubscription, paramSubscription));
    return localSubscription;
  }
  
  public boolean setResource(int paramInt, Subscription paramSubscription)
  {
    Subscription localSubscription;
    do
    {
      localSubscription = (Subscription)get(paramInt);
      if (localSubscription == SubscriptionHelper.CANCELLED)
      {
        if (paramSubscription != null) {
          paramSubscription.cancel();
        }
        return false;
      }
    } while (!compareAndSet(paramInt, localSubscription, paramSubscription));
    if (localSubscription != null) {
      localSubscription.cancel();
    }
    return true;
  }
}
