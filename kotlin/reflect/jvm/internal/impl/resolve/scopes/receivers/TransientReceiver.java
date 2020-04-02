package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class TransientReceiver
  extends AbstractReceiverValue
{
  public TransientReceiver(KotlinType paramKotlinType)
  {
    this(paramKotlinType, null);
  }
  
  private TransientReceiver(KotlinType paramKotlinType, ReceiverValue paramReceiverValue)
  {
    super(paramKotlinType, paramReceiverValue);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{Transient} : ");
    localStringBuilder.append(getType());
    return localStringBuilder.toString();
  }
}
