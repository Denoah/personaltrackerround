package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatWithCompletable<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final CompletableSource other;
  
  public ObservableConcatWithCompletable(Observable<T> paramObservable, CompletableSource paramCompletableSource)
  {
    super(paramObservable);
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ConcatWithObserver(paramObserver, this.other));
  }
  
  static final class ConcatWithObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, CompletableObserver, Disposable
  {
    private static final long serialVersionUID = -1953724749712440952L;
    final Observer<? super T> downstream;
    boolean inCompletable;
    CompletableSource other;
    
    ConcatWithObserver(Observer<? super T> paramObserver, CompletableSource paramCompletableSource)
    {
      this.downstream = paramObserver;
      this.other = paramCompletableSource;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
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
        DisposableHelper.replace(this, null);
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
      if ((DisposableHelper.setOnce(this, paramDisposable)) && (!this.inCompletable)) {
        this.downstream.onSubscribe(this);
      }
    }
  }
}
