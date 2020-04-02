package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

public abstract interface ErrorReporter
{
  public static final ErrorReporter DO_NOTHING = new ErrorReporter()
  {
    public void reportCannotInferVisibility(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
    {
      if (paramAnonymousCallableMemberDescriptor == null) {
        $$$reportNull$$$0(2);
      }
    }
    
    public void reportIncompleteHierarchy(ClassDescriptor paramAnonymousClassDescriptor, List<String> paramAnonymousList)
    {
      if (paramAnonymousClassDescriptor == null) {
        $$$reportNull$$$0(0);
      }
      if (paramAnonymousList == null) {
        $$$reportNull$$$0(1);
      }
    }
  };
  
  public abstract void reportCannotInferVisibility(CallableMemberDescriptor paramCallableMemberDescriptor);
  
  public abstract void reportIncompleteHierarchy(ClassDescriptor paramClassDescriptor, List<String> paramList);
}
