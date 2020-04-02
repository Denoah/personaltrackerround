package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public abstract class DelegatingSimpleTypeImpl
  extends DelegatingSimpleType
{
  private final SimpleType delegate;
  
  public DelegatingSimpleTypeImpl(SimpleType paramSimpleType)
  {
    this.delegate = paramSimpleType;
  }
  
  protected SimpleType getDelegate()
  {
    return this.delegate;
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    if (paramBoolean == isMarkedNullable()) {
      return (SimpleType)this;
    }
    return getDelegate().makeNullableAsSpecified(paramBoolean).replaceAnnotations(getAnnotations());
  }
  
  public DelegatingSimpleTypeImpl replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    if (paramAnnotations != getAnnotations()) {
      paramAnnotations = (DelegatingSimpleTypeImpl)new AnnotatedSimpleType((SimpleType)this, paramAnnotations);
    } else {
      paramAnnotations = this;
    }
    return paramAnnotations;
  }
}
