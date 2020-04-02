package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public abstract interface TypeAliasDescriptor
  extends ClassifierDescriptorWithTypeParameters
{
  public abstract ClassDescriptor getClassDescriptor();
  
  public abstract SimpleType getExpandedType();
  
  public abstract SimpleType getUnderlyingType();
}
