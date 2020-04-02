package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy.FULLY_QUALIFIED;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer.Companion;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class CapturedTypeApproximationKt
{
  public static final ApproximationBounds<KotlinType> approximateCapturedTypes(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    Object localObject1;
    Object localObject2;
    if (FlexibleTypesKt.isFlexible(paramKotlinType))
    {
      localObject1 = approximateCapturedTypes((KotlinType)FlexibleTypesKt.lowerIfFlexible(paramKotlinType));
      localObject2 = approximateCapturedTypes((KotlinType)FlexibleTypesKt.upperIfFlexible(paramKotlinType));
      return new ApproximationBounds(TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible((KotlinType)((ApproximationBounds)localObject1).getLower()), FlexibleTypesKt.upperIfFlexible((KotlinType)((ApproximationBounds)localObject2).getLower())), paramKotlinType), TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible((KotlinType)((ApproximationBounds)localObject1).getUpper()), FlexibleTypesKt.upperIfFlexible((KotlinType)((ApproximationBounds)localObject2).getUpper())), paramKotlinType));
    }
    Object localObject3 = paramKotlinType.getConstructor();
    boolean bool = CapturedTypeConstructorKt.isCaptured(paramKotlinType);
    int i = 1;
    Object localObject4;
    if (bool)
    {
      if (localObject3 != null)
      {
        localObject1 = ((CapturedTypeConstructor)localObject3).getProjection();
        localObject2 = new Lambda(paramKotlinType)
        {
          public final KotlinType invoke(KotlinType paramAnonymousKotlinType)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinType, "$this$makeNullableIfNeeded");
            paramAnonymousKotlinType = TypeUtils.makeNullableIfNeeded(paramAnonymousKotlinType, this.$type.isMarkedNullable());
            Intrinsics.checkExpressionValueIsNotNull(paramAnonymousKotlinType, "TypeUtils.makeNullableIf…s, type.isMarkedNullable)");
            return paramAnonymousKotlinType;
          }
        };
        localObject4 = ((TypeProjection)localObject1).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject4, "typeProjection.type");
        localObject4 = ((approximateCapturedTypes.1)localObject2).invoke((KotlinType)localObject4);
        localObject3 = ((TypeProjection)localObject1).getProjectionKind();
        i = CapturedTypeApproximationKt.WhenMappings.$EnumSwitchMapping$1[localObject3.ordinal()];
        if (i != 1)
        {
          if (i == 2)
          {
            paramKotlinType = TypeUtilsKt.getBuiltIns(paramKotlinType).getNothingType();
            Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "type.builtIns.nothingType");
            paramKotlinType = new ApproximationBounds(((approximateCapturedTypes.1)localObject2).invoke((KotlinType)paramKotlinType), localObject4);
          }
          else
          {
            paramKotlinType = new StringBuilder();
            paramKotlinType.append("Only nontrivial projections should have been captured, not: ");
            paramKotlinType.append(localObject1);
            throw ((Throwable)new AssertionError(paramKotlinType.toString()));
          }
        }
        else
        {
          paramKotlinType = TypeUtilsKt.getBuiltIns(paramKotlinType).getNullableAnyType();
          Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "type.builtIns.nullableAnyType");
          paramKotlinType = new ApproximationBounds(localObject4, paramKotlinType);
        }
        return paramKotlinType;
      }
      throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor");
    }
    if ((!paramKotlinType.getArguments().isEmpty()) && (paramKotlinType.getArguments().size() == ((TypeConstructor)localObject3).getParameters().size()))
    {
      localObject2 = new ArrayList();
      localObject1 = new ArrayList();
      localObject4 = (Iterable)paramKotlinType.getArguments();
      localObject3 = ((TypeConstructor)localObject3).getParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "typeConstructor.parameters");
      localObject4 = CollectionsKt.zip((Iterable)localObject4, (Iterable)localObject3).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        Object localObject5 = (Pair)((Iterator)localObject4).next();
        localObject3 = (TypeProjection)((Pair)localObject5).component1();
        localObject5 = (TypeParameterDescriptor)((Pair)localObject5).component2();
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "typeParameter");
        localObject5 = toTypeArgument((TypeProjection)localObject3, (TypeParameterDescriptor)localObject5);
        if (((TypeProjection)localObject3).isStarProjection())
        {
          ((ArrayList)localObject2).add(localObject5);
          ((ArrayList)localObject1).add(localObject5);
        }
        else
        {
          localObject5 = approximateProjection((TypeArgument)localObject5);
          localObject3 = (TypeArgument)((ApproximationBounds)localObject5).component1();
          localObject5 = (TypeArgument)((ApproximationBounds)localObject5).component2();
          ((ArrayList)localObject2).add(localObject3);
          ((ArrayList)localObject1).add(localObject5);
        }
      }
      localObject4 = (Iterable)localObject2;
      if (((localObject4 instanceof Collection)) && (((Collection)localObject4).isEmpty())) {}
      do
      {
        while (!((Iterator)localObject4).hasNext())
        {
          i = 0;
          break;
          localObject4 = ((Iterable)localObject4).iterator();
        }
      } while (!(((TypeArgument)((Iterator)localObject4).next()).isConsistent() ^ true));
      if (i != 0)
      {
        localObject2 = TypeUtilsKt.getBuiltIns(paramKotlinType).getNothingType();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "type.builtIns.nothingType");
        localObject2 = (KotlinType)localObject2;
      }
      else
      {
        localObject2 = replaceTypeArguments(paramKotlinType, (List)localObject2);
      }
      return new ApproximationBounds(localObject2, replaceTypeArguments(paramKotlinType, (List)localObject1));
    }
    return new ApproximationBounds(paramKotlinType, paramKotlinType);
  }
  
  public static final TypeProjection approximateCapturedTypesIfNecessary(TypeProjection paramTypeProjection, boolean paramBoolean)
  {
    if (paramTypeProjection == null) {
      return null;
    }
    if (paramTypeProjection.isStarProjection()) {
      return paramTypeProjection;
    }
    KotlinType localKotlinType = paramTypeProjection.getType();
    Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "typeProjection.type");
    if (!TypeUtils.contains(localKotlinType, (Function1)approximateCapturedTypesIfNecessary.1.INSTANCE)) {
      return paramTypeProjection;
    }
    Variance localVariance = paramTypeProjection.getProjectionKind();
    Intrinsics.checkExpressionValueIsNotNull(localVariance, "typeProjection.projectionKind");
    if (localVariance == Variance.OUT_VARIANCE) {
      return (TypeProjection)new TypeProjectionImpl(localVariance, (KotlinType)approximateCapturedTypes(localKotlinType).getUpper());
    }
    if (paramBoolean) {
      return (TypeProjection)new TypeProjectionImpl(localVariance, (KotlinType)approximateCapturedTypes(localKotlinType).getLower());
    }
    return substituteCapturedTypesWithProjections(paramTypeProjection);
  }
  
  private static final ApproximationBounds<TypeArgument> approximateProjection(TypeArgument paramTypeArgument)
  {
    Object localObject1 = approximateCapturedTypes(paramTypeArgument.getInProjection());
    KotlinType localKotlinType1 = (KotlinType)((ApproximationBounds)localObject1).component1();
    localObject1 = (KotlinType)((ApproximationBounds)localObject1).component2();
    Object localObject2 = approximateCapturedTypes(paramTypeArgument.getOutProjection());
    KotlinType localKotlinType2 = (KotlinType)((ApproximationBounds)localObject2).component1();
    localObject2 = (KotlinType)((ApproximationBounds)localObject2).component2();
    return new ApproximationBounds(new TypeArgument(paramTypeArgument.getTypeParameter(), (KotlinType)localObject1, localKotlinType2), new TypeArgument(paramTypeArgument.getTypeParameter(), localKotlinType1, (KotlinType)localObject2));
  }
  
  private static final KotlinType replaceTypeArguments(KotlinType paramKotlinType, List<TypeArgument> paramList)
  {
    int i;
    if (paramKotlinType.getArguments().size() == paramList.size()) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      paramKotlinType = new StringBuilder();
      paramKotlinType.append("Incorrect type arguments ");
      paramKotlinType.append(paramList);
      throw ((Throwable)new AssertionError(paramKotlinType.toString()));
    }
    Object localObject = (Iterable)paramList;
    paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramList.add(toTypeProjection((TypeArgument)((Iterator)localObject).next()));
    }
    return TypeSubstitutionKt.replace$default(paramKotlinType, (List)paramList, null, 2, null);
  }
  
  private static final TypeProjection substituteCapturedTypesWithProjections(TypeProjection paramTypeProjection)
  {
    TypeSubstitutor localTypeSubstitutor = TypeSubstitutor.create((TypeSubstitution)new TypeConstructorSubstitution()
    {
      public TypeProjection get(TypeConstructor paramAnonymousTypeConstructor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTypeConstructor, "key");
        TypeConstructor localTypeConstructor = paramAnonymousTypeConstructor;
        if (!(paramAnonymousTypeConstructor instanceof CapturedTypeConstructor)) {
          localTypeConstructor = null;
        }
        paramAnonymousTypeConstructor = (CapturedTypeConstructor)localTypeConstructor;
        if (paramAnonymousTypeConstructor != null)
        {
          if (paramAnonymousTypeConstructor.getProjection().isStarProjection()) {
            return (TypeProjection)new TypeProjectionImpl(Variance.OUT_VARIANCE, paramAnonymousTypeConstructor.getProjection().getType());
          }
          return paramAnonymousTypeConstructor.getProjection();
        }
        return null;
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(localTypeSubstitutor, "TypeSubstitutor.create(o…ojection\n        }\n    })");
    return localTypeSubstitutor.substituteWithoutApproximation(paramTypeProjection);
  }
  
  private static final TypeArgument toTypeArgument(TypeProjection paramTypeProjection, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Object localObject = TypeSubstitutor.combine(paramTypeParameterDescriptor.getVariance(), paramTypeProjection);
    int i = CapturedTypeApproximationKt.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i == 3)
        {
          localObject = DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)paramTypeParameterDescriptor).getNothingType();
          Intrinsics.checkExpressionValueIsNotNull(localObject, "typeParameter.builtIns.nothingType");
          localObject = (KotlinType)localObject;
          paramTypeProjection = paramTypeProjection.getType();
          Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "type");
          paramTypeProjection = new TypeArgument(paramTypeParameterDescriptor, (KotlinType)localObject, paramTypeProjection);
        }
        else
        {
          throw new NoWhenBranchMatchedException();
        }
      }
      else
      {
        paramTypeProjection = paramTypeProjection.getType();
        Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "type");
        localObject = DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)paramTypeParameterDescriptor).getNullableAnyType();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "typeParameter.builtIns.nullableAnyType");
        paramTypeProjection = new TypeArgument(paramTypeParameterDescriptor, paramTypeProjection, (KotlinType)localObject);
      }
    }
    else
    {
      localObject = paramTypeProjection.getType();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "type");
      paramTypeProjection = paramTypeProjection.getType();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeProjection, "type");
      paramTypeProjection = new TypeArgument(paramTypeParameterDescriptor, (KotlinType)localObject, paramTypeProjection);
    }
    return paramTypeProjection;
  }
  
  private static final TypeProjection toTypeProjection(TypeArgument paramTypeArgument)
  {
    boolean bool = paramTypeArgument.isConsistent();
    if ((_Assertions.ENABLED) && (!bool))
    {
      DescriptorRenderer localDescriptorRenderer = DescriptorRenderer.Companion.withOptions((Function1)toTypeProjection.1.descriptorRenderer.1.INSTANCE);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Only consistent enhanced type projection can be converted to type projection, but ");
      ((StringBuilder)localObject).append('[');
      ((StringBuilder)localObject).append(localDescriptorRenderer.render((DeclarationDescriptor)paramTypeArgument.getTypeParameter()));
      ((StringBuilder)localObject).append(": <");
      ((StringBuilder)localObject).append(localDescriptorRenderer.renderType(paramTypeArgument.getInProjection()));
      ((StringBuilder)localObject).append(", ");
      ((StringBuilder)localObject).append(localDescriptorRenderer.renderType(paramTypeArgument.getOutProjection()));
      ((StringBuilder)localObject).append(">]");
      ((StringBuilder)localObject).append(" was found");
      throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
    }
    Object localObject = new Lambda(paramTypeArgument)
    {
      public final Variance invoke(Variance paramAnonymousVariance)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousVariance, "variance");
        Variance localVariance = paramAnonymousVariance;
        if (paramAnonymousVariance == this.$this_toTypeProjection.getTypeParameter().getVariance()) {
          localVariance = Variance.INVARIANT;
        }
        return localVariance;
      }
    };
    if (Intrinsics.areEqual(paramTypeArgument.getInProjection(), paramTypeArgument.getOutProjection())) {
      paramTypeArgument = (TypeProjection)new TypeProjectionImpl(paramTypeArgument.getInProjection());
    } else if ((KotlinBuiltIns.isNothing(paramTypeArgument.getInProjection())) && (paramTypeArgument.getTypeParameter().getVariance() != Variance.IN_VARIANCE)) {
      paramTypeArgument = (TypeProjection)new TypeProjectionImpl(((toTypeProjection.2)localObject).invoke(Variance.OUT_VARIANCE), paramTypeArgument.getOutProjection());
    } else if (KotlinBuiltIns.isNullableAny(paramTypeArgument.getOutProjection())) {
      paramTypeArgument = (TypeProjection)new TypeProjectionImpl(((toTypeProjection.2)localObject).invoke(Variance.IN_VARIANCE), paramTypeArgument.getInProjection());
    } else {
      paramTypeArgument = (TypeProjection)new TypeProjectionImpl(((toTypeProjection.2)localObject).invoke(Variance.OUT_VARIANCE), paramTypeArgument.getOutProjection());
    }
    return paramTypeArgument;
  }
}
