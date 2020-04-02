package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface ClassConstructorDescriptor
  extends ConstructorDescriptor
{
  public abstract ClassConstructorDescriptor getOriginal();
  
  public abstract ClassConstructorDescriptor substitute(TypeSubstitutor paramTypeSubstitutor);
}
