package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class ObservableJust<T>
  extends Observable<T>
  implements ScalarCallable<T>
{
  private final T value;
  
  public ObservableJust(T paramT)
  {
    this.value = paramT;
  }
  
  public T call()
  {
    return this.value;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    ObservableScalarXMap.ScalarDisposable localScalarDisposable = new ObservableScalarXMap.ScalarDisposable(paramObserver, this.value);
    paramObserver.onSubscribe(localScalarDisposable);
    localScalarDisposable.run();
  }
}
