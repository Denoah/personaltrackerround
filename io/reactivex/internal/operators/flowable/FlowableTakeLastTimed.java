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

public final class FlowableTakeLastTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final int bufferSize;
  final long count;
  final boolean delayError;
  final Scheduler scheduler;
  final long time;
  final TimeUnit unit;
  
  public FlowableTakeLastTimed(Flowable<T> paramFlowable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.count = paramLong1;
    this.time = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new TakeLastTimedSubscriber(paramSubscriber, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
  }
  
  static final class TakeLastTimedSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -5677354903406201275L;
    volatile boolean cancelled;
    final long count;
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
    
    TakeLastTimedSubscriber(Subscriber<? super T> paramSubscriber, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.count = paramLong1;
      this.time = paramLong2;
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
    
    boolean checkTerminated(boolean paramBoolean1, Subscriber<? super T> paramSubscriber, boolean paramBoolean2)
    {
      if (this.cancelled)
      {
        this.queue.clear();
        return true;
      }
      Throwable localThrowable;
      if (paramBoolean2)
      {
        if (paramBoolean1)
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
        if (paramBoolean1)
        {
          paramSubscriber.onComplete();
          return true;
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
      int i = 1;
      int j;
      do
      {
        if (this.done)
        {
          if (checkTerminated(localSpscLinkedArrayQueue.isEmpty(), localSubscriber, bool1)) {
            return;
          }
          long l1 = this.requested.get();
          for (long l2 = 0L;; l2 += 1L)
          {
            boolean bool2;
            if (localSpscLinkedArrayQueue.peek() == null) {
              bool2 = true;
            } else {
              bool2 = false;
            }
            if (checkTerminated(bool2, localSubscriber, bool1)) {
              return;
            }
            if (l1 == l2)
            {
              if (l2 == 0L) {
                break;
              }
              BackpressureHelper.produced(this.requested, l2);
              break;
            }
            localSpscLinkedArrayQueue.poll();
            localSubscriber.onNext(localSpscLinkedArrayQueue.poll());
          }
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
      trim(this.scheduler.now(this.unit), this.queue);
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.delayError) {
        trim(this.scheduler.now(this.unit), this.queue);
      }
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      long l = this.scheduler.now(this.unit);
      localSpscLinkedArrayQueue.offer(Long.valueOf(l), paramT);
      trim(l, localSpscLinkedArrayQueue);
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
    
    void trim(long paramLong, SpscLinkedArrayQueue<Object> paramSpscLinkedArrayQueue)
    {
      long l1 = this.time;
      long l2 = this.count;
      int i;
      if (l2 == Long.MAX_VALUE) {
        i = 1;
      } else {
        i = 0;
      }
      while ((!paramSpscLinkedArrayQueue.isEmpty()) && ((((Long)paramSpscLinkedArrayQueue.peek()).longValue() < paramLong - l1) || ((i == 0) && (paramSpscLinkedArrayQueue.size() >> 1 > l2))))
      {
        paramSpscLinkedArrayQueue.poll();
        paramSpscLinkedArrayQueue.poll();
      }
    }
  }
}
