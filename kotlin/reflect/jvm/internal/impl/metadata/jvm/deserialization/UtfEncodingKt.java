package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;

public final class UtfEncodingKt
{
  public static final byte[] stringsToBytes(String[] paramArrayOfString)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "strings");
    int i = paramArrayOfString.length;
    int j = 0;
    int k = 0;
    int m = k;
    while (k < i)
    {
      m += paramArrayOfString[k].length();
      k++;
    }
    byte[] arrayOfByte = new byte[m];
    int n = paramArrayOfString.length;
    i = 0;
    int i2;
    for (k = i; i < n; k = i2)
    {
      String str = paramArrayOfString[i];
      int i1 = str.length() - 1;
      i2 = k;
      if (i1 >= 0)
      {
        int i3 = 0;
        for (i2 = k;; i2 = k)
        {
          k = i2 + 1;
          arrayOfByte[i2] = ((byte)(byte)str.charAt(i3));
          if (i3 == i1) {
            break;
          }
          i3++;
        }
        i2 = k;
      }
      i++;
    }
    i = j;
    if (k == m) {
      i = 1;
    }
    if ((_Assertions.ENABLED) && (i == 0)) {
      throw ((Throwable)new AssertionError("Should have reached the end"));
    }
    return arrayOfByte;
  }
}
