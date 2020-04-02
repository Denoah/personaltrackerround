package io.reactivex.parallel;

public abstract interface ParallelFlowableConverter<T, R>
{
  public abstract R apply(ParallelFlowable<T> paramParallelFlowable);
}
