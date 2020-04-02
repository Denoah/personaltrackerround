package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

public abstract interface ThisClassReceiver
  extends ReceiverValue
{
  public abstract ClassDescriptor getClassDescriptor();
}
