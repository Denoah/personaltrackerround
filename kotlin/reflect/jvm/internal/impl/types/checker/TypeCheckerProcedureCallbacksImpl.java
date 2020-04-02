package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;

class TypeCheckerProcedureCallbacksImpl
  implements TypeCheckingProcedureCallbacks
{
  TypeCheckerProcedureCallbacksImpl() {}
  
  public boolean assertEqualTypeConstructors(TypeConstructor paramTypeConstructor1, TypeConstructor paramTypeConstructor2)
  {
    if (paramTypeConstructor1 == null) {
      $$$reportNull$$$0(3);
    }
    if (paramTypeConstructor2 == null) {
      $$$reportNull$$$0(4);
    }
    return paramTypeConstructor1.equals(paramTypeConstructor2);
  }
  
  public boolean assertEqualTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeCheckingProcedure paramTypeCheckingProcedure)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(0);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(1);
    }
    if (paramTypeCheckingProcedure == null) {
      $$$reportNull$$$0(2);
    }
    return paramTypeCheckingProcedure.equalTypes(paramKotlinType1, paramKotlinType2);
  }
  
  public boolean assertSubtype(KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeCheckingProcedure paramTypeCheckingProcedure)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(5);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(6);
    }
    if (paramTypeCheckingProcedure == null) {
      $$$reportNull$$$0(7);
    }
    return paramTypeCheckingProcedure.isSubtypeOf(paramKotlinType1, paramKotlinType2);
  }
  
  public boolean capture(KotlinType paramKotlinType, TypeProjection paramTypeProjection)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(8);
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(9);
    }
    return false;
  }
  
  public boolean noCorrespondingSupertype(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(10);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(11);
    }
    return false;
  }
}
