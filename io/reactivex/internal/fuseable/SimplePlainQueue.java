package io.reactivex.internal.fuseable;

public abstract interface SimplePlainQueue<T>
  extends SimpleQueue<T>
{
  public abstract T poll();
}
