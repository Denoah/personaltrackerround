package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableAllSingle<T>
  extends Single<Boolean>
  implements FuseToFlowable<Boolean>
{
  final Predicate<? super T> predicate;
  final Flowable<T> source;
  
  public FlowableAllSingle(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    this.source = paramFlowable;
    this.predicate = paramPredicate;
  }
  
  public Flowable<Boolean> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableAll(this.source, this.predicate));
  }
  
  protected void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    this.source.subscribe(new AllSubscriber(paramSingleObserver, this.predicate));
  }
  
  static final class AllSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    boolean done;
    final SingleObserver<? super Boolean> downstream;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    AllSubscriber(SingleObserver<? super Boolean> paramSingleObserver, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramSingleObserver;
      this.predicate = paramPredicate;
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onSuccess(Boolean.valueOf(true));
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      try
      {
        boolean bool = this.predicate.test(paramT);
        if (!bool)
        {
          this.done = true;
          this.upstream.cancel();
          this.upstream = SubscriptionHelper.CANCELLED;
          this.downstream.onSuccess(Boolean.valueOf(false));
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        this.upstream = SubscriptionHelper.CANCELLED;
        onError(paramT);
      }
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
