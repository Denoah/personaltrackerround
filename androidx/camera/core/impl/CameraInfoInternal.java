package androidx.camera.core.impl;

import androidx.camera.core.CameraInfo;

public abstract interface CameraInfoInternal
  extends CameraInfo
{
  public abstract String getCameraId();
  
  public abstract Integer getLensFacing();
}
