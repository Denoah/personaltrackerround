package io.reactivex;

public abstract interface MaybeOnSubscribe<T>
{
  public abstract void subscribe(MaybeEmitter<T> paramMaybeEmitter)
    throws Exception;
}
