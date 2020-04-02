package kotlin.reflect.jvm.internal.impl.load.java.structure;

public abstract interface JavaArrayType
  extends JavaType
{
  public abstract JavaType getComponentType();
}
