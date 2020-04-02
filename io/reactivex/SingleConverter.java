package io.reactivex;

public abstract interface SingleConverter<T, R>
{
  public abstract R apply(Single<T> paramSingle);
}
