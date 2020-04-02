package io.reactivex.internal.operators.flowable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public final class FlowableReduceWithSingle<T, R>
  extends Single<R>
{
  final BiFunction<R, ? super T, R> reducer;
  final Callable<R> seedSupplier;
  final Publisher<T> source;
  
  public FlowableReduceWithSingle(Publisher<T> paramPublisher, Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    this.source = paramPublisher;
    this.seedSupplier = paramCallable;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seedSupplier returned a null value");
      this.source.subscribe(new FlowableReduceSeedSingle.ReduceSeedObserver(paramSingleObserver, this.reducer, localObject));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
}
