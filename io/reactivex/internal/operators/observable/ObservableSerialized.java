package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.observers.SerializedObserver;

public final class ObservableSerialized<T>
  extends AbstractObservableWithUpstream<T, T>
{
  public ObservableSerialized(Observable<T> paramObservable)
  {
    super(paramObservable);
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new SerializedObserver(paramObserver));
  }
}
