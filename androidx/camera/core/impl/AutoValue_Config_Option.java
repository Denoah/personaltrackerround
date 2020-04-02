package androidx.camera.core.impl;

final class AutoValue_Config_Option<T>
  extends Config.Option<T>
{
  private final String id;
  private final Object token;
  private final Class<T> valueClass;
  
  AutoValue_Config_Option(String paramString, Class<T> paramClass, Object paramObject)
  {
    if (paramString != null)
    {
      this.id = paramString;
      if (paramClass != null)
      {
        this.valueClass = paramClass;
        this.token = paramObject;
        return;
      }
      throw new NullPointerException("Null valueClass");
    }
    throw new NullPointerException("Null id");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof Config.Option))
    {
      paramObject = (Config.Option)paramObject;
      if ((this.id.equals(paramObject.getId())) && (this.valueClass.equals(paramObject.getValueClass())))
      {
        Object localObject = this.token;
        if (localObject == null ? paramObject.getToken() == null : localObject.equals(paramObject.getToken())) {}
      }
      else
      {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public Object getToken()
  {
    return this.token;
  }
  
  public Class<T> getValueClass()
  {
    return this.valueClass;
  }
  
  public int hashCode()
  {
    int i = this.id.hashCode();
    int j = this.valueClass.hashCode();
    Object localObject = this.token;
    int k;
    if (localObject == null) {
      k = 0;
    } else {
      k = localObject.hashCode();
    }
    return ((i ^ 0xF4243) * 1000003 ^ j) * 1000003 ^ k;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Option{id=");
    localStringBuilder.append(this.id);
    localStringBuilder.append(", valueClass=");
    localStringBuilder.append(this.valueClass);
    localStringBuilder.append(", token=");
    localStringBuilder.append(this.token);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
