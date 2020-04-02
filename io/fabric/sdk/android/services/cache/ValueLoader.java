package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueLoader<T>
{
  public abstract T load(Context paramContext)
    throws Exception;
}
