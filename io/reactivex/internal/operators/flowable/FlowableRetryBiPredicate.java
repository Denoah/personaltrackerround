package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryBiPredicate<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final BiPredicate<? super Integer, ? super Throwable> predicate;
  
  public FlowableRetryBiPredicate(Flowable<T> paramFlowable, BiPredicate<? super Integer, ? super Throwable> paramBiPredicate)
  {
    super(paramFlowable);
    this.predicate = paramBiPredicate;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SubscriptionArbiter localSubscriptionArbiter = new SubscriptionArbiter(false);
    paramSubscriber.onSubscribe(localSubscriptionArbiter);
    new RetryBiSubscriber(paramSubscriber, this.predicate, localSubscriptionArbiter, this.source).subscribeNext();
  }
  
  static final class RetryBiSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Subscriber<? super T> downstream;
    final BiPredicate<? super Integer, ? super Throwable> predicate;
    long produced;
    int retries;
    final SubscriptionArbiter sa;
    final Publisher<? extends T> source;
    
    RetryBiSubscriber(Subscriber<? super T> paramSubscriber, BiPredicate<? super Integer, ? super Throwable> paramBiPredicate, SubscriptionArbiter paramSubscriptionArbiter, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.sa = paramSubscriptionArbiter;
      this.source = paramPublisher;
      this.predicate = paramBiPredicate;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        BiPredicate localBiPredicate = this.predicate;
        int i = this.retries + 1;
        this.retries = i;
        boolean bool = localBiPredicate.test(Integer.valueOf(i), paramThrowable);
        if (!bool)
        {
          this.downstream.onError(paramThrowable);
          return;
        }
        subscribeNext();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
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
