package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;

public abstract interface ModuleClassResolver
{
  public abstract ClassDescriptor resolveClass(JavaClass paramJavaClass);
}
