package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DefaultExecutor;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000^\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\f\b\027\030\0002\0020\001B%\b\026\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\003\022\b\b\002\020\005\032\0020\006?\006\002\020\007B\033\b\027\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\003?\006\002\020\bB'\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\t\032\0020\n\022\b\b\002\020\005\032\0020\006?\006\002\020\013J\020\020\022\032\0020\0232\b\b\002\020\024\032\0020\003J\b\020\025\032\0020\026H\026J\b\020\027\032\0020\rH\002J\034\020\030\032\0020\0262\006\020\031\032\0020\0322\n\020\033\032\0060\034j\002`\035H\026J)\020\036\032\0020\0262\n\020\033\032\0060\034j\002`\0352\006\020\031\032\0020\0372\006\020 \032\0020!H\000?\006\002\b\"J\034\020#\032\0020\0262\006\020\031\032\0020\0322\n\020\033\032\0060\034j\002`\035H\026J\016\020$\032\0020\0232\006\020\024\032\0020\003J\r\020%\032\0020\026H\000?\006\002\b&J\025\020'\032\0020\0262\006\020(\032\0020\nH\000?\006\002\b)J\b\020*\032\0020\006H\026J\r\020+\032\0020\026H\000?\006\002\b,R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\f\032\0020\rX?\016?\006\002\n\000R\024\020\016\032\0020\0178VX?\004?\006\006\032\004\b\020\020\021R\016\020\t\032\0020\nX?\004?\006\002\n\000R\016\020\004\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\004?\006\002\n\000?\006-"}, d2={"Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "corePoolSize", "", "maxPoolSize", "schedulerName", "", "(IILjava/lang/String;)V", "(II)V", "idleWorkerKeepAliveNs", "", "(IIJLjava/lang/String;)V", "coroutineScheduler", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "blocking", "Lkotlinx/coroutines/CoroutineDispatcher;", "parallelism", "close", "", "createScheduler", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchWithContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "fair", "", "dispatchWithContext$kotlinx_coroutines_core", "dispatchYield", "limited", "restore", "restore$kotlinx_coroutines_core", "shutdown", "timeout", "shutdown$kotlinx_coroutines_core", "toString", "usePrivateScheduler", "usePrivateScheduler$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class ExperimentalCoroutineDispatcher
  extends ExecutorCoroutineDispatcher
{
  private final int corePoolSize;
  private CoroutineScheduler coroutineScheduler;
  private final long idleWorkerKeepAliveNs;
  private final int maxPoolSize;
  private final String schedulerName;
  
  public ExperimentalCoroutineDispatcher(int paramInt1, int paramInt2, long paramLong, String paramString)
  {
    this.corePoolSize = paramInt1;
    this.maxPoolSize = paramInt2;
    this.idleWorkerKeepAliveNs = paramLong;
    this.schedulerName = paramString;
    this.coroutineScheduler = createScheduler();
  }
  
  public ExperimentalCoroutineDispatcher(int paramInt1, int paramInt2, String paramString)
  {
    this(paramInt1, paramInt2, TasksKt.IDLE_WORKER_KEEP_ALIVE_NS, paramString);
  }
  
  private final CoroutineScheduler createScheduler()
  {
    return new CoroutineScheduler(this.corePoolSize, this.maxPoolSize, this.idleWorkerKeepAliveNs, this.schedulerName);
  }
  
  public final CoroutineDispatcher blocking(int paramInt)
  {
    int i;
    if (paramInt > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return (CoroutineDispatcher)new LimitingDispatcher(this, paramInt, TaskMode.PROBABLY_BLOCKING);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected positive parallelism level, but have ");
    localStringBuilder.append(paramInt);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  public void close()
  {
    this.coroutineScheduler.close();
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    try
    {
      CoroutineScheduler.dispatch$default(this.coroutineScheduler, paramRunnable, null, false, 6, null);
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      DefaultExecutor.INSTANCE.dispatch(paramCoroutineContext, paramRunnable);
    }
  }
  
  public final void dispatchWithContext$kotlinx_coroutines_core(Runnable paramRunnable, TaskContext paramTaskContext, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    Intrinsics.checkParameterIsNotNull(paramTaskContext, "context");
    try
    {
      this.coroutineScheduler.dispatch(paramRunnable, paramTaskContext, paramBoolean);
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      DefaultExecutor.INSTANCE.enqueue((Runnable)this.coroutineScheduler.createTask$kotlinx_coroutines_core(paramRunnable, paramTaskContext));
    }
  }
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    try
    {
      CoroutineScheduler.dispatch$default(this.coroutineScheduler, paramRunnable, null, true, 2, null);
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      DefaultExecutor.INSTANCE.dispatchYield(paramCoroutineContext, paramRunnable);
    }
  }
  
  public Executor getExecutor()
  {
    return (Executor)this.coroutineScheduler;
  }
  
  public final CoroutineDispatcher limited(int paramInt)
  {
    int i = 1;
    int j;
    if (paramInt > 0) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramInt <= this.corePoolSize) {
        j = i;
      } else {
        j = 0;
      }
      if (j != 0) {
        return (CoroutineDispatcher)new LimitingDispatcher(this, paramInt, TaskMode.NON_BLOCKING);
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Expected parallelism level lesser than core pool size (");
      localStringBuilder.append(this.corePoolSize);
      localStringBuilder.append("), but have ");
      localStringBuilder.append(paramInt);
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected positive parallelism level, but have ");
    localStringBuilder.append(paramInt);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  public final void restore$kotlinx_coroutines_core()
  {
    usePrivateScheduler$kotlinx_coroutines_core();
  }
  
  public final void shutdown$kotlinx_coroutines_core(long paramLong)
  {
    try
    {
      this.coroutineScheduler.shutdown(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append("[scheduler = ");
    localStringBuilder.append(this.coroutineScheduler);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  public final void usePrivateScheduler$kotlinx_coroutines_core()
  {
    try
    {
      this.coroutineScheduler.shutdown(1000L);
      this.coroutineScheduler = createScheduler();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}
