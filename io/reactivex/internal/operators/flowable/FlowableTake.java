package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTake<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long limit;
  
  public FlowableTake(Flowable<T> paramFlowable, long paramLong)
  {
    super(paramFlowable);
    this.limit = paramLong;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new TakeSubscriber(paramSubscriber, this.limit));
  }
  
  static final class TakeSubscriber<T>
    extends AtomicBoolean
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -5636543848937116287L;
    boolean done;
    final Subscriber<? super T> downstream;
    final long limit;
    long remaining;
    Subscription upstream;
    
    TakeSubscriber(Subscriber<? super T> paramSubscriber, long paramLong)
    {
      this.downstream = paramSubscriber;
      this.limit = paramLong;
      this.remaining = paramLong;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done)
      {
        this.done = true;
        this.upstream.cancel();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.done)
      {
        long l1 = this.remaining;
        long l2 = l1 - 1L;
        this.remaining = l2;
        if (l1 > 0L)
        {
          int i;
          if (l2 == 0L) {
            i = 1;
          } else {
            i = 0;
          }
          this.downstream.onNext(paramT);
          if (i != 0)
          {
            this.upstream.cancel();
            onComplete();
          }
        }
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if (this.limit == 0L)
        {
          paramSubscription.cancel();
          this.done = true;
          EmptySubscription.complete(this.downstream);
        }
        else
        {
          this.downstream.onSubscribe(this);
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (!SubscriptionHelper.validate(paramLong)) {
        return;
      }
      if ((!get()) && (compareAndSet(false, true)) && (paramLong >= this.limit))
      {
        this.upstream.request(Long.MAX_VALUE);
        return;
      }
      this.upstream.request(paramLong);
    }
  }
}
