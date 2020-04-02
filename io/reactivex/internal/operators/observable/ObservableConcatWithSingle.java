package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatWithSingle<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final SingleSource<? extends T> other;
  
  public ObservableConcatWithSingle(Observable<T> paramObservable, SingleSource<? extends T> paramSingleSource)
  {
    super(paramObservable);
    this.other = paramSingleSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ConcatWithObserver(paramObserver, this.other));
  }
  
  static final class ConcatWithObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, SingleObserver<T>, Disposable
  {
    private static final long serialVersionUID = -1953724749712440952L;
    final Observer<? super T> downstream;
    boolean inSingle;
    SingleSource<? extends T> other;
    
    ConcatWithObserver(Observer<? super T> paramObserver, SingleSource<? extends T> paramSingleSource)
    {
      this.downstream = paramObserver;
      this.other = paramSingleSource;
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
      this.inSingle = true;
      DisposableHelper.replace(this, null);
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
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if ((DisposableHelper.setOnce(this, paramDisposable)) && (!this.inSingle)) {
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
