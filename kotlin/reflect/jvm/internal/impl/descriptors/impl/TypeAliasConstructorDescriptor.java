package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;

public abstract interface TypeAliasConstructorDescriptor
  extends ConstructorDescriptor
{
  public abstract ClassConstructorDescriptor getUnderlyingConstructorDescriptor();
}
