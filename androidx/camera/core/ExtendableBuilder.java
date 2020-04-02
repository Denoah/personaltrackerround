package androidx.camera.core;

import androidx.camera.core.impl.MutableConfig;

public abstract interface ExtendableBuilder<T>
{
  public abstract T build();
  
  public abstract MutableConfig getMutableConfig();
}
