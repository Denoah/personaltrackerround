package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class CompletableDefer
  extends Completable
{
  final Callable<? extends CompletableSource> completableSupplier;
  
  public CompletableDefer(Callable<? extends CompletableSource> paramCallable)
  {
    this.completableSupplier = paramCallable;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    try
    {
      CompletableSource localCompletableSource = (CompletableSource)ObjectHelper.requireNonNull(this.completableSupplier.call(), "The completableSupplier returned a null CompletableSource");
      localCompletableSource.subscribe(paramCompletableObserver);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramCompletableObserver);
    }
  }
}
