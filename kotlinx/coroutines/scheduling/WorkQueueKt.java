package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\020\b\n\002\b\003\n\002\020\t\n\002\b\002\"\016\020\000\032\0020\001X?T?\006\002\n\000\"\016\020\002\032\0020\001X?T?\006\002\n\000\"\016\020\003\032\0020\001X?T?\006\002\n\000\"\016\020\004\032\0020\005X?T?\006\002\n\000\"\016\020\006\032\0020\005X?T?\006\002\n\000?\006\007"}, d2={"BUFFER_CAPACITY", "", "BUFFER_CAPACITY_BASE", "MASK", "NOTHING_TO_STEAL", "", "TASK_STOLEN", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class WorkQueueKt
{
  public static final int BUFFER_CAPACITY = 128;
  public static final int BUFFER_CAPACITY_BASE = 7;
  public static final int MASK = 127;
  public static final long NOTHING_TO_STEAL = -2L;
  public static final long TASK_STOLEN = -1L;
}
