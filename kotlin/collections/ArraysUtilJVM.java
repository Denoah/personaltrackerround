package kotlin.collections;

import java.util.Arrays;
import java.util.List;

class ArraysUtilJVM
{
  ArraysUtilJVM() {}
  
  static <T> List<T> asList(T[] paramArrayOfT)
  {
    return Arrays.asList(paramArrayOfT);
  }
}
