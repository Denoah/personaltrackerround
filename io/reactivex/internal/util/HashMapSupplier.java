package io.reactivex.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public enum HashMapSupplier
  implements Callable<Map<Object, Object>>
{
  static
  {
    HashMapSupplier localHashMapSupplier = new HashMapSupplier("INSTANCE", 0);
    INSTANCE = localHashMapSupplier;
    $VALUES = new HashMapSupplier[] { localHashMapSupplier };
  }
  
  private HashMapSupplier() {}
  
  public static <K, V> Callable<Map<K, V>> asCallable()
  {
    return INSTANCE;
  }
  
  public Map<Object, Object> call()
    throws Exception
  {
    return new HashMap();
  }
}
