package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.os.Build.VERSION;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import java.util.concurrent.Executor;

public final class CameraDeviceCompat
{
  private static final CameraDeviceCompatImpl IMPL = ;
  public static final int SESSION_OPERATION_MODE_CONSTRAINED_HIGH_SPEED = 1;
  public static final int SESSION_OPERATION_MODE_NORMAL = 0;
  
  private CameraDeviceCompat() {}
  
  private static CameraDeviceCompatImpl chooseImplementation()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return new CameraDeviceCompatApi28Impl();
    }
    if (Build.VERSION.SDK_INT >= 24) {
      return new CameraDeviceCompatApi24Impl();
    }
    if (Build.VERSION.SDK_INT >= 23) {
      return new CameraDeviceCompatApi23Impl();
    }
    return new CameraDeviceCompatBaseImpl();
  }
  
  public static void createCaptureSession(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
    throws CameraAccessException
  {
    IMPL.createCaptureSession(paramCameraDevice, paramSessionConfigurationCompat);
  }
  
  static abstract interface CameraDeviceCompatImpl
  {
    public abstract void createCaptureSession(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
      throws CameraAccessException;
  }
  
  static final class StateCallbackExecutorWrapper
    extends CameraDevice.StateCallback
  {
    private final Executor mExecutor;
    final CameraDevice.StateCallback mWrappedCallback;
    
    StateCallbackExecutorWrapper(Executor paramExecutor, CameraDevice.StateCallback paramStateCallback)
    {
      this.mExecutor = paramExecutor;
      this.mWrappedCallback = paramStateCallback;
    }
    
    public void onClosed(final CameraDevice paramCameraDevice)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraDeviceCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onClosed(paramCameraDevice);
        }
      });
    }
    
    public void onDisconnected(final CameraDevice paramCameraDevice)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraDeviceCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onDisconnected(paramCameraDevice);
        }
      });
    }
    
    public void onError(final CameraDevice paramCameraDevice, final int paramInt)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraDeviceCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onError(paramCameraDevice, paramInt);
        }
      });
    }
    
    public void onOpened(final CameraDevice paramCameraDevice)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraDeviceCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onOpened(paramCameraDevice);
        }
      });
    }
  }
}
