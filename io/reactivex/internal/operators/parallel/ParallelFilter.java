package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFilter<T>
  extends ParallelFlowable<T>
{
  final Predicate<? super T> predicate;
  final ParallelFlowable<T> source;
  
  public ParallelFilter(ParallelFlowable<T> paramParallelFlowable, Predicate<? super T> paramPredicate)
  {
    this.source = paramParallelFlowable;
    this.predicate = paramPredicate;
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  public void subscribe(Subscriber<? super T>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    Subscriber[] arrayOfSubscriber = new Subscriber[i];
    for (int j = 0; j < i; j++)
    {
      Subscriber<? super T> localSubscriber = paramArrayOfSubscriber[j];
      if ((localSubscriber instanceof ConditionalSubscriber)) {
        arrayOfSubscriber[j] = new ParallelFilterConditionalSubscriber((ConditionalSubscriber)localSubscriber, this.predicate);
      } else {
        arrayOfSubscriber[j] = new ParallelFilterSubscriber(localSubscriber, this.predicate);
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static abstract class BaseFilterSubscriber<T>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    BaseFilterSubscriber(Predicate<? super T> paramPredicate)
    {
      this.predicate = paramPredicate;
    }
    
    public final void cancel()
    {
      this.upstream.cancel();
    }
    
    public final void onNext(T paramT)
    {
      if ((!tryOnNext(paramT)) && (!this.done)) {
        this.upstream.request(1L);
      }
    }
    
    public final void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
  
  static final class ParallelFilterConditionalSubscriber<T>
    extends ParallelFilter.BaseFilterSubscriber<T>
  {
    final ConditionalSubscriber<? super T> downstream;
    
    ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Predicate<? super T> paramPredicate)
    {
      super();
      this.downstream = paramConditionalSubscriber;
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
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (!this.done) {
        try
        {
          boolean bool = this.predicate.test(paramT);
          if (bool) {
            return this.downstream.tryOnNext(paramT);
          }
        }
        finally
        {
          Exceptions.throwIfFatal(paramT);
          cancel();
          onError(paramT);
        }
      }
      return false;
    }
  }
  
  static final class ParallelFilterSubscriber<T>
    extends ParallelFilter.BaseFilterSubscriber<T>
  {
    final Subscriber<? super T> downstream;
    
    ParallelFilterSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate)
    {
      super();
      this.downstream = paramSubscriber;
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
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (!this.done) {
        try
        {
          boolean bool = this.predicate.test(paramT);
          if (bool)
          {
            this.downstream.onNext(paramT);
            return true;
          }
        }
        finally
        {
          Exceptions.throwIfFatal(paramT);
          cancel();
          onError(paramT);
        }
      }
      return false;
    }
  }
}
