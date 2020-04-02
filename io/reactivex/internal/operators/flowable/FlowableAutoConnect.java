package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class FlowableAutoConnect<T>
  extends Flowable<T>
{
  final AtomicInteger clients;
  final Consumer<? super Disposable> connection;
  final int numberOfSubscribers;
  final ConnectableFlowable<? extends T> source;
  
  public FlowableAutoConnect(ConnectableFlowable<? extends T> paramConnectableFlowable, int paramInt, Consumer<? super Disposable> paramConsumer)
  {
    this.source = paramConnectableFlowable;
    this.numberOfSubscribers = paramInt;
    this.connection = paramConsumer;
    this.clients = new AtomicInteger();
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(paramSubscriber);
    if (this.clients.incrementAndGet() == this.numberOfSubscribers) {
      this.source.connect(this.connection);
    }
  }
}
