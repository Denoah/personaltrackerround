package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\003\n\000\n\002\020\002\n\002\b\002\032\036\020\000\032\0060\001j\002`\0022\b\020\003\032\004\030\0010\0042\b\020\005\032\004\030\0010\006\032\025\020\007\032\0020\b*\0020\0062\006\020\t\032\0020\006H?\b*\n\020\000\"\0020\0012\0020\001?\006\n"}, d2={"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "cause", "", "addSuppressedThrowable", "", "other", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ExceptionsKt
{
  public static final CancellationException CancellationException(String paramString, Throwable paramThrowable)
  {
    paramString = new CancellationException(paramString);
    paramString.initCause(paramThrowable);
    return paramString;
  }
  
  public static final void addSuppressedThrowable(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable1, "$this$addSuppressedThrowable");
    Intrinsics.checkParameterIsNotNull(paramThrowable2, "other");
    kotlin.ExceptionsKt.addSuppressed(paramThrowable1, paramThrowable2);
  }
}
