package io.reactivex;

public abstract interface Emitter<T>
{
  public abstract void onComplete();
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onNext(T paramT);
}
