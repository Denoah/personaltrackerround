package io.reactivex;

public abstract interface MaybeOperator<Downstream, Upstream>
{
  public abstract MaybeObserver<? super Upstream> apply(MaybeObserver<? super Downstream> paramMaybeObserver)
    throws Exception;
}
