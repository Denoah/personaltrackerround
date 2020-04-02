package kotlin.reflect.jvm.internal;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\016\n\002\030\002\n\002\b\003\"\030\020\000\032\0020\001*\0020\0028BX?\004?\006\006\032\004\b\003\020\004?\006\005"}, d2={"signature", "", "Ljava/lang/reflect/Method;", "getSignature", "(Ljava/lang/reflect/Method;)Ljava/lang/String;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class RuntimeTypeMapperKt
{
  private static final String getSignature(Method paramMethod)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramMethod.getName());
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfClass, "parameterTypes");
    localStringBuilder.append(ArraysKt.joinToString$default(arrayOfClass, (CharSequence)"", (CharSequence)"(", (CharSequence)")", 0, null, (Function1)signature.1.INSTANCE, 24, null));
    paramMethod = paramMethod.getReturnType();
    Intrinsics.checkExpressionValueIsNotNull(paramMethod, "returnType");
    localStringBuilder.append(ReflectClassUtilKt.getDesc(paramMethod));
    return localStringBuilder.toString();
  }
}
