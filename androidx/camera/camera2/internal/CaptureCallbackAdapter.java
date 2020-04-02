package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureFailure.Reason;

final class CaptureCallbackAdapter
  extends CameraCaptureSession.CaptureCallback
{
  private final CameraCaptureCallback mCameraCaptureCallback;
  
  CaptureCallbackAdapter(CameraCaptureCallback paramCameraCaptureCallback)
  {
    if (paramCameraCaptureCallback != null)
    {
      this.mCameraCaptureCallback = paramCameraCaptureCallback;
      return;
    }
    throw new NullPointerException("cameraCaptureCallback is null");
  }
  
  public void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult)
  {
    super.onCaptureCompleted(paramCameraCaptureSession, paramCaptureRequest, paramTotalCaptureResult);
    this.mCameraCaptureCallback.onCaptureCompleted(new Camera2CameraCaptureResult(paramCaptureRequest.getTag(), paramTotalCaptureResult));
  }
  
  public void onCaptureFailed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureFailure paramCaptureFailure)
  {
    super.onCaptureFailed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureFailure);
    paramCameraCaptureSession = new CameraCaptureFailure(CameraCaptureFailure.Reason.ERROR);
    this.mCameraCaptureCallback.onCaptureFailed(paramCameraCaptureSession);
  }
}
