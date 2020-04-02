package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

public abstract interface NameResolver
{
  public abstract String getQualifiedClassName(int paramInt);
  
  public abstract String getString(int paramInt);
  
  public abstract boolean isLocalClassName(int paramInt);
}
