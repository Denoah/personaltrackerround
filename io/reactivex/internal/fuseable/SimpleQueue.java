package io.reactivex.internal.fuseable;

public abstract interface SimpleQueue<T>
{
  public abstract void clear();
  
  public abstract boolean isEmpty();
  
  public abstract boolean offer(T paramT);
  
  public abstract boolean offer(T paramT1, T paramT2);
  
  public abstract T poll()
    throws Exception;
}
