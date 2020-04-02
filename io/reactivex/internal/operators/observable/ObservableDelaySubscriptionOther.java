package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDelaySubscriptionOther<T, U>
  extends Observable<T>
{
  final ObservableSource<? extends T> main;
  final ObservableSource<U> other;
  
  public ObservableDelaySubscriptionOther(ObservableSource<? extends T> paramObservableSource, ObservableSource<U> paramObservableSource1)
  {
    this.main = paramObservableSource;
    this.other = paramObservableSource1;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    SequentialDisposable localSequentialDisposable = new SequentialDisposable();
    paramObserver.onSubscribe(localSequentialDisposable);
    paramObserver = new DelayObserver(localSequentialDisposable, paramObserver);
    this.other.subscribe(paramObserver);
  }
  
  final class DelayObserver
    implements Observer<U>
  {
    final Observer<? super T> child;
    boolean done;
    final SequentialDisposable serial;
    
    DelayObserver(Observer<? super T> paramObserver)
    {
      this.serial = paramObserver;
      Object localObject;
      this.child = localObject;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      ObservableDelaySubscriptionOther.this.main.subscribe(new OnComplete());
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.child.onError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      onComplete();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.serial.update(paramDisposable);
    }
    
    final class OnComplete
      implements Observer<T>
    {
      OnComplete() {}
      
      public void onComplete()
      {
        ObservableDelaySubscriptionOther.DelayObserver.this.child.onComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableDelaySubscriptionOther.DelayObserver.this.child.onError(paramThrowable);
      }
      
      public void onNext(T paramT)
      {
        ObservableDelaySubscriptionOther.DelayObserver.this.child.onNext(paramT);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        ObservableDelaySubscriptionOther.DelayObserver.this.serial.update(paramDisposable);
      }
    }
  }
}
