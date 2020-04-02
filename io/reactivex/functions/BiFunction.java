package io.reactivex.functions;

public abstract interface BiFunction<T1, T2, R>
{
  public abstract R apply(T1 paramT1, T2 paramT2)
    throws Exception;
}
