package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface ValueParameterDescriptor
  extends ParameterDescriptor, VariableDescriptor
{
  public abstract ValueParameterDescriptor copy(CallableDescriptor paramCallableDescriptor, Name paramName, int paramInt);
  
  public abstract boolean declaresDefaultValue();
  
  public abstract CallableDescriptor getContainingDeclaration();
  
  public abstract int getIndex();
  
  public abstract ValueParameterDescriptor getOriginal();
  
  public abstract Collection<ValueParameterDescriptor> getOverriddenDescriptors();
  
  public abstract KotlinType getVarargElementType();
  
  public abstract boolean isCrossinline();
  
  public abstract boolean isNoinline();
  
  public static final class DefaultImpls
  {
    public static boolean isLateInit(ValueParameterDescriptor paramValueParameterDescriptor)
    {
      return false;
    }
  }
}
