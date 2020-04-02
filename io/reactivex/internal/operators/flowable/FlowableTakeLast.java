package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeLast<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final int count;
  
  public FlowableTakeLast(Flowable<T> paramFlowable, int paramInt)
  {
    super(paramFlowable);
    this.count = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new TakeLastSubscriber(paramSubscriber, this.count));
  }
  
  static final class TakeLastSubscriber<T>
    extends ArrayDeque<T>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 7240042530241604978L;
    volatile boolean cancelled;
    final int count;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    final AtomicLong requested = new AtomicLong();
    Subscription upstream;
    final AtomicInteger wip = new AtomicInteger();
    
    TakeLastSubscriber(Subscriber<? super T> paramSubscriber, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.count = paramInt;
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
    }
    
    void drain()
    {
      if (this.wip.getAndIncrement() == 0)
      {
        Subscriber localSubscriber = this.downstream;
        long l1 = this.requested.get();
        do
        {
          if (this.cancelled) {
            return;
          }
          long l2 = l1;
          if (this.done)
          {
            for (long l3 = 0L; l3 != l1; l3 += 1L)
            {
              if (this.cancelled) {
                return;
              }
              Object localObject = poll();
              if (localObject == null)
              {
                localSubscriber.onComplete();
                return;
              }
              localSubscriber.onNext(localObject);
            }
            l2 = l1;
            if (l3 != 0L)
            {
              l2 = l1;
              if (l1 != Long.MAX_VALUE) {
                l2 = this.requested.addAndGet(-l3);
              }
            }
          }
          l1 = l2;
        } while (this.wip.decrementAndGet() != 0);
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.count == size()) {
        poll();
      }
      offer(paramT);
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
