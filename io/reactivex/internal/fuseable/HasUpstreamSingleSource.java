package io.reactivex.internal.fuseable;

import io.reactivex.SingleSource;

public abstract interface HasUpstreamSingleSource<T>
{
  public abstract SingleSource<T> source();
}
