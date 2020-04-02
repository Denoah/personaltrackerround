package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class SimpleTypeWithEnhancement
  extends DelegatingSimpleType
  implements TypeWithEnhancement
{
  private final SimpleType delegate;
  private final KotlinType enhancement;
  
  public SimpleTypeWithEnhancement(SimpleType paramSimpleType, KotlinType paramKotlinType)
  {
    this.delegate = paramSimpleType;
    this.enhancement = paramKotlinType;
  }
  
  protected SimpleType getDelegate()
  {
    return this.delegate;
  }
  
  public KotlinType getEnhancement()
  {
    return this.enhancement;
  }
  
  public UnwrappedType getOrigin()
  {
    return (UnwrappedType)getDelegate();
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    UnwrappedType localUnwrappedType = TypeWithEnhancementKt.wrapEnhancement(getOrigin().makeNullableAsSpecified(paramBoolean), (KotlinType)getEnhancement().unwrap().makeNullableAsSpecified(paramBoolean));
    if (localUnwrappedType != null) {
      return (SimpleType)localUnwrappedType;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public SimpleTypeWithEnhancement refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    KotlinType localKotlinType = paramKotlinTypeRefiner.refineType((KotlinType)getDelegate());
    if (localKotlinType != null) {
      return new SimpleTypeWithEnhancement((SimpleType)localKotlinType, paramKotlinTypeRefiner.refineType(getEnhancement()));
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public SimpleType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    paramAnnotations = TypeWithEnhancementKt.wrapEnhancement(getOrigin().replaceAnnotations(paramAnnotations), getEnhancement());
    if (paramAnnotations != null) {
      return (SimpleType)paramAnnotations;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public SimpleTypeWithEnhancement replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new SimpleTypeWithEnhancement(paramSimpleType, getEnhancement());
  }
}
