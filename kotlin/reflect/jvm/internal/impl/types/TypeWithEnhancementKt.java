package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

public final class TypeWithEnhancementKt
{
  public static final KotlinType getEnhancement(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getEnhancement");
    if ((paramKotlinType instanceof TypeWithEnhancement)) {
      paramKotlinType = ((TypeWithEnhancement)paramKotlinType).getEnhancement();
    } else {
      paramKotlinType = null;
    }
    return paramKotlinType;
  }
  
  public static final UnwrappedType inheritEnhancement(UnwrappedType paramUnwrappedType, KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "$this$inheritEnhancement");
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "origin");
    return wrapEnhancement(paramUnwrappedType, getEnhancement(paramKotlinType));
  }
  
  public static final KotlinType unwrapEnhancement(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$unwrapEnhancement");
    KotlinType localKotlinType = getEnhancement(paramKotlinType);
    if (localKotlinType != null) {
      paramKotlinType = localKotlinType;
    }
    return paramKotlinType;
  }
  
  public static final UnwrappedType wrapEnhancement(UnwrappedType paramUnwrappedType, KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "$this$wrapEnhancement");
    if (paramKotlinType == null) {
      return paramUnwrappedType;
    }
    if ((paramUnwrappedType instanceof SimpleType))
    {
      paramUnwrappedType = (UnwrappedType)new SimpleTypeWithEnhancement((SimpleType)paramUnwrappedType, paramKotlinType);
    }
    else
    {
      if (!(paramUnwrappedType instanceof FlexibleType)) {
        break label63;
      }
      paramUnwrappedType = (UnwrappedType)new FlexibleTypeWithEnhancement((FlexibleType)paramUnwrappedType, paramKotlinType);
    }
    return paramUnwrappedType;
    label63:
    throw new NoWhenBranchMatchedException();
  }
}
