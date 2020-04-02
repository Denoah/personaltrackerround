package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

class CameraManagerCompatBaseImpl
  implements CameraManagerCompat.CameraManagerCompatImpl
{
  final CameraManager mCameraManager;
  final Object mObject;
  
  CameraManagerCompatBaseImpl(Context paramContext)
  {
    this.mCameraManager = ((CameraManager)paramContext.getSystemService("camera"));
    this.mObject = new CameraManagerCompatParamsApi21();
  }
  
  CameraManagerCompatBaseImpl(Context paramContext, Object paramObject)
  {
    this.mCameraManager = ((CameraManager)paramContext.getSystemService("camera"));
    this.mObject = paramObject;
  }
  
  public CameraManager getCameraManager()
  {
    return this.mCameraManager;
  }
  
  public void openCamera(String paramString, Executor paramExecutor, CameraDevice.StateCallback paramStateCallback)
    throws CameraAccessException
  {
    Preconditions.checkNotNull(paramExecutor);
    Preconditions.checkNotNull(paramStateCallback);
    paramExecutor = new CameraDeviceCompat.StateCallbackExecutorWrapper(paramExecutor, paramStateCallback);
    this.mCameraManager.openCamera(paramString, paramExecutor, MainThreadAsyncHandler.getInstance());
  }
  
  public void registerAvailabilityCallback(Executor paramExecutor, CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    if (paramExecutor != null)
    {
      Object localObject = null;
      if (paramAvailabilityCallback != null)
      {
        CameraManagerCompatParamsApi21 localCameraManagerCompatParamsApi21 = (CameraManagerCompatParamsApi21)this.mObject;
        synchronized (localCameraManagerCompatParamsApi21.mWrapperMap)
        {
          CameraManagerCompat.AvailabilityCallbackExecutorWrapper localAvailabilityCallbackExecutorWrapper = (CameraManagerCompat.AvailabilityCallbackExecutorWrapper)localCameraManagerCompatParamsApi21.mWrapperMap.get(paramAvailabilityCallback);
          localObject = localAvailabilityCallbackExecutorWrapper;
          if (localAvailabilityCallbackExecutorWrapper == null)
          {
            localObject = new androidx/camera/camera2/internal/compat/CameraManagerCompat$AvailabilityCallbackExecutorWrapper;
            ((CameraManagerCompat.AvailabilityCallbackExecutorWrapper)localObject).<init>(paramExecutor, paramAvailabilityCallback);
            localCameraManagerCompatParamsApi21.mWrapperMap.put(paramAvailabilityCallback, localObject);
          }
        }
      }
      this.mCameraManager.registerAvailabilityCallback((CameraManager.AvailabilityCallback)localObject, MainThreadAsyncHandler.getInstance());
      return;
    }
    throw new IllegalArgumentException("executor was null");
  }
  
  public void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    if (paramAvailabilityCallback != null)
    {
      CameraManagerCompatParamsApi21 localCameraManagerCompatParamsApi21 = (CameraManagerCompatParamsApi21)this.mObject;
      synchronized (localCameraManagerCompatParamsApi21.mWrapperMap)
      {
        paramAvailabilityCallback = (CameraManagerCompat.AvailabilityCallbackExecutorWrapper)localCameraManagerCompatParamsApi21.mWrapperMap.remove(paramAvailabilityCallback);
      }
    }
    paramAvailabilityCallback = null;
    this.mCameraManager.unregisterAvailabilityCallback(paramAvailabilityCallback);
  }
  
  static final class CameraManagerCompatParamsApi21
  {
    final Map<CameraManager.AvailabilityCallback, CameraManagerCompat.AvailabilityCallbackExecutorWrapper> mWrapperMap = new HashMap();
    
    CameraManagerCompatParamsApi21() {}
  }
}
