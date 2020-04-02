package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;

public class ReceiverParameterDescriptorImpl
  extends AbstractReceiverParameterDescriptor
{
  private final DeclarationDescriptor containingDeclaration;
  private final ReceiverValue value;
  
  public ReceiverParameterDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, ReceiverValue paramReceiverValue, Annotations paramAnnotations)
  {
    super(paramAnnotations);
    this.containingDeclaration = paramDeclarationDescriptor;
    this.value = paramReceiverValue;
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    DeclarationDescriptor localDeclarationDescriptor = this.containingDeclaration;
    if (localDeclarationDescriptor == null) {
      $$$reportNull$$$0(4);
    }
    return localDeclarationDescriptor;
  }
  
  public ReceiverValue getValue()
  {
    ReceiverValue localReceiverValue = this.value;
    if (localReceiverValue == null) {
      $$$reportNull$$$0(3);
    }
    return localReceiverValue;
  }
}
