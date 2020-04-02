package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.TransientReceiver;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public abstract class AbstractReceiverParameterDescriptor
  extends DeclarationDescriptorImpl
  implements ReceiverParameterDescriptor
{
  private static final Name RECEIVER_PARAMETER_NAME = Name.special("<this>");
  
  public AbstractReceiverParameterDescriptor(Annotations paramAnnotations)
  {
    super(paramAnnotations, RECEIVER_PARAMETER_NAME);
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitReceiverParameterDescriptor(this, paramD);
  }
  
  public ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    return null;
  }
  
  public ReceiverParameterDescriptor getExtensionReceiverParameter()
  {
    return null;
  }
  
  public ParameterDescriptor getOriginal()
  {
    return this;
  }
  
  public Collection<? extends CallableDescriptor> getOverriddenDescriptors()
  {
    Set localSet = Collections.emptySet();
    if (localSet == null) {
      $$$reportNull$$$0(5);
    }
    return localSet;
  }
  
  public KotlinType getReturnType()
  {
    return getType();
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = SourceElement.NO_SOURCE;
    if (localSourceElement == null) {
      $$$reportNull$$$0(8);
    }
    return localSourceElement;
  }
  
  public KotlinType getType()
  {
    KotlinType localKotlinType = getValue().getType();
    if (localKotlinType == null) {
      $$$reportNull$$$0(3);
    }
    return localKotlinType;
  }
  
  public List<TypeParameterDescriptor> getTypeParameters()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(2);
    }
    return localList;
  }
  
  public <V> V getUserData(CallableDescriptor.UserDataKey<V> paramUserDataKey)
  {
    return null;
  }
  
  public List<ValueParameterDescriptor> getValueParameters()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(4);
    }
    return localList;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = Visibilities.LOCAL;
    if (localVisibility == null) {
      $$$reportNull$$$0(6);
    }
    return localVisibility;
  }
  
  public boolean hasSynthesizedParameterNames()
  {
    return false;
  }
  
  public ReceiverParameterDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(1);
    }
    if (paramTypeSubstitutor.isEmpty()) {
      return this;
    }
    if ((getContainingDeclaration() instanceof ClassDescriptor)) {
      paramTypeSubstitutor = paramTypeSubstitutor.substitute(getType(), Variance.OUT_VARIANCE);
    } else {
      paramTypeSubstitutor = paramTypeSubstitutor.substitute(getType(), Variance.INVARIANT);
    }
    if (paramTypeSubstitutor == null) {
      return null;
    }
    if (paramTypeSubstitutor == getType()) {
      return this;
    }
    return new ReceiverParameterDescriptorImpl(getContainingDeclaration(), new TransientReceiver(paramTypeSubstitutor), getAnnotations());
  }
}
