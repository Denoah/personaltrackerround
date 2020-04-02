package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

public final class ReflectJavaPrimitiveType
  extends ReflectJavaType
  implements JavaPrimitiveType
{
  private final Class<?> reflectType;
  
  public ReflectJavaPrimitiveType(Class<?> paramClass)
  {
    this.reflectType = paramClass;
  }
  
  protected Class<?> getReflectType()
  {
    return this.reflectType;
  }
  
  public PrimitiveType getType()
  {
    Object localObject;
    if (Intrinsics.areEqual(getReflectType(), Void.TYPE))
    {
      localObject = null;
    }
    else
    {
      localObject = JvmPrimitiveType.get(getReflectType().getName());
      Intrinsics.checkExpressionValueIsNotNull(localObject, "JvmPrimitiveType.get(reflectType.name)");
      localObject = ((JvmPrimitiveType)localObject).getPrimitiveType();
    }
    return localObject;
  }
}
