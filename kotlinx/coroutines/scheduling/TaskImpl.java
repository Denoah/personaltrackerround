package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlinx.coroutines.DebugStringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\016\n\000\b\000\030\0002\0020\001B!\022\n\020\002\032\0060\003j\002`\004\022\006\020\005\032\0020\006\022\006\020\007\032\0020\b?\006\002\020\tJ\b\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\026R\024\020\002\032\0060\003j\002`\0048\006X?\004?\006\002\n\000?\006\016"}, d2={"Lkotlinx/coroutines/scheduling/TaskImpl;", "Lkotlinx/coroutines/scheduling/Task;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "submissionTime", "", "taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "(Ljava/lang/Runnable;JLkotlinx/coroutines/scheduling/TaskContext;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class TaskImpl
  extends Task
{
  public final Runnable block;
  
  public TaskImpl(Runnable paramRunnable, long paramLong, TaskContext paramTaskContext)
  {
    super(paramLong, paramTaskContext);
    this.block = paramRunnable;
  }
  
  public void run()
  {
    try
    {
      this.block.run();
      return;
    }
    finally
    {
      this.taskContext.afterTask();
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Task[");
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this.block));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this.block));
    localStringBuilder.append(", ");
    localStringBuilder.append(this.submissionTime);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.taskContext);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
