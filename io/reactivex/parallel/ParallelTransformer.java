package io.reactivex.parallel;

public abstract interface ParallelTransformer<Upstream, Downstream>
{
  public abstract ParallelFlowable<Downstream> apply(ParallelFlowable<Upstream> paramParallelFlowable);
}
