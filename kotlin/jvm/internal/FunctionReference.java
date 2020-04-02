package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference
  extends CallableReference
  implements FunctionBase, KFunction
{
  private final int arity;
  
  public FunctionReference(int paramInt)
  {
    this.arity = paramInt;
  }
  
  public FunctionReference(int paramInt, Object paramObject)
  {
    super(paramObject);
    this.arity = paramInt;
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.function(this);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof FunctionReference))
    {
      paramObject = (FunctionReference)paramObject;
      if ((getOwner() == null ? paramObject.getOwner() != null : !getOwner().equals(paramObject.getOwner())) || (!getName().equals(paramObject.getName())) || (!getSignature().equals(paramObject.getSignature())) || (!Intrinsics.areEqual(getBoundReceiver(), paramObject.getBoundReceiver()))) {
        bool = false;
      }
      return bool;
    }
    if ((paramObject instanceof KFunction)) {
      return paramObject.equals(compute());
    }
    return false;
  }
  
  public int getArity()
  {
    return this.arity;
  }
  
  protected KFunction getReflected()
  {
    return (KFunction)super.getReflected();
  }
  
  public int hashCode()
  {
    int i;
    if (getOwner() == null) {
      i = 0;
    } else {
      i = getOwner().hashCode() * 31;
    }
    return (i + getName().hashCode()) * 31 + getSignature().hashCode();
  }
  
  public boolean isExternal()
  {
    return getReflected().isExternal();
  }
  
  public boolean isInfix()
  {
    return getReflected().isInfix();
  }
  
  public boolean isInline()
  {
    return getReflected().isInline();
  }
  
  public boolean isOperator()
  {
    return getReflected().isOperator();
  }
  
  public boolean isSuspend()
  {
    return getReflected().isSuspend();
  }
  
  public String toString()
  {
    Object localObject = compute();
    if (localObject != this) {
      return localObject.toString();
    }
    if ("<init>".equals(getName()))
    {
      localObject = "constructor (Kotlin reflection is not available)";
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("function ");
      ((StringBuilder)localObject).append(getName());
      ((StringBuilder)localObject).append(" (Kotlin reflection is not available)");
      localObject = ((StringBuilder)localObject).toString();
    }
    return localObject;
  }
}
