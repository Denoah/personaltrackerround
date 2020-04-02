package io.reactivex;

public abstract interface MaybeTransformer<Upstream, Downstream>
{
  public abstract MaybeSource<Downstream> apply(Maybe<Upstream> paramMaybe);
}
