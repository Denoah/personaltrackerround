package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import java.util.concurrent.Executor;

class CameraManagerCompatApi28Impl
  extends CameraManagerCompatBaseImpl
{
  CameraManagerCompatApi28Impl(Context paramContext)
  {
    super(paramContext, null);
  }
  
  public void openCamera(String paramString, Executor paramExecutor, CameraDevice.StateCallback paramStateCallback)
    throws CameraAccessException
  {
    this.mCameraManager.openCamera(paramString, paramExecutor, paramStateCallback);
  }
  
  public void registerAvailabilityCallback(Executor paramExecutor, CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    this.mCameraManager.registerAvailabilityCallback(paramExecutor, paramAvailabilityCallback);
  }
  
  public void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    this.mCameraManager.unregisterAvailabilityCallback(paramAvailabilityCallback);
  }
}
