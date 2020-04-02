package okhttp3.internal.ws;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer.UnsafeCursor;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\b\n\002\b\b\n\002\020\t\n\002\b\021\n\002\020\002\n\000\n\002\030\002\n\002\020\022\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\034\032\0020\0042\006\020\035\032\0020\004J\020\020\036\032\004\030\0010\0042\006\020\037\032\0020\006J\026\020 \032\0020!2\006\020\"\032\0020#2\006\020\035\032\0020$J\016\020%\032\0020!2\006\020\037\032\0020\006R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?T?\006\002\n\000R\016\020\007\032\0020\006X?T?\006\002\n\000R\016\020\b\032\0020\006X?T?\006\002\n\000R\016\020\t\032\0020\006X?T?\006\002\n\000R\016\020\n\032\0020\006X?T?\006\002\n\000R\016\020\013\032\0020\006X?T?\006\002\n\000R\016\020\f\032\0020\006X?T?\006\002\n\000R\016\020\r\032\0020\006X?T?\006\002\n\000R\016\020\016\032\0020\017X?T?\006\002\n\000R\016\020\020\032\0020\006X?T?\006\002\n\000R\016\020\021\032\0020\006X?T?\006\002\n\000R\016\020\022\032\0020\006X?T?\006\002\n\000R\016\020\023\032\0020\006X?T?\006\002\n\000R\016\020\024\032\0020\006X?T?\006\002\n\000R\016\020\025\032\0020\006X?T?\006\002\n\000R\016\020\026\032\0020\006X?T?\006\002\n\000R\016\020\027\032\0020\006X?T?\006\002\n\000R\016\020\030\032\0020\017X?T?\006\002\n\000R\016\020\031\032\0020\006X?T?\006\002\n\000R\016\020\032\032\0020\006X?T?\006\002\n\000R\016\020\033\032\0020\017X?T?\006\002\n\000?\006&"}, d2={"Lokhttp3/internal/ws/WebSocketProtocol;", "", "()V", "ACCEPT_MAGIC", "", "B0_FLAG_FIN", "", "B0_FLAG_RSV1", "B0_FLAG_RSV2", "B0_FLAG_RSV3", "B0_MASK_OPCODE", "B1_FLAG_MASK", "B1_MASK_LENGTH", "CLOSE_CLIENT_GOING_AWAY", "CLOSE_MESSAGE_MAX", "", "CLOSE_NO_STATUS_CODE", "OPCODE_BINARY", "OPCODE_CONTINUATION", "OPCODE_CONTROL_CLOSE", "OPCODE_CONTROL_PING", "OPCODE_CONTROL_PONG", "OPCODE_FLAG_CONTROL", "OPCODE_TEXT", "PAYLOAD_BYTE_MAX", "PAYLOAD_LONG", "PAYLOAD_SHORT", "PAYLOAD_SHORT_MAX", "acceptHeader", "key", "closeCodeExceptionMessage", "code", "toggleMask", "", "cursor", "Lokio/Buffer$UnsafeCursor;", "", "validateCloseCode", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketProtocol
{
  public static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
  public static final int B0_FLAG_FIN = 128;
  public static final int B0_FLAG_RSV1 = 64;
  public static final int B0_FLAG_RSV2 = 32;
  public static final int B0_FLAG_RSV3 = 16;
  public static final int B0_MASK_OPCODE = 15;
  public static final int B1_FLAG_MASK = 128;
  public static final int B1_MASK_LENGTH = 127;
  public static final int CLOSE_CLIENT_GOING_AWAY = 1001;
  public static final long CLOSE_MESSAGE_MAX = 123L;
  public static final int CLOSE_NO_STATUS_CODE = 1005;
  public static final WebSocketProtocol INSTANCE = new WebSocketProtocol();
  public static final int OPCODE_BINARY = 2;
  public static final int OPCODE_CONTINUATION = 0;
  public static final int OPCODE_CONTROL_CLOSE = 8;
  public static final int OPCODE_CONTROL_PING = 9;
  public static final int OPCODE_CONTROL_PONG = 10;
  public static final int OPCODE_FLAG_CONTROL = 8;
  public static final int OPCODE_TEXT = 1;
  public static final long PAYLOAD_BYTE_MAX = 125L;
  public static final int PAYLOAD_LONG = 127;
  public static final int PAYLOAD_SHORT = 126;
  public static final long PAYLOAD_SHORT_MAX = 65535L;
  
  private WebSocketProtocol() {}
  
  public final String acceptHeader(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "key");
    ByteString.Companion localCompanion = ByteString.Companion;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
    return localCompanion.encodeUtf8(localStringBuilder.toString()).sha1().base64();
  }
  
  public final String closeCodeExceptionMessage(int paramInt)
  {
    Object localObject;
    if ((paramInt >= 1000) && (paramInt < 5000))
    {
      if (((1004 <= paramInt) && (1006 >= paramInt)) || ((1015 <= paramInt) && (2999 >= paramInt)))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Code ");
        ((StringBuilder)localObject).append(paramInt);
        ((StringBuilder)localObject).append(" is reserved and may not be used.");
        localObject = ((StringBuilder)localObject).toString();
      }
      else
      {
        localObject = null;
      }
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Code must be in range [1000,5000): ");
      ((StringBuilder)localObject).append(paramInt);
      localObject = ((StringBuilder)localObject).toString();
    }
    return localObject;
  }
  
  public final void toggleMask(Buffer.UnsafeCursor paramUnsafeCursor, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramUnsafeCursor, "cursor");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "key");
    int i = paramArrayOfByte.length;
    int j = 0;
    do
    {
      byte[] arrayOfByte = paramUnsafeCursor.data;
      int k = paramUnsafeCursor.start;
      int m = paramUnsafeCursor.end;
      int n = j;
      if (arrayOfByte != null) {
        for (;;)
        {
          n = j;
          if (k >= m) {
            break;
          }
          j %= i;
          arrayOfByte[k] = ((byte)(byte)(arrayOfByte[k] ^ paramArrayOfByte[j]));
          k++;
          j++;
        }
      }
      j = n;
    } while (paramUnsafeCursor.next() != -1);
  }
  
  public final void validateCloseCode(int paramInt)
  {
    String str = closeCodeExceptionMessage(paramInt);
    if (str == null) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt == 0)
    {
      if (str == null) {
        Intrinsics.throwNpe();
      }
      throw ((Throwable)new IllegalArgumentException(str.toString()));
    }
  }
}
