package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\032\021\020\000\032\0020\001*\0020\002H\007?\006\002\b\003\032\021\020\000\032\0020\004*\0020\005H\007?\006\002\b\003\032\n\020\006\032\0020\002*\0020\001?\006\007"}, d2={"asCoroutineDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "from", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/ExecutorService;", "asExecutor", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ExecutorsKt
{
  public static final Executor asExecutor(CoroutineDispatcher paramCoroutineDispatcher)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineDispatcher, "$this$asExecutor");
    if (!(paramCoroutineDispatcher instanceof ExecutorCoroutineDispatcher)) {
      localObject = null;
    } else {
      localObject = paramCoroutineDispatcher;
    }
    Object localObject = (ExecutorCoroutineDispatcher)localObject;
    if (localObject != null)
    {
      localObject = ((ExecutorCoroutineDispatcher)localObject).getExecutor();
      if (localObject != null) {
        return (CoroutineDispatcher)localObject;
      }
    }
    paramCoroutineDispatcher = (Executor)new DispatcherExecutor(paramCoroutineDispatcher);
    return paramCoroutineDispatcher;
  }
  
  public static final CoroutineDispatcher from(Executor paramExecutor)
  {
    Intrinsics.checkParameterIsNotNull(paramExecutor, "$this$asCoroutineDispatcher");
    if (!(paramExecutor instanceof DispatcherExecutor)) {
      localObject = null;
    } else {
      localObject = paramExecutor;
    }
    Object localObject = (DispatcherExecutor)localObject;
    if (localObject != null)
    {
      localObject = ((DispatcherExecutor)localObject).dispatcher;
      if (localObject != null) {
        return (Executor)localObject;
      }
    }
    paramExecutor = (CoroutineDispatcher)new ExecutorCoroutineDispatcherImpl(paramExecutor);
    return paramExecutor;
  }
  
  public static final ExecutorCoroutineDispatcher from(ExecutorService paramExecutorService)
  {
    Intrinsics.checkParameterIsNotNull(paramExecutorService, "$this$asCoroutineDispatcher");
    return (ExecutorCoroutineDispatcher)new ExecutorCoroutineDispatcherImpl((Executor)paramExecutorService);
  }
}
