package io.reactivex.internal.operators.observable;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatWithMaybe<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final MaybeSource<? extends T> other;
  
  public ObservableConcatWithMaybe(Observable<T> paramObservable, MaybeSource<? extends T> paramMaybeSource)
  {
    super(paramObservable);
    this.other = paramMaybeSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ConcatWithObserver(paramObserver, this.other));
  }
  
  static final class ConcatWithObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, MaybeObserver<T>, Disposable
  {
    private static final long serialVersionUID = -1953724749712440952L;
    final Observer<? super T> downstream;
    boolean inMaybe;
    MaybeSource<? extends T> other;
    
    ConcatWithObserver(Observer<? super T> paramObserver, MaybeSource<? extends T> paramMaybeSource)
    {
      this.downstream = paramObserver;
      this.other = paramMaybeSource;
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
      if (this.inMaybe)
      {
        this.downstream.onComplete();
      }
      else
      {
        this.inMaybe = true;
        DisposableHelper.replace(this, null);
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
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if ((DisposableHelper.setOnce(this, paramDisposable)) && (!this.inMaybe)) {
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      this.downstream.onNext(paramT);
      this.downstream.onComplete();
    }
  }
}
