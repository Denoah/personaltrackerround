package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\b \030\000*\n\b\000\020\001 \001*\0020\0022\b\022\004\022\002H\0010\003B\r\022\006\020\004\032\0028\000?\006\002\020\005?\006\006"}, d2={"Lkotlinx/coroutines/JobCancellingNode;", "J", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/JobNode;", "job", "(Lkotlinx/coroutines/Job;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class JobCancellingNode<J extends Job>
  extends JobNode<J>
{
  public JobCancellingNode(J paramJ)
  {
    super(paramJ);
  }
}
