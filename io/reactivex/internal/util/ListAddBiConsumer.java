package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.List;

public enum ListAddBiConsumer
  implements BiFunction<List, Object, List>
{
  static
  {
    ListAddBiConsumer localListAddBiConsumer = new ListAddBiConsumer("INSTANCE", 0);
    INSTANCE = localListAddBiConsumer;
    $VALUES = new ListAddBiConsumer[] { localListAddBiConsumer };
  }
  
  private ListAddBiConsumer() {}
  
  public static <T> BiFunction<List<T>, T, List<T>> instance()
  {
    return INSTANCE;
  }
  
  public List apply(List paramList, Object paramObject)
    throws Exception
  {
    paramList.add(paramObject);
    return paramList;
  }
}
