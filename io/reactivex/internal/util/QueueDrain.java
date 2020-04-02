package io.reactivex.internal.util;

import org.reactivestreams.Subscriber;

public abstract interface QueueDrain<T, U>
{
  public abstract boolean accept(Subscriber<? super U> paramSubscriber, T paramT);
  
  public abstract boolean cancelled();
  
  public abstract boolean done();
  
  public abstract boolean enter();
  
  public abstract Throwable error();
  
  public abstract int leave(int paramInt);
  
  public abstract long produced(long paramLong);
  
  public abstract long requested();
}
