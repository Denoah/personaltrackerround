package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

final class NoDefaultAndVarargsCheck
  implements Check
{
  public static final NoDefaultAndVarargsCheck INSTANCE = new NoDefaultAndVarargsCheck();
  private static final String description = "should not have varargs or parameters with default values";
  
  private NoDefaultAndVarargsCheck() {}
  
  public boolean check(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    paramFunctionDescriptor = paramFunctionDescriptor.getValueParameters();
    Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "functionDescriptor.valueParameters");
    paramFunctionDescriptor = (Iterable)paramFunctionDescriptor;
    boolean bool1 = paramFunctionDescriptor instanceof Collection;
    boolean bool2 = false;
    if ((bool1) && (((Collection)paramFunctionDescriptor).isEmpty())) {}
    int i;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        bool2 = true;
        break;
        localIterator = paramFunctionDescriptor.iterator();
      }
      paramFunctionDescriptor = (ValueParameterDescriptor)localIterator.next();
      Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "it");
      if ((!DescriptorUtilsKt.declaresOrInheritsDefaultValue(paramFunctionDescriptor)) && (paramFunctionDescriptor.getVarargElementType() == null)) {
        i = 1;
      } else {
        i = 0;
      }
    } while (i != 0);
    return bool2;
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public String invoke(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    return Check.DefaultImpls.invoke(this, paramFunctionDescriptor);
  }
}
