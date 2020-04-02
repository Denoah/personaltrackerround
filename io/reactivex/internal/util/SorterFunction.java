package io.reactivex.internal.util;

import io.reactivex.functions.Function;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SorterFunction<T>
  implements Function<List<T>, List<T>>
{
  final Comparator<? super T> comparator;
  
  public SorterFunction(Comparator<? super T> paramComparator)
  {
    this.comparator = paramComparator;
  }
  
  public List<T> apply(List<T> paramList)
    throws Exception
  {
    Collections.sort(paramList, this.comparator);
    return paramList;
  }
}
