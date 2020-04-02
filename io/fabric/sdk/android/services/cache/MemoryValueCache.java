package io.fabric.sdk.android.services.cache;

import android.content.Context;

public class MemoryValueCache<T>
  extends AbstractValueCache<T>
{
  private T value;
  
  public MemoryValueCache()
  {
    this(null);
  }
  
  public MemoryValueCache(ValueCache<T> paramValueCache)
  {
    super(paramValueCache);
  }
  
  protected void cacheValue(Context paramContext, T paramT)
  {
    this.value = paramT;
  }
  
  protected void doInvalidate(Context paramContext)
  {
    this.value = null;
  }
  
  protected T getCached(Context paramContext)
  {
    return this.value;
  }
}
