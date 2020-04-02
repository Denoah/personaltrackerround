package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class ObservableFromFuture<T>
  extends Observable<T>
{
  final Future<? extends T> future;
  final long timeout;
  final TimeUnit unit;
  
  public ObservableFromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    this.future = paramFuture;
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    DeferredScalarDisposable localDeferredScalarDisposable = new DeferredScalarDisposable(paramObserver);
    paramObserver.onSubscribe(localDeferredScalarDisposable);
    if (!localDeferredScalarDisposable.isDisposed()) {
      try
      {
        if (this.unit != null) {
          localObject = this.future.get(this.timeout, this.unit);
        } else {
          localObject = this.future.get();
        }
        Object localObject = ObjectHelper.requireNonNull(localObject, "Future returned null");
        localDeferredScalarDisposable.complete(localObject);
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        if (!localDeferredScalarDisposable.isDisposed()) {
          paramObserver.onError(localThrowable);
        }
      }
    }
  }
}
