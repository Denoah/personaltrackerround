package io.reactivex.functions;

public abstract interface Function<T, R>
{
  public abstract R apply(T paramT)
    throws Exception;
}
