package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EmptyComponent;

public final class ObservableDetach<T>
  extends AbstractObservableWithUpstream<T, T>
{
  public ObservableDetach(ObservableSource<T> paramObservableSource)
  {
    super(paramObservableSource);
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DetachObserver(paramObserver));
  }
  
  static final class DetachObserver<T>
    implements Observer<T>, Disposable
  {
    Observer<? super T> downstream;
    Disposable upstream;
    
    DetachObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      Disposable localDisposable = this.upstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asObserver();
      localDisposable.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      Observer localObserver = this.downstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asObserver();
      localObserver.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      Observer localObserver = this.downstream;
      this.upstream = EmptyComponent.INSTANCE;
      this.downstream = EmptyComponent.asObserver();
      localObserver.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
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
