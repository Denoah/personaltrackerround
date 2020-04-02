package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\n\002\020\002\n\000\n\002\020\016\n\000\b\000\030\0002\b\022\004\022\0020\0020\0012\0020\003B\025\022\006\020\004\032\0020\002\022\006\020\005\032\0020\006?\006\002\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\023\020\f\032\0020\r2\b\020\n\032\004\030\0010\013H?\002J\b\020\016\032\0020\017H\026R\020\020\005\032\0020\0068\006X?\004?\006\002\n\000?\006\020"}, d2={"Lkotlinx/coroutines/ChildHandleNode;", "Lkotlinx/coroutines/JobCancellingNode;", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/ChildHandle;", "parent", "childJob", "Lkotlinx/coroutines/ChildJob;", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/ChildJob;)V", "childCancelled", "", "cause", "", "invoke", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChildHandleNode
  extends JobCancellingNode<JobSupport>
  implements ChildHandle
{
  public final ChildJob childJob;
  
  public ChildHandleNode(JobSupport paramJobSupport, ChildJob paramChildJob)
  {
    super((Job)paramJobSupport);
    this.childJob = paramChildJob;
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    return ((JobSupport)this.job).childCancelled(paramThrowable);
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.childJob.parentCancelled((ParentJob)this.job);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ChildHandle[");
    localStringBuilder.append(this.childJob);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
