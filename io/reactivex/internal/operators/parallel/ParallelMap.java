package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelMap<T, R>
  extends ParallelFlowable<R>
{
  final Function<? super T, ? extends R> mapper;
  final ParallelFlowable<T> source;
  
  public ParallelMap(ParallelFlowable<T> paramParallelFlowable, Function<? super T, ? extends R> paramFunction)
  {
    this.source = paramParallelFlowable;
    this.mapper = paramFunction;
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  public void subscribe(Subscriber<? super R>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    Subscriber[] arrayOfSubscriber = new Subscriber[i];
    for (int j = 0; j < i; j++)
    {
      Subscriber<? super R> localSubscriber = paramArrayOfSubscriber[j];
      if ((localSubscriber instanceof ConditionalSubscriber)) {
        arrayOfSubscriber[j] = new ParallelMapConditionalSubscriber((ConditionalSubscriber)localSubscriber, this.mapper);
      } else {
        arrayOfSubscriber[j] = new ParallelMapSubscriber(localSubscriber, this.mapper);
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static final class ParallelMapConditionalSubscriber<T, R>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final ConditionalSubscriber<? super R> downstream;
    final Function<? super T, ? extends R> mapper;
    Subscription upstream;
    
    ParallelMapConditionalSubscriber(ConditionalSubscriber<? super R> paramConditionalSubscriber, Function<? super T, ? extends R> paramFunction)
    {
      this.downstream = paramConditionalSubscriber;
      this.mapper = paramFunction;
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
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null value");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        cancel();
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
    
    public boolean tryOnNext(T paramT)
    {
      if (this.done) {
        return false;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null value");
        return this.downstream.tryOnNext(paramT);
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        cancel();
        onError(paramT);
      }
      return false;
    }
  }
  
  static final class ParallelMapSubscriber<T, R>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super R> downstream;
    final Function<? super T, ? extends R> mapper;
    Subscription upstream;
    
    ParallelMapSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends R> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
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
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null value");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        cancel();
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
