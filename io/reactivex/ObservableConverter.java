package io.reactivex;

public abstract interface ObservableConverter<T, R>
{
  public abstract R apply(Observable<T> paramObservable);
}
