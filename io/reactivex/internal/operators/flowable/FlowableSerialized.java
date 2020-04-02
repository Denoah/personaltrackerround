package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableSerialized<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  public FlowableSerialized(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new SerializedSubscriber(paramSubscriber));
  }
}
