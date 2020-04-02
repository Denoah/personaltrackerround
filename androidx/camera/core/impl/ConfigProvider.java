package androidx.camera.core.impl;

import androidx.camera.core.CameraInfo;

public abstract interface ConfigProvider<C extends Config>
{
  public abstract C getConfig(CameraInfo paramCameraInfo);
}
