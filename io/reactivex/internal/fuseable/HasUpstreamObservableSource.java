package io.reactivex.internal.fuseable;

import io.reactivex.ObservableSource;

public abstract interface HasUpstreamObservableSource<T>
{
  public abstract ObservableSource<T> source();
}
