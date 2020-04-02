package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\b \030\0002\0020\001B\005?\006\002\020\002J\030\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\fH\004J\b\020\r\032\0020\bH\004R\022\020\003\032\0020\004X¤\004?\006\006\032\004\b\005\020\006?\006\016"}, d2={"Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/EventLoop;", "()V", "thread", "Ljava/lang/Thread;", "getThread", "()Ljava/lang/Thread;", "reschedule", "", "now", "", "delayedTask", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "unpark", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class EventLoopImplPlatform
  extends EventLoop
{
  public EventLoopImplPlatform() {}
  
  protected abstract Thread getThread();
  
  protected final void reschedule(long paramLong, EventLoopImplBase.DelayedTask paramDelayedTask)
  {
    Intrinsics.checkParameterIsNotNull(paramDelayedTask, "delayedTask");
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if ((EventLoopImplPlatform)this != DefaultExecutor.INSTANCE) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    DefaultExecutor.INSTANCE.schedule(paramLong, paramDelayedTask);
  }
  
  protected final void unpark()
  {
    Thread localThread = getThread();
    if (Thread.currentThread() != localThread)
    {
      TimeSource localTimeSource = TimeSourceKt.getTimeSource();
      if (localTimeSource != null) {
        localTimeSource.unpark(localThread);
      } else {
        LockSupport.unpark(localThread);
      }
    }
  }
}
