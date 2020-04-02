package kotlin.reflect.jvm.internal.impl.load.java.structure;

public abstract interface JavaWildcardType
  extends JavaType
{
  public abstract JavaType getBound();
  
  public abstract boolean isExtends();
}
