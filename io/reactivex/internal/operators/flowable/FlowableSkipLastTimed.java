package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipLastTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final int bufferSize;
  final boolean delayError;
  final Scheduler scheduler;
  final long time;
  final TimeUnit unit;
  
  public FlowableSkipLastTimed(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.time = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SkipLastTimedSubscriber(paramSubscriber, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
  }
  
  static final class SkipLastTimedSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -5677354903406201275L;
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    Throwable error;
    final SpscLinkedArrayQueue<Object> queue;
    final AtomicLong requested = new AtomicLong();
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;
    Subscription upstream;
    
    SkipLastTimedSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.time = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.delayError = paramBoolean;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber, boolean paramBoolean3)
    {
      if (this.cancelled)
      {
        this.queue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable;
        if (paramBoolean3)
        {
          if (paramBoolean2)
          {
            localThrowable = this.error;
            if (localThrowable != null) {
              paramSubscriber.onError(localThrowable);
            } else {
              paramSubscriber.onComplete();
            }
            return true;
          }
        }
        else
        {
          localThrowable = this.error;
          if (localThrowable != null)
          {
            this.queue.clear();
            paramSubscriber.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            paramSubscriber.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      boolean bool1 = this.delayError;
      TimeUnit localTimeUnit = this.unit;
      Scheduler localScheduler = this.scheduler;
      long l1 = this.time;
      int i = 1;
      int j;
      do
      {
        long l2 = this.requested.get();
        for (long l3 = 0L; l3 != l2; l3 += 1L)
        {
          boolean bool2 = this.done;
          Long localLong = (Long)localSpscLinkedArrayQueue.peek();
          boolean bool3;
          if (localLong == null) {
            bool3 = true;
          } else {
            bool3 = false;
          }
          long l4 = localScheduler.now(localTimeUnit);
          if ((!bool3) && (localLong.longValue() > l4 - l1)) {
            bool3 = true;
          }
          if (checkTerminated(bool2, bool3, localSubscriber, bool1)) {
            return;
          }
          if (bool3) {
            break;
          }
          localSpscLinkedArrayQueue.poll();
          localSubscriber.onNext(localSpscLinkedArrayQueue.poll());
        }
        if (l3 != 0L) {
          BackpressureHelper.produced(this.requested, l3);
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      long l = this.scheduler.now(this.unit);
      this.queue.offer(Long.valueOf(l), paramT);
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
}
