package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

public final class FlowableSingleSingle<T>
  extends Single<T>
  implements FuseToFlowable<T>
{
  final T defaultValue;
  final Flowable<T> source;
  
  public FlowableSingleSingle(Flowable<T> paramFlowable, T paramT)
  {
    this.source = paramFlowable;
    this.defaultValue = paramT;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableSingle(this.source, this.defaultValue, true));
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new SingleElementSubscriber(paramSingleObserver, this.defaultValue));
  }
  
  static final class SingleElementSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    final T defaultValue;
    boolean done;
    final SingleObserver<? super T> downstream;
    Subscription upstream;
    T value;
    
    SingleElementSubscriber(SingleObserver<? super T> paramSingleObserver, T paramT)
    {
      this.downstream = paramSingleObserver;
      this.defaultValue = paramT;
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
      Object localObject1 = this.value;
      this.value = null;
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = this.defaultValue;
      }
      if (localObject2 != null) {
        this.downstream.onSuccess(localObject2);
      } else {
        this.downstream.onError(new NoSuchElementException());
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
