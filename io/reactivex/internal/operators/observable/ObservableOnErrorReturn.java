package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableOnErrorReturn<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Function<? super Throwable, ? extends T> valueSupplier;
  
  public ObservableOnErrorReturn(ObservableSource<T> paramObservableSource, Function<? super Throwable, ? extends T> paramFunction)
  {
    super(paramObservableSource);
    this.valueSupplier = paramFunction;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new OnErrorReturnObserver(paramObserver, this.valueSupplier));
  }
  
  static final class OnErrorReturnObserver<T>
    implements Observer<T>, Disposable
  {
    final Observer<? super T> downstream;
    Disposable upstream;
    final Function<? super Throwable, ? extends T> valueSupplier;
    
    OnErrorReturnObserver(Observer<? super T> paramObserver, Function<? super Throwable, ? extends T> paramFunction)
    {
      this.downstream = paramObserver;
      this.valueSupplier = paramFunction;
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
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        Object localObject = this.valueSupplier.apply(paramThrowable);
        if (localObject == null)
        {
          localObject = new NullPointerException("The supplied value is null");
          ((NullPointerException)localObject).initCause(paramThrowable);
          this.downstream.onError((Throwable)localObject);
          return;
        }
        this.downstream.onNext(localObject);
        this.downstream.onComplete();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
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
