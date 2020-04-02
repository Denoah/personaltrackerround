package androidx.camera.camera2.interop;

import androidx.camera.camera2.internal.Camera2CameraInfoImpl;
import androidx.camera.core.CameraInfo;
import androidx.core.util.Preconditions;

public final class Camera2CameraInfo
{
  private Camera2CameraInfo() {}
  
  public static String extractCameraId(CameraInfo paramCameraInfo)
  {
    Preconditions.checkState(paramCameraInfo instanceof Camera2CameraInfoImpl, "CameraInfo does not contain any Camera2 information.");
    return ((Camera2CameraInfoImpl)paramCameraInfo).getCameraId();
  }
}
