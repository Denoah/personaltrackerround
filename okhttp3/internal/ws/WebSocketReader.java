package okhttp3.internal.ws;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSource;
import okio.ByteString;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\007\b\000\030\0002\0020\001:\001$B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007?\006\002\020\bJ\006\020\035\032\0020\036J\b\020\037\032\0020\036H\002J\b\020 \032\0020\036H\002J\b\020!\032\0020\036H\002J\b\020\"\032\0020\036H\002J\b\020#\032\0020\036H\002R\032\020\t\032\0020\003X?\016?\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\rR\016\020\016\032\0020\017X?\004?\006\002\n\000R\016\020\006\032\0020\007X?\004?\006\002\n\000R\016\020\020\032\0020\021X?\016?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\022\032\0020\003X?\016?\006\002\n\000R\016\020\023\032\0020\003X?\016?\006\002\n\000R\020\020\024\032\004\030\0010\025X?\004?\006\002\n\000R\020\020\026\032\004\030\0010\027X?\004?\006\002\n\000R\016\020\030\032\0020\017X?\004?\006\002\n\000R\016\020\031\032\0020\032X?\016?\006\002\n\000R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\033\020\034?\006%"}, d2={"Lokhttp3/internal/ws/WebSocketReader;", "", "isClient", "", "source", "Lokio/BufferedSource;", "frameCallback", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "(ZLokio/BufferedSource;Lokhttp3/internal/ws/WebSocketReader$FrameCallback;)V", "closed", "getClosed", "()Z", "setClosed", "(Z)V", "controlFrameBuffer", "Lokio/Buffer;", "frameLength", "", "isControlFrame", "isFinalFrame", "maskCursor", "Lokio/Buffer$UnsafeCursor;", "maskKey", "", "messageFrameBuffer", "opcode", "", "getSource", "()Lokio/BufferedSource;", "processNextFrame", "", "readControlFrame", "readHeader", "readMessage", "readMessageFrame", "readUntilNonControlFrame", "FrameCallback", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketReader
{
  private boolean closed;
  private final Buffer controlFrameBuffer;
  private final FrameCallback frameCallback;
  private long frameLength;
  private final boolean isClient;
  private boolean isControlFrame;
  private boolean isFinalFrame;
  private final Buffer.UnsafeCursor maskCursor;
  private final byte[] maskKey;
  private final Buffer messageFrameBuffer;
  private int opcode;
  private final BufferedSource source;
  
  public WebSocketReader(boolean paramBoolean, BufferedSource paramBufferedSource, FrameCallback paramFrameCallback)
  {
    this.isClient = paramBoolean;
    this.source = paramBufferedSource;
    this.frameCallback = paramFrameCallback;
    this.controlFrameBuffer = new Buffer();
    this.messageFrameBuffer = new Buffer();
    paramBoolean = this.isClient;
    paramFrameCallback = null;
    if (paramBoolean) {
      paramBufferedSource = null;
    } else {
      paramBufferedSource = new byte[4];
    }
    this.maskKey = paramBufferedSource;
    if (this.isClient) {
      paramBufferedSource = paramFrameCallback;
    } else {
      paramBufferedSource = new Buffer.UnsafeCursor();
    }
    this.maskCursor = paramBufferedSource;
  }
  
  private final void readControlFrame()
    throws IOException
  {
    long l = this.frameLength;
    Object localObject1;
    Object localObject2;
    if (l > 0L)
    {
      this.source.readFully(this.controlFrameBuffer, l);
      if (!this.isClient)
      {
        localObject1 = this.controlFrameBuffer;
        localObject2 = this.maskCursor;
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        ((Buffer)localObject1).readAndWriteUnsafe((Buffer.UnsafeCursor)localObject2);
        this.maskCursor.seek(0L);
        WebSocketProtocol localWebSocketProtocol = WebSocketProtocol.INSTANCE;
        localObject1 = this.maskCursor;
        localObject2 = this.maskKey;
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        localWebSocketProtocol.toggleMask((Buffer.UnsafeCursor)localObject1, (byte[])localObject2);
        this.maskCursor.close();
      }
    }
    switch (this.opcode)
    {
    default: 
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Unknown control opcode: ");
      ((StringBuilder)localObject2).append(Util.toHexString(this.opcode));
      throw ((Throwable)new ProtocolException(((StringBuilder)localObject2).toString()));
    case 10: 
      this.frameCallback.onReadPong(this.controlFrameBuffer.readByteString());
      break;
    case 9: 
      this.frameCallback.onReadPing(this.controlFrameBuffer.readByteString());
      break;
    case 8: 
      int i = 1005;
      l = this.controlFrameBuffer.size();
      if (l == 1L) {
        break label314;
      }
      if (l != 0L)
      {
        i = this.controlFrameBuffer.readShort();
        localObject2 = this.controlFrameBuffer.readUtf8();
        localObject1 = WebSocketProtocol.INSTANCE.closeCodeExceptionMessage(i);
        if (localObject1 != null) {
          throw ((Throwable)new ProtocolException((String)localObject1));
        }
      }
      else
      {
        localObject2 = "";
      }
      this.frameCallback.onReadClose(i, (String)localObject2);
      this.closed = true;
    }
    return;
    label314:
    throw ((Throwable)new ProtocolException("Malformed close payload length of 1."));
  }
  
  private final void readHeader()
    throws IOException, ProtocolException
  {
    if (!this.closed)
    {
      long l = this.source.timeout().timeoutNanos();
      this.source.timeout().clearTimeout();
      try
      {
        int i = Util.and(this.source.readByte(), 255);
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
        this.opcode = (i & 0xF);
        boolean bool1 = true;
        boolean bool2;
        if ((i & 0x80) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        this.isFinalFrame = bool2;
        if ((i & 0x8) != 0) {
          bool2 = true;
        } else {
          bool2 = false;
        }
        this.isControlFrame = bool2;
        if ((bool2) && (!this.isFinalFrame)) {
          throw ((Throwable)new ProtocolException("Control frames must be final."));
        }
        int j;
        if ((i & 0x40) != 0) {
          j = 1;
        } else {
          j = 0;
        }
        int k;
        if ((i & 0x20) != 0) {
          k = 1;
        } else {
          k = 0;
        }
        if ((i & 0x10) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        if ((j == 0) && (k == 0) && (i == 0))
        {
          j = Util.and(this.source.readByte(), 255);
          if ((j & 0x80) != 0) {
            bool2 = bool1;
          } else {
            bool2 = false;
          }
          Object localObject1;
          if (bool2 == this.isClient)
          {
            if (this.isClient) {
              localObject1 = "Server-sent frames must not be masked.";
            } else {
              localObject1 = "Client-sent frames must be masked.";
            }
            throw ((Throwable)new ProtocolException((String)localObject1));
          }
          l = j & 0x7F;
          this.frameLength = l;
          if (l == 126)
          {
            this.frameLength = Util.and(this.source.readShort(), 65535);
          }
          else if (l == 127)
          {
            l = this.source.readLong();
            this.frameLength = l;
            if (l < 0L)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("Frame length 0x");
              ((StringBuilder)localObject1).append(Util.toHexString(this.frameLength));
              ((StringBuilder)localObject1).append(" > 0x7FFFFFFFFFFFFFFF");
              throw ((Throwable)new ProtocolException(((StringBuilder)localObject1).toString()));
            }
          }
          if ((this.isControlFrame) && (this.frameLength > 125L)) {
            throw ((Throwable)new ProtocolException("Control frame must be less than 125B."));
          }
          if (bool2)
          {
            BufferedSource localBufferedSource = this.source;
            localObject1 = this.maskKey;
            if (localObject1 == null) {
              Intrinsics.throwNpe();
            }
            localBufferedSource.readFully((byte[])localObject1);
          }
          return;
        }
        throw ((Throwable)new ProtocolException("Reserved flags are unsupported."));
      }
      finally
      {
        this.source.timeout().timeout(l, TimeUnit.NANOSECONDS);
      }
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  private final void readMessage()
    throws IOException
  {
    while (!this.closed)
    {
      long l = this.frameLength;
      Object localObject1;
      if (l > 0L)
      {
        this.source.readFully(this.messageFrameBuffer, l);
        if (!this.isClient)
        {
          localObject1 = this.messageFrameBuffer;
          Object localObject2 = this.maskCursor;
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          ((Buffer)localObject1).readAndWriteUnsafe((Buffer.UnsafeCursor)localObject2);
          this.maskCursor.seek(this.messageFrameBuffer.size() - this.frameLength);
          localObject2 = WebSocketProtocol.INSTANCE;
          localObject1 = this.maskCursor;
          byte[] arrayOfByte = this.maskKey;
          if (arrayOfByte == null) {
            Intrinsics.throwNpe();
          }
          ((WebSocketProtocol)localObject2).toggleMask((Buffer.UnsafeCursor)localObject1, arrayOfByte);
          this.maskCursor.close();
        }
      }
      if (this.isFinalFrame) {
        return;
      }
      readUntilNonControlFrame();
      if (this.opcode != 0)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Expected continuation opcode. Got: ");
        ((StringBuilder)localObject1).append(Util.toHexString(this.opcode));
        throw ((Throwable)new ProtocolException(((StringBuilder)localObject1).toString()));
      }
    }
    throw ((Throwable)new IOException("closed"));
  }
  
  private final void readMessageFrame()
    throws IOException
  {
    int i = this.opcode;
    if ((i != 1) && (i != 2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unknown opcode: ");
      localStringBuilder.append(Util.toHexString(i));
      throw ((Throwable)new ProtocolException(localStringBuilder.toString()));
    }
    readMessage();
    if (i == 1) {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.readUtf8());
    } else {
      this.frameCallback.onReadMessage(this.messageFrameBuffer.readByteString());
    }
  }
  
  private final void readUntilNonControlFrame()
    throws IOException
  {
    while (!this.closed)
    {
      readHeader();
      if (!this.isControlFrame) {
        break;
      }
      readControlFrame();
    }
  }
  
  public final boolean getClosed()
  {
    return this.closed;
  }
  
  public final BufferedSource getSource()
  {
    return this.source;
  }
  
  public final void processNextFrame()
    throws IOException
  {
    readHeader();
    if (this.isControlFrame) {
      readControlFrame();
    } else {
      readMessageFrame();
    }
  }
  
  public final void setClosed(boolean paramBoolean)
  {
    this.closed = paramBoolean;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\004\bf\030\0002\0020\001J\030\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\007H&J\020\020\b\032\0020\0032\006\020\t\032\0020\007H&J\020\020\b\032\0020\0032\006\020\n\032\0020\013H&J\020\020\f\032\0020\0032\006\020\r\032\0020\013H&J\020\020\016\032\0020\0032\006\020\r\032\0020\013H&?\006\017"}, d2={"Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "", "onReadClose", "", "code", "", "reason", "", "onReadMessage", "text", "bytes", "Lokio/ByteString;", "onReadPing", "payload", "onReadPong", "okhttp"}, k=1, mv={1, 1, 16})
  public static abstract interface FrameCallback
  {
    public abstract void onReadClose(int paramInt, String paramString);
    
    public abstract void onReadMessage(String paramString)
      throws IOException;
    
    public abstract void onReadMessage(ByteString paramByteString)
      throws IOException;
    
    public abstract void onReadPing(ByteString paramByteString);
    
    public abstract void onReadPong(ByteString paramByteString);
  }
}
