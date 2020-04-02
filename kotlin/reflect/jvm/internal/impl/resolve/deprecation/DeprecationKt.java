package kotlin.reflect.jvm.internal.impl.resolve.deprecation;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;

public final class DeprecationKt
{
  private static final CallableDescriptor.UserDataKey<Object> DEPRECATED_FUNCTION_KEY = (CallableDescriptor.UserDataKey)new CallableDescriptor.UserDataKey() {};
  
  public static final CallableDescriptor.UserDataKey<Object> getDEPRECATED_FUNCTION_KEY()
  {
    return DEPRECATED_FUNCTION_KEY;
  }
}
