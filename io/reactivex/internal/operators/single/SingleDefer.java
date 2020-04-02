package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class SingleDefer<T>
  extends Single<T>
{
  final Callable<? extends SingleSource<? extends T>> singleSupplier;
  
  public SingleDefer(Callable<? extends SingleSource<? extends T>> paramCallable)
  {
    this.singleSupplier = paramCallable;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    try
    {
      SingleSource localSingleSource = (SingleSource)ObjectHelper.requireNonNull(this.singleSupplier.call(), "The singleSupplier returned a null SingleSource");
      localSingleSource.subscribe(paramSingleObserver);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
}
