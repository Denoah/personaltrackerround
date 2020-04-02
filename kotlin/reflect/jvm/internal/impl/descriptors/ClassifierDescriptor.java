package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public abstract interface ClassifierDescriptor
  extends DeclarationDescriptorNonRoot
{
  public abstract SimpleType getDefaultType();
  
  public abstract ClassifierDescriptor getOriginal();
  
  public abstract TypeConstructor getTypeConstructor();
}
