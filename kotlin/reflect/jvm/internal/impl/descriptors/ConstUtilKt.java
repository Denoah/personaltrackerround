package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;

public final class ConstUtilKt
{
  public static final boolean canBeUsedForConstVal(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$canBeUsedForConstVal");
    boolean bool;
    if (((!KotlinBuiltIns.isPrimitiveType(paramKotlinType)) && (!UnsignedTypes.INSTANCE.isUnsignedType(paramKotlinType))) || ((TypeUtils.isNullableType(paramKotlinType)) && (!KotlinBuiltIns.isString(paramKotlinType)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
