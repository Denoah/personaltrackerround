package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004R\024\020\002\032\0020\003X?\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lkotlinx/coroutines/BlockingEventLoop;", "Lkotlinx/coroutines/EventLoopImplBase;", "thread", "Ljava/lang/Thread;", "(Ljava/lang/Thread;)V", "getThread", "()Ljava/lang/Thread;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class BlockingEventLoop
  extends EventLoopImplBase
{
  private final Thread thread;
  
  public BlockingEventLoop(Thread paramThread)
  {
    this.thread = paramThread;
  }
  
  protected Thread getThread()
  {
    return this.thread;
  }
}
