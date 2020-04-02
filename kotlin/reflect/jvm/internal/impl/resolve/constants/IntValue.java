package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class IntValue
  extends IntegerValueConstant<Integer>
{
  public IntValue(int paramInt)
  {
    super(Integer.valueOf(paramInt));
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getIntType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.intType");
    return paramModuleDescriptor;
  }
}
