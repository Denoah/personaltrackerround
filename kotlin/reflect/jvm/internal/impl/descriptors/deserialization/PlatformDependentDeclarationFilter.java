package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public abstract interface PlatformDependentDeclarationFilter
{
  public abstract boolean isFunctionAvailable(ClassDescriptor paramClassDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor);
  
  public static final class All
    implements PlatformDependentDeclarationFilter
  {
    public static final All INSTANCE = new All();
    
    private All() {}
    
    public boolean isFunctionAvailable(ClassDescriptor paramClassDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      Intrinsics.checkParameterIsNotNull(paramSimpleFunctionDescriptor, "functionDescriptor");
      return true;
    }
  }
  
  public static final class NoPlatformDependent
    implements PlatformDependentDeclarationFilter
  {
    public static final NoPlatformDependent INSTANCE = new NoPlatformDependent();
    
    private NoPlatformDependent() {}
    
    public boolean isFunctionAvailable(ClassDescriptor paramClassDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      Intrinsics.checkParameterIsNotNull(paramSimpleFunctionDescriptor, "functionDescriptor");
      return paramSimpleFunctionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME()) ^ true;
    }
  }
}
