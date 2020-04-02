package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class TypeAliasExpander
{
  public static final Companion Companion = new Companion(null);
  private static final TypeAliasExpander NON_REPORTING = new TypeAliasExpander((TypeAliasExpansionReportStrategy)TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false);
  private final TypeAliasExpansionReportStrategy reportStrategy;
  private final boolean shouldCheckBounds;
  
  public TypeAliasExpander(TypeAliasExpansionReportStrategy paramTypeAliasExpansionReportStrategy, boolean paramBoolean)
  {
    this.reportStrategy = paramTypeAliasExpansionReportStrategy;
    this.shouldCheckBounds = paramBoolean;
  }
  
  private final void checkRepeatedAnnotations(Annotations paramAnnotations1, Annotations paramAnnotations2)
  {
    Object localObject = (Iterable)paramAnnotations1;
    paramAnnotations1 = (Collection)new HashSet();
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramAnnotations1.add(((AnnotationDescriptor)((Iterator)localObject).next()).getFqName());
    }
    paramAnnotations1 = (HashSet)paramAnnotations1;
    paramAnnotations2 = paramAnnotations2.iterator();
    while (paramAnnotations2.hasNext())
    {
      localObject = (AnnotationDescriptor)paramAnnotations2.next();
      if (paramAnnotations1.contains(((AnnotationDescriptor)localObject).getFqName())) {
        this.reportStrategy.repeatedAnnotation((AnnotationDescriptor)localObject);
      }
    }
  }
  
  private final void checkTypeArgumentsSubstitution(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    TypeSubstitutor localTypeSubstitutor = TypeSubstitutor.create(paramKotlinType2);
    Intrinsics.checkExpressionValueIsNotNull(localTypeSubstitutor, "TypeSubstitutor.create(substitutedType)");
    paramKotlinType2 = ((Iterable)paramKotlinType2.getArguments()).iterator();
    for (int i = 0; paramKotlinType2.hasNext(); i++)
    {
      Object localObject1 = paramKotlinType2.next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      localObject1 = (TypeProjection)localObject1;
      if (!((TypeProjection)localObject1).isStarProjection())
      {
        Object localObject2 = ((TypeProjection)localObject1).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "substitutedArgument.type");
        if (!TypeUtilsKt.containsTypeAliasParameters((KotlinType)localObject2))
        {
          Object localObject3 = (TypeProjection)paramKotlinType1.getArguments().get(i);
          localObject2 = (TypeParameterDescriptor)paramKotlinType1.getConstructor().getParameters().get(i);
          if (this.shouldCheckBounds)
          {
            Companion localCompanion = Companion;
            TypeAliasExpansionReportStrategy localTypeAliasExpansionReportStrategy = this.reportStrategy;
            localObject3 = ((TypeProjection)localObject3).getType();
            Intrinsics.checkExpressionValueIsNotNull(localObject3, "unsubstitutedArgument.type");
            localObject1 = ((TypeProjection)localObject1).getType();
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "substitutedArgument.type");
            Intrinsics.checkExpressionValueIsNotNull(localObject2, "typeParameter");
            localCompanion.checkBoundsInTypeAlias(localTypeAliasExpansionReportStrategy, (KotlinType)localObject3, (KotlinType)localObject1, (TypeParameterDescriptor)localObject2, localTypeSubstitutor);
          }
        }
      }
    }
  }
  
  private final DynamicType combineAnnotations(DynamicType paramDynamicType, Annotations paramAnnotations)
  {
    return paramDynamicType.replaceAnnotations(createCombinedAnnotations((KotlinType)paramDynamicType, paramAnnotations));
  }
  
  private final SimpleType combineAnnotations(SimpleType paramSimpleType, Annotations paramAnnotations)
  {
    KotlinType localKotlinType = (KotlinType)paramSimpleType;
    if (!KotlinTypeKt.isError(localKotlinType)) {
      paramSimpleType = TypeSubstitutionKt.replace$default(paramSimpleType, null, createCombinedAnnotations(localKotlinType, paramAnnotations), 1, null);
    }
    return paramSimpleType;
  }
  
  private final SimpleType combineNullability(SimpleType paramSimpleType, KotlinType paramKotlinType)
  {
    paramSimpleType = TypeUtils.makeNullableIfNeeded(paramSimpleType, paramKotlinType.isMarkedNullable());
    Intrinsics.checkExpressionValueIsNotNull(paramSimpleType, "TypeUtils.makeNullableIf…romType.isMarkedNullable)");
    return paramSimpleType;
  }
  
  private final SimpleType combineNullabilityAndAnnotations(SimpleType paramSimpleType, KotlinType paramKotlinType)
  {
    return combineAnnotations(combineNullability(paramSimpleType, paramKotlinType), paramKotlinType.getAnnotations());
  }
  
  private final SimpleType createAbbreviation(TypeAliasExpansion paramTypeAliasExpansion, Annotations paramAnnotations, boolean paramBoolean)
  {
    TypeConstructor localTypeConstructor = paramTypeAliasExpansion.getDescriptor().getTypeConstructor();
    Intrinsics.checkExpressionValueIsNotNull(localTypeConstructor, "descriptor.typeConstructor");
    return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(paramAnnotations, localTypeConstructor, paramTypeAliasExpansion.getArguments(), paramBoolean, (MemberScope)MemberScope.Empty.INSTANCE);
  }
  
  private final Annotations createCombinedAnnotations(KotlinType paramKotlinType, Annotations paramAnnotations)
  {
    if (KotlinTypeKt.isError(paramKotlinType)) {
      return paramKotlinType.getAnnotations();
    }
    return AnnotationsKt.composeAnnotations(paramAnnotations, paramKotlinType.getAnnotations());
  }
  
  private final TypeProjection expandNonArgumentTypeProjection(TypeProjection paramTypeProjection, TypeAliasExpansion paramTypeAliasExpansion, int paramInt)
  {
    Object localObject1 = (KotlinType)paramTypeProjection.getType().unwrap();
    if (DynamicTypesKt.isDynamic((KotlinType)localObject1)) {
      return paramTypeProjection;
    }
    SimpleType localSimpleType = TypeSubstitutionKt.asSimpleType((KotlinType)localObject1);
    Object localObject2 = (KotlinType)localSimpleType;
    localObject1 = paramTypeProjection;
    if (!KotlinTypeKt.isError((KotlinType)localObject2)) {
      if (!TypeUtilsKt.requiresTypeAliasExpansion((KotlinType)localObject2))
      {
        localObject1 = paramTypeProjection;
      }
      else
      {
        Object localObject3 = localSimpleType.getConstructor();
        localObject1 = ((TypeConstructor)localObject3).getDeclarationDescriptor();
        int i = ((TypeConstructor)localObject3).getParameters().size();
        int j = localSimpleType.getArguments().size();
        int k = 0;
        if (i == j) {
          i = 1;
        } else {
          i = 0;
        }
        if ((_Assertions.ENABLED) && (i == 0))
        {
          paramTypeProjection = new StringBuilder();
          paramTypeProjection.append("Unexpected malformed type: ");
          paramTypeProjection.append(localSimpleType);
          throw ((Throwable)new AssertionError(paramTypeProjection.toString()));
        }
        if ((localObject1 instanceof TypeParameterDescriptor))
        {
          localObject1 = paramTypeProjection;
        }
        else if ((localObject1 instanceof TypeAliasDescriptor))
        {
          localObject1 = (TypeAliasDescriptor)localObject1;
          if (paramTypeAliasExpansion.isRecursion((TypeAliasDescriptor)localObject1))
          {
            this.reportStrategy.recursiveTypeAlias((TypeAliasDescriptor)localObject1);
            paramTypeAliasExpansion = Variance.INVARIANT;
            paramTypeProjection = new StringBuilder();
            paramTypeProjection.append("Recursive type alias: ");
            paramTypeProjection.append(((TypeAliasDescriptor)localObject1).getName());
            return (TypeProjection)new TypeProjectionImpl(paramTypeAliasExpansion, (KotlinType)ErrorUtils.createErrorType(paramTypeProjection.toString()));
          }
          Object localObject4 = (Iterable)localSimpleType.getArguments();
          localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
          localObject4 = ((Iterable)localObject4).iterator();
          for (i = k; ((Iterator)localObject4).hasNext(); i++)
          {
            Object localObject5 = ((Iterator)localObject4).next();
            if (i < 0) {
              CollectionsKt.throwIndexOverflow();
            }
            ((Collection)localObject2).add(expandTypeProjection((TypeProjection)localObject5, paramTypeAliasExpansion, (TypeParameterDescriptor)((TypeConstructor)localObject3).getParameters().get(i), paramInt + 1));
          }
          localObject3 = (List)localObject2;
          localObject1 = expandRecursively(TypeAliasExpansion.Companion.create(paramTypeAliasExpansion, (TypeAliasDescriptor)localObject1, (List)localObject3), localSimpleType.getAnnotations(), localSimpleType.isMarkedNullable(), paramInt + 1, false);
          paramTypeAliasExpansion = substituteArguments(localSimpleType, paramTypeAliasExpansion, paramInt);
          if (DynamicTypesKt.isDynamic((KotlinType)localObject1)) {
            paramTypeAliasExpansion = (TypeAliasExpansion)localObject1;
          } else {
            paramTypeAliasExpansion = SpecialTypesKt.withAbbreviation((SimpleType)localObject1, paramTypeAliasExpansion);
          }
          localObject1 = (TypeProjection)new TypeProjectionImpl(paramTypeProjection.getProjectionKind(), (KotlinType)paramTypeAliasExpansion);
        }
        else
        {
          paramTypeAliasExpansion = (KotlinType)substituteArguments(localSimpleType, paramTypeAliasExpansion, paramInt);
          checkTypeArgumentsSubstitution((KotlinType)localObject2, paramTypeAliasExpansion);
          localObject1 = (TypeProjection)new TypeProjectionImpl(paramTypeProjection.getProjectionKind(), paramTypeAliasExpansion);
        }
      }
    }
    return localObject1;
  }
  
  private final SimpleType expandRecursively(TypeAliasExpansion paramTypeAliasExpansion, Annotations paramAnnotations, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    Object localObject1 = expandTypeProjection((TypeProjection)new TypeProjectionImpl(Variance.INVARIANT, (KotlinType)paramTypeAliasExpansion.getDescriptor().getUnderlyingType()), paramTypeAliasExpansion, null, paramInt);
    Object localObject2 = ((TypeProjection)localObject1).getType();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "expandedProjection.type");
    localObject2 = TypeSubstitutionKt.asSimpleType((KotlinType)localObject2);
    if (KotlinTypeKt.isError((KotlinType)localObject2)) {
      return localObject2;
    }
    if (((TypeProjection)localObject1).getProjectionKind() == Variance.INVARIANT) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if ((_Assertions.ENABLED) && (paramInt == 0))
    {
      paramAnnotations = new StringBuilder();
      paramAnnotations.append("Type alias expansion: result for ");
      paramAnnotations.append(paramTypeAliasExpansion.getDescriptor());
      paramAnnotations.append(" is ");
      paramAnnotations.append(((TypeProjection)localObject1).getProjectionKind());
      paramAnnotations.append(", should be invariant");
      throw ((Throwable)new AssertionError(paramAnnotations.toString()));
    }
    checkRepeatedAnnotations(((SimpleType)localObject2).getAnnotations(), paramAnnotations);
    localObject2 = TypeUtils.makeNullableIfNeeded(combineAnnotations((SimpleType)localObject2, paramAnnotations), paramBoolean1);
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "expandedType.combineAnno…fNeeded(it, isNullable) }");
    localObject1 = localObject2;
    if (paramBoolean2) {
      localObject1 = SpecialTypesKt.withAbbreviation((SimpleType)localObject2, createAbbreviation(paramTypeAliasExpansion, paramAnnotations, paramBoolean1));
    }
    return localObject1;
  }
  
  private final TypeProjection expandTypeProjection(TypeProjection paramTypeProjection, TypeAliasExpansion paramTypeAliasExpansion, TypeParameterDescriptor paramTypeParameterDescriptor, int paramInt)
  {
    Companion.access$assertRecursionDepth(Companion, paramInt, paramTypeAliasExpansion.getDescriptor());
    if (paramTypeProjection.isStarProjection())
    {
      if (paramTypeParameterDescriptor == null) {
        Intrinsics.throwNpe();
      }
      paramTypeProjection = TypeUtils.makeStarProjection(paramTypeParameterDescriptor);
      Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "TypeUtils.makeStarProjec…ypeParameterDescriptor!!)");
      return paramTypeProjection;
    }
    KotlinType localKotlinType = paramTypeProjection.getType();
    Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "underlyingProjection.type");
    Object localObject = paramTypeAliasExpansion.getReplacement(localKotlinType.getConstructor());
    if (localObject != null)
    {
      if (((TypeProjection)localObject).isStarProjection())
      {
        if (paramTypeParameterDescriptor == null) {
          Intrinsics.throwNpe();
        }
        paramTypeProjection = TypeUtils.makeStarProjection(paramTypeParameterDescriptor);
        Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "TypeUtils.makeStarProjec…ypeParameterDescriptor!!)");
        return paramTypeProjection;
      }
      UnwrappedType localUnwrappedType = ((TypeProjection)localObject).getType().unwrap();
      TypeAliasExpander localTypeAliasExpander = (TypeAliasExpander)this;
      localObject = ((TypeProjection)localObject).getProjectionKind();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "argument.projectionKind");
      paramTypeProjection = paramTypeProjection.getProjectionKind();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "underlyingProjection.projectionKind");
      if (paramTypeProjection == localObject)
      {
        paramTypeProjection = (TypeProjection)localObject;
      }
      else if (paramTypeProjection == Variance.INVARIANT)
      {
        paramTypeProjection = (TypeProjection)localObject;
      }
      else if (localObject != Variance.INVARIANT)
      {
        localTypeAliasExpander.reportStrategy.conflictingProjection(paramTypeAliasExpansion.getDescriptor(), paramTypeParameterDescriptor, (KotlinType)localUnwrappedType);
        paramTypeProjection = (TypeProjection)localObject;
      }
      if (paramTypeParameterDescriptor != null)
      {
        localObject = paramTypeParameterDescriptor.getVariance();
        if (localObject != null) {}
      }
      else
      {
        localObject = Variance.INVARIANT;
      }
      Intrinsics.checkExpressionValueIsNotNull(localObject, "typeParameterDescriptor?…nce ?: Variance.INVARIANT");
      if ((localObject != paramTypeProjection) && (localObject != Variance.INVARIANT)) {
        if (paramTypeProjection == Variance.INVARIANT) {
          paramTypeProjection = Variance.INVARIANT;
        } else {
          localTypeAliasExpander.reportStrategy.conflictingProjection(paramTypeAliasExpansion.getDescriptor(), paramTypeParameterDescriptor, (KotlinType)localUnwrappedType);
        }
      }
      checkRepeatedAnnotations(localKotlinType.getAnnotations(), localUnwrappedType.getAnnotations());
      if ((localUnwrappedType instanceof DynamicType)) {
        paramTypeAliasExpansion = (UnwrappedType)combineAnnotations((DynamicType)localUnwrappedType, localKotlinType.getAnnotations());
      } else {
        paramTypeAliasExpansion = (UnwrappedType)combineNullabilityAndAnnotations(TypeSubstitutionKt.asSimpleType((KotlinType)localUnwrappedType), localKotlinType);
      }
      return (TypeProjection)new TypeProjectionImpl(paramTypeProjection, (KotlinType)paramTypeAliasExpansion);
    }
    return expandNonArgumentTypeProjection(paramTypeProjection, paramTypeAliasExpansion, paramInt);
  }
  
  private final SimpleType substituteArguments(SimpleType paramSimpleType, TypeAliasExpansion paramTypeAliasExpansion, int paramInt)
  {
    TypeConstructor localTypeConstructor = paramSimpleType.getConstructor();
    Object localObject = (Iterable)paramSimpleType.getArguments();
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    Iterator localIterator = ((Iterable)localObject).iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      localObject = localIterator.next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      TypeProjection localTypeProjection = (TypeProjection)localObject;
      localObject = expandTypeProjection(localTypeProjection, paramTypeAliasExpansion, (TypeParameterDescriptor)localTypeConstructor.getParameters().get(i), paramInt + 1);
      if (!((TypeProjection)localObject).isStarProjection()) {
        localObject = (TypeProjection)new TypeProjectionImpl(((TypeProjection)localObject).getProjectionKind(), TypeUtils.makeNullableIfNeeded(((TypeProjection)localObject).getType(), localTypeProjection.getType().isMarkedNullable()));
      }
      localCollection.add(localObject);
    }
    return TypeSubstitutionKt.replace$default(paramSimpleType, (List)localCollection, null, 2, null);
  }
  
  public final SimpleType expand(TypeAliasExpansion paramTypeAliasExpansion, Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAliasExpansion, "typeAliasExpansion");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    return expandRecursively(paramTypeAliasExpansion, paramAnnotations, false, 0, true);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final void assertRecursionDepth(int paramInt, TypeAliasDescriptor paramTypeAliasDescriptor)
    {
      if (paramInt <= 100) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Too deep recursion while expanding type alias ");
      localStringBuilder.append(paramTypeAliasDescriptor.getName());
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    
    public final void checkBoundsInTypeAlias(TypeAliasExpansionReportStrategy paramTypeAliasExpansionReportStrategy, KotlinType paramKotlinType1, KotlinType paramKotlinType2, TypeParameterDescriptor paramTypeParameterDescriptor, TypeSubstitutor paramTypeSubstitutor)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeAliasExpansionReportStrategy, "reportStrategy");
      Intrinsics.checkParameterIsNotNull(paramKotlinType1, "unsubstitutedArgument");
      Intrinsics.checkParameterIsNotNull(paramKotlinType2, "typeArgument");
      Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "typeParameterDescriptor");
      Intrinsics.checkParameterIsNotNull(paramTypeSubstitutor, "substitutor");
      Iterator localIterator = paramTypeParameterDescriptor.getUpperBounds().iterator();
      while (localIterator.hasNext())
      {
        KotlinType localKotlinType = paramTypeSubstitutor.safeSubstitute((KotlinType)localIterator.next(), Variance.INVARIANT);
        Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "substitutor.safeSubstitu…ound, Variance.INVARIANT)");
        if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(paramKotlinType2, localKotlinType)) {
          paramTypeAliasExpansionReportStrategy.boundsViolationInSubstitution(localKotlinType, paramKotlinType1, paramKotlinType2, paramTypeParameterDescriptor);
        }
      }
    }
  }
}
