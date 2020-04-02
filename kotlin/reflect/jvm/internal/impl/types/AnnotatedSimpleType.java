package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

final class AnnotatedSimpleType
  extends DelegatingSimpleTypeImpl
{
  private final Annotations annotations;
  
  public AnnotatedSimpleType(SimpleType paramSimpleType, Annotations paramAnnotations)
  {
    super(paramSimpleType);
    this.annotations = paramAnnotations;
  }
  
  public Annotations getAnnotations()
  {
    return this.annotations;
  }
  
  public AnnotatedSimpleType replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new AnnotatedSimpleType(paramSimpleType, getAnnotations());
  }
}
