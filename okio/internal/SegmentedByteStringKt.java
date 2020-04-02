package okio.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000R\n\000\n\002\020\b\n\002\020\025\n\002\b\004\n\002\020\013\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\005\n\002\b\003\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\004\032$\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\0012\006\020\004\032\0020\0012\006\020\005\032\0020\001H\000\032\027\020\006\032\0020\007*\0020\b2\b\020\t\032\004\030\0010\nH?\b\032\r\020\013\032\0020\001*\0020\bH?\b\032\r\020\f\032\0020\001*\0020\bH?\b\032\025\020\r\032\0020\016*\0020\b2\006\020\017\032\0020\001H?\b\032-\020\020\032\0020\007*\0020\b2\006\020\021\032\0020\0012\006\020\t\032\0020\0222\006\020\023\032\0020\0012\006\020\024\032\0020\001H?\b\032-\020\020\032\0020\007*\0020\b2\006\020\021\032\0020\0012\006\020\t\032\0020\0252\006\020\023\032\0020\0012\006\020\024\032\0020\001H?\b\032\035\020\026\032\0020\025*\0020\b2\006\020\027\032\0020\0012\006\020\030\032\0020\001H?\b\032\r\020\031\032\0020\022*\0020\bH?\b\032%\020\032\032\0020\033*\0020\b2\006\020\034\032\0020\0352\006\020\021\032\0020\0012\006\020\024\032\0020\001H?\b\032Z\020\036\032\0020\033*\0020\b2K\020\037\032G\022\023\022\0210\022?\006\f\b!\022\b\b\"\022\004\b\b(#\022\023\022\0210\001?\006\f\b!\022\b\b\"\022\004\b\b(\021\022\023\022\0210\001?\006\f\b!\022\b\b\"\022\004\b\b(\024\022\004\022\0020\0330 H?\b\032j\020\036\032\0020\033*\0020\b2\006\020\027\032\0020\0012\006\020\030\032\0020\0012K\020\037\032G\022\023\022\0210\022?\006\f\b!\022\b\b\"\022\004\b\b(#\022\023\022\0210\001?\006\f\b!\022\b\b\"\022\004\b\b(\021\022\023\022\0210\001?\006\f\b!\022\b\b\"\022\004\b\b(\024\022\004\022\0020\0330 H?\b\032\024\020$\032\0020\001*\0020\b2\006\020\017\032\0020\001H\000?\006%"}, d2={"binarySearch", "", "", "value", "fromIndex", "toIndex", "commonEquals", "", "Lokio/SegmentedByteString;", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "offset", "", "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k=2, mv={1, 1, 16})
public final class SegmentedByteStringKt
{
  public static final int binarySearch(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfInt, "$this$binarySearch");
    paramInt3--;
    while (paramInt2 <= paramInt3)
    {
      int i = paramInt2 + paramInt3 >>> 1;
      int j = paramArrayOfInt[i];
      if (j < paramInt1) {
        paramInt2 = i + 1;
      } else if (j > paramInt1) {
        paramInt3 = i - 1;
      } else {
        return i;
      }
    }
    return -paramInt2 - 1;
  }
  
  public static final boolean commonEquals(SegmentedByteString paramSegmentedByteString, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonEquals");
    boolean bool = true;
    if (paramObject != paramSegmentedByteString) {
      if ((paramObject instanceof ByteString))
      {
        paramObject = (ByteString)paramObject;
        if ((paramObject.size() == paramSegmentedByteString.size()) && (paramSegmentedByteString.rangeEquals(0, paramObject, 0, paramSegmentedByteString.size()))) {}
      }
      else
      {
        bool = false;
      }
    }
    return bool;
  }
  
  public static final int commonGetSize(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonGetSize");
    return paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length - 1)];
  }
  
  public static final int commonHashCode(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonHashCode");
    int i = paramSegmentedByteString.getHashCode$okio();
    if (i != 0) {
      return i;
    }
    int j = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int k = 0;
    int m = 1;
    int i2;
    for (int n = 0; k < j; n = i2)
    {
      int i1 = paramSegmentedByteString.getDirectory$okio()[(j + k)];
      i2 = paramSegmentedByteString.getDirectory$okio()[k];
      byte[] arrayOfByte = paramSegmentedByteString.getSegments$okio()[k];
      for (i = i1; i < i2 - n + i1; i++) {
        m = m * 31 + arrayOfByte[i];
      }
      k++;
    }
    paramSegmentedByteString.setHashCode$okio(m);
    return m;
  }
  
  public static final byte commonInternalGet(SegmentedByteString paramSegmentedByteString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonInternalGet");
    _Util.checkOffsetAndCount(paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length - 1)], paramInt, 1L);
    int i = segment(paramSegmentedByteString, paramInt);
    int j;
    if (i == 0) {
      j = 0;
    } else {
      j = paramSegmentedByteString.getDirectory$okio()[(i - 1)];
    }
    int k = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + i)];
    return paramSegmentedByteString.getSegments$okio()[i][(paramInt - j + k)];
  }
  
  public static final boolean commonRangeEquals(SegmentedByteString paramSegmentedByteString, int paramInt1, ByteString paramByteString, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonRangeEquals");
    Intrinsics.checkParameterIsNotNull(paramByteString, "other");
    if ((paramInt1 >= 0) && (paramInt1 <= paramSegmentedByteString.size() - paramInt3))
    {
      int i = paramInt3 + paramInt1;
      int j = segment(paramSegmentedByteString, paramInt1);
      paramInt3 = paramInt2;
      paramInt2 = paramInt1;
      for (paramInt1 = j; paramInt2 < i; paramInt1++)
      {
        if (paramInt1 == 0) {
          j = 0;
        } else {
          j = paramSegmentedByteString.getDirectory$okio()[(paramInt1 - 1)];
        }
        int k = paramSegmentedByteString.getDirectory$okio()[paramInt1];
        int m = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt1)];
        k = Math.min(i, k - j + j) - paramInt2;
        if (!paramByteString.rangeEquals(paramInt3, paramSegmentedByteString.getSegments$okio()[paramInt1], m + (paramInt2 - j), k)) {
          return false;
        }
        paramInt3 += k;
        paramInt2 += k;
      }
      return true;
    }
    return false;
  }
  
  public static final boolean commonRangeEquals(SegmentedByteString paramSegmentedByteString, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonRangeEquals");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "other");
    if ((paramInt1 >= 0) && (paramInt1 <= paramSegmentedByteString.size() - paramInt3) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt3))
    {
      int i = paramInt3 + paramInt1;
      for (paramInt3 = segment(paramSegmentedByteString, paramInt1); paramInt1 < i; paramInt3++)
      {
        int j;
        if (paramInt3 == 0) {
          j = 0;
        } else {
          j = paramSegmentedByteString.getDirectory$okio()[(paramInt3 - 1)];
        }
        int k = paramSegmentedByteString.getDirectory$okio()[paramInt3];
        int m = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt3)];
        k = Math.min(i, k - j + j) - paramInt1;
        if (!_Util.arrayRangeEquals(paramSegmentedByteString.getSegments$okio()[paramInt3], m + (paramInt1 - j), paramArrayOfByte, paramInt2, k)) {
          return false;
        }
        paramInt2 += k;
        paramInt1 += k;
      }
      return true;
    }
    return false;
  }
  
  public static final ByteString commonSubstring(SegmentedByteString paramSegmentedByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonSubstring");
    int i = 0;
    int j;
    if (paramInt1 >= 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramInt2 <= paramSegmentedByteString.size()) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        int k = paramInt2 - paramInt1;
        if (k >= 0) {
          j = 1;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          if ((paramInt1 == 0) && (paramInt2 == paramSegmentedByteString.size())) {
            return (ByteString)paramSegmentedByteString;
          }
          if (paramInt1 == paramInt2) {
            return ByteString.EMPTY;
          }
          int m = segment(paramSegmentedByteString, paramInt1);
          int n = segment(paramSegmentedByteString, paramInt2 - 1);
          byte[][] arrayOfByte = (byte[][])ArraysKt.copyOfRange((Object[])paramSegmentedByteString.getSegments$okio(), m, n + 1);
          Object[] arrayOfObject = (Object[])arrayOfByte;
          localObject = new int[arrayOfObject.length * 2];
          if (m <= n)
          {
            j = 0;
            paramInt2 = m;
            for (;;)
            {
              localObject[j] = Math.min(paramSegmentedByteString.getDirectory$okio()[paramInt2] - paramInt1, k);
              localObject[(j + arrayOfObject.length)] = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt2)];
              if (paramInt2 == n) {
                break;
              }
              paramInt2++;
              j++;
            }
          }
          if (m == 0) {
            paramInt2 = i;
          } else {
            paramInt2 = paramSegmentedByteString.getDirectory$okio()[(m - 1)];
          }
          j = arrayOfObject.length;
          localObject[j] += paramInt1 - paramInt2;
          return (ByteString)new SegmentedByteString(arrayOfByte, (int[])localObject);
        }
        paramSegmentedByteString = new StringBuilder();
        paramSegmentedByteString.append("endIndex=");
        paramSegmentedByteString.append(paramInt2);
        paramSegmentedByteString.append(" < beginIndex=");
        paramSegmentedByteString.append(paramInt1);
        throw ((Throwable)new IllegalArgumentException(paramSegmentedByteString.toString().toString()));
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("endIndex=");
      ((StringBuilder)localObject).append(paramInt2);
      ((StringBuilder)localObject).append(" > length(");
      ((StringBuilder)localObject).append(paramSegmentedByteString.size());
      ((StringBuilder)localObject).append(')');
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
    }
    paramSegmentedByteString = new StringBuilder();
    paramSegmentedByteString.append("beginIndex=");
    paramSegmentedByteString.append(paramInt1);
    paramSegmentedByteString.append(" < 0");
    throw ((Throwable)new IllegalArgumentException(paramSegmentedByteString.toString().toString()));
  }
  
  public static final byte[] commonToByteArray(SegmentedByteString paramSegmentedByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonToByteArray");
    byte[] arrayOfByte1 = new byte[paramSegmentedByteString.size()];
    int i = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int j = 0;
    int k = 0;
    int m = k;
    while (j < i)
    {
      int n = paramSegmentedByteString.getDirectory$okio()[(i + j)];
      int i1 = paramSegmentedByteString.getDirectory$okio()[j];
      byte[] arrayOfByte2 = paramSegmentedByteString.getSegments$okio()[j];
      k = i1 - k;
      ArraysKt.copyInto(arrayOfByte2, arrayOfByte1, m, n, n + k);
      m += k;
      j++;
      k = i1;
    }
    return arrayOfByte1;
  }
  
  public static final void commonWrite(SegmentedByteString paramSegmentedByteString, Buffer paramBuffer, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramBuffer, "buffer");
    int i = paramInt2 + paramInt1;
    for (paramInt2 = segment(paramSegmentedByteString, paramInt1); paramInt1 < i; paramInt2++)
    {
      if (paramInt2 == 0) {
        j = 0;
      } else {
        j = paramSegmentedByteString.getDirectory$okio()[(paramInt2 - 1)];
      }
      int k = paramSegmentedByteString.getDirectory$okio()[paramInt2];
      int m = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + paramInt2)];
      k = Math.min(i, k - j + j) - paramInt1;
      int j = m + (paramInt1 - j);
      Segment localSegment1 = new Segment(paramSegmentedByteString.getSegments$okio()[paramInt2], j, j + k, true, false);
      if (paramBuffer.head == null)
      {
        localSegment1.prev = localSegment1;
        localSegment1.next = localSegment1.prev;
        paramBuffer.head = localSegment1.next;
      }
      else
      {
        Segment localSegment2 = paramBuffer.head;
        if (localSegment2 == null) {
          Intrinsics.throwNpe();
        }
        localSegment2 = localSegment2.prev;
        if (localSegment2 == null) {
          Intrinsics.throwNpe();
        }
        localSegment2.push(localSegment1);
      }
      paramInt1 += k;
    }
    paramBuffer.setSize$okio(paramBuffer.size() + paramSegmentedByteString.size());
  }
  
  private static final void forEachSegment(SegmentedByteString paramSegmentedByteString, int paramInt1, int paramInt2, Function3<? super byte[], ? super Integer, ? super Integer, Unit> paramFunction3)
  {
    for (int i = segment(paramSegmentedByteString, paramInt1); paramInt1 < paramInt2; i++)
    {
      int j;
      if (i == 0) {
        j = 0;
      } else {
        j = paramSegmentedByteString.getDirectory$okio()[(i - 1)];
      }
      int k = paramSegmentedByteString.getDirectory$okio()[i];
      int m = paramSegmentedByteString.getDirectory$okio()[(((Object[])paramSegmentedByteString.getSegments$okio()).length + i)];
      k = Math.min(paramInt2, k - j + j) - paramInt1;
      paramFunction3.invoke(paramSegmentedByteString.getSegments$okio()[i], Integer.valueOf(m + (paramInt1 - j)), Integer.valueOf(k));
      paramInt1 += k;
    }
  }
  
  public static final void forEachSegment(SegmentedByteString paramSegmentedByteString, Function3<? super byte[], ? super Integer, ? super Integer, Unit> paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$forEachSegment");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "action");
    int i = ((Object[])paramSegmentedByteString.getSegments$okio()).length;
    int j = 0;
    int n;
    for (int k = 0; j < i; k = n)
    {
      int m = paramSegmentedByteString.getDirectory$okio()[(i + j)];
      n = paramSegmentedByteString.getDirectory$okio()[j];
      paramFunction3.invoke(paramSegmentedByteString.getSegments$okio()[j], Integer.valueOf(m), Integer.valueOf(n - k));
      j++;
    }
  }
  
  public static final int segment(SegmentedByteString paramSegmentedByteString, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSegmentedByteString, "$this$segment");
    paramInt = binarySearch(paramSegmentedByteString.getDirectory$okio(), paramInt + 1, 0, ((Object[])paramSegmentedByteString.getSegments$okio()).length);
    if (paramInt < 0) {
      paramInt = paramInt;
    }
    return paramInt;
  }
}
