package io.reactivex.functions;

public abstract interface IntFunction<T>
{
  public abstract T apply(int paramInt)
    throws Exception;
}
