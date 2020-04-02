package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;

public final class ReflectJavaClassFinderKt
{
  public static final Class<?> tryLoadClass(ClassLoader paramClassLoader, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramClassLoader, "$this$tryLoadClass");
    Intrinsics.checkParameterIsNotNull(paramString, "fqName");
    try
    {
      paramClassLoader = Class.forName(paramString, false, paramClassLoader);
    }
    catch (ClassNotFoundException paramClassLoader)
    {
      paramClassLoader = null;
    }
    return paramClassLoader;
  }
}
