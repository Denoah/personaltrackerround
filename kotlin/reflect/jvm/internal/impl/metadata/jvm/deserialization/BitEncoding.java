package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

public class BitEncoding
{
  private static final boolean FORCE_8TO7_ENCODING;
  
  static
  {
    Object localObject;
    try
    {
      String str = System.getProperty("kotlin.jvm.serialization.use8to7");
    }
    catch (SecurityException localSecurityException)
    {
      localObject = null;
    }
    FORCE_8TO7_ENCODING = "true".equals(localObject);
  }
  
  private BitEncoding() {}
  
  private static void addModuloByte(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null) {
      $$$reportNull$$$0(4);
    }
    int i = 0;
    int j = paramArrayOfByte.length;
    while (i < j)
    {
      paramArrayOfByte[i] = ((byte)(byte)(paramArrayOfByte[i] + paramInt & 0x7F));
      i++;
    }
  }
  
  private static byte[] combineStringArrayIntoBytes(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      $$$reportNull$$$0(11);
    }
    int i = paramArrayOfString.length;
    int j = 0;
    int k = j;
    while (j < i)
    {
      k += paramArrayOfString[j].length();
      j++;
    }
    byte[] arrayOfByte = new byte[k];
    int m = paramArrayOfString.length;
    k = 0;
    j = k;
    while (k < m)
    {
      String str = paramArrayOfString[k];
      int n = str.length();
      i = 0;
      while (i < n)
      {
        arrayOfByte[j] = ((byte)(byte)str.charAt(i));
        i++;
        j++;
      }
      k++;
    }
    return arrayOfByte;
  }
  
  private static byte[] decode7to8(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      $$$reportNull$$$0(13);
    }
    int i = paramArrayOfByte.length * 7 / 8;
    byte[] arrayOfByte = new byte[i];
    int j = 0;
    int k = j;
    int m = k;
    int n = k;
    while (j < i)
    {
      int i1 = paramArrayOfByte[n];
      n++;
      int i2 = paramArrayOfByte[n];
      k = m + 1;
      arrayOfByte[j] = ((byte)(byte)(((i1 & 0xFF) >>> m) + ((i2 & (1 << k) - 1) << 7 - m)));
      if (m == 6)
      {
        m = n + 1;
        k = 0;
      }
      else
      {
        m = n;
      }
      j++;
      n = m;
      m = k;
    }
    return arrayOfByte;
  }
  
  public static byte[] decodeBytes(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      $$$reportNull$$$0(7);
    }
    String[] arrayOfString = paramArrayOfString;
    if (paramArrayOfString.length > 0)
    {
      arrayOfString = paramArrayOfString;
      if (!paramArrayOfString[0].isEmpty())
      {
        int i = paramArrayOfString[0].charAt(0);
        if (i == 0)
        {
          paramArrayOfString = UtfEncodingKt.stringsToBytes(dropMarker(paramArrayOfString));
          if (paramArrayOfString == null) {
            $$$reportNull$$$0(8);
          }
          return paramArrayOfString;
        }
        arrayOfString = paramArrayOfString;
        if (i == 65535) {
          arrayOfString = dropMarker(paramArrayOfString);
        }
      }
    }
    paramArrayOfString = combineStringArrayIntoBytes(arrayOfString);
    addModuloByte(paramArrayOfString, 127);
    return decode7to8(paramArrayOfString);
  }
  
  private static String[] dropMarker(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {
      $$$reportNull$$$0(9);
    }
    paramArrayOfString = (String[])paramArrayOfString.clone();
    paramArrayOfString[0] = paramArrayOfString[0].substring(1);
    if (paramArrayOfString == null) {
      $$$reportNull$$$0(10);
    }
    return paramArrayOfString;
  }
}
