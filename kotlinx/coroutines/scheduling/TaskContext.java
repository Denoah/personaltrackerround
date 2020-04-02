package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\b`\030\0002\0020\001J\b\020\006\032\0020\007H&R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\004\020\005?\006\b"}, d2={"Lkotlinx/coroutines/scheduling/TaskContext;", "", "taskMode", "Lkotlinx/coroutines/scheduling/TaskMode;", "getTaskMode", "()Lkotlinx/coroutines/scheduling/TaskMode;", "afterTask", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface TaskContext
{
  public abstract void afterTask();
  
  public abstract TaskMode getTaskMode();
}
