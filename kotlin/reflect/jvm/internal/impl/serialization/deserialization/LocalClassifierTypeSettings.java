package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public abstract interface LocalClassifierTypeSettings
{
  public abstract SimpleType getReplacementTypeForLocalClassifiers();
  
  public static final class Default
    implements LocalClassifierTypeSettings
  {
    public static final Default INSTANCE = new Default();
    
    private Default() {}
    
    public SimpleType getReplacementTypeForLocalClassifiers()
    {
      return null;
    }
  }
}
