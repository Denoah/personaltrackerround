package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor
  extends ThreadPoolExecutor
{
  private static final int CORE_POOL_SIZE;
  private static final int CPU_COUNT;
  private static final long KEEP_ALIVE = 1L;
  private static final int MAXIMUM_POOL_SIZE;
  
  static
  {
    int i = Runtime.getRuntime().availableProcessors();
    CPU_COUNT = i;
    CORE_POOL_SIZE = i + 1;
    MAXIMUM_POOL_SIZE = i * 2 + 1;
  }
  
  <T extends Runnable,  extends Dependency,  extends Task,  extends PriorityProvider> PriorityThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, DependencyPriorityBlockingQueue<T> paramDependencyPriorityBlockingQueue, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramDependencyPriorityBlockingQueue, paramThreadFactory);
    prestartAllCoreThreads();
  }
  
  public static PriorityThreadPoolExecutor create()
  {
    return create(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE);
  }
  
  public static PriorityThreadPoolExecutor create(int paramInt)
  {
    return create(paramInt, paramInt);
  }
  
  public static <T extends Runnable,  extends Dependency,  extends Task,  extends PriorityProvider> PriorityThreadPoolExecutor create(int paramInt1, int paramInt2)
  {
    return new PriorityThreadPoolExecutor(paramInt1, paramInt2, 1L, TimeUnit.SECONDS, new DependencyPriorityBlockingQueue(), new PriorityThreadFactory(10));
  }
  
  protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
  {
    Task localTask = (Task)paramRunnable;
    localTask.setFinished(true);
    localTask.setError(paramThrowable);
    getQueue().recycleBlockedQueue();
    super.afterExecute(paramRunnable, paramThrowable);
  }
  
  public void execute(Runnable paramRunnable)
  {
    if (PriorityTask.isProperDelegate(paramRunnable)) {
      super.execute(paramRunnable);
    } else {
      super.execute(newTaskFor(paramRunnable, null));
    }
  }
  
  public DependencyPriorityBlockingQueue getQueue()
  {
    return (DependencyPriorityBlockingQueue)super.getQueue();
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
  {
    return new PriorityFutureTask(paramRunnable, paramT);
  }
  
  protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
  {
    return new PriorityFutureTask(paramCallable);
  }
  
  protected static final class PriorityThreadFactory
    implements ThreadFactory
  {
    private final int threadPriority;
    
    public PriorityThreadFactory(int paramInt)
    {
      this.threadPriority = paramInt;
    }
    
    public Thread newThread(Runnable paramRunnable)
    {
      paramRunnable = new Thread(paramRunnable);
      paramRunnable.setPriority(this.threadPriority);
      paramRunnable.setName("Queue");
      return paramRunnable;
    }
  }
}
