package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface CallableDescriptor
  extends DeclarationDescriptorNonRoot, DeclarationDescriptorWithVisibility, Substitutable<CallableDescriptor>
{
  public abstract ReceiverParameterDescriptor getDispatchReceiverParameter();
  
  public abstract ReceiverParameterDescriptor getExtensionReceiverParameter();
  
  public abstract CallableDescriptor getOriginal();
  
  public abstract Collection<? extends CallableDescriptor> getOverriddenDescriptors();
  
  public abstract KotlinType getReturnType();
  
  public abstract List<TypeParameterDescriptor> getTypeParameters();
  
  public abstract <V> V getUserData(UserDataKey<V> paramUserDataKey);
  
  public abstract List<ValueParameterDescriptor> getValueParameters();
  
  public abstract boolean hasSynthesizedParameterNames();
  
  public static abstract interface UserDataKey<V> {}
}
