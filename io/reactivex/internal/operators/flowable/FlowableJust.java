package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import org.reactivestreams.Subscriber;

public final class FlowableJust<T>
  extends Flowable<T>
  implements ScalarCallable<T>
{
  private final T value;
  
  public FlowableJust(T paramT)
  {
    this.value = paramT;
  }
  
  public T call()
  {
    return this.value;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    paramSubscriber.onSubscribe(new ScalarSubscription(paramSubscriber, this.value));
  }
}
