package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

final class SimpleResult
  extends Result
{
  private final SimpleType type;
  
  public SimpleResult(SimpleType paramSimpleType, int paramInt, boolean paramBoolean)
  {
    super((KotlinType)paramSimpleType, paramInt, paramBoolean);
    this.type = paramSimpleType;
  }
  
  public SimpleType getType()
  {
    return this.type;
  }
}
