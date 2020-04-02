package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableCountSingle<T>
  extends Single<Long>
  implements FuseToFlowable<Long>
{
  final Flowable<T> source;
  
  public FlowableCountSingle(Flowable<T> paramFlowable)
  {
    this.source = paramFlowable;
  }
  
  public Flowable<Long> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableCount(this.source));
  }
  
  protected void subscribeActual(SingleObserver<? super Long> paramSingleObserver)
  {
    this.source.subscribe(new CountSubscriber(paramSingleObserver));
  }
  
  static final class CountSubscriber
    implements FlowableSubscriber<Object>, Disposable
  {
    long count;
    final SingleObserver<? super Long> downstream;
    Subscription upstream;
    
    CountSubscriber(SingleObserver<? super Long> paramSingleObserver)
    {
      this.downstream = paramSingleObserver;
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
      this.downstream.onSuccess(Long.valueOf(this.count));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.count += 1L;
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
