package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;

public abstract interface TypeCheckingProcedureCallbacks
{
  public abstract boolean assertEqualTypeConstructors(TypeConstructor paramTypeConstructor1, TypeConstructor paramTypeConstructor2);
  
  public abstract boolean assertEqualTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeCheckingProcedure paramTypeCheckingProcedure);
  
  public abstract boolean assertSubtype(KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeCheckingProcedure paramTypeCheckingProcedure);
  
  public abstract boolean capture(KotlinType paramKotlinType, TypeProjection paramTypeProjection);
  
  public abstract boolean noCorrespondingSupertype(KotlinType paramKotlinType1, KotlinType paramKotlinType2);
}
