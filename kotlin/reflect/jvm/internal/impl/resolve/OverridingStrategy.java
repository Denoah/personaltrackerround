package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;

public abstract class OverridingStrategy
{
  public OverridingStrategy() {}
  
  public abstract void addFakeOverride(CallableMemberDescriptor paramCallableMemberDescriptor);
  
  public abstract void inheritanceConflict(CallableMemberDescriptor paramCallableMemberDescriptor1, CallableMemberDescriptor paramCallableMemberDescriptor2);
  
  public abstract void overrideConflict(CallableMemberDescriptor paramCallableMemberDescriptor1, CallableMemberDescriptor paramCallableMemberDescriptor2);
  
  public void setOverriddenDescriptors(CallableMemberDescriptor paramCallableMemberDescriptor, Collection<? extends CallableMemberDescriptor> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "member");
    Intrinsics.checkParameterIsNotNull(paramCollection, "overridden");
    paramCallableMemberDescriptor.setOverriddenDescriptors(paramCollection);
  }
}
