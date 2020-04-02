package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\000\030\0002\b\022\004\022\0020\0020\001B\031\022\006\020\003\032\0020\002\022\n\020\004\032\006\022\002\b\0030\005?\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH?\002J\b\020\013\032\0020\fH\026R\024\020\004\032\006\022\002\b\0030\0058\006X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/ChildContinuation;", "Lkotlinx/coroutines/JobCancellingNode;", "Lkotlinx/coroutines/Job;", "parent", "child", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/Job;Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChildContinuation
  extends JobCancellingNode<Job>
{
  public final CancellableContinuationImpl<?> child;
  
  public ChildContinuation(Job paramJob, CancellableContinuationImpl<?> paramCancellableContinuationImpl)
  {
    super(paramJob);
    this.child = paramCancellableContinuationImpl;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    paramThrowable = this.child;
    paramThrowable.parentCancelled$kotlinx_coroutines_core(paramThrowable.getContinuationCancellationCause(this.job));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ChildContinuation[");
    localStringBuilder.append(this.child);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
