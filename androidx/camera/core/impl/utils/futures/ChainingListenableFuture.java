package androidx.camera.core.impl.utils.futures;

import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class ChainingListenableFuture<I, O>
  extends FutureChain<O>
  implements Runnable
{
  private AsyncFunction<? super I, ? extends O> mFunction;
  private ListenableFuture<? extends I> mInputFuture;
  private final BlockingQueue<Boolean> mMayInterruptIfRunningChannel = new LinkedBlockingQueue(1);
  private final CountDownLatch mOutputCreated = new CountDownLatch(1);
  volatile ListenableFuture<? extends O> mOutputFuture;
  
  ChainingListenableFuture(AsyncFunction<? super I, ? extends O> paramAsyncFunction, ListenableFuture<? extends I> paramListenableFuture)
  {
    this.mFunction = ((AsyncFunction)Preconditions.checkNotNull(paramAsyncFunction));
    this.mInputFuture = ((ListenableFuture)Preconditions.checkNotNull(paramListenableFuture));
  }
  
  private void cancel(Future<?> paramFuture, boolean paramBoolean)
  {
    if (paramFuture != null) {
      paramFuture.cancel(paramBoolean);
    }
  }
  
  /* Error */
  private <E> void putUninterruptibly(BlockingQueue<E> paramBlockingQueue, E paramE)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aload_1
    //   3: aload_2
    //   4: invokeinterface 74 2 0
    //   9: iload_3
    //   10: ifeq +9 -> 19
    //   13: invokestatic 80	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   16: invokevirtual 83	java/lang/Thread:interrupt	()V
    //   19: return
    //   20: astore_1
    //   21: iload_3
    //   22: ifeq +9 -> 31
    //   25: invokestatic 80	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   28: invokevirtual 83	java/lang/Thread:interrupt	()V
    //   31: aload_1
    //   32: athrow
    //   33: astore 4
    //   35: iconst_1
    //   36: istore_3
    //   37: goto -35 -> 2
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	ChainingListenableFuture
    //   0	40	1	paramBlockingQueue	BlockingQueue<E>
    //   0	40	2	paramE	E
    //   1	36	3	i	int
    //   33	1	4	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   2	9	20	finally
    //   2	9	33	java/lang/InterruptedException
  }
  
  /* Error */
  private <E> E takeUninterruptibly(BlockingQueue<E> paramBlockingQueue)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_1
    //   3: invokeinterface 90 1 0
    //   8: astore_3
    //   9: iload_2
    //   10: ifeq +9 -> 19
    //   13: invokestatic 80	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   16: invokevirtual 83	java/lang/Thread:interrupt	()V
    //   19: aload_3
    //   20: areturn
    //   21: astore_1
    //   22: iload_2
    //   23: ifeq +9 -> 32
    //   26: invokestatic 80	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   29: invokevirtual 83	java/lang/Thread:interrupt	()V
    //   32: aload_1
    //   33: athrow
    //   34: astore_3
    //   35: iconst_1
    //   36: istore_2
    //   37: goto -35 -> 2
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	ChainingListenableFuture
    //   0	40	1	paramBlockingQueue	BlockingQueue<E>
    //   1	36	2	i	int
    //   8	12	3	localObject	Object
    //   34	1	3	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   2	9	21	finally
    //   2	9	34	java/lang/InterruptedException
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    if (super.cancel(paramBoolean))
    {
      putUninterruptibly(this.mMayInterruptIfRunningChannel, Boolean.valueOf(paramBoolean));
      cancel(this.mInputFuture, paramBoolean);
      cancel(this.mOutputFuture, paramBoolean);
      return true;
    }
    return false;
  }
  
  public O get()
    throws InterruptedException, ExecutionException
  {
    if (!isDone())
    {
      ListenableFuture localListenableFuture = this.mInputFuture;
      if (localListenableFuture != null) {
        localListenableFuture.get();
      }
      this.mOutputCreated.await();
      localListenableFuture = this.mOutputFuture;
      if (localListenableFuture != null) {
        localListenableFuture.get();
      }
    }
    return super.get();
  }
  
  public O get(long paramLong, TimeUnit paramTimeUnit)
    throws TimeoutException, ExecutionException, InterruptedException
  {
    long l = paramLong;
    Object localObject = paramTimeUnit;
    if (!isDone())
    {
      l = paramLong;
      TimeUnit localTimeUnit = paramTimeUnit;
      if (paramTimeUnit != TimeUnit.NANOSECONDS)
      {
        l = TimeUnit.NANOSECONDS.convert(paramLong, paramTimeUnit);
        localTimeUnit = TimeUnit.NANOSECONDS;
      }
      paramTimeUnit = this.mInputFuture;
      paramLong = l;
      if (paramTimeUnit != null)
      {
        paramLong = System.nanoTime();
        paramTimeUnit.get(l, localTimeUnit);
        paramLong = l - Math.max(0L, System.nanoTime() - paramLong);
      }
      l = System.nanoTime();
      if (this.mOutputCreated.await(paramLong, localTimeUnit))
      {
        paramLong -= Math.max(0L, System.nanoTime() - l);
        paramTimeUnit = this.mOutputFuture;
        l = paramLong;
        localObject = localTimeUnit;
        if (paramTimeUnit != null)
        {
          paramTimeUnit.get(paramLong, localTimeUnit);
          l = paramLong;
          localObject = localTimeUnit;
        }
      }
      else
      {
        throw new TimeoutException();
      }
    }
    return super.get(l, (TimeUnit)localObject);
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 53	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mInputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   4: invokestatic 167	androidx/camera/core/impl/utils/futures/Futures:getUninterruptibly	(Ljava/util/concurrent/Future;)Ljava/lang/Object;
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 49	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mFunction	Landroidx/camera/core/impl/utils/futures/AsyncFunction;
    //   12: aload_1
    //   13: invokeinterface 171 2 0
    //   18: astore_2
    //   19: aload_0
    //   20: aload_2
    //   21: putfield 104	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mOutputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   24: aload_0
    //   25: invokevirtual 174	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:isCancelled	()Z
    //   28: ifeq +47 -> 75
    //   31: aload_2
    //   32: aload_0
    //   33: aload_0
    //   34: getfield 34	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mMayInterruptIfRunningChannel	Ljava/util/concurrent/BlockingQueue;
    //   37: invokespecial 176	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:takeUninterruptibly	(Ljava/util/concurrent/BlockingQueue;)Ljava/lang/Object;
    //   40: checkcast 94	java/lang/Boolean
    //   43: invokevirtual 179	java/lang/Boolean:booleanValue	()Z
    //   46: invokeinterface 180 2 0
    //   51: pop
    //   52: aload_0
    //   53: aconst_null
    //   54: putfield 104	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mOutputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   57: aload_0
    //   58: aconst_null
    //   59: putfield 49	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mFunction	Landroidx/camera/core/impl/utils/futures/AsyncFunction;
    //   62: aload_0
    //   63: aconst_null
    //   64: putfield 53	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mInputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   67: aload_0
    //   68: getfield 39	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mOutputCreated	Ljava/util/concurrent/CountDownLatch;
    //   71: invokevirtual 183	java/util/concurrent/CountDownLatch:countDown	()V
    //   74: return
    //   75: new 9	androidx/camera/core/impl/utils/futures/ChainingListenableFuture$1
    //   78: astore_1
    //   79: aload_1
    //   80: aload_0
    //   81: aload_2
    //   82: invokespecial 186	androidx/camera/core/impl/utils/futures/ChainingListenableFuture$1:<init>	(Landroidx/camera/core/impl/utils/futures/ChainingListenableFuture;Lcom/google/common/util/concurrent/ListenableFuture;)V
    //   85: aload_2
    //   86: aload_1
    //   87: invokestatic 192	androidx/camera/core/impl/utils/executor/CameraXExecutors:directExecutor	()Ljava/util/concurrent/Executor;
    //   90: invokeinterface 196 3 0
    //   95: goto +57 -> 152
    //   98: astore_1
    //   99: goto +86 -> 185
    //   102: astore_1
    //   103: goto +34 -> 137
    //   106: astore_1
    //   107: goto +39 -> 146
    //   110: astore_1
    //   111: goto +61 -> 172
    //   114: astore_1
    //   115: aload_0
    //   116: aload_1
    //   117: invokevirtual 200	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   120: invokevirtual 204	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:setException	(Ljava/lang/Throwable;)Z
    //   123: pop
    //   124: goto -67 -> 57
    //   127: astore_1
    //   128: aload_0
    //   129: iconst_0
    //   130: invokevirtual 205	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:cancel	(Z)Z
    //   133: pop
    //   134: goto -77 -> 57
    //   137: aload_0
    //   138: aload_1
    //   139: invokevirtual 204	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:setException	(Ljava/lang/Throwable;)Z
    //   142: pop
    //   143: goto +9 -> 152
    //   146: aload_0
    //   147: aload_1
    //   148: invokevirtual 204	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:setException	(Ljava/lang/Throwable;)Z
    //   151: pop
    //   152: aload_0
    //   153: aconst_null
    //   154: putfield 49	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mFunction	Landroidx/camera/core/impl/utils/futures/AsyncFunction;
    //   157: aload_0
    //   158: aconst_null
    //   159: putfield 53	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mInputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   162: aload_0
    //   163: getfield 39	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mOutputCreated	Ljava/util/concurrent/CountDownLatch;
    //   166: invokevirtual 183	java/util/concurrent/CountDownLatch:countDown	()V
    //   169: goto +15 -> 184
    //   172: aload_0
    //   173: aload_1
    //   174: invokevirtual 206	java/lang/reflect/UndeclaredThrowableException:getCause	()Ljava/lang/Throwable;
    //   177: invokevirtual 204	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:setException	(Ljava/lang/Throwable;)Z
    //   180: pop
    //   181: goto -29 -> 152
    //   184: return
    //   185: aload_0
    //   186: aconst_null
    //   187: putfield 49	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mFunction	Landroidx/camera/core/impl/utils/futures/AsyncFunction;
    //   190: aload_0
    //   191: aconst_null
    //   192: putfield 53	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mInputFuture	Lcom/google/common/util/concurrent/ListenableFuture;
    //   195: aload_0
    //   196: getfield 39	androidx/camera/core/impl/utils/futures/ChainingListenableFuture:mOutputCreated	Ljava/util/concurrent/CountDownLatch;
    //   199: invokevirtual 183	java/util/concurrent/CountDownLatch:countDown	()V
    //   202: aload_1
    //   203: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	204	0	this	ChainingListenableFuture
    //   7	80	1	localObject1	Object
    //   98	1	1	localObject2	Object
    //   102	1	1	localError	Error
    //   106	1	1	localException	Exception
    //   110	1	1	localUndeclaredThrowableException	java.lang.reflect.UndeclaredThrowableException
    //   114	3	1	localExecutionException	ExecutionException
    //   127	76	1	localCancellationException	java.util.concurrent.CancellationException
    //   18	68	2	localListenableFuture	ListenableFuture
    // Exception table:
    //   from	to	target	type
    //   0	8	98	finally
    //   8	57	98	finally
    //   75	95	98	finally
    //   115	124	98	finally
    //   128	134	98	finally
    //   137	143	98	finally
    //   146	152	98	finally
    //   172	181	98	finally
    //   0	8	102	java/lang/Error
    //   8	57	102	java/lang/Error
    //   75	95	102	java/lang/Error
    //   115	124	102	java/lang/Error
    //   128	134	102	java/lang/Error
    //   0	8	106	java/lang/Exception
    //   8	57	106	java/lang/Exception
    //   75	95	106	java/lang/Exception
    //   115	124	106	java/lang/Exception
    //   128	134	106	java/lang/Exception
    //   0	8	110	java/lang/reflect/UndeclaredThrowableException
    //   8	57	110	java/lang/reflect/UndeclaredThrowableException
    //   75	95	110	java/lang/reflect/UndeclaredThrowableException
    //   115	124	110	java/lang/reflect/UndeclaredThrowableException
    //   128	134	110	java/lang/reflect/UndeclaredThrowableException
    //   0	8	114	java/util/concurrent/ExecutionException
    //   0	8	127	java/util/concurrent/CancellationException
  }
}
