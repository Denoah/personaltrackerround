package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.InputConfiguration;
import android.os.Handler;
import androidx.camera.camera2.internal.compat.params.InputConfigurationCompat;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import androidx.core.util.Preconditions;
import java.util.List;

class CameraDeviceCompatApi23Impl
  extends CameraDeviceCompatBaseImpl
{
  CameraDeviceCompatApi23Impl() {}
  
  public void createCaptureSession(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
    throws CameraAccessException
  {
    checkPreconditions(paramCameraDevice, paramSessionConfigurationCompat);
    CameraCaptureSessionCompat.StateCallbackExecutorWrapper localStateCallbackExecutorWrapper = new CameraCaptureSessionCompat.StateCallbackExecutorWrapper(paramSessionConfigurationCompat.getExecutor(), paramSessionConfigurationCompat.getStateCallback());
    List localList = unpackSurfaces(paramSessionConfigurationCompat.getOutputConfigurations());
    Handler localHandler = MainThreadAsyncHandler.getInstance();
    InputConfigurationCompat localInputConfigurationCompat = paramSessionConfigurationCompat.getInputConfiguration();
    if (localInputConfigurationCompat != null)
    {
      paramSessionConfigurationCompat = (InputConfiguration)localInputConfigurationCompat.unwrap();
      Preconditions.checkNotNull(paramSessionConfigurationCompat);
      paramCameraDevice.createReprocessableCaptureSession(paramSessionConfigurationCompat, localList, localStateCallbackExecutorWrapper, localHandler);
    }
    else if (paramSessionConfigurationCompat.getSessionType() == 1)
    {
      paramCameraDevice.createConstrainedHighSpeedCaptureSession(localList, localStateCallbackExecutorWrapper, localHandler);
    }
    else
    {
      createBaseCaptureSession(paramCameraDevice, localList, localStateCallbackExecutorWrapper, localHandler);
    }
  }
}
