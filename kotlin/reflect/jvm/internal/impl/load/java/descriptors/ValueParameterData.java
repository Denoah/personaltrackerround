package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class ValueParameterData
{
  private final boolean hasDefaultValue;
  private final KotlinType type;
  
  public ValueParameterData(KotlinType paramKotlinType, boolean paramBoolean)
  {
    this.type = paramKotlinType;
    this.hasDefaultValue = paramBoolean;
  }
  
  public final boolean getHasDefaultValue()
  {
    return this.hasDefaultValue;
  }
  
  public final KotlinType getType()
  {
    return this.type;
  }
}
