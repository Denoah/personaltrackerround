package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;

public final class JavaDescriptorUtilKt
{
  public static final boolean isJavaField(PropertyDescriptor paramPropertyDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramPropertyDescriptor, "$this$isJavaField");
    boolean bool;
    if (paramPropertyDescriptor.getGetter() == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
