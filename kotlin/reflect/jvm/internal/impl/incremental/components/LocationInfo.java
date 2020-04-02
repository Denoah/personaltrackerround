package kotlin.reflect.jvm.internal.impl.incremental.components;

public abstract interface LocationInfo
{
  public abstract String getFilePath();
  
  public abstract Position getPosition();
}
