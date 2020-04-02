package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeat<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long count;
  
  public FlowableRepeat(Flowable<T> paramFlowable, long paramLong)
  {
    super(paramFlowable);
    this.count = paramLong;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SubscriptionArbiter localSubscriptionArbiter = new SubscriptionArbiter(false);
    paramSubscriber.onSubscribe(localSubscriptionArbiter);
    long l1 = this.count;
    long l2 = Long.MAX_VALUE;
    if (l1 != Long.MAX_VALUE) {
      l2 = l1 - 1L;
    }
    new RepeatSubscriber(paramSubscriber, l2, localSubscriptionArbiter, this.source).subscribeNext();
  }
  
  static final class RepeatSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Subscriber<? super T> downstream;
    long produced;
    long remaining;
    final SubscriptionArbiter sa;
    final Publisher<? extends T> source;
    
    RepeatSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, SubscriptionArbiter paramSubscriptionArbiter, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.sa = paramSubscriptionArbiter;
      this.source = paramPublisher;
      this.remaining = paramLong;
    }
    
    public void onComplete()
    {
      long l = this.remaining;
      if (l != Long.MAX_VALUE) {
        this.remaining = (l - 1L);
      }
      if (l != 0L) {
        subscribeNext();
      } else {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.sa.setSubscription(paramSubscription);
    }
    
    void subscribeNext()
    {
      if (getAndIncrement() == 0)
      {
        int i = 1;
        int j;
        do
        {
          if (this.sa.isCancelled()) {
            return;
          }
          long l = this.produced;
          if (l != 0L)
          {
            this.produced = 0L;
            this.sa.produced(l);
          }
          this.source.subscribe(this);
          j = addAndGet(-i);
          i = j;
        } while (j != 0);
      }
    }
  }
}
