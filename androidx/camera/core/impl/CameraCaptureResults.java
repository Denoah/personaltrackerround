package androidx.camera.core.impl;

import androidx.camera.core.ImageInfo;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;

public final class CameraCaptureResults
{
  private CameraCaptureResults() {}
  
  public static CameraCaptureResult retrieveCameraCaptureResult(ImageInfo paramImageInfo)
  {
    if ((paramImageInfo instanceof CameraCaptureResultImageInfo)) {
      return ((CameraCaptureResultImageInfo)paramImageInfo).getCameraCaptureResult();
    }
    return null;
  }
}
