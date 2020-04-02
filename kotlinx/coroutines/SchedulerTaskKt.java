package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\003\032\021\020\t\032\0020\n*\0060\001j\002`\002H?\b\"&\020\000\032\0060\001j\002`\002*\0060\003j\002`\0048@X?\004?\006\f\022\004\b\005\020\006\032\004\b\007\020\b*\f\b\000\020\013\"\0020\0032\0020\003*\f\b\000\020\f\"\0020\0012\0020\001?\006\r"}, d2={"taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "Lkotlinx/coroutines/SchedulerTaskContext;", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "taskContext$annotations", "(Lkotlinx/coroutines/scheduling/Task;)V", "getTaskContext", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/TaskContext;", "afterTask", "", "SchedulerTask", "SchedulerTaskContext", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SchedulerTaskKt
{
  public static final void afterTask(TaskContext paramTaskContext)
  {
    Intrinsics.checkParameterIsNotNull(paramTaskContext, "$this$afterTask");
    paramTaskContext.afterTask();
  }
  
  public static final TaskContext getTaskContext(Task paramTask)
  {
    Intrinsics.checkParameterIsNotNull(paramTask, "$this$taskContext");
    return paramTask.taskContext;
  }
}
