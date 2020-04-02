package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public class KotlinTypeCheckerImpl
  implements KotlinTypeChecker
{
  private final TypeCheckingProcedure procedure;
  
  protected KotlinTypeCheckerImpl(TypeCheckingProcedure paramTypeCheckingProcedure)
  {
    this.procedure = paramTypeCheckingProcedure;
  }
  
  public static KotlinTypeChecker withAxioms(KotlinTypeChecker.TypeConstructorEquality paramTypeConstructorEquality)
  {
    if (paramTypeConstructorEquality == null) {
      $$$reportNull$$$0(0);
    }
    new KotlinTypeCheckerImpl(new TypeCheckingProcedure(new TypeCheckerProcedureCallbacksImpl()
    {
      public boolean assertEqualTypeConstructors(TypeConstructor paramAnonymousTypeConstructor1, TypeConstructor paramAnonymousTypeConstructor2)
      {
        boolean bool = false;
        if (paramAnonymousTypeConstructor1 == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousTypeConstructor2 == null) {
          $$$reportNull$$$0(1);
        }
        if ((paramAnonymousTypeConstructor1.equals(paramAnonymousTypeConstructor2)) || (this.val$equalityAxioms.equals(paramAnonymousTypeConstructor1, paramAnonymousTypeConstructor2))) {
          bool = true;
        }
        return bool;
      }
    }));
  }
  
  public boolean equalTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(4);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(5);
    }
    return this.procedure.equalTypes(paramKotlinType1, paramKotlinType2);
  }
  
  public boolean isSubtypeOf(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(2);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(3);
    }
    return this.procedure.isSubtypeOf(paramKotlinType1, paramKotlinType2);
  }
}
