package androidx.camera.camera2.impl;

import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CaptureRequest.Key;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.Config.OptionMatcher;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Camera2ImplConfig
  implements Config
{
  public static final Config.Option<CameraEventCallbacks> CAMERA_EVENT_CALLBACK_OPTION = Config.Option.create("camera2.cameraEvent.callback", CameraEventCallbacks.class);
  public static final String CAPTURE_REQUEST_ID_STEM = "camera2.captureRequest.option.";
  public static final Config.Option<CameraDevice.StateCallback> DEVICE_STATE_CALLBACK_OPTION;
  public static final Config.Option<CameraCaptureSession.CaptureCallback> SESSION_CAPTURE_CALLBACK_OPTION;
  public static final Config.Option<CameraCaptureSession.StateCallback> SESSION_STATE_CALLBACK_OPTION;
  public static final Config.Option<Integer> TEMPLATE_TYPE_OPTION = Config.Option.create("camera2.captureRequest.templateType", Integer.TYPE);
  private final Config mConfig;
  
  static
  {
    DEVICE_STATE_CALLBACK_OPTION = Config.Option.create("camera2.cameraDevice.stateCallback", CameraDevice.StateCallback.class);
    SESSION_STATE_CALLBACK_OPTION = Config.Option.create("camera2.cameraCaptureSession.stateCallback", CameraCaptureSession.StateCallback.class);
    SESSION_CAPTURE_CALLBACK_OPTION = Config.Option.create("camera2.cameraCaptureSession.captureCallback", CameraCaptureSession.CaptureCallback.class);
  }
  
  public Camera2ImplConfig(Config paramConfig)
  {
    this.mConfig = paramConfig;
  }
  
  public static Config.Option<Object> createCaptureRequestOption(CaptureRequest.Key<?> paramKey)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("camera2.captureRequest.option.");
    localStringBuilder.append(paramKey.getName());
    return Config.Option.create(localStringBuilder.toString(), Object.class, paramKey);
  }
  
  public boolean containsOption(Config.Option<?> paramOption)
  {
    return this.mConfig.containsOption(paramOption);
  }
  
  public void findOptions(String paramString, Config.OptionMatcher paramOptionMatcher)
  {
    this.mConfig.findOptions(paramString, paramOptionMatcher);
  }
  
  public CameraEventCallbacks getCameraEventCallback(CameraEventCallbacks paramCameraEventCallbacks)
  {
    return (CameraEventCallbacks)this.mConfig.retrieveOption(CAMERA_EVENT_CALLBACK_OPTION, paramCameraEventCallbacks);
  }
  
  public <ValueT> ValueT getCaptureRequestOption(CaptureRequest.Key<ValueT> paramKey, ValueT paramValueT)
  {
    paramKey = createCaptureRequestOption(paramKey);
    return this.mConfig.retrieveOption(paramKey, paramValueT);
  }
  
  public Set<Config.Option<?>> getCaptureRequestOptions()
  {
    final HashSet localHashSet = new HashSet();
    findOptions("camera2.captureRequest.option.", new Config.OptionMatcher()
    {
      public boolean onOptionMatched(Config.Option<?> paramAnonymousOption)
      {
        localHashSet.add(paramAnonymousOption);
        return true;
      }
    });
    return localHashSet;
  }
  
  public int getCaptureRequestTemplate(int paramInt)
  {
    return ((Integer)this.mConfig.retrieveOption(TEMPLATE_TYPE_OPTION, Integer.valueOf(paramInt))).intValue();
  }
  
  public CameraDevice.StateCallback getDeviceStateCallback(CameraDevice.StateCallback paramStateCallback)
  {
    return (CameraDevice.StateCallback)this.mConfig.retrieveOption(DEVICE_STATE_CALLBACK_OPTION, paramStateCallback);
  }
  
  public CameraCaptureSession.CaptureCallback getSessionCaptureCallback(CameraCaptureSession.CaptureCallback paramCaptureCallback)
  {
    return (CameraCaptureSession.CaptureCallback)this.mConfig.retrieveOption(SESSION_CAPTURE_CALLBACK_OPTION, paramCaptureCallback);
  }
  
  public CameraCaptureSession.StateCallback getSessionStateCallback(CameraCaptureSession.StateCallback paramStateCallback)
  {
    return (CameraCaptureSession.StateCallback)this.mConfig.retrieveOption(SESSION_STATE_CALLBACK_OPTION, paramStateCallback);
  }
  
  public Set<Config.Option<?>> listOptions()
  {
    return this.mConfig.listOptions();
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption)
  {
    return this.mConfig.retrieveOption(paramOption);
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption, ValueT paramValueT)
  {
    return this.mConfig.retrieveOption(paramOption, paramValueT);
  }
  
  public static final class Builder
    implements ExtendableBuilder<Camera2ImplConfig>
  {
    private final MutableOptionsBundle mMutableOptionsBundle = MutableOptionsBundle.create();
    
    public Builder() {}
    
    public Camera2ImplConfig build()
    {
      return new Camera2ImplConfig(OptionsBundle.from(this.mMutableOptionsBundle));
    }
    
    public MutableConfig getMutableConfig()
    {
      return this.mMutableOptionsBundle;
    }
    
    public Builder insertAllOptions(Config paramConfig)
    {
      Iterator localIterator = paramConfig.listOptions().iterator();
      while (localIterator.hasNext())
      {
        Config.Option localOption = (Config.Option)localIterator.next();
        this.mMutableOptionsBundle.insertOption(localOption, paramConfig.retrieveOption(localOption));
      }
      return this;
    }
    
    public <ValueT> Builder setCaptureRequestOption(CaptureRequest.Key<ValueT> paramKey, ValueT paramValueT)
    {
      paramKey = Camera2ImplConfig.createCaptureRequestOption(paramKey);
      this.mMutableOptionsBundle.insertOption(paramKey, paramValueT);
      return this;
    }
  }
  
  public static final class Extender<T>
  {
    ExtendableBuilder<T> mBaseBuilder;
    
    public Extender(ExtendableBuilder<T> paramExtendableBuilder)
    {
      this.mBaseBuilder = paramExtendableBuilder;
    }
    
    public Extender<T> setCameraEventCallback(CameraEventCallbacks paramCameraEventCallbacks)
    {
      this.mBaseBuilder.getMutableConfig().insertOption(Camera2ImplConfig.CAMERA_EVENT_CALLBACK_OPTION, paramCameraEventCallbacks);
      return this;
    }
  }
}
