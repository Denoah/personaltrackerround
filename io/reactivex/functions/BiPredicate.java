package io.reactivex.functions;

public abstract interface BiPredicate<T1, T2>
{
  public abstract boolean test(T1 paramT1, T2 paramT2)
    throws Exception;
}
