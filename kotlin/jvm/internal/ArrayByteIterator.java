package kotlin.jvm.internal;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.ByteIterator;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\000\n\002\020\005\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\t\020\007\032\0020\bH?\002J\b\020\t\032\0020\nH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000?\006\013"}, d2={"Lkotlin/jvm/internal/ArrayByteIterator;", "Lkotlin/collections/ByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextByte", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
final class ArrayByteIterator
  extends ByteIterator
{
  private final byte[] array;
  private int index;
  
  public ArrayByteIterator(byte[] paramArrayOfByte)
  {
    this.array = paramArrayOfByte;
  }
  
  public boolean hasNext()
  {
    boolean bool;
    if (this.index < this.array.length) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public byte nextByte()
  {
    try
    {
      byte[] arrayOfByte = this.array;
      int i = this.index;
      this.index = (i + 1);
      byte b = arrayOfByte[i];
      return b;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      this.index -= 1;
      throw ((Throwable)new NoSuchElementException(localArrayIndexOutOfBoundsException.getMessage()));
    }
  }
}
