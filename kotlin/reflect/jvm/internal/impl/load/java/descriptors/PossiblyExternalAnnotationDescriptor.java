package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;

public abstract interface PossiblyExternalAnnotationDescriptor
  extends AnnotationDescriptor
{
  public abstract boolean isIdeExternalAnnotation();
}
