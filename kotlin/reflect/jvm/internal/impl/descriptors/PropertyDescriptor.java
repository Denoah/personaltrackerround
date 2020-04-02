package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface PropertyDescriptor
  extends CallableMemberDescriptor, VariableDescriptorWithAccessors
{
  public abstract List<PropertyAccessorDescriptor> getAccessors();
  
  public abstract FieldDescriptor getBackingField();
  
  public abstract FieldDescriptor getDelegateField();
  
  public abstract PropertyGetterDescriptor getGetter();
  
  public abstract PropertyDescriptor getOriginal();
  
  public abstract Collection<? extends PropertyDescriptor> getOverriddenDescriptors();
  
  public abstract PropertySetterDescriptor getSetter();
  
  public abstract PropertyDescriptor substitute(TypeSubstitutor paramTypeSubstitutor);
}
