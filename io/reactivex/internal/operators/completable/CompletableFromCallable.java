package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class CompletableFromCallable
  extends Completable
{
  final Callable<?> callable;
  
  public CompletableFromCallable(Callable<?> paramCallable)
  {
    this.callable = paramCallable;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Disposable localDisposable = Disposables.empty();
    paramCompletableObserver.onSubscribe(localDisposable);
    try
    {
      this.callable.call();
      if (!localDisposable.isDisposed()) {
        paramCompletableObserver.onComplete();
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      if (!localDisposable.isDisposed()) {
        paramCompletableObserver.onError(localThrowable);
      } else {
        RxJavaPlugins.onError(localThrowable);
      }
    }
  }
}
