package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

final class Java8ParameterNamesLoader
{
  public static final Java8ParameterNamesLoader INSTANCE = new Java8ParameterNamesLoader();
  private static Cache cache;
  
  private Java8ParameterNamesLoader() {}
  
  public final Cache buildCache(Member paramMember)
  {
    Intrinsics.checkParameterIsNotNull(paramMember, "member");
    Class localClass = paramMember.getClass();
    try
    {
      paramMember = localClass.getMethod("getParameters", new Class[0]);
      return new Cache(paramMember, ReflectClassUtilKt.getSafeClassLoader(localClass).loadClass("java.lang.reflect.Parameter").getMethod("getName", new Class[0]));
    }
    catch (NoSuchMethodException paramMember) {}
    return new Cache(null, null);
  }
  
  public final List<String> loadParameterNames(Member paramMember)
  {
    Intrinsics.checkParameterIsNotNull(paramMember, "member");
    Object localObject1 = cache;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = buildCache(paramMember);
      cache = (Cache)localObject2;
    }
    localObject1 = ((Cache)localObject2).getGetParameters();
    if (localObject1 != null)
    {
      localObject2 = ((Cache)localObject2).getGetName();
      if (localObject2 != null)
      {
        paramMember = ((Method)localObject1).invoke(paramMember, new Object[0]);
        if (paramMember != null)
        {
          paramMember = (Object[])paramMember;
          localObject1 = (Collection)new ArrayList(paramMember.length);
          int i = paramMember.length;
          int j = 0;
          while (j < i)
          {
            Object localObject3 = ((Method)localObject2).invoke(paramMember[j], new Object[0]);
            if (localObject3 != null)
            {
              ((Collection)localObject1).add((String)localObject3);
              j++;
            }
            else
            {
              throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
          }
          return (List)localObject1;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<*>");
      }
    }
    return null;
  }
  
  public static final class Cache
  {
    private final Method getName;
    private final Method getParameters;
    
    public Cache(Method paramMethod1, Method paramMethod2)
    {
      this.getParameters = paramMethod1;
      this.getName = paramMethod2;
    }
    
    public final Method getGetName()
    {
      return this.getName;
    }
    
    public final Method getGetParameters()
    {
      return this.getParameters;
    }
  }
}
