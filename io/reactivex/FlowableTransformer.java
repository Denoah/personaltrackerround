package io.reactivex;

import org.reactivestreams.Publisher;

public abstract interface FlowableTransformer<Upstream, Downstream>
{
  public abstract Publisher<Downstream> apply(Flowable<Upstream> paramFlowable);
}
