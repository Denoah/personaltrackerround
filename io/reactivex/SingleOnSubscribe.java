package io.reactivex;

public abstract interface SingleOnSubscribe<T>
{
  public abstract void subscribe(SingleEmitter<T> paramSingleEmitter)
    throws Exception;
}
