package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFilterTry<T>
  extends ParallelFlowable<T>
{
  final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
  final Predicate<? super T> predicate;
  final ParallelFlowable<T> source;
  
  public ParallelFilterTry(ParallelFlowable<T> paramParallelFlowable, Predicate<? super T> paramPredicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
  {
    this.source = paramParallelFlowable;
    this.predicate = paramPredicate;
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
        arrayOfSubscriber[j] = new ParallelFilterConditionalSubscriber((ConditionalSubscriber)localSubscriber, this.predicate, this.errorHandler);
      } else {
        arrayOfSubscriber[j] = new ParallelFilterSubscriber(localSubscriber, this.predicate, this.errorHandler);
      }
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static abstract class BaseFilterSubscriber<T>
    implements ConditionalSubscriber<T>, Subscription
  {
    boolean done;
    final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    BaseFilterSubscriber(Predicate<? super T> paramPredicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      this.predicate = paramPredicate;
      this.errorHandler = paramBiFunction;
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
    extends ParallelFilterTry.BaseFilterSubscriber<T>
  {
    final ConditionalSubscriber<? super T> downstream;
    
    ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Predicate<? super T> paramPredicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      super(paramBiFunction);
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
      boolean bool1 = this.done;
      boolean bool2 = false;
      if (!bool1)
      {
        long l = 0L;
        try
        {
          boolean bool3 = this.predicate.test(paramT);
          bool1 = bool2;
          if (bool3)
          {
            bool1 = bool2;
            if (this.downstream.tryOnNext(paramT)) {
              bool1 = true;
            }
          }
          return bool1;
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
              int i = ParallelFilterTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject.ordinal()];
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
      }
      return false;
    }
  }
  
  static final class ParallelFilterSubscriber<T>
    extends ParallelFilterTry.BaseFilterSubscriber<T>
  {
    final Subscriber<? super T> downstream;
    
    ParallelFilterSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> paramBiFunction)
    {
      super(paramBiFunction);
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
      if (!this.done)
      {
        long l = 0L;
        try
        {
          boolean bool = this.predicate.test(paramT);
          if (bool)
          {
            this.downstream.onNext(paramT);
            return true;
          }
          return false;
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
              int i = ParallelFilterTry.1.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[localObject.ordinal()];
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
      }
      return false;
    }
  }
}
