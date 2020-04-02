package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;

public final class CameraManagerCompat
{
  private final CameraManagerCompatImpl mImpl;
  
  private CameraManagerCompat(CameraManagerCompatImpl paramCameraManagerCompatImpl)
  {
    this.mImpl = paramCameraManagerCompatImpl;
  }
  
  public static CameraManagerCompat from(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return new CameraManagerCompat(new CameraManagerCompatApi28Impl(paramContext));
    }
    return new CameraManagerCompat(new CameraManagerCompatBaseImpl(paramContext));
  }
  
  public void openCamera(String paramString, Executor paramExecutor, CameraDevice.StateCallback paramStateCallback)
    throws CameraAccessException
  {
    this.mImpl.openCamera(paramString, paramExecutor, paramStateCallback);
  }
  
  public void registerAvailabilityCallback(Executor paramExecutor, CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    this.mImpl.registerAvailabilityCallback(paramExecutor, paramAvailabilityCallback);
  }
  
  public void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback paramAvailabilityCallback)
  {
    this.mImpl.unregisterAvailabilityCallback(paramAvailabilityCallback);
  }
  
  public CameraManager unwrap()
  {
    return this.mImpl.getCameraManager();
  }
  
  static final class AvailabilityCallbackExecutorWrapper
    extends CameraManager.AvailabilityCallback
  {
    private final Executor mExecutor;
    final CameraManager.AvailabilityCallback mWrappedCallback;
    
    AvailabilityCallbackExecutorWrapper(Executor paramExecutor, CameraManager.AvailabilityCallback paramAvailabilityCallback)
    {
      this.mExecutor = paramExecutor;
      this.mWrappedCallback = paramAvailabilityCallback;
    }
    
    public void onCameraAccessPrioritiesChanged()
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraManagerCompat.AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraAccessPrioritiesChanged();
        }
      });
    }
    
    public void onCameraAvailable(final String paramString)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraManagerCompat.AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraAvailable(paramString);
        }
      });
    }
    
    public void onCameraUnavailable(final String paramString)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraManagerCompat.AvailabilityCallbackExecutorWrapper.this.mWrappedCallback.onCameraUnavailable(paramString);
        }
      });
    }
  }
  
  static abstract interface CameraManagerCompatImpl
  {
    public abstract CameraManager getCameraManager();
    
    public abstract void openCamera(String paramString, Executor paramExecutor, CameraDevice.StateCallback paramStateCallback)
      throws CameraAccessException;
    
    public abstract void registerAvailabilityCallback(Executor paramExecutor, CameraManager.AvailabilityCallback paramAvailabilityCallback);
    
    public abstract void unregisterAvailabilityCallback(CameraManager.AvailabilityCallback paramAvailabilityCallback);
  }
}
