package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\000\030\0002\b\022\004\022\0020\0020\001B\025\022\006\020\003\032\0020\002\022\006\020\004\032\0020\005?\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH?\002J\b\020\013\032\0020\fH\026R\016\020\004\032\0020\005X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/DisposeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/Job;", "job", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/Job;Lkotlinx/coroutines/DisposableHandle;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class DisposeOnCompletion
  extends JobNode<Job>
{
  private final DisposableHandle handle;
  
  public DisposeOnCompletion(Job paramJob, DisposableHandle paramDisposableHandle)
  {
    super(paramJob);
    this.handle = paramDisposableHandle;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handle.dispose();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DisposeOnCompletion[");
    localStringBuilder.append(this.handle);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
