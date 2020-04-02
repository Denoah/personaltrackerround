package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;

public abstract class MemberKindCheck
  implements Check
{
  private final String description;
  
  private MemberKindCheck(String paramString)
  {
    this.description = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String invoke(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    return Check.DefaultImpls.invoke(this, paramFunctionDescriptor);
  }
  
  public static final class Member
    extends MemberKindCheck
  {
    public static final Member INSTANCE = new Member();
    
    private Member()
    {
      super(null);
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      boolean bool;
      if (paramFunctionDescriptor.getDispatchReceiverParameter() != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public static final class MemberOrExtension
    extends MemberKindCheck
  {
    public static final MemberOrExtension INSTANCE = new MemberOrExtension();
    
    private MemberOrExtension()
    {
      super(null);
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      boolean bool;
      if ((paramFunctionDescriptor.getDispatchReceiverParameter() == null) && (paramFunctionDescriptor.getExtensionReceiverParameter() == null)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
}
