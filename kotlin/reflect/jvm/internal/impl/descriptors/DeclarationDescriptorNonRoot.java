package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface DeclarationDescriptorNonRoot
  extends DeclarationDescriptorWithSource
{
  public abstract DeclarationDescriptor getContainingDeclaration();
}
