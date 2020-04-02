package androidx.core.util;

import android.os.Build.VERSION;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat
{
  private ObjectsCompat() {}
  
  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Objects.equals(paramObject1, paramObject2);
    }
    boolean bool;
    if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static int hash(Object... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Objects.hash(paramVarArgs);
    }
    return Arrays.hashCode(paramVarArgs);
  }
  
  public static int hashCode(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
}
