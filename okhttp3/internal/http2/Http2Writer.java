package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\000\\\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\021\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\f\n\002\020\t\n\002\b\003\030\000 :2\0020\001:\001:B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\016\020\020\032\0020\0212\006\020\022\032\0020\023J\b\020\024\032\0020\021H\026J\006\020\025\032\0020\021J(\020\026\032\0020\0212\006\020\027\032\0020\0052\006\020\030\032\0020\0172\b\020\031\032\004\030\0010\t2\006\020\032\032\0020\017J(\020\033\032\0020\0212\006\020\030\032\0020\0172\006\020\034\032\0020\0172\b\020\035\032\004\030\0010\t2\006\020\032\032\0020\017J\006\020\036\032\0020\021J&\020\037\032\0020\0212\006\020\030\032\0020\0172\006\020 \032\0020\0172\006\020!\032\0020\0172\006\020\034\032\0020\017J\036\020\"\032\0020\0212\006\020#\032\0020\0172\006\020$\032\0020%2\006\020&\032\0020'J$\020(\032\0020\0212\006\020\027\032\0020\0052\006\020\030\032\0020\0172\f\020)\032\b\022\004\022\0020+0*J\006\020,\032\0020\017J\036\020-\032\0020\0212\006\020.\032\0020\0052\006\020/\032\0020\0172\006\0200\032\0020\017J$\0201\032\0020\0212\006\020\030\032\0020\0172\006\0202\032\0020\0172\f\0203\032\b\022\004\022\0020+0*J\026\0204\032\0020\0212\006\020\030\032\0020\0172\006\020$\032\0020%J\016\0205\032\0020\0212\006\0205\032\0020\023J\026\0206\032\0020\0212\006\020\030\032\0020\0172\006\0207\032\00208J\030\0209\032\0020\0212\006\020\030\032\0020\0172\006\020\032\032\00208H\002R\016\020\004\032\0020\005X?\004?\006\002\n\000R\016\020\007\032\0020\005X?\016?\006\002\n\000R\016\020\b\032\0020\tX?\004?\006\002\n\000R\021\020\n\032\0020\013?\006\b\n\000\032\004\b\f\020\rR\016\020\016\032\0020\017X?\016?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006;"}, d2={"Lokhttp3/internal/http2/Http2Writer;", "Ljava/io/Closeable;", "sink", "Lokio/BufferedSink;", "client", "", "(Lokio/BufferedSink;Z)V", "closed", "hpackBuffer", "Lokio/Buffer;", "hpackWriter", "Lokhttp3/internal/http2/Hpack$Writer;", "getHpackWriter", "()Lokhttp3/internal/http2/Hpack$Writer;", "maxFrameSize", "", "applyAndAckSettings", "", "peerSettings", "Lokhttp3/internal/http2/Settings;", "close", "connectionPreface", "data", "outFinished", "streamId", "source", "byteCount", "dataFrame", "flags", "buffer", "flush", "frameHeader", "length", "type", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "", "headers", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "maxDataLength", "ping", "ack", "payload1", "payload2", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "settings", "windowUpdate", "windowSizeIncrement", "", "writeContinuationFrames", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2Writer
  implements Closeable
{
  public static final Companion Companion = new Companion(null);
  private static final Logger logger = Logger.getLogger(Http2.class.getName());
  private final boolean client;
  private boolean closed;
  private final Buffer hpackBuffer;
  private final Hpack.Writer hpackWriter;
  private int maxFrameSize;
  private final BufferedSink sink;
  
  public Http2Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    this.sink = paramBufferedSink;
    this.client = paramBoolean;
    paramBufferedSink = new Buffer();
    this.hpackBuffer = paramBufferedSink;
    this.maxFrameSize = 16384;
    this.hpackWriter = new Hpack.Writer(0, false, paramBufferedSink, 3, null);
  }
  
  private final void writeContinuationFrames(int paramInt, long paramLong)
    throws IOException
  {
    while (paramLong > 0L)
    {
      long l = Math.min(this.maxFrameSize, paramLong);
      paramLong -= l;
      int i = (int)l;
      int j;
      if (paramLong == 0L) {
        j = 4;
      } else {
        j = 0;
      }
      frameHeader(paramInt, i, 9, j);
      this.sink.write(this.hpackBuffer, l);
    }
  }
  
  public final void applyAndAckSettings(Settings paramSettings)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramSettings, "peerSettings");
      if (!this.closed)
      {
        this.maxFrameSize = paramSettings.getMaxFrameSize(this.maxFrameSize);
        if (paramSettings.getHeaderTableSize() != -1) {
          this.hpackWriter.resizeHeaderTable(paramSettings.getHeaderTableSize());
        }
        frameHeader(0, 0, 4, 1);
        this.sink.flush();
        return;
      }
      paramSettings = new java/io/IOException;
      paramSettings.<init>("closed");
      throw ((Throwable)paramSettings);
    }
    finally {}
  }
  
  public void close()
    throws IOException
  {
    try
    {
      this.closed = true;
      this.sink.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void connectionPreface()
    throws IOException
  {
    try
    {
      if (!this.closed)
      {
        boolean bool = this.client;
        if (!bool) {
          return;
        }
        if (logger.isLoggable(Level.FINE))
        {
          Logger localLogger = logger;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append(">> CONNECTION ");
          ((StringBuilder)localObject1).append(Http2.CONNECTION_PREFACE.hex());
          localLogger.fine(Util.format(((StringBuilder)localObject1).toString(), new Object[0]));
        }
        this.sink.write(Http2.CONNECTION_PREFACE);
        this.sink.flush();
        return;
      }
      Object localObject1 = new java/io/IOException;
      ((IOException)localObject1).<init>("closed");
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public final void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
    throws IOException
  {
    try
    {
      if (!this.closed)
      {
        int i = 0;
        if (paramBoolean) {
          i = 1;
        }
        dataFrame(paramInt1, i, paramBuffer, paramInt2);
        return;
      }
      paramBuffer = new java/io/IOException;
      paramBuffer.<init>("closed");
      throw ((Throwable)paramBuffer);
    }
    finally {}
  }
  
  public final void dataFrame(int paramInt1, int paramInt2, Buffer paramBuffer, int paramInt3)
    throws IOException
  {
    frameHeader(paramInt1, paramInt3, 0, paramInt2);
    if (paramInt3 > 0)
    {
      BufferedSink localBufferedSink = this.sink;
      if (paramBuffer == null) {
        Intrinsics.throwNpe();
      }
      localBufferedSink.write(paramBuffer, paramInt3);
    }
  }
  
  public final void flush()
    throws IOException
  {
    try
    {
      if (!this.closed)
      {
        this.sink.flush();
        return;
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw ((Throwable)localIOException);
    }
    finally {}
  }
  
  public final void frameHeader(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IOException
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.fine(Http2.INSTANCE.frameLog(false, paramInt1, paramInt2, paramInt3, paramInt4));
    }
    int i = this.maxFrameSize;
    int j = 1;
    if (paramInt2 <= i) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (((int)2147483648L & paramInt1) == 0) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        Util.writeMedium(this.sink, paramInt2);
        this.sink.writeByte(paramInt3 & 0xFF);
        this.sink.writeByte(paramInt4 & 0xFF);
        this.sink.writeInt(paramInt1 & 0x7FFFFFFF);
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("reserved bit set: ");
      localStringBuilder.append(paramInt1);
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("FRAME_SIZE_ERROR length > ");
    localStringBuilder.append(this.maxFrameSize);
    localStringBuilder.append(": ");
    localStringBuilder.append(paramInt2);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  public final Hpack.Writer getHpackWriter()
  {
    return this.hpackWriter;
  }
  
  public final void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramErrorCode, "errorCode");
      Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "debugData");
      if (!this.closed)
      {
        int i = paramErrorCode.getHttpCode();
        int j = 0;
        if (i != -1) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          frameHeader(0, paramArrayOfByte.length + 8, 7, 0);
          this.sink.writeInt(paramInt);
          this.sink.writeInt(paramErrorCode.getHttpCode());
          paramInt = j;
          if (paramArrayOfByte.length == 0) {
            paramInt = 1;
          }
          if ((paramInt ^ 0x1) != 0) {
            this.sink.write(paramArrayOfByte);
          }
          this.sink.flush();
          return;
        }
        paramErrorCode = new java/lang/IllegalArgumentException;
        paramErrorCode.<init>("errorCode.httpCode == -1".toString());
        throw ((Throwable)paramErrorCode);
      }
      paramErrorCode = new java/io/IOException;
      paramErrorCode.<init>("closed");
      throw ((Throwable)paramErrorCode);
    }
    finally {}
  }
  
  public final void headers(boolean paramBoolean, int paramInt, List<Header> paramList)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramList, "headerBlock");
      if (!this.closed)
      {
        this.hpackWriter.writeHeaders(paramList);
        long l1 = this.hpackBuffer.size();
        long l2 = Math.min(this.maxFrameSize, l1);
        boolean bool = l1 < l2;
        int i;
        if (!bool) {
          i = 4;
        } else {
          i = 0;
        }
        int j = i;
        if (paramBoolean) {
          j = i | 0x1;
        }
        frameHeader(paramInt, (int)l2, 1, j);
        this.sink.write(this.hpackBuffer, l2);
        if (bool) {
          writeContinuationFrames(paramInt, l1 - l2);
        }
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw ((Throwable)paramList);
    }
    finally {}
  }
  
  public final int maxDataLength()
  {
    return this.maxFrameSize;
  }
  
  public final void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      if (!this.closed)
      {
        int i;
        if (paramBoolean) {
          i = 1;
        } else {
          i = 0;
        }
        frameHeader(0, 8, 6, i);
        this.sink.writeInt(paramInt1);
        this.sink.writeInt(paramInt2);
        this.sink.flush();
        return;
      }
      IOException localIOException = new java/io/IOException;
      localIOException.<init>("closed");
      throw ((Throwable)localIOException);
    }
    finally {}
  }
  
  public final void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
      if (!this.closed)
      {
        this.hpackWriter.writeHeaders(paramList);
        long l1 = this.hpackBuffer.size();
        int i = (int)Math.min(this.maxFrameSize - 4L, l1);
        long l2 = i;
        boolean bool = l1 < l2;
        int j;
        if (!bool) {
          j = 4;
        } else {
          j = 0;
        }
        frameHeader(paramInt1, i + 4, 5, j);
        this.sink.writeInt(paramInt2 & 0x7FFFFFFF);
        this.sink.write(this.hpackBuffer, l2);
        if (bool) {
          writeContinuationFrames(paramInt1, l1 - l2);
        }
        return;
      }
      paramList = new java/io/IOException;
      paramList.<init>("closed");
      throw ((Throwable)paramList);
    }
    finally {}
  }
  
  public final void rstStream(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramErrorCode, "errorCode");
      if (!this.closed)
      {
        int i;
        if (paramErrorCode.getHttpCode() != -1) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          frameHeader(paramInt, 4, 3, 0);
          this.sink.writeInt(paramErrorCode.getHttpCode());
          this.sink.flush();
          return;
        }
        paramErrorCode = new java/lang/IllegalArgumentException;
        paramErrorCode.<init>("Failed requirement.".toString());
        throw ((Throwable)paramErrorCode);
      }
      paramErrorCode = new java/io/IOException;
      paramErrorCode.<init>("closed");
      throw ((Throwable)paramErrorCode);
    }
    finally {}
  }
  
  public final void settings(Settings paramSettings)
    throws IOException
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramSettings, "settings");
      if (!this.closed)
      {
        int i = paramSettings.size();
        int j = 0;
        frameHeader(0, i * 6, 4, 0);
        while (j < 10)
        {
          if (paramSettings.isSet(j))
          {
            if (j != 4)
            {
              if (j != 7) {
                i = j;
              } else {
                i = 4;
              }
            }
            else {
              i = 3;
            }
            this.sink.writeShort(i);
            this.sink.writeInt(paramSettings.get(j));
          }
          j++;
        }
        this.sink.flush();
        return;
      }
      paramSettings = new java/io/IOException;
      paramSettings.<init>("closed");
      throw ((Throwable)paramSettings);
    }
    finally {}
  }
  
  public final void windowUpdate(int paramInt, long paramLong)
    throws IOException
  {
    try
    {
      if (!this.closed)
      {
        int i;
        if ((paramLong != 0L) && (paramLong <= 2147483647L)) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          frameHeader(paramInt, 4, 8, 0);
          this.sink.writeInt((int)paramLong);
          this.sink.flush();
          return;
        }
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: ");
        ((StringBuilder)localObject1).append(paramLong);
        String str = ((StringBuilder)localObject1).toString();
        localObject1 = new java/lang/IllegalArgumentException;
        ((IllegalArgumentException)localObject1).<init>(str.toString());
        throw ((Throwable)localObject1);
      }
      Object localObject1 = new java/io/IOException;
      ((IOException)localObject1).<init>("closed");
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\026\020\003\032\n \005*\004\030\0010\0040\004X?\004?\006\002\n\000?\006\006"}, d2={"Lokhttp3/internal/http2/Http2Writer$Companion;", "", "()V", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
