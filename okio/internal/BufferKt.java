package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio._Platform;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\000v\n\000\n\002\020\022\n\002\b\003\n\002\020\t\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\002\030\002\n\002\b\b\n\002\020\000\n\000\n\002\020\005\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\t\n\002\020\n\n\000\n\002\020\016\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\025\n\002\030\002\n\002\b\004\0320\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\b2\006\020\016\032\0020\0012\006\020\017\032\0020\b2\006\020\020\032\0020\bH\000\032\r\020\021\032\0020\022*\0020\023H?\b\032\r\020\024\032\0020\005*\0020\023H?\b\032\r\020\025\032\0020\023*\0020\023H?\b\032%\020\026\032\0020\023*\0020\0232\006\020\027\032\0020\0232\006\020\030\032\0020\0052\006\020\031\032\0020\005H?\b\032\027\020\032\032\0020\n*\0020\0232\b\020\033\032\004\030\0010\034H?\b\032\025\020\035\032\0020\036*\0020\0232\006\020\037\032\0020\005H?\b\032\r\020 \032\0020\b*\0020\023H?\b\032%\020!\032\0020\005*\0020\0232\006\020\"\032\0020\0362\006\020#\032\0020\0052\006\020$\032\0020\005H?\b\032\035\020!\032\0020\005*\0020\0232\006\020\016\032\0020%2\006\020#\032\0020\005H?\b\032\035\020&\032\0020\005*\0020\0232\006\020'\032\0020%2\006\020#\032\0020\005H?\b\032-\020(\032\0020\n*\0020\0232\006\020\030\032\0020\0052\006\020\016\032\0020%2\006\020\017\032\0020\b2\006\020\031\032\0020\bH?\b\032\025\020)\032\0020\b*\0020\0232\006\020*\032\0020\001H?\b\032%\020)\032\0020\b*\0020\0232\006\020*\032\0020\0012\006\020\030\032\0020\b2\006\020\031\032\0020\bH?\b\032\035\020)\032\0020\005*\0020\0232\006\020*\032\0020\0232\006\020\031\032\0020\005H?\b\032\025\020+\032\0020\005*\0020\0232\006\020*\032\0020,H?\b\032\r\020-\032\0020\036*\0020\023H?\b\032\r\020.\032\0020\001*\0020\023H?\b\032\025\020.\032\0020\001*\0020\0232\006\020\031\032\0020\005H?\b\032\r\020/\032\0020%*\0020\023H?\b\032\025\020/\032\0020%*\0020\0232\006\020\031\032\0020\005H?\b\032\r\0200\032\0020\005*\0020\023H?\b\032\025\0201\032\0020\022*\0020\0232\006\020*\032\0020\001H?\b\032\035\0201\032\0020\022*\0020\0232\006\020*\032\0020\0232\006\020\031\032\0020\005H?\b\032\r\0202\032\0020\005*\0020\023H?\b\032\r\0203\032\0020\b*\0020\023H?\b\032\r\0204\032\0020\005*\0020\023H?\b\032\r\0205\032\00206*\0020\023H?\b\032\025\0207\032\00208*\0020\0232\006\020\031\032\0020\005H?\b\032\r\0209\032\0020\b*\0020\023H?\b\032\017\020:\032\004\030\00108*\0020\023H?\b\032\025\020;\032\00208*\0020\0232\006\020<\032\0020\005H?\b\032\025\020=\032\0020\b*\0020\0232\006\020>\032\0020?H?\b\032\025\020@\032\0020\022*\0020\0232\006\020\031\032\0020\005H?\b\032\r\020A\032\0020%*\0020\023H?\b\032\025\020A\032\0020%*\0020\0232\006\020\031\032\0020\bH?\b\032\025\020B\032\0020\f*\0020\0232\006\020C\032\0020\bH?\b\032\025\020D\032\0020\023*\0020\0232\006\020E\032\0020\001H?\b\032%\020D\032\0020\023*\0020\0232\006\020E\032\0020\0012\006\020\030\032\0020\b2\006\020\031\032\0020\bH?\b\032\035\020D\032\0020\022*\0020\0232\006\020E\032\0020\0232\006\020\031\032\0020\005H?\b\032)\020D\032\0020\023*\0020\0232\006\020F\032\0020%2\b\b\002\020\030\032\0020\b2\b\b\002\020\031\032\0020\bH?\b\032\035\020D\032\0020\023*\0020\0232\006\020E\032\0020G2\006\020\031\032\0020\005H?\b\032\025\020H\032\0020\005*\0020\0232\006\020E\032\0020GH?\b\032\025\020I\032\0020\023*\0020\0232\006\020\"\032\0020\bH?\b\032\025\020J\032\0020\023*\0020\0232\006\020K\032\0020\005H?\b\032\025\020L\032\0020\023*\0020\0232\006\020K\032\0020\005H?\b\032\025\020M\032\0020\023*\0020\0232\006\020N\032\0020\bH?\b\032\025\020O\032\0020\023*\0020\0232\006\020K\032\0020\005H?\b\032\025\020P\032\0020\023*\0020\0232\006\020Q\032\0020\bH?\b\032%\020R\032\0020\023*\0020\0232\006\020S\032\002082\006\020T\032\0020\b2\006\020U\032\0020\bH?\b\032\025\020V\032\0020\023*\0020\0232\006\020W\032\0020\bH?\b\032\024\020X\032\00208*\0020\0232\006\020Y\032\0020\005H\000\032<\020Z\032\002H[\"\004\b\000\020[*\0020\0232\006\020#\032\0020\0052\032\020\\\032\026\022\006\022\004\030\0010\f\022\004\022\0020\005\022\004\022\002H[0]H?\b?\006\002\020^\032\036\020_\032\0020\b*\0020\0232\006\020>\032\0020?2\b\b\002\020`\032\0020\nH\000\"\024\020\000\032\0020\001X?\004?\006\b\n\000\032\004\b\002\020\003\"\016\020\004\032\0020\005X?T?\006\002\n\000\"\016\020\006\032\0020\005X?T?\006\002\n\000\"\016\020\007\032\0020\bX?T?\006\002\n\000?\006a"}, d2={"HEX_DIGIT_BYTES", "", "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", "", "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", "", "rangeEquals", "", "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", "", "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", "offset", "byteCount", "commonEquals", "other", "", "commonGet", "", "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", "", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", "string", "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"}, k=2, mv={1, 1, 16})
public final class BufferKt
{
  private static final byte[] HEX_DIGIT_BYTES = _Platform.asUtf8ToByteArray("0123456789abcdef");
  public static final long OVERFLOW_DIGIT_START = -7L;
  public static final long OVERFLOW_ZONE = -922337203685477580L;
  public static final int SEGMENTING_THRESHOLD = 4096;
  
  public static final void commonClear(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonClear");
    paramBuffer.skip(paramBuffer.size());
  }
  
  public static final long commonCompleteSegmentByteCount(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonCompleteSegmentByteCount");
    long l1 = paramBuffer.size();
    if (l1 == 0L) {
      return 0L;
    }
    paramBuffer = paramBuffer.head;
    if (paramBuffer == null) {
      Intrinsics.throwNpe();
    }
    paramBuffer = paramBuffer.prev;
    if (paramBuffer == null) {
      Intrinsics.throwNpe();
    }
    long l2 = l1;
    if (paramBuffer.limit < 8192)
    {
      l2 = l1;
      if (paramBuffer.owner) {
        l2 = l1 - (paramBuffer.limit - paramBuffer.pos);
      }
    }
    return l2;
  }
  
  public static final Buffer commonCopy(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonCopy");
    Buffer localBuffer = new Buffer();
    if (paramBuffer.size() == 0L) {
      return localBuffer;
    }
    Segment localSegment1 = paramBuffer.head;
    if (localSegment1 == null) {
      Intrinsics.throwNpe();
    }
    Segment localSegment2 = localSegment1.sharedCopy();
    localBuffer.head = localSegment2;
    localSegment2.prev = localBuffer.head;
    localSegment2.next = localSegment2.prev;
    for (Segment localSegment3 = localSegment1.next; localSegment3 != localSegment1; localSegment3 = localSegment3.next)
    {
      Segment localSegment4 = localSegment2.prev;
      if (localSegment4 == null) {
        Intrinsics.throwNpe();
      }
      if (localSegment3 == null) {
        Intrinsics.throwNpe();
      }
      localSegment4.push(localSegment3.sharedCopy());
    }
    localBuffer.setSize$okio(paramBuffer.size());
    return localBuffer;
  }
  
  public static final Buffer commonCopyTo(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong1, long paramLong2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer1, "$this$commonCopyTo");
    Intrinsics.checkParameterIsNotNull(paramBuffer2, "out");
    _Util.checkOffsetAndCount(paramBuffer1.size(), paramLong1, paramLong2);
    if (paramLong2 == 0L) {
      return paramBuffer1;
    }
    paramBuffer2.setSize$okio(paramBuffer2.size() + paramLong2);
    Segment localSegment2;
    long l1;
    long l2;
    for (Segment localSegment1 = paramBuffer1.head;; localSegment1 = localSegment1.next)
    {
      if (localSegment1 == null) {
        Intrinsics.throwNpe();
      }
      localSegment2 = localSegment1;
      l1 = paramLong1;
      l2 = paramLong2;
      if (paramLong1 < localSegment1.limit - localSegment1.pos) {
        break;
      }
      paramLong1 -= localSegment1.limit - localSegment1.pos;
    }
    while (l2 > 0L)
    {
      if (localSegment2 == null) {
        Intrinsics.throwNpe();
      }
      localSegment1 = localSegment2.sharedCopy();
      localSegment1.pos += (int)l1;
      localSegment1.limit = Math.min(localSegment1.pos + (int)l2, localSegment1.limit);
      if (paramBuffer2.head == null)
      {
        localSegment1.prev = localSegment1;
        localSegment1.next = localSegment1.prev;
        paramBuffer2.head = localSegment1.next;
      }
      else
      {
        Segment localSegment3 = paramBuffer2.head;
        if (localSegment3 == null) {
          Intrinsics.throwNpe();
        }
        localSegment3 = localSegment3.prev;
        if (localSegment3 == null) {
          Intrinsics.throwNpe();
        }
        localSegment3.push(localSegment1);
      }
      l2 -= localSegment1.limit - localSegment1.pos;
      localSegment2 = localSegment2.next;
      l1 = 0L;
    }
    return paramBuffer1;
  }
  
  public static final boolean commonEquals(Buffer paramBuffer, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonEquals");
    if (paramBuffer == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Buffer)) {
      return false;
    }
    long l1 = paramBuffer.size();
    paramObject = (Buffer)paramObject;
    if (l1 != paramObject.size()) {
      return false;
    }
    if (paramBuffer.size() == 0L) {
      return true;
    }
    Object localObject1 = paramBuffer.head;
    if (localObject1 == null) {
      Intrinsics.throwNpe();
    }
    Object localObject2 = paramObject.head;
    if (localObject2 == null) {
      Intrinsics.throwNpe();
    }
    int i = ((Segment)localObject1).pos;
    int j = ((Segment)localObject2).pos;
    l1 = 0L;
    while (l1 < paramBuffer.size())
    {
      long l2 = Math.min(((Segment)localObject1).limit - i, ((Segment)localObject2).limit - j);
      long l3 = 0L;
      int k = i;
      while (l3 < l2)
      {
        if (localObject1.data[k] != localObject2.data[j]) {
          return false;
        }
        l3 += 1L;
        k++;
        j++;
      }
      paramObject = localObject1;
      i = k;
      if (k == ((Segment)localObject1).limit)
      {
        paramObject = ((Segment)localObject1).next;
        if (paramObject == null) {
          Intrinsics.throwNpe();
        }
        i = paramObject.pos;
      }
      localObject1 = localObject2;
      k = j;
      if (j == ((Segment)localObject2).limit)
      {
        localObject1 = ((Segment)localObject2).next;
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        k = ((Segment)localObject1).pos;
      }
      l1 += l2;
      localObject2 = localObject1;
      localObject1 = paramObject;
      j = k;
    }
    return true;
  }
  
  public static final byte commonGet(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonGet");
    _Util.checkOffsetAndCount(paramBuffer.size(), paramLong, 1L);
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      if (paramBuffer.size() - paramLong < paramLong)
      {
        for (l1 = paramBuffer.size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          if (localSegment == null) {
            Intrinsics.throwNpe();
          }
        }
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        return localSegment.data[((int)(localSegment.pos + paramLong - l1))];
      }
      long l2;
      for (long l1 = 0L;; l1 = l2)
      {
        l2 = localSegment.limit - localSegment.pos + l1;
        if (l2 > paramLong)
        {
          if (localSegment == null) {
            Intrinsics.throwNpe();
          }
          return localSegment.data[((int)(localSegment.pos + paramLong - l1))];
        }
        localSegment = localSegment.next;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
      }
    }
    paramBuffer = (Segment)null;
    Intrinsics.throwNpe();
    return paramBuffer.data[((int)(paramBuffer.pos + paramLong + 1L))];
  }
  
  public static final int commonHashCode(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonHashCode");
    Object localObject = paramBuffer.head;
    if (localObject != null)
    {
      int i = 1;
      int m;
      Segment localSegment;
      do
      {
        int j = ((Segment)localObject).pos;
        int k = ((Segment)localObject).limit;
        m = i;
        while (j < k)
        {
          m = m * 31 + localObject.data[j];
          j++;
        }
        localSegment = ((Segment)localObject).next;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        localObject = localSegment;
        i = m;
      } while (localSegment != paramBuffer.head);
      return m;
    }
    return 0;
  }
  
  public static final long commonIndexOf(Buffer paramBuffer, byte paramByte, long paramLong1, long paramLong2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonIndexOf");
    long l1 = 0L;
    int i;
    if ((0L <= paramLong1) && (paramLong2 >= paramLong1)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l2 = paramLong2;
      if (paramLong2 > paramBuffer.size()) {
        l2 = paramBuffer.size();
      }
      if (paramLong1 == l2) {
        return -1L;
      }
      Segment localSegment = paramBuffer.head;
      if (localSegment != null)
      {
        paramLong2 = l1;
        localObject = localSegment;
        int j;
        if (paramBuffer.size() - paramLong1 < paramLong1)
        {
          paramLong2 = paramBuffer.size();
          paramBuffer = localSegment;
          while (paramLong2 > paramLong1)
          {
            paramBuffer = paramBuffer.prev;
            if (paramBuffer == null) {
              Intrinsics.throwNpe();
            }
            paramLong2 -= paramBuffer.limit - paramBuffer.pos;
          }
          if (paramBuffer != null)
          {
            l1 = paramLong1;
            paramLong1 = paramLong2;
            while (paramLong1 < l2)
            {
              localObject = paramBuffer.data;
              j = (int)Math.min(paramBuffer.limit, paramBuffer.pos + l2 - paramLong1);
              for (i = (int)(paramBuffer.pos + l1 - paramLong1); i < j; i++) {
                if (localObject[i] == paramByte) {
                  return i - paramBuffer.pos + paramLong1;
                }
              }
              paramLong1 += paramBuffer.limit - paramBuffer.pos;
              paramBuffer = paramBuffer.next;
              if (paramBuffer == null) {
                Intrinsics.throwNpe();
              }
              l1 = paramLong1;
            }
          }
          return -1L;
        }
        for (;;)
        {
          l1 = ((Segment)localObject).limit - ((Segment)localObject).pos + paramLong2;
          if (l1 > paramLong1)
          {
            if (localObject != null) {
              for (;;)
              {
                if (paramLong2 >= l2) {
                  break label420;
                }
                paramBuffer = ((Segment)localObject).data;
                j = (int)Math.min(((Segment)localObject).limit, ((Segment)localObject).pos + l2 - paramLong2);
                for (i = (int)(((Segment)localObject).pos + paramLong1 - paramLong2);; i++)
                {
                  if (i >= j) {
                    break label382;
                  }
                  if (paramBuffer[i] == paramByte)
                  {
                    paramLong1 = paramLong2;
                    paramBuffer = (Buffer)localObject;
                    break;
                  }
                }
                label382:
                paramLong2 += ((Segment)localObject).limit - ((Segment)localObject).pos;
                localObject = ((Segment)localObject).next;
                if (localObject == null) {
                  Intrinsics.throwNpe();
                }
                paramLong1 = paramLong2;
              }
            }
            label420:
            return -1L;
          }
          localObject = ((Segment)localObject).next;
          if (localObject == null) {
            Intrinsics.throwNpe();
          }
          paramLong2 = l1;
        }
      }
      paramBuffer = (Segment)null;
      return -1L;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("size=");
    ((StringBuilder)localObject).append(paramBuffer.size());
    ((StringBuilder)localObject).append(" fromIndex=");
    ((StringBuilder)localObject).append(paramLong1);
    ((StringBuilder)localObject).append(" toIndex=");
    ((StringBuilder)localObject).append(paramLong2);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public static final long commonIndexOf(Buffer paramBuffer, ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonIndexOf");
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    int i = paramByteString.size();
    int j = 1;
    if (i > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l1 = 0L;
      if (paramLong >= 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        Object localObject1 = paramBuffer.head;
        if (localObject1 != null)
        {
          Object localObject2 = localObject1;
          int k;
          int m;
          long l2;
          long l3;
          int n;
          if (paramBuffer.size() - paramLong < paramLong)
          {
            l1 = paramBuffer.size();
            localObject2 = localObject1;
            while (l1 > paramLong)
            {
              localObject2 = ((Segment)localObject2).prev;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              l1 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
            }
            if (localObject2 != null)
            {
              localObject1 = paramByteString.internalArray$okio();
              k = localObject1[0];
              m = paramByteString.size();
              l2 = paramBuffer.size() - m + 1L;
              while (l1 < l2)
              {
                paramBuffer = ((Segment)localObject2).data;
                i = ((Segment)localObject2).limit;
                l3 = ((Segment)localObject2).pos;
                n = (int)Math.min(i, l3 + l2 - l1);
                for (i = (int)(((Segment)localObject2).pos + paramLong - l1); i < n; i++) {
                  if ((paramBuffer[i] == k) && (rangeEquals((Segment)localObject2, i + 1, (byte[])localObject1, 1, m))) {
                    return i - ((Segment)localObject2).pos + l1;
                  }
                }
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                paramLong = l1;
              }
              return -1L;
            }
            return -1L;
          }
          for (;;)
          {
            l2 = ((Segment)localObject2).limit - ((Segment)localObject2).pos + l1;
            if (l2 > paramLong)
            {
              if (localObject2 != null)
              {
                localObject1 = paramByteString.internalArray$okio();
                m = localObject1[0];
                k = paramByteString.size();
                l2 = paramBuffer.size() - k + 1L;
                while (l1 < l2)
                {
                  paramBuffer = ((Segment)localObject2).data;
                  i = ((Segment)localObject2).limit;
                  l3 = ((Segment)localObject2).pos;
                  n = (int)Math.min(i, l3 + l2 - l1);
                  for (i = (int)(((Segment)localObject2).pos + paramLong - l1); i < n; i++) {
                    if ((paramBuffer[i] == m) && (rangeEquals((Segment)localObject2, i + 1, (byte[])localObject1, 1, k))) {
                      return i - ((Segment)localObject2).pos + l1;
                    }
                  }
                  paramLong = ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                  localObject2 = ((Segment)localObject2).next;
                  if (localObject2 == null) {
                    Intrinsics.throwNpe();
                  }
                  l1 += paramLong;
                  paramLong = l1;
                }
                return -1L;
              }
              return -1L;
            }
            localObject2 = ((Segment)localObject2).next;
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            l1 = l2;
          }
        }
        paramBuffer = (Segment)null;
        return -1L;
      }
      paramBuffer = new StringBuilder();
      paramBuffer.append("fromIndex < 0: ");
      paramBuffer.append(paramLong);
      throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
    }
    throw ((Throwable)new IllegalArgumentException("bytes is empty".toString()));
  }
  
  public static final long commonIndexOfElement(Buffer paramBuffer, ByteString paramByteString, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonIndexOfElement");
    Intrinsics.checkParameterIsNotNull(paramByteString, "targetBytes");
    long l1 = 0L;
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Object localObject1 = paramBuffer.head;
      if (localObject1 != null)
      {
        Object localObject2 = localObject1;
        int j;
        int k;
        int m;
        int n;
        int i1;
        if (paramBuffer.size() - paramLong < paramLong)
        {
          l1 = paramBuffer.size();
          localObject2 = localObject1;
          while (l1 > paramLong)
          {
            localObject2 = ((Segment)localObject2).prev;
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            l1 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
          }
          if (localObject2 != null)
          {
            if (paramByteString.size() == 2)
            {
              j = paramByteString.getByte(0);
              k = paramByteString.getByte(1);
              while (l1 < paramBuffer.size())
              {
                localObject1 = ((Segment)localObject2).data;
                m = (int)(((Segment)localObject2).pos + paramLong - l1);
                n = ((Segment)localObject2).limit;
                while (m < n)
                {
                  i1 = localObject1[m];
                  paramLong = l1;
                  paramByteString = (ByteString)localObject2;
                  i = m;
                  if (i1 != j) {
                    if (i1 == k)
                    {
                      paramLong = l1;
                      paramByteString = (ByteString)localObject2;
                      i = m;
                    }
                    else
                    {
                      m++;
                      continue;
                    }
                  }
                  m = paramByteString.pos;
                  return i - m + paramLong;
                }
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                paramLong = l1;
              }
            }
            paramByteString = paramByteString.internalArray$okio();
            for (;;)
            {
              if (l1 >= paramBuffer.size()) {
                break label433;
              }
              localObject1 = ((Segment)localObject2).data;
              i = (int)(((Segment)localObject2).pos + paramLong - l1);
              j = ((Segment)localObject2).limit;
              for (;;)
              {
                if (i >= j) {
                  break label395;
                }
                n = localObject1[i];
                k = paramByteString.length;
                for (m = 0;; m++)
                {
                  if (m >= k) {
                    break label389;
                  }
                  if (n == paramByteString[m])
                  {
                    m = ((Segment)localObject2).pos;
                    paramLong = l1;
                    break;
                  }
                }
                label389:
                i++;
              }
              label395:
              l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
              localObject2 = ((Segment)localObject2).next;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              paramLong = l1;
            }
          }
          label433:
          return -1L;
        }
        for (;;)
        {
          long l2 = ((Segment)localObject2).limit - ((Segment)localObject2).pos + l1;
          if (l2 > paramLong)
          {
            if (localObject2 != null)
            {
              if (paramByteString.size() == 2)
              {
                k = paramByteString.getByte(0);
                n = paramByteString.getByte(1);
                for (;;)
                {
                  if (l1 >= paramBuffer.size()) {
                    break label754;
                  }
                  localObject1 = ((Segment)localObject2).data;
                  m = (int)(((Segment)localObject2).pos + paramLong - l1);
                  j = ((Segment)localObject2).limit;
                  for (;;)
                  {
                    if (m >= j) {
                      break label583;
                    }
                    i1 = localObject1[m];
                    paramLong = l1;
                    paramByteString = (ByteString)localObject2;
                    i = m;
                    if (i1 == k) {
                      break;
                    }
                    if (i1 == n)
                    {
                      paramLong = l1;
                      paramByteString = (ByteString)localObject2;
                      i = m;
                      break;
                    }
                    m++;
                  }
                  label583:
                  l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                  localObject2 = ((Segment)localObject2).next;
                  if (localObject2 == null) {
                    Intrinsics.throwNpe();
                  }
                  paramLong = l1;
                }
              }
              paramByteString = paramByteString.internalArray$okio();
              for (;;)
              {
                if (l1 >= paramBuffer.size()) {
                  break label754;
                }
                localObject1 = ((Segment)localObject2).data;
                i = (int)(((Segment)localObject2).pos + paramLong - l1);
                j = ((Segment)localObject2).limit;
                for (;;)
                {
                  if (i >= j) {
                    break label716;
                  }
                  k = localObject1[i];
                  n = paramByteString.length;
                  for (m = 0;; m++)
                  {
                    if (m >= n) {
                      break label710;
                    }
                    if (k == paramByteString[m]) {
                      break;
                    }
                  }
                  label710:
                  i++;
                }
                label716:
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                paramLong = l1;
              }
            }
            label754:
            return -1L;
          }
          localObject2 = ((Segment)localObject2).next;
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          l1 = l2;
        }
      }
      paramBuffer = (Segment)null;
      return -1L;
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("fromIndex < 0: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final boolean commonRangeEquals(Buffer paramBuffer, long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonRangeEquals");
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    if ((paramLong >= 0L) && (paramInt1 >= 0) && (paramInt2 >= 0) && (paramBuffer.size() - paramLong >= paramInt2) && (paramByteString.size() - paramInt1 >= paramInt2))
    {
      for (int i = 0; i < paramInt2; i++) {
        if (paramBuffer.getByte(i + paramLong) != paramByteString.getByte(paramInt1 + i)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  public static final int commonRead(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonRead");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    return paramBuffer.read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final int commonRead(Buffer paramBuffer, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonRead");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      paramInt2 = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      ArraysKt.copyInto(localSegment.data, paramArrayOfByte, paramInt1, localSegment.pos, localSegment.pos + paramInt2);
      localSegment.pos += paramInt2;
      paramBuffer.setSize$okio(paramBuffer.size() - paramInt2);
      if (localSegment.pos == localSegment.limit)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      return paramInt2;
    }
    return -1;
  }
  
  public static final long commonRead(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer1, "$this$commonRead");
    Intrinsics.checkParameterIsNotNull(paramBuffer2, "sink");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer1.size() == 0L) {
        return -1L;
      }
      long l = paramLong;
      if (paramLong > paramBuffer1.size()) {
        l = paramBuffer1.size();
      }
      paramBuffer2.write(paramBuffer1, l);
      return l;
    }
    paramBuffer1 = new StringBuilder();
    paramBuffer1.append("byteCount < 0: ");
    paramBuffer1.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer1.toString().toString()));
  }
  
  public static final long commonReadAll(Buffer paramBuffer, Sink paramSink)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadAll");
    Intrinsics.checkParameterIsNotNull(paramSink, "sink");
    long l = paramBuffer.size();
    if (l > 0L) {
      paramSink.write(paramBuffer, l);
    }
    return l;
  }
  
  public static final byte commonReadByte(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadByte");
    if (paramBuffer.size() != 0L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      byte b = arrayOfByte[i];
      paramBuffer.setSize$okio(paramBuffer.size() - 1L);
      if (k == j)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      else
      {
        localSegment.pos = k;
      }
      return b;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final byte[] commonReadByteArray(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadByteArray");
    return paramBuffer.readByteArray(paramBuffer.size());
  }
  
  public static final byte[] commonReadByteArray(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadByteArray");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        byte[] arrayOfByte = new byte[(int)paramLong];
        paramBuffer.readFully(arrayOfByte);
        return arrayOfByte;
      }
      throw ((Throwable)new EOFException());
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("byteCount: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final ByteString commonReadByteString(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadByteString");
    return paramBuffer.readByteString(paramBuffer.size());
  }
  
  public static final ByteString commonReadByteString(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadByteString");
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        if (paramLong >= '?')
        {
          ByteString localByteString = paramBuffer.snapshot((int)paramLong);
          paramBuffer.skip(paramLong);
          return localByteString;
        }
        return new ByteString(paramBuffer.readByteArray(paramLong));
      }
      throw ((Throwable)new EOFException());
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("byteCount: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final long commonReadDecimalLong(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadDecimalLong");
    long l1 = paramBuffer.size();
    long l2 = 0L;
    if (l1 != 0L)
    {
      long l3 = -7L;
      int i = 0;
      int j = 0;
      int k = j;
      int i1;
      int i2;
      label265:
      do
      {
        Object localObject = paramBuffer.head;
        if (localObject == null) {
          Intrinsics.throwNpe();
        }
        byte[] arrayOfByte = ((Segment)localObject).data;
        int m = ((Segment)localObject).pos;
        int n = ((Segment)localObject).limit;
        i1 = j;
        i2 = i;
        l1 = l2;
        l2 = l3;
        while (m < n)
        {
          byte b = arrayOfByte[m];
          j = (byte)48;
          if ((b >= j) && (b <= (byte)57))
          {
            i = j - b;
            bool = l1 < -922337203685477580L;
            if ((!bool) && ((bool) || (i >= l2)))
            {
              l1 = l1 * 10L + i;
            }
            else
            {
              paramBuffer = new Buffer().writeDecimalLong(l1).writeByte(b);
              if (i1 == 0) {
                paramBuffer.readByte();
              }
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("Number too large: ");
              ((StringBuilder)localObject).append(paramBuffer.readUtf8());
              throw ((Throwable)new NumberFormatException(((StringBuilder)localObject).toString()));
            }
          }
          else
          {
            if ((b != (byte)45) || (i2 != 0)) {
              break label265;
            }
            l2 -= 1L;
            i1 = 1;
          }
          m++;
          i2++;
          continue;
          if (i2 != 0)
          {
            k = 1;
          }
          else
          {
            paramBuffer = new StringBuilder();
            paramBuffer.append("Expected leading [0-9] or '-' character but was 0x");
            paramBuffer.append(_Util.toHexString(b));
            throw ((Throwable)new NumberFormatException(paramBuffer.toString()));
          }
        }
        if (m == n)
        {
          paramBuffer.head = ((Segment)localObject).pop();
          SegmentPool.INSTANCE.recycle((Segment)localObject);
        }
        else
        {
          ((Segment)localObject).pos = m;
        }
        if (k != 0) {
          break;
        }
        l3 = l2;
        l2 = l1;
        i = i2;
        boolean bool = i1;
      } while (paramBuffer.head != null);
      paramBuffer.setSize$okio(paramBuffer.size() - i2);
      if (i1 == 0) {
        l1 = -l1;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final void commonReadFully(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer1, "$this$commonReadFully");
    Intrinsics.checkParameterIsNotNull(paramBuffer2, "sink");
    if (paramBuffer1.size() >= paramLong)
    {
      paramBuffer2.write(paramBuffer1, paramLong);
      return;
    }
    paramBuffer2.write(paramBuffer1, paramBuffer1.size());
    throw ((Throwable)new EOFException());
  }
  
  public static final void commonReadFully(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadFully");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramBuffer.read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public static final long commonReadHexadecimalUnsignedLong(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadHexadecimalUnsignedLong");
    if (paramBuffer.size() != 0L)
    {
      int i = 0;
      int j = 0;
      long l1 = 0L;
      long l2;
      int n;
      label247:
      label299:
      do
      {
        Object localObject = paramBuffer.head;
        if (localObject == null) {
          Intrinsics.throwNpe();
        }
        byte[] arrayOfByte = ((Segment)localObject).data;
        int k = ((Segment)localObject).pos;
        int m = ((Segment)localObject).limit;
        l2 = l1;
        int i1;
        int i2;
        for (n = i;; n++)
        {
          i1 = j;
          if (k >= m) {
            break label299;
          }
          i2 = arrayOfByte[k];
          i = (byte)48;
          if ((i2 >= i) && (i2 <= (byte)57))
          {
            i = i2 - i;
          }
          else
          {
            i = (byte)97;
            if ((i2 >= i) && (i2 <= (byte)102)) {}
            for (;;)
            {
              i = i2 - i + 10;
              break;
              i = (byte)65;
              if ((i2 < i) || (i2 > (byte)70)) {
                break label247;
              }
            }
          }
          if ((0xF000000000000000 & l2) != 0L) {
            break;
          }
          l2 = l2 << 4 | i;
          k++;
        }
        paramBuffer = new Buffer().writeHexadecimalUnsignedLong(l2).writeByte(i2);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Number too large: ");
        ((StringBuilder)localObject).append(paramBuffer.readUtf8());
        throw ((Throwable)new NumberFormatException(((StringBuilder)localObject).toString()));
        if (n != 0)
        {
          i1 = 1;
        }
        else
        {
          paramBuffer = new StringBuilder();
          paramBuffer.append("Expected leading [0-9a-fA-F] character but was 0x");
          paramBuffer.append(_Util.toHexString(i2));
          throw ((Throwable)new NumberFormatException(paramBuffer.toString()));
        }
        if (k == m)
        {
          paramBuffer.head = ((Segment)localObject).pop();
          SegmentPool.INSTANCE.recycle((Segment)localObject);
        }
        else
        {
          ((Segment)localObject).pos = k;
        }
        if (i1 != 0) {
          break;
        }
        i = n;
        j = i1;
        l1 = l2;
      } while (paramBuffer.head != null);
      paramBuffer.setSize$okio(paramBuffer.size() - n);
      return l2;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final int commonReadInt(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadInt");
    if (paramBuffer.size() >= 4L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      if (j - i < 4L)
      {
        j = paramBuffer.readByte();
        i = paramBuffer.readByte();
        k = paramBuffer.readByte();
        return paramBuffer.readByte() & 0xFF | (j & 0xFF) << 24 | (i & 0xFF) << 16 | (k & 0xFF) << 8;
      }
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      i = arrayOfByte[i];
      int m = k + 1;
      k = arrayOfByte[k];
      int n = m + 1;
      int i1 = arrayOfByte[m];
      m = n + 1;
      n = arrayOfByte[n];
      paramBuffer.setSize$okio(paramBuffer.size() - 4L);
      if (m == j)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      else
      {
        localSegment.pos = m;
      }
      return (i & 0xFF) << 24 | (k & 0xFF) << 16 | (i1 & 0xFF) << 8 | n & 0xFF;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final long commonReadLong(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadLong");
    if (paramBuffer.size() >= 8L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      if (j - i < 8L) {
        return (paramBuffer.readInt() & 0xFFFFFFFF) << 32 | 0xFFFFFFFF & paramBuffer.readInt();
      }
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      long l1 = arrayOfByte[i];
      i = k + 1;
      long l2 = arrayOfByte[k];
      k = i + 1;
      long l3 = arrayOfByte[i];
      i = k + 1;
      long l4 = arrayOfByte[k];
      k = i + 1;
      long l5 = arrayOfByte[i];
      i = k + 1;
      long l6 = arrayOfByte[k];
      k = i + 1;
      long l7 = arrayOfByte[i];
      i = k + 1;
      long l8 = arrayOfByte[k];
      paramBuffer.setSize$okio(paramBuffer.size() - 8L);
      if (i == j)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      else
      {
        localSegment.pos = i;
      }
      return (l1 & 0xFF) << 56 | (l2 & 0xFF) << 48 | (l3 & 0xFF) << 40 | (l4 & 0xFF) << 32 | (l5 & 0xFF) << 24 | (l6 & 0xFF) << 16 | (l7 & 0xFF) << 8 | l8 & 0xFF;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final short commonReadShort(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadShort");
    if (paramBuffer.size() >= 2L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      if (j - i < 2)
      {
        j = paramBuffer.readByte();
        return (short)(paramBuffer.readByte() & 0xFF | (j & 0xFF) << 8);
      }
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      int m = arrayOfByte[i];
      i = k + 1;
      k = arrayOfByte[k];
      paramBuffer.setSize$okio(paramBuffer.size() - 2L);
      if (i == j)
      {
        paramBuffer.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      else
      {
        localSegment.pos = i;
      }
      return (short)((m & 0xFF) << 8 | k & 0xFF);
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final String commonReadUtf8(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadUtf8");
    boolean bool = paramLong < 0L;
    int j;
    if ((!bool) && (paramLong <= Integer.MAX_VALUE)) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramBuffer.size() >= paramLong)
      {
        if (!bool) {
          return "";
        }
        Segment localSegment = paramBuffer.head;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        if (localSegment.pos + paramLong > localSegment.limit) {
          return _Utf8Kt.commonToUtf8String$default(paramBuffer.readByteArray(paramLong), 0, 0, 3, null);
        }
        Object localObject = localSegment.data;
        int k = localSegment.pos;
        j = localSegment.pos;
        int i = (int)paramLong;
        localObject = _Utf8Kt.commonToUtf8String((byte[])localObject, k, j + i);
        localSegment.pos += i;
        paramBuffer.setSize$okio(paramBuffer.size() - paramLong);
        if (localSegment.pos == localSegment.limit)
        {
          paramBuffer.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        return localObject;
      }
      throw ((Throwable)new EOFException());
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("byteCount: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final int commonReadUtf8CodePoint(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadUtf8CodePoint");
    if (paramBuffer.size() != 0L)
    {
      int i = paramBuffer.getByte(0L);
      int j = 1;
      int k = 65533;
      int m;
      int n;
      int i1;
      if ((i & 0x80) == 0)
      {
        m = i & 0x7F;
        n = 0;
        i1 = 1;
      }
      else if ((i & 0xE0) == 192)
      {
        m = i & 0x1F;
        i1 = 2;
        n = 128;
      }
      else if ((i & 0xF0) == 224)
      {
        m = i & 0xF;
        i1 = 3;
        n = 2048;
      }
      else
      {
        if ((i & 0xF8) != 240) {
          break label359;
        }
        m = i & 0x7;
        i1 = 4;
        n = 65536;
      }
      long l1 = paramBuffer.size();
      long l2 = i1;
      if (l1 >= l2)
      {
        while (j < i1)
        {
          l1 = j;
          int i2 = paramBuffer.getByte(l1);
          if ((i2 & 0xC0) == 128)
          {
            m = m << 6 | i2 & 0x3F;
            j++;
          }
          else
          {
            paramBuffer.skip(l1);
            return 65533;
          }
        }
        paramBuffer.skip(l2);
        if (m > 1114111) {
          m = k;
        } else if ((55296 <= m) && (57343 >= m)) {
          m = k;
        } else if (m < n) {
          m = k;
        }
        return m;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("size < ");
      localStringBuilder.append(i1);
      localStringBuilder.append(": ");
      localStringBuilder.append(paramBuffer.size());
      localStringBuilder.append(" (to read code point prefixed 0x");
      localStringBuilder.append(_Util.toHexString(i));
      localStringBuilder.append(')');
      throw ((Throwable)new EOFException(localStringBuilder.toString()));
      label359:
      paramBuffer.skip(1L);
      return 65533;
    }
    throw ((Throwable)new EOFException());
  }
  
  public static final String commonReadUtf8Line(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadUtf8Line");
    long l = paramBuffer.indexOf((byte)10);
    if (l != -1L) {
      paramBuffer = readUtf8Line(paramBuffer, l);
    } else if (paramBuffer.size() != 0L) {
      paramBuffer = paramBuffer.readUtf8(paramBuffer.size());
    } else {
      paramBuffer = null;
    }
    return paramBuffer;
  }
  
  public static final String commonReadUtf8LineStrict(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonReadUtf8LineStrict");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      long l1 = Long.MAX_VALUE;
      if (paramLong != Long.MAX_VALUE) {
        l1 = paramLong + 1L;
      }
      byte b = (byte)10;
      long l2 = paramBuffer.indexOf(b, 0L, l1);
      if (l2 != -1L) {
        return readUtf8Line(paramBuffer, l2);
      }
      if ((l1 < paramBuffer.size()) && (paramBuffer.getByte(l1 - 1L) == (byte)13) && (paramBuffer.getByte(l1) == b)) {
        return readUtf8Line(paramBuffer, l1);
      }
      Buffer localBuffer = new Buffer();
      l1 = paramBuffer.size();
      paramBuffer.copyTo(localBuffer, 0L, Math.min(32, l1));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("\\n not found: limit=");
      localStringBuilder.append(Math.min(paramBuffer.size(), paramLong));
      localStringBuilder.append(" content=");
      localStringBuilder.append(localBuffer.readByteString().hex());
      localStringBuilder.append('�');
      throw ((Throwable)new EOFException(localStringBuilder.toString()));
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("limit < 0: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final int commonSelect(Buffer paramBuffer, Options paramOptions)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonSelect");
    Intrinsics.checkParameterIsNotNull(paramOptions, "options");
    int i = selectPrefix$default(paramBuffer, paramOptions, false, 2, null);
    if (i == -1) {
      return -1;
    }
    paramBuffer.skip(paramOptions.getByteStrings$okio()[i].size());
    return i;
  }
  
  public static final void commonSkip(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonSkip");
    while (paramLong > 0L)
    {
      Segment localSegment = paramBuffer.head;
      if (localSegment != null)
      {
        int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
        long l1 = paramBuffer.size();
        long l2 = i;
        paramBuffer.setSize$okio(l1 - l2);
        l2 = paramLong - l2;
        localSegment.pos += i;
        paramLong = l2;
        if (localSegment.pos == localSegment.limit)
        {
          paramBuffer.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
          paramLong = l2;
        }
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public static final ByteString commonSnapshot(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonSnapshot");
    int i;
    if (paramBuffer.size() <= Integer.MAX_VALUE) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return paramBuffer.snapshot((int)paramBuffer.size());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("size > Int.MAX_VALUE: ");
    localStringBuilder.append(paramBuffer.size());
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public static final ByteString commonSnapshot(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonSnapshot");
    if (paramInt == 0) {
      return ByteString.EMPTY;
    }
    _Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramInt);
    Object localObject = paramBuffer.head;
    int i = 0;
    int j = 0;
    int k = j;
    while (j < paramInt)
    {
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      if (((Segment)localObject).limit != ((Segment)localObject).pos)
      {
        j += ((Segment)localObject).limit - ((Segment)localObject).pos;
        k++;
        localObject = ((Segment)localObject).next;
      }
      else
      {
        throw ((Throwable)new AssertionError("s.limit == s.pos"));
      }
    }
    byte[][] arrayOfByte = new byte[k][];
    localObject = new int[k * 2];
    paramBuffer = paramBuffer.head;
    k = 0;
    j = i;
    while (j < paramInt)
    {
      if (paramBuffer == null) {
        Intrinsics.throwNpe();
      }
      arrayOfByte[k] = paramBuffer.data;
      j += paramBuffer.limit - paramBuffer.pos;
      localObject[k] = Math.min(j, paramInt);
      localObject[(((Object[])arrayOfByte).length + k)] = paramBuffer.pos;
      paramBuffer.shared = true;
      k++;
      paramBuffer = paramBuffer.next;
    }
    return (ByteString)new SegmentedByteString((byte[][])arrayOfByte, (int[])localObject);
  }
  
  public static final Segment commonWritableSegment(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWritableSegment");
    int i = 1;
    if ((paramInt < 1) || (paramInt > 8192)) {
      i = 0;
    }
    if (i != 0)
    {
      if (paramBuffer.head == null)
      {
        localSegment = SegmentPool.INSTANCE.take();
        paramBuffer.head = localSegment;
        localSegment.prev = localSegment;
        localSegment.next = localSegment;
        return localSegment;
      }
      paramBuffer = paramBuffer.head;
      if (paramBuffer == null) {
        Intrinsics.throwNpe();
      }
      Segment localSegment = paramBuffer.prev;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      if (localSegment.limit + paramInt <= 8192)
      {
        paramBuffer = localSegment;
        if (localSegment.owner) {}
      }
      else
      {
        paramBuffer = localSegment.push(SegmentPool.INSTANCE.take());
      }
      return paramBuffer;
    }
    throw ((Throwable)new IllegalArgumentException("unexpected capacity".toString()));
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramByteString, "byteString");
    paramByteString.write$okio(paramBuffer, paramInt1, paramInt2);
    return paramBuffer;
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, Source paramSource, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramSource, "source");
    while (paramLong > 0L)
    {
      long l = paramSource.read(paramBuffer, paramLong);
      if (l != -1L) {
        paramLong -= l;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
    return paramBuffer;
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "source");
    return paramBuffer.write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static final Buffer commonWrite(Buffer paramBuffer, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "source");
    long l1 = paramArrayOfByte.length;
    long l2 = paramInt1;
    long l3 = paramInt2;
    _Util.checkOffsetAndCount(l1, l2, l3);
    int i = paramInt2 + paramInt1;
    while (paramInt1 < i)
    {
      Segment localSegment = paramBuffer.writableSegment$okio(1);
      int j = Math.min(i - paramInt1, 8192 - localSegment.limit);
      byte[] arrayOfByte = localSegment.data;
      int k = localSegment.limit;
      paramInt2 = paramInt1 + j;
      ArraysKt.copyInto(paramArrayOfByte, arrayOfByte, k, paramInt1, paramInt2);
      localSegment.limit += j;
      paramInt1 = paramInt2;
    }
    paramBuffer.setSize$okio(paramBuffer.size() + l3);
    return paramBuffer;
  }
  
  public static final void commonWrite(Buffer paramBuffer1, Buffer paramBuffer2, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer1, "$this$commonWrite");
    Intrinsics.checkParameterIsNotNull(paramBuffer2, "source");
    int i;
    if (paramBuffer2 != paramBuffer1) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      _Util.checkOffsetAndCount(paramBuffer2.size(), 0L, paramLong);
      while (paramLong > 0L)
      {
        Segment localSegment1 = paramBuffer2.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        i = localSegment1.limit;
        localSegment1 = paramBuffer2.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        Segment localSegment2;
        if (paramLong < i - localSegment1.pos)
        {
          if (paramBuffer1.head != null)
          {
            localSegment1 = paramBuffer1.head;
            if (localSegment1 == null) {
              Intrinsics.throwNpe();
            }
            localSegment1 = localSegment1.prev;
          }
          else
          {
            localSegment1 = null;
          }
          if ((localSegment1 != null) && (localSegment1.owner))
          {
            l = localSegment1.limit;
            if (localSegment1.shared) {
              i = 0;
            } else {
              i = localSegment1.pos;
            }
            if (l + paramLong - i <= '?')
            {
              localSegment2 = paramBuffer2.head;
              if (localSegment2 == null) {
                Intrinsics.throwNpe();
              }
              localSegment2.writeTo(localSegment1, (int)paramLong);
              paramBuffer2.setSize$okio(paramBuffer2.size() - paramLong);
              paramBuffer1.setSize$okio(paramBuffer1.size() + paramLong);
              return;
            }
          }
          localSegment1 = paramBuffer2.head;
          if (localSegment1 == null) {
            Intrinsics.throwNpe();
          }
          paramBuffer2.head = localSegment1.split((int)paramLong);
        }
        localSegment1 = paramBuffer2.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        long l = localSegment1.limit - localSegment1.pos;
        paramBuffer2.head = localSegment1.pop();
        if (paramBuffer1.head == null)
        {
          paramBuffer1.head = localSegment1;
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
        }
        else
        {
          localSegment2 = paramBuffer1.head;
          if (localSegment2 == null) {
            Intrinsics.throwNpe();
          }
          localSegment2 = localSegment2.prev;
          if (localSegment2 == null) {
            Intrinsics.throwNpe();
          }
          localSegment2.push(localSegment1).compact();
        }
        paramBuffer2.setSize$okio(paramBuffer2.size() - l);
        paramBuffer1.setSize$okio(paramBuffer1.size() + l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalArgumentException("source == this".toString()));
  }
  
  public static final long commonWriteAll(Buffer paramBuffer, Source paramSource)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteAll");
    Intrinsics.checkParameterIsNotNull(paramSource, "source");
    long l2;
    for (long l1 = 0L;; l1 += l2)
    {
      l2 = paramSource.read(paramBuffer, '?');
      if (l2 == -1L) {
        return l1;
      }
    }
  }
  
  public static final Buffer commonWriteByte(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteByte");
    Segment localSegment = paramBuffer.writableSegment$okio(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)(byte)paramInt);
    paramBuffer.setSize$okio(paramBuffer.size() + 1L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteDecimalLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteDecimalLong");
    boolean bool = paramLong < 0L;
    if (!bool) {
      return paramBuffer.writeByte(48);
    }
    int j = 0;
    int k = 1;
    long l = paramLong;
    if (bool)
    {
      l = -paramLong;
      if (l < 0L) {
        return paramBuffer.writeUtf8("-9223372036854775808");
      }
      j = 1;
    }
    if (l < 100000000L) {
      if (l < 10000L) {
        if (l < 100L)
        {
          if (l >= 10L) {
            k = 2;
          }
        }
        else if (l < 1000L) {
          k = 3;
        } else {
          k = 4;
        }
      }
    }
    for (;;)
    {
      break;
      if (l < 1000000L)
      {
        if (l < 100000L) {
          k = 5;
        } else {
          k = 6;
        }
      }
      else if (l < 10000000L)
      {
        k = 7;
      }
      else
      {
        k = 8;
        continue;
        if (l < 1000000000000L)
        {
          if (l < 10000000000L)
          {
            if (l < 1000000000L) {
              k = 9;
            } else {
              k = 10;
            }
          }
          else if (l < 100000000000L) {
            k = 11;
          } else {
            k = 12;
          }
        }
        else if (l < 1000000000000000L)
        {
          if (l < 10000000000000L) {
            k = 13;
          } else if (l < 100000000000000L) {
            k = 14;
          } else {
            k = 15;
          }
        }
        else if (l < 100000000000000000L)
        {
          if (l < 10000000000000000L) {
            k = 16;
          } else {
            k = 17;
          }
        }
        else if (l < 1000000000000000000L) {
          k = 18;
        } else {
          k = 19;
        }
      }
    }
    bool = k;
    int i;
    if (j != 0) {
      i = k + 1;
    }
    Segment localSegment = paramBuffer.writableSegment$okio(i);
    byte[] arrayOfByte = localSegment.data;
    k = localSegment.limit + i;
    while (l != 0L)
    {
      paramLong = 10;
      int m = (int)(l % paramLong);
      k--;
      arrayOfByte[k] = ((byte)getHEX_DIGIT_BYTES()[m]);
      l /= paramLong;
    }
    if (j != 0) {
      arrayOfByte[(k - 1)] = ((byte)(byte)45);
    }
    localSegment.limit += i;
    paramBuffer.setSize$okio(paramBuffer.size() + i);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteHexadecimalUnsignedLong");
    if (paramLong == 0L) {
      return paramBuffer.writeByte(48);
    }
    long l = paramLong >>> 1 | paramLong;
    l |= l >>> 2;
    l |= l >>> 4;
    l |= l >>> 8;
    l |= l >>> 16;
    l |= l >>> 32;
    l -= (l >>> 1 & 0x5555555555555555);
    l = (l >>> 2 & 0x3333333333333333) + (l & 0x3333333333333333);
    l = (l >>> 4) + l & 0xF0F0F0F0F0F0F0F;
    l += (l >>> 8);
    l += (l >>> 16);
    int i = (int)(((l & 0x3F) + (l >>> 32 & 0x3F) + 3) / 4);
    Segment localSegment = paramBuffer.writableSegment$okio(i);
    byte[] arrayOfByte = localSegment.data;
    int j = localSegment.limit + i - 1;
    int k = localSegment.limit;
    while (j >= k)
    {
      arrayOfByte[j] = ((byte)getHEX_DIGIT_BYTES()[((int)(0xF & paramLong))]);
      paramLong >>>= 4;
      j--;
    }
    localSegment.limit += i;
    paramBuffer.setSize$okio(paramBuffer.size() + i);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteInt(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteInt");
    Segment localSegment = paramBuffer.writableSegment$okio(4);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 24 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(paramInt >>> 16 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(paramInt & 0xFF));
    localSegment.limit = (j + 1);
    paramBuffer.setSize$okio(paramBuffer.size() + 4L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteLong(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteLong");
    Segment localSegment = paramBuffer.writableSegment$okio(8);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 56 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 48 & 0xFF));
    int k = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 40 & 0xFF));
    j = k + 1;
    arrayOfByte[k] = ((byte)(byte)(int)(paramLong >>> 32 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 24 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 16 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong & 0xFF));
    localSegment.limit = (i + 1);
    paramBuffer.setSize$okio(paramBuffer.size() + 8L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteShort(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteShort");
    Segment localSegment = paramBuffer.writableSegment$okio(2);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(paramInt & 0xFF));
    localSegment.limit = (j + 1);
    paramBuffer.setSize$okio(paramBuffer.size() + 2L);
    return paramBuffer;
  }
  
  public static final Buffer commonWriteUtf8(Buffer paramBuffer, String paramString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteUtf8");
    Intrinsics.checkParameterIsNotNull(paramString, "string");
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 >= paramInt1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt2 <= paramString.length()) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          while (paramInt1 < paramInt2)
          {
            int j = paramString.charAt(paramInt1);
            Segment localSegment;
            int m;
            if (j < 128)
            {
              localSegment = paramBuffer.writableSegment$okio(1);
              byte[] arrayOfByte = localSegment.data;
              int k = localSegment.limit - paramInt1;
              m = Math.min(paramInt2, 8192 - k);
              i = paramInt1 + 1;
              arrayOfByte[(paramInt1 + k)] = ((byte)(byte)j);
              for (paramInt1 = i; paramInt1 < m; paramInt1++)
              {
                i = paramString.charAt(paramInt1);
                if (i >= 128) {
                  break;
                }
                arrayOfByte[(paramInt1 + k)] = ((byte)(byte)i);
              }
              i = k + paramInt1 - localSegment.limit;
              localSegment.limit += i;
              paramBuffer.setSize$okio(paramBuffer.size() + i);
            }
            else
            {
              label225:
              if (j < 2048)
              {
                localSegment = paramBuffer.writableSegment$okio(2);
                localSegment.data[localSegment.limit] = ((byte)(byte)(j >> 6 | 0xC0));
                localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(j & 0x3F | 0x80));
                localSegment.limit += 2;
                paramBuffer.setSize$okio(paramBuffer.size() + 2L);
              }
              for (;;)
              {
                paramInt1++;
                break;
                if ((j >= 55296) && (j <= 57343))
                {
                  m = paramInt1 + 1;
                  if (m < paramInt2) {
                    i = paramString.charAt(m);
                  } else {
                    i = 0;
                  }
                  if ((j <= 56319) && (56320 <= i) && (57343 >= i))
                  {
                    i = ((j & 0x3FF) << 10 | i & 0x3FF) + 65536;
                    localSegment = paramBuffer.writableSegment$okio(4);
                    localSegment.data[localSegment.limit] = ((byte)(byte)(i >> 18 | 0xF0));
                    localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(i >> 12 & 0x3F | 0x80));
                    localSegment.data[(localSegment.limit + 2)] = ((byte)(byte)(i >> 6 & 0x3F | 0x80));
                    localSegment.data[(localSegment.limit + 3)] = ((byte)(byte)(i & 0x3F | 0x80));
                    localSegment.limit += 4;
                    paramBuffer.setSize$okio(paramBuffer.size() + 4L);
                    paramInt1 += 2;
                    break;
                  }
                  paramBuffer.writeByte(63);
                  paramInt1 = m;
                  break label225;
                }
                localSegment = paramBuffer.writableSegment$okio(3);
                localSegment.data[localSegment.limit] = ((byte)(byte)(j >> 12 | 0xE0));
                localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(0x3F & j >> 6 | 0x80));
                localSegment.data[(localSegment.limit + 2)] = ((byte)(byte)(j & 0x3F | 0x80));
                localSegment.limit += 3;
                paramBuffer.setSize$okio(paramBuffer.size() + 3L);
              }
            }
          }
          return paramBuffer;
        }
        paramBuffer = new StringBuilder();
        paramBuffer.append("endIndex > string.length: ");
        paramBuffer.append(paramInt2);
        paramBuffer.append(" > ");
        paramBuffer.append(paramString.length());
        throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
      }
      paramBuffer = new StringBuilder();
      paramBuffer.append("endIndex < beginIndex: ");
      paramBuffer.append(paramInt2);
      paramBuffer.append(" < ");
      paramBuffer.append(paramInt1);
      throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("beginIndex < 0: ");
    paramBuffer.append(paramInt1);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public static final Buffer commonWriteUtf8CodePoint(Buffer paramBuffer, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$commonWriteUtf8CodePoint");
    if (paramInt < 128)
    {
      paramBuffer.writeByte(paramInt);
    }
    else
    {
      Segment localSegment;
      if (paramInt < 2048)
      {
        localSegment = paramBuffer.writableSegment$okio(2);
        localSegment.data[localSegment.limit] = ((byte)(byte)(paramInt >> 6 | 0xC0));
        localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
        localSegment.limit += 2;
        paramBuffer.setSize$okio(paramBuffer.size() + 2L);
      }
      else if ((55296 <= paramInt) && (57343 >= paramInt))
      {
        paramBuffer.writeByte(63);
      }
      else if (paramInt < 65536)
      {
        localSegment = paramBuffer.writableSegment$okio(3);
        localSegment.data[localSegment.limit] = ((byte)(byte)(paramInt >> 12 | 0xE0));
        localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(paramInt >> 6 & 0x3F | 0x80));
        localSegment.data[(localSegment.limit + 2)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
        localSegment.limit += 3;
        paramBuffer.setSize$okio(paramBuffer.size() + 3L);
      }
      else
      {
        if (paramInt > 1114111) {
          break label355;
        }
        localSegment = paramBuffer.writableSegment$okio(4);
        localSegment.data[localSegment.limit] = ((byte)(byte)(paramInt >> 18 | 0xF0));
        localSegment.data[(localSegment.limit + 1)] = ((byte)(byte)(paramInt >> 12 & 0x3F | 0x80));
        localSegment.data[(localSegment.limit + 2)] = ((byte)(byte)(paramInt >> 6 & 0x3F | 0x80));
        localSegment.data[(localSegment.limit + 3)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
        localSegment.limit += 4;
        paramBuffer.setSize$okio(paramBuffer.size() + 4L);
      }
    }
    return paramBuffer;
    label355:
    paramBuffer = new StringBuilder();
    paramBuffer.append("Unexpected code point: 0x");
    paramBuffer.append(_Util.toHexString(paramInt));
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString()));
  }
  
  public static final byte[] getHEX_DIGIT_BYTES()
  {
    return HEX_DIGIT_BYTES;
  }
  
  public static final boolean rangeEquals(Segment paramSegment, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    Intrinsics.checkParameterIsNotNull(paramSegment, "segment");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "bytes");
    int i = paramSegment.limit;
    byte[] arrayOfByte = paramSegment.data;
    while (paramInt2 < paramInt3)
    {
      int j = i;
      Segment localSegment = paramSegment;
      int k = paramInt1;
      if (paramInt1 == i)
      {
        localSegment = paramSegment.next;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        arrayOfByte = localSegment.data;
        k = localSegment.pos;
        j = localSegment.limit;
      }
      if (arrayOfByte[k] != paramArrayOfByte[paramInt2]) {
        return false;
      }
      paramInt1 = k + 1;
      paramInt2++;
      i = j;
      paramSegment = localSegment;
    }
    return true;
  }
  
  public static final String readUtf8Line(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$readUtf8Line");
    if (paramLong > 0L)
    {
      long l = paramLong - 1L;
      if (paramBuffer.getByte(l) == (byte)13)
      {
        str = paramBuffer.readUtf8(l);
        paramBuffer.skip(2L);
        return str;
      }
    }
    String str = paramBuffer.readUtf8(paramLong);
    paramBuffer.skip(1L);
    paramBuffer = str;
    return paramBuffer;
  }
  
  public static final <T> T seek(Buffer paramBuffer, long paramLong, Function2<? super Segment, ? super Long, ? extends T> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$seek");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "lambda");
    Segment localSegment = paramBuffer.head;
    if (localSegment != null)
    {
      if (paramBuffer.size() - paramLong < paramLong)
      {
        for (l1 = paramBuffer.size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          if (localSegment == null) {
            Intrinsics.throwNpe();
          }
        }
        return paramFunction2.invoke(localSegment, Long.valueOf(l1));
      }
      long l2;
      for (long l1 = 0L;; l1 = l2)
      {
        l2 = localSegment.limit - localSegment.pos + l1;
        if (l2 > paramLong) {
          return paramFunction2.invoke(localSegment, Long.valueOf(l1));
        }
        localSegment = localSegment.next;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
      }
    }
    return paramFunction2.invoke(null, Long.valueOf(-1L));
  }
  
  public static final int selectPrefix(Buffer paramBuffer, Options paramOptions, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$selectPrefix");
    Intrinsics.checkParameterIsNotNull(paramOptions, "options");
    Segment localSegment = paramBuffer.head;
    int i = -2;
    if (localSegment != null)
    {
      Object localObject = localSegment.data;
      i = localSegment.pos;
      int j = localSegment.limit;
      int[] arrayOfInt = paramOptions.getTrie$okio();
      paramOptions = localSegment;
      int k = -1;
      int m = 0;
      int n = m + 1;
      int i1 = arrayOfInt[m];
      int i2 = n + 1;
      m = arrayOfInt[n];
      if (m != -1) {
        k = m;
      }
      int i3;
      if (paramOptions != null)
      {
        if (i1 >= 0) {}
      }
      else {
        for (m = i2;; m = n)
        {
          i3 = i + 1;
          i = localObject[i];
          n = m + 1;
          if ((i & 0xFF) != arrayOfInt[m]) {
            return k;
          }
          if (n == i2 + i1 * -1) {
            m = 1;
          } else {
            m = 0;
          }
          if (i3 == j)
          {
            if (paramOptions == null) {
              Intrinsics.throwNpe();
            }
            localObject = paramOptions.next;
            if (localObject == null) {
              Intrinsics.throwNpe();
            }
            j = ((Segment)localObject).pos;
            paramOptions = ((Segment)localObject).data;
            i = ((Segment)localObject).limit;
            paramBuffer = (Buffer)localObject;
            if (localObject == localSegment)
            {
              if (m == 0)
              {
                if (paramBoolean) {
                  return -2;
                }
                return k;
              }
              paramBuffer = (Segment)null;
            }
          }
          else
          {
            i = j;
            j = i3;
            paramBuffer = paramOptions;
            paramOptions = (Options)localObject;
          }
          if (m != 0)
          {
            i2 = arrayOfInt[n];
            localObject = paramOptions;
            m = i;
            i = j;
            break;
          }
          m = j;
          j = i;
          localObject = paramOptions;
          i = m;
          paramOptions = paramBuffer;
        }
      }
      n = i + 1;
      m = localObject[i];
      for (i = i2;; i++)
      {
        if (i == i2 + i1) {
          return k;
        }
        if ((m & 0xFF) == arrayOfInt[i])
        {
          i3 = arrayOfInt[(i + i1)];
          i2 = i3;
          m = j;
          paramBuffer = paramOptions;
          i = n;
          if (n == j)
          {
            paramOptions = paramOptions.next;
            if (paramOptions == null) {
              Intrinsics.throwNpe();
            }
            i = paramOptions.pos;
            localObject = paramOptions.data;
            m = paramOptions.limit;
            paramBuffer = paramOptions;
            if (paramOptions == localSegment) {
              paramBuffer = (Segment)null;
            }
            i2 = i3;
          }
          if (i2 >= 0) {
            return i2;
          }
          i2 = -i2;
          j = m;
          m = i2;
          paramOptions = paramBuffer;
          break;
        }
      }
    }
    if (!paramBoolean) {
      i = -1;
    }
    return i;
  }
}
