package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public final class TypeSubstitutionKt
{
  public static final SimpleType asSimpleType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$asSimpleType");
    UnwrappedType localUnwrappedType = paramKotlinType.unwrap();
    Object localObject = localUnwrappedType;
    if (!(localUnwrappedType instanceof SimpleType)) {
      localObject = null;
    }
    localObject = (SimpleType)localObject;
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("This is should be simple type: ");
    ((StringBuilder)localObject).append(paramKotlinType);
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject).toString().toString()));
  }
  
  public static final KotlinType replace(KotlinType paramKotlinType, List<? extends TypeProjection> paramList, Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$replace");
    Intrinsics.checkParameterIsNotNull(paramList, "newArguments");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    if (((paramList.isEmpty()) || (paramList == paramKotlinType.getArguments())) && (paramAnnotations == paramKotlinType.getAnnotations())) {
      return paramKotlinType;
    }
    paramKotlinType = paramKotlinType.unwrap();
    if ((paramKotlinType instanceof FlexibleType))
    {
      paramKotlinType = (FlexibleType)paramKotlinType;
      paramKotlinType = (KotlinType)KotlinTypeFactory.flexibleType(replace(paramKotlinType.getLowerBound(), paramList, paramAnnotations), replace(paramKotlinType.getUpperBound(), paramList, paramAnnotations));
    }
    else
    {
      if (!(paramKotlinType instanceof SimpleType)) {
        break label112;
      }
      paramKotlinType = (KotlinType)replace((SimpleType)paramKotlinType, paramList, paramAnnotations);
    }
    return paramKotlinType;
    label112:
    throw new NoWhenBranchMatchedException();
  }
  
  public static final SimpleType replace(SimpleType paramSimpleType, List<? extends TypeProjection> paramList, Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "$this$replace");
    Intrinsics.checkParameterIsNotNull(paramList, "newArguments");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    if ((paramList.isEmpty()) && (paramAnnotations == paramSimpleType.getAnnotations())) {
      return paramSimpleType;
    }
    if (paramList.isEmpty()) {
      return paramSimpleType.replaceAnnotations(paramAnnotations);
    }
    return KotlinTypeFactory.simpleType$default(paramAnnotations, paramSimpleType.getConstructor(), paramList, paramSimpleType.isMarkedNullable(), null, 16, null);
  }
}
