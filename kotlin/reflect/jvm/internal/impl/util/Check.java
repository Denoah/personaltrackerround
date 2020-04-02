package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;

public abstract interface Check
{
  public abstract boolean check(FunctionDescriptor paramFunctionDescriptor);
  
  public abstract String getDescription();
  
  public abstract String invoke(FunctionDescriptor paramFunctionDescriptor);
  
  public static final class DefaultImpls
  {
    public static String invoke(Check paramCheck, FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      if (!paramCheck.check(paramFunctionDescriptor)) {
        paramCheck = paramCheck.getDescription();
      } else {
        paramCheck = null;
      }
      return paramCheck;
    }
  }
}
