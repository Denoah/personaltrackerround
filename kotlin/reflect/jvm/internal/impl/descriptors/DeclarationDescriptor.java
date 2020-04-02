package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;

public abstract interface DeclarationDescriptor
  extends Named, Annotated
{
  public abstract <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD);
  
  public abstract DeclarationDescriptor getContainingDeclaration();
  
  public abstract DeclarationDescriptor getOriginal();
}
