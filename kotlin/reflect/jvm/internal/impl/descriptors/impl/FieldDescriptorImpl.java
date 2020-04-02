package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotatedImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public final class FieldDescriptorImpl
  extends AnnotatedImpl
  implements FieldDescriptor
{
  private final PropertyDescriptor correspondingProperty;
  
  public FieldDescriptorImpl(Annotations paramAnnotations, PropertyDescriptor paramPropertyDescriptor)
  {
    super(paramAnnotations);
    this.correspondingProperty = paramPropertyDescriptor;
  }
}
