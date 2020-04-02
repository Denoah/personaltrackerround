package io.reactivex.internal.fuseable;

import io.reactivex.MaybeSource;

public abstract interface HasUpstreamMaybeSource<T>
{
  public abstract MaybeSource<T> source();
}
