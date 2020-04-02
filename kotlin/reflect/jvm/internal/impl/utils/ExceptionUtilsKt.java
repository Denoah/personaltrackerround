package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;

public final class ExceptionUtilsKt
{
  public static final boolean isProcessCanceledException(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "$this$isProcessCanceledException");
    paramThrowable = paramThrowable.getClass();
    do
    {
      if (Intrinsics.areEqual(paramThrowable.getCanonicalName(), "com.intellij.openapi.progress.ProcessCanceledException")) {
        return true;
      }
      paramThrowable = paramThrowable.getSuperclass();
    } while (paramThrowable != null);
    return false;
  }
  
  public static final RuntimeException rethrow(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "e");
    throw paramThrowable;
  }
}
