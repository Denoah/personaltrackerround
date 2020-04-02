package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Contract;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Result;

public final class FieldOverridabilityCondition
  implements ExternalOverridabilityCondition
{
  public FieldOverridabilityCondition() {}
  
  public ExternalOverridabilityCondition.Contract getContract()
  {
    return ExternalOverridabilityCondition.Contract.BOTH;
  }
  
  public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor1, "superDescriptor");
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor2, "subDescriptor");
    if (((paramCallableDescriptor2 instanceof PropertyDescriptor)) && ((paramCallableDescriptor1 instanceof PropertyDescriptor)))
    {
      paramCallableDescriptor2 = (PropertyDescriptor)paramCallableDescriptor2;
      paramClassDescriptor = paramCallableDescriptor2.getName();
      paramCallableDescriptor1 = (PropertyDescriptor)paramCallableDescriptor1;
      if ((Intrinsics.areEqual(paramClassDescriptor, paramCallableDescriptor1.getName()) ^ true)) {
        return ExternalOverridabilityCondition.Result.UNKNOWN;
      }
      if ((JavaDescriptorUtilKt.isJavaField(paramCallableDescriptor2)) && (JavaDescriptorUtilKt.isJavaField(paramCallableDescriptor1))) {
        return ExternalOverridabilityCondition.Result.OVERRIDABLE;
      }
      if ((!JavaDescriptorUtilKt.isJavaField(paramCallableDescriptor2)) && (!JavaDescriptorUtilKt.isJavaField(paramCallableDescriptor1))) {
        return ExternalOverridabilityCondition.Result.UNKNOWN;
      }
      return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
    }
    return ExternalOverridabilityCondition.Result.UNKNOWN;
  }
}
