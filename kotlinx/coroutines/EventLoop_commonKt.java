package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\b\005\n\002\020\t\n\002\b\003\n\002\020\b\n\002\b\b\n\002\030\002\n\000\032\020\020\016\032\0020\0072\006\020\017\032\0020\007H\000\032\020\020\020\032\0020\0072\006\020\021\032\0020\007H\000\"\026\020\000\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\005\020\003\"\016\020\006\032\0020\007X?T?\006\002\n\000\"\016\020\b\032\0020\007X?T?\006\002\n\000\"\016\020\t\032\0020\007X?T?\006\002\n\000\"\016\020\n\032\0020\013X?T?\006\002\n\000\"\016\020\f\032\0020\013X?T?\006\002\n\000\"\016\020\r\032\0020\013X?T?\006\002\n\000*\036\b\002\020\022\032\004\b\000\020\023\"\b\022\004\022\002H\0230\0242\b\022\004\022\002H\0230\024?\006\025"}, d2={"CLOSED_EMPTY", "Lkotlinx/coroutines/internal/Symbol;", "CLOSED_EMPTY$annotations", "()V", "DISPOSED_TASK", "DISPOSED_TASK$annotations", "MAX_DELAY_NS", "", "MAX_MS", "MS_TO_NS", "SCHEDULE_COMPLETED", "", "SCHEDULE_DISPOSED", "SCHEDULE_OK", "delayNanosToMillis", "timeNanos", "delayToNanos", "timeMillis", "Queue", "T", "Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class EventLoop_commonKt
{
  private static final Symbol CLOSED_EMPTY = new Symbol("CLOSED_EMPTY");
  private static final Symbol DISPOSED_TASK = new Symbol("REMOVED_TASK");
  private static final long MAX_DELAY_NS = 4611686018427387903L;
  private static final long MAX_MS = 9223372036854L;
  private static final long MS_TO_NS = 1000000L;
  private static final int SCHEDULE_COMPLETED = 1;
  private static final int SCHEDULE_DISPOSED = 2;
  private static final int SCHEDULE_OK = 0;
  
  public static final long delayNanosToMillis(long paramLong)
  {
    return paramLong / 1000000L;
  }
  
  public static final long delayToNanos(long paramLong)
  {
    long l = 0L;
    if (paramLong <= 0L) {
      paramLong = l;
    } else if (paramLong >= 9223372036854L) {
      paramLong = Long.MAX_VALUE;
    } else {
      paramLong = 1000000L * paramLong;
    }
    return paramLong;
  }
}
