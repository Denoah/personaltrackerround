package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

public final class FlexibleTypesKt
{
  public static final FlexibleType asFlexibleType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$asFlexibleType");
    paramKotlinType = paramKotlinType.unwrap();
    if (paramKotlinType != null) {
      return (FlexibleType)paramKotlinType;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
  }
  
  public static final boolean isFlexible(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isFlexible");
    return paramKotlinType.unwrap() instanceof FlexibleType;
  }
  
  public static final SimpleType lowerIfFlexible(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$lowerIfFlexible");
    paramKotlinType = paramKotlinType.unwrap();
    if ((paramKotlinType instanceof FlexibleType))
    {
      paramKotlinType = ((FlexibleType)paramKotlinType).getLowerBound();
    }
    else
    {
      if (!(paramKotlinType instanceof SimpleType)) {
        break label43;
      }
      paramKotlinType = (SimpleType)paramKotlinType;
    }
    return paramKotlinType;
    label43:
    throw new NoWhenBranchMatchedException();
  }
  
  public static final SimpleType upperIfFlexible(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$upperIfFlexible");
    paramKotlinType = paramKotlinType.unwrap();
    if ((paramKotlinType instanceof FlexibleType))
    {
      paramKotlinType = ((FlexibleType)paramKotlinType).getUpperBound();
    }
    else
    {
      if (!(paramKotlinType instanceof SimpleType)) {
        break label43;
      }
      paramKotlinType = (SimpleType)paramKotlinType;
    }
    return paramKotlinType;
    label43:
    throw new NoWhenBranchMatchedException();
  }
}
