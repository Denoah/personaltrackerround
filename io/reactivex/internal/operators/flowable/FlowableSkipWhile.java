package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipWhile<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public FlowableSkipWhile(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SkipWhileSubscriber(paramSubscriber, this.predicate));
  }
  
  static final class SkipWhileSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    final Subscriber<? super T> downstream;
    boolean notSkipping;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    SkipWhileSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramSubscriber;
      this.predicate = paramPredicate;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.notSkipping) {
        this.downstream.onNext(paramT);
      }
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (bool)
        {
          this.upstream.request(1L);
        }
        else
        {
          this.notSkipping = true;
          this.downstream.onNext(paramT);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        this.downstream.onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
