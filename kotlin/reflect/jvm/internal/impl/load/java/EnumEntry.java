package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

public final class EnumEntry
  extends JavaDefaultValue
{
  private final ClassDescriptor descriptor;
  
  public EnumEntry(ClassDescriptor paramClassDescriptor)
  {
    super(null);
    this.descriptor = paramClassDescriptor;
  }
}
