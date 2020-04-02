package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Reflection;

public final class ClassicTypeCheckerContextKt
{
  private static final String errorMessage(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ClassicTypeCheckerContext couldn't handle ");
    localStringBuilder.append(Reflection.getOrCreateKotlinClass(paramObject.getClass()));
    localStringBuilder.append(' ');
    localStringBuilder.append(paramObject);
    return localStringBuilder.toString();
  }
}
