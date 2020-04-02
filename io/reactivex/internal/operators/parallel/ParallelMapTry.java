package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelMapTry<T, R>
  extends ParallelFlowable<R>
{
  final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
  final Function<? super T, ? extends R> mapper;
  final ParallelFlowable<T> source;
  
  public ParallelMapTry(ParallelFlowable<T> paramParallelFlowable, Function<? super T, ? extends R> paramFunction, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
  {
    this.source = paramParallelFlowable;
    this.mapper = paramFunction;
    this.errorHandler = paramBiFunction;
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
        arrayOfSubscriber[j] = new ParallelMapTryConditionalSubscriber((ConditionalSubscriber)localSubscriber, this.mapper, this.errorHandler);
      } else {
        arrayOfSubscriber[j] = new ParallelMapTrySubscriber(localSubscriber, this.mapper, this.errorHandler);
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static final class ParallelMapTryConditionalSubscriber<T, R>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final ConditionalSubscriber<? super R> downstream;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Function<? super T, ? extends R> mapper;
    Subscription upstream;
    
    ParallelMapTryConditionalSubscriber(ConditionalSubscriber<? super R> paramConditionalSubscriber, Function<? super T, ? extends R> paramFunction, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      this.downstream = paramConditionalSubscriber;
      this.mapper = paramFunction;
      this.errorHandler = paramBiFunction;
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
      if ((!tryOnNext(paramT)) && (!this.done)) {
        this.upstream.request(1L);
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
      long l = 0L;
      try
      {
        Object localObject1 = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null value");
        return this.downstream.tryOnNext(localObject1);
      }
      finally
      {
        for (;;)
        {
          Exceptions.throwIfFatal(localThrowable);
          try
          {
            Object localObject2 = this.errorHandler;
            l += 1L;
            localObject2 = (ParallelFailureHandling)ObjectHelper.requireNonNull(((BiFunction)localObject2).apply(Long.valueOf(l), localThrowable), "The errorHandler returned a null item");
            int i = ParallelMapTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject2.ordinal()];
            if (i != 1)
            {
              if (i != 2)
              {
                if (i != 3)
                {
                  cancel();
                  onError(localThrowable);
                  return false;
                }
                cancel();
                onComplete();
              }
              return false;
            }
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            cancel();
            onError(new CompositeException(new Throwable[] { localThrowable, paramT }));
          }
        }
      }
      return false;
    }
  }
  
  static final class ParallelMapTrySubscriber<T, R>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super R> downstream;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Function<? super T, ? extends R> mapper;
    Subscription upstream;
    
    ParallelMapTrySubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends R> paramFunction, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.errorHandler = paramBiFunction;
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
      if ((!tryOnNext(paramT)) && (!this.done)) {
        this.upstream.request(1L);
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
      long l = 0L;
      try
      {
        Object localObject1 = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null value");
        this.downstream.onNext(localObject1);
        return true;
      }
      finally
      {
        for (;;)
        {
          Exceptions.throwIfFatal(localThrowable);
          try
          {
            Object localObject2 = this.errorHandler;
            l += 1L;
            localObject2 = (ParallelFailureHandling)ObjectHelper.requireNonNull(((BiFunction)localObject2).apply(Long.valueOf(l), localThrowable), "The errorHandler returned a null item");
            int i = ParallelMapTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject2.ordinal()];
            if (i != 1)
            {
              if (i != 2)
              {
                if (i != 3)
                {
                  cancel();
                  onError(localThrowable);
                  return false;
                }
                cancel();
                onComplete();
              }
              return false;
            }
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            cancel();
            onError(new CompositeException(new Throwable[] { localThrowable, paramT }));
          }
        }
      }
      return false;
    }
  }
}
