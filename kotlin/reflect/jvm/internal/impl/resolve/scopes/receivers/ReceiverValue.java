package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface ReceiverValue
{
  public abstract KotlinType getType();
}
