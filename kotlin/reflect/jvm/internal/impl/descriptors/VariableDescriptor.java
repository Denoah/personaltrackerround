package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;

public abstract interface VariableDescriptor
  extends ValueDescriptor
{
  public abstract ConstantValue<?> getCompileTimeInitializer();
  
  public abstract boolean isConst();
  
  public abstract boolean isLateInit();
  
  public abstract boolean isVar();
}
