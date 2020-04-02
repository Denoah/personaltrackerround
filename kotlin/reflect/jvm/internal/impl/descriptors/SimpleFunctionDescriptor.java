package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface SimpleFunctionDescriptor
  extends FunctionDescriptor
{
  public abstract FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder();
}
