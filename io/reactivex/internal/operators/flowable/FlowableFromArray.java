package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableFromArray<T>
  extends Flowable<T>
{
  final T[] array;
  
  public FlowableFromArray(T[] paramArrayOfT)
  {
    this.array = paramArrayOfT;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      paramSubscriber.onSubscribe(new ArrayConditionalSubscription((ConditionalSubscriber)paramSubscriber, this.array));
    } else {
      paramSubscriber.onSubscribe(new ArraySubscription(paramSubscriber, this.array));
    }
  }
  
  static final class ArrayConditionalSubscription<T>
    extends FlowableFromArray.BaseArraySubscription<T>
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final ConditionalSubscriber<? super T> downstream;
    
    ArrayConditionalSubscription(ConditionalSubscriber<? super T> paramConditionalSubscriber, T[] paramArrayOfT)
    {
      super();
      this.downstream = paramConditionalSubscriber;
    }
    
    void fastPath()
    {
      Object[] arrayOfObject = this.array;
      int i = arrayOfObject.length;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      for (int j = this.index; j != i; j++)
      {
        if (this.cancelled) {
          return;
        }
        Object localObject = arrayOfObject[j];
        if (localObject == null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("The element at index ");
          ((StringBuilder)localObject).append(j);
          ((StringBuilder)localObject).append(" is null");
          localConditionalSubscriber.onError(new NullPointerException(((StringBuilder)localObject).toString()));
          return;
        }
        localConditionalSubscriber.tryOnNext(localObject);
      }
      if (this.cancelled) {
        return;
      }
      localConditionalSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      Object[] arrayOfObject = this.array;
      int i = arrayOfObject.length;
      int j = this.index;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      long l1;
      do
      {
        l1 = 0L;
        long l2;
        do
        {
          while ((l1 != paramLong) && (j != i))
          {
            if (this.cancelled) {
              return;
            }
            Object localObject = arrayOfObject[j];
            if (localObject == null)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("The element at index ");
              ((StringBuilder)localObject).append(j);
              ((StringBuilder)localObject).append(" is null");
              localConditionalSubscriber.onError(new NullPointerException(((StringBuilder)localObject).toString()));
              return;
            }
            l2 = l1;
            if (localConditionalSubscriber.tryOnNext(localObject)) {
              l2 = l1 + 1L;
            }
            j++;
            l1 = l2;
          }
          if (j == i)
          {
            if (!this.cancelled) {
              localConditionalSubscriber.onComplete();
            }
            return;
          }
          l2 = get();
          paramLong = l2;
        } while (l1 != l2);
        this.index = j;
        l1 = addAndGet(-l1);
        paramLong = l1;
      } while (l1 != 0L);
    }
  }
  
  static final class ArraySubscription<T>
    extends FlowableFromArray.BaseArraySubscription<T>
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final Subscriber<? super T> downstream;
    
    ArraySubscription(Subscriber<? super T> paramSubscriber, T[] paramArrayOfT)
    {
      super();
      this.downstream = paramSubscriber;
    }
    
    void fastPath()
    {
      Object[] arrayOfObject = this.array;
      int i = arrayOfObject.length;
      Subscriber localSubscriber = this.downstream;
      for (int j = this.index; j != i; j++)
      {
        if (this.cancelled) {
          return;
        }
        Object localObject = arrayOfObject[j];
        if (localObject == null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("The element at index ");
          ((StringBuilder)localObject).append(j);
          ((StringBuilder)localObject).append(" is null");
          localSubscriber.onError(new NullPointerException(((StringBuilder)localObject).toString()));
          return;
        }
        localSubscriber.onNext(localObject);
      }
      if (this.cancelled) {
        return;
      }
      localSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      Object localObject1 = this.array;
      int i = localObject1.length;
      int j = this.index;
      Subscriber localSubscriber = this.downstream;
      long l1;
      do
      {
        l1 = 0L;
        long l2;
        do
        {
          while ((l1 != paramLong) && (j != i))
          {
            if (this.cancelled) {
              return;
            }
            Object localObject2 = localObject1[j];
            if (localObject2 == null)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("The element at index ");
              ((StringBuilder)localObject1).append(j);
              ((StringBuilder)localObject1).append(" is null");
              localSubscriber.onError(new NullPointerException(((StringBuilder)localObject1).toString()));
              return;
            }
            localSubscriber.onNext(localObject2);
            l1 += 1L;
            j++;
          }
          if (j == i)
          {
            if (!this.cancelled) {
              localSubscriber.onComplete();
            }
            return;
          }
          l2 = get();
          paramLong = l2;
        } while (l1 != l2);
        this.index = j;
        l1 = addAndGet(-l1);
        paramLong = l1;
      } while (l1 != 0L);
    }
  }
  
  static abstract class BaseArraySubscription<T>
    extends BasicQueueSubscription<T>
  {
    private static final long serialVersionUID = -2252972430506210021L;
    final T[] array;
    volatile boolean cancelled;
    int index;
    
    BaseArraySubscription(T[] paramArrayOfT)
    {
      this.array = paramArrayOfT;
    }
    
    public final void cancel()
    {
      this.cancelled = true;
    }
    
    public final void clear()
    {
      this.index = this.array.length;
    }
    
    abstract void fastPath();
    
    public final boolean isEmpty()
    {
      boolean bool;
      if (this.index == this.array.length) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final T poll()
    {
      int i = this.index;
      Object[] arrayOfObject = this.array;
      if (i == arrayOfObject.length) {
        return null;
      }
      this.index = (i + 1);
      return ObjectHelper.requireNonNull(arrayOfObject[i], "array element is null");
    }
    
    public final void request(long paramLong)
    {
      if ((SubscriptionHelper.validate(paramLong)) && (BackpressureHelper.add(this, paramLong) == 0L)) {
        if (paramLong == Long.MAX_VALUE) {
          fastPath();
        } else {
          slowPath(paramLong);
        }
      }
    }
    
    public final int requestFusion(int paramInt)
    {
      return paramInt & 0x1;
    }
    
    abstract void slowPath(long paramLong);
  }
}
