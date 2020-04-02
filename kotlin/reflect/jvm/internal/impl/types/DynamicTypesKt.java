package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

public final class DynamicTypesKt
{
  public static final boolean isDynamic(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isDynamic");
    return paramKotlinType.unwrap() instanceof DynamicType;
  }
}
