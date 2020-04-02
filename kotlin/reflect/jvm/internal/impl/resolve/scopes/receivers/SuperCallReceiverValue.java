package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface SuperCallReceiverValue
  extends ReceiverValue
{
  public abstract KotlinType getThisType();
}
