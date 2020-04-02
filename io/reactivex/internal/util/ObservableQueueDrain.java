package io.reactivex.internal.util;

import io.reactivex.Observer;

public abstract interface ObservableQueueDrain<T, U>
{
  public abstract void accept(Observer<? super U> paramObserver, T paramT);
  
  public abstract boolean cancelled();
  
  public abstract boolean done();
  
  public abstract boolean enter();
  
  public abstract Throwable error();
  
  public abstract int leave(int paramInt);
}
