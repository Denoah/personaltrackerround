package androidx.camera.core.impl;

final class AutoValue_DeviceProperties
  extends DeviceProperties
{
  private final String manufacturer;
  private final String model;
  private final int sdkVersion;
  
  AutoValue_DeviceProperties(String paramString1, String paramString2, int paramInt)
  {
    if (paramString1 != null)
    {
      this.manufacturer = paramString1;
      if (paramString2 != null)
      {
        this.model = paramString2;
        this.sdkVersion = paramInt;
        return;
      }
      throw new NullPointerException("Null model");
    }
    throw new NullPointerException("Null manufacturer");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof DeviceProperties))
    {
      paramObject = (DeviceProperties)paramObject;
      if ((!this.manufacturer.equals(paramObject.manufacturer())) || (!this.model.equals(paramObject.model())) || (this.sdkVersion != paramObject.sdkVersion())) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int hashCode()
  {
    return ((this.manufacturer.hashCode() ^ 0xF4243) * 1000003 ^ this.model.hashCode()) * 1000003 ^ this.sdkVersion;
  }
  
  public String manufacturer()
  {
    return this.manufacturer;
  }
  
  public String model()
  {
    return this.model;
  }
  
  public int sdkVersion()
  {
    return this.sdkVersion;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DeviceProperties{manufacturer=");
    localStringBuilder.append(this.manufacturer);
    localStringBuilder.append(", model=");
    localStringBuilder.append(this.model);
    localStringBuilder.append(", sdkVersion=");
    localStringBuilder.append(this.sdkVersion);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
