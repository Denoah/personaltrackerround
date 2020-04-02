package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableMapPublisher<T, U>
  extends Flowable<U>
{
  final Function<? super T, ? extends U> mapper;
  final Publisher<T> source;
  
  public FlowableMapPublisher(Publisher<T> paramPublisher, Function<? super T, ? extends U> paramFunction)
  {
    this.source = paramPublisher;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    this.source.subscribe(new FlowableMap.MapSubscriber(paramSubscriber, this.mapper));
  }
}
