package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b\000\030\0002\0020\001B!\022\006\020\002\032\0020\003\022\n\020\004\032\0060\005j\002`\006\022\006\020\007\032\0020\b?\006\002\020\tR\020\020\002\032\0020\0038\006X?\004?\006\002\n\000?\006\n"}, d2={"Lkotlinx/coroutines/PoolThread;", "Ljava/lang/Thread;", "dispatcher", "Lkotlinx/coroutines/ThreadPoolDispatcher;", "target", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "name", "", "(Lkotlinx/coroutines/ThreadPoolDispatcher;Ljava/lang/Runnable;Ljava/lang/String;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class PoolThread
  extends Thread
{
  public final ThreadPoolDispatcher dispatcher;
  
  public PoolThread(ThreadPoolDispatcher paramThreadPoolDispatcher, Runnable paramRunnable, String paramString)
  {
    super(paramRunnable, paramString);
    this.dispatcher = paramThreadPoolDispatcher;
    setDaemon(true);
  }
}
