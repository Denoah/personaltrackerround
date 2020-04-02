package io.reactivex.internal.subscriptions;

import org.reactivestreams.Subscriber;

public class DeferredScalarSubscription<T>
  extends BasicIntQueueSubscription<T>
{
  static final int CANCELLED = 4;
  static final int FUSED_CONSUMED = 32;
  static final int FUSED_EMPTY = 8;
  static final int FUSED_READY = 16;
  static final int HAS_REQUEST_HAS_VALUE = 3;
  static final int HAS_REQUEST_NO_VALUE = 2;
  static final int NO_REQUEST_HAS_VALUE = 1;
  static final int NO_REQUEST_NO_VALUE = 0;
  private static final long serialVersionUID = -2151279923272604993L;
  protected final Subscriber<? super T> downstream;
  protected T value;
  
  public DeferredScalarSubscription(Subscriber<? super T> paramSubscriber)
  {
    this.downstream = paramSubscriber;
  }
  
  public void cancel()
  {
    set(4);
    this.value = null;
  }
  
  public final void clear()
  {
    lazySet(32);
    this.value = null;
  }
  
  public final void complete(T paramT)
  {
    int i = get();
    int j;
    do
    {
      Subscriber localSubscriber;
      if (i == 8)
      {
        this.value = paramT;
        lazySet(16);
        localSubscriber = this.downstream;
        localSubscriber.onNext(paramT);
        if (get() != 4) {
          localSubscriber.onComplete();
        }
        return;
      }
      if ((i & 0xFFFFFFFD) != 0) {
        return;
      }
      if (i == 2)
      {
        lazySet(3);
        localSubscriber = this.downstream;
        localSubscriber.onNext(paramT);
        if (get() != 4) {
          localSubscriber.onComplete();
        }
        return;
      }
      this.value = paramT;
      if (compareAndSet(0, 1)) {
        return;
      }
      j = get();
      i = j;
    } while (j != 4);
    this.value = null;
  }
  
  public final boolean isCancelled()
  {
    boolean bool;
    if (get() == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (get() != 16) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final T poll()
  {
    if (get() == 16)
    {
      lazySet(32);
      Object localObject = this.value;
      this.value = null;
      return localObject;
    }
    return null;
  }
  
  public final void request(long paramLong)
  {
    if (SubscriptionHelper.validate(paramLong)) {
      do
      {
        int i = get();
        if ((i & 0xFFFFFFFE) != 0) {
          return;
        }
        if (i == 1)
        {
          if (compareAndSet(1, 3))
          {
            Object localObject = this.value;
            if (localObject != null)
            {
              this.value = null;
              Subscriber localSubscriber = this.downstream;
              localSubscriber.onNext(localObject);
              if (get() != 4) {
                localSubscriber.onComplete();
              }
            }
          }
          return;
        }
      } while (!compareAndSet(0, 2));
    }
  }
  
  public final int requestFusion(int paramInt)
  {
    if ((paramInt & 0x2) != 0)
    {
      lazySet(8);
      return 2;
    }
    return 0;
  }
  
  public final boolean tryCancel()
  {
    boolean bool;
    if (getAndSet(4) != 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
