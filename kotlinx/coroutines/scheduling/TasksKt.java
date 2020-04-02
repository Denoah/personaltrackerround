package kotlinx.coroutines.scheduling;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020\b\n\002\b\002\n\002\020\016\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\002\030\002\n\002\b\002\"\020\020\000\032\0020\0018\000X?\004?\006\002\n\000\"\020\020\002\032\0020\0018\000X?\004?\006\002\n\000\"\016\020\003\032\0020\004X?T?\006\002\n\000\"\020\020\005\032\0020\0068\000X?\004?\006\002\n\000\"\020\020\007\032\0020\0018\000X?\004?\006\002\n\000\"\020\020\b\032\0020\0068\000X?\004?\006\002\n\000\"\022\020\t\032\0020\n8\000@\000X?\016?\006\002\n\000\"\031\020\013\032\0020\f*\0020\r8?\002X?\004?\006\006\032\004\b\013\020\016?\006\017"}, d2={"BLOCKING_DEFAULT_PARALLELISM", "", "CORE_POOL_SIZE", "DEFAULT_SCHEDULER_NAME", "", "IDLE_WORKER_KEEP_ALIVE_NS", "", "MAX_POOL_SIZE", "WORK_STEALING_TIME_RESOLUTION_NS", "schedulerTimeSource", "Lkotlinx/coroutines/scheduling/TimeSource;", "isBlocking", "", "Lkotlinx/coroutines/scheduling/Task;", "(Lkotlinx/coroutines/scheduling/Task;)Z", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class TasksKt
{
  public static final int BLOCKING_DEFAULT_PARALLELISM;
  public static final int CORE_POOL_SIZE;
  public static final String DEFAULT_SCHEDULER_NAME = "DefaultDispatcher";
  public static final long IDLE_WORKER_KEEP_ALIVE_NS = TimeUnit.SECONDS.toNanos(SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 0L, 0L, 12, null));
  public static final int MAX_POOL_SIZE;
  public static final long WORK_STEALING_TIME_RESOLUTION_NS = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.resolution.ns", 100000L, 0L, 0L, 12, null);
  public static TimeSource schedulerTimeSource = (TimeSource)NanoTimeSource.INSTANCE;
  
  static
  {
    BLOCKING_DEFAULT_PARALLELISM = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.blocking.parallelism", 16, 0, 0, 12, null);
    CORE_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.core.pool.size", RangesKt.coerceAtLeast(SystemPropsKt.getAVAILABLE_PROCESSORS(), 2), 1, 0, 8, null);
    MAX_POOL_SIZE = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.max.pool.size", RangesKt.coerceIn(SystemPropsKt.getAVAILABLE_PROCESSORS() * 128, CORE_POOL_SIZE, 2097150), 0, 2097150, 4, null);
  }
  
  public static final boolean isBlocking(Task paramTask)
  {
    Intrinsics.checkParameterIsNotNull(paramTask, "$this$isBlocking");
    boolean bool;
    if (paramTask.taskContext.getTaskMode() == TaskMode.PROBABLY_BLOCKING) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
