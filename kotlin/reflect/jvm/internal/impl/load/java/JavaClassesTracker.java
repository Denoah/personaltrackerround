package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;

public abstract interface JavaClassesTracker
{
  public abstract void reportClass(JavaClassDescriptor paramJavaClassDescriptor);
  
  public static final class Default
    implements JavaClassesTracker
  {
    public static final Default INSTANCE = new Default();
    
    private Default() {}
    
    public void reportClass(JavaClassDescriptor paramJavaClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramJavaClassDescriptor, "classDescriptor");
    }
  }
}
