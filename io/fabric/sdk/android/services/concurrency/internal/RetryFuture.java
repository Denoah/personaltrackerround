package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

class RetryFuture<T>
  extends AbstractFuture<T>
  implements Runnable
{
  private final RetryThreadPoolExecutor executor;
  RetryState retryState;
  private final AtomicReference<Thread> runner;
  private final Callable<T> task;
  
  RetryFuture(Callable<T> paramCallable, RetryState paramRetryState, RetryThreadPoolExecutor paramRetryThreadPoolExecutor)
  {
    this.task = paramCallable;
    this.retryState = paramRetryState;
    this.executor = paramRetryThreadPoolExecutor;
    this.runner = new AtomicReference();
  }
  
  private Backoff getBackoff()
  {
    return this.retryState.getBackoff();
  }
  
  private int getRetryCount()
  {
    return this.retryState.getRetryCount();
  }
  
  private RetryPolicy getRetryPolicy()
  {
    return this.retryState.getRetryPolicy();
  }
  
  protected void interruptTask()
  {
    Thread localThread = (Thread)this.runner.getAndSet(null);
    if (localThread != null) {
      localThread.interrupt();
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 65	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:isDone	()Z
    //   4: ifne +127 -> 131
    //   7: aload_0
    //   8: getfield 33	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:runner	Ljava/util/concurrent/atomic/AtomicReference;
    //   11: aconst_null
    //   12: invokestatic 69	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   15: invokevirtual 73	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   18: ifne +6 -> 24
    //   21: goto +110 -> 131
    //   24: aload_0
    //   25: aload_0
    //   26: getfield 24	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:task	Ljava/util/concurrent/Callable;
    //   29: invokeinterface 79 1 0
    //   34: invokevirtual 83	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:set	(Ljava/lang/Object;)Z
    //   37: pop
    //   38: aload_0
    //   39: getfield 33	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:runner	Ljava/util/concurrent/atomic/AtomicReference;
    //   42: aconst_null
    //   43: invokevirtual 55	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   46: pop
    //   47: goto +71 -> 118
    //   50: astore_1
    //   51: aload_0
    //   52: invokespecial 84	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:getRetryPolicy	()Lio/fabric/sdk/android/services/concurrency/internal/RetryPolicy;
    //   55: aload_0
    //   56: invokespecial 85	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:getRetryCount	()I
    //   59: aload_1
    //   60: invokeinterface 91 3 0
    //   65: ifeq +44 -> 109
    //   68: aload_0
    //   69: invokespecial 92	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:getBackoff	()Lio/fabric/sdk/android/services/concurrency/internal/Backoff;
    //   72: aload_0
    //   73: invokespecial 85	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:getRetryCount	()I
    //   76: invokeinterface 98 2 0
    //   81: lstore_2
    //   82: aload_0
    //   83: aload_0
    //   84: getfield 26	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:retryState	Lio/fabric/sdk/android/services/concurrency/internal/RetryState;
    //   87: invokevirtual 102	io/fabric/sdk/android/services/concurrency/internal/RetryState:nextRetryState	()Lio/fabric/sdk/android/services/concurrency/internal/RetryState;
    //   90: putfield 26	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:retryState	Lio/fabric/sdk/android/services/concurrency/internal/RetryState;
    //   93: aload_0
    //   94: getfield 28	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:executor	Lio/fabric/sdk/android/services/concurrency/internal/RetryThreadPoolExecutor;
    //   97: aload_0
    //   98: lload_2
    //   99: getstatic 108	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   102: invokevirtual 114	io/fabric/sdk/android/services/concurrency/internal/RetryThreadPoolExecutor:schedule	(Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;
    //   105: pop
    //   106: goto -68 -> 38
    //   109: aload_0
    //   110: aload_1
    //   111: invokevirtual 118	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:setException	(Ljava/lang/Throwable;)Z
    //   114: pop
    //   115: goto -77 -> 38
    //   118: return
    //   119: astore_1
    //   120: aload_0
    //   121: getfield 33	io/fabric/sdk/android/services/concurrency/internal/RetryFuture:runner	Ljava/util/concurrent/atomic/AtomicReference;
    //   124: aconst_null
    //   125: invokevirtual 55	java/util/concurrent/atomic/AtomicReference:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
    //   128: pop
    //   129: aload_1
    //   130: athrow
    //   131: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	RetryFuture
    //   50	61	1	localThrowable	Throwable
    //   119	11	1	localObject	Object
    //   81	18	2	l	long
    // Exception table:
    //   from	to	target	type
    //   24	38	50	finally
    //   51	106	119	finally
    //   109	115	119	finally
  }
}
