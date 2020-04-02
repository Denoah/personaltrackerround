package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\b \030\000*\n\b\000\020\001 \001*\0020\0022\0020\0032\0020\0042\0020\005B\r\022\006\020\006\032\0028\000?\006\002\020\007J\b\020\020\032\0020\021H\026R\024\020\b\032\0020\t8VX?\004?\006\006\032\004\b\b\020\nR\022\020\006\032\0028\0008\006X?\004?\006\004\n\002\020\013R\026\020\f\032\004\030\0010\r8VX?\004?\006\006\032\004\b\016\020\017?\006\022"}, d2={"Lkotlinx/coroutines/JobNode;", "J", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CompletionHandlerBase;", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/Incomplete;", "job", "(Lkotlinx/coroutines/Job;)V", "isActive", "", "()Z", "Lkotlinx/coroutines/Job;", "list", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "dispose", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class JobNode<J extends Job>
  extends CompletionHandlerBase
  implements DisposableHandle, Incomplete
{
  public final J job;
  
  public JobNode(J paramJ)
  {
    this.job = paramJ;
  }
  
  public void dispose()
  {
    Job localJob = this.job;
    if (localJob != null)
    {
      ((JobSupport)localJob).removeNode$kotlinx_coroutines_core(this);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.JobSupport");
  }
  
  public NodeList getList()
  {
    return null;
  }
  
  public boolean isActive()
  {
    return true;
  }
}
