package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;

public abstract class NonReportingOverrideStrategy
  extends OverridingStrategy
{
  public NonReportingOverrideStrategy() {}
  
  protected abstract void conflict(CallableMemberDescriptor paramCallableMemberDescriptor1, CallableMemberDescriptor paramCallableMemberDescriptor2);
  
  public void inheritanceConflict(CallableMemberDescriptor paramCallableMemberDescriptor1, CallableMemberDescriptor paramCallableMemberDescriptor2)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor1, "first");
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor2, "second");
    conflict(paramCallableMemberDescriptor1, paramCallableMemberDescriptor2);
  }
  
  public void overrideConflict(CallableMemberDescriptor paramCallableMemberDescriptor1, CallableMemberDescriptor paramCallableMemberDescriptor2)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor1, "fromSuper");
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor2, "fromCurrent");
    conflict(paramCallableMemberDescriptor1, paramCallableMemberDescriptor2);
  }
}
