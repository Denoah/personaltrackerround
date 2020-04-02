package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Internal
{
  public static final byte[] EMPTY_BYTE_ARRAY;
  public static final ByteBuffer EMPTY_BYTE_BUFFER;
  
  static
  {
    byte[] arrayOfByte = new byte[0];
    EMPTY_BYTE_ARRAY = arrayOfByte;
    EMPTY_BYTE_BUFFER = ByteBuffer.wrap(arrayOfByte);
  }
  
  public static boolean isValidUtf8(byte[] paramArrayOfByte)
  {
    return Utf8.isValidUtf8(paramArrayOfByte);
  }
  
  public static String toStringUtf8(byte[] paramArrayOfByte)
  {
    try
    {
      paramArrayOfByte = new String(paramArrayOfByte, "UTF-8");
      return paramArrayOfByte;
    }
    catch (UnsupportedEncodingException paramArrayOfByte)
    {
      throw new RuntimeException("UTF-8 not supported?", paramArrayOfByte);
    }
  }
  
  public static abstract interface EnumLite
  {
    public abstract int getNumber();
  }
  
  public static abstract interface EnumLiteMap<T extends Internal.EnumLite>
  {
    public abstract T findValueByNumber(int paramInt);
  }
}
