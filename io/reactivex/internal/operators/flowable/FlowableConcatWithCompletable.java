package io.reactivex.internal.operators.flowable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatWithCompletable<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final CompletableSource other;
  
  public FlowableConcatWithCompletable(Flowable<T> paramFlowable, CompletableSource paramCompletableSource)
  {
    super(paramFlowable);
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ConcatWithSubscriber(paramSubscriber, this.other));
  }
  
  static final class ConcatWithSubscriber<T>
    extends AtomicReference<Disposable>
    implements FlowableSubscriber<T>, CompletableObserver, Subscription
  {
    private static final long serialVersionUID = -7346385463600070225L;
    final Subscriber<? super T> downstream;
    boolean inCompletable;
    CompletableSource other;
    Subscription upstream;
    
    ConcatWithSubscriber(Subscriber<? super T> paramSubscriber, CompletableSource paramCompletableSource)
    {
      this.downstream = paramSubscriber;
      this.other = paramCompletableSource;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      if (this.inCompletable)
      {
        this.downstream.onComplete();
      }
      else
      {
        this.inCompletable = true;
        this.upstream = SubscriptionHelper.CANCELLED;
        CompletableSource localCompletableSource = this.other;
        this.other = null;
        localCompletableSource.subscribe(this);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
