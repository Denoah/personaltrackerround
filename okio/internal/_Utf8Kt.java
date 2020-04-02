package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\020\022\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\002\032\n\020\000\032\0020\001*\0020\002\032\036\020\003\032\0020\002*\0020\0012\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\005?\006\007"}, d2={"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k=2, mv={1, 1, 16})
public final class _Utf8Kt
{
  public static final byte[] commonAsUtf8ToByteArray(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$commonAsUtf8ToByteArray");
    byte[] arrayOfByte = new byte[paramString.length() * 4];
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      int k = paramString.charAt(j);
      if (k >= 128)
      {
        int m = paramString.length();
        k = j;
        i = j;
        while (i < m)
        {
          j = paramString.charAt(i);
          int n;
          if (j < 128)
          {
            n = (byte)j;
            j = k + 1;
            arrayOfByte[k] = ((byte)n);
            i++;
            while ((i < m) && (paramString.charAt(i) < '?'))
            {
              arrayOfByte[j] = ((byte)(byte)paramString.charAt(i));
              i++;
              j++;
            }
            k = j;
          }
          else
          {
            int i1;
            if (j < 2048)
            {
              i1 = (byte)(j >> 6 | 0xC0);
              n = k + 1;
              arrayOfByte[k] = ((byte)i1);
              k = (byte)(j & 0x3F | 0x80);
              j = n + 1;
              arrayOfByte[n] = ((byte)k);
            }
            for (;;)
            {
              i++;
              for (;;)
              {
                k = j;
                break;
                if ((55296 > j) || (57343 < j)) {
                  break label391;
                }
                if (j > 56319) {
                  break label376;
                }
                n = i + 1;
                if (m <= n) {
                  break label376;
                }
                i1 = paramString.charAt(n);
                if ((56320 > i1) || (57343 < i1)) {
                  break label376;
                }
                j = (j << 10) + paramString.charAt(n) - 56613888;
                i1 = (byte)(j >> 18 | 0xF0);
                n = k + 1;
                arrayOfByte[k] = ((byte)i1);
                k = (byte)(j >> 12 & 0x3F | 0x80);
                i1 = n + 1;
                arrayOfByte[n] = ((byte)k);
                n = (byte)(j >> 6 & 0x3F | 0x80);
                k = i1 + 1;
                arrayOfByte[i1] = ((byte)n);
                n = (byte)(j & 0x3F | 0x80);
                j = k + 1;
                arrayOfByte[k] = ((byte)n);
                i += 2;
              }
              label376:
              j = k + 1;
              arrayOfByte[k] = ((byte)63);
              continue;
              label391:
              i1 = (byte)(j >> 12 | 0xE0);
              n = k + 1;
              arrayOfByte[k] = ((byte)i1);
              i1 = (byte)(j >> 6 & 0x3F | 0x80);
              k = n + 1;
              arrayOfByte[n] = ((byte)i1);
              n = (byte)(j & 0x3F | 0x80);
              j = k + 1;
              arrayOfByte[k] = ((byte)n);
            }
          }
        }
        paramString = Arrays.copyOf(arrayOfByte, k);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "java.util.Arrays.copyOf(this, newSize)");
        return paramString;
      }
      arrayOfByte[j] = ((byte)(byte)k);
    }
    paramString = Arrays.copyOf(arrayOfByte, paramString.length());
    Intrinsics.checkExpressionValueIsNotNull(paramString, "java.util.Arrays.copyOf(this, newSize)");
    return paramString;
  }
  
  public static final String commonToUtf8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "$this$commonToUtf8String");
    if ((i >= 0) && (paramInt2 <= paramArrayOfByte.length) && (i <= paramInt2))
    {
      localObject = new char[paramInt2 - i];
      paramInt1 = 0;
      while (i < paramInt2)
      {
        int j = paramArrayOfByte[i];
        int k;
        if (j >= 0)
        {
          k = (char)j;
          j = paramInt1 + 1;
          localObject[paramInt1] = ((char)k);
          i++;
          paramInt1 = j;
          j = i;
          for (;;)
          {
            i = j;
            k = paramInt1;
            if (j >= paramInt2) {
              break;
            }
            i = j;
            k = paramInt1;
            if (paramArrayOfByte[j] < 0) {
              break;
            }
            localObject[paramInt1] = ((char)(char)paramArrayOfByte[j]);
            j++;
            paramInt1++;
          }
          paramInt1 = k;
        }
        else
        {
          label163:
          int m;
          if (j >> 5 == -2)
          {
            j = i + 1;
            if (paramInt2 <= j)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
            }
            for (paramInt1 = j;; paramInt1 = j)
            {
              j = 1;
              break label297;
              k = paramArrayOfByte[i];
              m = paramArrayOfByte[j];
              if ((m & 0xC0) == 128) {
                j = 1;
              } else {
                j = 0;
              }
              if (j != 0) {
                break;
              }
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
            }
            j = m ^ 0xF80 ^ k << 6;
            if (j < 128)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
            }
            else
            {
              k = (char)j;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
            }
          }
          label294:
          label297:
          int n;
          for (j = 2;; j = 3)
          {
            i += j;
            k = paramInt1;
            break;
            if (j >> 4 != -2) {
              break label628;
            }
            n = i + 2;
            if (paramInt2 <= n)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              k = i + 1;
              paramInt1 = j;
              if (paramInt2 <= k) {
                break label163;
              }
              if ((paramArrayOfByte[k] & 0xC0) == 128) {
                k = 1;
              } else {
                k = 0;
              }
              paramInt1 = j;
              if (k != 0) {
                break label294;
              }
              paramInt1 = j;
              break label163;
            }
            k = paramArrayOfByte[i];
            m = paramArrayOfByte[(i + 1)];
            if ((m & 0xC0) == 128) {
              j = 1;
            } else {
              j = 0;
            }
            if (j == 0)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
              break label163;
            }
            n = paramArrayOfByte[n];
            if ((n & 0xC0) == 128) {
              j = 1;
            } else {
              j = 0;
            }
            if (j == 0)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
              break label294;
            }
            j = n ^ 0xFFFE1F80 ^ m << 6 ^ k << 12;
            if (j < 2048)
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
            }
            else if ((55296 <= j) && (57343 >= j))
            {
              k = (char)65533;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
            }
            else
            {
              k = (char)j;
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              paramInt1 = j;
            }
          }
          label628:
          if (j >> 3 == -2)
          {
            int i1 = i + 3;
            if (paramInt2 <= i1)
            {
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              k = i + 1;
              paramInt1 = j;
              if (paramInt2 > k)
              {
                if ((paramArrayOfByte[k] & 0xC0) == 128) {
                  paramInt1 = 1;
                } else {
                  paramInt1 = 0;
                }
                if (paramInt1 == 0)
                {
                  paramInt1 = j;
                }
                else
                {
                  k = i + 2;
                  paramInt1 = j;
                  if (paramInt2 > k)
                  {
                    if ((paramArrayOfByte[k] & 0xC0) == 128) {
                      k = 1;
                    } else {
                      k = 0;
                    }
                    paramInt1 = j;
                    if (k == 0) {
                      paramInt1 = j;
                    }
                  }
                }
              }
            }
            for (;;)
            {
              j = 3;
              break label1117;
              for (;;)
              {
                j = 2;
                break label1117;
                for (;;)
                {
                  j = 1;
                  break label1117;
                  m = paramArrayOfByte[i];
                  k = paramArrayOfByte[(i + 1)];
                  if ((k & 0xC0) == 128) {
                    j = 1;
                  } else {
                    j = 0;
                  }
                  if (j != 0) {
                    break;
                  }
                  j = paramInt1 + 1;
                  localObject[paramInt1] = ((char)65533);
                  paramInt1 = j;
                }
                n = paramArrayOfByte[(i + 2)];
                if ((n & 0xC0) == 128) {
                  j = 1;
                } else {
                  j = 0;
                }
                if (j != 0) {
                  break;
                }
                j = paramInt1 + 1;
                localObject[paramInt1] = ((char)65533);
                paramInt1 = j;
              }
              i1 = paramArrayOfByte[i1];
              if ((i1 & 0xC0) == 128) {
                j = 1;
              } else {
                j = 0;
              }
              if (j != 0) {
                break;
              }
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              paramInt1 = j;
            }
            m = i1 ^ 0x381F80 ^ n << 6 ^ k << 12 ^ m << 18;
            if (m > 1114111)
            {
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              paramInt1 = j;
            }
            else if ((55296 <= m) && (57343 >= m))
            {
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              paramInt1 = j;
            }
            else if (m < 65536)
            {
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              paramInt1 = j;
            }
            else if (m != 65533)
            {
              k = (char)((m >>> 10) + 55232);
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)k);
              k = (char)((m & 0x3FF) + 56320);
              paramInt1 = j + 1;
              localObject[j] = ((char)k);
            }
            else
            {
              j = paramInt1 + 1;
              localObject[paramInt1] = ((char)65533);
              paramInt1 = j;
            }
            j = 4;
            label1117:
            i += j;
          }
          else
          {
            j = paramInt1 + 1;
            localObject[paramInt1] = ((char)65533);
            i++;
            paramInt1 = j;
          }
        }
      }
      return new String((char[])localObject, 0, paramInt1);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size=");
    ((StringBuilder)localObject).append(paramArrayOfByte.length);
    ((StringBuilder)localObject).append(" beginIndex=");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).append(" endIndex=");
    ((StringBuilder)localObject).append(paramInt2);
    throw ((Throwable)new ArrayIndexOutOfBoundsException(((StringBuilder)localObject).toString()));
  }
}
