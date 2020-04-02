package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class MergerBiFunction<T>
  implements BiFunction<List<T>, List<T>, List<T>>
{
  final Comparator<? super T> comparator;
  
  public MergerBiFunction(Comparator<? super T> paramComparator)
  {
    this.comparator = paramComparator;
  }
  
  public List<T> apply(List<T> paramList1, List<T> paramList2)
    throws Exception
  {
    int i = paramList1.size() + paramList2.size();
    if (i == 0) {
      return new ArrayList();
    }
    ArrayList localArrayList = new ArrayList(i);
    Iterator localIterator1 = paramList1.iterator();
    Iterator localIterator2 = paramList2.iterator();
    if (localIterator1.hasNext()) {
      paramList1 = localIterator1.next();
    } else {
      paramList1 = null;
    }
    List<T> localList = paramList1;
    if (localIterator2.hasNext())
    {
      paramList2 = localIterator2.next();
    }
    else
    {
      paramList2 = null;
      paramList1 = localList;
    }
    for (;;)
    {
      if ((paramList1 == null) || (paramList2 == null)) {
        break label194;
      }
      if (this.comparator.compare(paramList1, paramList2) < 0)
      {
        localArrayList.add(paramList1);
        if (localIterator1.hasNext()) {
          paramList1 = localIterator1.next();
        } else {
          paramList1 = null;
        }
      }
      else
      {
        localArrayList.add(paramList2);
        localList = paramList1;
        if (!localIterator2.hasNext()) {
          break;
        }
        paramList2 = localIterator2.next();
      }
    }
    label194:
    if (paramList1 != null)
    {
      localArrayList.add(paramList1);
      while (localIterator1.hasNext()) {
        localArrayList.add(localIterator1.next());
      }
    }
    localArrayList.add(paramList2);
    while (localIterator2.hasNext()) {
      localArrayList.add(localIterator2.next());
    }
    return localArrayList;
  }
}
