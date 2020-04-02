package io.reactivex;

public abstract interface MaybeSource<T>
{
  public abstract void subscribe(MaybeObserver<? super T> paramMaybeObserver);
}
