package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleDelayWithPublisher<T, U>
  extends Single<T>
{
  final Publisher<U> other;
  final SingleSource<T> source;
  
  public SingleDelayWithPublisher(SingleSource<T> paramSingleSource, Publisher<U> paramPublisher)
  {
    this.source = paramSingleSource;
    this.other = paramPublisher;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    this.other.subscribe(new OtherSubscriber(paramSingleObserver, this.source));
  }
  
  static final class OtherSubscriber<T, U>
    extends AtomicReference<Disposable>
    implements FlowableSubscriber<U>, Disposable
  {
    private static final long serialVersionUID = -8565274649390031272L;
    boolean done;
    final SingleObserver<? super T> downstream;
    final SingleSource<T> source;
    Subscription upstream;
    
    OtherSubscriber(SingleObserver<? super T> paramSingleObserver, SingleSource<T> paramSingleSource)
    {
      this.downstream = paramSingleObserver;
      this.source = paramSingleSource;
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      this.upstream.cancel();
      onComplete();
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
