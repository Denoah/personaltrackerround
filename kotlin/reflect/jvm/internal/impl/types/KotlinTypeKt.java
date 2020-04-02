package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

public final class KotlinTypeKt
{
  public static final boolean isError(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isError");
    paramKotlinType = paramKotlinType.unwrap();
    boolean bool;
    if ((!(paramKotlinType instanceof ErrorType)) && ((!(paramKotlinType instanceof FlexibleType)) || (!(((FlexibleType)paramKotlinType).getDelegate() instanceof ErrorType)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
