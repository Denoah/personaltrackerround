package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

class LiteralByteString
  extends ByteString
{
  protected final byte[] bytes;
  private int hash = 0;
  
  LiteralByteString(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }
  
  static int hashCode(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    for (int i = paramInt2; i < paramInt2 + paramInt3; i++) {
      paramInt1 = paramInt1 * 31 + paramArrayOfByte[i];
    }
    return paramInt1;
  }
  
  public byte byteAt(int paramInt)
  {
    return this.bytes[paramInt];
  }
  
  protected void copyToInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.bytes, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ByteString)) {
      return false;
    }
    if (size() != ((ByteString)paramObject).size()) {
      return false;
    }
    if (size() == 0) {
      return true;
    }
    if ((paramObject instanceof LiteralByteString)) {
      return equalsRange((LiteralByteString)paramObject, 0, size());
    }
    if ((paramObject instanceof RopeByteString)) {
      return paramObject.equals(this);
    }
    String str = String.valueOf(String.valueOf(paramObject.getClass()));
    paramObject = new StringBuilder(str.length() + 49);
    paramObject.append("Has a new type of ByteString been created? Found ");
    paramObject.append(str);
    throw new IllegalArgumentException(paramObject.toString());
  }
  
  boolean equalsRange(LiteralByteString paramLiteralByteString, int paramInt1, int paramInt2)
  {
    if (paramInt2 <= paramLiteralByteString.size())
    {
      if (paramInt1 + paramInt2 <= paramLiteralByteString.size())
      {
        byte[] arrayOfByte1 = this.bytes;
        byte[] arrayOfByte2 = paramLiteralByteString.bytes;
        int i = getOffsetIntoBytes();
        j = getOffsetIntoBytes();
        for (paramInt1 = paramLiteralByteString.getOffsetIntoBytes() + paramInt1; j < i + paramInt2; paramInt1++)
        {
          if (arrayOfByte1[j] != arrayOfByte2[paramInt1]) {
            return false;
          }
          j++;
        }
        return true;
      }
      int j = paramLiteralByteString.size();
      paramLiteralByteString = new StringBuilder(59);
      paramLiteralByteString.append("Ran off end of other: ");
      paramLiteralByteString.append(paramInt1);
      paramLiteralByteString.append(", ");
      paramLiteralByteString.append(paramInt2);
      paramLiteralByteString.append(", ");
      paramLiteralByteString.append(j);
      throw new IllegalArgumentException(paramLiteralByteString.toString());
    }
    paramInt1 = size();
    paramLiteralByteString = new StringBuilder(40);
    paramLiteralByteString.append("Length too large: ");
    paramLiteralByteString.append(paramInt2);
    paramLiteralByteString.append(paramInt1);
    throw new IllegalArgumentException(paramLiteralByteString.toString());
  }
  
  protected int getOffsetIntoBytes()
  {
    return 0;
  }
  
  protected int getTreeDepth()
  {
    return 0;
  }
  
  public int hashCode()
  {
    int i = this.hash;
    int j = i;
    if (i == 0)
    {
      j = size();
      i = partialHash(j, 0, j);
      j = i;
      if (i == 0) {
        j = 1;
      }
      this.hash = j;
    }
    return j;
  }
  
  protected boolean isBalanced()
  {
    return true;
  }
  
  public boolean isValidUtf8()
  {
    int i = getOffsetIntoBytes();
    return Utf8.isValidUtf8(this.bytes, i, size() + i);
  }
  
  public ByteString.ByteIterator iterator()
  {
    return new LiteralByteIterator(null);
  }
  
  public CodedInputStream newCodedInput()
  {
    return CodedInputStream.newInstance(this);
  }
  
  protected int partialHash(int paramInt1, int paramInt2, int paramInt3)
  {
    return hashCode(paramInt1, this.bytes, getOffsetIntoBytes() + paramInt2, paramInt3);
  }
  
  protected int partialIsValidUtf8(int paramInt1, int paramInt2, int paramInt3)
  {
    paramInt2 = getOffsetIntoBytes() + paramInt2;
    return Utf8.partialIsValidUtf8(paramInt1, this.bytes, paramInt2, paramInt3 + paramInt2);
  }
  
  protected int peekCachedHashCode()
  {
    return this.hash;
  }
  
  public int size()
  {
    return this.bytes.length;
  }
  
  public String toString(String paramString)
    throws UnsupportedEncodingException
  {
    return new String(this.bytes, getOffsetIntoBytes(), size(), paramString);
  }
  
  void writeToInternal(OutputStream paramOutputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    paramOutputStream.write(this.bytes, getOffsetIntoBytes() + paramInt1, paramInt2);
  }
  
  private class LiteralByteIterator
    implements ByteString.ByteIterator
  {
    private final int limit = LiteralByteString.this.size();
    private int position = 0;
    
    private LiteralByteIterator() {}
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.position < this.limit) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Byte next()
    {
      return Byte.valueOf(nextByte());
    }
    
    public byte nextByte()
    {
      try
      {
        byte[] arrayOfByte = LiteralByteString.this.bytes;
        int i = this.position;
        this.position = (i + 1);
        byte b = arrayOfByte[i];
        return b;
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        throw new NoSuchElementException(localArrayIndexOutOfBoundsException.getMessage());
      }
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
}
