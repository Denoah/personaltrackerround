package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.HashSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;

public final class InlineClassMappingKt
{
  public static final KotlinTypeMarker computeExpandedTypeForInlineClass(TypeSystemCommonBackendContext paramTypeSystemCommonBackendContext, KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeSystemCommonBackendContext, "$this$computeExpandedTypeForInlineClass");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "inlineClassType");
    return computeExpandedTypeInner(paramTypeSystemCommonBackendContext, paramKotlinTypeMarker, new HashSet());
  }
  
  private static final KotlinTypeMarker computeExpandedTypeInner(TypeSystemCommonBackendContext paramTypeSystemCommonBackendContext, KotlinTypeMarker paramKotlinTypeMarker, HashSet<TypeConstructorMarker> paramHashSet)
  {
    TypeConstructorMarker localTypeConstructorMarker = paramTypeSystemCommonBackendContext.typeConstructor(paramKotlinTypeMarker);
    if (!paramHashSet.add(localTypeConstructorMarker)) {
      return null;
    }
    Object localObject = paramTypeSystemCommonBackendContext.getTypeParameterClassifier(localTypeConstructorMarker);
    if (localObject != null)
    {
      localObject = computeExpandedTypeInner(paramTypeSystemCommonBackendContext, paramTypeSystemCommonBackendContext.getRepresentativeUpperBound((TypeParameterMarker)localObject), paramHashSet);
      if (localObject != null)
      {
        paramHashSet = (HashSet<TypeConstructorMarker>)localObject;
        if (!paramTypeSystemCommonBackendContext.isNullableType((KotlinTypeMarker)localObject)) {
          if (!paramTypeSystemCommonBackendContext.isMarkedNullable(paramKotlinTypeMarker))
          {
            paramHashSet = (HashSet<TypeConstructorMarker>)localObject;
          }
          else
          {
            localObject = paramTypeSystemCommonBackendContext.makeNullable((KotlinTypeMarker)localObject);
            break label212;
          }
        }
      }
      else
      {
        localObject = null;
        break label212;
      }
    }
    else
    {
      localObject = paramKotlinTypeMarker;
      if (!paramTypeSystemCommonBackendContext.isInlineClass(localTypeConstructorMarker)) {
        break label212;
      }
      localObject = paramTypeSystemCommonBackendContext.getSubstitutedUnderlyingType(paramKotlinTypeMarker);
      if (localObject == null) {
        break label210;
      }
      paramHashSet = computeExpandedTypeInner(paramTypeSystemCommonBackendContext, (KotlinTypeMarker)localObject, paramHashSet);
      if (paramHashSet == null) {
        break label210;
      }
      if (paramTypeSystemCommonBackendContext.isNullableType(paramKotlinTypeMarker)) {
        break label156;
      }
    }
    localObject = paramHashSet;
    break label212;
    label156:
    if (paramTypeSystemCommonBackendContext.isNullableType(paramHashSet))
    {
      localObject = paramKotlinTypeMarker;
    }
    else if (((paramHashSet instanceof SimpleTypeMarker)) && (paramTypeSystemCommonBackendContext.isPrimitiveType((SimpleTypeMarker)paramHashSet)))
    {
      localObject = paramKotlinTypeMarker;
    }
    else
    {
      localObject = paramTypeSystemCommonBackendContext.makeNullable(paramHashSet);
      break label212;
      label210:
      return null;
    }
    label212:
    return localObject;
  }
}
