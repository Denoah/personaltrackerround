package kotlin.reflect.jvm.internal.impl.util;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;

public abstract class ValueParameterCountCheck
  implements Check
{
  private final String description;
  
  private ValueParameterCountCheck(String paramString)
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
  
  public static final class AtLeast
    extends ValueParameterCountCheck
  {
    private final int n;
    
    public AtLeast(int paramInt)
    {
      super(null);
      this.n = paramInt;
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      boolean bool;
      if (paramFunctionDescriptor.getValueParameters().size() >= this.n) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public static final class Equals
    extends ValueParameterCountCheck
  {
    private final int n;
    
    public Equals(int paramInt)
    {
      super(null);
      this.n = paramInt;
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      boolean bool;
      if (paramFunctionDescriptor.getValueParameters().size() == this.n) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public static final class NoValueParameters
    extends ValueParameterCountCheck
  {
    public static final NoValueParameters INSTANCE = new NoValueParameters();
    
    private NoValueParameters()
    {
      super(null);
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      return paramFunctionDescriptor.getValueParameters().isEmpty();
    }
  }
  
  public static final class SingleValueParameter
    extends ValueParameterCountCheck
  {
    public static final SingleValueParameter INSTANCE = new SingleValueParameter();
    
    private SingleValueParameter()
    {
      super(null);
    }
    
    public boolean check(FunctionDescriptor paramFunctionDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
      int i = paramFunctionDescriptor.getValueParameters().size();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      return bool;
    }
  }
}
