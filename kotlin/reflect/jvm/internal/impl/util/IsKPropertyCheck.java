package kotlin.reflect.jvm.internal.impl.util;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

final class IsKPropertyCheck
  implements Check
{
  public static final IsKPropertyCheck INSTANCE = new IsKPropertyCheck();
  private static final String description = "second parameter must be of type KProperty<*> or its supertype";
  
  private IsKPropertyCheck() {}
  
  public boolean check(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    Object localObject = (ValueParameterDescriptor)paramFunctionDescriptor.getValueParameters().get(1);
    paramFunctionDescriptor = ReflectionTypes.Companion;
    Intrinsics.checkExpressionValueIsNotNull(localObject, "secondParameter");
    paramFunctionDescriptor = paramFunctionDescriptor.createKPropertyStarType(DescriptorUtilsKt.getModule((DeclarationDescriptor)localObject));
    boolean bool;
    if (paramFunctionDescriptor != null)
    {
      localObject = ((ValueParameterDescriptor)localObject).getType();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "secondParameter.type");
      bool = TypeUtilsKt.isSubtypeOf(paramFunctionDescriptor, TypeUtilsKt.makeNotNullable((KotlinType)localObject));
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public String invoke(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    return Check.DefaultImpls.invoke(this, paramFunctionDescriptor);
  }
}
