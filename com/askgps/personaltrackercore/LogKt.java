package com.askgps.personaltrackercore;

import android.util.Log;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\020\016\n\000\n\002\020\002\n\002\020\000\n\002\b\004\n\002\020\003\n\000\n\002\020\034\n\002\b\002\032,\020\002\032\0020\003*\004\030\0010\0042\b\b\002\020\005\032\0020\0012\n\b\002\020\006\032\004\030\0010\0012\b\b\002\020\007\032\0020\001\032,\020\b\032\0020\003*\004\030\0010\0042\b\b\002\020\005\032\0020\0012\n\b\002\020\006\032\004\030\0010\0012\b\b\002\020\007\032\0020\001\032*\020\b\032\0020\003*\0020\t2\b\b\002\020\005\032\0020\0012\n\b\002\020\006\032\004\030\0010\0012\b\b\002\020\007\032\0020\001\0326\020\b\032\0020\003\"\004\b\000\020\n*\b\022\004\022\002H\n0\0132\b\b\002\020\005\032\0020\0012\n\b\002\020\006\032\004\030\0010\0012\b\b\002\020\007\032\0020\001\0326\020\f\032\0020\003\"\004\b\000\020\n*\b\022\004\022\002H\n0\0132\b\b\002\020\005\032\0020\0012\n\b\002\020\006\032\004\030\0010\0012\b\b\002\020\007\032\0020\001\"\016\020\000\032\0020\001X?T?\006\002\n\000?\006\r"}, d2={"TAG", "", "toFile", "", "", "prefix", "methodName", "tag", "toLog", "", "T", "", "toLogLn", "personaltrackercore_release"}, k=2, mv={1, 1, 16})
public final class LogKt
{
  private static final String TAG = "pTracker";
  
  public static final void toFile(Object paramObject, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    Intrinsics.checkParameterIsNotNull(paramString3, "tag");
    Object localObject;
    if (paramString2 != null) {
      localObject = paramString2;
    }
    try
    {
      localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      localObject = localObject.getStackTrace()[4];
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread().stackTrace[4]");
      localObject = ((StackTraceElement)localObject).getMethodName();
      if ((paramObject instanceof Object[]))
      {
        paramString2 = ArraysKt.joinToString$default((Object[])paramObject, (CharSequence)"\n", null, null, 0, null, null, 62, null);
      }
      else if ((paramObject instanceof Iterable))
      {
        paramString2 = CollectionsKt.joinToString$default((Iterable)paramObject, (CharSequence)"\n", null, null, 0, null, null, 62, null);
      }
      else
      {
        paramString2 = paramObject;
        if ((paramObject instanceof Throwable))
        {
          paramObject = ((Throwable)paramObject).getStackTrace();
          Intrinsics.checkExpressionValueIsNotNull(paramObject, "this.stackTrace");
          paramString2 = ArraysKt.joinToString$default(paramObject, (CharSequence)"\n", null, null, 0, null, null, 62, null);
        }
      }
      toLog(paramString2, paramString1, (String)localObject, paramString3);
      paramString3 = Logger.INSTANCE;
      paramObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(paramObject, "Thread.currentThread()");
      paramString3.writeToFile(paramString2, paramString1, (String)localObject, Long.valueOf(paramObject.getId()));
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str = paramString2;
      }
    }
  }
  
  public static final <T> void toLog(Iterable<? extends T> paramIterable, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toLog");
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    Intrinsics.checkParameterIsNotNull(paramString3, "tag");
    if (paramString2 == null) {}
    try
    {
      Object localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      localObject = localObject.getStackTrace()[4];
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread().stackTrace[4]");
      localObject = ((StackTraceElement)localObject).getMethodName();
      paramString2 = (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    toLog(CollectionsKt.joinToString$default(paramIterable, null, null, null, 0, null, null, 63, null), paramString1, paramString2, paramString3);
  }
  
  public static final void toLog(Object paramObject, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    Intrinsics.checkParameterIsNotNull(paramString3, "tag");
    if (paramString2 == null) {}
    try
    {
      localObject1 = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "Thread.currentThread()");
      localObject1 = localObject1.getStackTrace()[4];
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "Thread.currentThread().stackTrace[4]");
      localObject1 = ((StackTraceElement)localObject1).getMethodName();
      paramString2 = (String)localObject1;
    }
    catch (Exception localException)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append('[');
    localObject2 = StringCompanionObject.INSTANCE;
    localObject2 = Thread.currentThread();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "Thread.currentThread()");
    localObject2 = String.format("%5d", Arrays.copyOf(new Object[] { Long.valueOf(((Thread)localObject2).getId()) }, 1));
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "java.lang.String.format(format, *args)");
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).append("] [");
    localObject2 = StringCompanionObject.INSTANCE;
    paramString2 = String.format("%-15s", Arrays.copyOf(new Object[] { paramString2 }, 1));
    Intrinsics.checkExpressionValueIsNotNull(paramString2, "java.lang.String.format(format, *args)");
    ((StringBuilder)localObject1).append(paramString2);
    ((StringBuilder)localObject1).append("] ");
    ((StringBuilder)localObject1).append(paramString1);
    ((StringBuilder)localObject1).append(": ");
    ((StringBuilder)localObject1).append(paramObject);
    Log.d(paramString3, ((StringBuilder)localObject1).toString());
  }
  
  public static final void toLog(Throwable paramThrowable, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "$this$toLog");
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    Intrinsics.checkParameterIsNotNull(paramString3, "tag");
    if (paramString2 == null) {}
    try
    {
      Object localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      localObject = localObject.getStackTrace()[4];
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread().stackTrace[4]");
      localObject = ((StackTraceElement)localObject).getMethodName();
      paramString2 = (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    toLog(paramThrowable.getMessage(), paramString1, paramString2, paramString3);
    paramThrowable.printStackTrace();
  }
  
  public static final <T> void toLogLn(Iterable<? extends T> paramIterable, String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toLogLn");
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    Intrinsics.checkParameterIsNotNull(paramString3, "tag");
    if (paramString2 == null) {}
    try
    {
      localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      localObject = localObject.getStackTrace()[4];
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread().stackTrace[4]");
      localObject = ((StackTraceElement)localObject).getMethodName();
      paramString2 = (String)localObject;
    }
    catch (Exception localException)
    {
      Object localObject;
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append('\n');
    ((StringBuilder)localObject).append(CollectionsKt.joinToString$default(paramIterable, (CharSequence)"\n", null, null, 0, null, null, 62, null));
    toLog(((StringBuilder)localObject).toString(), paramString1, paramString2, paramString3);
  }
}
