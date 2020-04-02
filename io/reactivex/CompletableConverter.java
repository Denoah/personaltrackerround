package io.reactivex;

public abstract interface CompletableConverter<R>
{
  public abstract R apply(Completable paramCompletable);
}
