package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeWhile<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public FlowableTakeWhile(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new TakeWhileSubscriber(paramSubscriber, this.predicate));
  }
  
  static final class TakeWhileSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super T> downstream;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    TakeWhileSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate)
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (!bool)
        {
          this.done = true;
          this.upstream.cancel();
          this.downstream.onComplete();
          return;
        }
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
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
