package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface DeclarationDescriptorWithVisibility
  extends DeclarationDescriptor
{
  public abstract Visibility getVisibility();
}
