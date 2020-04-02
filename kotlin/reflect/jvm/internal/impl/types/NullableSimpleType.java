package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

final class NullableSimpleType
  extends DelegatingSimpleTypeImpl
{
  public NullableSimpleType(SimpleType paramSimpleType)
  {
    super(paramSimpleType);
  }
  
  public boolean isMarkedNullable()
  {
    return true;
  }
  
  public NullableSimpleType replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new NullableSimpleType(paramSimpleType);
  }
}
