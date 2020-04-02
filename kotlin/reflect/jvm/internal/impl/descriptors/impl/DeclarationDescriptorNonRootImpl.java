package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract class DeclarationDescriptorNonRootImpl
  extends DeclarationDescriptorImpl
  implements DeclarationDescriptorNonRoot
{
  private final DeclarationDescriptor containingDeclaration;
  private final SourceElement source;
  
  protected DeclarationDescriptorNonRootImpl(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, SourceElement paramSourceElement)
  {
    super(paramAnnotations, paramName);
    this.containingDeclaration = paramDeclarationDescriptor;
    this.source = paramSourceElement;
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    DeclarationDescriptor localDeclarationDescriptor = this.containingDeclaration;
    if (localDeclarationDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    return localDeclarationDescriptor;
  }
  
  public DeclarationDescriptorWithSource getOriginal()
  {
    DeclarationDescriptorWithSource localDeclarationDescriptorWithSource = (DeclarationDescriptorWithSource)super.getOriginal();
    if (localDeclarationDescriptorWithSource == null) {
      $$$reportNull$$$0(4);
    }
    return localDeclarationDescriptorWithSource;
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = this.source;
    if (localSourceElement == null) {
      $$$reportNull$$$0(6);
    }
    return localSourceElement;
  }
}
