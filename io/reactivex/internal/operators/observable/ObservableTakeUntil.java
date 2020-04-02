package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTakeUntil<T, U>
  extends AbstractObservableWithUpstream<T, T>
{
  final ObservableSource<? extends U> other;
  
  public ObservableTakeUntil(ObservableSource<T> paramObservableSource, ObservableSource<? extends U> paramObservableSource1)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    TakeUntilMainObserver localTakeUntilMainObserver = new TakeUntilMainObserver(paramObserver);
    paramObserver.onSubscribe(localTakeUntilMainObserver);
    this.other.subscribe(localTakeUntilMainObserver.otherObserver);
    this.source.subscribe(localTakeUntilMainObserver);
  }
  
  static final class TakeUntilMainObserver<T, U>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 1418547743690811973L;
    final Observer<? super T> downstream;
    final AtomicThrowable error;
    final TakeUntilMainObserver<T, U>.OtherObserver otherObserver;
    final AtomicReference<Disposable> upstream;
    
    TakeUntilMainObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
      this.upstream = new AtomicReference();
      this.otherObserver = new OtherObserver();
      this.error = new AtomicThrowable();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this.otherObserver);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this.otherObserver);
      HalfSerializer.onComplete(this.downstream, this, this.error);
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.otherObserver);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void onNext(T paramT)
    {
      HalfSerializer.onNext(this.downstream, paramT, this, this.error);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    void otherComplete()
    {
      DisposableHelper.dispose(this.upstream);
      HalfSerializer.onComplete(this.downstream, this, this.error);
    }
    
    void otherError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.upstream);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    final class OtherObserver
      extends AtomicReference<Disposable>
      implements Observer<U>
    {
      private static final long serialVersionUID = -8693423678067375039L;
      
      OtherObserver() {}
      
      public void onComplete()
      {
        ObservableTakeUntil.TakeUntilMainObserver.this.otherComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableTakeUntil.TakeUntilMainObserver.this.otherError(paramThrowable);
      }
      
      public void onNext(U paramU)
      {
        DisposableHelper.dispose(this);
        ObservableTakeUntil.TakeUntilMainObserver.this.otherComplete();
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
