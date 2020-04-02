package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableLastMaybe<T>
  extends Maybe<T>
{
  final Publisher<T> source;
  
  public FlowableLastMaybe(Publisher<T> paramPublisher)
  {
    this.source = paramPublisher;
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new LastSubscriber(paramMaybeObserver));
  }
  
  static final class LastSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final MaybeObserver<? super T> downstream;
    T item;
    Subscription upstream;
    
    LastSubscriber(MaybeObserver<? super T> paramMaybeObserver)
    {
      this.downstream = paramMaybeObserver;
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      this.upstream = SubscriptionHelper.CANCELLED;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.upstream == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      this.upstream = SubscriptionHelper.CANCELLED;
      Object localObject = this.item;
      if (localObject != null)
      {
        this.item = null;
        this.downstream.onSuccess(localObject);
      }
      else
      {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = SubscriptionHelper.CANCELLED;
      this.item = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.item = paramT;
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
  }
}
