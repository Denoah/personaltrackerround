package io.reactivex.internal.fuseable;

import io.reactivex.Maybe;

public abstract interface FuseToMaybe<T>
{
  public abstract Maybe<T> fuseToMaybe();
}
