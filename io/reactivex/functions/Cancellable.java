package io.reactivex.functions;

public abstract interface Cancellable
{
  public abstract void cancel()
    throws Exception;
}
