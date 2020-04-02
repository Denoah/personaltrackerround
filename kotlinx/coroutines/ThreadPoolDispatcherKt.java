package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005H\007\032\020\020\006\032\0020\0012\006\020\004\032\0020\005H\007?\006\007"}, d2={"newFixedThreadPoolContext", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "nThreads", "", "name", "", "newSingleThreadContext", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ThreadPoolDispatcherKt
{
  public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(int paramInt, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0) {
      return (ExecutorCoroutineDispatcher)new ThreadPoolDispatcher(paramInt, paramString);
    }
    paramString = new StringBuilder();
    paramString.append("Expected at least one thread, but ");
    paramString.append(paramInt);
    paramString.append(" specified");
    throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
  }
  
  public static final ExecutorCoroutineDispatcher newSingleThreadContext(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    return newFixedThreadPoolContext(1, paramString);
  }
}
