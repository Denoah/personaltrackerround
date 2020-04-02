package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableFlatMapPublisher<T, U>
  extends Flowable<U>
{
  final int bufferSize;
  final boolean delayErrors;
  final Function<? super T, ? extends Publisher<? extends U>> mapper;
  final int maxConcurrency;
  final Publisher<T> source;
  
  public FlowableFlatMapPublisher(Publisher<T> paramPublisher, Function<? super T, ? extends Publisher<? extends U>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.source = paramPublisher;
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt1;
    this.bufferSize = paramInt2;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, paramSubscriber, this.mapper)) {
      return;
    }
    this.source.subscribe(FlowableFlatMap.subscribe(paramSubscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
  }
}
