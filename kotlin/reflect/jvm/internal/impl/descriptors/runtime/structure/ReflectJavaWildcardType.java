package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaWildcardType;

public final class ReflectJavaWildcardType
  extends ReflectJavaType
  implements JavaWildcardType
{
  private final WildcardType reflectType;
  
  public ReflectJavaWildcardType(WildcardType paramWildcardType)
  {
    this.reflectType = paramWildcardType;
  }
  
  public ReflectJavaType getBound()
  {
    Object localObject1 = getReflectType().getUpperBounds();
    Object localObject2 = getReflectType().getLowerBounds();
    if ((localObject1.length <= 1) && (localObject2.length <= 1))
    {
      int i = localObject2.length;
      ReflectJavaType.Factory localFactory = null;
      if (i == 1)
      {
        localFactory = ReflectJavaType.Factory;
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "lowerBounds");
        localObject2 = ArraysKt.single((Object[])localObject2);
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "lowerBounds.single()");
        localObject2 = localFactory.create((Type)localObject2);
      }
      else
      {
        localObject2 = localFactory;
        if (localObject1.length == 1)
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "upperBounds");
          localObject1 = (Type)ArraysKt.single((Object[])localObject1);
          localObject2 = localFactory;
          if ((Intrinsics.areEqual(localObject1, Object.class) ^ true))
          {
            localObject2 = ReflectJavaType.Factory;
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "ub");
            localObject2 = ((ReflectJavaType.Factory)localObject2).create((Type)localObject1);
          }
        }
      }
      return localObject2;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Wildcard types with many bounds are not yet supported: ");
    ((StringBuilder)localObject2).append(getReflectType());
    throw ((Throwable)new UnsupportedOperationException(((StringBuilder)localObject2).toString()));
  }
  
  protected WildcardType getReflectType()
  {
    return this.reflectType;
  }
  
  public boolean isExtends()
  {
    Type[] arrayOfType = getReflectType().getUpperBounds();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfType, "reflectType.upperBounds");
    return Intrinsics.areEqual((Type)ArraysKt.firstOrNull(arrayOfType), Object.class) ^ true;
  }
}
