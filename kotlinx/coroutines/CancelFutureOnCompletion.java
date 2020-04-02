package kotlinx.coroutines;

import java.util.concurrent.Future;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\b\022\004\022\0020\0020\001B\031\022\006\020\003\032\0020\002\022\n\020\004\032\006\022\002\b\0030\005?\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH?\002J\b\020\013\032\0020\fH\026R\022\020\004\032\006\022\002\b\0030\005X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/CancelFutureOnCompletion;", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/Job;", "job", "future", "Ljava/util/concurrent/Future;", "(Lkotlinx/coroutines/Job;Ljava/util/concurrent/Future;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CancelFutureOnCompletion
  extends JobNode<Job>
{
  private final Future<?> future;
  
  public CancelFutureOnCompletion(Job paramJob, Future<?> paramFuture)
  {
    super(paramJob);
    this.future = paramFuture;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.future.cancel(false);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CancelFutureOnCompletion[");
    localStringBuilder.append(this.future);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
