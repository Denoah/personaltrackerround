package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.SystemPropsKt;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\000\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\b\020\013\032\0020\fH\000\0324\020\r\032\002H\016\"\004\b\000\020\0162\006\020\017\032\0020\b2\b\020\020\032\004\030\0010\0212\f\020\022\032\b\022\004\022\002H\0160\023H?\b?\006\002\020\024\032\024\020\025\032\0020\b*\0020\0262\006\020\017\032\0020\bH\007\"\016\020\000\032\0020\001X?T?\006\002\n\000\"\016\020\002\032\0020\001X?T?\006\002\n\000\"\024\020\003\032\0020\004X?\004?\006\b\n\000\032\004\b\005\020\006\"\032\020\007\032\004\030\0010\001*\0020\b8@X?\004?\006\006\032\004\b\t\020\n?\006\027"}, d2={"COROUTINES_SCHEDULER_PROPERTY_NAME", "", "DEBUG_THREAD_NAME_SEPARATOR", "useCoroutinesScheduler", "", "getUseCoroutinesScheduler", "()Z", "coroutineName", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "createDefaultDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "withCoroutineContext", "T", "context", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "newCoroutineContext", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CoroutineContextKt
{
  public static final String COROUTINES_SCHEDULER_PROPERTY_NAME = "kotlinx.coroutines.scheduler";
  private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";
  private static final boolean useCoroutinesScheduler;
  
  static
  {
    String str = SystemPropsKt.systemProp("kotlinx.coroutines.scheduler");
    if (str != null)
    {
      int i = str.hashCode();
      if (i != 0)
      {
        if (i != 3551)
        {
          if ((i != 109935) || (!str.equals("off"))) {
            break label77;
          }
          bool = false;
          break label72;
        }
        if (!str.equals("on")) {
          break label77;
        }
      }
      else
      {
        if (!str.equals("")) {
          break label77;
        }
      }
    }
    boolean bool = true;
    label72:
    useCoroutinesScheduler = bool;
    return;
    label77:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("System property 'kotlinx.coroutines.scheduler' has unrecognized value '");
    localStringBuilder.append(str);
    localStringBuilder.append('\'');
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public static final CoroutineDispatcher createDefaultDispatcher()
  {
    Object localObject;
    if (useCoroutinesScheduler) {
      localObject = DefaultScheduler.INSTANCE;
    } else {
      localObject = CommonPool.INSTANCE;
    }
    return (CoroutineDispatcher)localObject;
  }
  
  public static final String getCoroutineName(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "$this$coroutineName");
    if (!DebugKt.getDEBUG()) {
      return null;
    }
    CoroutineId localCoroutineId = (CoroutineId)paramCoroutineContext.get((CoroutineContext.Key)CoroutineId.Key);
    if (localCoroutineId != null)
    {
      paramCoroutineContext = (CoroutineName)paramCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
      if (paramCoroutineContext != null)
      {
        paramCoroutineContext = paramCoroutineContext.getName();
        if (paramCoroutineContext != null) {}
      }
      else
      {
        paramCoroutineContext = "coroutine";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramCoroutineContext);
      localStringBuilder.append('#');
      localStringBuilder.append(localCoroutineId.getId());
      return localStringBuilder.toString();
    }
    return null;
  }
  
  public static final boolean getUseCoroutinesScheduler()
  {
    return useCoroutinesScheduler;
  }
  
  public static final CoroutineContext newCoroutineContext(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$newCoroutineContext");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    CoroutineContext localCoroutineContext = paramCoroutineScope.getCoroutineContext().plus(paramCoroutineContext);
    if (DebugKt.getDEBUG()) {
      paramCoroutineScope = localCoroutineContext.plus((CoroutineContext)new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet()));
    } else {
      paramCoroutineScope = localCoroutineContext;
    }
    paramCoroutineContext = paramCoroutineScope;
    if (localCoroutineContext != Dispatchers.getDefault())
    {
      paramCoroutineContext = paramCoroutineScope;
      if (localCoroutineContext.get((CoroutineContext.Key)ContinuationInterceptor.Key) == null) {
        paramCoroutineContext = paramCoroutineScope.plus((CoroutineContext)Dispatchers.getDefault());
      }
    }
    return paramCoroutineContext;
  }
  
  public static final <T> T withCoroutineContext(CoroutineContext paramCoroutineContext, Object paramObject, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    paramObject = ThreadContextKt.updateThreadContext(paramCoroutineContext, paramObject);
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      ThreadContextKt.restoreThreadContext(paramCoroutineContext, paramObject);
      InlineMarker.finallyEnd(1);
    }
  }
}
