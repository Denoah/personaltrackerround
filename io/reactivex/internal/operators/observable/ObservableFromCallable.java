package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableFromCallable<T>
  extends Observable<T>
  implements Callable<T>
{
  final Callable<? extends T> callable;
  
  public ObservableFromCallable(Callable<? extends T> paramCallable)
  {
    this.callable = paramCallable;
  }
  
  public T call()
    throws Exception
  {
    return ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    DeferredScalarDisposable localDeferredScalarDisposable = new DeferredScalarDisposable(paramObserver);
    paramObserver.onSubscribe(localDeferredScalarDisposable);
    if (localDeferredScalarDisposable.isDisposed()) {
      return;
    }
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.callable.call(), "Callable returned null");
      localDeferredScalarDisposable.complete(localObject);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      if (!localDeferredScalarDisposable.isDisposed()) {
        paramObserver.onError(localThrowable);
      } else {
        RxJavaPlugins.onError(localThrowable);
      }
    }
  }
}
