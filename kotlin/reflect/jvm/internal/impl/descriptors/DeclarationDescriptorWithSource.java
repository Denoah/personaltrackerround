package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface DeclarationDescriptorWithSource
  extends DeclarationDescriptor
{
  public abstract SourceElement getSource();
}
