package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableIgnoreElementsCompletable<T>
  extends Completable
  implements FuseToFlowable<T>
{
  final Flowable<T> source;
  
  public FlowableIgnoreElementsCompletable(Flowable<T> paramFlowable)
  {
    this.source = paramFlowable;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableIgnoreElements(this.source));
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new IgnoreElementsSubscriber(paramCompletableObserver));
  }
  
  static final class IgnoreElementsSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final CompletableObserver downstream;
    Subscription upstream;
    
    IgnoreElementsSubscriber(CompletableObserver paramCompletableObserver)
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
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = SubscriptionHelper.CANCELLED;
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
