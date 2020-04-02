package io.reactivex;

public abstract interface SingleSource<T>
{
  public abstract void subscribe(SingleObserver<? super T> paramSingleObserver);
}
