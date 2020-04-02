package androidx.camera.camera2.impl;

import android.hardware.camera2.CaptureResult;
import androidx.camera.camera2.internal.Camera2CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResult;

public final class Camera2CameraCaptureResultConverter
{
  private Camera2CameraCaptureResultConverter() {}
  
  public static CaptureResult getCaptureResult(CameraCaptureResult paramCameraCaptureResult)
  {
    if ((paramCameraCaptureResult instanceof Camera2CameraCaptureResult)) {
      return ((Camera2CameraCaptureResult)paramCameraCaptureResult).getCaptureResult();
    }
    return null;
  }
}
