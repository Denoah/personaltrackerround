package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class AsyncSubscription
  extends AtomicLong
  implements Subscription, Disposable
{
  private static final long serialVersionUID = 7028635084060361255L;
  final AtomicReference<Subscription> actual = new AtomicReference();
  final AtomicReference<Disposable> resource = new AtomicReference();
  
  public AsyncSubscription() {}
  
  public AsyncSubscription(Disposable paramDisposable)
  {
    this();
    this.resource.lazySet(paramDisposable);
  }
  
  public void cancel()
  {
    dispose();
  }
  
  public void dispose()
  {
    SubscriptionHelper.cancel(this.actual);
    DisposableHelper.dispose(this.resource);
  }
  
  public boolean isDisposed()
  {
    boolean bool;
    if (this.actual.get() == SubscriptionHelper.CANCELLED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean replaceResource(Disposable paramDisposable)
  {
    return DisposableHelper.replace(this.resource, paramDisposable);
  }
  
  public void request(long paramLong)
  {
    SubscriptionHelper.deferredRequest(this.actual, this, paramLong);
  }
  
  public boolean setResource(Disposable paramDisposable)
  {
    return DisposableHelper.set(this.resource, paramDisposable);
  }
  
  public void setSubscription(Subscription paramSubscription)
  {
    SubscriptionHelper.deferredSetOnce(this.actual, this, paramSubscription);
  }
}
