package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;

public abstract interface ClassifierDescriptorWithTypeParameters
  extends ClassifierDescriptor, DeclarationDescriptorWithVisibility, MemberDescriptor, Substitutable<ClassifierDescriptorWithTypeParameters>
{
  public abstract List<TypeParameterDescriptor> getDeclaredTypeParameters();
  
  public abstract boolean isInner();
}
