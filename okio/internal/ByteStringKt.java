package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;
import okio.ByteString;
import okio._Base64;
import okio._Platform;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000P\n\000\n\002\020\031\n\002\b\003\n\002\020\b\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\f\n\000\n\002\020\016\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\000\n\000\n\002\020\005\n\002\b\030\n\002\020\002\n\000\n\002\030\002\n\000\032\030\020\004\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\005H\002\032\021\020\t\032\0020\n2\006\020\013\032\0020\007H?\b\032\020\020\f\032\0020\0052\006\020\r\032\0020\016H\002\032\r\020\017\032\0020\020*\0020\nH?\b\032\r\020\021\032\0020\020*\0020\nH?\b\032\025\020\022\032\0020\005*\0020\n2\006\020\023\032\0020\nH?\b\032\017\020\024\032\004\030\0010\n*\0020\020H?\b\032\r\020\025\032\0020\n*\0020\020H?\b\032\r\020\026\032\0020\n*\0020\020H?\b\032\025\020\027\032\0020\030*\0020\n2\006\020\031\032\0020\007H?\b\032\025\020\027\032\0020\030*\0020\n2\006\020\031\032\0020\nH?\b\032\027\020\032\032\0020\030*\0020\n2\b\020\023\032\004\030\0010\033H?\b\032\025\020\034\032\0020\035*\0020\n2\006\020\036\032\0020\005H?\b\032\r\020\037\032\0020\005*\0020\nH?\b\032\r\020 \032\0020\005*\0020\nH?\b\032\r\020!\032\0020\020*\0020\nH?\b\032\035\020\"\032\0020\005*\0020\n2\006\020\023\032\0020\0072\006\020#\032\0020\005H?\b\032\r\020$\032\0020\007*\0020\nH?\b\032\035\020%\032\0020\005*\0020\n2\006\020\023\032\0020\0072\006\020#\032\0020\005H?\b\032\035\020%\032\0020\005*\0020\n2\006\020\023\032\0020\n2\006\020#\032\0020\005H?\b\032-\020&\032\0020\030*\0020\n2\006\020'\032\0020\0052\006\020\023\032\0020\0072\006\020(\032\0020\0052\006\020)\032\0020\005H?\b\032-\020&\032\0020\030*\0020\n2\006\020'\032\0020\0052\006\020\023\032\0020\n2\006\020(\032\0020\0052\006\020)\032\0020\005H?\b\032\025\020*\032\0020\030*\0020\n2\006\020+\032\0020\007H?\b\032\025\020*\032\0020\030*\0020\n2\006\020+\032\0020\nH?\b\032\035\020,\032\0020\n*\0020\n2\006\020-\032\0020\0052\006\020.\032\0020\005H?\b\032\r\020/\032\0020\n*\0020\nH?\b\032\r\0200\032\0020\n*\0020\nH?\b\032\r\0201\032\0020\007*\0020\nH?\b\032\035\0202\032\0020\n*\0020\0072\006\020'\032\0020\0052\006\020)\032\0020\005H?\b\032\r\0203\032\0020\020*\0020\nH?\b\032\r\0204\032\0020\020*\0020\nH?\b\032$\0205\032\00206*\0020\n2\006\0207\032\002082\006\020'\032\0020\0052\006\020)\032\0020\005H\000\"\024\020\000\032\0020\001X?\004?\006\b\n\000\032\004\b\002\020\003?\0069"}, d2={"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "offset", "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "", "buffer", "Lokio/Buffer;", "okio"}, k=2, mv={1, 1, 16})
public final class ByteStringKt
{
  private static final char[] HEX_DIGIT_CHARS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  
  private static final int codePointIndexToCharIndex(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length;
    int j = 0;
    int k = 0;
    int m = 0;
    if (j < i)
    {
      int n = paramArrayOfByte[j];
      int i1;
      label108:
      int i2;
      if (n >= 0)
      {
        i1 = m + 1;
        if (m == paramInt) {
          return k;
        }
        if ((n != 10) && (n != 13))
        {
          if (((n >= 0) && (31 >= n)) || ((127 <= n) && (159 >= n))) {
            m = 1;
          } else {
            m = 0;
          }
          if (m != 0) {}
        }
        else
        {
          if (n != 65533) {
            break label108;
          }
        }
        return -1;
        if (n < 65536) {
          m = 1;
        } else {
          m = 2;
        }
        k += m;
        n = j + 1;
        j = i1;
        i1 = k;
        for (;;)
        {
          i2 = j;
          j = n;
          k = i1;
          m = i2;
          if (n >= i) {
            break;
          }
          j = n;
          k = i1;
          m = i2;
          if (paramArrayOfByte[n] < 0) {
            break;
          }
          m = paramArrayOfByte[n];
          k = i2 + 1;
          if (i2 == paramInt) {
            return i1;
          }
          if ((m != 10) && (m != 13))
          {
            if (((m >= 0) && (31 >= m)) || ((127 <= m) && (159 >= m))) {
              j = 1;
            } else {
              j = 0;
            }
            if (j != 0) {}
          }
          else
          {
            if (m != 65533) {
              break label266;
            }
          }
          return -1;
          label266:
          if (m < 65536) {
            j = 1;
          } else {
            j = 2;
          }
          i1 += j;
          n++;
          j = k;
        }
      }
      if (n >> 5 == -2)
      {
        i1 = j + 1;
        if (i <= i1)
        {
          if (m == paramInt) {
            return k;
          }
          return -1;
        }
        n = paramArrayOfByte[j];
        i2 = paramArrayOfByte[i1];
        if ((i2 & 0xC0) == 128) {
          i1 = 1;
        } else {
          i1 = 0;
        }
        if (i1 == 0)
        {
          if (m == paramInt) {
            return k;
          }
          return -1;
        }
        n = i2 ^ 0xF80 ^ n << 6;
        if (n < 128)
        {
          if (m == paramInt) {
            return k;
          }
          return -1;
        }
        i1 = m + 1;
        if (m == paramInt) {
          return k;
        }
        if ((n != 10) && (n != 13))
        {
          if (((n >= 0) && (31 >= n)) || ((127 <= n) && (159 >= n))) {
            m = 1;
          } else {
            m = 0;
          }
          if (m != 0) {}
        }
        else
        {
          if (n != 65533) {
            break label489;
          }
        }
        return -1;
        label489:
        if (n < 65536) {
          m = 1;
        } else {
          m = 2;
        }
        k += m;
        j += 2;
        m = i1;
      }
      for (;;)
      {
        break;
        int i3;
        if (n >> 4 == -2)
        {
          i3 = j + 2;
          if (i <= i3)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          n = paramArrayOfByte[j];
          i2 = paramArrayOfByte[(j + 1)];
          if ((i2 & 0xC0) == 128) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i1 == 0)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i3 = paramArrayOfByte[i3];
          if ((i3 & 0xC0) == 128) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i1 == 0)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          n = i3 ^ 0xFFFE1F80 ^ i2 << 6 ^ n << 12;
          if (n < 2048)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          if ((55296 <= n) && (57343 >= n))
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i1 = m + 1;
          if (m == paramInt) {
            return k;
          }
          if ((n != 10) && (n != 13))
          {
            if (((n >= 0) && (31 >= n)) || ((127 <= n) && (159 >= n))) {
              m = 1;
            } else {
              m = 0;
            }
            if (m != 0) {}
          }
          else
          {
            if (n != 65533) {
              break label793;
            }
          }
          return -1;
          label793:
          if (n < 65536) {
            m = 1;
          } else {
            m = 2;
          }
          k += m;
          j += 3;
          m = i1;
        }
        else
        {
          if (n >> 3 != -2) {
            break label1197;
          }
          int i4 = j + 3;
          if (i <= i4)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i2 = paramArrayOfByte[j];
          n = paramArrayOfByte[(j + 1)];
          if ((n & 0xC0) == 128) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i1 == 0)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i3 = paramArrayOfByte[(j + 2)];
          if ((i3 & 0xC0) == 128) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i1 == 0)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i4 = paramArrayOfByte[i4];
          if ((i4 & 0xC0) == 128) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i1 == 0)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          n = i4 ^ 0x381F80 ^ i3 << 6 ^ n << 12 ^ i2 << 18;
          if (n > 1114111)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          if ((55296 <= n) && (57343 >= n))
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          if (n < 65536)
          {
            if (m == paramInt) {
              return k;
            }
            return -1;
          }
          i1 = m + 1;
          if (m == paramInt) {
            return k;
          }
          if ((n != 10) && (n != 13))
          {
            if (((n >= 0) && (31 >= n)) || ((127 <= n) && (159 >= n))) {
              m = 1;
            } else {
              m = 0;
            }
            if (m != 0) {}
          }
          else
          {
            if (n != 65533) {
              break label1164;
            }
          }
          return -1;
          label1164:
          if (n < 65536) {
            m = 1;
          } else {
            m = 2;
          }
          k += m;
          j += 4;
          m = i1;
        }
      }
      label1197:
      if (m == paramInt) {
        return k;
      }
      return -1;
    }
    return k;
  }
  
  public static final String commonBase64(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonBase64");
    return _Base64.encodeBase64$default(paramByteString.getData$okio(), null, 1, null);
  }
  
  public static final String commonBase64Url(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonBase64Url");
    return _Base64.encodeBase64(paramByteString.getData$okio(), _Base64.getBASE64_URL_SAFE());
  }
  
  public static final int commonCompareTo(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString1, "$this$commonCompareTo");
    Intrinsics.checkParameterIsNotNull(paramByteString2, "other");
    int i = paramByteString1.size();
    int j = paramByteString2.size();
    int k = Math.min(i, j);
    int n;
    int i1;
    int i2;
    for (int m = 0;; m++)
    {
      n = -1;
      if (m >= k) {
        break label95;
      }
      i1 = paramByteString1.getByte(m) & 0xFF;
      i2 = paramByteString2.getByte(m) & 0xFF;
      if (i1 != i2) {
        break;
      }
    }
    if (i1 >= i2) {
      n = 1;
    }
    return n;
    label95:
    if (i == j) {
      return 0;
    }
    if (i >= j) {
      n = 1;
    }
    return n;
  }
  
  public static final ByteString commonDecodeBase64(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$commonDecodeBase64");
    paramString = _Base64.decodeBase64ToArray(paramString);
    if (paramString != null) {
      paramString = new ByteString(paramString);
    } else {
      paramString = null;
    }
    return paramString;
  }
  
  public static final ByteString commonDecodeHex(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$commonDecodeHex");
    int i = paramString.length();
    int j = 0;
    if (i % 2 == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      int k = paramString.length() / 2;
      localObject = new byte[k];
      for (i = j; i < k; i++)
      {
        j = i * 2;
        localObject[i] = ((byte)(byte)((access$decodeHexDigit(paramString.charAt(j)) << 4) + access$decodeHexDigit(paramString.charAt(j + 1))));
      }
      return new ByteString((byte[])localObject);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected hex string: ");
    ((StringBuilder)localObject).append(paramString);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public static final ByteString commonEncodeUtf8(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$commonEncodeUtf8");
    ByteString localByteString = new ByteString(_Platform.asUtf8ToByteArray(paramString));
    localByteString.setUtf8$okio(paramString);
    return localByteString;
  }
  
  public static final boolean commonEndsWith(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString1, "$this$commonEndsWith");
    Intrinsics.checkParameterIsNotNull(paramByteString2, "suffix");
    return paramByteString1.rangeEquals(paramByteString1.size() - paramByteString2.size(), paramByteString2, 0, paramByteString2.size());
  }
  
  public static final boolean commonEndsWith(ByteString paramByteString, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonEndsWith");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "suffix");
    return paramByteString.rangeEquals(paramByteString.size() - paramArrayOfByte.length, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final boolean commonEquals(ByteString paramByteString, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonEquals");
    boolean bool = true;
    if (paramObject != paramByteString) {
      if ((paramObject instanceof ByteString))
      {
        paramObject = (ByteString)paramObject;
        if ((paramObject.size() == paramByteString.getData$okio().length) && (paramObject.rangeEquals(0, paramByteString.getData$okio(), 0, paramByteString.getData$okio().length))) {}
      }
      else
      {
        bool = false;
      }
    }
    return bool;
  }
  
  public static final byte commonGetByte(ByteString paramByteString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonGetByte");
    return paramByteString.getData$okio()[paramInt];
  }
  
  public static final int commonGetSize(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonGetSize");
    return paramByteString.getData$okio().length;
  }
  
  public static final int commonHashCode(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonHashCode");
    int i = paramByteString.getHashCode$okio();
    if (i != 0) {
      return i;
    }
    i = Arrays.hashCode(paramByteString.getData$okio());
    paramByteString.setHashCode$okio(i);
    return i;
  }
  
  public static final String commonHex(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonHex");
    char[] arrayOfChar = new char[paramByteString.getData$okio().length * 2];
    paramByteString = paramByteString.getData$okio();
    int i = paramByteString.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      int m = paramByteString[j];
      int n = k + 1;
      arrayOfChar[k] = ((char)getHEX_DIGIT_CHARS()[(m >> 4 & 0xF)]);
      k = n + 1;
      arrayOfChar[n] = ((char)getHEX_DIGIT_CHARS()[(m & 0xF)]);
      j++;
    }
    return new String(arrayOfChar);
  }
  
  public static final int commonIndexOf(ByteString paramByteString, byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonIndexOf");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "other");
    int i = paramByteString.getData$okio().length - paramArrayOfByte.length;
    paramInt = Math.max(paramInt, 0);
    if (paramInt <= i) {
      for (;;)
      {
        if (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
          return paramInt;
        }
        if (paramInt == i) {
          break;
        }
        paramInt++;
      }
    }
    return -1;
  }
  
  public static final byte[] commonInternalArray(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonInternalArray");
    return paramByteString.getData$okio();
  }
  
  public static final int commonLastIndexOf(ByteString paramByteString1, ByteString paramByteString2, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString1, "$this$commonLastIndexOf");
    Intrinsics.checkParameterIsNotNull(paramByteString2, "other");
    return paramByteString1.lastIndexOf(paramByteString2.internalArray$okio(), paramInt);
  }
  
  public static final int commonLastIndexOf(ByteString paramByteString, byte[] paramArrayOfByte, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonLastIndexOf");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "other");
    for (paramInt = Math.min(paramInt, paramByteString.getData$okio().length - paramArrayOfByte.length); paramInt >= 0; paramInt--) {
      if (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt, paramArrayOfByte, 0, paramArrayOfByte.length)) {
        return paramInt;
      }
    }
    return -1;
  }
  
  public static final ByteString commonOf(byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "data");
    paramArrayOfByte = Arrays.copyOf(paramArrayOfByte, paramArrayOfByte.length);
    Intrinsics.checkExpressionValueIsNotNull(paramArrayOfByte, "java.util.Arrays.copyOf(this, size)");
    return new ByteString(paramArrayOfByte);
  }
  
  public static final boolean commonRangeEquals(ByteString paramByteString1, int paramInt1, ByteString paramByteString2, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString1, "$this$commonRangeEquals");
    Intrinsics.checkParameterIsNotNull(paramByteString2, "other");
    return paramByteString2.rangeEquals(paramInt2, paramByteString1.getData$okio(), paramInt1, paramInt3);
  }
  
  public static final boolean commonRangeEquals(ByteString paramByteString, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonRangeEquals");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "other");
    boolean bool;
    if ((paramInt1 >= 0) && (paramInt1 <= paramByteString.getData$okio().length - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3) && (_Util.arrayRangeEquals(paramByteString.getData$okio(), paramInt1, paramArrayOfByte, paramInt2, paramInt3))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean commonStartsWith(ByteString paramByteString1, ByteString paramByteString2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString1, "$this$commonStartsWith");
    Intrinsics.checkParameterIsNotNull(paramByteString2, "prefix");
    return paramByteString1.rangeEquals(0, paramByteString2, 0, paramByteString2.size());
  }
  
  public static final boolean commonStartsWith(ByteString paramByteString, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonStartsWith");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "prefix");
    return paramByteString.rangeEquals(0, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final ByteString commonSubstring(ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonSubstring");
    int i = 1;
    int j;
    if (paramInt1 >= 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramInt2 <= paramByteString.getData$okio().length) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (paramInt2 - paramInt1 >= 0) {
          j = i;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == paramByteString.getData$okio().length)) {
            return paramByteString;
          }
          return new ByteString(ArraysKt.copyOfRange(paramByteString.getData$okio(), paramInt1, paramInt2));
        }
        throw ((Throwable)new IllegalArgumentException("endIndex < beginIndex".toString()));
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("endIndex > length(");
      localStringBuilder.append(paramByteString.getData$okio().length);
      localStringBuilder.append(')');
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
    }
    throw ((Throwable)new IllegalArgumentException("beginIndex < 0".toString()));
  }
  
  public static final ByteString commonToAsciiLowercase(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonToAsciiLowercase");
    for (int i = 0; i < paramByteString.getData$okio().length; i++)
    {
      int j = paramByteString.getData$okio()[i];
      int k = (byte)65;
      if (j >= k)
      {
        int m = (byte)90;
        if (j <= m)
        {
          paramByteString = paramByteString.getData$okio();
          paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
          Intrinsics.checkExpressionValueIsNotNull(paramByteString, "java.util.Arrays.copyOf(this, size)");
          int n = i + 1;
          paramByteString[i] = ((byte)(byte)(j + 32));
          for (i = n; i < paramByteString.length; i++)
          {
            n = paramByteString[i];
            if ((n >= k) && (n <= m)) {
              paramByteString[i] = ((byte)(byte)(n + 32));
            }
          }
          return new ByteString(paramByteString);
        }
      }
    }
    return paramByteString;
  }
  
  public static final ByteString commonToAsciiUppercase(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonToAsciiUppercase");
    for (int i = 0; i < paramByteString.getData$okio().length; i++)
    {
      int j = paramByteString.getData$okio()[i];
      int k = (byte)97;
      if (j >= k)
      {
        int m = (byte)122;
        if (j <= m)
        {
          paramByteString = paramByteString.getData$okio();
          paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
          Intrinsics.checkExpressionValueIsNotNull(paramByteString, "java.util.Arrays.copyOf(this, size)");
          int n = i + 1;
          paramByteString[i] = ((byte)(byte)(j - 32));
          for (i = n; i < paramByteString.length; i++)
          {
            n = paramByteString[i];
            if ((n >= k) && (n <= m)) {
              paramByteString[i] = ((byte)(byte)(n - 32));
            }
          }
          return new ByteString(paramByteString);
        }
      }
    }
    return paramByteString;
  }
  
  public static final byte[] commonToByteArray(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonToByteArray");
    paramByteString = paramByteString.getData$okio();
    paramByteString = Arrays.copyOf(paramByteString, paramByteString.length);
    Intrinsics.checkExpressionValueIsNotNull(paramByteString, "java.util.Arrays.copyOf(this, size)");
    return paramByteString;
  }
  
  public static final ByteString commonToByteString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "$this$commonToByteString");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    return new ByteString(ArraysKt.copyOfRange(paramArrayOfByte, paramInt1, paramInt2 + paramInt1));
  }
  
  public static final String commonToString(ByteString paramByteString)
  {
    Object localObject1 = paramByteString;
    Intrinsics.checkParameterIsNotNull(localObject1, "$this$commonToString");
    int i = paramByteString.getData$okio().length;
    int j = 1;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return "[size=0]";
    }
    i = access$codePointIndexToCharIndex(paramByteString.getData$okio(), 64);
    if (i == -1)
    {
      if (paramByteString.getData$okio().length <= 64)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("[hex=");
        ((StringBuilder)localObject1).append(paramByteString.hex());
        ((StringBuilder)localObject1).append(']');
        paramByteString = ((StringBuilder)localObject1).toString();
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[size=");
        ((StringBuilder)localObject2).append(paramByteString.getData$okio().length);
        ((StringBuilder)localObject2).append(" hex=");
        if (64 <= paramByteString.getData$okio().length) {
          i = j;
        } else {
          i = 0;
        }
        if (i == 0) {
          break label218;
        }
        if (64 != paramByteString.getData$okio().length) {
          localObject1 = new ByteString(ArraysKt.copyOfRange(paramByteString.getData$okio(), 0, 64));
        }
        ((StringBuilder)localObject2).append(((ByteString)localObject1).hex());
        ((StringBuilder)localObject2).append("…]");
        paramByteString = ((StringBuilder)localObject2).toString();
      }
      return paramByteString;
      label218:
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("endIndex > length(");
      ((StringBuilder)localObject1).append(paramByteString.getData$okio().length);
      ((StringBuilder)localObject1).append(')');
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString().toString()));
    }
    Object localObject2 = paramByteString.utf8();
    if (localObject2 != null)
    {
      localObject1 = ((String)localObject2).substring(0, i);
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      localObject1 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default((String)localObject1, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null), "\r", "\\r", false, 4, null);
      if (i < ((String)localObject2).length())
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[size=");
        ((StringBuilder)localObject2).append(paramByteString.getData$okio().length);
        ((StringBuilder)localObject2).append(" text=");
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("…]");
        paramByteString = ((StringBuilder)localObject2).toString();
      }
      else
      {
        paramByteString = new StringBuilder();
        paramByteString.append("[text=");
        paramByteString.append((String)localObject1);
        paramByteString.append(']');
        paramByteString = paramByteString.toString();
      }
      return paramByteString;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public static final String commonUtf8(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonUtf8");
    String str1 = paramByteString.getUtf8$okio();
    String str2 = str1;
    if (str1 == null)
    {
      str2 = _Platform.toUtf8String(paramByteString.internalArray$okio());
      paramByteString.setUtf8$okio(str2);
    }
    return str2;
  }
  
  public static final void commonWrite(ByteString paramByteString, Buffer paramBuffer, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramBuffer, "buffer");
    paramBuffer.write(paramByteString.getData$okio(), paramInt1, paramInt2);
  }
  
  private static final int decodeHexDigit(char paramChar)
  {
    char c;
    if (('0' <= paramChar) && ('9' >= paramChar))
    {
      c = paramChar - '0';
    }
    else
    {
      c = 'a';
      if (('a' <= paramChar) && ('f' >= paramChar)) {}
      for (;;)
      {
        c = paramChar - c + 10;
        break;
        c = 'A';
        if (('A' > paramChar) || ('F' < paramChar)) {
          break label71;
        }
      }
    }
    return c;
    label71:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected hex digit: ");
    localStringBuilder.append(paramChar);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
  }
  
  public static final char[] getHEX_DIGIT_CHARS()
  {
    return HEX_DIGIT_CHARS;
  }
}
