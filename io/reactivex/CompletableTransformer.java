package io.reactivex;

public abstract interface CompletableTransformer
{
  public abstract CompletableSource apply(Completable paramCompletable);
}
