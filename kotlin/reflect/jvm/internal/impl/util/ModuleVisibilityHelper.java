package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;

public abstract interface ModuleVisibilityHelper
{
  public abstract boolean isInFriendModule(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2);
  
  public static final class EMPTY
    implements ModuleVisibilityHelper
  {
    public static final EMPTY INSTANCE = new EMPTY();
    
    private EMPTY() {}
    
    public boolean isInFriendModule(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
    {
      Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor1, "what");
      Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor2, "from");
      return true;
    }
  }
}
