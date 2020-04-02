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

public final class FlowableElementAtSingle<T>
  extends Single<T>
  implements FuseToFlowable<T>
{
  final T defaultValue;
  final long index;
  final Flowable<T> source;
  
  public FlowableElementAtSingle(Flowable<T> paramFlowable, long paramLong, T paramT)
  {
    this.source = paramFlowable;
    this.index = paramLong;
    this.defaultValue = paramT;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableElementAt(this.source, this.index, this.defaultValue, true));
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.source.subscribe(new ElementAtSubscriber(paramSingleObserver, this.index, this.defaultValue));
  }
  
  static final class ElementAtSubscriber<T>
    implements FlowableSubscriber<T>, Disposable
  {
    long count;
    final T defaultValue;
    boolean done;
    final SingleObserver<? super T> downstream;
    final long index;
    Subscription upstream;
    
    ElementAtSubscriber(SingleObserver<? super T> paramSingleObserver, long paramLong, T paramT)
    {
      this.downstream = paramSingleObserver;
      this.index = paramLong;
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
      this.upstream = SubscriptionHelper.CANCELLED;
      if (!this.done)
      {
        this.done = true;
        Object localObject = this.defaultValue;
        if (localObject != null) {
          this.downstream.onSuccess(localObject);
        } else {
          this.downstream.onError(new NoSuchElementException());
        }
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
