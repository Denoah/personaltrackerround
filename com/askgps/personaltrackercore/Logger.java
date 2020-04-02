package com.askgps.personaltrackercore;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import androidx.core.os.BundleKt;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KProperty;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\004\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\005\n\002\020\t\n\002\b\004\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\026\032\0020\0272\006\020\030\032\0020\031J;\020\032\032\0020\0272\b\020\033\032\004\030\0010\0012\n\b\002\020\034\032\004\030\0010\0042\n\b\002\020\035\032\004\030\0010\0042\n\b\002\020\036\032\004\030\0010\037H\002?\006\002\020 J9\020!\032\0020\0272\b\020\"\032\004\030\0010\0012\n\b\002\020\034\032\004\030\0010\0042\n\b\002\020\035\032\004\030\0010\0042\n\b\002\020\036\032\004\030\0010\037?\006\002\020 R\016\020\003\032\0020\004X?\004?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\004X?T?\006\002\n\000R\016\020\007\032\0020\004X?T?\006\002\n\000R\016\020\b\032\0020\tX?T?\006\002\n\000R\026\020\n\032\n \013*\004\030\0010\0040\004X?\004?\006\002\n\000R\016\020\f\032\0020\rX?.?\006\002\n\000R\033\020\016\032\0020\0178BX??\002?\006\f\n\004\b\022\020\023\032\004\b\020\020\021R\016\020\024\032\0020\025X?\004?\006\002\n\000?\006#"}, d2={"Lcom/askgps/personaltrackercore/Logger;", "", "()V", "FILE_NAME", "", "METHOD_NAME", "PREFIX", "THREAD_ID", "TO_FILE", "", "date", "kotlin.jvm.PlatformType", "file", "Ljava/io/File;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "handlerThread", "Landroid/os/HandlerThread;", "initFile", "", "context", "Landroid/content/Context;", "toFile", "msg", "prefix", "methodName", "threadId", "", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V", "writeToFile", "obj", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class Logger
{
  private static final String FILE_NAME;
  public static final Logger INSTANCE = new Logger();
  private static final String METHOD_NAME = "methodName";
  private static final String PREFIX = "prefix";
  private static final String THREAD_ID = "threadId";
  private static final int TO_FILE = 0;
  private static final String date = new SimpleDateFormat("ddMMyy").format(new Date(System.currentTimeMillis()));
  private static File file;
  private static final Lazy handler$delegate = LazyKt.lazy((Function0)handler.2.INSTANCE);
  private static final HandlerThread handlerThread;
  
  static
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("tracker_");
    ((StringBuilder)localObject).append(date);
    ((StringBuilder)localObject).append(".txt");
    FILE_NAME = ((StringBuilder)localObject).toString();
    localObject = new HandlerThread("logger");
    ((HandlerThread)localObject).setDaemon(true);
    ((HandlerThread)localObject).start();
    handlerThread = (HandlerThread)localObject;
  }
  
  private Logger() {}
  
  private final Handler getHandler()
  {
    Lazy localLazy = handler$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Handler)localLazy.getValue();
  }
  
  private final void toFile(Object paramObject, String paramString1, String paramString2, Long paramLong)
  {
    try
    {
      File localFile = file;
      if (localFile == null) {
        Intrinsics.throwUninitializedPropertyAccessException("file");
      }
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append('[');
      Object localObject = new java/text/SimpleDateFormat;
      ((SimpleDateFormat)localObject).<init>("ddMMyy HH:mm:ss");
      Calendar localCalendar = Calendar.getInstance();
      Intrinsics.checkExpressionValueIsNotNull(localCalendar, "Calendar.getInstance()");
      localStringBuilder.append(((SimpleDateFormat)localObject).format(localCalendar.getTime()));
      localStringBuilder.append("] [");
      localObject = StringCompanionObject.INSTANCE;
      if (paramLong == null)
      {
        paramLong = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(paramLong, "Thread.currentThread()");
        paramLong = Long.valueOf(paramLong.getId());
      }
      paramLong = String.format("%5d", Arrays.copyOf(new Object[] { paramLong }, 1));
      Intrinsics.checkExpressionValueIsNotNull(paramLong, "java.lang.String.format(format, *args)");
      localStringBuilder.append(paramLong);
      localStringBuilder.append(']');
      localStringBuilder.append('[');
      paramLong = StringCompanionObject.INSTANCE;
      paramString2 = String.format("%-15s", Arrays.copyOf(new Object[] { paramString2 }, 1));
      Intrinsics.checkExpressionValueIsNotNull(paramString2, "java.lang.String.format(format, *args)");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("] ");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(": ");
      localStringBuilder.append(paramObject);
      localStringBuilder.append(" \n");
      FilesKt.appendText$default(localFile, localStringBuilder.toString(), null, 2, null);
      return;
    }
    catch (Exception paramObject)
    {
      for (;;) {}
    }
  }
  
  public final void initFile(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext = new File(paramContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Log");
    paramContext.mkdir();
    paramContext = new File(paramContext, FILE_NAME);
    file = paramContext;
    if (paramContext == null) {
      Intrinsics.throwUninitializedPropertyAccessException("file");
    }
    if (!(paramContext.exists() ^ true)) {
      paramContext = null;
    }
    if (paramContext != null) {
      paramContext.createNewFile();
    }
  }
  
  public final void writeToFile(Object paramObject, String paramString1, String paramString2, Long paramLong)
  {
    Handler localHandler = getHandler();
    Message localMessage = new Message();
    localMessage.what = 0;
    localMessage.obj = paramObject;
    if (paramString1 == null) {
      paramString1 = "";
    }
    paramObject = TuplesKt.to("prefix", paramString1);
    if (paramString2 == null) {
      paramString2 = "";
    }
    localMessage.setData(BundleKt.bundleOf(new Pair[] { paramObject, TuplesKt.to("methodName", paramString2), TuplesKt.to("threadId", paramLong) }));
    localHandler.sendMessage(localMessage);
  }
}
