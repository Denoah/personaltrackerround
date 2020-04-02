package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableTakeLastOne<T>
  extends AbstractObservableWithUpstream<T, T>
{
  public ObservableTakeLastOne(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new TakeLastOneObserver(paramObserver));
  }
  
  static final class TakeLastOneObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    Disposable upstream;
    T value;
    
    TakeLastOneObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      this.value = null;
      this.upstream.dispose();
    }
    
    void emit()
    {
      Object localObject = this.value;
      if (localObject != null)
      {
        this.value = null;
        this.downstream.onNext(localObject);
      }
      this.downstream.onComplete();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      emit();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.value = paramT;
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
