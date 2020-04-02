package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class ConstUtil
{
  public static final ConstUtil INSTANCE = new ConstUtil();
  
  private ConstUtil() {}
  
  @JvmStatic
  public static final boolean canBeUsedForConstVal(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    return ConstUtilKt.canBeUsedForConstVal(paramKotlinType);
  }
}
