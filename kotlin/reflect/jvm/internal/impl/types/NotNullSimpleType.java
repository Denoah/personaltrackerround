package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

final class NotNullSimpleType
  extends DelegatingSimpleTypeImpl
{
  public NotNullSimpleType(SimpleType paramSimpleType)
  {
    super(paramSimpleType);
  }
  
  public boolean isMarkedNullable()
  {
    return false;
  }
  
  public NotNullSimpleType replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new NotNullSimpleType(paramSimpleType);
  }
}
