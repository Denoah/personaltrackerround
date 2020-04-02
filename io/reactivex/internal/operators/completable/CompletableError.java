package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableError
  extends Completable
{
  final Throwable error;
  
  public CompletableError(Throwable paramThrowable)
  {
    this.error = paramThrowable;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    EmptyDisposable.error(this.error, paramCompletableObserver);
  }
}
