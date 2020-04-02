package okhttp3.logging;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\000\n\002\020\013\n\002\030\002\n\000\032\f\020\000\032\0020\001*\0020\002H\000?\006\003"}, d2={"isProbablyUtf8", "", "Lokio/Buffer;", "okhttp-logging-interceptor"}, k=2, mv={1, 1, 16})
public final class Utf8Kt
{
  public static final boolean isProbablyUtf8(Buffer paramBuffer)
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "$this$isProbablyUtf8");
    try
    {
      Buffer localBuffer = new okio/Buffer;
      localBuffer.<init>();
      paramBuffer.copyTo(localBuffer, 0L, RangesKt.coerceAtMost(paramBuffer.size(), 64L));
      for (int i = 0; (i < 16) && (!localBuffer.exhausted()); i++)
      {
        int j = localBuffer.readUtf8CodePoint();
        if (Character.isISOControl(j))
        {
          boolean bool = Character.isWhitespace(j);
          if (!bool) {
            return false;
          }
        }
      }
      return true;
    }
    catch (EOFException paramBuffer) {}
    return false;
  }
}
