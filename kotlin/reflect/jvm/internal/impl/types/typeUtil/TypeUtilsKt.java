package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;

public final class TypeUtilsKt
{
  public static final TypeProjection asTypeProjection(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$asTypeProjection");
    return (TypeProjection)new TypeProjectionImpl(paramKotlinType);
  }
  
  public static final boolean canHaveUndefinedNullability(UnwrappedType paramUnwrappedType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "$this$canHaveUndefinedNullability");
    boolean bool;
    if ((!(paramUnwrappedType.getConstructor() instanceof NewTypeVariableConstructor)) && (!(paramUnwrappedType.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor)) && (!(paramUnwrappedType instanceof NewCapturedType))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final boolean contains(KotlinType paramKotlinType, Function1<? super UnwrappedType, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    return TypeUtils.contains(paramKotlinType, paramFunction1);
  }
  
  public static final boolean containsTypeAliasParameters(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$containsTypeAliasParameters");
    return contains(paramKotlinType, (Function1)containsTypeAliasParameters.1.INSTANCE);
  }
  
  public static final TypeProjection createProjection(KotlinType paramKotlinType, Variance paramVariance, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    Intrinsics.checkParameterIsNotNull(paramVariance, "projectionKind");
    if (paramTypeParameterDescriptor != null) {
      paramTypeParameterDescriptor = paramTypeParameterDescriptor.getVariance();
    } else {
      paramTypeParameterDescriptor = null;
    }
    Variance localVariance = paramVariance;
    if (paramTypeParameterDescriptor == paramVariance) {
      localVariance = Variance.INVARIANT;
    }
    return (TypeProjection)new TypeProjectionImpl(localVariance, paramKotlinType);
  }
  
  public static final KotlinBuiltIns getBuiltIns(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$builtIns");
    paramKotlinType = paramKotlinType.getConstructor().getBuiltIns();
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "constructor.builtIns");
    return paramKotlinType;
  }
  
  public static final KotlinType getRepresentativeUpperBound(TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "$this$representativeUpperBound");
    Object localObject1 = paramTypeParameterDescriptor.getUpperBounds();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "upperBounds");
    boolean bool = ((Collection)localObject1).isEmpty();
    if ((_Assertions.ENABLED) && (!(bool ^ true)))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Upper bounds should not be empty: ");
      ((StringBuilder)localObject1).append(paramTypeParameterDescriptor);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject1).toString()));
    }
    localObject1 = paramTypeParameterDescriptor.getUpperBounds();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "upperBounds");
    Iterator localIterator = ((Iterable)localObject1).iterator();
    Object localObject3;
    int j;
    do
    {
      bool = localIterator.hasNext();
      localObject1 = null;
      Object localObject2 = null;
      if (!bool) {
        break;
      }
      localObject3 = localIterator.next();
      localObject1 = ((KotlinType)localObject3).getConstructor().getDeclarationDescriptor();
      if (!(localObject1 instanceof ClassDescriptor)) {
        localObject1 = localObject2;
      }
      localObject1 = (ClassDescriptor)localObject1;
      int i = 0;
      j = i;
      if (localObject1 != null)
      {
        j = i;
        if (((ClassDescriptor)localObject1).getKind() != ClassKind.INTERFACE)
        {
          j = i;
          if (((ClassDescriptor)localObject1).getKind() != ClassKind.ANNOTATION_CLASS) {
            j = 1;
          }
        }
      }
    } while (j == 0);
    localObject1 = localObject3;
    localObject1 = (KotlinType)localObject1;
    if (localObject1 != null)
    {
      paramTypeParameterDescriptor = (TypeParameterDescriptor)localObject1;
    }
    else
    {
      paramTypeParameterDescriptor = paramTypeParameterDescriptor.getUpperBounds();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "upperBounds");
      paramTypeParameterDescriptor = CollectionsKt.first(paramTypeParameterDescriptor);
      Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "upperBounds.first()");
      paramTypeParameterDescriptor = (KotlinType)paramTypeParameterDescriptor;
    }
    return paramTypeParameterDescriptor;
  }
  
  public static final boolean isSubtypeOf(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType1, "$this$isSubtypeOf");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "superType");
    return KotlinTypeChecker.DEFAULT.isSubtypeOf(paramKotlinType1, paramKotlinType2);
  }
  
  public static final boolean isTypeAliasParameter(ClassifierDescriptor paramClassifierDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassifierDescriptor, "$this$isTypeAliasParameter");
    boolean bool;
    if (((paramClassifierDescriptor instanceof TypeParameterDescriptor)) && ((((TypeParameterDescriptor)paramClassifierDescriptor).getContainingDeclaration() instanceof TypeAliasDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isTypeParameter(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isTypeParameter");
    return TypeUtils.isTypeParameter(paramKotlinType);
  }
  
  public static final KotlinType makeNotNullable(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$makeNotNullable");
    paramKotlinType = TypeUtils.makeNotNullable(paramKotlinType);
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "TypeUtils.makeNotNullable(this)");
    return paramKotlinType;
  }
  
  public static final KotlinType makeNullable(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$makeNullable");
    paramKotlinType = TypeUtils.makeNullable(paramKotlinType);
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "TypeUtils.makeNullable(this)");
    return paramKotlinType;
  }
  
  public static final KotlinType replaceAnnotations(KotlinType paramKotlinType, Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$replaceAnnotations");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    if ((paramKotlinType.getAnnotations().isEmpty()) && (paramAnnotations.isEmpty())) {
      return paramKotlinType;
    }
    return (KotlinType)paramKotlinType.unwrap().replaceAnnotations(paramAnnotations);
  }
  
  public static final KotlinType replaceArgumentsWithStarProjections(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$replaceArgumentsWithStarProjections");
    UnwrappedType localUnwrappedType = paramKotlinType.unwrap();
    Object localObject1;
    Object localObject2;
    if ((localUnwrappedType instanceof FlexibleType))
    {
      localObject1 = (FlexibleType)localUnwrappedType;
      localObject2 = ((FlexibleType)localObject1).getLowerBound();
      paramKotlinType = (KotlinType)localObject2;
      Object localObject3;
      if (!((SimpleType)localObject2).getConstructor().getParameters().isEmpty()) {
        if (((SimpleType)localObject2).getConstructor().getDeclarationDescriptor() == null)
        {
          paramKotlinType = (KotlinType)localObject2;
        }
        else
        {
          paramKotlinType = ((SimpleType)localObject2).getConstructor().getParameters();
          Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "constructor.parameters");
          localObject3 = (Iterable)paramKotlinType;
          paramKotlinType = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
          localObject3 = ((Iterable)localObject3).iterator();
          while (((Iterator)localObject3).hasNext()) {
            paramKotlinType.add(new StarProjectionImpl((TypeParameterDescriptor)((Iterator)localObject3).next()));
          }
          paramKotlinType = TypeSubstitutionKt.replace$default((SimpleType)localObject2, (List)paramKotlinType, null, 2, null);
        }
      }
      localObject1 = ((FlexibleType)localObject1).getUpperBound();
      localObject2 = localObject1;
      if (!((SimpleType)localObject1).getConstructor().getParameters().isEmpty()) {
        if (((SimpleType)localObject1).getConstructor().getDeclarationDescriptor() == null)
        {
          localObject2 = localObject1;
        }
        else
        {
          localObject2 = ((SimpleType)localObject1).getConstructor().getParameters();
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "constructor.parameters");
          localObject3 = (Iterable)localObject2;
          localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
          localObject3 = ((Iterable)localObject3).iterator();
          while (((Iterator)localObject3).hasNext()) {
            ((Collection)localObject2).add(new StarProjectionImpl((TypeParameterDescriptor)((Iterator)localObject3).next()));
          }
          localObject2 = TypeSubstitutionKt.replace$default((SimpleType)localObject1, (List)localObject2, null, 2, null);
        }
      }
      paramKotlinType = KotlinTypeFactory.flexibleType(paramKotlinType, (SimpleType)localObject2);
    }
    else
    {
      if (!(localUnwrappedType instanceof SimpleType)) {
        break label471;
      }
      localObject2 = (SimpleType)localUnwrappedType;
      paramKotlinType = (KotlinType)localObject2;
      if (!((SimpleType)localObject2).getConstructor().getParameters().isEmpty()) {
        if (((SimpleType)localObject2).getConstructor().getDeclarationDescriptor() == null)
        {
          paramKotlinType = (KotlinType)localObject2;
        }
        else
        {
          paramKotlinType = ((SimpleType)localObject2).getConstructor().getParameters();
          Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "constructor.parameters");
          localObject1 = (Iterable)paramKotlinType;
          paramKotlinType = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
          localObject1 = ((Iterable)localObject1).iterator();
          while (((Iterator)localObject1).hasNext()) {
            paramKotlinType.add(new StarProjectionImpl((TypeParameterDescriptor)((Iterator)localObject1).next()));
          }
          paramKotlinType = TypeSubstitutionKt.replace$default((SimpleType)localObject2, (List)paramKotlinType, null, 2, null);
        }
      }
      paramKotlinType = (UnwrappedType)paramKotlinType;
    }
    return (KotlinType)TypeWithEnhancementKt.inheritEnhancement(paramKotlinType, (KotlinType)localUnwrappedType);
    label471:
    throw new NoWhenBranchMatchedException();
  }
  
  public static final boolean requiresTypeAliasExpansion(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$requiresTypeAliasExpansion");
    return contains(paramKotlinType, (Function1)requiresTypeAliasExpansion.1.INSTANCE);
  }
}
