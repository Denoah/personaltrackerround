package io.reactivex.internal.fuseable;

import java.util.concurrent.Callable;

public abstract interface ScalarCallable<T>
  extends Callable<T>
{
  public abstract T call();
}
