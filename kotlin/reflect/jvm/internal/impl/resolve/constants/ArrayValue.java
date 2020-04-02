package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.List;
import kotlin._Assertions;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class ArrayValue
  extends ConstantValue<List<? extends ConstantValue<?>>>
{
  private final Function1<ModuleDescriptor, KotlinType> computeType;
  
  public ArrayValue(List<? extends ConstantValue<?>> paramList, Function1<? super ModuleDescriptor, ? extends KotlinType> paramFunction1)
  {
    super(paramList);
    this.computeType = paramFunction1;
  }
  
  public KotlinType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = (KotlinType)this.computeType.invoke(paramModuleDescriptor);
    int i;
    if ((!KotlinBuiltIns.isArray(paramModuleDescriptor)) && (!KotlinBuiltIns.isPrimitiveArray(paramModuleDescriptor))) {
      i = 0;
    } else {
      i = 1;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Type should be an array, but was ");
      localStringBuilder.append(paramModuleDescriptor);
      localStringBuilder.append(": ");
      localStringBuilder.append((List)getValue());
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    return paramModuleDescriptor;
  }
}
