package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface ReceiverParameterDescriptor
  extends ParameterDescriptor
{
  public abstract ReceiverValue getValue();
  
  public abstract ReceiverParameterDescriptor substitute(TypeSubstitutor paramTypeSubstitutor);
}
