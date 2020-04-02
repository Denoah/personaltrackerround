package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableTakePublisher<T>
  extends Flowable<T>
{
  final long limit;
  final Publisher<T> source;
  
  public FlowableTakePublisher(Publisher<T> paramPublisher, long paramLong)
  {
    this.source = paramPublisher;
    this.limit = paramLong;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new FlowableTake.TakeSubscriber(paramSubscriber, this.limit));
  }
}
