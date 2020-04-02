package androidx.camera.core.impl;

public abstract class SurfaceConfig
{
  SurfaceConfig() {}
  
  public static SurfaceConfig create(ConfigType paramConfigType, ConfigSize paramConfigSize)
  {
    return new AutoValue_SurfaceConfig(paramConfigType, paramConfigSize);
  }
  
  public abstract ConfigSize getConfigSize();
  
  public abstract ConfigType getConfigType();
  
  public final boolean isSupported(SurfaceConfig paramSurfaceConfig)
  {
    ConfigType localConfigType = paramSurfaceConfig.getConfigType();
    boolean bool;
    if ((paramSurfaceConfig.getConfigSize().getId() <= getConfigSize().getId()) && (localConfigType == getConfigType())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static enum ConfigSize
  {
    final int mId;
    
    static
    {
      MAXIMUM = new ConfigSize("MAXIMUM", 3, 3);
      ConfigSize localConfigSize = new ConfigSize("NOT_SUPPORT", 4, 4);
      NOT_SUPPORT = localConfigSize;
      $VALUES = new ConfigSize[] { ANALYSIS, PREVIEW, RECORD, MAXIMUM, localConfigSize };
    }
    
    private ConfigSize(int paramInt)
    {
      this.mId = paramInt;
    }
    
    int getId()
    {
      return this.mId;
    }
  }
  
  public static enum ConfigType
  {
    static
    {
      JPEG = new ConfigType("JPEG", 2);
      ConfigType localConfigType = new ConfigType("RAW", 3);
      RAW = localConfigType;
      $VALUES = new ConfigType[] { PRIV, YUV, JPEG, localConfigType };
    }
    
    private ConfigType() {}
  }
}
