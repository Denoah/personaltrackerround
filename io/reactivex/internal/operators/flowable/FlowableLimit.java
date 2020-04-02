package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableLimit<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long n;
  
  public FlowableLimit(Flowable<T> paramFlowable, long paramLong)
  {
    super(paramFlowable);
    this.n = paramLong;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new LimitSubscriber(paramSubscriber, this.n));
  }
  
  static final class LimitSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 2288246011222124525L;
    final Subscriber<? super T> downstream;
    long remaining;
    Subscription upstream;
    
    LimitSubscriber(Subscriber<? super T> paramSubscriber, long paramLong)
    {
      this.downstream = paramSubscriber;
      this.remaining = paramLong;
      lazySet(paramLong);
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.remaining > 0L)
      {
        this.remaining = 0L;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.remaining > 0L)
      {
        this.remaining = 0L;
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l = this.remaining;
      if (l > 0L)
      {
        l -= 1L;
        this.remaining = l;
        this.downstream.onNext(paramT);
        if (l == 0L)
        {
          this.upstream.cancel();
          this.downstream.onComplete();
        }
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription)) {
        if (this.remaining == 0L)
        {
          paramSubscription.cancel();
          EmptySubscription.complete(this.downstream);
        }
        else
        {
          this.upstream = paramSubscription;
          this.downstream.onSubscribe(this);
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        long l1;
        long l2;
        do
        {
          l1 = get();
          if (l1 == 0L) {
            break;
          }
          if (l1 <= paramLong) {
            l2 = l1;
          } else {
            l2 = paramLong;
          }
        } while (!compareAndSet(l1, l1 - l2));
        this.upstream.request(l2);
      }
    }
  }
}
