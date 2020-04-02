package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRange
  extends Flowable<Integer>
{
  final int end;
  final int start;
  
  public FlowableRange(int paramInt1, int paramInt2)
  {
    this.start = paramInt1;
    this.end = (paramInt1 + paramInt2);
  }
  
  public void subscribeActual(Subscriber<? super Integer> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      paramSubscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber)paramSubscriber, this.start, this.end));
    } else {
      paramSubscriber.onSubscribe(new RangeSubscription(paramSubscriber, this.start, this.end));
    }
  }
  
  static abstract class BaseRangeSubscription
    extends BasicQueueSubscription<Integer>
  {
    private static final long serialVersionUID = -2252972430506210021L;
    volatile boolean cancelled;
    final int end;
    int index;
    
    BaseRangeSubscription(int paramInt1, int paramInt2)
    {
      this.index = paramInt1;
      this.end = paramInt2;
    }
    
    public final void cancel()
    {
      this.cancelled = true;
    }
    
    public final void clear()
    {
      this.index = this.end;
    }
    
    abstract void fastPath();
    
    public final boolean isEmpty()
    {
      boolean bool;
      if (this.index == this.end) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final Integer poll()
    {
      int i = this.index;
      if (i == this.end) {
        return null;
      }
      this.index = (i + 1);
      return Integer.valueOf(i);
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
  
  static final class RangeConditionalSubscription
    extends FlowableRange.BaseRangeSubscription
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final ConditionalSubscriber<? super Integer> downstream;
    
    RangeConditionalSubscription(ConditionalSubscriber<? super Integer> paramConditionalSubscriber, int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.downstream = paramConditionalSubscriber;
    }
    
    void fastPath()
    {
      int i = this.end;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      for (int j = this.index; j != i; j++)
      {
        if (this.cancelled) {
          return;
        }
        localConditionalSubscriber.tryOnNext(Integer.valueOf(j));
      }
      if (this.cancelled) {
        return;
      }
      localConditionalSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      int i = this.end;
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
            l2 = l1;
            if (localConditionalSubscriber.tryOnNext(Integer.valueOf(j))) {
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
  
  static final class RangeSubscription
    extends FlowableRange.BaseRangeSubscription
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final Subscriber<? super Integer> downstream;
    
    RangeSubscription(Subscriber<? super Integer> paramSubscriber, int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.downstream = paramSubscriber;
    }
    
    void fastPath()
    {
      int i = this.end;
      Subscriber localSubscriber = this.downstream;
      for (int j = this.index; j != i; j++)
      {
        if (this.cancelled) {
          return;
        }
        localSubscriber.onNext(Integer.valueOf(j));
      }
      if (this.cancelled) {
        return;
      }
      localSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      int i = this.end;
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
            localSubscriber.onNext(Integer.valueOf(j));
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
}
