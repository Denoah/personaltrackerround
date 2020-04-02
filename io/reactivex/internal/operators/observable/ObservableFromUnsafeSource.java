package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;

public final class ObservableFromUnsafeSource<T>
  extends Observable<T>
{
  final ObservableSource<T> source;
  
  public ObservableFromUnsafeSource(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(paramObserver);
  }
}
