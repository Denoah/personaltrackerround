package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\021\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\030\n\002\020\013\n\002\b\003\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\026\020\037\032\0020\0052\006\020 \032\0020\0132\006\020!\032\0020\013J.\020\"\032\0020\0052\006\020#\032\0020$2\006\020%\032\0020\0132\006\020&\032\0020\0132\006\020 \032\0020\0132\006\020!\032\0020\013R\026\020\003\032\b\022\004\022\0020\0050\004X?\004?\006\004\n\002\020\006R\020\020\007\032\0020\b8\006X?\004?\006\002\n\000R\030\020\t\032\n\022\006\022\004\030\0010\0050\004X?\004?\006\004\n\002\020\006R\016\020\n\032\0020\013X?T?\006\002\n\000R\016\020\f\032\0020\013X?T?\006\002\n\000R\016\020\r\032\0020\013X?T?\006\002\n\000R\016\020\016\032\0020\013X?T?\006\002\n\000R\016\020\017\032\0020\013X?T?\006\002\n\000R\016\020\020\032\0020\013X?T?\006\002\n\000R\016\020\021\032\0020\013X?T?\006\002\n\000R\016\020\022\032\0020\013X?T?\006\002\n\000R\026\020\023\032\b\022\004\022\0020\0050\004X?\004?\006\004\n\002\020\006R\016\020\024\032\0020\013X?T?\006\002\n\000R\016\020\025\032\0020\013X?T?\006\002\n\000R\016\020\026\032\0020\013X?T?\006\002\n\000R\016\020\027\032\0020\013X?T?\006\002\n\000R\016\020\030\032\0020\013X?T?\006\002\n\000R\016\020\031\032\0020\013X?T?\006\002\n\000R\016\020\032\032\0020\013X?T?\006\002\n\000R\016\020\033\032\0020\013X?T?\006\002\n\000R\016\020\034\032\0020\013X?T?\006\002\n\000R\016\020\035\032\0020\013X?T?\006\002\n\000R\016\020\036\032\0020\013X?T?\006\002\n\000?\006'"}, d2={"Lokhttp3/internal/http2/Http2;", "", "()V", "BINARY", "", "", "[Ljava/lang/String;", "CONNECTION_PREFACE", "Lokio/ByteString;", "FLAGS", "FLAG_ACK", "", "FLAG_COMPRESSED", "FLAG_END_HEADERS", "FLAG_END_PUSH_PROMISE", "FLAG_END_STREAM", "FLAG_NONE", "FLAG_PADDED", "FLAG_PRIORITY", "FRAME_NAMES", "INITIAL_MAX_FRAME_SIZE", "TYPE_CONTINUATION", "TYPE_DATA", "TYPE_GOAWAY", "TYPE_HEADERS", "TYPE_PING", "TYPE_PRIORITY", "TYPE_PUSH_PROMISE", "TYPE_RST_STREAM", "TYPE_SETTINGS", "TYPE_WINDOW_UPDATE", "formatFlags", "type", "flags", "frameLog", "inbound", "", "streamId", "length", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2
{
  private static final String[] BINARY;
  public static final ByteString CONNECTION_PREFACE;
  private static final String[] FLAGS;
  public static final int FLAG_ACK = 1;
  public static final int FLAG_COMPRESSED = 32;
  public static final int FLAG_END_HEADERS = 4;
  public static final int FLAG_END_PUSH_PROMISE = 4;
  public static final int FLAG_END_STREAM = 1;
  public static final int FLAG_NONE = 0;
  public static final int FLAG_PADDED = 8;
  public static final int FLAG_PRIORITY = 32;
  private static final String[] FRAME_NAMES;
  public static final int INITIAL_MAX_FRAME_SIZE = 16384;
  public static final Http2 INSTANCE = new Http2();
  public static final int TYPE_CONTINUATION = 9;
  public static final int TYPE_DATA = 0;
  public static final int TYPE_GOAWAY = 7;
  public static final int TYPE_HEADERS = 1;
  public static final int TYPE_PING = 6;
  public static final int TYPE_PRIORITY = 2;
  public static final int TYPE_PUSH_PROMISE = 5;
  public static final int TYPE_RST_STREAM = 3;
  public static final int TYPE_SETTINGS = 4;
  public static final int TYPE_WINDOW_UPDATE = 8;
  
  static
  {
    CONNECTION_PREFACE = ByteString.Companion.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    FRAME_NAMES = new String[] { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
    FLAGS = new String[64];
    Object localObject1 = new String['?'];
    int i = 0;
    for (int j = 0; j < 256; j++)
    {
      localObject2 = Integer.toBinaryString(j);
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Integer.toBinaryString(it)");
      localObject1[j] = StringsKt.replace$default(Util.format("%8s", new Object[] { localObject2 }), ' ', '0', false, 4, null);
    }
    BINARY = (String[])localObject1;
    Object localObject2 = FLAGS;
    localObject2[0] = "";
    localObject2[1] = "END_STREAM";
    localObject1 = new int[1];
    localObject1[0] = 1;
    localObject2[8] = "PADDED";
    for (j = 0; j < 1; j++)
    {
      k = localObject1[j];
      localObject2 = FLAGS;
      localObject2[(k | 0x8)] = Intrinsics.stringPlus(localObject2[k], "|PADDED");
    }
    localObject2 = FLAGS;
    localObject2[4] = "END_HEADERS";
    localObject2[32] = "PRIORITY";
    localObject2[36] = "END_HEADERS|PRIORITY";
    for (j = 0; j < 3; j++)
    {
      int m = new int[] { 4, 32, 36 }[j];
      for (k = 0; k < 1; k++)
      {
        int n = localObject1[k];
        localObject2 = FLAGS;
        int i1 = n | m;
        Object localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(FLAGS[n]);
        ((StringBuilder)localObject3).append("|");
        ((StringBuilder)localObject3).append(FLAGS[m]);
        localObject2[i1] = ((StringBuilder)localObject3).toString();
        localObject3 = FLAGS;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(FLAGS[n]);
        ((StringBuilder)localObject2).append("|");
        ((StringBuilder)localObject2).append(FLAGS[m]);
        ((StringBuilder)localObject2).append("|PADDED");
        localObject3[(i1 | 0x8)] = ((StringBuilder)localObject2).toString();
      }
    }
    int k = FLAGS.length;
    for (j = i; j < k; j++)
    {
      localObject1 = FLAGS;
      if (localObject1[j] == null) {
        localObject1[j] = BINARY[j];
      }
    }
  }
  
  private Http2() {}
  
  public final String formatFlags(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      return "";
    }
    if ((paramInt1 != 2) && (paramInt1 != 3))
    {
      Object localObject1;
      if ((paramInt1 != 4) && (paramInt1 != 6))
      {
        if ((paramInt1 != 7) && (paramInt1 != 8))
        {
          localObject1 = FLAGS;
          if (paramInt2 < localObject1.length)
          {
            localObject2 = localObject1[paramInt2];
            localObject1 = localObject2;
            if (localObject2 == null)
            {
              Intrinsics.throwNpe();
              localObject1 = localObject2;
            }
          }
          else
          {
            localObject1 = BINARY[paramInt2];
          }
          Object localObject2 = localObject1;
          if ((paramInt1 == 5) && ((paramInt2 & 0x4) != 0))
          {
            localObject1 = StringsKt.replace$default(localObject2, "HEADERS", "PUSH_PROMISE", false, 4, null);
          }
          else
          {
            localObject1 = localObject2;
            if (paramInt1 == 0)
            {
              localObject1 = localObject2;
              if ((paramInt2 & 0x20) != 0) {
                localObject1 = StringsKt.replace$default(localObject2, "PRIORITY", "COMPRESSED", false, 4, null);
              }
            }
          }
          return localObject1;
        }
      }
      else
      {
        if (paramInt2 == 1) {
          localObject1 = "ACK";
        } else {
          localObject1 = BINARY[paramInt2];
        }
        return localObject1;
      }
    }
    return BINARY[paramInt2];
  }
  
  public final String frameLog(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = FRAME_NAMES;
    if (paramInt3 < localObject.length) {
      localObject = localObject[paramInt3];
    } else {
      localObject = Util.format("0x%02x", new Object[] { Integer.valueOf(paramInt3) });
    }
    String str1 = formatFlags(paramInt3, paramInt4);
    String str2;
    if (paramBoolean) {
      str2 = "<<";
    } else {
      str2 = ">>";
    }
    return Util.format("%s 0x%08x %5d %-13s %s", new Object[] { str2, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), localObject, str1 });
  }
}
