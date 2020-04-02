package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;

public final class ClassicTypeSystemContextKt
{
  public static final TypeVariance convertVariance(Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramVariance, "$this$convertVariance");
    int i = ClassicTypeSystemContextKt.WhenMappings.$EnumSwitchMapping$1[paramVariance.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i == 3) {
          paramVariance = TypeVariance.OUT;
        } else {
          throw new NoWhenBranchMatchedException();
        }
      }
      else {
        paramVariance = TypeVariance.IN;
      }
    }
    else {
      paramVariance = TypeVariance.INV;
    }
    return paramVariance;
  }
}
