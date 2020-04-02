package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution.Companion;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class NewCapturedTypeKt
{
  public static final SimpleType captureFromArguments(SimpleType paramSimpleType, CaptureStatus paramCaptureStatus)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "type");
    Intrinsics.checkParameterIsNotNull(paramCaptureStatus, "status");
    if (paramSimpleType.getArguments().size() != paramSimpleType.getConstructor().getParameters().size()) {
      return null;
    }
    List localList = paramSimpleType.getArguments();
    Object localObject1 = (Iterable)localList;
    boolean bool = localObject1 instanceof Collection;
    int i = 0;
    int j = 1;
    Object localObject2;
    if ((bool) && (((Collection)localObject1).isEmpty()))
    {
      k = j;
    }
    else
    {
      localObject2 = ((Iterable)localObject1).iterator();
      do
      {
        k = j;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        if (((TypeProjection)((Iterator)localObject2).next()).getProjectionKind() == Variance.INVARIANT) {
          k = 1;
        } else {
          k = 0;
        }
      } while (k != 0);
      k = 0;
    }
    if (k != 0) {
      return null;
    }
    Object localObject3 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    Object localObject4 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject2 = (TypeProjection)((Iterator)localObject4).next();
      if (((TypeProjection)localObject2).getProjectionKind() == Variance.INVARIANT)
      {
        localObject1 = localObject2;
      }
      else
      {
        if ((!((TypeProjection)localObject2).isStarProjection()) && (((TypeProjection)localObject2).getProjectionKind() == Variance.IN_VARIANCE)) {
          localObject1 = ((TypeProjection)localObject2).getType().unwrap();
        } else {
          localObject1 = null;
        }
        localObject1 = TypeUtilsKt.asTypeProjection((KotlinType)new NewCapturedType(paramCaptureStatus, (UnwrappedType)localObject1, (TypeProjection)localObject2));
      }
      ((Collection)localObject3).add(localObject1);
    }
    localObject1 = (List)localObject3;
    paramCaptureStatus = TypeConstructorSubstitution.Companion.create(paramSimpleType.getConstructor(), (List)localObject1).buildSubstitutor();
    j = ((Collection)localList).size();
    int k = i;
    while (k < j)
    {
      localObject3 = (TypeProjection)localList.get(k);
      localObject2 = (TypeProjection)((List)localObject1).get(k);
      if (((TypeProjection)localObject3).getProjectionKind() != Variance.INVARIANT)
      {
        localObject4 = paramSimpleType.getConstructor().getParameters().get(k);
        Intrinsics.checkExpressionValueIsNotNull(localObject4, "type.constructor.parameters[index]");
        localObject4 = ((TypeParameterDescriptor)localObject4).getUpperBounds();
        Intrinsics.checkExpressionValueIsNotNull(localObject4, "type.constructor.parameters[index].upperBounds");
        Object localObject5 = (Iterable)localObject4;
        localObject4 = (Collection)new ArrayList();
        localObject5 = ((Iterable)localObject5).iterator();
        while (((Iterator)localObject5).hasNext())
        {
          KotlinType localKotlinType = (KotlinType)((Iterator)localObject5).next();
          ((Collection)localObject4).add(NewKotlinTypeChecker.Companion.getDefault().transformToNewType(paramCaptureStatus.safeSubstitute(localKotlinType, Variance.INVARIANT).unwrap()));
        }
        localObject4 = (List)localObject4;
        if ((!((TypeProjection)localObject3).isStarProjection()) && (((TypeProjection)localObject3).getProjectionKind() == Variance.OUT_VARIANCE)) {
          ((Collection)localObject4).add(NewKotlinTypeChecker.Companion.getDefault().transformToNewType(((TypeProjection)localObject3).getType().unwrap()));
        }
        localObject2 = ((TypeProjection)localObject2).getType();
        if (localObject2 != null) {
          ((NewCapturedType)localObject2).getConstructor().initializeSupertypes((List)localObject4);
        }
      }
      else
      {
        k++;
        continue;
      }
      throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
    }
    return KotlinTypeFactory.simpleType$default(paramSimpleType.getAnnotations(), paramSimpleType.getConstructor(), (List)localObject1, paramSimpleType.isMarkedNullable(), null, 16, null);
  }
}
