package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import org.reactivestreams.Subscriber;

public final class SingleToFlowable<T>
  extends Flowable<T>
{
  final SingleSource<? extends T> source;
  
  public SingleToFlowable(SingleSource<? extends T> paramSingleSource)
  {
    this.source = paramSingleSource;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SingleToFlowableObserver(paramSubscriber));
  }
  
  static final class SingleToFlowableObserver<T>
    extends DeferredScalarSubscription<T>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = 187782011903685568L;
    Disposable upstream;
    
    SingleToFlowableObserver(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    public void cancel()
    {
      super.cancel();
      this.upstream.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      complete(paramT);
    }
  }
}
