package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract class AbstractReceiverValue
  implements ReceiverValue
{
  private final ReceiverValue original;
  protected final KotlinType receiverType;
  
  public AbstractReceiverValue(KotlinType paramKotlinType, ReceiverValue paramReceiverValue)
  {
    this.receiverType = paramKotlinType;
    if (paramReceiverValue == null) {
      paramReceiverValue = this;
    }
    this.original = paramReceiverValue;
  }
  
  public KotlinType getType()
  {
    KotlinType localKotlinType = this.receiverType;
    if (localKotlinType == null) {
      $$$reportNull$$$0(1);
    }
    return localKotlinType;
  }
}
