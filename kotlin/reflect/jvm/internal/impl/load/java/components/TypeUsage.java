package kotlin.reflect.jvm.internal.impl.load.java.components;

public enum TypeUsage
{
  static
  {
    TypeUsage localTypeUsage = new TypeUsage("COMMON", 1);
    COMMON = localTypeUsage;
    $VALUES = new TypeUsage[] { SUPERTYPE, localTypeUsage };
  }
  
  private TypeUsage() {}
}
