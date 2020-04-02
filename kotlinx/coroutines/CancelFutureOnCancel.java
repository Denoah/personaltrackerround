package kotlinx.coroutines;

import java.util.concurrent.Future;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH?\002J\b\020\t\032\0020\nH\026R\022\020\002\032\006\022\002\b\0030\003X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/CancelFutureOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "future", "Ljava/util/concurrent/Future;", "(Ljava/util/concurrent/Future;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CancelFutureOnCancel
  extends CancelHandler
{
  private final Future<?> future;
  
  public CancelFutureOnCancel(Future<?> paramFuture)
  {
    this.future = paramFuture;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.future.cancel(false);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CancelFutureOnCancel[");
    localStringBuilder.append(this.future);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
