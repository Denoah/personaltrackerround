package io.reactivex;

import org.reactivestreams.Subscriber;

public abstract interface FlowableOperator<Downstream, Upstream>
{
  public abstract Subscriber<? super Upstream> apply(Subscriber<? super Downstream> paramSubscriber)
    throws Exception;
}
