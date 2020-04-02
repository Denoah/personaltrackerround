package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface AnnotationDescriptor
{
  public abstract Map<Name, ConstantValue<?>> getAllValueArguments();
  
  public abstract FqName getFqName();
  
  public abstract SourceElement getSource();
  
  public abstract KotlinType getType();
  
  public static final class DefaultImpls
  {
    public static FqName getFqName(AnnotationDescriptor paramAnnotationDescriptor)
    {
      ClassDescriptor localClassDescriptor = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
      Object localObject = null;
      paramAnnotationDescriptor = localObject;
      if (localClassDescriptor != null)
      {
        if (ErrorUtils.isError((DeclarationDescriptor)localClassDescriptor)) {
          localClassDescriptor = null;
        }
        paramAnnotationDescriptor = localObject;
        if (localClassDescriptor != null) {
          paramAnnotationDescriptor = DescriptorUtilsKt.fqNameOrNull((DeclarationDescriptor)localClassDescriptor);
        }
      }
      return paramAnnotationDescriptor;
    }
  }
}
