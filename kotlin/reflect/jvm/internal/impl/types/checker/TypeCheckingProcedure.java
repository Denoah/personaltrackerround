package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeCapabilitiesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public class TypeCheckingProcedure
{
  private final TypeCheckingProcedureCallbacks constraints;
  
  public TypeCheckingProcedure(TypeCheckingProcedureCallbacks paramTypeCheckingProcedureCallbacks)
  {
    this.constraints = paramTypeCheckingProcedureCallbacks;
  }
  
  private boolean capture(TypeProjection paramTypeProjection1, TypeProjection paramTypeProjection2, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    if (paramTypeProjection1 == null) {
      $$$reportNull$$$0(19);
    }
    if (paramTypeProjection2 == null) {
      $$$reportNull$$$0(20);
    }
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(21);
    }
    if (paramTypeParameterDescriptor.getVariance() != Variance.INVARIANT) {
      return false;
    }
    if ((paramTypeProjection1.getProjectionKind() != Variance.INVARIANT) && (paramTypeProjection2.getProjectionKind() == Variance.INVARIANT)) {
      return this.constraints.capture(paramTypeProjection2.getType(), paramTypeProjection1);
    }
    return false;
  }
  
  private boolean checkSubtypeForTheSameConstructor(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(17);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(18);
    }
    Object localObject1 = paramKotlinType1.getConstructor();
    paramKotlinType1 = paramKotlinType1.getArguments();
    paramKotlinType2 = paramKotlinType2.getArguments();
    if (paramKotlinType1.size() != paramKotlinType2.size()) {
      return false;
    }
    List localList = ((TypeConstructor)localObject1).getParameters();
    for (int i = 0;; i++)
    {
      int j = localList.size();
      int k = 1;
      if (i >= j) {
        break;
      }
      TypeParameterDescriptor localTypeParameterDescriptor = (TypeParameterDescriptor)localList.get(i);
      localObject1 = (TypeProjection)paramKotlinType2.get(i);
      Object localObject2 = (TypeProjection)paramKotlinType1.get(i);
      if ((!((TypeProjection)localObject1).isStarProjection()) && (!capture((TypeProjection)localObject2, (TypeProjection)localObject1, localTypeParameterDescriptor)))
      {
        j = k;
        if (!KotlinTypeKt.isError(((TypeProjection)localObject2).getType())) {
          if (KotlinTypeKt.isError(((TypeProjection)localObject1).getType())) {
            j = k;
          } else {
            j = 0;
          }
        }
        if ((j == 0) && (localTypeParameterDescriptor.getVariance() == Variance.INVARIANT) && (((TypeProjection)localObject2).getProjectionKind() == Variance.INVARIANT) && (((TypeProjection)localObject1).getProjectionKind() == Variance.INVARIANT))
        {
          if (!this.constraints.assertEqualTypes(((TypeProjection)localObject2).getType(), ((TypeProjection)localObject1).getType(), this)) {
            return false;
          }
        }
        else
        {
          KotlinType localKotlinType1 = getOutType(localTypeParameterDescriptor, (TypeProjection)localObject1);
          KotlinType localKotlinType2 = getOutType(localTypeParameterDescriptor, (TypeProjection)localObject2);
          if (!this.constraints.assertSubtype(localKotlinType2, localKotlinType1, this)) {
            return false;
          }
          localKotlinType2 = getInType(localTypeParameterDescriptor, (TypeProjection)localObject1);
          localObject2 = getInType(localTypeParameterDescriptor, (TypeProjection)localObject2);
          if ((((TypeProjection)localObject1).getProjectionKind() != Variance.OUT_VARIANCE) && (!this.constraints.assertSubtype(localKotlinType2, (KotlinType)localObject2, this))) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  public static KotlinType findCorrespondingSupertype(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(0);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(1);
    }
    return findCorrespondingSupertype(paramKotlinType1, paramKotlinType2, new TypeCheckerProcedureCallbacksImpl());
  }
  
  public static KotlinType findCorrespondingSupertype(KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeCheckingProcedureCallbacks paramTypeCheckingProcedureCallbacks)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(2);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(3);
    }
    if (paramTypeCheckingProcedureCallbacks == null) {
      $$$reportNull$$$0(4);
    }
    return UtilsKt.findCorrespondingSupertype(paramKotlinType1, paramKotlinType2, paramTypeCheckingProcedureCallbacks);
  }
  
  public static EnrichedProjectionKind getEffectiveProjectionKind(TypeParameterDescriptor paramTypeParameterDescriptor, TypeProjection paramTypeProjection)
  {
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(14);
    }
    Variance localVariance = paramTypeParameterDescriptor.getVariance();
    paramTypeParameterDescriptor = paramTypeProjection.getProjectionKind();
    Object localObject = localVariance;
    paramTypeProjection = paramTypeParameterDescriptor;
    if (paramTypeParameterDescriptor == Variance.INVARIANT)
    {
      paramTypeProjection = localVariance;
      localObject = paramTypeParameterDescriptor;
    }
    if ((localObject == Variance.IN_VARIANCE) && (paramTypeProjection == Variance.OUT_VARIANCE)) {
      return EnrichedProjectionKind.STAR;
    }
    if ((localObject == Variance.OUT_VARIANCE) && (paramTypeProjection == Variance.IN_VARIANCE)) {
      return EnrichedProjectionKind.STAR;
    }
    return EnrichedProjectionKind.fromVariance(paramTypeProjection);
  }
  
  private static KotlinType getInType(TypeParameterDescriptor paramTypeParameterDescriptor, TypeProjection paramTypeProjection)
  {
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(8);
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(9);
    }
    int i;
    if ((paramTypeProjection.getProjectionKind() != Variance.OUT_VARIANCE) && (paramTypeParameterDescriptor.getVariance() != Variance.OUT_VARIANCE)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0) {
      paramTypeParameterDescriptor = DescriptorUtilsKt.getBuiltIns(paramTypeParameterDescriptor).getNothingType();
    } else {
      paramTypeParameterDescriptor = paramTypeProjection.getType();
    }
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(10);
    }
    return paramTypeParameterDescriptor;
  }
  
  private static KotlinType getOutType(TypeParameterDescriptor paramTypeParameterDescriptor, TypeProjection paramTypeProjection)
  {
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(6);
    }
    int i;
    if ((paramTypeProjection.getProjectionKind() != Variance.IN_VARIANCE) && (paramTypeParameterDescriptor.getVariance() != Variance.IN_VARIANCE)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0) {
      paramTypeParameterDescriptor = DescriptorUtilsKt.getBuiltIns(paramTypeParameterDescriptor).getNullableAnyType();
    } else {
      paramTypeParameterDescriptor = paramTypeProjection.getType();
    }
    if (paramTypeParameterDescriptor == null) {
      $$$reportNull$$$0(7);
    }
    return paramTypeParameterDescriptor;
  }
  
  private boolean isSubtypeOfForRepresentatives(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if ((!KotlinTypeKt.isError(paramKotlinType1)) && (!KotlinTypeKt.isError(paramKotlinType2)))
    {
      if ((!paramKotlinType2.isMarkedNullable()) && (paramKotlinType1.isMarkedNullable())) {
        return false;
      }
      if (KotlinBuiltIns.isNothingOrNullableNothing(paramKotlinType1)) {
        return true;
      }
      KotlinType localKotlinType = findCorrespondingSupertype(paramKotlinType1, paramKotlinType2, this.constraints);
      if (localKotlinType == null) {
        return this.constraints.noCorrespondingSupertype(paramKotlinType1, paramKotlinType2);
      }
      if ((!paramKotlinType2.isMarkedNullable()) && (localKotlinType.isMarkedNullable())) {
        return false;
      }
      return checkSubtypeForTheSameConstructor(localKotlinType, paramKotlinType2);
    }
    return true;
  }
  
  public boolean equalTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(11);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(12);
    }
    boolean bool = true;
    if (paramKotlinType1 == paramKotlinType2) {
      return true;
    }
    if (FlexibleTypesKt.isFlexible(paramKotlinType1))
    {
      if (FlexibleTypesKt.isFlexible(paramKotlinType2))
      {
        if ((KotlinTypeKt.isError(paramKotlinType1)) || (KotlinTypeKt.isError(paramKotlinType2)) || (!isSubtypeOf(paramKotlinType1, paramKotlinType2)) || (!isSubtypeOf(paramKotlinType2, paramKotlinType1))) {
          bool = false;
        }
        return bool;
      }
      return heterogeneousEquivalence(paramKotlinType2, paramKotlinType1);
    }
    if (FlexibleTypesKt.isFlexible(paramKotlinType2)) {
      return heterogeneousEquivalence(paramKotlinType1, paramKotlinType2);
    }
    if (paramKotlinType1.isMarkedNullable() != paramKotlinType2.isMarkedNullable()) {
      return false;
    }
    if (paramKotlinType1.isMarkedNullable()) {
      return this.constraints.assertEqualTypes(TypeUtils.makeNotNullable(paramKotlinType1), TypeUtils.makeNotNullable(paramKotlinType2), this);
    }
    TypeConstructor localTypeConstructor1 = paramKotlinType1.getConstructor();
    TypeConstructor localTypeConstructor2 = paramKotlinType2.getConstructor();
    if (!this.constraints.assertEqualTypeConstructors(localTypeConstructor1, localTypeConstructor2)) {
      return false;
    }
    paramKotlinType1 = paramKotlinType1.getArguments();
    List localList = paramKotlinType2.getArguments();
    if (paramKotlinType1.size() != localList.size()) {
      return false;
    }
    for (int i = 0; i < paramKotlinType1.size(); i++)
    {
      paramKotlinType2 = (TypeProjection)paramKotlinType1.get(i);
      TypeProjection localTypeProjection = (TypeProjection)localList.get(i);
      if ((!paramKotlinType2.isStarProjection()) || (!localTypeProjection.isStarProjection()))
      {
        TypeParameterDescriptor localTypeParameterDescriptor1 = (TypeParameterDescriptor)localTypeConstructor1.getParameters().get(i);
        TypeParameterDescriptor localTypeParameterDescriptor2 = (TypeParameterDescriptor)localTypeConstructor2.getParameters().get(i);
        if (!capture(paramKotlinType2, localTypeProjection, localTypeParameterDescriptor1))
        {
          if (getEffectiveProjectionKind(localTypeParameterDescriptor1, paramKotlinType2) != getEffectiveProjectionKind(localTypeParameterDescriptor2, localTypeProjection)) {
            return false;
          }
          if (!this.constraints.assertEqualTypes(paramKotlinType2.getType(), localTypeProjection.getType(), this)) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  protected boolean heterogeneousEquivalence(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    boolean bool;
    if ((isSubtypeOf(FlexibleTypesKt.asFlexibleType(paramKotlinType2).getLowerBound(), paramKotlinType1)) && (isSubtypeOf(paramKotlinType1, FlexibleTypesKt.asFlexibleType(paramKotlinType2).getUpperBound()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isSubtypeOf(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(15);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(16);
    }
    if (TypeCapabilitiesKt.sameTypeConstructors(paramKotlinType1, paramKotlinType2))
    {
      boolean bool;
      if ((paramKotlinType1.isMarkedNullable()) && (!paramKotlinType2.isMarkedNullable())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    KotlinType localKotlinType1 = TypeCapabilitiesKt.getSubtypeRepresentative(paramKotlinType1);
    KotlinType localKotlinType2 = TypeCapabilitiesKt.getSupertypeRepresentative(paramKotlinType2);
    if ((localKotlinType1 == paramKotlinType1) && (localKotlinType2 == paramKotlinType2)) {
      return isSubtypeOfForRepresentatives(paramKotlinType1, paramKotlinType2);
    }
    return isSubtypeOf(localKotlinType1, localKotlinType2);
  }
  
  public static enum EnrichedProjectionKind
  {
    static
    {
      INV = new EnrichedProjectionKind("INV", 2);
      EnrichedProjectionKind localEnrichedProjectionKind = new EnrichedProjectionKind("STAR", 3);
      STAR = localEnrichedProjectionKind;
      $VALUES = new EnrichedProjectionKind[] { IN, OUT, INV, localEnrichedProjectionKind };
    }
    
    private EnrichedProjectionKind() {}
    
    public static EnrichedProjectionKind fromVariance(Variance paramVariance)
    {
      if (paramVariance == null) {
        $$$reportNull$$$0(0);
      }
      int i = TypeCheckingProcedure.1.$SwitchMap$org$jetbrains$kotlin$types$Variance[paramVariance.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i == 3)
          {
            paramVariance = OUT;
            if (paramVariance == null) {
              $$$reportNull$$$0(3);
            }
            return paramVariance;
          }
          throw new IllegalStateException("Unknown variance");
        }
        paramVariance = IN;
        if (paramVariance == null) {
          $$$reportNull$$$0(2);
        }
        return paramVariance;
      }
      paramVariance = INV;
      if (paramVariance == null) {
        $$$reportNull$$$0(1);
      }
      return paramVariance;
    }
  }
}
