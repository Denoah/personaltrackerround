package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryPredicate<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long count;
  final Predicate<? super Throwable> predicate;
  
  public FlowableRetryPredicate(Flowable<T> paramFlowable, long paramLong, Predicate<? super Throwable> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
    this.count = paramLong;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    SubscriptionArbiter localSubscriptionArbiter = new SubscriptionArbiter(false);
    paramSubscriber.onSubscribe(localSubscriptionArbiter);
    new RetrySubscriber(paramSubscriber, this.count, this.predicate, localSubscriptionArbiter, this.source).subscribeNext();
  }
  
  static final class RetrySubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -7098360935104053232L;
    final Subscriber<? super T> downstream;
    final Predicate<? super Throwable> predicate;
    long produced;
    long remaining;
    final SubscriptionArbiter sa;
    final Publisher<? extends T> source;
    
    RetrySubscriber(Subscriber<? super T> paramSubscriber, long paramLong, Predicate<? super Throwable> paramPredicate, SubscriptionArbiter paramSubscriptionArbiter, Publisher<? extends T> paramPublisher)
    {
      this.downstream = paramSubscriber;
      this.sa = paramSubscriptionArbiter;
      this.source = paramPublisher;
      this.predicate = paramPredicate;
      this.remaining = paramLong;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      long l = this.remaining;
      if (l != Long.MAX_VALUE) {
        this.remaining = (l - 1L);
      }
      if (l == 0L) {
        this.downstream.onError(paramThrowable);
      }
      try
      {
        boolean bool = this.predicate.test(paramThrowable);
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
