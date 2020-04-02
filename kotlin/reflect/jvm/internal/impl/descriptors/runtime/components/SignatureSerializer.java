package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;

final class SignatureSerializer
{
  public static final SignatureSerializer INSTANCE = new SignatureSerializer();
  
  private SignatureSerializer() {}
  
  public final String constructorDesc(Constructor<?> paramConstructor)
  {
    Intrinsics.checkParameterIsNotNull(paramConstructor, "constructor");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(");
    for (paramConstructor : paramConstructor.getParameterTypes())
    {
      Intrinsics.checkExpressionValueIsNotNull(paramConstructor, "parameterType");
      localStringBuilder.append(ReflectClassUtilKt.getDesc(paramConstructor));
    }
    localStringBuilder.append(")V");
    paramConstructor = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramConstructor, "sb.toString()");
    return paramConstructor;
  }
  
  public final String fieldDesc(Field paramField)
  {
    Intrinsics.checkParameterIsNotNull(paramField, "field");
    paramField = paramField.getType();
    Intrinsics.checkExpressionValueIsNotNull(paramField, "field.type");
    return ReflectClassUtilKt.getDesc(paramField);
  }
  
  public final String methodDesc(Method paramMethod)
  {
    Intrinsics.checkParameterIsNotNull(paramMethod, "method");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(");
    for (Class localClass : paramMethod.getParameterTypes())
    {
      Intrinsics.checkExpressionValueIsNotNull(localClass, "parameterType");
      localStringBuilder.append(ReflectClassUtilKt.getDesc(localClass));
    }
    localStringBuilder.append(")");
    paramMethod = paramMethod.getReturnType();
    Intrinsics.checkExpressionValueIsNotNull(paramMethod, "method.returnType");
    localStringBuilder.append(ReflectClassUtilKt.getDesc(paramMethod));
    paramMethod = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramMethod, "sb.toString()");
    return paramMethod;
  }
}
