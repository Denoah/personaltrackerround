package androidx.camera.core.impl;

import androidx.camera.core.CameraInfo;
import java.util.HashMap;
import java.util.Map;

public final class ExtendableUseCaseConfigFactory
  implements UseCaseConfigFactory
{
  private final Map<Class<?>, ConfigProvider<?>> mDefaultProviders = new HashMap();
  
  public ExtendableUseCaseConfigFactory() {}
  
  public <C extends UseCaseConfig<?>> C getConfig(Class<C> paramClass, CameraInfo paramCameraInfo)
  {
    paramClass = (ConfigProvider)this.mDefaultProviders.get(paramClass);
    if (paramClass != null) {
      return (UseCaseConfig)paramClass.getConfig(paramCameraInfo);
    }
    return null;
  }
  
  public <C extends Config> void installDefaultProvider(Class<C> paramClass, ConfigProvider<C> paramConfigProvider)
  {
    this.mDefaultProviders.put(paramClass, paramConfigProvider);
  }
}
