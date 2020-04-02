package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class ByteString
  implements Iterable<Byte>
{
  public static final ByteString EMPTY = new LiteralByteString(new byte[0]);
  
  ByteString() {}
  
  private static ByteString balancedConcat(Iterator<ByteString> paramIterator, int paramInt)
  {
    if (paramInt == 1)
    {
      paramIterator = (ByteString)paramIterator.next();
    }
    else
    {
      int i = paramInt >>> 1;
      paramIterator = balancedConcat(paramIterator, i).concat(balancedConcat(paramIterator, paramInt - i));
    }
    return paramIterator;
  }
  
  public static ByteString copyFrom(Iterable<ByteString> paramIterable)
  {
    if (!(paramIterable instanceof Collection))
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramIterable.iterator();
      for (;;)
      {
        paramIterable = localArrayList;
        if (!localIterator.hasNext()) {
          break;
        }
        localArrayList.add((ByteString)localIterator.next());
      }
    }
    paramIterable = (Collection)paramIterable;
    if (paramIterable.isEmpty()) {
      paramIterable = EMPTY;
    } else {
      paramIterable = balancedConcat(paramIterable.iterator(), paramIterable.size());
    }
    return paramIterable;
  }
  
  public static ByteString copyFrom(byte[] paramArrayOfByte)
  {
    return copyFrom(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static ByteString copyFrom(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return new LiteralByteString(arrayOfByte);
  }
  
  public static ByteString copyFromUtf8(String paramString)
  {
    try
    {
      paramString = new LiteralByteString(paramString.getBytes("UTF-8"));
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException("UTF-8 not supported?", paramString);
    }
  }
  
  public static Output newOutput()
  {
    return new Output(128);
  }
  
  public ByteString concat(ByteString paramByteString)
  {
    int i = size();
    int j = paramByteString.size();
    if (i + j < 2147483647L) {
      return RopeByteString.concatenate(this, paramByteString);
    }
    paramByteString = new StringBuilder(53);
    paramByteString.append("ByteString would be too long: ");
    paramByteString.append(i);
    paramByteString.append("+");
    paramByteString.append(j);
    throw new IllegalArgumentException(paramByteString.toString());
  }
  
  public void copyTo(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= 0)
    {
      if (paramInt2 >= 0)
      {
        if (paramInt3 >= 0)
        {
          int i = paramInt1 + paramInt3;
          if (i <= size())
          {
            i = paramInt2 + paramInt3;
            if (i <= paramArrayOfByte.length)
            {
              if (paramInt3 > 0) {
                copyToInternal(paramArrayOfByte, paramInt1, paramInt2, paramInt3);
              }
              return;
            }
            paramArrayOfByte = new StringBuilder(34);
            paramArrayOfByte.append("Target end offset < 0: ");
            paramArrayOfByte.append(i);
            throw new IndexOutOfBoundsException(paramArrayOfByte.toString());
          }
          paramArrayOfByte = new StringBuilder(34);
          paramArrayOfByte.append("Source end offset < 0: ");
          paramArrayOfByte.append(i);
          throw new IndexOutOfBoundsException(paramArrayOfByte.toString());
        }
        paramArrayOfByte = new StringBuilder(23);
        paramArrayOfByte.append("Length < 0: ");
        paramArrayOfByte.append(paramInt3);
        throw new IndexOutOfBoundsException(paramArrayOfByte.toString());
      }
      paramArrayOfByte = new StringBuilder(30);
      paramArrayOfByte.append("Target offset < 0: ");
      paramArrayOfByte.append(paramInt2);
      throw new IndexOutOfBoundsException(paramArrayOfByte.toString());
    }
    paramArrayOfByte = new StringBuilder(30);
    paramArrayOfByte.append("Source offset < 0: ");
    paramArrayOfByte.append(paramInt1);
    throw new IndexOutOfBoundsException(paramArrayOfByte.toString());
  }
  
  protected abstract void copyToInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
  
  protected abstract int getTreeDepth();
  
  protected abstract boolean isBalanced();
  
  public boolean isEmpty()
  {
    boolean bool;
    if (size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public abstract boolean isValidUtf8();
  
  public abstract ByteIterator iterator();
  
  public abstract CodedInputStream newCodedInput();
  
  protected abstract int partialHash(int paramInt1, int paramInt2, int paramInt3);
  
  protected abstract int partialIsValidUtf8(int paramInt1, int paramInt2, int paramInt3);
  
  protected abstract int peekCachedHashCode();
  
  public abstract int size();
  
  public byte[] toByteArray()
  {
    int i = size();
    if (i == 0) {
      return Internal.EMPTY_BYTE_ARRAY;
    }
    byte[] arrayOfByte = new byte[i];
    copyToInternal(arrayOfByte, 0, 0, i);
    return arrayOfByte;
  }
  
  public String toString()
  {
    return String.format("<ByteString@%s size=%d>", new Object[] { Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()) });
  }
  
  public abstract String toString(String paramString)
    throws UnsupportedEncodingException;
  
  public String toStringUtf8()
  {
    try
    {
      String str = toString("UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported?", localUnsupportedEncodingException);
    }
  }
  
  void writeTo(OutputStream paramOutputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt1 >= 0)
    {
      if (paramInt2 >= 0)
      {
        int i = paramInt1 + paramInt2;
        if (i <= size())
        {
          if (paramInt2 > 0) {
            writeToInternal(paramOutputStream, paramInt1, paramInt2);
          }
          return;
        }
        paramOutputStream = new StringBuilder(39);
        paramOutputStream.append("Source end offset exceeded: ");
        paramOutputStream.append(i);
        throw new IndexOutOfBoundsException(paramOutputStream.toString());
      }
      paramOutputStream = new StringBuilder(23);
      paramOutputStream.append("Length < 0: ");
      paramOutputStream.append(paramInt2);
      throw new IndexOutOfBoundsException(paramOutputStream.toString());
    }
    paramOutputStream = new StringBuilder(30);
    paramOutputStream.append("Source offset < 0: ");
    paramOutputStream.append(paramInt1);
    throw new IndexOutOfBoundsException(paramOutputStream.toString());
  }
  
  abstract void writeToInternal(OutputStream paramOutputStream, int paramInt1, int paramInt2)
    throws IOException;
  
  public static abstract interface ByteIterator
    extends Iterator<Byte>
  {
    public abstract byte nextByte();
  }
  
  public static final class Output
    extends OutputStream
  {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private byte[] buffer;
    private int bufferPos;
    private final ArrayList<ByteString> flushedBuffers;
    private int flushedBuffersTotalBytes;
    private final int initialCapacity;
    
    Output(int paramInt)
    {
      if (paramInt >= 0)
      {
        this.initialCapacity = paramInt;
        this.flushedBuffers = new ArrayList();
        this.buffer = new byte[paramInt];
        return;
      }
      throw new IllegalArgumentException("Buffer size < 0");
    }
    
    private byte[] copyArray(byte[] paramArrayOfByte, int paramInt)
    {
      byte[] arrayOfByte = new byte[paramInt];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, Math.min(paramArrayOfByte.length, paramInt));
      return arrayOfByte;
    }
    
    private void flushFullBuffer(int paramInt)
    {
      this.flushedBuffers.add(new LiteralByteString(this.buffer));
      int i = this.flushedBuffersTotalBytes + this.buffer.length;
      this.flushedBuffersTotalBytes = i;
      this.buffer = new byte[Math.max(this.initialCapacity, Math.max(paramInt, i >>> 1))];
      this.bufferPos = 0;
    }
    
    private void flushLastBuffer()
    {
      int i = this.bufferPos;
      byte[] arrayOfByte = this.buffer;
      if (i < arrayOfByte.length)
      {
        if (i > 0)
        {
          arrayOfByte = copyArray(arrayOfByte, i);
          this.flushedBuffers.add(new LiteralByteString(arrayOfByte));
        }
      }
      else
      {
        this.flushedBuffers.add(new LiteralByteString(this.buffer));
        this.buffer = EMPTY_BYTE_ARRAY;
      }
      this.flushedBuffersTotalBytes += this.bufferPos;
      this.bufferPos = 0;
    }
    
    public int size()
    {
      try
      {
        int i = this.flushedBuffersTotalBytes;
        int j = this.bufferPos;
        return i + j;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public ByteString toByteString()
    {
      try
      {
        flushLastBuffer();
        ByteString localByteString = ByteString.copyFrom(this.flushedBuffers);
        return localByteString;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public String toString()
    {
      return String.format("<ByteString.Output@%s size=%d>", new Object[] { Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()) });
    }
    
    public void write(int paramInt)
    {
      try
      {
        if (this.bufferPos == this.buffer.length) {
          flushFullBuffer(1);
        }
        byte[] arrayOfByte = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = (i + 1);
        arrayOfByte[i] = ((byte)(byte)paramInt);
        return;
      }
      finally {}
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      try
      {
        if (paramInt2 <= this.buffer.length - this.bufferPos)
        {
          System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.bufferPos, paramInt2);
          this.bufferPos += paramInt2;
        }
        else
        {
          int i = this.buffer.length - this.bufferPos;
          System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.bufferPos, i);
          paramInt2 -= i;
          flushFullBuffer(paramInt2);
          System.arraycopy(paramArrayOfByte, paramInt1 + i, this.buffer, 0, paramInt2);
          this.bufferPos = paramInt2;
        }
        return;
      }
      finally {}
    }
  }
}
