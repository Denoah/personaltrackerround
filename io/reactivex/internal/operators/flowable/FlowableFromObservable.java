package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T>
  extends Flowable<T>
{
  private final Observable<T> upstream;
  
  public FlowableFromObservable(Observable<T> paramObservable)
  {
    this.upstream = paramObservable;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.upstream.subscribe(new SubscriberObserver(paramSubscriber));
  }
  
  static final class SubscriberObserver<T>
    implements Observer<T>, Subscription
  {
    final Subscriber<? super T> downstream;
    Disposable upstream;
    
    SubscriberObserver(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
    }
    
    public void cancel()
    {
      this.upstream.dispose();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
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
      this.upstream = paramDisposable;
      this.downstream.onSubscribe(this);
    }
    
    public void request(long paramLong) {}
  }
}
