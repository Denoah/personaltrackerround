package io.reactivex.internal.util;

import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public enum ArrayListSupplier
  implements Callable<List<Object>>, Function<Object, List<Object>>
{
  static
  {
    ArrayListSupplier localArrayListSupplier = new ArrayListSupplier("INSTANCE", 0);
    INSTANCE = localArrayListSupplier;
    $VALUES = new ArrayListSupplier[] { localArrayListSupplier };
  }
  
  private ArrayListSupplier() {}
  
  public static <T> Callable<List<T>> asCallable()
  {
    return INSTANCE;
  }
  
  public static <T, O> Function<O, List<T>> asFunction()
  {
    return INSTANCE;
  }
  
  public List<Object> apply(Object paramObject)
    throws Exception
  {
    return new ArrayList();
  }
  
  public List<Object> call()
    throws Exception
  {
    return new ArrayList();
  }
}
