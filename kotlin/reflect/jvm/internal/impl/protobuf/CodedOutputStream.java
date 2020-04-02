package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class CodedOutputStream
{
  private final byte[] buffer;
  private final int limit;
  private final OutputStream output;
  private int position;
  private int totalBytesWritten = 0;
  
  private CodedOutputStream(OutputStream paramOutputStream, byte[] paramArrayOfByte)
  {
    this.output = paramOutputStream;
    this.buffer = paramArrayOfByte;
    this.position = 0;
    this.limit = paramArrayOfByte.length;
  }
  
  public static int computeBoolSize(int paramInt, boolean paramBoolean)
  {
    return computeTagSize(paramInt) + computeBoolSizeNoTag(paramBoolean);
  }
  
  public static int computeBoolSizeNoTag(boolean paramBoolean)
  {
    return 1;
  }
  
  public static int computeByteArraySizeNoTag(byte[] paramArrayOfByte)
  {
    return computeRawVarint32Size(paramArrayOfByte.length) + paramArrayOfByte.length;
  }
  
  public static int computeBytesSize(int paramInt, ByteString paramByteString)
  {
    return computeTagSize(paramInt) + computeBytesSizeNoTag(paramByteString);
  }
  
  public static int computeBytesSizeNoTag(ByteString paramByteString)
  {
    return computeRawVarint32Size(paramByteString.size()) + paramByteString.size();
  }
  
  public static int computeDoubleSize(int paramInt, double paramDouble)
  {
    return computeTagSize(paramInt) + computeDoubleSizeNoTag(paramDouble);
  }
  
  public static int computeDoubleSizeNoTag(double paramDouble)
  {
    return 8;
  }
  
  public static int computeEnumSize(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeEnumSizeNoTag(paramInt2);
  }
  
  public static int computeEnumSizeNoTag(int paramInt)
  {
    return computeInt32SizeNoTag(paramInt);
  }
  
  public static int computeFixed32SizeNoTag(int paramInt)
  {
    return 4;
  }
  
  public static int computeFixed64SizeNoTag(long paramLong)
  {
    return 8;
  }
  
  public static int computeFloatSize(int paramInt, float paramFloat)
  {
    return computeTagSize(paramInt) + computeFloatSizeNoTag(paramFloat);
  }
  
  public static int computeFloatSizeNoTag(float paramFloat)
  {
    return 4;
  }
  
  public static int computeGroupSizeNoTag(MessageLite paramMessageLite)
  {
    return paramMessageLite.getSerializedSize();
  }
  
  public static int computeInt32Size(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeInt32SizeNoTag(paramInt2);
  }
  
  public static int computeInt32SizeNoTag(int paramInt)
  {
    if (paramInt >= 0) {
      return computeRawVarint32Size(paramInt);
    }
    return 10;
  }
  
  public static int computeInt64SizeNoTag(long paramLong)
  {
    return computeRawVarint64Size(paramLong);
  }
  
  public static int computeLazyFieldSizeNoTag(LazyFieldLite paramLazyFieldLite)
  {
    int i = paramLazyFieldLite.getSerializedSize();
    return computeRawVarint32Size(i) + i;
  }
  
  public static int computeMessageSize(int paramInt, MessageLite paramMessageLite)
  {
    return computeTagSize(paramInt) + computeMessageSizeNoTag(paramMessageLite);
  }
  
  public static int computeMessageSizeNoTag(MessageLite paramMessageLite)
  {
    int i = paramMessageLite.getSerializedSize();
    return computeRawVarint32Size(i) + i;
  }
  
  static int computePreferredBufferSize(int paramInt)
  {
    if (paramInt > 4096) {
      return 4096;
    }
    return paramInt;
  }
  
  public static int computeRawVarint32Size(int paramInt)
  {
    if ((paramInt & 0xFFFFFF80) == 0) {
      return 1;
    }
    if ((paramInt & 0xC000) == 0) {
      return 2;
    }
    if ((0xFFE00000 & paramInt) == 0) {
      return 3;
    }
    if ((paramInt & 0xF0000000) == 0) {
      return 4;
    }
    return 5;
  }
  
  public static int computeRawVarint64Size(long paramLong)
  {
    if ((0xFFFFFFFFFFFFFF80 & paramLong) == 0L) {
      return 1;
    }
    if ((0xFFFFFFFFFFFFC000 & paramLong) == 0L) {
      return 2;
    }
    if ((0xFFFFFFFFFFE00000 & paramLong) == 0L) {
      return 3;
    }
    if ((0xFFFFFFFFF0000000 & paramLong) == 0L) {
      return 4;
    }
    if ((0xFFFFFFF800000000 & paramLong) == 0L) {
      return 5;
    }
    if ((0xFFFFFC0000000000 & paramLong) == 0L) {
      return 6;
    }
    if ((0xFFFE000000000000 & paramLong) == 0L) {
      return 7;
    }
    if ((0xFF00000000000000 & paramLong) == 0L) {
      return 8;
    }
    if ((paramLong & 0x8000000000000000) == 0L) {
      return 9;
    }
    return 10;
  }
  
  public static int computeSFixed32SizeNoTag(int paramInt)
  {
    return 4;
  }
  
  public static int computeSFixed64SizeNoTag(long paramLong)
  {
    return 8;
  }
  
  public static int computeSInt32SizeNoTag(int paramInt)
  {
    return computeRawVarint32Size(encodeZigZag32(paramInt));
  }
  
  public static int computeSInt64Size(int paramInt, long paramLong)
  {
    return computeTagSize(paramInt) + computeSInt64SizeNoTag(paramLong);
  }
  
  public static int computeSInt64SizeNoTag(long paramLong)
  {
    return computeRawVarint64Size(encodeZigZag64(paramLong));
  }
  
  public static int computeStringSizeNoTag(String paramString)
  {
    try
    {
      paramString = paramString.getBytes("UTF-8");
      int i = computeRawVarint32Size(paramString.length);
      int j = paramString.length;
      return i + j;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException("UTF-8 not supported.", paramString);
    }
  }
  
  public static int computeTagSize(int paramInt)
  {
    return computeRawVarint32Size(WireFormat.makeTag(paramInt, 0));
  }
  
  public static int computeUInt32SizeNoTag(int paramInt)
  {
    return computeRawVarint32Size(paramInt);
  }
  
  public static int computeUInt64SizeNoTag(long paramLong)
  {
    return computeRawVarint64Size(paramLong);
  }
  
  public static int encodeZigZag32(int paramInt)
  {
    return paramInt >> 31 ^ paramInt << 1;
  }
  
  public static long encodeZigZag64(long paramLong)
  {
    return paramLong >> 63 ^ paramLong << 1;
  }
  
  public static CodedOutputStream newInstance(OutputStream paramOutputStream, int paramInt)
  {
    return new CodedOutputStream(paramOutputStream, new byte[paramInt]);
  }
  
  private void refreshBuffer()
    throws IOException
  {
    OutputStream localOutputStream = this.output;
    if (localOutputStream != null)
    {
      localOutputStream.write(this.buffer, 0, this.position);
      this.position = 0;
      return;
    }
    throw new OutOfSpaceException();
  }
  
  public void flush()
    throws IOException
  {
    if (this.output != null) {
      refreshBuffer();
    }
  }
  
  public void writeBool(int paramInt, boolean paramBoolean)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeBoolNoTag(paramBoolean);
  }
  
  public void writeBoolNoTag(boolean paramBoolean)
    throws IOException
  {
    writeRawByte(paramBoolean);
  }
  
  public void writeByteArrayNoTag(byte[] paramArrayOfByte)
    throws IOException
  {
    writeRawVarint32(paramArrayOfByte.length);
    writeRawBytes(paramArrayOfByte);
  }
  
  public void writeBytes(int paramInt, ByteString paramByteString)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeBytesNoTag(paramByteString);
  }
  
  public void writeBytesNoTag(ByteString paramByteString)
    throws IOException
  {
    writeRawVarint32(paramByteString.size());
    writeRawBytes(paramByteString);
  }
  
  public void writeDouble(int paramInt, double paramDouble)
    throws IOException
  {
    writeTag(paramInt, 1);
    writeDoubleNoTag(paramDouble);
  }
  
  public void writeDoubleNoTag(double paramDouble)
    throws IOException
  {
    writeRawLittleEndian64(Double.doubleToRawLongBits(paramDouble));
  }
  
  public void writeEnum(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeEnumNoTag(paramInt2);
  }
  
  public void writeEnumNoTag(int paramInt)
    throws IOException
  {
    writeInt32NoTag(paramInt);
  }
  
  public void writeFixed32NoTag(int paramInt)
    throws IOException
  {
    writeRawLittleEndian32(paramInt);
  }
  
  public void writeFixed64NoTag(long paramLong)
    throws IOException
  {
    writeRawLittleEndian64(paramLong);
  }
  
  public void writeFloat(int paramInt, float paramFloat)
    throws IOException
  {
    writeTag(paramInt, 5);
    writeFloatNoTag(paramFloat);
  }
  
  public void writeFloatNoTag(float paramFloat)
    throws IOException
  {
    writeRawLittleEndian32(Float.floatToRawIntBits(paramFloat));
  }
  
  public void writeGroup(int paramInt, MessageLite paramMessageLite)
    throws IOException
  {
    writeTag(paramInt, 3);
    writeGroupNoTag(paramMessageLite);
    writeTag(paramInt, 4);
  }
  
  public void writeGroupNoTag(MessageLite paramMessageLite)
    throws IOException
  {
    paramMessageLite.writeTo(this);
  }
  
  public void writeInt32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeInt32NoTag(paramInt2);
  }
  
  public void writeInt32NoTag(int paramInt)
    throws IOException
  {
    if (paramInt >= 0) {
      writeRawVarint32(paramInt);
    } else {
      writeRawVarint64(paramInt);
    }
  }
  
  public void writeInt64NoTag(long paramLong)
    throws IOException
  {
    writeRawVarint64(paramLong);
  }
  
  public void writeMessage(int paramInt, MessageLite paramMessageLite)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeMessageNoTag(paramMessageLite);
  }
  
  public void writeMessageNoTag(MessageLite paramMessageLite)
    throws IOException
  {
    writeRawVarint32(paramMessageLite.getSerializedSize());
    paramMessageLite.writeTo(this);
  }
  
  public void writeMessageSetExtension(int paramInt, MessageLite paramMessageLite)
    throws IOException
  {
    writeTag(1, 3);
    writeUInt32(2, paramInt);
    writeMessage(3, paramMessageLite);
    writeTag(1, 4);
  }
  
  public void writeRawByte(byte paramByte)
    throws IOException
  {
    if (this.position == this.limit) {
      refreshBuffer();
    }
    byte[] arrayOfByte = this.buffer;
    int i = this.position;
    this.position = (i + 1);
    arrayOfByte[i] = ((byte)paramByte);
    this.totalBytesWritten += 1;
  }
  
  public void writeRawByte(int paramInt)
    throws IOException
  {
    writeRawByte((byte)paramInt);
  }
  
  public void writeRawBytes(ByteString paramByteString)
    throws IOException
  {
    writeRawBytes(paramByteString, 0, paramByteString.size());
  }
  
  public void writeRawBytes(ByteString paramByteString, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.limit;
    int j = this.position;
    if (i - j >= paramInt2)
    {
      paramByteString.copyTo(this.buffer, paramInt1, j, paramInt2);
      this.position += paramInt2;
      this.totalBytesWritten += paramInt2;
    }
    else
    {
      i -= j;
      paramByteString.copyTo(this.buffer, paramInt1, j, i);
      paramInt1 += i;
      paramInt2 -= i;
      this.position = this.limit;
      this.totalBytesWritten += i;
      refreshBuffer();
      if (paramInt2 <= this.limit)
      {
        paramByteString.copyTo(this.buffer, paramInt1, 0, paramInt2);
        this.position = paramInt2;
      }
      else
      {
        paramByteString.writeTo(this.output, paramInt1, paramInt2);
      }
      this.totalBytesWritten += paramInt2;
    }
  }
  
  public void writeRawBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    writeRawBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void writeRawBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.limit;
    int j = this.position;
    if (i - j >= paramInt2)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, j, paramInt2);
      this.position += paramInt2;
      this.totalBytesWritten += paramInt2;
    }
    else
    {
      i -= j;
      System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, j, i);
      paramInt1 += i;
      paramInt2 -= i;
      this.position = this.limit;
      this.totalBytesWritten += i;
      refreshBuffer();
      if (paramInt2 <= this.limit)
      {
        System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, 0, paramInt2);
        this.position = paramInt2;
      }
      else
      {
        this.output.write(paramArrayOfByte, paramInt1, paramInt2);
      }
      this.totalBytesWritten += paramInt2;
    }
  }
  
  public void writeRawLittleEndian32(int paramInt)
    throws IOException
  {
    writeRawByte(paramInt & 0xFF);
    writeRawByte(paramInt >> 8 & 0xFF);
    writeRawByte(paramInt >> 16 & 0xFF);
    writeRawByte(paramInt >> 24 & 0xFF);
  }
  
  public void writeRawLittleEndian64(long paramLong)
    throws IOException
  {
    writeRawByte((int)paramLong & 0xFF);
    writeRawByte((int)(paramLong >> 8) & 0xFF);
    writeRawByte((int)(paramLong >> 16) & 0xFF);
    writeRawByte((int)(paramLong >> 24) & 0xFF);
    writeRawByte((int)(paramLong >> 32) & 0xFF);
    writeRawByte((int)(paramLong >> 40) & 0xFF);
    writeRawByte((int)(paramLong >> 48) & 0xFF);
    writeRawByte((int)(paramLong >> 56) & 0xFF);
  }
  
  public void writeRawVarint32(int paramInt)
    throws IOException
  {
    for (;;)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        writeRawByte(paramInt);
        return;
      }
      writeRawByte(paramInt & 0x7F | 0x80);
      paramInt >>>= 7;
    }
  }
  
  public void writeRawVarint64(long paramLong)
    throws IOException
  {
    for (;;)
    {
      if ((0xFFFFFFFFFFFFFF80 & paramLong) == 0L)
      {
        writeRawByte((int)paramLong);
        return;
      }
      writeRawByte((int)paramLong & 0x7F | 0x80);
      paramLong >>>= 7;
    }
  }
  
  public void writeSFixed32NoTag(int paramInt)
    throws IOException
  {
    writeRawLittleEndian32(paramInt);
  }
  
  public void writeSFixed64NoTag(long paramLong)
    throws IOException
  {
    writeRawLittleEndian64(paramLong);
  }
  
  public void writeSInt32NoTag(int paramInt)
    throws IOException
  {
    writeRawVarint32(encodeZigZag32(paramInt));
  }
  
  public void writeSInt64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeSInt64NoTag(paramLong);
  }
  
  public void writeSInt64NoTag(long paramLong)
    throws IOException
  {
    writeRawVarint64(encodeZigZag64(paramLong));
  }
  
  public void writeStringNoTag(String paramString)
    throws IOException
  {
    paramString = paramString.getBytes("UTF-8");
    writeRawVarint32(paramString.length);
    writeRawBytes(paramString);
  }
  
  public void writeTag(int paramInt1, int paramInt2)
    throws IOException
  {
    writeRawVarint32(WireFormat.makeTag(paramInt1, paramInt2));
  }
  
  public void writeUInt32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeUInt32NoTag(paramInt2);
  }
  
  public void writeUInt32NoTag(int paramInt)
    throws IOException
  {
    writeRawVarint32(paramInt);
  }
  
  public void writeUInt64NoTag(long paramLong)
    throws IOException
  {
    writeRawVarint64(paramLong);
  }
  
  public static class OutOfSpaceException
    extends IOException
  {
    OutOfSpaceException()
    {
      super();
    }
  }
}
