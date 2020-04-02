package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;

public final class CompletableFromUnsafeSource
  extends Completable
{
  final CompletableSource source;
  
  public CompletableFromUnsafeSource(CompletableSource paramCompletableSource)
  {
    this.source = paramCompletableSource;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(paramCompletableObserver);
  }
}
