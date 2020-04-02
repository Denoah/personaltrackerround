package androidx.camera.core;

import androidx.camera.core.impl.CameraDeviceSurfaceManager.Provider;
import androidx.camera.core.impl.CameraFactory.Provider;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.Config.OptionMatcher;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.UseCaseConfigFactory.Provider;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.TargetConfig.Builder;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class CameraXConfig
  implements TargetConfig<CameraX>, Config
{
  static final Config.Option<Executor> OPTION_CAMERA_EXECUTOR = Config.Option.create("camerax.core.appConfig.cameraExecutor", Executor.class);
  static final Config.Option<CameraFactory.Provider> OPTION_CAMERA_FACTORY_PROVIDER = Config.Option.create("camerax.core.appConfig.cameraFactoryProvider", CameraFactory.Provider.class);
  static final Config.Option<CameraDeviceSurfaceManager.Provider> OPTION_DEVICE_SURFACE_MANAGER_PROVIDER = Config.Option.create("camerax.core.appConfig.deviceSurfaceManagerProvider", CameraDeviceSurfaceManager.Provider.class);
  static final Config.Option<UseCaseConfigFactory.Provider> OPTION_USECASE_CONFIG_FACTORY_PROVIDER = Config.Option.create("camerax.core.appConfig.useCaseConfigFactoryProvider", UseCaseConfigFactory.Provider.class);
  private final OptionsBundle mConfig;
  
  CameraXConfig(OptionsBundle paramOptionsBundle)
  {
    this.mConfig = paramOptionsBundle;
  }
  
  public boolean containsOption(Config.Option<?> paramOption)
  {
    return this.mConfig.containsOption(paramOption);
  }
  
  public void findOptions(String paramString, Config.OptionMatcher paramOptionMatcher)
  {
    this.mConfig.findOptions(paramString, paramOptionMatcher);
  }
  
  public Executor getCameraExecutor(Executor paramExecutor)
  {
    return (Executor)this.mConfig.retrieveOption(OPTION_CAMERA_EXECUTOR, paramExecutor);
  }
  
  public CameraFactory.Provider getCameraFactoryProvider(CameraFactory.Provider paramProvider)
  {
    return (CameraFactory.Provider)this.mConfig.retrieveOption(OPTION_CAMERA_FACTORY_PROVIDER, paramProvider);
  }
  
  public CameraDeviceSurfaceManager.Provider getDeviceSurfaceManagerProvider(CameraDeviceSurfaceManager.Provider paramProvider)
  {
    return (CameraDeviceSurfaceManager.Provider)this.mConfig.retrieveOption(OPTION_DEVICE_SURFACE_MANAGER_PROVIDER, paramProvider);
  }
  
  public Class<CameraX> getTargetClass()
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS);
  }
  
  public Class<CameraX> getTargetClass(Class<CameraX> paramClass)
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS, paramClass);
  }
  
  public String getTargetName()
  {
    return (String)retrieveOption(OPTION_TARGET_NAME);
  }
  
  public String getTargetName(String paramString)
  {
    return (String)retrieveOption(OPTION_TARGET_NAME, paramString);
  }
  
  public UseCaseConfigFactory.Provider getUseCaseConfigFactoryProvider(UseCaseConfigFactory.Provider paramProvider)
  {
    return (UseCaseConfigFactory.Provider)this.mConfig.retrieveOption(OPTION_USECASE_CONFIG_FACTORY_PROVIDER, paramProvider);
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
    implements TargetConfig.Builder<CameraX, Builder>
  {
    private final MutableOptionsBundle mMutableConfig;
    
    public Builder()
    {
      this(MutableOptionsBundle.create());
    }
    
    private Builder(MutableOptionsBundle paramMutableOptionsBundle)
    {
      this.mMutableConfig = paramMutableOptionsBundle;
      paramMutableOptionsBundle = (Class)paramMutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
      if ((paramMutableOptionsBundle != null) && (!paramMutableOptionsBundle.equals(CameraX.class)))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Invalid target class configuration for ");
        localStringBuilder.append(this);
        localStringBuilder.append(": ");
        localStringBuilder.append(paramMutableOptionsBundle);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      setTargetClass(CameraX.class);
    }
    
    public static Builder fromConfig(CameraXConfig paramCameraXConfig)
    {
      return new Builder(MutableOptionsBundle.from(paramCameraXConfig));
    }
    
    private MutableConfig getMutableConfig()
    {
      return this.mMutableConfig;
    }
    
    public CameraXConfig build()
    {
      return new CameraXConfig(OptionsBundle.from(this.mMutableConfig));
    }
    
    public Builder setCameraExecutor(Executor paramExecutor)
    {
      getMutableConfig().insertOption(CameraXConfig.OPTION_CAMERA_EXECUTOR, paramExecutor);
      return this;
    }
    
    public Builder setCameraFactoryProvider(CameraFactory.Provider paramProvider)
    {
      getMutableConfig().insertOption(CameraXConfig.OPTION_CAMERA_FACTORY_PROVIDER, paramProvider);
      return this;
    }
    
    public Builder setDeviceSurfaceManagerProvider(CameraDeviceSurfaceManager.Provider paramProvider)
    {
      getMutableConfig().insertOption(CameraXConfig.OPTION_DEVICE_SURFACE_MANAGER_PROVIDER, paramProvider);
      return this;
    }
    
    public Builder setTargetClass(Class<CameraX> paramClass)
    {
      getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, paramClass);
      if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramClass.getCanonicalName());
        localStringBuilder.append("-");
        localStringBuilder.append(UUID.randomUUID());
        setTargetName(localStringBuilder.toString());
      }
      return this;
    }
    
    public Builder setTargetName(String paramString)
    {
      getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, paramString);
      return this;
    }
    
    public Builder setUseCaseConfigFactoryProvider(UseCaseConfigFactory.Provider paramProvider)
    {
      getMutableConfig().insertOption(CameraXConfig.OPTION_USECASE_CONFIG_FACTORY_PROVIDER, paramProvider);
      return this;
    }
  }
  
  public static abstract interface Provider
  {
    public abstract CameraXConfig getCameraXConfig();
  }
}
