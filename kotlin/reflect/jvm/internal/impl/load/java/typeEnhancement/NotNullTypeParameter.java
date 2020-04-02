package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.NotNullTypeVariable;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class NotNullTypeParameter
  extends DelegatingSimpleType
  implements NotNullTypeVariable
{
  private final SimpleType delegate;
  
  public NotNullTypeParameter(SimpleType paramSimpleType)
  {
    this.delegate = paramSimpleType;
  }
  
  private final SimpleType prepareReplacement(SimpleType paramSimpleType)
  {
    SimpleType localSimpleType = paramSimpleType.makeNullableAsSpecified(false);
    if (!TypeUtilsKt.isTypeParameter((KotlinType)paramSimpleType)) {
      return localSimpleType;
    }
    return (SimpleType)new NotNullTypeParameter(localSimpleType);
  }
  
  protected SimpleType getDelegate()
  {
    return this.delegate;
  }
  
  public boolean isMarkedNullable()
  {
    return false;
  }
  
  public boolean isTypeVariable()
  {
    return true;
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    SimpleType localSimpleType;
    if (paramBoolean) {
      localSimpleType = getDelegate().makeNullableAsSpecified(true);
    } else {
      localSimpleType = (SimpleType)this;
    }
    return localSimpleType;
  }
  
  public NotNullTypeParameter replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new NotNullTypeParameter(getDelegate().replaceAnnotations(paramAnnotations));
  }
  
  public NotNullTypeParameter replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new NotNullTypeParameter(paramSimpleType);
  }
  
  public KotlinType substitutionResult(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "replacement");
    paramKotlinType = paramKotlinType.unwrap();
    Object localObject = (KotlinType)paramKotlinType;
    if ((!TypeUtils.isNullableType((KotlinType)localObject)) && (!TypeUtilsKt.isTypeParameter((KotlinType)localObject))) {
      return localObject;
    }
    if ((paramKotlinType instanceof SimpleType))
    {
      paramKotlinType = (KotlinType)prepareReplacement((SimpleType)paramKotlinType);
    }
    else
    {
      if (!(paramKotlinType instanceof FlexibleType)) {
        break label98;
      }
      paramKotlinType = (FlexibleType)paramKotlinType;
      paramKotlinType = (KotlinType)TypeWithEnhancementKt.wrapEnhancement(KotlinTypeFactory.flexibleType(prepareReplacement(paramKotlinType.getLowerBound()), prepareReplacement(paramKotlinType.getUpperBound())), TypeWithEnhancementKt.getEnhancement((KotlinType)localObject));
    }
    return paramKotlinType;
    label98:
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Incorrect type: ");
    ((StringBuilder)localObject).append(paramKotlinType);
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject).toString().toString()));
  }
}
