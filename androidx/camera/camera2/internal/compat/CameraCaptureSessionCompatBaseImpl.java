package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureRequest;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import androidx.core.util.Preconditions;
import java.util.List;
import java.util.concurrent.Executor;

class CameraCaptureSessionCompatBaseImpl
  implements CameraCaptureSessionCompat.CameraCaptureSessionCompatImpl
{
  CameraCaptureSessionCompatBaseImpl() {}
  
  public int captureBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.captureBurst(paramList, new CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper(paramExecutor, paramCaptureCallback), MainThreadAsyncHandler.getInstance());
  }
  
  public int captureSingleRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.capture(paramCaptureRequest, new CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper(paramExecutor, paramCaptureCallback), MainThreadAsyncHandler.getInstance());
  }
  
  public int setRepeatingBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.setRepeatingBurst(paramList, new CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper(paramExecutor, paramCaptureCallback), MainThreadAsyncHandler.getInstance());
  }
  
  public int setSingleRepeatingRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraCaptureSession);
    return paramCameraCaptureSession.setRepeatingRequest(paramCaptureRequest, new CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper(paramExecutor, paramCaptureCallback), MainThreadAsyncHandler.getInstance());
  }
}
