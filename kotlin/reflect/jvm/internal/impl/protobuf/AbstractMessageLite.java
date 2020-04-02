package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractMessageLite
  implements MessageLite
{
  protected int memoizedHashCode = 0;
  
  public AbstractMessageLite() {}
  
  UninitializedMessageException newUninitializedMessageException()
  {
    return new UninitializedMessageException(this);
  }
  
  public void writeDelimitedTo(OutputStream paramOutputStream)
    throws IOException
  {
    int i = getSerializedSize();
    paramOutputStream = CodedOutputStream.newInstance(paramOutputStream, CodedOutputStream.computePreferredBufferSize(CodedOutputStream.computeRawVarint32Size(i) + i));
    paramOutputStream.writeRawVarint32(i);
    writeTo(paramOutputStream);
    paramOutputStream.flush();
  }
  
  public static abstract class Builder<BuilderType extends Builder>
    implements MessageLite.Builder
  {
    public Builder() {}
    
    protected static UninitializedMessageException newUninitializedMessageException(MessageLite paramMessageLite)
    {
      return new UninitializedMessageException(paramMessageLite);
    }
    
    public abstract BuilderType clone();
    
    public abstract BuilderType mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException;
    
    static final class LimitedInputStream
      extends FilterInputStream
    {
      private int limit;
      
      LimitedInputStream(InputStream paramInputStream, int paramInt)
      {
        super();
        this.limit = paramInt;
      }
      
      public int available()
        throws IOException
      {
        return Math.min(super.available(), this.limit);
      }
      
      public int read()
        throws IOException
      {
        if (this.limit <= 0) {
          return -1;
        }
        int i = super.read();
        if (i >= 0) {
          this.limit -= 1;
        }
        return i;
      }
      
      public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
        throws IOException
      {
        int i = this.limit;
        if (i <= 0) {
          return -1;
        }
        paramInt1 = super.read(paramArrayOfByte, paramInt1, Math.min(paramInt2, i));
        if (paramInt1 >= 0) {
          this.limit -= paramInt1;
        }
        return paramInt1;
      }
      
      public long skip(long paramLong)
        throws IOException
      {
        paramLong = super.skip(Math.min(paramLong, this.limit));
        if (paramLong >= 0L) {
          this.limit = ((int)(this.limit - paramLong));
        }
        return paramLong;
      }
    }
  }
}
