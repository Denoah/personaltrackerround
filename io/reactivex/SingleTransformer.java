package io.reactivex;

public abstract interface SingleTransformer<Upstream, Downstream>
{
  public abstract SingleSource<Downstream> apply(Single<Upstream> paramSingle);
}
