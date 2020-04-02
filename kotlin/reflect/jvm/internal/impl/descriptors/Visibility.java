package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;

public abstract class Visibility
{
  private final boolean isPublicAPI;
  private final String name;
  
  protected Visibility(String paramString, boolean paramBoolean)
  {
    this.name = paramString;
    this.isPublicAPI = paramBoolean;
  }
  
  protected Integer compareTo(Visibility paramVisibility)
  {
    Intrinsics.checkParameterIsNotNull(paramVisibility, "visibility");
    return Visibilities.compareLocal(this, paramVisibility);
  }
  
  public String getInternalDisplayName()
  {
    return this.name;
  }
  
  public final boolean isPublicAPI()
  {
    return this.isPublicAPI;
  }
  
  public abstract boolean isVisible(ReceiverValue paramReceiverValue, DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility, DeclarationDescriptor paramDeclarationDescriptor);
  
  public Visibility normalize()
  {
    return this;
  }
  
  public final String toString()
  {
    return getInternalDisplayName();
  }
}
