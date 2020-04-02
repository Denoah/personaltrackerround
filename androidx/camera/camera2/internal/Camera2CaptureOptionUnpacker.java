package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest.Key;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.CaptureConfig.OptionUnpacker;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.UseCaseConfig;
import java.util.Iterator;
import java.util.Set;

class Camera2CaptureOptionUnpacker
  implements CaptureConfig.OptionUnpacker
{
  static final Camera2CaptureOptionUnpacker INSTANCE = new Camera2CaptureOptionUnpacker();
  
  Camera2CaptureOptionUnpacker() {}
  
  public void unpack(UseCaseConfig<?> paramUseCaseConfig, CaptureConfig.Builder paramBuilder)
  {
    Object localObject1 = paramUseCaseConfig.getDefaultCaptureConfig(null);
    Object localObject2 = OptionsBundle.emptyBundle();
    int i = CaptureConfig.defaultEmptyCaptureConfig().getTemplateType();
    if (localObject1 != null)
    {
      i = ((CaptureConfig)localObject1).getTemplateType();
      paramBuilder.addAllCameraCaptureCallbacks(((CaptureConfig)localObject1).getCameraCaptureCallbacks());
      localObject2 = ((CaptureConfig)localObject1).getImplementationOptions();
    }
    paramBuilder.setImplementationOptions((Config)localObject2);
    Camera2ImplConfig localCamera2ImplConfig = new Camera2ImplConfig(paramUseCaseConfig);
    paramBuilder.setTemplateType(localCamera2ImplConfig.getCaptureRequestTemplate(i));
    paramBuilder.addCameraCaptureCallback(CaptureCallbackContainer.create(localCamera2ImplConfig.getSessionCaptureCallback(Camera2CaptureCallbacks.createNoOpCallback())));
    localObject2 = new Camera2ImplConfig.Builder();
    paramUseCaseConfig = localCamera2ImplConfig.getCaptureRequestOptions().iterator();
    while (paramUseCaseConfig.hasNext())
    {
      localObject1 = (Config.Option)paramUseCaseConfig.next();
      ((Camera2ImplConfig.Builder)localObject2).setCaptureRequestOption((CaptureRequest.Key)((Config.Option)localObject1).getToken(), localCamera2ImplConfig.retrieveOption((Config.Option)localObject1));
    }
    paramBuilder.addImplementationOptions(((Camera2ImplConfig.Builder)localObject2).build());
  }
}
