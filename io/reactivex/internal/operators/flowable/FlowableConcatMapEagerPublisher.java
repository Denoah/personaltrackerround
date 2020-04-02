package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableConcatMapEagerPublisher<T, R>
  extends Flowable<R>
{
  final ErrorMode errorMode;
  final Function<? super T, ? extends Publisher<? extends R>> mapper;
  final int maxConcurrency;
  final int prefetch;
  final Publisher<T> source;
  
  public FlowableConcatMapEagerPublisher(Publisher<T> paramPublisher, Function<? super T, ? extends Publisher<? extends R>> paramFunction, int paramInt1, int paramInt2, ErrorMode paramErrorMode)
  {
    this.source = paramPublisher;
    this.mapper = paramFunction;
    this.maxConcurrency = paramInt1;
    this.prefetch = paramInt2;
    this.errorMode = paramErrorMode;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber(paramSubscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
  }
}
