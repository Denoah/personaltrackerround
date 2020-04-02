package kotlinx.coroutines;

import java.util.concurrent.Executor;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004R\024\020\002\032\0020\003X?\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcherImpl;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcherBase;", "executor", "Ljava/util/concurrent/Executor;", "(Ljava/util/concurrent/Executor;)V", "getExecutor", "()Ljava/util/concurrent/Executor;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ExecutorCoroutineDispatcherImpl
  extends ExecutorCoroutineDispatcherBase
{
  private final Executor executor;
  
  public ExecutorCoroutineDispatcherImpl(Executor paramExecutor)
  {
    this.executor = paramExecutor;
    initFutureCancellation$kotlinx_coroutines_core();
  }
  
  public Executor getExecutor()
  {
    return this.executor;
  }
}
