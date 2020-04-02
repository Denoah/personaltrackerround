package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

public final class FlowableConcatWithMaybe<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final MaybeSource<? extends T> other;
  
  public FlowableConcatWithMaybe(Flowable<T> paramFlowable, MaybeSource<? extends T> paramMaybeSource)
  {
    super(paramFlowable);
    this.other = paramMaybeSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ConcatWithSubscriber(paramSubscriber, this.other));
  }
  
  static final class ConcatWithSubscriber<T>
    extends SinglePostCompleteSubscriber<T, T>
    implements MaybeObserver<T>
  {
    private static final long serialVersionUID = -7346385463600070225L;
    boolean inMaybe;
    MaybeSource<? extends T> other;
    final AtomicReference<Disposable> otherDisposable;
    
    ConcatWithSubscriber(Subscriber<? super T> paramSubscriber, MaybeSource<? extends T> paramMaybeSource)
    {
      super();
      this.other = paramMaybeSource;
      this.otherDisposable = new AtomicReference();
    }
    
    public void cancel()
    {
      super.cancel();
      DisposableHelper.dispose(this.otherDisposable);
    }
    
    public void onComplete()
    {
      if (this.inMaybe)
      {
        this.downstream.onComplete();
      }
      else
      {
        this.inMaybe = true;
        this.upstream = SubscriptionHelper.CANCELLED;
        MaybeSource localMaybeSource = this.other;
        this.other = null;
        localMaybeSource.subscribe(this);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.otherDisposable, paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      complete(paramT);
    }
  }
}
