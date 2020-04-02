package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableMaterialize<T>
  extends AbstractObservableWithUpstream<T, Notification<T>>
{
  public ObservableMaterialize(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  public void subscribeActual(Observer<? super Notification<T>> paramObserver)
  {
    this.source.subscribe(new MaterializeObserver(paramObserver));
  }
  
  static final class MaterializeObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super Notification<T>> downstream;
    Disposable upstream;
    
    MaterializeObserver(Observer<? super Notification<T>> paramObserver)
    {
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      Notification localNotification = Notification.createOnComplete();
      this.downstream.onNext(localNotification);
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      paramThrowable = Notification.createOnError(paramThrowable);
      this.downstream.onNext(paramThrowable);
      this.downstream.onComplete();
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(Notification.createOnNext(paramT));
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
