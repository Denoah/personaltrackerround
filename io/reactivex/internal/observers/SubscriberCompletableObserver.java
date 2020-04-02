package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SubscriberCompletableObserver<T>
  implements CompletableObserver, Subscription
{
  final Subscriber<? super T> subscriber;
  Disposable upstream;
  
  public SubscriberCompletableObserver(Subscriber<? super T> paramSubscriber)
  {
    this.subscriber = paramSubscriber;
  }
  
  public void cancel()
  {
    this.upstream.dispose();
  }
  
  public void onComplete()
  {
    this.subscriber.onComplete();
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.subscriber.onError(paramThrowable);
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (DisposableHelper.validate(this.upstream, paramDisposable))
    {
      this.upstream = paramDisposable;
      this.subscriber.onSubscribe(this);
    }
  }
  
  public void request(long paramLong) {}
}
