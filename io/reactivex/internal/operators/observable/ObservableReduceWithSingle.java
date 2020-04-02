package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableReduceWithSingle<T, R>
  extends Single<R>
{
  final BiFunction<R, ? super T, R> reducer;
  final Callable<R> seedSupplier;
  final ObservableSource<T> source;
  
  public ObservableReduceWithSingle(ObservableSource<T> paramObservableSource, Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    this.source = paramObservableSource;
    this.seedSupplier = paramCallable;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seedSupplier returned a null value");
      this.source.subscribe(new ObservableReduceSeedSingle.ReduceSeedObserver(paramSingleObserver, this.reducer, localObject));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
}
