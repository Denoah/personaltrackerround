package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeTaskQueue;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\000\030\0002\b\022\004\022\0020\0020\001B\005?\006\002\020\003?\006\004"}, d2={"Lkotlinx/coroutines/scheduling/GlobalQueue;", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "Lkotlinx/coroutines/scheduling/Task;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class GlobalQueue
  extends LockFreeTaskQueue<Task>
{
  public GlobalQueue()
  {
    super(false);
  }
}
