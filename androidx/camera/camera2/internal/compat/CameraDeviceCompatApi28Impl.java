package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.SessionConfiguration;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.core.util.Preconditions;

class CameraDeviceCompatApi28Impl
  extends CameraDeviceCompatApi24Impl
{
  CameraDeviceCompatApi28Impl() {}
  
  public void createCaptureSession(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramCameraDevice);
    paramSessionConfigurationCompat = (SessionConfiguration)paramSessionConfigurationCompat.unwrap();
    Preconditions.checkNotNull(paramSessionConfigurationCompat);
    paramCameraDevice.createCaptureSession(paramSessionConfigurationCompat);
  }
}
