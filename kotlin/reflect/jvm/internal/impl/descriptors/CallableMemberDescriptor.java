package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;

public abstract interface CallableMemberDescriptor
  extends CallableDescriptor, MemberDescriptor
{
  public abstract CallableMemberDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, Kind paramKind, boolean paramBoolean);
  
  public abstract Kind getKind();
  
  public abstract CallableMemberDescriptor getOriginal();
  
  public abstract Collection<? extends CallableMemberDescriptor> getOverriddenDescriptors();
  
  public abstract void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> paramCollection);
  
  public static enum Kind
  {
    static
    {
      DELEGATION = new Kind("DELEGATION", 2);
      Kind localKind = new Kind("SYNTHESIZED", 3);
      SYNTHESIZED = localKind;
      $VALUES = new Kind[] { DECLARATION, FAKE_OVERRIDE, DELEGATION, localKind };
    }
    
    private Kind() {}
    
    public boolean isReal()
    {
      boolean bool;
      if (this != FAKE_OVERRIDE) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}
