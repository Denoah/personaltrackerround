package io.reactivex.internal.fuseable;

import org.reactivestreams.Publisher;

public abstract interface HasUpstreamPublisher<T>
{
  public abstract Publisher<T> source();
}
