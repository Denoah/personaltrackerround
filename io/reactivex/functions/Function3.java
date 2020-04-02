package io.reactivex.functions;

public abstract interface Function3<T1, T2, T3, R>
{
  public abstract R apply(T1 paramT1, T2 paramT2, T3 paramT3)
    throws Exception;
}
