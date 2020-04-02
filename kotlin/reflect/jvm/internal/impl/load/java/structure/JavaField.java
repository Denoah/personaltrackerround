package kotlin.reflect.jvm.internal.impl.load.java.structure;

public abstract interface JavaField
  extends JavaMember
{
  public abstract boolean getHasConstantNotNullInitializer();
  
  public abstract JavaType getType();
  
  public abstract boolean isEnumEntry();
}
