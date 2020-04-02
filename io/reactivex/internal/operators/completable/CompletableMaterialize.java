package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

public final class CompletableMaterialize<T>
  extends Single<Notification<T>>
{
  final Completable source;
  
  public CompletableMaterialize(Completable paramCompletable)
  {
    this.source = paramCompletable;
  }
  
  protected void subscribeActual(SingleObserver<? super Notification<T>> paramSingleObserver)
  {
    this.source.subscribe(new MaterializeSingleObserver(paramSingleObserver));
  }
}
