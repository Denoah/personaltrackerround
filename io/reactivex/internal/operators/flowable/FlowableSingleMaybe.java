package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableSingleMaybe<T>
  extends Maybe<T>
  implements FuseToFlowable<T>
{
  final Flowable<T> source;
  
  public FlowableSingleMaybe(Flowable<T> paramFlowable)
  {
    this.source = paramFlowable;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableSingle(this.source, null, false));
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new SingleElementSubscriber(paramMaybeObserver));
  }
  
  static final class SingleElementSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    boolean done;
    final MaybeObserver<? super T> downstream;
    Subscription upstream;
    T value;
    
    SingleElementSubscriber(MaybeObserver<? super T> paramMaybeObserver)
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.upstream = SubscriptionHelper.CANCELLED;
      Object localObject = this.value;
      this.value = null;
      if (localObject == null) {
        this.downstream.onComplete();
      } else {
        this.downstream.onSuccess(localObject);
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
      this.upstream = SubscriptionHelper.CANCELLED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.value != null)
      {
        this.done = true;
        this.upstream.cancel();
        this.upstream = SubscriptionHelper.CANCELLED;
        this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
        return;
      }
      this.value = paramT;
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
