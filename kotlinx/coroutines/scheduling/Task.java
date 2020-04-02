package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\b \030\0002\0060\001j\002`\002B\007\b\026?\006\002\020\003B\025\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007?\006\002\020\bR\022\020\t\032\0020\n8?\002?\006\006\032\004\b\013\020\fR\022\020\004\032\0020\0058\006@\006X?\016?\006\002\n\000R\022\020\006\032\0020\0078\006@\006X?\016?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/scheduling/Task;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "submissionTime", "", "taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "(JLkotlinx/coroutines/scheduling/TaskContext;)V", "mode", "Lkotlinx/coroutines/scheduling/TaskMode;", "getMode", "()Lkotlinx/coroutines/scheduling/TaskMode;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class Task
  implements Runnable
{
  public long submissionTime;
  public TaskContext taskContext;
  
  public Task()
  {
    this(0L, (TaskContext)NonBlockingContext.INSTANCE);
  }
  
  public Task(long paramLong, TaskContext paramTaskContext)
  {
    this.submissionTime = paramLong;
    this.taskContext = paramTaskContext;
  }
  
  public final TaskMode getMode()
  {
    return this.taskContext.getTaskMode();
  }
}
