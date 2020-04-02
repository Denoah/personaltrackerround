package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest.Key;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.OptionUnpacker;
import androidx.camera.core.impl.UseCaseConfig;
import java.util.Iterator;
import java.util.Set;

final class Camera2SessionOptionUnpacker
  implements SessionConfig.OptionUnpacker
{
  static final Camera2SessionOptionUnpacker INSTANCE = new Camera2SessionOptionUnpacker();
  
  Camera2SessionOptionUnpacker() {}
  
  public void unpack(UseCaseConfig<?> paramUseCaseConfig, SessionConfig.Builder paramBuilder)
  {
    Object localObject1 = paramUseCaseConfig.getDefaultSessionConfig(null);
    Object localObject2 = OptionsBundle.emptyBundle();
    int i = SessionConfig.defaultEmptySessionConfig().getTemplateType();
    if (localObject1 != null)
    {
      i = ((SessionConfig)localObject1).getTemplateType();
      paramBuilder.addAllDeviceStateCallbacks(((SessionConfig)localObject1).getDeviceStateCallbacks());
      paramBuilder.addAllSessionStateCallbacks(((SessionConfig)localObject1).getSessionStateCallbacks());
      paramBuilder.addAllRepeatingCameraCaptureCallbacks(((SessionConfig)localObject1).getRepeatingCameraCaptureCallbacks());
      localObject2 = ((SessionConfig)localObject1).getImplementationOptions();
    }
    paramBuilder.setImplementationOptions((Config)localObject2);
    paramUseCaseConfig = new Camera2ImplConfig(paramUseCaseConfig);
    paramBuilder.setTemplateType(paramUseCaseConfig.getCaptureRequestTemplate(i));
    paramBuilder.addDeviceStateCallback(paramUseCaseConfig.getDeviceStateCallback(CameraDeviceStateCallbacks.createNoOpCallback()));
    paramBuilder.addSessionStateCallback(paramUseCaseConfig.getSessionStateCallback(CameraCaptureSessionStateCallbacks.createNoOpCallback()));
    paramBuilder.addCameraCaptureCallback(CaptureCallbackContainer.create(paramUseCaseConfig.getSessionCaptureCallback(Camera2CaptureCallbacks.createNoOpCallback())));
    localObject2 = MutableOptionsBundle.create();
    ((MutableOptionsBundle)localObject2).insertOption(Camera2ImplConfig.CAMERA_EVENT_CALLBACK_OPTION, paramUseCaseConfig.getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()));
    paramBuilder.addImplementationOptions((Config)localObject2);
    localObject2 = new Camera2ImplConfig.Builder();
    localObject1 = paramUseCaseConfig.getCaptureRequestOptions().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Config.Option localOption = (Config.Option)((Iterator)localObject1).next();
      ((Camera2ImplConfig.Builder)localObject2).setCaptureRequestOption((CaptureRequest.Key)localOption.getToken(), paramUseCaseConfig.retrieveOption(localOption));
    }
    paramBuilder.addImplementationOptions(((Camera2ImplConfig.Builder)localObject2).build());
  }
}
