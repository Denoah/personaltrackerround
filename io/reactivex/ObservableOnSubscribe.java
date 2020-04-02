package io.reactivex;

public abstract interface ObservableOnSubscribe<T>
{
  public abstract void subscribe(ObservableEmitter<T> paramObservableEmitter)
    throws Exception;
}
