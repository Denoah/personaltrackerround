package io.reactivex;

public abstract interface CompletableOperator
{
  public abstract CompletableObserver apply(CompletableObserver paramCompletableObserver)
    throws Exception;
}
