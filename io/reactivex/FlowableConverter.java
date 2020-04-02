package io.reactivex;

public abstract interface FlowableConverter<T, R>
{
  public abstract R apply(Flowable<T> paramFlowable);
}
