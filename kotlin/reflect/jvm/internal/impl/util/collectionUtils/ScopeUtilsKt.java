package kotlin.reflect.jvm.internal.impl.util.collectionUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.jvm.internal.Intrinsics;

public final class ScopeUtilsKt
{
  public static final <T> Collection<T> concat(Collection<? extends T> paramCollection1, Collection<? extends T> paramCollection2)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection2, "collection");
    if (paramCollection2.isEmpty()) {
      return paramCollection1;
    }
    if (paramCollection1 == null) {
      return paramCollection2;
    }
    if ((paramCollection1 instanceof LinkedHashSet))
    {
      ((LinkedHashSet)paramCollection1).addAll(paramCollection2);
      return paramCollection1;
    }
    paramCollection1 = new LinkedHashSet(paramCollection1);
    paramCollection1.addAll(paramCollection2);
    return (Collection)paramCollection1;
  }
}
