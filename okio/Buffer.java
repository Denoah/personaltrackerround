package okio;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import okio.internal.BufferKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\032\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\005\n\002\b\005\n\002\020\b\n\002\b\r\n\002\030\002\n\002\b\b\n\002\030\002\n\002\020\022\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\017\n\002\020\n\n\002\b\003\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\027\030\0002\0020\0012\0020\0022\0020\0032\0020\004:\002?\001B\005?\006\002\020\005J\b\020\006\032\0020\000H\026J\006\020\021\032\0020\022J\b\020\023\032\0020\000H\026J\b\020\024\032\0020\022H\026J\006\020\025\032\0020\fJ\006\020\026\032\0020\000J$\020\027\032\0020\0002\006\020\030\032\0020\0312\b\b\002\020\032\032\0020\f2\b\b\002\020\033\032\0020\fH\007J\030\020\027\032\0020\0002\006\020\030\032\0020\0002\b\b\002\020\032\032\0020\fJ \020\027\032\0020\0002\006\020\030\032\0020\0002\b\b\002\020\032\032\0020\f2\006\020\033\032\0020\fJ\020\020\034\032\0020\0352\006\020\036\032\0020\037H\002J\b\020 \032\0020\000H\026J\b\020!\032\0020\000H\026J\023\020\"\032\0020#2\b\020$\032\004\030\0010%H?\002J\b\020&\032\0020#H\026J\b\020'\032\0020\022H\026J\026\020(\032\0020)2\006\020*\032\0020\fH?\002?\006\002\b+J\025\020+\032\0020)2\006\020,\032\0020\fH\007?\006\002\b-J\b\020.\032\0020/H\026J\030\0200\032\0020\0352\006\020\036\032\0020\0372\006\0201\032\0020\035H\002J\016\0202\032\0020\0352\006\0201\032\0020\035J\016\0203\032\0020\0352\006\0201\032\0020\035J\016\0204\032\0020\0352\006\0201\032\0020\035J\020\0205\032\0020\f2\006\0206\032\0020)H\026J\030\0205\032\0020\f2\006\0206\032\0020)2\006\0207\032\0020\fH\026J \0205\032\0020\f2\006\0206\032\0020)2\006\0207\032\0020\f2\006\0208\032\0020\fH\026J\020\0205\032\0020\f2\006\0209\032\0020\035H\026J\030\0205\032\0020\f2\006\0209\032\0020\0352\006\0207\032\0020\fH\026J\020\020:\032\0020\f2\006\020;\032\0020\035H\026J\030\020:\032\0020\f2\006\020;\032\0020\0352\006\0207\032\0020\fH\026J\b\020<\032\0020=H\026J\b\020>\032\0020#H\026J\006\020?\032\0020\035J\b\020@\032\0020\031H\026J\b\020A\032\0020\001H\026J\030\020B\032\0020#2\006\020\032\032\0020\f2\006\0209\032\0020\035H\026J(\020B\032\0020#2\006\020\032\032\0020\f2\006\0209\032\0020\0352\006\020C\032\0020/2\006\020\033\032\0020/H\026J\020\020D\032\0020/2\006\020E\032\0020FH\026J\020\020D\032\0020/2\006\020E\032\0020GH\026J \020D\032\0020/2\006\020E\032\0020G2\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020D\032\0020\f2\006\020E\032\0020\0002\006\020\033\032\0020\fH\026J\020\020H\032\0020\f2\006\020E\032\0020IH\026J\022\020J\032\0020K2\b\b\002\020L\032\0020KH\007J\b\020M\032\0020)H\026J\b\020N\032\0020GH\026J\020\020N\032\0020G2\006\020\033\032\0020\fH\026J\b\020O\032\0020\035H\026J\020\020O\032\0020\0352\006\020\033\032\0020\fH\026J\b\020P\032\0020\fH\026J\016\020Q\032\0020\0002\006\020R\032\0020=J\026\020Q\032\0020\0002\006\020R\032\0020=2\006\020\033\032\0020\fJ \020Q\032\0020\0222\006\020R\032\0020=2\006\020\033\032\0020\f2\006\020S\032\0020#H\002J\020\020T\032\0020\0222\006\020E\032\0020GH\026J\030\020T\032\0020\0222\006\020E\032\0020\0002\006\020\033\032\0020\fH\026J\b\020U\032\0020\fH\026J\b\020V\032\0020/H\026J\b\020W\032\0020/H\026J\b\020X\032\0020\fH\026J\b\020Y\032\0020\fH\026J\b\020Z\032\0020[H\026J\b\020\\\032\0020[H\026J\020\020]\032\0020\0372\006\020^\032\0020_H\026J\030\020]\032\0020\0372\006\020\033\032\0020\f2\006\020^\032\0020_H\026J\022\020`\032\0020K2\b\b\002\020L\032\0020KH\007J\b\020a\032\0020\037H\026J\020\020a\032\0020\0372\006\020\033\032\0020\fH\026J\b\020b\032\0020/H\026J\n\020c\032\004\030\0010\037H\026J\b\020d\032\0020\037H\026J\020\020d\032\0020\0372\006\020e\032\0020\fH\026J\020\020f\032\0020#2\006\020\033\032\0020\fH\026J\020\020g\032\0020\0222\006\020\033\032\0020\fH\026J\020\020h\032\0020/2\006\020i\032\0020jH\026J\006\020k\032\0020\035J\006\020l\032\0020\035J\006\020m\032\0020\035J\r\020\r\032\0020\fH\007?\006\002\bnJ\020\020o\032\0020\0222\006\020\033\032\0020\fH\026J\006\020p\032\0020\035J\016\020p\032\0020\0352\006\020\033\032\0020/J\b\020q\032\0020rH\026J\b\020s\032\0020\037H\026J\025\020t\032\0020\n2\006\020u\032\0020/H\000?\006\002\bvJ\020\020w\032\0020/2\006\020x\032\0020FH\026J\020\020w\032\0020\0002\006\020x\032\0020GH\026J \020w\032\0020\0002\006\020x\032\0020G2\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020w\032\0020\0222\006\020x\032\0020\0002\006\020\033\032\0020\fH\026J\020\020w\032\0020\0002\006\020y\032\0020\035H\026J \020w\032\0020\0002\006\020y\032\0020\0352\006\020\032\032\0020/2\006\020\033\032\0020/H\026J\030\020w\032\0020\0002\006\020x\032\0020z2\006\020\033\032\0020\fH\026J\020\020{\032\0020\f2\006\020x\032\0020zH\026J\020\020|\032\0020\0002\006\0206\032\0020/H\026J\020\020}\032\0020\0002\006\020~\032\0020\fH\026J\020\020\032\0020\0002\006\020~\032\0020\fH\026J\022\020?\001\032\0020\0002\007\020?\001\032\0020/H\026J\022\020?\001\032\0020\0002\007\020?\001\032\0020/H\026J\021\020?\001\032\0020\0002\006\020~\032\0020\fH\026J\021\020?\001\032\0020\0002\006\020~\032\0020\fH\026J\022\020?\001\032\0020\0002\007\020?\001\032\0020/H\026J\022\020?\001\032\0020\0002\007\020?\001\032\0020/H\026J\032\020?\001\032\0020\0002\007\020?\001\032\0020\0372\006\020^\032\0020_H\026J,\020?\001\032\0020\0002\007\020?\001\032\0020\0372\007\020?\001\032\0020/2\007\020?\001\032\0020/2\006\020^\032\0020_H\026J\033\020?\001\032\0020\0002\006\020\030\032\0020\0312\b\b\002\020\033\032\0020\fH\007J\022\020?\001\032\0020\0002\007\020?\001\032\0020\037H\026J$\020?\001\032\0020\0002\007\020?\001\032\0020\0372\007\020?\001\032\0020/2\007\020?\001\032\0020/H\026J\022\020?\001\032\0020\0002\007\020?\001\032\0020/H\026R\024\020\006\032\0020\0008VX?\004?\006\006\032\004\b\007\020\bR\024\020\t\032\004\030\0010\n8\000@\000X?\016?\006\002\n\000R&\020\r\032\0020\f2\006\020\013\032\0020\f8G@@X?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020?\006?\001"}, d2={"Lokio/Buffer;", "Lokio/BufferedSource;", "Lokio/BufferedSink;", "", "Ljava/nio/channels/ByteChannel;", "()V", "buffer", "getBuffer", "()Lokio/Buffer;", "head", "Lokio/Segment;", "<set-?>", "", "size", "()J", "setSize$okio", "(J)V", "clear", "", "clone", "close", "completeSegmentByteCount", "copy", "copyTo", "out", "Ljava/io/OutputStream;", "offset", "byteCount", "digest", "Lokio/ByteString;", "algorithm", "", "emit", "emitCompleteSegments", "equals", "", "other", "", "exhausted", "flush", "get", "", "pos", "getByte", "index", "-deprecated_getByte", "hashCode", "", "hmac", "key", "hmacSha1", "hmacSha256", "hmacSha512", "indexOf", "b", "fromIndex", "toIndex", "bytes", "indexOfElement", "targetBytes", "inputStream", "Ljava/io/InputStream;", "isOpen", "md5", "outputStream", "peek", "rangeEquals", "bytesOffset", "read", "sink", "Ljava/nio/ByteBuffer;", "", "readAll", "Lokio/Sink;", "readAndWriteUnsafe", "Lokio/Buffer$UnsafeCursor;", "unsafeCursor", "readByte", "readByteArray", "readByteString", "readDecimalLong", "readFrom", "input", "forever", "readFully", "readHexadecimalUnsignedLong", "readInt", "readIntLe", "readLong", "readLongLe", "readShort", "", "readShortLe", "readString", "charset", "Ljava/nio/charset/Charset;", "readUnsafe", "readUtf8", "readUtf8CodePoint", "readUtf8Line", "readUtf8LineStrict", "limit", "request", "require", "select", "options", "Lokio/Options;", "sha1", "sha256", "sha512", "-deprecated_size", "skip", "snapshot", "timeout", "Lokio/Timeout;", "toString", "writableSegment", "minimumCapacity", "writableSegment$okio", "write", "source", "byteString", "Lokio/Source;", "writeAll", "writeByte", "writeDecimalLong", "v", "writeHexadecimalUnsignedLong", "writeInt", "i", "writeIntLe", "writeLong", "writeLongLe", "writeShort", "s", "writeShortLe", "writeString", "string", "beginIndex", "endIndex", "writeTo", "writeUtf8", "writeUtf8CodePoint", "codePoint", "UnsafeCursor", "okio"}, k=1, mv={1, 1, 16})
public final class Buffer
  implements BufferedSource, BufferedSink, Cloneable, ByteChannel
{
  public Segment head;
  private long size;
  
  public Buffer() {}
  
  private final ByteString digest(String paramString)
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
    Segment localSegment1 = this.head;
    if (localSegment1 != null)
    {
      localMessageDigest.update(localSegment1.data, localSegment1.pos, localSegment1.limit - localSegment1.pos);
      Segment localSegment2 = localSegment1.next;
      paramString = localSegment2;
      if (localSegment2 == null)
      {
        Intrinsics.throwNpe();
        paramString = localSegment2;
      }
      while (paramString != localSegment1)
      {
        localMessageDigest.update(paramString.data, paramString.pos, paramString.limit - paramString.pos);
        localSegment2 = paramString.next;
        paramString = localSegment2;
        if (localSegment2 == null)
        {
          Intrinsics.throwNpe();
          paramString = localSegment2;
        }
      }
    }
    paramString = localMessageDigest.digest();
    Intrinsics.checkExpressionValueIsNotNull(paramString, "messageDigest.digest()");
    return new ByteString(paramString);
  }
  
  private final ByteString hmac(String paramString, ByteString paramByteString)
  {
    try
    {
      Mac localMac = Mac.getInstance(paramString);
      Object localObject = new javax/crypto/spec/SecretKeySpec;
      ((SecretKeySpec)localObject).<init>(paramByteString.internalArray$okio(), paramString);
      localMac.init((Key)localObject);
      localObject = this.head;
      if (localObject != null)
      {
        localMac.update(((Segment)localObject).data, ((Segment)localObject).pos, ((Segment)localObject).limit - ((Segment)localObject).pos);
        paramByteString = ((Segment)localObject).next;
        paramString = paramByteString;
        if (paramByteString == null)
        {
          Intrinsics.throwNpe();
          paramString = paramByteString;
        }
        while (paramString != localObject)
        {
          localMac.update(paramString.data, paramString.pos, paramString.limit - paramString.pos);
          paramByteString = paramString.next;
          paramString = paramByteString;
          if (paramByteString == null)
          {
            Intrinsics.throwNpe();
            paramString = paramByteString;
          }
        }
      }
      paramString = localMac.doFinal();
      Intrinsics.checkExpressionValueIsNotNull(paramString, "mac.doFinal()");
      paramString = new ByteString(paramString);
      return paramString;
    }
    catch (InvalidKeyException paramString)
    {
      throw ((Throwable)new IllegalArgumentException((Throwable)paramString));
    }
  }
  
  private final void readFrom(InputStream paramInputStream, long paramLong, boolean paramBoolean)
    throws IOException
  {
    for (;;)
    {
      if ((paramLong <= 0L) && (!paramBoolean)) {
        return;
      }
      Segment localSegment = writableSegment$okio(1);
      int i = (int)Math.min(paramLong, 8192 - localSegment.limit);
      i = paramInputStream.read(localSegment.data, localSegment.limit, i);
      if (i == -1)
      {
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        if (paramBoolean) {
          return;
        }
        throw ((Throwable)new EOFException());
      }
      localSegment.limit += i;
      long l1 = this.size;
      long l2 = i;
      this.size = (l1 + l2);
      paramLong -= l2;
    }
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to operator function", replaceWith=@ReplaceWith(expression="this[index]", imports={}))
  public final byte -deprecated_getByte(long paramLong)
  {
    return getByte(paramLong);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
  public final long -deprecated_size()
  {
    return this.size;
  }
  
  public Buffer buffer()
  {
    return this;
  }
  
  public final void clear()
  {
    skip(size());
  }
  
  public Buffer clone()
  {
    return copy();
  }
  
  public void close() {}
  
  public final long completeSegmentByteCount()
  {
    long l1 = size();
    long l2 = 0L;
    if (l1 != 0L)
    {
      Segment localSegment = this.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      localSegment = localSegment.prev;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      l2 = l1;
      if (localSegment.limit < 8192)
      {
        l2 = l1;
        if (localSegment.owner) {
          l2 = l1 - (localSegment.limit - localSegment.pos);
        }
      }
    }
    return l2;
  }
  
  public final Buffer copy()
  {
    Buffer localBuffer = new Buffer();
    if (size() != 0L)
    {
      Segment localSegment1 = this.head;
      if (localSegment1 == null) {
        Intrinsics.throwNpe();
      }
      Segment localSegment2 = localSegment1.sharedCopy();
      localBuffer.head = localSegment2;
      localSegment2.prev = localSegment2;
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
      localBuffer.setSize$okio(size());
    }
    return localBuffer;
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream)
    throws IOException
  {
    return copyTo$default(this, paramOutputStream, 0L, 0L, 6, null);
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    return copyTo$default(this, paramOutputStream, paramLong, 0L, 4, null);
  }
  
  public final Buffer copyTo(OutputStream paramOutputStream, long paramLong1, long paramLong2)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramOutputStream, "out");
    _Util.checkOffsetAndCount(this.size, paramLong1, paramLong2);
    if (paramLong2 == 0L) {
      return this;
    }
    Segment localSegment2;
    long l1;
    long l2;
    for (Segment localSegment1 = this.head;; localSegment1 = localSegment1.next)
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
      int i = (int)(localSegment2.pos + l1);
      int j = (int)Math.min(localSegment2.limit - i, l2);
      paramOutputStream.write(localSegment2.data, i, j);
      l2 -= j;
      localSegment2 = localSegment2.next;
      l1 = 0L;
    }
    return this;
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "out");
    return copyTo(paramBuffer, paramLong, this.size - paramLong);
  }
  
  public final Buffer copyTo(Buffer paramBuffer, long paramLong1, long paramLong2)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "out");
    _Util.checkOffsetAndCount(size(), paramLong1, paramLong2);
    if (paramLong2 != 0L)
    {
      paramBuffer.setSize$okio(paramBuffer.size() + paramLong2);
      Segment localSegment2;
      long l1;
      long l2;
      for (Segment localSegment1 = this.head;; localSegment1 = localSegment1.next)
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
        Segment localSegment3 = paramBuffer.head;
        if (localSegment3 == null)
        {
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
          paramBuffer.head = localSegment1.next;
        }
        else
        {
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
    }
    return this;
  }
  
  public Buffer emit()
  {
    return this;
  }
  
  public Buffer emitCompleteSegments()
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if (this == paramObject) {}
    for (;;)
    {
      bool = true;
      break;
      if (!(paramObject instanceof Buffer)) {
        break;
      }
      long l1 = size();
      paramObject = (Buffer)paramObject;
      if (l1 != paramObject.size()) {
        break;
      }
      if (size() != 0L)
      {
        Object localObject1 = this.head;
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
        while (l1 < size())
        {
          long l2 = Math.min(((Segment)localObject1).limit - i, ((Segment)localObject2).limit - j);
          long l3 = 0L;
          int k = i;
          while (l3 < l2)
          {
            if (localObject1.data[k] != localObject2.data[j]) {
              return bool;
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
      }
    }
    return bool;
  }
  
  public boolean exhausted()
  {
    boolean bool;
    if (this.size == 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void flush() {}
  
  public Buffer getBuffer()
  {
    return this;
  }
  
  public final byte getByte(long paramLong)
  {
    _Util.checkOffsetAndCount(size(), paramLong, 1L);
    Segment localSegment = this.head;
    byte b1;
    byte b2;
    if (localSegment != null)
    {
      long l1;
      if (size() - paramLong < paramLong)
      {
        for (l1 = size(); l1 > paramLong; l1 -= localSegment.limit - localSegment.pos)
        {
          localSegment = localSegment.prev;
          if (localSegment == null) {
            Intrinsics.throwNpe();
          }
        }
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        b1 = localSegment.data[((int)(localSegment.pos + paramLong - l1))];
        b2 = b1;
      }
      else
      {
        long l2;
        for (l1 = 0L;; l1 = l2)
        {
          l2 = localSegment.limit - localSegment.pos + l1;
          if (l2 > paramLong)
          {
            if (localSegment == null) {
              Intrinsics.throwNpe();
            }
            b1 = localSegment.data[((int)(localSegment.pos + paramLong - l1))];
            b2 = b1;
            break;
          }
          localSegment = localSegment.next;
          if (localSegment == null) {
            Intrinsics.throwNpe();
          }
        }
      }
    }
    else
    {
      localSegment = (Segment)null;
      Intrinsics.throwNpe();
      b1 = localSegment.data[((int)(localSegment.pos + paramLong + 1L))];
      b2 = b1;
    }
    return b2;
  }
  
  public int hashCode()
  {
    Object localObject = this.head;
    int m;
    if (localObject != null)
    {
      int i = 1;
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
      } while (localSegment != this.head);
    }
    else
    {
      m = 0;
    }
    return m;
  }
  
  public final ByteString hmacSha1(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "key");
    return hmac("HmacSHA1", paramByteString);
  }
  
  public final ByteString hmacSha256(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "key");
    return hmac("HmacSHA256", paramByteString);
  }
  
  public final ByteString hmacSha512(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "key");
    return hmac("HmacSHA512", paramByteString);
  }
  
  public long indexOf(byte paramByte)
  {
    return indexOf(paramByte, 0L, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong)
  {
    return indexOf(paramByte, paramLong, Long.MAX_VALUE);
  }
  
  public long indexOf(byte paramByte, long paramLong1, long paramLong2)
  {
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
      if (paramLong2 > size()) {
        l2 = size();
      }
      long l3 = -1L;
      if (paramLong1 == l2)
      {
        l1 = l3;
      }
      else
      {
        Object localObject1 = this.head;
        if (localObject1 != null)
        {
          paramLong2 = l1;
          localObject2 = localObject1;
          int j;
          if (size() - paramLong1 < paramLong1)
          {
            paramLong2 = size();
            localObject2 = localObject1;
            while (paramLong2 > paramLong1)
            {
              localObject2 = ((Segment)localObject2).prev;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              paramLong2 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
            }
            l1 = l3;
            if (localObject2 != null)
            {
              long l4 = paramLong1;
              paramLong1 = paramLong2;
              for (;;)
              {
                l1 = l3;
                if (paramLong1 >= l2) {
                  break;
                }
                localObject1 = ((Segment)localObject2).data;
                j = (int)Math.min(((Segment)localObject2).limit, ((Segment)localObject2).pos + l2 - paramLong1);
                for (i = (int)(((Segment)localObject2).pos + l4 - paramLong1); i < j; i++) {
                  if (localObject1[i] == paramByte)
                  {
                    l1 = i - ((Segment)localObject2).pos + paramLong1;
                    break label486;
                  }
                }
                paramLong1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                l4 = paramLong1;
              }
            }
          }
          else
          {
            for (;;)
            {
              l1 = ((Segment)localObject2).limit - ((Segment)localObject2).pos + paramLong2;
              if (l1 > paramLong1)
              {
                l1 = l3;
                if (localObject2 == null) {
                  break label486;
                }
                for (;;)
                {
                  l1 = l3;
                  if (paramLong2 >= l2) {
                    break label486;
                  }
                  localObject1 = ((Segment)localObject2).data;
                  j = (int)Math.min(((Segment)localObject2).limit, ((Segment)localObject2).pos + l2 - paramLong2);
                  for (i = (int)(((Segment)localObject2).pos + paramLong1 - paramLong2);; i++)
                  {
                    if (i >= j) {
                      break label416;
                    }
                    if (localObject1[i] == paramByte)
                    {
                      paramLong1 = paramLong2;
                      break;
                    }
                  }
                  label416:
                  paramLong2 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                  localObject2 = ((Segment)localObject2).next;
                  if (localObject2 == null) {
                    Intrinsics.throwNpe();
                  }
                  paramLong1 = paramLong2;
                }
              }
              localObject2 = ((Segment)localObject2).next;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              paramLong2 = l1;
            }
          }
        }
        else
        {
          localObject2 = (Segment)null;
          l1 = l3;
        }
      }
      label486:
      return l1;
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("size=");
    ((StringBuilder)localObject2).append(size());
    ((StringBuilder)localObject2).append(" fromIndex=");
    ((StringBuilder)localObject2).append(paramLong1);
    ((StringBuilder)localObject2).append(" toIndex=");
    ((StringBuilder)localObject2).append(paramLong2);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject2).toString().toString()));
  }
  
  public long indexOf(ByteString paramByteString)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    return indexOf(paramByteString, 0L);
  }
  
  public long indexOf(ByteString paramByteString, long paramLong)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    int i;
    if (paramByteString.size() > 0) {
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
        Object localObject1 = this.head;
        if (localObject1 != null)
        {
          Object localObject2 = localObject1;
          int j;
          int k;
          long l2;
          long l3;
          int m;
          if (size() - paramLong < paramLong)
          {
            l1 = size();
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
              j = localObject1[0];
              k = paramByteString.size();
              l2 = size() - k + 1L;
              while (l1 < l2)
              {
                paramByteString = ((Segment)localObject2).data;
                i = ((Segment)localObject2).limit;
                l3 = ((Segment)localObject2).pos;
                m = (int)Math.min(i, l3 + l2 - l1);
                for (i = (int)(((Segment)localObject2).pos + paramLong - l1); i < m; i++) {
                  if ((paramByteString[i] == j) && (BufferKt.rangeEquals((Segment)localObject2, i + 1, (byte[])localObject1, 1, k)))
                  {
                    paramLong = i - ((Segment)localObject2).pos + l1;
                    break label560;
                  }
                }
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                paramLong = l1;
              }
            }
          }
          else
          {
            for (;;)
            {
              l2 = ((Segment)localObject2).limit - ((Segment)localObject2).pos + l1;
              if (l2 > paramLong)
              {
                if (localObject2 == null) {
                  break;
                }
                localObject1 = paramByteString.internalArray$okio();
                j = localObject1[0];
                k = paramByteString.size();
                l2 = size() - k + 1L;
                while (l1 < l2)
                {
                  paramByteString = ((Segment)localObject2).data;
                  i = ((Segment)localObject2).limit;
                  l3 = ((Segment)localObject2).pos;
                  m = (int)Math.min(i, l3 + l2 - l1);
                  for (i = (int)(((Segment)localObject2).pos + paramLong - l1); i < m; i++) {
                    if ((paramByteString[i] == j) && (BufferKt.rangeEquals((Segment)localObject2, i + 1, (byte[])localObject1, 1, k)))
                    {
                      paramLong = i - ((Segment)localObject2).pos + l1;
                      break label560;
                    }
                  }
                  l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                  localObject2 = ((Segment)localObject2).next;
                  if (localObject2 == null) {
                    Intrinsics.throwNpe();
                  }
                  paramLong = l1;
                }
              }
              localObject2 = ((Segment)localObject2).next;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              l1 = l2;
            }
          }
        }
        else
        {
          paramByteString = (Segment)null;
        }
        paramLong = -1L;
        label560:
        return paramLong;
      }
      paramByteString = new StringBuilder();
      paramByteString.append("fromIndex < 0: ");
      paramByteString.append(paramLong);
      throw ((Throwable)new IllegalArgumentException(paramByteString.toString().toString()));
    }
    throw ((Throwable)new IllegalArgumentException("bytes is empty".toString()));
  }
  
  public long indexOfElement(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "targetBytes");
    return indexOfElement(paramByteString, 0L);
  }
  
  public long indexOfElement(ByteString paramByteString, long paramLong)
  {
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
      Object localObject1 = this.head;
      long l2 = -1L;
      long l3;
      if (localObject1 != null)
      {
        Object localObject2 = localObject1;
        int j;
        int k;
        int m;
        int n;
        int i1;
        if (size() - paramLong < paramLong)
        {
          l1 = size();
          localObject2 = localObject1;
          while (l1 > paramLong)
          {
            localObject2 = ((Segment)localObject2).prev;
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            l1 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
          }
          l3 = l2;
          if (localObject2 != null)
          {
            if (paramByteString.size() == 2)
            {
              j = paramByteString.getByte(0);
              k = paramByteString.getByte(1);
              for (;;)
              {
                l3 = l2;
                if (l1 >= size()) {
                  break;
                }
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
                  l3 = i - m + paramLong;
                  break label807;
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
              l3 = l2;
              if (l1 >= size()) {
                break label807;
              }
              localObject1 = ((Segment)localObject2).data;
              i = (int)(((Segment)localObject2).pos + paramLong - l1);
              n = ((Segment)localObject2).limit;
              for (;;)
              {
                if (i >= n) {
                  break label409;
                }
                j = localObject1[i];
                k = paramByteString.length;
                for (m = 0;; m++)
                {
                  if (m >= k) {
                    break label403;
                  }
                  if (j == paramByteString[m])
                  {
                    m = ((Segment)localObject2).pos;
                    paramLong = l1;
                    break;
                  }
                }
                label403:
                i++;
              }
              label409:
              l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
              localObject2 = ((Segment)localObject2).next;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              paramLong = l1;
            }
          }
        }
        else
        {
          for (;;)
          {
            l3 = ((Segment)localObject2).limit - ((Segment)localObject2).pos + l1;
            if (l3 > paramLong)
            {
              l3 = l2;
              if (localObject2 == null) {
                break label807;
              }
              if (paramByteString.size() == 2)
              {
                k = paramByteString.getByte(0);
                j = paramByteString.getByte(1);
                for (;;)
                {
                  l3 = l2;
                  if (l1 >= size()) {
                    break label807;
                  }
                  localObject1 = ((Segment)localObject2).data;
                  m = (int)(((Segment)localObject2).pos + paramLong - l1);
                  n = ((Segment)localObject2).limit;
                  for (;;)
                  {
                    if (m >= n) {
                      break label601;
                    }
                    i1 = localObject1[m];
                    paramLong = l1;
                    paramByteString = (ByteString)localObject2;
                    i = m;
                    if (i1 == k) {
                      break;
                    }
                    if (i1 == j)
                    {
                      paramLong = l1;
                      paramByteString = (ByteString)localObject2;
                      i = m;
                      break;
                    }
                    m++;
                  }
                  label601:
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
                l3 = l2;
                if (l1 >= size()) {
                  break label807;
                }
                localObject1 = ((Segment)localObject2).data;
                i = (int)(((Segment)localObject2).pos + paramLong - l1);
                n = ((Segment)localObject2).limit;
                for (;;)
                {
                  if (i >= n) {
                    break label738;
                  }
                  k = localObject1[i];
                  j = paramByteString.length;
                  for (m = 0;; m++)
                  {
                    if (m >= j) {
                      break label732;
                    }
                    if (k == paramByteString[m]) {
                      break;
                    }
                  }
                  label732:
                  i++;
                }
                label738:
                l1 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
                localObject2 = ((Segment)localObject2).next;
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                paramLong = l1;
              }
            }
            localObject2 = ((Segment)localObject2).next;
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            l1 = l3;
          }
        }
      }
      else
      {
        paramByteString = (Segment)null;
        l3 = l2;
      }
      label807:
      return l3;
    }
    paramByteString = new StringBuilder();
    paramByteString.append("fromIndex < 0: ");
    paramByteString.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramByteString.toString().toString()));
  }
  
  public InputStream inputStream()
  {
    (InputStream)new InputStream()
    {
      public int available()
      {
        return (int)Math.min(this.this$0.size(), Integer.MAX_VALUE);
      }
      
      public void close() {}
      
      public int read()
      {
        int i;
        if (this.this$0.size() > 0L) {
          i = this.this$0.readByte() & 0xFF;
        } else {
          i = -1;
        }
        return i;
      }
      
      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousArrayOfByte, "sink");
        return this.this$0.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.this$0);
        localStringBuilder.append(".inputStream()");
        return localStringBuilder.toString();
      }
    };
  }
  
  public boolean isOpen()
  {
    return true;
  }
  
  public final ByteString md5()
  {
    return digest("MD5");
  }
  
  public OutputStream outputStream()
  {
    (OutputStream)new OutputStream()
    {
      public void close() {}
      
      public void flush() {}
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.this$0);
        localStringBuilder.append(".outputStream()");
        return localStringBuilder.toString();
      }
      
      public void write(int paramAnonymousInt)
      {
        this.this$0.writeByte(paramAnonymousInt);
      }
      
      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousArrayOfByte, "data");
        this.this$0.write(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
      }
    };
  }
  
  public BufferedSource peek()
  {
    return Okio.buffer((Source)new PeekSource((BufferedSource)this));
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }
  
  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "bytes");
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramLong >= 0L)
    {
      bool2 = bool1;
      if (paramInt1 >= 0)
      {
        bool2 = bool1;
        if (paramInt2 >= 0)
        {
          bool2 = bool1;
          if (size() - paramLong >= paramInt2) {
            if (paramByteString.size() - paramInt1 < paramInt2)
            {
              bool2 = bool1;
            }
            else
            {
              for (int i = 0; i < paramInt2; i++) {
                if (getByte(i + paramLong) != paramByteString.getByte(paramInt1 + i))
                {
                  bool2 = bool1;
                  break label121;
                }
              }
              bool2 = true;
            }
          }
        }
      }
    }
    label121:
    return bool2;
  }
  
  public int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramByteBuffer, "sink");
    Segment localSegment = this.head;
    if (localSegment != null)
    {
      int i = Math.min(paramByteBuffer.remaining(), localSegment.limit - localSegment.pos);
      paramByteBuffer.put(localSegment.data, localSegment.pos, i);
      localSegment.pos += i;
      this.size -= i;
      if (localSegment.pos == localSegment.limit)
      {
        this.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
      }
      return i;
    }
    return -1;
  }
  
  public int read(byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    _Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    Segment localSegment = this.head;
    if (localSegment != null)
    {
      paramInt2 = Math.min(paramInt2, localSegment.limit - localSegment.pos);
      ArraysKt.copyInto(localSegment.data, paramArrayOfByte, paramInt1, localSegment.pos, localSegment.pos + paramInt2);
      localSegment.pos += paramInt2;
      setSize$okio(size() - paramInt2);
      paramInt1 = paramInt2;
      if (localSegment.pos == localSegment.limit)
      {
        this.head = localSegment.pop();
        SegmentPool.INSTANCE.recycle(localSegment);
        paramInt1 = paramInt2;
      }
    }
    else
    {
      paramInt1 = -1;
    }
    return paramInt1;
  }
  
  public long read(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "sink");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() == 0L)
      {
        paramLong = -1L;
      }
      else
      {
        long l = paramLong;
        if (paramLong > size()) {
          l = size();
        }
        paramBuffer.write(this, l);
        paramLong = l;
      }
      return paramLong;
    }
    paramBuffer = new StringBuilder();
    paramBuffer.append("byteCount < 0: ");
    paramBuffer.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramBuffer.toString().toString()));
  }
  
  public long readAll(Sink paramSink)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramSink, "sink");
    long l = size();
    if (l > 0L) {
      paramSink.write(this, l);
    }
    return l;
  }
  
  public final UnsafeCursor readAndWriteUnsafe()
  {
    return readAndWriteUnsafe$default(this, null, 1, null);
  }
  
  public final UnsafeCursor readAndWriteUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    Intrinsics.checkParameterIsNotNull(paramUnsafeCursor, "unsafeCursor");
    int i;
    if (paramUnsafeCursor.buffer == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramUnsafeCursor.buffer = ((Buffer)this);
      paramUnsafeCursor.readWrite = true;
      return paramUnsafeCursor;
    }
    throw ((Throwable)new IllegalStateException("already attached to a buffer".toString()));
  }
  
  public byte readByte()
    throws EOFException
  {
    if (size() != 0L)
    {
      Segment localSegment = this.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      byte[] arrayOfByte = localSegment.data;
      int k = i + 1;
      byte b = arrayOfByte[i];
      setSize$okio(size() - 1L);
      if (k == j)
      {
        this.head = localSegment.pop();
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
  
  public byte[] readByteArray()
  {
    return readByteArray(size());
  }
  
  public byte[] readByteArray(long paramLong)
    throws EOFException
  {
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() >= paramLong)
      {
        localObject = new byte[(int)paramLong];
        readFully((byte[])localObject);
        return localObject;
      }
      throw ((Throwable)new EOFException());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("byteCount: ");
    ((StringBuilder)localObject).append(paramLong);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public ByteString readByteString()
  {
    return readByteString(size());
  }
  
  public ByteString readByteString(long paramLong)
    throws EOFException
  {
    int i;
    if ((paramLong >= 0L) && (paramLong <= Integer.MAX_VALUE)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (size() >= paramLong)
      {
        if (paramLong >= '?')
        {
          localObject = snapshot((int)paramLong);
          skip(paramLong);
        }
        else
        {
          localObject = new ByteString(readByteArray(paramLong));
        }
        return localObject;
      }
      throw ((Throwable)new EOFException());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("byteCount: ");
    ((StringBuilder)localObject).append(paramLong);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public long readDecimalLong()
    throws EOFException
  {
    long l1 = size();
    long l2 = 0L;
    if (l1 != 0L)
    {
      long l3 = -7L;
      boolean bool1 = false;
      int i = 0;
      int j = i;
      int n;
      boolean bool2;
      label261:
      do
      {
        Object localObject1 = this.head;
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        Object localObject2 = ((Segment)localObject1).data;
        int k = ((Segment)localObject1).pos;
        int m = ((Segment)localObject1).limit;
        n = i;
        bool2 = bool1;
        l1 = l2;
        l2 = l3;
        while (k < m)
        {
          int i1 = localObject2[k];
          i = (byte)48;
          if ((i1 >= i) && (i1 <= (byte)57))
          {
            i -= i1;
            bool1 = l1 < -922337203685477580L;
            if ((!bool1) && ((bool1) || (i >= l2)))
            {
              l1 = l1 * 10L + i;
            }
            else
            {
              localObject1 = new Buffer().writeDecimalLong(l1).writeByte(i1);
              if (n == 0) {
                ((Buffer)localObject1).readByte();
              }
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("Number too large: ");
              ((StringBuilder)localObject2).append(((Buffer)localObject1).readUtf8());
              throw ((Throwable)new NumberFormatException(((StringBuilder)localObject2).toString()));
            }
          }
          else
          {
            if ((i1 != (byte)45) || (bool2)) {
              break label261;
            }
            l2 -= 1L;
            n = 1;
          }
          k++;
          bool2++;
          continue;
          if (bool2)
          {
            j = 1;
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Expected leading [0-9] or '-' character but was 0x");
            ((StringBuilder)localObject1).append(_Util.toHexString(i1));
            throw ((Throwable)new NumberFormatException(((StringBuilder)localObject1).toString()));
          }
        }
        if (k == m)
        {
          this.head = ((Segment)localObject1).pop();
          SegmentPool.INSTANCE.recycle((Segment)localObject1);
        }
        else
        {
          ((Segment)localObject1).pos = k;
        }
        if (j != 0) {
          break;
        }
        l3 = l2;
        l2 = l1;
        bool1 = bool2;
        i = n;
      } while (this.head != null);
      setSize$okio(size() - bool2);
      if (n == 0) {
        l1 = -l1;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public final Buffer readFrom(InputStream paramInputStream)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramInputStream, "input");
    readFrom(paramInputStream, Long.MAX_VALUE, true);
    return this;
  }
  
  public final Buffer readFrom(InputStream paramInputStream, long paramLong)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramInputStream, "input");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      readFrom(paramInputStream, paramLong, false);
      return this;
    }
    paramInputStream = new StringBuilder();
    paramInputStream.append("byteCount < 0: ");
    paramInputStream.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramInputStream.toString().toString()));
  }
  
  public void readFully(Buffer paramBuffer, long paramLong)
    throws EOFException
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "sink");
    if (size() >= paramLong)
    {
      paramBuffer.write(this, paramLong);
      return;
    }
    paramBuffer.write(this, size());
    throw ((Throwable)new EOFException());
  }
  
  public void readFully(byte[] paramArrayOfByte)
    throws EOFException
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "sink");
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = read(paramArrayOfByte, i, paramArrayOfByte.length - i);
      if (j != -1) {
        i += j;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public long readHexadecimalUnsignedLong()
    throws EOFException
  {
    if (size() != 0L)
    {
      int i = 0;
      int j = 0;
      long l1 = 0L;
      long l2;
      int n;
      label242:
      label298:
      do
      {
        Object localObject1 = this.head;
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        Object localObject2 = ((Segment)localObject1).data;
        int k = ((Segment)localObject1).pos;
        int m = ((Segment)localObject1).limit;
        l2 = l1;
        int i1;
        int i2;
        for (n = i;; n++)
        {
          i1 = j;
          if (k >= m) {
            break label298;
          }
          i2 = localObject2[k];
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
                break label242;
              }
            }
          }
          if ((0xF000000000000000 & l2) != 0L) {
            break;
          }
          l2 = l2 << 4 | i;
          k++;
        }
        localObject2 = new Buffer().writeHexadecimalUnsignedLong(l2).writeByte(i2);
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Number too large: ");
        ((StringBuilder)localObject1).append(((Buffer)localObject2).readUtf8());
        throw ((Throwable)new NumberFormatException(((StringBuilder)localObject1).toString()));
        if (n != 0)
        {
          i1 = 1;
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Expected leading [0-9a-fA-F] character but was 0x");
          ((StringBuilder)localObject1).append(_Util.toHexString(i2));
          throw ((Throwable)new NumberFormatException(((StringBuilder)localObject1).toString()));
        }
        if (k == m)
        {
          this.head = ((Segment)localObject1).pop();
          SegmentPool.INSTANCE.recycle((Segment)localObject1);
        }
        else
        {
          ((Segment)localObject1).pos = k;
        }
        if (i1 != 0) {
          break;
        }
        i = n;
        j = i1;
        l1 = l2;
      } while (this.head != null);
      setSize$okio(size() - n);
      return l2;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int readInt()
    throws EOFException
  {
    if (size() >= 4L)
    {
      Segment localSegment = this.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      if (j - i < 4L)
      {
        j = (readByte() & 0xFF) << 24 | (readByte() & 0xFF) << 16 | (readByte() & 0xFF) << 8 | readByte() & 0xFF;
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int k = i + 1;
        i = arrayOfByte[i];
        int m = k + 1;
        k = arrayOfByte[k];
        int n = m + 1;
        int i1 = arrayOfByte[m];
        m = n + 1;
        n = arrayOfByte[n];
        setSize$okio(size() - 4L);
        if (m == j)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        else
        {
          localSegment.pos = m;
        }
        j = (i & 0xFF) << 24 | (k & 0xFF) << 16 | (i1 & 0xFF) << 8 | n & 0xFF;
      }
      return j;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int readIntLe()
    throws EOFException
  {
    return _Util.reverseBytes(readInt());
  }
  
  public long readLong()
    throws EOFException
  {
    if (size() >= 8L)
    {
      Segment localSegment = this.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      long l1;
      if (j - i < 8L)
      {
        l1 = (readInt() & 0xFFFFFFFF) << 32 | 0xFFFFFFFF & readInt();
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int k = i + 1;
        long l2 = arrayOfByte[i];
        i = k + 1;
        long l3 = arrayOfByte[k];
        k = i + 1;
        long l4 = arrayOfByte[i];
        i = k + 1;
        long l5 = arrayOfByte[k];
        k = i + 1;
        long l6 = arrayOfByte[i];
        i = k + 1;
        long l7 = arrayOfByte[k];
        k = i + 1;
        l1 = arrayOfByte[i];
        i = k + 1;
        long l8 = arrayOfByte[k];
        setSize$okio(size() - 8L);
        if (i == j)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        else
        {
          localSegment.pos = i;
        }
        l1 = (l2 & 0xFF) << 56 | (l3 & 0xFF) << 48 | (l4 & 0xFF) << 40 | (l5 & 0xFF) << 32 | (l6 & 0xFF) << 24 | (l7 & 0xFF) << 16 | (l1 & 0xFF) << 8 | l8 & 0xFF;
      }
      return l1;
    }
    throw ((Throwable)new EOFException());
  }
  
  public long readLongLe()
    throws EOFException
  {
    return _Util.reverseBytes(readLong());
  }
  
  public short readShort()
    throws EOFException
  {
    if (size() >= 2L)
    {
      Segment localSegment = this.head;
      if (localSegment == null) {
        Intrinsics.throwNpe();
      }
      int i = localSegment.pos;
      int j = localSegment.limit;
      int k;
      if (j - i < 2)
      {
        j = (short)((readByte() & 0xFF) << 8 | readByte() & 0xFF);
        k = j;
      }
      else
      {
        byte[] arrayOfByte = localSegment.data;
        int m = i + 1;
        i = arrayOfByte[i];
        int n = m + 1;
        m = arrayOfByte[m];
        setSize$okio(size() - 2L);
        if (n == j)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        else
        {
          localSegment.pos = n;
        }
        j = (short)((i & 0xFF) << 8 | m & 0xFF);
        k = j;
      }
      return k;
    }
    throw ((Throwable)new EOFException());
  }
  
  public short readShortLe()
    throws EOFException
  {
    return _Util.reverseBytes(readShort());
  }
  
  public String readString(long paramLong, Charset paramCharset)
    throws EOFException
  {
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    boolean bool = paramLong < 0L;
    int j;
    if ((!bool) && (paramLong <= Integer.MAX_VALUE)) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (this.size >= paramLong)
      {
        if (!bool) {
          return "";
        }
        Segment localSegment = this.head;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        if (localSegment.pos + paramLong > localSegment.limit) {
          return new String(readByteArray(paramLong), paramCharset);
        }
        byte[] arrayOfByte = localSegment.data;
        int i = localSegment.pos;
        j = (int)paramLong;
        paramCharset = new String(arrayOfByte, i, j, paramCharset);
        localSegment.pos += j;
        this.size -= paramLong;
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
        }
        return paramCharset;
      }
      throw ((Throwable)new EOFException());
    }
    paramCharset = new StringBuilder();
    paramCharset.append("byteCount: ");
    paramCharset.append(paramLong);
    throw ((Throwable)new IllegalArgumentException(paramCharset.toString().toString()));
  }
  
  public String readString(Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    return readString(this.size, paramCharset);
  }
  
  public final UnsafeCursor readUnsafe()
  {
    return readUnsafe$default(this, null, 1, null);
  }
  
  public final UnsafeCursor readUnsafe(UnsafeCursor paramUnsafeCursor)
  {
    Intrinsics.checkParameterIsNotNull(paramUnsafeCursor, "unsafeCursor");
    int i;
    if (paramUnsafeCursor.buffer == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramUnsafeCursor.buffer = ((Buffer)this);
      paramUnsafeCursor.readWrite = false;
      return paramUnsafeCursor;
    }
    throw ((Throwable)new IllegalStateException("already attached to a buffer".toString()));
  }
  
  public String readUtf8()
  {
    return readString(this.size, Charsets.UTF_8);
  }
  
  public String readUtf8(long paramLong)
    throws EOFException
  {
    return readString(paramLong, Charsets.UTF_8);
  }
  
  public int readUtf8CodePoint()
    throws EOFException
  {
    if (size() != 0L)
    {
      int i = getByte(0L);
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
          break label354;
        }
        m = i & 0x7;
        i1 = 4;
        n = 65536;
      }
      long l1 = size();
      long l2 = i1;
      if (l1 >= l2)
      {
        while (j < i1)
        {
          l1 = j;
          int i2 = getByte(l1);
          if ((i2 & 0xC0) == 128)
          {
            m = m << 6 | i2 & 0x3F;
            j++;
          }
          else
          {
            skip(l1);
            m = k;
            break label362;
          }
        }
        skip(l2);
        if (m > 1114111) {
          m = k;
        } else if ((55296 <= m) && (57343 >= m)) {
          m = k;
        } else if (m < n) {
          m = k;
        }
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("size < ");
        localStringBuilder.append(i1);
        localStringBuilder.append(": ");
        localStringBuilder.append(size());
        localStringBuilder.append(" (to read code point prefixed 0x");
        localStringBuilder.append(_Util.toHexString(i));
        localStringBuilder.append(')');
        throw ((Throwable)new EOFException(localStringBuilder.toString()));
        label354:
        skip(1L);
        m = k;
      }
      label362:
      return m;
    }
    throw ((Throwable)new EOFException());
  }
  
  public String readUtf8Line()
    throws EOFException
  {
    long l = indexOf((byte)10);
    String str;
    if (l != -1L) {
      str = BufferKt.readUtf8Line(this, l);
    } else if (size() != 0L) {
      str = readUtf8(size());
    } else {
      str = null;
    }
    return str;
  }
  
  public String readUtf8LineStrict()
    throws EOFException
  {
    return readUtf8LineStrict(Long.MAX_VALUE);
  }
  
  public String readUtf8LineStrict(long paramLong)
    throws EOFException
  {
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
      long l2 = indexOf(b, 0L, l1);
      if (l2 != -1L)
      {
        localObject = BufferKt.readUtf8Line(this, l2);
      }
      else
      {
        if ((l1 >= size()) || (getByte(l1 - 1L) != (byte)13) || (getByte(l1) != b)) {
          break label120;
        }
        localObject = BufferKt.readUtf8Line(this, l1);
      }
      return localObject;
      label120:
      Buffer localBuffer = new Buffer();
      l1 = size();
      copyTo(localBuffer, 0L, Math.min(32, l1));
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("\\n not found: limit=");
      ((StringBuilder)localObject).append(Math.min(size(), paramLong));
      ((StringBuilder)localObject).append(" content=");
      ((StringBuilder)localObject).append(localBuffer.readByteString().hex());
      ((StringBuilder)localObject).append('…');
      throw ((Throwable)new EOFException(((StringBuilder)localObject).toString()));
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("limit < 0: ");
    ((StringBuilder)localObject).append(paramLong);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public boolean request(long paramLong)
  {
    boolean bool;
    if (this.size >= paramLong) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void require(long paramLong)
    throws EOFException
  {
    if (this.size >= paramLong) {
      return;
    }
    throw ((Throwable)new EOFException());
  }
  
  public int select(Options paramOptions)
  {
    Intrinsics.checkParameterIsNotNull(paramOptions, "options");
    int i = BufferKt.selectPrefix$default(this, paramOptions, false, 2, null);
    if (i == -1) {
      i = -1;
    } else {
      skip(paramOptions.getByteStrings$okio()[i].size());
    }
    return i;
  }
  
  public final void setSize$okio(long paramLong)
  {
    this.size = paramLong;
  }
  
  public final ByteString sha1()
  {
    return digest("SHA-1");
  }
  
  public final ByteString sha256()
  {
    return digest("SHA-256");
  }
  
  public final ByteString sha512()
  {
    return digest("SHA-512");
  }
  
  public final long size()
  {
    return this.size;
  }
  
  public void skip(long paramLong)
    throws EOFException
  {
    while (paramLong > 0L)
    {
      Segment localSegment = this.head;
      if (localSegment != null)
      {
        int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
        long l1 = size();
        long l2 = i;
        setSize$okio(l1 - l2);
        l1 = paramLong - l2;
        localSegment.pos += i;
        paramLong = l1;
        if (localSegment.pos == localSegment.limit)
        {
          this.head = localSegment.pop();
          SegmentPool.INSTANCE.recycle(localSegment);
          paramLong = l1;
        }
      }
      else
      {
        throw ((Throwable)new EOFException());
      }
    }
  }
  
  public final ByteString snapshot()
  {
    int i;
    if (size() <= Integer.MAX_VALUE) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return snapshot((int)size());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("size > Int.MAX_VALUE: ");
    localStringBuilder.append(size());
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public final ByteString snapshot(int paramInt)
  {
    Object localObject;
    if (paramInt == 0)
    {
      localObject = ByteString.EMPTY;
    }
    else
    {
      _Util.checkOffsetAndCount(size(), 0L, paramInt);
      localObject = this.head;
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
      int[] arrayOfInt = new int[k * 2];
      localObject = this.head;
      k = 0;
      j = i;
      while (j < paramInt)
      {
        if (localObject == null) {
          Intrinsics.throwNpe();
        }
        arrayOfByte[k] = ((Segment)localObject).data;
        j += ((Segment)localObject).limit - ((Segment)localObject).pos;
        arrayOfInt[k] = Math.min(j, paramInt);
        arrayOfInt[(((Object[])arrayOfByte).length + k)] = ((Segment)localObject).pos;
        ((Segment)localObject).shared = true;
        k++;
        localObject = ((Segment)localObject).next;
      }
      localObject = (ByteString)new SegmentedByteString((byte[][])arrayOfByte, arrayOfInt);
    }
    return localObject;
  }
  
  public Timeout timeout()
  {
    return Timeout.NONE;
  }
  
  public String toString()
  {
    return snapshot().toString();
  }
  
  public final Segment writableSegment$okio(int paramInt)
  {
    int i = 1;
    if ((paramInt < 1) || (paramInt > 8192)) {
      i = 0;
    }
    if (i != 0)
    {
      Segment localSegment = this.head;
      if (localSegment == null)
      {
        localSegment = SegmentPool.INSTANCE.take();
        this.head = localSegment;
        localSegment.prev = localSegment;
        localSegment.next = localSegment;
      }
      else
      {
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        localSegment = localSegment.prev;
        if (localSegment == null) {
          Intrinsics.throwNpe();
        }
        if ((localSegment.limit + paramInt <= 8192) && (localSegment.owner)) {
          break label112;
        }
        localSegment = localSegment.push(SegmentPool.INSTANCE.take());
      }
      label112:
      return localSegment;
    }
    throw ((Throwable)new IllegalArgumentException("unexpected capacity".toString()));
  }
  
  public int write(ByteBuffer paramByteBuffer)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramByteBuffer, "source");
    int i = paramByteBuffer.remaining();
    int j = i;
    while (j > 0)
    {
      Segment localSegment = writableSegment$okio(1);
      int k = Math.min(j, 8192 - localSegment.limit);
      paramByteBuffer.get(localSegment.data, localSegment.limit, k);
      j -= k;
      localSegment.limit += k;
    }
    this.size += i;
    return i;
  }
  
  public Buffer write(ByteString paramByteString)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "byteString");
    paramByteString.write$okio(this, 0, paramByteString.size());
    return this;
  }
  
  public Buffer write(ByteString paramByteString, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramByteString, "byteString");
    paramByteString.write$okio(this, paramInt1, paramInt2);
    return this;
  }
  
  public Buffer write(Source paramSource, long paramLong)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramSource, "source");
    while (paramLong > 0L)
    {
      long l = paramSource.read(this, paramLong);
      if (l != -1L) {
        paramLong -= l;
      } else {
        throw ((Throwable)new EOFException());
      }
    }
    return this;
  }
  
  public Buffer write(byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "source");
    return write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public Buffer write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "source");
    long l1 = paramArrayOfByte.length;
    long l2 = paramInt1;
    long l3 = paramInt2;
    _Util.checkOffsetAndCount(l1, l2, l3);
    int i = paramInt2 + paramInt1;
    while (paramInt1 < i)
    {
      Segment localSegment = writableSegment$okio(1);
      int j = Math.min(i - paramInt1, 8192 - localSegment.limit);
      byte[] arrayOfByte = localSegment.data;
      int k = localSegment.limit;
      paramInt2 = paramInt1 + j;
      ArraysKt.copyInto(paramArrayOfByte, arrayOfByte, k, paramInt1, paramInt2);
      localSegment.limit += j;
      paramInt1 = paramInt2;
    }
    setSize$okio(size() + l3);
    return this;
  }
  
  public void write(Buffer paramBuffer, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "source");
    int i;
    if (paramBuffer != this) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      _Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
      while (paramLong > 0L)
      {
        Segment localSegment1 = paramBuffer.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        i = localSegment1.limit;
        localSegment1 = paramBuffer.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        if (paramLong < i - localSegment1.pos)
        {
          localSegment1 = this.head;
          if (localSegment1 != null)
          {
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
              localSegment2 = paramBuffer.head;
              if (localSegment2 == null) {
                Intrinsics.throwNpe();
              }
              localSegment2.writeTo(localSegment1, (int)paramLong);
              paramBuffer.setSize$okio(paramBuffer.size() - paramLong);
              setSize$okio(size() + paramLong);
              break;
            }
          }
          localSegment1 = paramBuffer.head;
          if (localSegment1 == null) {
            Intrinsics.throwNpe();
          }
          paramBuffer.head = localSegment1.split((int)paramLong);
        }
        localSegment1 = paramBuffer.head;
        if (localSegment1 == null) {
          Intrinsics.throwNpe();
        }
        long l = localSegment1.limit - localSegment1.pos;
        paramBuffer.head = localSegment1.pop();
        Segment localSegment2 = this.head;
        if (localSegment2 == null)
        {
          this.head = localSegment1;
          localSegment1.prev = localSegment1;
          localSegment1.next = localSegment1.prev;
        }
        else
        {
          if (localSegment2 == null) {
            Intrinsics.throwNpe();
          }
          localSegment2 = localSegment2.prev;
          if (localSegment2 == null) {
            Intrinsics.throwNpe();
          }
          localSegment2.push(localSegment1).compact();
        }
        paramBuffer.setSize$okio(paramBuffer.size() - l);
        setSize$okio(size() + l);
        paramLong -= l;
      }
      return;
    }
    throw ((Throwable)new IllegalArgumentException("source == this".toString()));
  }
  
  public long writeAll(Source paramSource)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramSource, "source");
    long l2;
    for (long l1 = 0L;; l1 += l2)
    {
      l2 = paramSource.read(this, '?');
      if (l2 == -1L) {
        return l1;
      }
    }
  }
  
  public Buffer writeByte(int paramInt)
  {
    Segment localSegment = writableSegment$okio(1);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    localSegment.limit = (i + 1);
    arrayOfByte[i] = ((byte)(byte)paramInt);
    setSize$okio(size() + 1L);
    return this;
  }
  
  public Buffer writeDecimalLong(long paramLong)
  {
    boolean bool = paramLong < 0L;
    Object localObject;
    if (!bool)
    {
      localObject = writeByte(48);
    }
    else
    {
      int j = 0;
      int k = 1;
      long l = paramLong;
      if (bool)
      {
        l = -paramLong;
        if (l < 0L) {
          localObject = writeUtf8("-9223372036854775808");
        } else {
          j = 1;
        }
      }
      else
      {
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
        localObject = writableSegment$okio(i);
        byte[] arrayOfByte = ((Segment)localObject).data;
        k = ((Segment)localObject).limit + i;
        while (l != 0L)
        {
          paramLong = 10;
          int m = (int)(l % paramLong);
          k--;
          arrayOfByte[k] = ((byte)BufferKt.getHEX_DIGIT_BYTES()[m]);
          l /= paramLong;
        }
        if (j != 0) {
          arrayOfByte[(k - 1)] = ((byte)(byte)45);
        }
        ((Segment)localObject).limit += i;
        setSize$okio(size() + i);
        localObject = this;
      }
    }
    return localObject;
  }
  
  public Buffer writeHexadecimalUnsignedLong(long paramLong)
  {
    Object localObject;
    if (paramLong == 0L)
    {
      localObject = writeByte(48);
    }
    else
    {
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
      localObject = writableSegment$okio(i);
      byte[] arrayOfByte = ((Segment)localObject).data;
      int j = ((Segment)localObject).limit + i - 1;
      int k = ((Segment)localObject).limit;
      while (j >= k)
      {
        arrayOfByte[j] = ((byte)BufferKt.getHEX_DIGIT_BYTES()[((int)(0xF & paramLong))]);
        paramLong >>>= 4;
        j--;
      }
      ((Segment)localObject).limit += i;
      setSize$okio(size() + i);
      localObject = this;
    }
    return localObject;
  }
  
  public Buffer writeInt(int paramInt)
  {
    Segment localSegment = writableSegment$okio(4);
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
    setSize$okio(size() + 4L);
    return this;
  }
  
  public Buffer writeIntLe(int paramInt)
  {
    return writeInt(_Util.reverseBytes(paramInt));
  }
  
  public Buffer writeLong(long paramLong)
  {
    Segment localSegment = writableSegment$okio(8);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 56 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 48 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 40 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 32 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 24 & 0xFF));
    i = j + 1;
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong >>> 16 & 0xFF));
    j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(int)(paramLong >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(int)(paramLong & 0xFF));
    localSegment.limit = (j + 1);
    setSize$okio(size() + 8L);
    return this;
  }
  
  public Buffer writeLongLe(long paramLong)
  {
    return writeLong(_Util.reverseBytes(paramLong));
  }
  
  public Buffer writeShort(int paramInt)
  {
    Segment localSegment = writableSegment$okio(2);
    byte[] arrayOfByte = localSegment.data;
    int i = localSegment.limit;
    int j = i + 1;
    arrayOfByte[i] = ((byte)(byte)(paramInt >>> 8 & 0xFF));
    arrayOfByte[j] = ((byte)(byte)(paramInt & 0xFF));
    localSegment.limit = (j + 1);
    setSize$okio(size() + 2L);
    return this;
  }
  
  public Buffer writeShortLe(int paramInt)
  {
    return writeShort(_Util.reverseBytes((short)paramInt));
  }
  
  public Buffer writeString(String paramString, int paramInt1, int paramInt2, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "string");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    int i = 1;
    int j;
    if (paramInt1 >= 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramInt2 >= paramInt1) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (paramInt2 <= paramString.length()) {
          j = i;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          if (Intrinsics.areEqual(paramCharset, Charsets.UTF_8)) {
            return writeUtf8(paramString, paramInt1, paramInt2);
          }
          paramString = paramString.substring(paramInt1, paramInt2);
          Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          if (paramString != null)
          {
            paramString = paramString.getBytes(paramCharset);
            Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).getBytes(charset)");
            return write(paramString, 0, paramString.length);
          }
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        paramCharset = new StringBuilder();
        paramCharset.append("endIndex > string.length: ");
        paramCharset.append(paramInt2);
        paramCharset.append(" > ");
        paramCharset.append(paramString.length());
        throw ((Throwable)new IllegalArgumentException(paramCharset.toString().toString()));
      }
      paramString = new StringBuilder();
      paramString.append("endIndex < beginIndex: ");
      paramString.append(paramInt2);
      paramString.append(" < ");
      paramString.append(paramInt1);
      throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
    }
    paramString = new StringBuilder();
    paramString.append("beginIndex < 0: ");
    paramString.append(paramInt1);
    throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
  }
  
  public Buffer writeString(String paramString, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "string");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    return writeString(paramString, 0, paramString.length(), paramCharset);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    return writeTo$default(this, paramOutputStream, 0L, 2, null);
  }
  
  public final Buffer writeTo(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramOutputStream, "out");
    _Util.checkOffsetAndCount(this.size, 0L, paramLong);
    Object localObject = this.head;
    while (paramLong > 0L)
    {
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      int i = (int)Math.min(paramLong, ((Segment)localObject).limit - ((Segment)localObject).pos);
      paramOutputStream.write(((Segment)localObject).data, ((Segment)localObject).pos, i);
      ((Segment)localObject).pos += i;
      long l1 = this.size;
      long l2 = i;
      this.size = (l1 - l2);
      l2 = paramLong - l2;
      paramLong = l2;
      if (((Segment)localObject).pos == ((Segment)localObject).limit)
      {
        Segment localSegment = ((Segment)localObject).pop();
        this.head = localSegment;
        SegmentPool.INSTANCE.recycle((Segment)localObject);
        localObject = localSegment;
        paramLong = l2;
      }
    }
    return this;
  }
  
  public Buffer writeUtf8(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "string");
    return writeUtf8(paramString, 0, paramString.length());
  }
  
  public Buffer writeUtf8(String paramString, int paramInt1, int paramInt2)
  {
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
            int m;
            if (j < 128)
            {
              localObject = writableSegment$okio(1);
              byte[] arrayOfByte = ((Segment)localObject).data;
              int k = ((Segment)localObject).limit - paramInt1;
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
              i = k + paramInt1 - ((Segment)localObject).limit;
              ((Segment)localObject).limit += i;
              setSize$okio(size() + i);
            }
            else
            {
              label218:
              if (j < 2048)
              {
                localObject = writableSegment$okio(2);
                ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(j >> 6 | 0xC0));
                ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(j & 0x3F | 0x80));
                ((Segment)localObject).limit += 2;
                setSize$okio(size() + 2L);
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
                    localObject = writableSegment$okio(4);
                    ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(i >> 18 | 0xF0));
                    ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(i >> 12 & 0x3F | 0x80));
                    ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(byte)(i >> 6 & 0x3F | 0x80));
                    ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(byte)(i & 0x3F | 0x80));
                    ((Segment)localObject).limit += 4;
                    setSize$okio(size() + 4L);
                    paramInt1 += 2;
                    break;
                  }
                  writeByte(63);
                  paramInt1 = m;
                  break label218;
                }
                localObject = writableSegment$okio(3);
                ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(j >> 12 | 0xE0));
                ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(0x3F & j >> 6 | 0x80));
                ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(byte)(j & 0x3F | 0x80));
                ((Segment)localObject).limit += 3;
                setSize$okio(size() + 3L);
              }
            }
          }
          return this;
        }
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("endIndex > string.length: ");
        ((StringBuilder)localObject).append(paramInt2);
        ((StringBuilder)localObject).append(" > ");
        ((StringBuilder)localObject).append(paramString.length());
        throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
      }
      paramString = new StringBuilder();
      paramString.append("endIndex < beginIndex: ");
      paramString.append(paramInt2);
      paramString.append(" < ");
      paramString.append(paramInt1);
      throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
    }
    paramString = new StringBuilder();
    paramString.append("beginIndex < 0: ");
    paramString.append(paramInt1);
    throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
  }
  
  public Buffer writeUtf8CodePoint(int paramInt)
  {
    if (paramInt < 128)
    {
      writeByte(paramInt);
    }
    else if (paramInt < 2048)
    {
      localObject = writableSegment$okio(2);
      ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(paramInt >> 6 | 0xC0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
      ((Segment)localObject).limit += 2;
      setSize$okio(size() + 2L);
    }
    else if ((55296 <= paramInt) && (57343 >= paramInt))
    {
      writeByte(63);
    }
    else if (paramInt < 65536)
    {
      localObject = writableSegment$okio(3);
      ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(paramInt >> 12 | 0xE0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(paramInt >> 6 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
      ((Segment)localObject).limit += 3;
      setSize$okio(size() + 3L);
    }
    else
    {
      if (paramInt > 1114111) {
        break label348;
      }
      localObject = writableSegment$okio(4);
      ((Segment)localObject).data[localObject.limit] = ((byte)(byte)(paramInt >> 18 | 0xF0));
      ((Segment)localObject).data[(localObject.limit + 1)] = ((byte)(byte)(paramInt >> 12 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 2)] = ((byte)(byte)(paramInt >> 6 & 0x3F | 0x80));
      ((Segment)localObject).data[(localObject.limit + 3)] = ((byte)(byte)(paramInt & 0x3F | 0x80));
      ((Segment)localObject).limit += 4;
      setSize$okio(size() + 4L);
    }
    return this;
    label348:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected code point: 0x");
    ((StringBuilder)localObject).append(_Util.toHexString(paramInt));
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\022\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\007\030\0002\0020\001B\005?\006\002\020\002J\b\020\020\032\0020\021H\026J\016\020\022\032\0020\n2\006\020\023\032\0020\bJ\006\020\024\032\0020\bJ\016\020\025\032\0020\n2\006\020\026\032\0020\nJ\016\020\027\032\0020\b2\006\020\t\032\0020\nR\024\020\003\032\004\030\0010\0048\006@\006X?\016?\006\002\n\000R\024\020\005\032\004\030\0010\0068\006@\006X?\016?\006\002\n\000R\022\020\007\032\0020\b8\006@\006X?\016?\006\002\n\000R\022\020\t\032\0020\n8\006@\006X?\016?\006\002\n\000R\022\020\013\032\0020\f8\006@\006X?\016?\006\002\n\000R\020\020\r\032\004\030\0010\016X?\016?\006\002\n\000R\022\020\017\032\0020\b8\006@\006X?\016?\006\002\n\000?\006\030"}, d2={"Lokio/Buffer$UnsafeCursor;", "Ljava/io/Closeable;", "()V", "buffer", "Lokio/Buffer;", "data", "", "end", "", "offset", "", "readWrite", "", "segment", "Lokio/Segment;", "start", "close", "", "expandBuffer", "minByteCount", "next", "resizeBuffer", "newSize", "seek", "okio"}, k=1, mv={1, 1, 16})
  public static final class UnsafeCursor
    implements Closeable
  {
    public Buffer buffer;
    public byte[] data;
    public int end = -1;
    public long offset = -1L;
    public boolean readWrite;
    private Segment segment;
    public int start = -1;
    
    public UnsafeCursor() {}
    
    public void close()
    {
      int i;
      if (this.buffer != null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.buffer = ((Buffer)null);
        this.segment = ((Segment)null);
        this.offset = -1L;
        this.data = ((byte[])null);
        this.start = -1;
        this.end = -1;
        return;
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
    
    public final long expandBuffer(int paramInt)
    {
      int i = 1;
      int j;
      if (paramInt > 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (paramInt <= 8192) {
          j = i;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          localObject = this.buffer;
          if (localObject != null)
          {
            if (this.readWrite)
            {
              long l1 = ((Buffer)localObject).size();
              Segment localSegment = ((Buffer)localObject).writableSegment$okio(paramInt);
              paramInt = 8192 - localSegment.limit;
              localSegment.limit = 8192;
              long l2 = paramInt;
              ((Buffer)localObject).setSize$okio(l1 + l2);
              this.segment = localSegment;
              this.offset = l1;
              this.data = localSegment.data;
              this.start = (8192 - paramInt);
              this.end = 8192;
              return l2;
            }
            throw ((Throwable)new IllegalStateException("expandBuffer() only permitted for read/write buffers".toString()));
          }
          throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("minByteCount > Segment.SIZE: ");
        ((StringBuilder)localObject).append(paramInt);
        throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("minByteCount <= 0: ");
      ((StringBuilder)localObject).append(paramInt);
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
    }
    
    public final int next()
    {
      long l = this.offset;
      Buffer localBuffer = this.buffer;
      if (localBuffer == null) {
        Intrinsics.throwNpe();
      }
      int i;
      if (l != localBuffer.size()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        l = this.offset;
        if (l == -1L) {
          l = 0L;
        } else {
          l += this.end - this.start;
        }
        return seek(l);
      }
      throw ((Throwable)new IllegalStateException("no more bytes".toString()));
    }
    
    public final long resizeBuffer(long paramLong)
    {
      Object localObject = this.buffer;
      if (localObject != null)
      {
        if (this.readWrite)
        {
          long l1 = ((Buffer)localObject).size();
          boolean bool1 = paramLong < l1;
          long l2;
          Segment localSegment;
          if (!bool1)
          {
            if (paramLong >= 0L) {
              bool1 = true;
            } else {
              bool1 = false;
            }
            if (bool1)
            {
              l2 = l1 - paramLong;
              while (l2 > 0L)
              {
                localSegment = ((Buffer)localObject).head;
                if (localSegment == null) {
                  Intrinsics.throwNpe();
                }
                localSegment = localSegment.prev;
                if (localSegment == null) {
                  Intrinsics.throwNpe();
                }
                long l3 = localSegment.limit - localSegment.pos;
                if (l3 <= l2)
                {
                  ((Buffer)localObject).head = localSegment.pop();
                  SegmentPool.INSTANCE.recycle(localSegment);
                  l2 -= l3;
                }
                else
                {
                  localSegment.limit -= (int)l2;
                }
              }
              this.segment = ((Segment)null);
              this.offset = paramLong;
              this.data = ((byte[])null);
              this.start = -1;
              this.end = -1;
            }
            else
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("newSize < 0: ");
              ((StringBuilder)localObject).append(paramLong);
              throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
            }
          }
          else if (bool1)
          {
            l2 = paramLong - l1;
            boolean bool2;
            for (bool1 = true; l2 > 0L; bool1 = bool2)
            {
              localSegment = ((Buffer)localObject).writableSegment$okio(1);
              int i = (int)Math.min(l2, 8192 - localSegment.limit);
              localSegment.limit += i;
              l2 -= i;
              bool2 = bool1;
              if (bool1)
              {
                this.segment = localSegment;
                this.offset = l1;
                this.data = localSegment.data;
                this.start = (localSegment.limit - i);
                this.end = localSegment.limit;
                bool2 = false;
              }
            }
          }
          ((Buffer)localObject).setSize$okio(paramLong);
          return l1;
        }
        throw ((Throwable)new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString()));
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
    
    public final int seek(long paramLong)
    {
      Buffer localBuffer = this.buffer;
      if (localBuffer != null)
      {
        if ((paramLong >= -1) && (paramLong <= localBuffer.size()))
        {
          if ((paramLong != -1L) && (paramLong != localBuffer.size()))
          {
            long l1 = 0L;
            long l2 = localBuffer.size();
            Segment localSegment1 = localBuffer.head;
            Segment localSegment2 = localBuffer.head;
            Segment localSegment3 = this.segment;
            long l3 = l1;
            long l4 = l2;
            localObject1 = localSegment1;
            Object localObject2 = localSegment2;
            if (localSegment3 != null)
            {
              l4 = this.offset;
              i = this.start;
              if (localSegment3 == null) {
                Intrinsics.throwNpe();
              }
              l4 -= i - localSegment3.pos;
              if (l4 > paramLong)
              {
                localObject2 = this.segment;
                l3 = l1;
                localObject1 = localSegment1;
              }
              else
              {
                localObject1 = this.segment;
                l3 = l4;
                localObject2 = localSegment2;
                l4 = l2;
              }
            }
            l1 = l4;
            if (l4 - paramLong > paramLong - l3) {
              for (localObject2 = localObject1;; localObject2 = ((Segment)localObject2).next)
              {
                if (localObject2 == null) {
                  Intrinsics.throwNpe();
                }
                l4 = l3;
                localObject1 = localObject2;
                if (paramLong < ((Segment)localObject2).limit - ((Segment)localObject2).pos + l3) {
                  break;
                }
                l3 += ((Segment)localObject2).limit - ((Segment)localObject2).pos;
              }
            }
            while (l1 > paramLong)
            {
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              localObject2 = ((Segment)localObject2).prev;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              l1 -= ((Segment)localObject2).limit - ((Segment)localObject2).pos;
            }
            l4 = l1;
            localObject1 = localObject2;
            localObject2 = localObject1;
            if (this.readWrite)
            {
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              localObject2 = localObject1;
              if (((Segment)localObject1).shared)
              {
                localObject2 = ((Segment)localObject1).unsharedCopy();
                if (localBuffer.head == localObject1) {
                  localBuffer.head = ((Segment)localObject2);
                }
                localObject2 = ((Segment)localObject1).push((Segment)localObject2);
                localObject1 = ((Segment)localObject2).prev;
                if (localObject1 == null) {
                  Intrinsics.throwNpe();
                }
                ((Segment)localObject1).pop();
              }
            }
            this.segment = ((Segment)localObject2);
            this.offset = paramLong;
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            this.data = ((Segment)localObject2).data;
            this.start = (((Segment)localObject2).pos + (int)(paramLong - l4));
            int i = ((Segment)localObject2).limit;
            this.end = i;
            return i - this.start;
          }
          this.segment = ((Segment)null);
          this.offset = paramLong;
          this.data = ((byte[])null);
          this.start = -1;
          this.end = -1;
          return -1;
        }
        Object localObject1 = StringCompanionObject.INSTANCE;
        localObject1 = String.format("offset=%s > size=%s", Arrays.copyOf(new Object[] { Long.valueOf(paramLong), Long.valueOf(localBuffer.size()) }, 2));
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "java.lang.String.format(format, *args)");
        throw ((Throwable)new ArrayIndexOutOfBoundsException((String)localObject1));
      }
      throw ((Throwable)new IllegalStateException("not attached to a buffer".toString()));
    }
  }
}
