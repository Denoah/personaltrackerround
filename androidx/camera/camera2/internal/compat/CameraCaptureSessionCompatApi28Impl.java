package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureRequest;
import androidx.core.util.Preconditions;
import java.util.List;
import java.util.concurrent.Executor;

class CameraCaptureSessionCompatApi28Impl
  extends CameraCaptureSessionCompatBaseImpl
{
  CameraCaptureSessionCompatApi28Impl() {}
  
  public int captureBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.captureBurstRequests(paramList, paramExecutor, paramCaptureCallback);
  }
  
  public int captureSingleRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.captureSingleRequest(paramCaptureRequest, paramExecutor, paramCaptureCallback);
  }
  
  public int setRepeatingBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.setRepeatingBurstRequests(paramList, paramExecutor, paramCaptureCallback);
  }
  
  public int setSingleRepeatingRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.setSingleRepeatingRequest(paramCaptureRequest, paramExecutor, paramCaptureCallback);
  }
}
