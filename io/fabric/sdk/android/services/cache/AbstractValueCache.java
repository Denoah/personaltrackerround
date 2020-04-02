package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract class AbstractValueCache<T>
  implements ValueCache<T>
{
  private final ValueCache<T> childCache;
  
  public AbstractValueCache()
  {
    this(null);
  }
  
  public AbstractValueCache(ValueCache<T> paramValueCache)
  {
    this.childCache = paramValueCache;
  }
  
  private void cache(Context paramContext, T paramT)
  {
    if (paramT != null)
    {
      cacheValue(paramContext, paramT);
      return;
    }
    throw null;
  }
  
  protected abstract void cacheValue(Context paramContext, T paramT);
  
  protected abstract void doInvalidate(Context paramContext);
  
  public final T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws Exception
  {
    try
    {
      Object localObject1 = getCached(paramContext);
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        if (this.childCache != null) {
          paramValueLoader = this.childCache.get(paramContext, paramValueLoader);
        } else {
          paramValueLoader = paramValueLoader.load(paramContext);
        }
        cache(paramContext, paramValueLoader);
        localObject2 = paramValueLoader;
      }
      return localObject2;
    }
    finally {}
  }
  
  protected abstract T getCached(Context paramContext);
  
  public final void invalidate(Context paramContext)
  {
    try
    {
      doInvalidate(paramContext);
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
}
