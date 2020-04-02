package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

public final class CollectionsKt
{
  public static final <T> void addIfNotNull(Collection<T> paramCollection, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$addIfNotNull");
    if (paramT != null) {
      paramCollection.add(paramT);
    }
  }
  
  private static final int capacity(int paramInt)
  {
    int i = 3;
    if (paramInt < 3) {
      paramInt = i;
    } else {
      paramInt = paramInt + paramInt / 3 + 1;
    }
    return paramInt;
  }
  
  public static final <T> List<T> compact(ArrayList<T> paramArrayList)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayList, "$this$compact");
    int i = paramArrayList.size();
    if (i != 0)
    {
      if (i != 1)
      {
        paramArrayList.trimToSize();
        paramArrayList = (List)paramArrayList;
      }
      else
      {
        paramArrayList = kotlin.collections.CollectionsKt.listOf(kotlin.collections.CollectionsKt.first((List)paramArrayList));
      }
    }
    else {
      paramArrayList = kotlin.collections.CollectionsKt.emptyList();
    }
    return paramArrayList;
  }
  
  public static final <K> Map<K, Integer> mapToIndex(Iterable<? extends K> paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$mapToIndex");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Iterator localIterator = paramIterable.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      paramIterable = localIterator.next();
      ((Map)localLinkedHashMap).put(paramIterable, Integer.valueOf(i));
    }
    return (Map)localLinkedHashMap;
  }
  
  public static final <K, V> HashMap<K, V> newHashMapWithExpectedSize(int paramInt)
  {
    return new HashMap(capacity(paramInt));
  }
  
  public static final <E> HashSet<E> newHashSetWithExpectedSize(int paramInt)
  {
    return new HashSet(capacity(paramInt));
  }
  
  public static final <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int paramInt)
  {
    return new LinkedHashSet(capacity(paramInt));
  }
}
