package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

public abstract interface ExternalOverridabilityCondition
{
  public abstract Contract getContract();
  
  public abstract Result isOverridable(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor);
  
  public static enum Contract
  {
    static
    {
      Contract localContract = new Contract("BOTH", 2);
      BOTH = localContract;
      $VALUES = new Contract[] { CONFLICTS_ONLY, SUCCESS_ONLY, localContract };
    }
    
    private Contract() {}
  }
  
  public static enum Result
  {
    static
    {
      CONFLICT = new Result("CONFLICT", 1);
      INCOMPATIBLE = new Result("INCOMPATIBLE", 2);
      Result localResult = new Result("UNKNOWN", 3);
      UNKNOWN = localResult;
      $VALUES = new Result[] { OVERRIDABLE, CONFLICT, INCOMPATIBLE, localResult };
    }
    
    private Result() {}
  }
}
