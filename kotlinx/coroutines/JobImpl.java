package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\007\n\002\020\003\n\000\b\020\030\0002\0020\0012\0020\002B\017\022\b\020\003\032\004\030\0010\004?\006\002\020\005J\b\020\f\032\0020\007H\026J\020\020\r\032\0020\0072\006\020\016\032\0020\017H\026J\b\020\006\032\0020\007H\003R\024\020\006\032\0020\007X?\004?\006\b\n\000\032\004\b\b\020\tR\024\020\n\032\0020\0078PX?\004?\006\006\032\004\b\013\020\t?\006\020"}, d2={"Lkotlinx/coroutines/JobImpl;", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/CompletableJob;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "handlesException", "", "getHandlesException$kotlinx_coroutines_core", "()Z", "onCancelComplete", "getOnCancelComplete$kotlinx_coroutines_core", "complete", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class JobImpl
  extends JobSupport
  implements CompletableJob
{
  private final boolean handlesException;
  
  public JobImpl(Job paramJob)
  {
    super(true);
    initParentJobInternal$kotlinx_coroutines_core(paramJob);
    this.handlesException = handlesException();
  }
  
  private final boolean handlesException()
  {
    ChildHandle localChildHandle = getParentHandle$kotlinx_coroutines_core();
    Object localObject = localChildHandle;
    if (!(localChildHandle instanceof ChildHandleNode)) {
      localObject = null;
    }
    localObject = (ChildHandleNode)localObject;
    if (localObject != null)
    {
      localObject = (JobSupport)((ChildHandleNode)localObject).job;
      if (localObject != null) {
        do
        {
          if (((JobSupport)localObject).getHandlesException$kotlinx_coroutines_core()) {
            return true;
          }
          localChildHandle = ((JobSupport)localObject).getParentHandle$kotlinx_coroutines_core();
          localObject = localChildHandle;
          if (!(localChildHandle instanceof ChildHandleNode)) {
            localObject = null;
          }
          localObject = (ChildHandleNode)localObject;
          if (localObject == null) {
            break;
          }
          localObject = (JobSupport)((ChildHandleNode)localObject).job;
        } while (localObject != null);
      }
    }
    return false;
  }
  
  public boolean complete()
  {
    return makeCompleting$kotlinx_coroutines_core(Unit.INSTANCE);
  }
  
  public boolean completeExceptionally(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    return makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(paramThrowable, false, 2, null));
  }
  
  public boolean getHandlesException$kotlinx_coroutines_core()
  {
    return this.handlesException;
  }
  
  public boolean getOnCancelComplete$kotlinx_coroutines_core()
  {
    return true;
  }
}
