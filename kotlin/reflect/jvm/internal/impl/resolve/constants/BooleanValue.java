package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class BooleanValue
  extends ConstantValue<Boolean>
{
  public BooleanValue(boolean paramBoolean)
  {
    super(Boolean.valueOf(paramBoolean));
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getBooleanType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.booleanType");
    return paramModuleDescriptor;
  }
}
