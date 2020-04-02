package kotlin.reflect.jvm.internal.impl.protobuf;

final class Utf8
{
  private static int incompleteStateFor(int paramInt)
  {
    int i = paramInt;
    if (paramInt > -12) {
      i = -1;
    }
    return i;
  }
  
  private static int incompleteStateFor(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= -12) && (paramInt2 <= -65)) {
      paramInt1 ^= paramInt2 << 8;
    } else {
      paramInt1 = -1;
    }
    return paramInt1;
  }
  
  private static int incompleteStateFor(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 <= -12) && (paramInt2 <= -65) && (paramInt3 <= -65)) {
      paramInt1 = paramInt1 ^ paramInt2 << 8 ^ paramInt3 << 16;
    } else {
      paramInt1 = -1;
    }
    return paramInt1;
  }
  
  private static int incompleteStateFor(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[(paramInt1 - 1)];
    paramInt2 -= paramInt1;
    if (paramInt2 != 0)
    {
      if (paramInt2 != 1)
      {
        if (paramInt2 == 2) {
          return incompleteStateFor(i, paramArrayOfByte[paramInt1], paramArrayOfByte[(paramInt1 + 1)]);
        }
        throw new AssertionError();
      }
      return incompleteStateFor(i, paramArrayOfByte[paramInt1]);
    }
    return incompleteStateFor(i);
  }
  
  public static boolean isValidUtf8(byte[] paramArrayOfByte)
  {
    return isValidUtf8(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static boolean isValidUtf8(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    boolean bool;
    if (partialIsValidUtf8(paramArrayOfByte, paramInt1, paramInt2) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static int partialIsValidUtf8(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    int i = paramInt2;
    if (paramInt1 != 0)
    {
      if (paramInt2 >= paramInt3) {
        return paramInt1;
      }
      int j = (byte)paramInt1;
      if (j < -32) {
        if (j >= -62)
        {
          paramInt1 = paramInt2 + 1;
          if (paramArrayOfByte[paramInt2] > -65) {}
        }
      }
      int k;
      label150:
      do
      {
        do
        {
          i = paramInt1;
          break label284;
          return -1;
          if (j >= -16) {
            break label150;
          }
          k = (byte)(paramInt1 >> 8);
          paramInt1 = k;
          i = paramInt2;
          if (k == 0)
          {
            i = paramInt2 + 1;
            paramInt1 = paramArrayOfByte[paramInt2];
            if (i >= paramInt3) {
              return incompleteStateFor(j, paramInt1);
            }
          }
          if ((paramInt1 > -65) || ((j == -32) && (paramInt1 < -96)) || ((j == -19) && (paramInt1 >= -96))) {
            break;
          }
          paramInt1 = i + 1;
        } while (paramArrayOfByte[i] <= -65);
        return -1;
        i = (byte)(paramInt1 >> 8);
        k = 0;
        if (i == 0)
        {
          paramInt1 = paramInt2 + 1;
          i = paramArrayOfByte[paramInt2];
          if (paramInt1 >= paramInt3) {
            return incompleteStateFor(j, i);
          }
          paramInt2 = paramInt1;
          paramInt1 = k;
        }
        else
        {
          paramInt1 = (byte)(paramInt1 >> 16);
        }
        int m = paramInt1;
        k = paramInt2;
        if (paramInt1 == 0)
        {
          k = paramInt2 + 1;
          m = paramArrayOfByte[paramInt2];
          if (k >= paramInt3) {
            return incompleteStateFor(j, i, m);
          }
        }
        if ((i > -65) || ((j << 28) + (i + 112) >> 30 != 0) || (m > -65)) {
          break;
        }
        paramInt1 = k + 1;
      } while (paramArrayOfByte[k] <= -65);
      return -1;
    }
    label284:
    return partialIsValidUtf8(paramArrayOfByte, i, paramInt3);
  }
  
  public static int partialIsValidUtf8(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while ((paramInt1 < paramInt2) && (paramArrayOfByte[paramInt1] >= 0)) {
      paramInt1++;
    }
    if (paramInt1 >= paramInt2) {
      paramInt1 = 0;
    } else {
      paramInt1 = partialIsValidUtf8NonAscii(paramArrayOfByte, paramInt1, paramInt2);
    }
    return paramInt1;
  }
  
  private static int partialIsValidUtf8NonAscii(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    label207:
    for (;;)
    {
      if (paramInt1 >= paramInt2) {
        return 0;
      }
      int i = paramInt1 + 1;
      int j = paramArrayOfByte[paramInt1];
      paramInt1 = i;
      if (j < 0) {
        if (j < -32)
        {
          if (i >= paramInt2) {
            return j;
          }
          if (j >= -62)
          {
            paramInt1 = i + 1;
            if (paramArrayOfByte[i] <= -65) {}
          }
          else
          {
            return -1;
          }
        }
        else if (j < -16)
        {
          if (i >= paramInt2 - 1) {
            return incompleteStateFor(paramArrayOfByte, i, paramInt2);
          }
          int k = i + 1;
          paramInt1 = paramArrayOfByte[i];
          if ((paramInt1 <= -65) && ((j != -32) || (paramInt1 >= -96)) && ((j != -19) || (paramInt1 < -96)))
          {
            paramInt1 = k + 1;
            if (paramArrayOfByte[k] <= -65) {}
          }
          else
          {
            return -1;
          }
        }
        else
        {
          if (i >= paramInt2 - 2) {
            return incompleteStateFor(paramArrayOfByte, i, paramInt2);
          }
          paramInt1 = i + 1;
          i = paramArrayOfByte[i];
          if ((i <= -65) && ((j << 28) + (i + 112) >> 30 == 0))
          {
            i = paramInt1 + 1;
            if (paramArrayOfByte[paramInt1] <= -65)
            {
              paramInt1 = i + 1;
              if (paramArrayOfByte[i] <= -65) {
                break label207;
              }
            }
          }
          return -1;
        }
      }
    }
  }
}
