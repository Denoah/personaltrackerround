package io.reactivex;

public abstract interface ObservableSource<T>
{
  public abstract void subscribe(Observer<? super T> paramObserver);
}
