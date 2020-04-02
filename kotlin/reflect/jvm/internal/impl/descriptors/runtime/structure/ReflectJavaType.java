package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;

public abstract class ReflectJavaType
  implements JavaType
{
  public static final Factory Factory = new Factory(null);
  
  public ReflectJavaType() {}
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectJavaType)) && (Intrinsics.areEqual(getReflectType(), ((ReflectJavaType)paramObject).getReflectType()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected abstract Type getReflectType();
  
  public int hashCode()
  {
    return getReflectType().hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(getReflectType());
    return localStringBuilder.toString();
  }
  
  public static final class Factory
  {
    private Factory() {}
    
    public final ReflectJavaType create(Type paramType)
    {
      Intrinsics.checkParameterIsNotNull(paramType, "type");
      boolean bool = paramType instanceof Class;
      if (bool)
      {
        Class localClass = (Class)paramType;
        if (localClass.isPrimitive()) {
          return (ReflectJavaType)new ReflectJavaPrimitiveType(localClass);
        }
      }
      if ((!(paramType instanceof GenericArrayType)) && ((!bool) || (!((Class)paramType).isArray())))
      {
        if ((paramType instanceof WildcardType)) {
          paramType = (ReflectJavaType)new ReflectJavaWildcardType((WildcardType)paramType);
        } else {
          paramType = (ReflectJavaType)new ReflectJavaClassifierType(paramType);
        }
      }
      else {
        paramType = (ReflectJavaType)new ReflectJavaArrayType(paramType);
      }
      return paramType;
    }
  }
}
