package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class AnnotationValue
  extends ConstantValue<AnnotationDescriptor>
{
  public AnnotationValue(AnnotationDescriptor paramAnnotationDescriptor)
  {
    super(paramAnnotationDescriptor);
  }
  
  public KotlinType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    return ((AnnotationDescriptor)getValue()).getType();
  }
}
