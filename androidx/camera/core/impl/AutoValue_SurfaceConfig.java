package androidx.camera.core.impl;

final class AutoValue_SurfaceConfig
  extends SurfaceConfig
{
  private final SurfaceConfig.ConfigSize configSize;
  private final SurfaceConfig.ConfigType configType;
  
  AutoValue_SurfaceConfig(SurfaceConfig.ConfigType paramConfigType, SurfaceConfig.ConfigSize paramConfigSize)
  {
    if (paramConfigType != null)
    {
      this.configType = paramConfigType;
      if (paramConfigSize != null)
      {
        this.configSize = paramConfigSize;
        return;
      }
      throw new NullPointerException("Null configSize");
    }
    throw new NullPointerException("Null configType");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof SurfaceConfig))
    {
      paramObject = (SurfaceConfig)paramObject;
      if ((!this.configType.equals(paramObject.getConfigType())) || (!this.configSize.equals(paramObject.getConfigSize()))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public SurfaceConfig.ConfigSize getConfigSize()
  {
    return this.configSize;
  }
  
  public SurfaceConfig.ConfigType getConfigType()
  {
    return this.configType;
  }
  
  public int hashCode()
  {
    return (this.configType.hashCode() ^ 0xF4243) * 1000003 ^ this.configSize.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SurfaceConfig{configType=");
    localStringBuilder.append(this.configType);
    localStringBuilder.append(", configSize=");
    localStringBuilder.append(this.configSize);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
