package io.reactivex;

public abstract interface FlowableOnSubscribe<T>
{
  public abstract void subscribe(FlowableEmitter<T> paramFlowableEmitter)
    throws Exception;
}
