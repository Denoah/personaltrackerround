package androidx.camera.core.impl;

import android.os.Build;
import android.os.Build.VERSION;

public abstract class DeviceProperties
{
  public DeviceProperties() {}
  
  public static DeviceProperties create()
  {
    return create(Build.MANUFACTURER, Build.MODEL, Build.VERSION.SDK_INT);
  }
  
  public static DeviceProperties create(String paramString1, String paramString2, int paramInt)
  {
    return new AutoValue_DeviceProperties(paramString1, paramString2, paramInt);
  }
  
  public abstract String manufacturer();
  
  public abstract String model();
  
  public abstract int sdkVersion();
}
