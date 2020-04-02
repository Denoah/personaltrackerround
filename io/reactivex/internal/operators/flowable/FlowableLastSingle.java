package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableLastSingle<T>
  extends Single<T>
{
  final T defaultItem;
  final Publisher<T> source;
  
  public FlowableLastSingle(Publisher<T> paramPublisher, T paramT)
  {
    this.source = paramPublisher;
    this.defaultItem = paramT;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new LastSubscriber(paramSingleObserver, this.defaultItem));
  }
  
  static final class LastSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final T defaultItem;
    final SingleObserver<? super T> downstream;
    T item;
    Subscription upstream;
    
    LastSubscriber(SingleObserver<? super T> paramSingleObserver, T paramT)
    {
      this.downstream = paramSingleObserver;
      this.defaultItem = paramT;
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
        localObject = this.defaultItem;
        if (localObject != null) {
          this.downstream.onSuccess(localObject);
        } else {
          this.downstream.onError(new NoSuchElementException());
        }
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
