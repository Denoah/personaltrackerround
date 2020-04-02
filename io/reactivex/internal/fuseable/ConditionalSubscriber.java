package io.reactivex.internal.fuseable;

import io.reactivex.FlowableSubscriber;

public abstract interface ConditionalSubscriber<T>
  extends FlowableSubscriber<T>
{
  public abstract boolean tryOnNext(T paramT);
}
