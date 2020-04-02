package io.reactivex;

public abstract interface ObservableOperator<Downstream, Upstream>
{
  public abstract Observer<? super Upstream> apply(Observer<? super Downstream> paramObserver)
    throws Exception;
}
