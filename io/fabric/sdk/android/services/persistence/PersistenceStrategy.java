package io.fabric.sdk.android.services.persistence;

public abstract interface PersistenceStrategy<T>
{
  public abstract void clear();
  
  public abstract T restore();
  
  public abstract void save(T paramT);
}
