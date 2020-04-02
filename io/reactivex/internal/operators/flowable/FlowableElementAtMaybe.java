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

public final class FlowableElementAtMaybe<T>
  extends Maybe<T>
  implements FuseToFlowable<T>
{
  final long index;
  final Flowable<T> source;
  
  public FlowableElementAtMaybe(Flowable<T> paramFlowable, long paramLong)
  {
    this.source = paramFlowable;
    this.index = paramLong;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableElementAt(this.source, this.index, null, false));
  }
  
  protected void subscribeActual(MaybeObserver<? super T> paramMaybeObserver)
  {
    this.source.subscribe(new ElementAtSubscriber(paramMaybeObserver, this.index));
  }
  
  static final class ElementAtSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    long count;
    boolean done;
    final MaybeObserver<? super T> downstream;
    final long index;
    Subscription upstream;
    
    ElementAtSubscriber(MaybeObserver<? super T> paramMaybeObserver, long paramLong)
    {
      this.downstream = paramMaybeObserver;
      this.index = paramLong;
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
      if (!this.done)
      {
        this.done = true;
        this.downstream.onComplete();
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
      long l = this.count;
      if (l == this.index)
      {
        this.done = true;
        this.upstream.cancel();
        this.upstream = SubscriptionHelper.CANCELLED;
        this.downstream.onSuccess(paramT);
        return;
      }
      this.count = (l + 1L);
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
