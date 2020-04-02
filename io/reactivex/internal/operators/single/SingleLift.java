package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOperator;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleLift<T, R>
  extends Single<R>
{
  final SingleOperator<? extends R, ? super T> onLift;
  final SingleSource<T> source;
  
  public SingleLift(SingleSource<T> paramSingleSource, SingleOperator<? extends R, ? super T> paramSingleOperator)
  {
    this.source = paramSingleSource;
    this.onLift = paramSingleOperator;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    try
    {
      SingleObserver localSingleObserver = (SingleObserver)ObjectHelper.requireNonNull(this.onLift.apply(paramSingleObserver), "The onLift returned a null SingleObserver");
      this.source.subscribe(localSingleObserver);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
}
