package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class ExtensionReceiver
  extends AbstractReceiverValue
  implements ImplicitReceiver
{
  private final CallableDescriptor descriptor;
  
  public ExtensionReceiver(CallableDescriptor paramCallableDescriptor, KotlinType paramKotlinType, ReceiverValue paramReceiverValue)
  {
    super(paramKotlinType, paramReceiverValue);
    this.descriptor = paramCallableDescriptor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getType());
    localStringBuilder.append(": Ext {");
    localStringBuilder.append(this.descriptor);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
