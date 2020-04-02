package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public class SubscriptionArbiter
  extends AtomicInteger
  implements Subscription
{
  private static final long serialVersionUID = -2189523197179400958L;
  Subscription actual;
  final boolean cancelOnReplace;
  volatile boolean cancelled;
  final AtomicLong missedProduced;
  final AtomicLong missedRequested;
  final AtomicReference<Subscription> missedSubscription;
  long requested;
  protected boolean unbounded;
  
  public SubscriptionArbiter(boolean paramBoolean)
  {
    this.cancelOnReplace = paramBoolean;
    this.missedSubscription = new AtomicReference();
    this.missedRequested = new AtomicLong();
    this.missedProduced = new AtomicLong();
  }
  
  public void cancel()
  {
    if (!this.cancelled)
    {
      this.cancelled = true;
      drain();
    }
  }
  
  final void drain()
  {
    if (getAndIncrement() != 0) {
      return;
    }
    drainLoop();
  }
  
  final void drainLoop()
  {
    int i = 1;
    Object localObject1 = null;
    long l1 = 0L;
    Object localObject2;
    long l2;
    int j;
    do
    {
      localObject2 = (Subscription)this.missedSubscription.get();
      Object localObject3 = localObject2;
      if (localObject2 != null) {
        localObject3 = (Subscription)this.missedSubscription.getAndSet(null);
      }
      l2 = this.missedRequested.get();
      long l3 = l2;
      if (l2 != 0L) {
        l3 = this.missedRequested.getAndSet(0L);
      }
      l2 = this.missedProduced.get();
      long l4 = l2;
      if (l2 != 0L) {
        l4 = this.missedProduced.getAndSet(0L);
      }
      Subscription localSubscription = this.actual;
      if (this.cancelled)
      {
        if (localSubscription != null)
        {
          localSubscription.cancel();
          this.actual = null;
        }
        l2 = l1;
        localObject2 = localObject1;
        if (localObject3 != null)
        {
          ((Subscription)localObject3).cancel();
          l2 = l1;
          localObject2 = localObject1;
        }
      }
      else
      {
        l2 = this.requested;
        long l5 = l2;
        if (l2 != Long.MAX_VALUE)
        {
          l5 = BackpressureHelper.addCap(l2, l3);
          l2 = l5;
          if (l5 != Long.MAX_VALUE)
          {
            l4 = l5 - l4;
            l2 = l4;
            if (l4 < 0L)
            {
              SubscriptionHelper.reportMoreProduced(l4);
              l2 = 0L;
            }
          }
          this.requested = l2;
          l5 = l2;
        }
        if (localObject3 != null)
        {
          if ((localSubscription != null) && (this.cancelOnReplace)) {
            localSubscription.cancel();
          }
          this.actual = ((Subscription)localObject3);
          l2 = l1;
          localObject2 = localObject1;
          if (l5 != 0L)
          {
            l2 = BackpressureHelper.addCap(l1, l5);
            localObject2 = localObject3;
          }
        }
        else
        {
          l2 = l1;
          localObject2 = localObject1;
          if (localSubscription != null)
          {
            l2 = l1;
            localObject2 = localObject1;
            if (l3 != 0L)
            {
              l2 = BackpressureHelper.addCap(l1, l3);
              localObject2 = localSubscription;
            }
          }
        }
      }
      j = addAndGet(-i);
      i = j;
      l1 = l2;
      localObject1 = localObject2;
    } while (j != 0);
    if (l2 != 0L) {
      ((Subscription)localObject2).request(l2);
    }
  }
  
  public final boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public final boolean isUnbounded()
  {
    return this.unbounded;
  }
  
  public final void produced(long paramLong)
  {
    if (this.unbounded) {
      return;
    }
    if ((get() == 0) && (compareAndSet(0, 1)))
    {
      long l = this.requested;
      if (l != Long.MAX_VALUE)
      {
        l -= paramLong;
        paramLong = l;
        if (l < 0L)
        {
          SubscriptionHelper.reportMoreProduced(l);
          paramLong = 0L;
        }
        this.requested = paramLong;
      }
      if (decrementAndGet() == 0) {
        return;
      }
      drainLoop();
      return;
    }
    BackpressureHelper.add(this.missedProduced, paramLong);
    drain();
  }
  
  public final void request(long paramLong)
  {
    if (SubscriptionHelper.validate(paramLong))
    {
      if (this.unbounded) {
        return;
      }
      if ((get() == 0) && (compareAndSet(0, 1)))
      {
        long l = this.requested;
        if (l != Long.MAX_VALUE)
        {
          l = BackpressureHelper.addCap(l, paramLong);
          this.requested = l;
          if (l == Long.MAX_VALUE) {
            this.unbounded = true;
          }
        }
        Subscription localSubscription = this.actual;
        if (decrementAndGet() != 0) {
          drainLoop();
        }
        if (localSubscription != null) {
          localSubscription.request(paramLong);
        }
        return;
      }
      BackpressureHelper.add(this.missedRequested, paramLong);
      drain();
    }
  }
  
  public final void setSubscription(Subscription paramSubscription)
  {
    if (this.cancelled)
    {
      paramSubscription.cancel();
      return;
    }
    ObjectHelper.requireNonNull(paramSubscription, "s is null");
    if ((get() == 0) && (compareAndSet(0, 1)))
    {
      Subscription localSubscription = this.actual;
      if ((localSubscription != null) && (this.cancelOnReplace)) {
        localSubscription.cancel();
      }
      this.actual = paramSubscription;
      long l = this.requested;
      if (decrementAndGet() != 0) {
        drainLoop();
      }
      if (l != 0L) {
        paramSubscription.request(l);
      }
      return;
    }
    paramSubscription = (Subscription)this.missedSubscription.getAndSet(paramSubscription);
    if ((paramSubscription != null) && (this.cancelOnReplace)) {
      paramSubscription.cancel();
    }
    drain();
  }
}
