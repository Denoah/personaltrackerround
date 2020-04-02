package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class ByteValue
  extends IntegerValueConstant<Byte>
{
  public ByteValue(byte paramByte)
  {
    super(Byte.valueOf(paramByte));
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getByteType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.byteType");
    return paramModuleDescriptor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(((Number)getValue()).byteValue());
    localStringBuilder.append(".toByte()");
    return localStringBuilder.toString();
  }
}
