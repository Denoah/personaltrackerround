package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableFromAction
  extends Completable
{
  final Action run;
  
  public CompletableFromAction(Action paramAction)
  {
    this.run = paramAction;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Disposable localDisposable = Disposables.empty();
    paramCompletableObserver.onSubscribe(localDisposable);
    try
    {
      this.run.run();
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
