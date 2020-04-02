package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import androidx.camera.core.impl.CameraCaptureCallback;

final class CaptureCallbackContainer
  extends CameraCaptureCallback
{
  private final CameraCaptureSession.CaptureCallback mCaptureCallback;
  
  private CaptureCallbackContainer(CameraCaptureSession.CaptureCallback paramCaptureCallback)
  {
    if (paramCaptureCallback != null)
    {
      this.mCaptureCallback = paramCaptureCallback;
      return;
    }
    throw new NullPointerException("captureCallback is null");
  }
  
  static CaptureCallbackContainer create(CameraCaptureSession.CaptureCallback paramCaptureCallback)
  {
    return new CaptureCallbackContainer(paramCaptureCallback);
  }
  
  CameraCaptureSession.CaptureCallback getCaptureCallback()
  {
    return this.mCaptureCallback;
  }
}
