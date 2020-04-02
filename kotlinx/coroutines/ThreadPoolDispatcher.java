package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\002\b\000\030\0002\0020\001B\027\b\000\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\b\020\r\032\0020\016H\026J\b\020\017\032\0020\005H\026R\024\020\007\032\0020\bX?\004?\006\b\n\000\032\004\b\t\020\nR\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\004\032\0020\005X?\004?\006\002\n\000R\016\020\013\032\0020\fX?\004?\006\002\n\000?\006\020"}, d2={"Lkotlinx/coroutines/ThreadPoolDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcherBase;", "nThreads", "", "name", "", "(ILjava/lang/String;)V", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "threadNo", "Ljava/util/concurrent/atomic/AtomicInteger;", "close", "", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ThreadPoolDispatcher
  extends ExecutorCoroutineDispatcherBase
{
  private final Executor executor;
  private final int nThreads;
  private final String name;
  private final AtomicInteger threadNo;
  
  public ThreadPoolDispatcher(int paramInt, String paramString)
  {
    this.nThreads = paramInt;
    this.name = paramString;
    this.threadNo = new AtomicInteger();
    paramString = Executors.newScheduledThreadPool(this.nThreads, (ThreadFactory)new ThreadFactory()
    {
      public final PoolThread newThread(Runnable paramAnonymousRunnable)
      {
        ThreadPoolDispatcher localThreadPoolDispatcher = this.this$0;
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousRunnable, "target");
        Object localObject;
        if (ThreadPoolDispatcher.access$getNThreads$p(this.this$0) == 1)
        {
          localObject = ThreadPoolDispatcher.access$getName$p(this.this$0);
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(ThreadPoolDispatcher.access$getName$p(this.this$0));
          ((StringBuilder)localObject).append("-");
          ((StringBuilder)localObject).append(ThreadPoolDispatcher.access$getThreadNo$p(this.this$0).incrementAndGet());
          localObject = ((StringBuilder)localObject).toString();
        }
        return new PoolThread(localThreadPoolDispatcher, paramAnonymousRunnable, (String)localObject);
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(paramString, "Executors.newScheduledTh….incrementAndGet())\n    }");
    this.executor = ((Executor)paramString);
    initFutureCancellation$kotlinx_coroutines_core();
  }
  
  public void close()
  {
    Executor localExecutor = getExecutor();
    if (localExecutor != null)
    {
      ((ExecutorService)localExecutor).shutdown();
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.ExecutorService");
  }
  
  public Executor getExecutor()
  {
    return this.executor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ThreadPoolDispatcher[");
    localStringBuilder.append(this.nThreads);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.name);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
