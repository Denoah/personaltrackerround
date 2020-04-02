package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\000\n\002\030\002\n\002\b\005\n\002\020\t\n\002\b\002\n\002\020\002\n\000\n\002\020\000\n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\032\t\020\006\032\0020\007H?\b\032\t\020\b\032\0020\007H?\b\032\031\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\007H?\b\032\t\020\016\032\0020\nH?\b\032\t\020\017\032\0020\nH?\b\032\t\020\020\032\0020\nH?\b\032\021\020\021\032\0020\n2\006\020\022\032\0020\023H?\b\032\t\020\024\032\0020\nH?\b\032\031\020\025\032\0060\026j\002`\0272\n\020\030\032\0060\026j\002`\027H?\b\"\034\020\000\032\004\030\0010\001X?\016?\006\016\n\000\032\004\b\002\020\003\"\004\b\004\020\005?\006\031"}, d2={"timeSource", "Lkotlinx/coroutines/TimeSource;", "getTimeSource", "()Lkotlinx/coroutines/TimeSource;", "setTimeSource", "(Lkotlinx/coroutines/TimeSource;)V", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class TimeSourceKt
{
  private static TimeSource timeSource;
  
  private static final long currentTimeMillis()
  {
    TimeSource localTimeSource = getTimeSource();
    long l;
    if (localTimeSource != null) {
      l = localTimeSource.currentTimeMillis();
    } else {
      l = System.currentTimeMillis();
    }
    return l;
  }
  
  public static final TimeSource getTimeSource()
  {
    return timeSource;
  }
  
  private static final long nanoTime()
  {
    TimeSource localTimeSource = getTimeSource();
    long l;
    if (localTimeSource != null) {
      l = localTimeSource.nanoTime();
    } else {
      l = System.nanoTime();
    }
    return l;
  }
  
  private static final void parkNanos(Object paramObject, long paramLong)
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.parkNanos(paramObject, paramLong);
    } else {
      LockSupport.parkNanos(paramObject, paramLong);
    }
  }
  
  private static final void registerTimeLoopThread()
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.registerTimeLoopThread();
    }
  }
  
  public static final void setTimeSource(TimeSource paramTimeSource)
  {
    timeSource = paramTimeSource;
  }
  
  private static final void trackTask()
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.trackTask();
    }
  }
  
  private static final void unTrackTask()
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.unTrackTask();
    }
  }
  
  private static final void unpark(Thread paramThread)
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.unpark(paramThread);
    } else {
      LockSupport.unpark(paramThread);
    }
  }
  
  private static final void unregisterTimeLoopThread()
  {
    TimeSource localTimeSource = getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.unregisterTimeLoopThread();
    }
  }
  
  private static final Runnable wrapTask(Runnable paramRunnable)
  {
    Object localObject1 = getTimeSource();
    Object localObject2 = paramRunnable;
    if (localObject1 != null)
    {
      localObject1 = ((TimeSource)localObject1).wrapTask(paramRunnable);
      localObject2 = paramRunnable;
      if (localObject1 != null) {
        localObject2 = localObject1;
      }
    }
    return localObject2;
  }
}
