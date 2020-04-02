package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class StarProjectionForAbsentTypeParameter
  extends TypeProjectionBase
{
  private final KotlinType nullableAnyType;
  
  public StarProjectionForAbsentTypeParameter(KotlinBuiltIns paramKotlinBuiltIns)
  {
    paramKotlinBuiltIns = paramKotlinBuiltIns.getNullableAnyType();
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinBuiltIns, "kotlinBuiltIns.nullableAnyType");
    this.nullableAnyType = ((KotlinType)paramKotlinBuiltIns);
  }
  
  public Variance getProjectionKind()
  {
    return Variance.OUT_VARIANCE;
  }
  
  public KotlinType getType()
  {
    return this.nullableAnyType;
  }
  
  public boolean isStarProjection()
  {
    return true;
  }
  
  public TypeProjection refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return (TypeProjection)this;
  }
}
