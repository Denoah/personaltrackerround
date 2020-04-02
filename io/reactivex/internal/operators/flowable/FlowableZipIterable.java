package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZipIterable<T, U, V>
  extends AbstractFlowableWithUpstream<T, V>
{
  final Iterable<U> other;
  final BiFunction<? super T, ? super U, ? extends V> zipper;
  
  public FlowableZipIterable(Flowable<T> paramFlowable, Iterable<U> paramIterable, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
  {
    super(paramFlowable);
    this.other = paramIterable;
    this.zipper = paramBiFunction;
  }
  
  public void subscribeActual(Subscriber<? super V> paramSubscriber)
  {
    try
    {
      Iterator localIterator = (Iterator)ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
      try
      {
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          EmptySubscription.complete(paramSubscriber);
          return;
        }
        this.source.subscribe(new ZipIterableSubscriber(paramSubscriber, localIterator, this.zipper));
        return;
      }
      finally {}
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable2);
      EmptySubscription.error(localThrowable2, paramSubscriber);
    }
  }
  
  static final class ZipIterableSubscriber<T, U, V>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super V> downstream;
    final Iterator<U> iterator;
    Subscription upstream;
    final BiFunction<? super T, ? super U, ? extends V> zipper;
    
    ZipIterableSubscriber(Subscriber<? super V> paramSubscriber, Iterator<U> paramIterator, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
    {
      this.downstream = paramSubscriber;
      this.iterator = paramIterator;
      this.zipper = paramBiFunction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    void error(Throwable paramThrowable)
    {
      Exceptions.throwIfFatal(paramThrowable);
      this.done = true;
      this.upstream.cancel();
      this.downstream.onError(paramThrowable);
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
        Object localObject = ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value");
        try
        {
          paramT = ObjectHelper.requireNonNull(this.zipper.apply(paramT, localObject), "The zipper function returned a null value");
          this.downstream.onNext(paramT);
          try
          {
            boolean bool = this.iterator.hasNext();
            if (!bool)
            {
              this.done = true;
              this.upstream.cancel();
              this.downstream.onComplete();
            }
            return;
          }
          finally {}
          paramT = finally;
        }
        finally {}
        return;
      }
      finally
      {
        error(paramT);
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
