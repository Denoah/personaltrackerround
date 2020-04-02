package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelDoOnNextTry<T>
  extends ParallelFlowable<T>
{
  final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
  final Consumer<? super T> onNext;
  final ParallelFlowable<T> source;
  
  public ParallelDoOnNextTry(ParallelFlowable<T> paramParallelFlowable, Consumer<? super T> paramConsumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
  {
    this.source = paramParallelFlowable;
    this.onNext = paramConsumer;
    this.errorHandler = paramBiFunction;
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
        arrayOfSubscriber[j] = new ParallelDoOnNextConditionalSubscriber((ConditionalSubscriber)localSubscriber, this.onNext, this.errorHandler);
      } else {
        arrayOfSubscriber[j] = new ParallelDoOnNextSubscriber(localSubscriber, this.onNext, this.errorHandler);
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static final class ParallelDoOnNextConditionalSubscriber<T>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final ConditionalSubscriber<? super T> downstream;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Consumer<? super T> onNext;
    Subscription upstream;
    
    ParallelDoOnNextConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Consumer<? super T> paramConsumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      this.downstream = paramConditionalSubscriber;
      this.onNext = paramConsumer;
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
        this.onNext.accept(paramT);
        return this.downstream.tryOnNext(paramT);
      }
      finally
      {
        for (;;)
        {
          Exceptions.throwIfFatal(localThrowable);
          try
          {
            Object localObject = this.errorHandler;
            l += 1L;
            localObject = (ParallelFailureHandling)ObjectHelper.requireNonNull(((BiFunction)localObject).apply(Long.valueOf(l), localThrowable), "The errorHandler returned a null item");
            int i = ParallelDoOnNextTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject.ordinal()];
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
  
  static final class ParallelDoOnNextSubscriber<T>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super T> downstream;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Consumer<? super T> onNext;
    Subscription upstream;
    
    ParallelDoOnNextSubscriber(Subscriber<? super T> paramSubscriber, Consumer<? super T> paramConsumer, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.onNext = paramConsumer;
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
      if (!tryOnNext(paramT)) {
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
        this.onNext.accept(paramT);
        this.downstream.onNext(paramT);
        return true;
      }
      finally
      {
        for (;;)
        {
          Exceptions.throwIfFatal(localThrowable);
          try
          {
            Object localObject = this.errorHandler;
            l += 1L;
            localObject = (ParallelFailureHandling)ObjectHelper.requireNonNull(((BiFunction)localObject).apply(Long.valueOf(l), localThrowable), "The errorHandler returned a null item");
            int i = ParallelDoOnNextTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject.ordinal()];
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
