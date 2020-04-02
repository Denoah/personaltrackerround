package io.reactivex.internal.operators.single;

import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

public final class SingleMaterialize<T>
  extends Single<Notification<T>>
{
  final Single<T> source;
  
  public SingleMaterialize(Single<T> paramSingle)
  {
    this.source = paramSingle;
  }
  
  protected void subscribeActual(SingleObserver<? super Notification<T>> paramSingleObserver)
  {
    this.source.subscribe(new MaterializeSingleObserver(paramSingleObserver));
  }
}
