package io.reactivex;

public abstract interface SingleOperator<Downstream, Upstream>
{
  public abstract SingleObserver<? super Upstream> apply(SingleObserver<? super Downstream> paramSingleObserver)
    throws Exception;
}
