package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface PropertyAccessorDescriptor
  extends VariableAccessorDescriptor
{
  public abstract PropertyDescriptor getCorrespondingProperty();
  
  public abstract boolean isDefault();
}
