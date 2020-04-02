package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class NullValue
  extends ConstantValue<Void>
{
  public NullValue()
  {
    super(null);
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getNullableNothingType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.nullableNothingType");
    return paramModuleDescriptor;
  }
}
