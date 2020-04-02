package androidx.camera.core.impl;

import android.content.Context;
import androidx.camera.core.CameraInfo;

public abstract interface UseCaseConfigFactory
{
  public abstract <C extends UseCaseConfig<?>> C getConfig(Class<C> paramClass, CameraInfo paramCameraInfo);
  
  public static abstract interface Provider
  {
    public abstract UseCaseConfigFactory newInstance(Context paramContext);
  }
}
