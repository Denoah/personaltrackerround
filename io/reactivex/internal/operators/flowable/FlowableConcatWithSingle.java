package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

public final class FlowableConcatWithSingle<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final SingleSource<? extends T> other;
  
  public FlowableConcatWithSingle(Flowable<T> paramFlowable, SingleSource<? extends T> paramSingleSource)
  {
    super(paramFlowable);
    this.other = paramSingleSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new ConcatWithSubscriber(paramSubscriber, this.other));
  }
  
  static final class ConcatWithSubscriber<T>
    extends SinglePostCompleteSubscriber<T, T>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = -7346385463600070225L;
    SingleSource<? extends T> other;
    final AtomicReference<Disposable> otherDisposable;
    
    ConcatWithSubscriber(Subscriber<? super T> paramSubscriber, SingleSource<? extends T> paramSingleSource)
    {
      super();
      this.other = paramSingleSource;
      this.otherDisposable = new AtomicReference();
    }
    
    public void cancel()
    {
      super.cancel();
      DisposableHelper.dispose(this.otherDisposable);
    }
    
    public void onComplete()
    {
      this.upstream = SubscriptionHelper.CANCELLED;
      SingleSource localSingleSource = this.other;
      this.other = null;
      localSingleSource.subscribe(this);
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
