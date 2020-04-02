package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.FilteredAnnotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;

public class TypeSubstitutor
{
  public static final TypeSubstitutor EMPTY = create(TypeSubstitution.EMPTY);
  private final TypeSubstitution substitution;
  
  protected TypeSubstitutor(TypeSubstitution paramTypeSubstitution)
  {
    this.substitution = paramTypeSubstitution;
  }
  
  private static void assertRecursionDepth(int paramInt, TypeProjection paramTypeProjection, TypeSubstitution paramTypeSubstitution)
  {
    if (paramInt <= 100) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Recursion too deep. Most likely infinite loop while substituting ");
    localStringBuilder.append(safeToString(paramTypeProjection));
    localStringBuilder.append("; substitution: ");
    localStringBuilder.append(safeToString(paramTypeSubstitution));
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public static Variance combine(Variance paramVariance, TypeProjection paramTypeProjection)
  {
    if (paramVariance == null) {
      $$$reportNull$$$0(25);
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(26);
    }
    if (paramTypeProjection.isStarProjection())
    {
      paramVariance = Variance.OUT_VARIANCE;
      if (paramVariance == null) {
        $$$reportNull$$$0(27);
      }
      return paramVariance;
    }
    return combine(paramVariance, paramTypeProjection.getProjectionKind());
  }
  
  public static Variance combine(Variance paramVariance1, Variance paramVariance2)
  {
    if (paramVariance1 == null) {
      $$$reportNull$$$0(28);
    }
    if (paramVariance2 == null) {
      $$$reportNull$$$0(29);
    }
    if (paramVariance1 == Variance.INVARIANT)
    {
      if (paramVariance2 == null) {
        $$$reportNull$$$0(30);
      }
      return paramVariance2;
    }
    if (paramVariance2 == Variance.INVARIANT)
    {
      if (paramVariance1 == null) {
        $$$reportNull$$$0(31);
      }
      return paramVariance1;
    }
    if (paramVariance1 == paramVariance2)
    {
      if (paramVariance2 == null) {
        $$$reportNull$$$0(32);
      }
      return paramVariance2;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Variance conflict: type parameter variance '");
    localStringBuilder.append(paramVariance1);
    localStringBuilder.append("' and ");
    localStringBuilder.append("projection kind '");
    localStringBuilder.append(paramVariance2);
    localStringBuilder.append("' cannot be combined");
    throw new AssertionError(localStringBuilder.toString());
  }
  
  private static VarianceConflictType conflictType(Variance paramVariance1, Variance paramVariance2)
  {
    if ((paramVariance1 == Variance.IN_VARIANCE) && (paramVariance2 == Variance.OUT_VARIANCE)) {
      return VarianceConflictType.OUT_IN_IN_POSITION;
    }
    if ((paramVariance1 == Variance.OUT_VARIANCE) && (paramVariance2 == Variance.IN_VARIANCE)) {
      return VarianceConflictType.IN_IN_OUT_POSITION;
    }
    return VarianceConflictType.NO_CONFLICT;
  }
  
  public static TypeSubstitutor create(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(4);
    }
    return create(TypeConstructorSubstitution.create(paramKotlinType.getConstructor(), paramKotlinType.getArguments()));
  }
  
  public static TypeSubstitutor create(TypeSubstitution paramTypeSubstitution)
  {
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(0);
    }
    return new TypeSubstitutor(paramTypeSubstitution);
  }
  
  public static TypeSubstitutor createChainedSubstitutor(TypeSubstitution paramTypeSubstitution1, TypeSubstitution paramTypeSubstitution2)
  {
    if (paramTypeSubstitution1 == null) {
      $$$reportNull$$$0(1);
    }
    if (paramTypeSubstitution2 == null) {
      $$$reportNull$$$0(2);
    }
    return create(DisjointKeysUnionTypeSubstitution.create(paramTypeSubstitution1, paramTypeSubstitution2));
  }
  
  private static Annotations filterOutUnsafeVariance(Annotations paramAnnotations)
  {
    if (paramAnnotations == null) {
      $$$reportNull$$$0(23);
    }
    if (!paramAnnotations.hasAnnotation(KotlinBuiltIns.FQ_NAMES.unsafeVariance))
    {
      if (paramAnnotations == null) {
        $$$reportNull$$$0(24);
      }
      return paramAnnotations;
    }
    new FilteredAnnotations(paramAnnotations, new Function1()
    {
      public Boolean invoke(FqName paramAnonymousFqName)
      {
        if (paramAnonymousFqName == null) {
          $$$reportNull$$$0(0);
        }
        return Boolean.valueOf(paramAnonymousFqName.equals(KotlinBuiltIns.FQ_NAMES.unsafeVariance) ^ true);
      }
    });
  }
  
  private static String safeToString(Object paramObject)
  {
    try
    {
      paramObject = paramObject.toString();
      return paramObject;
    }
    finally
    {
      if (!ExceptionUtilsKt.isProcessCanceledException(localThrowable))
      {
        paramObject = new StringBuilder();
        paramObject.append("[Exception while computing toString(): ");
        paramObject.append(localThrowable);
        paramObject.append("]");
        return paramObject.toString();
      }
    }
  }
  
  private TypeProjection substituteCompoundType(TypeProjection paramTypeProjection, int paramInt)
    throws TypeSubstitutor.SubstitutionException
  {
    Object localObject1 = paramTypeProjection.getType();
    Variance localVariance = paramTypeProjection.getProjectionKind();
    if ((((KotlinType)localObject1).getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor)) {
      return paramTypeProjection;
    }
    paramTypeProjection = null;
    Object localObject2 = SpecialTypesKt.getAbbreviation((KotlinType)localObject1);
    if (localObject2 != null) {
      paramTypeProjection = substitute((KotlinType)localObject2, Variance.INVARIANT);
    }
    localObject2 = TypeSubstitutionKt.replace((KotlinType)localObject1, substituteTypeArguments(((KotlinType)localObject1).getConstructor().getParameters(), ((KotlinType)localObject1).getArguments(), paramInt), this.substitution.filterAnnotations(((KotlinType)localObject1).getAnnotations()));
    localObject1 = localObject2;
    if ((localObject2 instanceof SimpleType))
    {
      localObject1 = localObject2;
      if ((paramTypeProjection instanceof SimpleType)) {
        localObject1 = SpecialTypesKt.withAbbreviation((SimpleType)localObject2, (SimpleType)paramTypeProjection);
      }
    }
    return new TypeProjectionImpl(localVariance, (KotlinType)localObject1);
  }
  
  private List<TypeProjection> substituteTypeArguments(List<TypeParameterDescriptor> paramList, List<TypeProjection> paramList1, int paramInt)
    throws TypeSubstitutor.SubstitutionException
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    int i = 0;
    int j = 0;
    while (i < paramList.size())
    {
      TypeParameterDescriptor localTypeParameterDescriptor = (TypeParameterDescriptor)paramList.get(i);
      TypeProjection localTypeProjection1 = (TypeProjection)paramList1.get(i);
      TypeProjection localTypeProjection2 = unsafeSubstitute(localTypeProjection1, paramInt + 1);
      int k = 2.$SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[conflictType(localTypeParameterDescriptor.getVariance(), localTypeProjection2.getProjectionKind()).ordinal()];
      Object localObject;
      if ((k != 1) && (k != 2))
      {
        if (k != 3)
        {
          localObject = localTypeProjection2;
        }
        else
        {
          localObject = localTypeProjection2;
          if (localTypeParameterDescriptor.getVariance() != Variance.INVARIANT)
          {
            localObject = localTypeProjection2;
            if (!localTypeProjection2.isStarProjection()) {
              localObject = new TypeProjectionImpl(Variance.INVARIANT, localTypeProjection2.getType());
            }
          }
        }
      }
      else {
        localObject = TypeUtils.makeStarProjection(localTypeParameterDescriptor);
      }
      if (localObject != localTypeProjection1) {
        j = 1;
      }
      localArrayList.add(localObject);
      i++;
    }
    if (j == 0) {
      return paramList1;
    }
    return localArrayList;
  }
  
  private TypeProjection unsafeSubstitute(TypeProjection paramTypeProjection, int paramInt)
    throws TypeSubstitutor.SubstitutionException
  {
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(16);
    }
    assertRecursionDepth(paramInt, paramTypeProjection, this.substitution);
    if (paramTypeProjection.isStarProjection())
    {
      if (paramTypeProjection == null) {
        $$$reportNull$$$0(17);
      }
      return paramTypeProjection;
    }
    KotlinType localKotlinType = paramTypeProjection.getType();
    Object localObject1;
    Object localObject2;
    if ((localKotlinType instanceof TypeWithEnhancement))
    {
      localObject1 = (TypeWithEnhancement)localKotlinType;
      localObject2 = ((TypeWithEnhancement)localObject1).getOrigin();
      localObject1 = ((TypeWithEnhancement)localObject1).getEnhancement();
      localObject2 = unsafeSubstitute(new TypeProjectionImpl(paramTypeProjection.getProjectionKind(), (KotlinType)localObject2), paramInt + 1);
      paramTypeProjection = substitute((KotlinType)localObject1, paramTypeProjection.getProjectionKind());
      paramTypeProjection = TypeWithEnhancementKt.wrapEnhancement(((TypeProjection)localObject2).getType().unwrap(), paramTypeProjection);
      return new TypeProjectionImpl(((TypeProjection)localObject2).getProjectionKind(), paramTypeProjection);
    }
    if ((!DynamicTypesKt.isDynamic(localKotlinType)) && (!(localKotlinType.unwrap() instanceof RawType)))
    {
      Object localObject3 = this.substitution.get(localKotlinType);
      localObject1 = paramTypeProjection.getProjectionKind();
      Object localObject4;
      if ((localObject3 == null) && (FlexibleTypesKt.isFlexible(localKotlinType)) && (!TypeCapabilitiesKt.isCustomTypeVariable(localKotlinType)))
      {
        localObject2 = FlexibleTypesKt.asFlexibleType(localKotlinType);
        localObject3 = new TypeProjectionImpl((Variance)localObject1, ((FlexibleType)localObject2).getLowerBound());
        paramInt++;
        localObject3 = unsafeSubstitute((TypeProjection)localObject3, paramInt);
        localObject4 = unsafeSubstitute(new TypeProjectionImpl((Variance)localObject1, ((FlexibleType)localObject2).getUpperBound()), paramInt);
        localObject1 = ((TypeProjection)localObject3).getProjectionKind();
        if ((((TypeProjection)localObject3).getType() == ((FlexibleType)localObject2).getLowerBound()) && (((TypeProjection)localObject4).getType() == ((FlexibleType)localObject2).getUpperBound()))
        {
          if (paramTypeProjection == null) {
            $$$reportNull$$$0(19);
          }
          return paramTypeProjection;
        }
        return new TypeProjectionImpl((Variance)localObject1, KotlinTypeFactory.flexibleType(TypeSubstitutionKt.asSimpleType(((TypeProjection)localObject3).getType()), TypeSubstitutionKt.asSimpleType(((TypeProjection)localObject4).getType())));
      }
      if ((!KotlinBuiltIns.isNothing(localKotlinType)) && (!KotlinTypeKt.isError(localKotlinType)))
      {
        if (localObject3 != null)
        {
          localObject4 = conflictType((Variance)localObject1, ((TypeProjection)localObject3).getProjectionKind());
          if (!CapturedTypeConstructorKt.isCaptured(localKotlinType))
          {
            paramInt = 2.$SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[localObject4.ordinal()];
            if (paramInt != 1)
            {
              if (paramInt == 2) {
                return new TypeProjectionImpl(Variance.OUT_VARIANCE, localKotlinType.getConstructor().getBuiltIns().getNullableAnyType());
              }
            }
            else {
              throw new SubstitutionException("Out-projection in in-position");
            }
          }
          paramTypeProjection = TypeCapabilitiesKt.getCustomTypeVariable(localKotlinType);
          if (((TypeProjection)localObject3).isStarProjection())
          {
            if (localObject3 == null) {
              $$$reportNull$$$0(21);
            }
            return localObject3;
          }
          if (paramTypeProjection != null) {
            paramTypeProjection = paramTypeProjection.substitutionResult(((TypeProjection)localObject3).getType());
          } else {
            paramTypeProjection = TypeUtils.makeNullableIfNeeded(((TypeProjection)localObject3).getType(), localKotlinType.isMarkedNullable());
          }
          localObject2 = paramTypeProjection;
          if (!localKotlinType.getAnnotations().isEmpty())
          {
            localObject2 = filterOutUnsafeVariance(this.substitution.filterAnnotations(localKotlinType.getAnnotations()));
            localObject2 = TypeUtilsKt.replaceAnnotations(paramTypeProjection, new CompositeAnnotations(new Annotations[] { paramTypeProjection.getAnnotations(), localObject2 }));
          }
          paramTypeProjection = (TypeProjection)localObject1;
          if (localObject4 == VarianceConflictType.NO_CONFLICT) {
            paramTypeProjection = combine((Variance)localObject1, ((TypeProjection)localObject3).getProjectionKind());
          }
          return new TypeProjectionImpl(paramTypeProjection, (KotlinType)localObject2);
        }
        paramTypeProjection = substituteCompoundType(paramTypeProjection, paramInt);
        if (paramTypeProjection == null) {
          $$$reportNull$$$0(22);
        }
        return paramTypeProjection;
      }
      if (paramTypeProjection == null) {
        $$$reportNull$$$0(20);
      }
      return paramTypeProjection;
    }
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(18);
    }
    return paramTypeProjection;
  }
  
  public TypeSubstitution getSubstitution()
  {
    TypeSubstitution localTypeSubstitution = this.substitution;
    if (localTypeSubstitution == null) {
      $$$reportNull$$$0(6);
    }
    return localTypeSubstitution;
  }
  
  public boolean isEmpty()
  {
    return this.substitution.isEmpty();
  }
  
  public KotlinType safeSubstitute(KotlinType paramKotlinType, Variance paramVariance)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(7);
    }
    if (paramVariance == null) {
      $$$reportNull$$$0(8);
    }
    if (isEmpty())
    {
      if (paramKotlinType == null) {
        $$$reportNull$$$0(9);
      }
      return paramKotlinType;
    }
    try
    {
      TypeProjectionImpl localTypeProjectionImpl = new kotlin/reflect/jvm/internal/impl/types/TypeProjectionImpl;
      localTypeProjectionImpl.<init>(paramVariance, paramKotlinType);
      paramKotlinType = unsafeSubstitute(localTypeProjectionImpl, 0).getType();
      if (paramKotlinType == null) {
        $$$reportNull$$$0(10);
      }
      return paramKotlinType;
    }
    catch (SubstitutionException paramKotlinType)
    {
      paramKotlinType = ErrorUtils.createErrorType(paramKotlinType.getMessage());
      if (paramKotlinType == null) {
        $$$reportNull$$$0(11);
      }
    }
    return paramKotlinType;
  }
  
  public KotlinType substitute(KotlinType paramKotlinType, Variance paramVariance)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(12);
    }
    if (paramVariance == null) {
      $$$reportNull$$$0(13);
    }
    paramKotlinType = substitute(new TypeProjectionImpl(paramVariance, getSubstitution().prepareTopLevelType(paramKotlinType, paramVariance)));
    if (paramKotlinType == null) {
      paramKotlinType = null;
    } else {
      paramKotlinType = paramKotlinType.getType();
    }
    return paramKotlinType;
  }
  
  public TypeProjection substitute(TypeProjection paramTypeProjection)
  {
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(14);
    }
    paramTypeProjection = substituteWithoutApproximation(paramTypeProjection);
    if ((!this.substitution.approximateCapturedTypes()) && (!this.substitution.approximateContravariantCapturedTypes())) {
      return paramTypeProjection;
    }
    return CapturedTypeApproximationKt.approximateCapturedTypesIfNecessary(paramTypeProjection, this.substitution.approximateContravariantCapturedTypes());
  }
  
  public TypeProjection substituteWithoutApproximation(TypeProjection paramTypeProjection)
  {
    if (paramTypeProjection == null) {
      $$$reportNull$$$0(15);
    }
    if (isEmpty()) {
      return paramTypeProjection;
    }
    try
    {
      paramTypeProjection = unsafeSubstitute(paramTypeProjection, 0);
      return paramTypeProjection;
    }
    catch (SubstitutionException paramTypeProjection) {}
    return null;
  }
  
  private static final class SubstitutionException
    extends Exception
  {
    public SubstitutionException(String paramString)
    {
      super();
    }
  }
  
  private static enum VarianceConflictType
  {
    static
    {
      IN_IN_OUT_POSITION = new VarianceConflictType("IN_IN_OUT_POSITION", 1);
      VarianceConflictType localVarianceConflictType = new VarianceConflictType("OUT_IN_IN_POSITION", 2);
      OUT_IN_IN_POSITION = localVarianceConflictType;
      $VALUES = new VarianceConflictType[] { NO_CONFLICT, IN_IN_OUT_POSITION, localVarianceConflictType };
    }
    
    private VarianceConflictType() {}
  }
}
