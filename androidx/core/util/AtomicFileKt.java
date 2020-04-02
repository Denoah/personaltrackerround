package androidx.core.util;

import android.util.AtomicFile;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\020\022\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\032\r\020\000\032\0020\001*\0020\002H?\b\032\026\020\003\032\0020\004*\0020\0022\b\b\002\020\005\032\0020\006H\007\0320\020\007\032\0020\b*\0020\0022!\020\t\032\035\022\023\022\0210\013?\006\f\b\f\022\b\b\r\022\004\b\b(\016\022\004\022\0020\b0\nH?\b\032\024\020\017\032\0020\b*\0020\0022\006\020\020\032\0020\001H\007\032\036\020\021\032\0020\b*\0020\0022\006\020\022\032\0020\0042\b\b\002\020\005\032\0020\006H\007?\006\023"}, d2={"readBytes", "", "Landroid/util/AtomicFile;", "readText", "", "charset", "Ljava/nio/charset/Charset;", "tryWrite", "", "block", "Lkotlin/Function1;", "Ljava/io/FileOutputStream;", "Lkotlin/ParameterName;", "name", "out", "writeBytes", "array", "writeText", "text", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class AtomicFileKt
{
  public static final byte[] readBytes(AtomicFile paramAtomicFile)
  {
    Intrinsics.checkParameterIsNotNull(paramAtomicFile, "$this$readBytes");
    paramAtomicFile = paramAtomicFile.readFully();
    Intrinsics.checkExpressionValueIsNotNull(paramAtomicFile, "readFully()");
    return paramAtomicFile;
  }
  
  public static final String readText(AtomicFile paramAtomicFile, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramAtomicFile, "$this$readText");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    paramAtomicFile = paramAtomicFile.readFully();
    Intrinsics.checkExpressionValueIsNotNull(paramAtomicFile, "readFully()");
    return new String(paramAtomicFile, paramCharset);
  }
  
  public static final void tryWrite(AtomicFile paramAtomicFile, Function1<? super FileOutputStream, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAtomicFile, "$this$tryWrite");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    FileOutputStream localFileOutputStream = paramAtomicFile.startWrite();
    try
    {
      Intrinsics.checkExpressionValueIsNotNull(localFileOutputStream, "stream");
      paramFunction1.invoke(localFileOutputStream);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramAtomicFile.failWrite(localFileOutputStream);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void writeBytes(AtomicFile paramAtomicFile, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramAtomicFile, "$this$writeBytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "array");
    FileOutputStream localFileOutputStream = paramAtomicFile.startWrite();
    try
    {
      Intrinsics.checkExpressionValueIsNotNull(localFileOutputStream, "stream");
      localFileOutputStream.write(paramArrayOfByte);
      return;
    }
    finally
    {
      paramAtomicFile.failWrite(localFileOutputStream);
    }
  }
  
  public static final void writeText(AtomicFile paramAtomicFile, String paramString, Charset paramCharset)
  {
    Intrinsics.checkParameterIsNotNull(paramAtomicFile, "$this$writeText");
    Intrinsics.checkParameterIsNotNull(paramString, "text");
    Intrinsics.checkParameterIsNotNull(paramCharset, "charset");
    paramString = paramString.getBytes(paramCharset);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).getBytes(charset)");
    writeBytes(paramAtomicFile, paramString);
  }
}
