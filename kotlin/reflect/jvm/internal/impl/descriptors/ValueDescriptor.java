package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface ValueDescriptor
  extends CallableDescriptor
{
  public abstract DeclarationDescriptor getContainingDeclaration();
  
  public abstract KotlinType getType();
}
