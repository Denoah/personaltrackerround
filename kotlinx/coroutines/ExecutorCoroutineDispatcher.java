package kotlinx.coroutines;

import java.io.Closeable;
import java.util.concurrent.Executor;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\b&\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\b\020\b\032\0020\tH&R\022\020\004\032\0020\005X¦\004?\006\006\032\004\b\006\020\007?\006\n"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/io/Closeable;", "()V", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class ExecutorCoroutineDispatcher
  extends CoroutineDispatcher
  implements Closeable
{
  public ExecutorCoroutineDispatcher() {}
  
  public abstract void close();
  
  public abstract Executor getExecutor();
}
