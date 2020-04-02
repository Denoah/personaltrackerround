package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ImplicitClassReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;

public class LazyClassReceiverParameterDescriptor
  extends AbstractReceiverParameterDescriptor
{
  private final ClassDescriptor descriptor;
  private final ImplicitClassReceiver receiverValue;
  
  public LazyClassReceiverParameterDescriptor(ClassDescriptor paramClassDescriptor)
  {
    super(Annotations.Companion.getEMPTY());
    this.descriptor = paramClassDescriptor;
    this.receiverValue = new ImplicitClassReceiver(paramClassDescriptor, null);
  }
  
  public DeclarationDescriptor getContainingDeclaration()
  {
    ClassDescriptor localClassDescriptor = this.descriptor;
    if (localClassDescriptor == null) {
      $$$reportNull$$$0(2);
    }
    return localClassDescriptor;
  }
  
  public ReceiverValue getValue()
  {
    ImplicitClassReceiver localImplicitClassReceiver = this.receiverValue;
    if (localImplicitClassReceiver == null) {
      $$$reportNull$$$0(1);
    }
    return localImplicitClassReceiver;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("class ");
    localStringBuilder.append(this.descriptor.getName());
    localStringBuilder.append("::this");
    return localStringBuilder.toString();
  }
}
