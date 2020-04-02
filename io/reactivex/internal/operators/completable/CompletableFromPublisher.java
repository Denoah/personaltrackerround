package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableFromPublisher<T>
  extends Completable
{
  final Publisher<T> flowable;
  
  public CompletableFromPublisher(Publisher<T> paramPublisher)
  {
    this.flowable = paramPublisher;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.flowable.subscribe(new FromPublisherSubscriber(paramCompletableObserver));
  }
  
  static final class FromPublisherSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final CompletableObserver downstream;
    Subscription upstream;
    
    FromPublisherSubscriber(CompletableObserver paramCompletableObserver)
    {
      this.downstream = paramCompletableObserver;
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
    
    public void onNext(T paramT) {}
    
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
