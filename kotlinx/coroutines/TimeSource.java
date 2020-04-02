package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\020\000\n\000\n\002\020\t\n\002\b\002\n\002\020\002\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\b`\030\0002\0020\001J\b\020\002\032\0020\003H&J\b\020\004\032\0020\003H&J\030\020\005\032\0020\0062\006\020\007\032\0020\0012\006\020\b\032\0020\003H&J\b\020\t\032\0020\006H&J\b\020\n\032\0020\006H&J\b\020\013\032\0020\006H&J\020\020\f\032\0020\0062\006\020\r\032\0020\016H&J\b\020\017\032\0020\006H&J\030\020\020\032\0060\021j\002`\0222\n\020\023\032\0060\021j\002`\022H&?\006\024"}, d2={"Lkotlinx/coroutines/TimeSource;", "", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface TimeSource
{
  public abstract long currentTimeMillis();
  
  public abstract long nanoTime();
  
  public abstract void parkNanos(Object paramObject, long paramLong);
  
  public abstract void registerTimeLoopThread();
  
  public abstract void trackTask();
  
  public abstract void unTrackTask();
  
  public abstract void unpark(Thread paramThread);
  
  public abstract void unregisterTimeLoopThread();
  
  public abstract Runnable wrapTask(Runnable paramRunnable);
}
