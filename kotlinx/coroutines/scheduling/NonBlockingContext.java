package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\007\032\0020\bH\026R\024\020\003\032\0020\004X?\004?\006\b\n\000\032\004\b\005\020\006?\006\t"}, d2={"Lkotlinx/coroutines/scheduling/NonBlockingContext;", "Lkotlinx/coroutines/scheduling/TaskContext;", "()V", "taskMode", "Lkotlinx/coroutines/scheduling/TaskMode;", "getTaskMode", "()Lkotlinx/coroutines/scheduling/TaskMode;", "afterTask", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class NonBlockingContext
  implements TaskContext
{
  public static final NonBlockingContext INSTANCE = new NonBlockingContext();
  private static final TaskMode taskMode = TaskMode.NON_BLOCKING;
  
  private NonBlockingContext() {}
  
  public void afterTask() {}
  
  public TaskMode getTaskMode()
  {
    return taskMode;
  }
}
