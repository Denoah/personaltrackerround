package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.DisposableLambdaObserver;

public final class ObservableDoOnLifecycle<T>
  extends AbstractObservableWithUpstream<T, T>
{
  private final Action onDispose;
  private final Consumer<? super Disposable> onSubscribe;
  
  public ObservableDoOnLifecycle(Observable<T> paramObservable, Consumer<? super Disposable> paramConsumer, Action paramAction)
  {
    super(paramObservable);
    this.onSubscribe = paramConsumer;
    this.onDispose = paramAction;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DisposableLambdaObserver(paramObserver, this.onSubscribe, this.onDispose));
  }
}
