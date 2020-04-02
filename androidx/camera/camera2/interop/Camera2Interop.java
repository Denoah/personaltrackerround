package androidx.camera.camera2.interop;

import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CaptureRequest.Key;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.impl.MutableConfig;

public final class Camera2Interop
{
  private Camera2Interop() {}
  
  public static final class Extender<T>
  {
    ExtendableBuilder<T> mBaseBuilder;
    
    public Extender(ExtendableBuilder<T> paramExtendableBuilder)
    {
      this.mBaseBuilder = paramExtendableBuilder;
    }
    
    public <ValueT> Extender<T> setCaptureRequestOption(CaptureRequest.Key<ValueT> paramKey, ValueT paramValueT)
    {
      paramKey = Camera2ImplConfig.createCaptureRequestOption(paramKey);
      this.mBaseBuilder.getMutableConfig().insertOption(paramKey, paramValueT);
      return this;
    }
    
    public Extender<T> setCaptureRequestTemplate(int paramInt)
    {
      this.mBaseBuilder.getMutableConfig().insertOption(Camera2ImplConfig.TEMPLATE_TYPE_OPTION, Integer.valueOf(paramInt));
      return this;
    }
    
    public Extender<T> setDeviceStateCallback(CameraDevice.StateCallback paramStateCallback)
    {
      this.mBaseBuilder.getMutableConfig().insertOption(Camera2ImplConfig.DEVICE_STATE_CALLBACK_OPTION, paramStateCallback);
      return this;
    }
    
    public Extender<T> setSessionCaptureCallback(CameraCaptureSession.CaptureCallback paramCaptureCallback)
    {
      this.mBaseBuilder.getMutableConfig().insertOption(Camera2ImplConfig.SESSION_CAPTURE_CALLBACK_OPTION, paramCaptureCallback);
      return this;
    }
    
    public Extender<T> setSessionStateCallback(CameraCaptureSession.StateCallback paramStateCallback)
    {
      this.mBaseBuilder.getMutableConfig().insertOption(Camera2ImplConfig.SESSION_STATE_CALLBACK_OPTION, paramStateCallback);
      return this;
    }
  }
}
