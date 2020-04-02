package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public abstract class ClassDescriptorBase
  extends AbstractClassDescriptor
{
  private final DeclarationDescriptor containingDeclaration;
  private final boolean isExternal;
  private final SourceElement source;
  
  protected ClassDescriptorBase(StorageManager paramStorageManager, DeclarationDescriptor paramDeclarationDescriptor, Name paramName, SourceElement paramSourceElement, boolean paramBoolean)
  {
    super(paramStorageManager, paramName);
    this.containingDeclaration = paramDeclarationDescriptor;
    this.source = paramSourceElement;
    this.isExternal = paramBoolean;
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    DeclarationDescriptor localDeclarationDescriptor = this.containingDeclaration;
    if (localDeclarationDescriptor == null) {
      $$$reportNull$$$0(4);
    }
    return localDeclarationDescriptor;
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = this.source;
    if (localSourceElement == null) {
      $$$reportNull$$$0(5);
    }
    return localSourceElement;
  }
  
  public boolean isExternal()
  {
    return this.isExternal;
  }
}
