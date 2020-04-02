package io.reactivex;

public abstract interface MaybeConverter<T, R>
{
  public abstract R apply(Maybe<T> paramMaybe);
}
