package androidx.camera.core.internal;

import androidx.camera.core.ImageInfo;
import androidx.camera.core.impl.CameraCaptureResult;

public final class CameraCaptureResultImageInfo
  implements ImageInfo
{
  private final CameraCaptureResult mCameraCaptureResult;
  
  public CameraCaptureResultImageInfo(CameraCaptureResult paramCameraCaptureResult)
  {
    this.mCameraCaptureResult = paramCameraCaptureResult;
  }
  
  public CameraCaptureResult getCameraCaptureResult()
  {
    return this.mCameraCaptureResult;
  }
  
  public int getRotationDegrees()
  {
    return 0;
  }
  
  public Object getTag()
  {
    return this.mCameraCaptureResult.getTag();
  }
  
  public long getTimestamp()
  {
    return this.mCameraCaptureResult.getTimestamp();
  }
}
