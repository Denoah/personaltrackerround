package io.reactivex;

public abstract interface ObservableTransformer<Upstream, Downstream>
{
  public abstract ObservableSource<Downstream> apply(Observable<Upstream> paramObservable);
}
