package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAmb<T>
  extends Flowable<T>
{
  final Publisher<? extends T>[] sources;
  final Iterable<? extends Publisher<? extends T>> sourcesIterable;
  
  public FlowableAmb(Publisher<? extends T>[] paramArrayOfPublisher, Iterable<? extends Publisher<? extends T>> paramIterable)
  {
    this.sources = paramArrayOfPublisher;
    this.sourcesIterable = paramIterable;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject1 = this.sources;
    int j;
    if (localObject1 == null)
    {
      Object localObject2 = new Publisher[8];
      try
      {
        Iterator localIterator = this.sourcesIterable.iterator();
        int i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          Publisher localPublisher = (Publisher)localIterator.next();
          if (localPublisher == null)
          {
            localObject2 = new java/lang/NullPointerException;
            ((NullPointerException)localObject2).<init>("One of the sources is null");
            EmptySubscription.error((Throwable)localObject2, paramSubscriber);
            return;
          }
          localObject1 = localObject2;
          if (i == localObject2.length)
          {
            localObject1 = new Publisher[(i >> 2) + i];
            System.arraycopy(localObject2, 0, localObject1, 0, i);
          }
          localObject1[i] = localPublisher;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptySubscription.error(localThrowable, paramSubscriber);
        return;
      }
    }
    if (j == 0)
    {
      EmptySubscription.complete(paramSubscriber);
      return;
    }
    if (j == 1)
    {
      localObject1[0].subscribe(paramSubscriber);
      return;
    }
    new AmbCoordinator(paramSubscriber, j).subscribe((Publisher[])localObject1);
  }
  
  static final class AmbCoordinator<T>
    implements Subscription
  {
    final Subscriber<? super T> downstream;
    final FlowableAmb.AmbInnerSubscriber<T>[] subscribers;
    final AtomicInteger winner = new AtomicInteger();
    
    AmbCoordinator(Subscriber<? super T> paramSubscriber, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.subscribers = new FlowableAmb.AmbInnerSubscriber[paramInt];
    }
    
    public void cancel()
    {
      if (this.winner.get() != -1)
      {
        this.winner.lazySet(-1);
        FlowableAmb.AmbInnerSubscriber[] arrayOfAmbInnerSubscriber = this.subscribers;
        int i = arrayOfAmbInnerSubscriber.length;
        for (int j = 0; j < i; j++) {
          arrayOfAmbInnerSubscriber[j].cancel();
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        int i = this.winner.get();
        if (i > 0)
        {
          this.subscribers[(i - 1)].request(paramLong);
        }
        else if (i == 0)
        {
          FlowableAmb.AmbInnerSubscriber[] arrayOfAmbInnerSubscriber = this.subscribers;
          int j = arrayOfAmbInnerSubscriber.length;
          for (i = 0; i < j; i++) {
            arrayOfAmbInnerSubscriber[i].request(paramLong);
          }
        }
      }
    }
    
    public void subscribe(Publisher<? extends T>[] paramArrayOfPublisher)
    {
      FlowableAmb.AmbInnerSubscriber[] arrayOfAmbInnerSubscriber = this.subscribers;
      int i = arrayOfAmbInnerSubscriber.length;
      int j = 0;
      int m;
      for (int k = 0; k < i; k = m)
      {
        m = k + 1;
        arrayOfAmbInnerSubscriber[k] = new FlowableAmb.AmbInnerSubscriber(this, m, this.downstream);
      }
      this.winner.lazySet(0);
      this.downstream.onSubscribe(this);
      for (k = j; k < i; k++)
      {
        if (this.winner.get() != 0) {
          return;
        }
        paramArrayOfPublisher[k].subscribe(arrayOfAmbInnerSubscriber[k]);
      }
    }
    
    public boolean win(int paramInt)
    {
      int i = this.winner.get();
      int j = 0;
      if ((i == 0) && (this.winner.compareAndSet(0, paramInt)))
      {
        FlowableAmb.AmbInnerSubscriber[] arrayOfAmbInnerSubscriber = this.subscribers;
        int k = arrayOfAmbInnerSubscriber.length;
        while (j < k)
        {
          i = j + 1;
          if (i != paramInt) {
            arrayOfAmbInnerSubscriber[j].cancel();
          }
          j = i;
        }
        return true;
      }
      return false;
    }
  }
  
  static final class AmbInnerSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -1185974347409665484L;
    final Subscriber<? super T> downstream;
    final int index;
    final AtomicLong missedRequested = new AtomicLong();
    final FlowableAmb.AmbCoordinator<T> parent;
    boolean won;
    
    AmbInnerSubscriber(FlowableAmb.AmbCoordinator<T> paramAmbCoordinator, int paramInt, Subscriber<? super T> paramSubscriber)
    {
      this.parent = paramAmbCoordinator;
      this.index = paramInt;
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      if (this.won)
      {
        this.downstream.onComplete();
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onComplete();
      }
      else
      {
        ((Subscription)get()).cancel();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.won)
      {
        this.downstream.onError(paramThrowable);
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onError(paramThrowable);
      }
      else
      {
        ((Subscription)get()).cancel();
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (this.won)
      {
        this.downstream.onNext(paramT);
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onNext(paramT);
      }
      else
      {
        ((Subscription)get()).cancel();
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this, this.missedRequested, paramSubscription);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this, this.missedRequested, paramLong);
    }
  }
}
