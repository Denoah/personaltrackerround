package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class SinglePostCompleteSubscriber<T, R>
  extends AtomicLong
  implements FlowableSubscriber<T>, Subscription
{
  static final long COMPLETE_MASK = Long.MIN_VALUE;
  static final long REQUEST_MASK = Long.MAX_VALUE;
  private static final long serialVersionUID = 7917814472626990048L;
  protected final Subscriber<? super R> downstream;
  protected long produced;
  protected Subscription upstream;
  protected R value;
  
  public SinglePostCompleteSubscriber(Subscriber<? super R> paramSubscriber)
  {
    this.downstream = paramSubscriber;
  }
  
  public void cancel()
  {
    this.upstream.cancel();
  }
  
  protected final void complete(R paramR)
  {
    long l = this.produced;
    if (l != 0L) {
      BackpressureHelper.produced(this, l);
    }
    for (;;)
    {
      l = get();
      if ((l & 0x8000000000000000) != 0L)
      {
        onDrop(paramR);
        return;
      }
      if ((l & 0x7FFFFFFFFFFFFFFF) != 0L)
      {
        lazySet(-9223372036854775807L);
        this.downstream.onNext(paramR);
        this.downstream.onComplete();
        return;
      }
      this.value = paramR;
      if (compareAndSet(0L, Long.MIN_VALUE)) {
        return;
      }
      this.value = null;
    }
  }
  
  protected void onDrop(R paramR) {}
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (SubscriptionHelper.validate(this.upstream, paramSubscription))
    {
      this.upstream = paramSubscription;
      this.downstream.onSubscribe(this);
    }
  }
  
  public final void request(long paramLong)
  {
    if (SubscriptionHelper.validate(paramLong))
    {
      long l;
      do
      {
        l = get();
        if ((l & 0x8000000000000000) != 0L)
        {
          if (!compareAndSet(Long.MIN_VALUE, -9223372036854775807L)) {
            break;
          }
          this.downstream.onNext(this.value);
          this.downstream.onComplete();
          break;
        }
      } while (!compareAndSet(l, BackpressureHelper.addCap(l, paramLong)));
      this.upstream.request(paramLong);
    }
  }
}
