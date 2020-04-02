package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JavaDescriptorResolver;

public final class SingleModuleClassResolver
  implements ModuleClassResolver
{
  public JavaDescriptorResolver resolver;
  
  public SingleModuleClassResolver() {}
  
  public ClassDescriptor resolveClass(JavaClass paramJavaClass)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaClass, "javaClass");
    JavaDescriptorResolver localJavaDescriptorResolver = this.resolver;
    if (localJavaDescriptorResolver == null) {
      Intrinsics.throwUninitializedPropertyAccessException("resolver");
    }
    return localJavaDescriptorResolver.resolveClass(paramJavaClass);
  }
  
  public final void setResolver(JavaDescriptorResolver paramJavaDescriptorResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaDescriptorResolver, "<set-?>");
    this.resolver = paramJavaDescriptorResolver;
  }
}
