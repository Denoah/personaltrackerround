package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface MemberDescriptor
  extends DeclarationDescriptorNonRoot, DeclarationDescriptorWithVisibility
{
  public abstract Modality getModality();
  
  public abstract Visibility getVisibility();
  
  public abstract boolean isActual();
  
  public abstract boolean isExpect();
  
  public abstract boolean isExternal();
}
