package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\000\n\002\020\t\n\000\032\b\020\000\032\0020\001H\000\032\b\020\002\032\0020\003H\007?\006\004"}, d2={"createEventLoop", "Lkotlinx/coroutines/EventLoop;", "processNextEventInCurrentThread", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class EventLoopKt
{
  public static final EventLoop createEventLoop()
  {
    Thread localThread = Thread.currentThread();
    Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
    return (EventLoop)new BlockingEventLoop(localThread);
  }
  
  public static final long processNextEventInCurrentThread()
  {
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
    long l;
    if (localEventLoop != null) {
      l = localEventLoop.processNextEvent();
    } else {
      l = Long.MAX_VALUE;
    }
    return l;
  }
}
