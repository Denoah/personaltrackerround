package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableAutoConnect<T>
  extends Observable<T>
{
  final AtomicInteger clients;
  final Consumer<? super Disposable> connection;
  final int numberOfObservers;
  final ConnectableObservable<? extends T> source;
  
  public ObservableAutoConnect(ConnectableObservable<? extends T> paramConnectableObservable, int paramInt, Consumer<? super Disposable> paramConsumer)
  {
    this.source = paramConnectableObservable;
    this.numberOfObservers = paramInt;
    this.connection = paramConsumer;
    this.clients = new AtomicInteger();
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(paramObserver);
    if (this.clients.incrementAndGet() == this.numberOfObservers) {
      this.source.connect(this.connection);
    }
  }
}
