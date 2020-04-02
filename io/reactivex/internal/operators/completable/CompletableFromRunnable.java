package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableFromRunnable
  extends Completable
{
  final Runnable runnable;
  
  public CompletableFromRunnable(Runnable paramRunnable)
  {
    this.runnable = paramRunnable;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Disposable localDisposable = Disposables.empty();
    paramCompletableObserver.onSubscribe(localDisposable);
    try
    {
      this.runnable.run();
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
