package androidx.camera.core.impl;

public abstract class CameraCaptureCallback
{
  public CameraCaptureCallback() {}
  
  public void onCaptureCancelled() {}
  
  public void onCaptureCompleted(CameraCaptureResult paramCameraCaptureResult) {}
  
  public void onCaptureFailed(CameraCaptureFailure paramCameraCaptureFailure) {}
}
