package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class StringValue
  extends ConstantValue<String>
{
  public StringValue(String paramString)
  {
    super(paramString);
  }
  
  public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    paramModuleDescriptor = paramModuleDescriptor.getBuiltIns().getStringType();
    Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "module.builtIns.stringType");
    return paramModuleDescriptor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('"');
    localStringBuilder.append((String)getValue());
    localStringBuilder.append('"');
    return localStringBuilder.toString();
  }
}
