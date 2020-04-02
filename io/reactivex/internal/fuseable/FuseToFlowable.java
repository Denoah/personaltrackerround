package io.reactivex.internal.fuseable;

import io.reactivex.Flowable;

public abstract interface FuseToFlowable<T>
{
  public abstract Flowable<T> fuseToFlowable();
}
