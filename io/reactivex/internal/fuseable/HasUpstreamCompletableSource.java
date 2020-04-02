package io.reactivex.internal.fuseable;

import io.reactivex.CompletableSource;

public abstract interface HasUpstreamCompletableSource
{
  public abstract CompletableSource source();
}
