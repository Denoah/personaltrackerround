package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface Substitutable<T extends DeclarationDescriptorNonRoot>
{
  public abstract T substitute(TypeSubstitutor paramTypeSubstitutor);
}
