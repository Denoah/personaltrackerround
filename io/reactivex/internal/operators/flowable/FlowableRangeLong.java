package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRangeLong
  extends Flowable<Long>
{
  final long end;
  final long start;
  
  public FlowableRangeLong(long paramLong1, long paramLong2)
  {
    this.start = paramLong1;
    this.end = (paramLong1 + paramLong2);
  }
  
  public void subscribeActual(Subscriber<? super Long> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      paramSubscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber)paramSubscriber, this.start, this.end));
    } else {
      paramSubscriber.onSubscribe(new RangeSubscription(paramSubscriber, this.start, this.end));
    }
  }
  
  static abstract class BaseRangeSubscription
    extends BasicQueueSubscription<Long>
  {
    private static final long serialVersionUID = -2252972430506210021L;
    volatile boolean cancelled;
    final long end;
    long index;
    
    BaseRangeSubscription(long paramLong1, long paramLong2)
    {
      this.index = paramLong1;
      this.end = paramLong2;
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
    
    public final Long poll()
    {
      long l = this.index;
      if (l == this.end) {
        return null;
      }
      this.index = (1L + l);
      return Long.valueOf(l);
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
    extends FlowableRangeLong.BaseRangeSubscription
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final ConditionalSubscriber<? super Long> downstream;
    
    RangeConditionalSubscription(ConditionalSubscriber<? super Long> paramConditionalSubscriber, long paramLong1, long paramLong2)
    {
      super(paramLong2);
      this.downstream = paramConditionalSubscriber;
    }
    
    void fastPath()
    {
      long l1 = this.end;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      for (long l2 = this.index; l2 != l1; l2 += 1L)
      {
        if (this.cancelled) {
          return;
        }
        localConditionalSubscriber.tryOnNext(Long.valueOf(l2));
      }
      if (this.cancelled) {
        return;
      }
      localConditionalSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      long l1 = this.end;
      long l2 = this.index;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      long l3;
      do
      {
        l3 = 0L;
        long l4;
        do
        {
          while ((l3 != paramLong) && (l2 != l1))
          {
            if (this.cancelled) {
              return;
            }
            l4 = l3;
            if (localConditionalSubscriber.tryOnNext(Long.valueOf(l2))) {
              l4 = l3 + 1L;
            }
            l2 += 1L;
            l3 = l4;
          }
          if (l2 == l1)
          {
            if (!this.cancelled) {
              localConditionalSubscriber.onComplete();
            }
            return;
          }
          l4 = get();
          paramLong = l4;
        } while (l3 != l4);
        this.index = l2;
        l3 = addAndGet(-l3);
        paramLong = l3;
      } while (l3 != 0L);
    }
  }
  
  static final class RangeSubscription
    extends FlowableRangeLong.BaseRangeSubscription
  {
    private static final long serialVersionUID = 2587302975077663557L;
    final Subscriber<? super Long> downstream;
    
    RangeSubscription(Subscriber<? super Long> paramSubscriber, long paramLong1, long paramLong2)
    {
      super(paramLong2);
      this.downstream = paramSubscriber;
    }
    
    void fastPath()
    {
      long l1 = this.end;
      Subscriber localSubscriber = this.downstream;
      for (long l2 = this.index; l2 != l1; l2 += 1L)
      {
        if (this.cancelled) {
          return;
        }
        localSubscriber.onNext(Long.valueOf(l2));
      }
      if (this.cancelled) {
        return;
      }
      localSubscriber.onComplete();
    }
    
    void slowPath(long paramLong)
    {
      long l1 = this.end;
      long l2 = this.index;
      Subscriber localSubscriber = this.downstream;
      long l3;
      do
      {
        l3 = 0L;
        long l4;
        do
        {
          while ((l3 != paramLong) && (l2 != l1))
          {
            if (this.cancelled) {
              return;
            }
            localSubscriber.onNext(Long.valueOf(l2));
            l3 += 1L;
            l2 += 1L;
          }
          if (l2 == l1)
          {
            if (!this.cancelled) {
              localSubscriber.onComplete();
            }
            return;
          }
          l4 = get();
          paramLong = l4;
        } while (l3 != l4);
        this.index = l2;
        l3 = addAndGet(-l3);
        paramLong = l3;
      } while (l3 != 0L);
    }
  }
}
