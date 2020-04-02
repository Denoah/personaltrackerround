package io.reactivex.internal.fuseable;

import io.reactivex.Observable;

public abstract interface FuseToObservable<T>
{
  public abstract Observable<T> fuseToObservable();
}
