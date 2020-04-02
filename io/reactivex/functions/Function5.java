package io.reactivex.functions;

public abstract interface Function5<T1, T2, T3, T4, T5, R>
{
  public abstract R apply(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5)
    throws Exception;
}
