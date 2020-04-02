package io.fabric.sdk.android.services.events;

import java.io.IOException;

public abstract interface EventTransform<T>
{
  public abstract byte[] toBytes(T paramT)
    throws IOException;
}
