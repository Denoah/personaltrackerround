package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueCache<T>
{
  public abstract T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws Exception;
  
  public abstract void invalidate(Context paramContext);
}
