package kotlinx.coroutines;

import java.util.concurrent.Future;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\b\020\005\032\0020\006H\026J\b\020\007\032\0020\bH\026R\022\020\002\032\006\022\002\b\0030\003X?\004?\006\002\n\000?\006\t"}, d2={"Lkotlinx/coroutines/DisposableFutureHandle;", "Lkotlinx/coroutines/DisposableHandle;", "future", "Ljava/util/concurrent/Future;", "(Ljava/util/concurrent/Future;)V", "dispose", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class DisposableFutureHandle
  implements DisposableHandle
{
  private final Future<?> future;
  
  public DisposableFutureHandle(Future<?> paramFuture)
  {
    this.future = paramFuture;
  }
  
  public void dispose()
  {
    this.future.cancel(false);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DisposableFutureHandle[");
    localStringBuilder.append(this.future);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
