package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract class VariableDescriptorImpl
  extends DeclarationDescriptorNonRootImpl
  implements VariableDescriptor
{
  protected KotlinType outType;
  
  public VariableDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, KotlinType paramKotlinType, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, paramSourceElement);
    this.outType = paramKotlinType;
  }
  
  public ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    return null;
  }
  
  public ReceiverParameterDescriptor getExtensionReceiverParameter()
  {
    return null;
  }
  
  public VariableDescriptor getOriginal()
  {
    VariableDescriptor localVariableDescriptor = (VariableDescriptor)super.getOriginal();
    if (localVariableDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    return localVariableDescriptor;
  }
  
  public Collection<? extends CallableDescriptor> getOverriddenDescriptors()
  {
    Set localSet = Collections.emptySet();
    if (localSet == null) {
      $$$reportNull$$$0(7);
    }
    return localSet;
  }
  
  public KotlinType getReturnType()
  {
    KotlinType localKotlinType = getType();
    if (localKotlinType == null) {
      $$$reportNull$$$0(9);
    }
    return localKotlinType;
  }
  
  public KotlinType getType()
  {
    KotlinType localKotlinType = this.outType;
    if (localKotlinType == null) {
      $$$reportNull$$$0(4);
    }
    return localKotlinType;
  }
  
  public List<TypeParameterDescriptor> getTypeParameters()
  {
    List localList = Collections.emptyList();
    if (localList == null) {
      $$$reportNull$$$0(8);
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
      $$$reportNull$$$0(6);
    }
    return localList;
  }
  
  public boolean hasSynthesizedParameterNames()
  {
    return false;
  }
  
  public boolean isConst()
  {
    return false;
  }
  
  public void setOutType(KotlinType paramKotlinType)
  {
    this.outType = paramKotlinType;
  }
}
