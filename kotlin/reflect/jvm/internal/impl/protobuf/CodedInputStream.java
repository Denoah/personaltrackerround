package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CodedInputStream
{
  private final byte[] buffer;
  private final boolean bufferIsImmutable;
  private int bufferPos;
  private int bufferSize;
  private int bufferSizeAfterLimit;
  private int currentLimit = Integer.MAX_VALUE;
  private boolean enableAliasing = false;
  private final InputStream input;
  private int lastTag;
  private int recursionDepth;
  private int recursionLimit = 64;
  private RefillCallback refillCallback = null;
  private int sizeLimit = 67108864;
  private int totalBytesRetired;
  
  private CodedInputStream(InputStream paramInputStream)
  {
    this.buffer = new byte['?'];
    this.bufferSize = 0;
    this.bufferPos = 0;
    this.totalBytesRetired = 0;
    this.input = paramInputStream;
    this.bufferIsImmutable = false;
  }
  
  private CodedInputStream(LiteralByteString paramLiteralByteString)
  {
    this.buffer = paramLiteralByteString.bytes;
    int i = paramLiteralByteString.getOffsetIntoBytes();
    this.bufferPos = i;
    this.bufferSize = (i + paramLiteralByteString.size());
    this.totalBytesRetired = (-this.bufferPos);
    this.input = null;
    this.bufferIsImmutable = true;
  }
  
  public static int decodeZigZag32(int paramInt)
  {
    return -(paramInt & 0x1) ^ paramInt >>> 1;
  }
  
  public static long decodeZigZag64(long paramLong)
  {
    return -(paramLong & 1L) ^ paramLong >>> 1;
  }
  
  private void ensureAvailable(int paramInt)
    throws IOException
  {
    if (this.bufferSize - this.bufferPos < paramInt) {
      refillBuffer(paramInt);
    }
  }
  
  public static CodedInputStream newInstance(InputStream paramInputStream)
  {
    return new CodedInputStream(paramInputStream);
  }
  
  static CodedInputStream newInstance(LiteralByteString paramLiteralByteString)
  {
    CodedInputStream localCodedInputStream = new CodedInputStream(paramLiteralByteString);
    try
    {
      localCodedInputStream.pushLimit(paramLiteralByteString.size());
      return localCodedInputStream;
    }
    catch (InvalidProtocolBufferException paramLiteralByteString)
    {
      throw new IllegalArgumentException(paramLiteralByteString);
    }
  }
  
  private byte[] readRawBytesSlowPath(int paramInt)
    throws IOException
  {
    if (paramInt <= 0)
    {
      if (paramInt == 0) {
        return Internal.EMPTY_BYTE_ARRAY;
      }
      throw InvalidProtocolBufferException.negativeSize();
    }
    int i = this.totalBytesRetired;
    int j = this.bufferPos;
    int k = this.currentLimit;
    if (i + j + paramInt <= k)
    {
      if (paramInt < 4096)
      {
        localObject1 = new byte[paramInt];
        i = this.bufferSize - j;
        System.arraycopy(this.buffer, j, localObject1, 0, i);
        this.bufferPos = this.bufferSize;
        paramInt -= i;
        ensureAvailable(paramInt);
        System.arraycopy(this.buffer, 0, localObject1, i, paramInt);
        this.bufferPos = paramInt;
        return localObject1;
      }
      k = this.bufferSize;
      this.totalBytesRetired = (i + k);
      this.bufferPos = 0;
      this.bufferSize = 0;
      int m = k - j;
      i = paramInt - m;
      Object localObject1 = new ArrayList();
      Object localObject2;
      while (i > 0)
      {
        int n = Math.min(i, 4096);
        arrayOfByte = new byte[n];
        k = 0;
        while (k < n)
        {
          localObject2 = this.input;
          int i1;
          if (localObject2 == null) {
            i1 = -1;
          } else {
            i1 = ((InputStream)localObject2).read(arrayOfByte, k, n - k);
          }
          if (i1 != -1)
          {
            this.totalBytesRetired += i1;
            k += i1;
          }
          else
          {
            throw InvalidProtocolBufferException.truncatedMessage();
          }
        }
        i -= n;
        ((List)localObject1).add(arrayOfByte);
      }
      byte[] arrayOfByte = new byte[paramInt];
      System.arraycopy(this.buffer, j, arrayOfByte, 0, m);
      localObject1 = ((List)localObject1).iterator();
      paramInt = m;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (byte[])((Iterator)localObject1).next();
        System.arraycopy(localObject2, 0, arrayOfByte, paramInt, localObject2.length);
        paramInt += localObject2.length;
      }
      return arrayOfByte;
    }
    skipRawBytes(k - i - j);
    throw InvalidProtocolBufferException.truncatedMessage();
  }
  
  public static int readRawVarint32(int paramInt, InputStream paramInputStream)
    throws IOException
  {
    if ((paramInt & 0x80) == 0) {
      return paramInt;
    }
    int i = paramInt & 0x7F;
    int j;
    for (paramInt = 7;; paramInt += 7)
    {
      j = paramInt;
      if (paramInt >= 32) {
        break label65;
      }
      j = paramInputStream.read();
      if (j == -1) {
        break;
      }
      i |= (j & 0x7F) << paramInt;
      if ((j & 0x80) == 0) {
        return i;
      }
    }
    throw InvalidProtocolBufferException.truncatedMessage();
    label65:
    while (j < 64)
    {
      paramInt = paramInputStream.read();
      if (paramInt != -1)
      {
        if ((paramInt & 0x80) == 0) {
          return i;
        }
        j += 7;
      }
      else
      {
        throw InvalidProtocolBufferException.truncatedMessage();
      }
    }
    throw InvalidProtocolBufferException.malformedVarint();
  }
  
  private void recomputeBufferSizeAfterLimit()
  {
    int i = this.bufferSize + this.bufferSizeAfterLimit;
    this.bufferSize = i;
    int j = this.totalBytesRetired + i;
    int k = this.currentLimit;
    if (j > k)
    {
      k = j - k;
      this.bufferSizeAfterLimit = k;
      this.bufferSize = (i - k);
    }
    else
    {
      this.bufferSizeAfterLimit = 0;
    }
  }
  
  private void refillBuffer(int paramInt)
    throws IOException
  {
    if (tryRefillBuffer(paramInt)) {
      return;
    }
    throw InvalidProtocolBufferException.truncatedMessage();
  }
  
  private void skipRawBytesSlowPath(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
    {
      int i = this.totalBytesRetired;
      int j = this.bufferPos;
      int k = this.currentLimit;
      if (i + j + paramInt <= k)
      {
        i = this.bufferSize;
        j = i - j;
        this.bufferPos = i;
        refillBuffer(1);
        for (;;)
        {
          k = paramInt - j;
          i = this.bufferSize;
          if (k <= i) {
            break;
          }
          j += i;
          this.bufferPos = i;
          refillBuffer(1);
        }
        this.bufferPos = k;
        return;
      }
      skipRawBytes(k - i - j);
      throw InvalidProtocolBufferException.truncatedMessage();
    }
    throw InvalidProtocolBufferException.negativeSize();
  }
  
  private boolean tryRefillBuffer(int paramInt)
    throws IOException
  {
    int i = this.bufferPos;
    if (i + paramInt > this.bufferSize)
    {
      if (this.totalBytesRetired + i + paramInt > this.currentLimit) {
        return false;
      }
      localObject = this.refillCallback;
      if (localObject != null) {
        ((RefillCallback)localObject).onRefill();
      }
      if (this.input != null)
      {
        i = this.bufferPos;
        if (i > 0)
        {
          int j = this.bufferSize;
          if (j > i)
          {
            localObject = this.buffer;
            System.arraycopy(localObject, i, localObject, 0, j - i);
          }
          this.totalBytesRetired += i;
          this.bufferSize -= i;
          this.bufferPos = 0;
        }
        InputStream localInputStream = this.input;
        localObject = this.buffer;
        i = this.bufferSize;
        i = localInputStream.read((byte[])localObject, i, localObject.length - i);
        if ((i != 0) && (i >= -1) && (i <= this.buffer.length))
        {
          if (i > 0)
          {
            this.bufferSize += i;
            if (this.totalBytesRetired + paramInt - this.sizeLimit <= 0)
            {
              recomputeBufferSizeAfterLimit();
              boolean bool;
              if (this.bufferSize >= paramInt) {
                bool = true;
              } else {
                bool = tryRefillBuffer(paramInt);
              }
              return bool;
            }
            throw InvalidProtocolBufferException.sizeLimitExceeded();
          }
        }
        else
        {
          localObject = new StringBuilder(102);
          ((StringBuilder)localObject).append("InputStream#read(byte[]) returned invalid result: ");
          ((StringBuilder)localObject).append(i);
          ((StringBuilder)localObject).append("\nThe InputStream implementation is buggy.");
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
      }
      return false;
    }
    Object localObject = new StringBuilder(77);
    ((StringBuilder)localObject).append("refillBuffer() called when ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(" bytes were already available in buffer");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void checkLastTagWas(int paramInt)
    throws InvalidProtocolBufferException
  {
    if (this.lastTag == paramInt) {
      return;
    }
    throw InvalidProtocolBufferException.invalidEndTag();
  }
  
  public int getBytesUntilLimit()
  {
    int i = this.currentLimit;
    if (i == Integer.MAX_VALUE) {
      return -1;
    }
    return i - (this.totalBytesRetired + this.bufferPos);
  }
  
  public boolean isAtEnd()
    throws IOException
  {
    int i = this.bufferPos;
    int j = this.bufferSize;
    boolean bool = true;
    if ((i != j) || (tryRefillBuffer(1))) {
      bool = false;
    }
    return bool;
  }
  
  public void popLimit(int paramInt)
  {
    this.currentLimit = paramInt;
    recomputeBufferSizeAfterLimit();
  }
  
  public int pushLimit(int paramInt)
    throws InvalidProtocolBufferException
  {
    if (paramInt >= 0)
    {
      paramInt += this.totalBytesRetired + this.bufferPos;
      int i = this.currentLimit;
      if (paramInt <= i)
      {
        this.currentLimit = paramInt;
        recomputeBufferSizeAfterLimit();
        return i;
      }
      throw InvalidProtocolBufferException.truncatedMessage();
    }
    throw InvalidProtocolBufferException.negativeSize();
  }
  
  public boolean readBool()
    throws IOException
  {
    boolean bool;
    if (readRawVarint64() != 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public ByteString readBytes()
    throws IOException
  {
    int i = readRawVarint32();
    if ((i <= this.bufferSize - this.bufferPos) && (i > 0))
    {
      Object localObject;
      if ((this.bufferIsImmutable) && (this.enableAliasing)) {
        localObject = new BoundedByteString(this.buffer, this.bufferPos, i);
      } else {
        localObject = ByteString.copyFrom(this.buffer, this.bufferPos, i);
      }
      this.bufferPos += i;
      return localObject;
    }
    if (i == 0) {
      return ByteString.EMPTY;
    }
    return new LiteralByteString(readRawBytesSlowPath(i));
  }
  
  public double readDouble()
    throws IOException
  {
    return Double.longBitsToDouble(readRawLittleEndian64());
  }
  
  public int readEnum()
    throws IOException
  {
    return readRawVarint32();
  }
  
  public int readFixed32()
    throws IOException
  {
    return readRawLittleEndian32();
  }
  
  public long readFixed64()
    throws IOException
  {
    return readRawLittleEndian64();
  }
  
  public float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(readRawLittleEndian32());
  }
  
  public void readGroup(int paramInt, MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite)
    throws IOException
  {
    int i = this.recursionDepth;
    if (i < this.recursionLimit)
    {
      this.recursionDepth = (i + 1);
      paramBuilder.mergeFrom(this, paramExtensionRegistryLite);
      checkLastTagWas(WireFormat.makeTag(paramInt, 4));
      this.recursionDepth -= 1;
      return;
    }
    throw InvalidProtocolBufferException.recursionLimitExceeded();
  }
  
  public int readInt32()
    throws IOException
  {
    return readRawVarint32();
  }
  
  public long readInt64()
    throws IOException
  {
    return readRawVarint64();
  }
  
  public <T extends MessageLite> T readMessage(Parser<T> paramParser, ExtensionRegistryLite paramExtensionRegistryLite)
    throws IOException
  {
    int i = readRawVarint32();
    if (this.recursionDepth < this.recursionLimit)
    {
      i = pushLimit(i);
      this.recursionDepth += 1;
      paramParser = (MessageLite)paramParser.parsePartialFrom(this, paramExtensionRegistryLite);
      checkLastTagWas(0);
      this.recursionDepth -= 1;
      popLimit(i);
      return paramParser;
    }
    throw InvalidProtocolBufferException.recursionLimitExceeded();
  }
  
  public void readMessage(MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite)
    throws IOException
  {
    int i = readRawVarint32();
    if (this.recursionDepth < this.recursionLimit)
    {
      i = pushLimit(i);
      this.recursionDepth += 1;
      paramBuilder.mergeFrom(this, paramExtensionRegistryLite);
      checkLastTagWas(0);
      this.recursionDepth -= 1;
      popLimit(i);
      return;
    }
    throw InvalidProtocolBufferException.recursionLimitExceeded();
  }
  
  public byte readRawByte()
    throws IOException
  {
    if (this.bufferPos == this.bufferSize) {
      refillBuffer(1);
    }
    byte[] arrayOfByte = this.buffer;
    int i = this.bufferPos;
    this.bufferPos = (i + 1);
    return arrayOfByte[i];
  }
  
  public int readRawLittleEndian32()
    throws IOException
  {
    int i = this.bufferPos;
    int j = i;
    if (this.bufferSize - i < 4)
    {
      refillBuffer(4);
      j = this.bufferPos;
    }
    byte[] arrayOfByte = this.buffer;
    this.bufferPos = (j + 4);
    i = arrayOfByte[j];
    int k = arrayOfByte[(j + 1)];
    int m = arrayOfByte[(j + 2)];
    return (arrayOfByte[(j + 3)] & 0xFF) << 24 | i & 0xFF | (k & 0xFF) << 8 | (m & 0xFF) << 16;
  }
  
  public long readRawLittleEndian64()
    throws IOException
  {
    int i = this.bufferPos;
    int j = i;
    if (this.bufferSize - i < 8)
    {
      refillBuffer(8);
      j = this.bufferPos;
    }
    byte[] arrayOfByte = this.buffer;
    this.bufferPos = (j + 8);
    long l1 = arrayOfByte[j];
    long l2 = arrayOfByte[(j + 1)];
    long l3 = arrayOfByte[(j + 2)];
    long l4 = arrayOfByte[(j + 3)];
    long l5 = arrayOfByte[(j + 4)];
    long l6 = arrayOfByte[(j + 5)];
    long l7 = arrayOfByte[(j + 6)];
    return (arrayOfByte[(j + 7)] & 0xFF) << 56 | l1 & 0xFF | (l2 & 0xFF) << 8 | (l3 & 0xFF) << 16 | (l4 & 0xFF) << 24 | (l5 & 0xFF) << 32 | (l6 & 0xFF) << 40 | (l7 & 0xFF) << 48;
  }
  
  public int readRawVarint32()
    throws IOException
  {
    int i = this.bufferPos;
    int j = this.bufferSize;
    int k;
    if (j != i)
    {
      byte[] arrayOfByte = this.buffer;
      k = i + 1;
      i = arrayOfByte[i];
      if (i >= 0)
      {
        this.bufferPos = k;
        return i;
      }
      if (j - k >= 9)
      {
        j = k + 1;
        i ^= arrayOfByte[k] << 7;
        long l1 = i;
        long l2;
        if (l1 < 0L)
        {
          l2 = -128L;
          k = j;
          j = (int)(l1 ^ l2);
          break label308;
        }
        k = j + 1;
        i ^= arrayOfByte[j] << 14;
        l1 = i;
        if (l1 >= 0L) {
          j = (int)(0x3F80 ^ l1);
        }
        int m;
        int n;
        do
        {
          int i1;
          do
          {
            do
            {
              break label308;
              j = k + 1;
              k = i ^ arrayOfByte[k] << 21;
              l1 = k;
              if (l1 < 0L)
              {
                l2 = -2080896L;
                k = j;
                break;
              }
              m = j + 1;
              n = arrayOfByte[j];
              i = (int)(k ^ n << 28 ^ 0xFE03F80);
              j = i;
              k = m;
            } while (n >= 0);
            i1 = m + 1;
            j = i;
            k = i1;
            if (arrayOfByte[m] >= 0) {
              break label308;
            }
            n = i1 + 1;
            j = i;
            k = n;
          } while (arrayOfByte[i1] >= 0);
          m = n + 1;
          j = i;
          k = m;
          if (arrayOfByte[n] >= 0) {
            break label308;
          }
          n = m + 1;
          j = i;
          k = n;
        } while (arrayOfByte[m] >= 0);
        k = n + 1;
        j = i;
        if (arrayOfByte[n] >= 0) {
          break label308;
        }
      }
    }
    return (int)readRawVarint64SlowPath();
    label308:
    this.bufferPos = k;
    return j;
  }
  
  public long readRawVarint64()
    throws IOException
  {
    int i = this.bufferPos;
    int j = this.bufferSize;
    int k;
    long l1;
    if (j != i)
    {
      byte[] arrayOfByte = this.buffer;
      k = i + 1;
      i = arrayOfByte[i];
      if (i >= 0)
      {
        this.bufferPos = k;
        return i;
      }
      if (j - k >= 9)
      {
        j = k + 1;
        l1 = i ^ arrayOfByte[k] << 7;
        long l2;
        if (l1 < 0L) {
          l2 = -128L;
        }
        for (;;)
        {
          l1 ^= l2;
          break label356;
          k = j + 1;
          l2 = l1 ^ arrayOfByte[j] << 14;
          if (l2 >= 0L)
          {
            l1 = 16256L;
            j = k;
          }
          for (;;)
          {
            l1 = l2 ^ l1;
            break label356;
            i = k + 1;
            l1 = l2 ^ arrayOfByte[k] << 21;
            if (l1 < 0L)
            {
              l2 = -2080896L;
              j = i;
              break;
            }
            j = i + 1;
            l2 = l1 ^ arrayOfByte[i] << 28;
            if (l2 >= 0L)
            {
              l1 = 266354560L;
            }
            else
            {
              k = j + 1;
              l1 = l2 ^ arrayOfByte[j] << 35;
              if (l1 < 0L)
              {
                l2 = -34093383808L;
                j = k;
                break;
              }
              j = k + 1;
              l2 = l1 ^ arrayOfByte[k] << 42;
              if (l2 < 0L) {
                break label272;
              }
              l1 = 4363953127296L;
            }
          }
          label272:
          i = j + 1;
          l1 = l2 ^ arrayOfByte[j] << 49;
          if (l1 >= 0L) {
            break;
          }
          l2 = -558586000294016L;
          j = i;
        }
        k = i + 1;
        l1 = l1 ^ arrayOfByte[i] << 56 ^ 0xFE03F80FE03F80;
        if (l1 >= 0L) {
          break label353;
        }
        j = k + 1;
        if (arrayOfByte[k] >= 0L) {
          break label356;
        }
      }
    }
    return readRawVarint64SlowPath();
    label353:
    j = k;
    label356:
    this.bufferPos = j;
    return l1;
  }
  
  long readRawVarint64SlowPath()
    throws IOException
  {
    long l = 0L;
    for (int i = 0; i < 64; i += 7)
    {
      int j = readRawByte();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0) {
        return l;
      }
    }
    throw InvalidProtocolBufferException.malformedVarint();
  }
  
  public int readSFixed32()
    throws IOException
  {
    return readRawLittleEndian32();
  }
  
  public long readSFixed64()
    throws IOException
  {
    return readRawLittleEndian64();
  }
  
  public int readSInt32()
    throws IOException
  {
    return decodeZigZag32(readRawVarint32());
  }
  
  public long readSInt64()
    throws IOException
  {
    return decodeZigZag64(readRawVarint64());
  }
  
  public String readString()
    throws IOException
  {
    int i = readRawVarint32();
    if ((i <= this.bufferSize - this.bufferPos) && (i > 0))
    {
      String str = new String(this.buffer, this.bufferPos, i, "UTF-8");
      this.bufferPos += i;
      return str;
    }
    if (i == 0) {
      return "";
    }
    return new String(readRawBytesSlowPath(i), "UTF-8");
  }
  
  public String readStringRequireUtf8()
    throws IOException
  {
    int i = readRawVarint32();
    int j = this.bufferPos;
    byte[] arrayOfByte;
    if ((i <= this.bufferSize - j) && (i > 0))
    {
      arrayOfByte = this.buffer;
      this.bufferPos = (j + i);
    }
    else
    {
      if (i == 0) {
        return "";
      }
      arrayOfByte = readRawBytesSlowPath(i);
      j = 0;
    }
    if (Utf8.isValidUtf8(arrayOfByte, j, j + i)) {
      return new String(arrayOfByte, j, i, "UTF-8");
    }
    throw InvalidProtocolBufferException.invalidUtf8();
  }
  
  public int readTag()
    throws IOException
  {
    if (isAtEnd())
    {
      this.lastTag = 0;
      return 0;
    }
    int i = readRawVarint32();
    this.lastTag = i;
    if (WireFormat.getTagFieldNumber(i) != 0) {
      return this.lastTag;
    }
    throw InvalidProtocolBufferException.invalidTag();
  }
  
  public int readUInt32()
    throws IOException
  {
    return readRawVarint32();
  }
  
  public long readUInt64()
    throws IOException
  {
    return readRawVarint64();
  }
  
  public boolean skipField(int paramInt, CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    int i = WireFormat.getTagWireType(paramInt);
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 4)
            {
              if (i == 5)
              {
                i = readRawLittleEndian32();
                paramCodedOutputStream.writeRawVarint32(paramInt);
                paramCodedOutputStream.writeFixed32NoTag(i);
                return true;
              }
              throw InvalidProtocolBufferException.invalidWireType();
            }
            return false;
          }
          paramCodedOutputStream.writeRawVarint32(paramInt);
          skipMessage(paramCodedOutputStream);
          paramInt = WireFormat.makeTag(WireFormat.getTagFieldNumber(paramInt), 4);
          checkLastTagWas(paramInt);
          paramCodedOutputStream.writeRawVarint32(paramInt);
          return true;
        }
        ByteString localByteString = readBytes();
        paramCodedOutputStream.writeRawVarint32(paramInt);
        paramCodedOutputStream.writeBytesNoTag(localByteString);
        return true;
      }
      l = readRawLittleEndian64();
      paramCodedOutputStream.writeRawVarint32(paramInt);
      paramCodedOutputStream.writeFixed64NoTag(l);
      return true;
    }
    long l = readInt64();
    paramCodedOutputStream.writeRawVarint32(paramInt);
    paramCodedOutputStream.writeUInt64NoTag(l);
    return true;
  }
  
  public void skipMessage(CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    int i;
    do
    {
      i = readTag();
    } while ((i != 0) && (skipField(i, paramCodedOutputStream)));
  }
  
  public void skipRawBytes(int paramInt)
    throws IOException
  {
    int i = this.bufferSize;
    int j = this.bufferPos;
    if ((paramInt <= i - j) && (paramInt >= 0)) {
      this.bufferPos = (j + paramInt);
    } else {
      skipRawBytesSlowPath(paramInt);
    }
  }
  
  private static abstract interface RefillCallback
  {
    public abstract void onRefill();
  }
}
