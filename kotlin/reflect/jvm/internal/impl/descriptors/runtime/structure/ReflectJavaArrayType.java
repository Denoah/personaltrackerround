package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;

public final class ReflectJavaArrayType
  extends ReflectJavaType
  implements JavaArrayType
{
  private final ReflectJavaType componentType;
  private final Type reflectType;
  
  public ReflectJavaArrayType(Type paramType)
  {
    this.reflectType = paramType;
    Object localObject = getReflectType();
    if ((localObject instanceof GenericArrayType))
    {
      paramType = ReflectJavaType.Factory;
      localObject = ((GenericArrayType)localObject).getGenericComponentType();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "genericComponentType");
      paramType = paramType.create((Type)localObject);
    }
    else
    {
      if (!(localObject instanceof Class)) {
        break label105;
      }
      localObject = (Class)localObject;
      if (!((Class)localObject).isArray()) {
        break label105;
      }
      paramType = ReflectJavaType.Factory;
      localObject = ((Class)localObject).getComponentType();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "getComponentType()");
      paramType = paramType.create((Type)localObject);
    }
    this.componentType = paramType;
    return;
    label105:
    paramType = new StringBuilder();
    paramType.append("Not an array type (");
    paramType.append(getReflectType().getClass());
    paramType.append("): ");
    paramType.append(getReflectType());
    throw ((Throwable)new IllegalArgumentException(paramType.toString()));
  }
  
  public ReflectJavaType getComponentType()
  {
    return this.componentType;
  }
  
  protected Type getReflectType()
  {
    return this.reflectType;
  }
}
