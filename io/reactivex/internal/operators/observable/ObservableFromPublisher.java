package io.reactivex.internal.operators.observable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class ObservableFromPublisher<T>
  extends Observable<T>
{
  final Publisher<? extends T> source;
  
  public ObservableFromPublisher(Publisher<? extends T> paramPublisher)
  {
    this.source = paramPublisher;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new PublisherSubscriber(paramObserver));
  }
  
  static final class PublisherSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final Observer<? super T> downstream;
    Subscription upstream;
    
    PublisherSubscriber(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
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
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
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
