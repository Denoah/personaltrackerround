package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class UByteValue
  extends UnsignedValueConstant<Byte>
{
  public UByteValue(byte paramByte)
  {
    super(Byte.valueOf(paramByte));
  }
  
  public KotlinType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    ClassId localClassId = KotlinBuiltIns.FQ_NAMES.uByte;
    Intrinsics.checkExpressionValueIsNotNull(localClassId, "KotlinBuiltIns.FQ_NAMES.uByte");
    paramModuleDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(paramModuleDescriptor, localClassId);
    if (paramModuleDescriptor != null)
    {
      paramModuleDescriptor = paramModuleDescriptor.getDefaultType();
      if (paramModuleDescriptor != null) {
        return (KotlinType)paramModuleDescriptor;
      }
    }
    paramModuleDescriptor = ErrorUtils.createErrorType("Unsigned type UByte not found");
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "ErrorUtils.createErrorTy…ed type UByte not found\")");
    paramModuleDescriptor = (KotlinType)paramModuleDescriptor;
    return paramModuleDescriptor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(((Number)getValue()).byteValue());
    localStringBuilder.append(".toUByte()");
    return localStringBuilder.toString();
  }
}
