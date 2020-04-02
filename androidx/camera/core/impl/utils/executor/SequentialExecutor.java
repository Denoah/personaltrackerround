package androidx.camera.core.impl.utils.executor;

import androidx.core.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

final class SequentialExecutor
  implements Executor
{
  private static final String TAG = "SequentialExecutor";
  private final Executor mExecutor;
  final Deque<Runnable> mQueue = new ArrayDeque();
  private final QueueWorker mWorker = new QueueWorker();
  long mWorkerRunCount = 0L;
  WorkerRunningState mWorkerRunningState = WorkerRunningState.IDLE;
  
  SequentialExecutor(Executor paramExecutor)
  {
    this.mExecutor = ((Executor)Preconditions.checkNotNull(paramExecutor));
  }
  
  public void execute(Runnable paramRunnable)
  {
    Preconditions.checkNotNull(paramRunnable);
    synchronized (this.mQueue)
    {
      int i;
      int j;
      if ((this.mWorkerRunningState != WorkerRunningState.RUNNING) && (this.mWorkerRunningState != WorkerRunningState.QUEUED))
      {
        long l = this.mWorkerRunCount;
        ??? = new androidx/camera/core/impl/utils/executor/SequentialExecutor$1;
        ((1)???).<init>(this, paramRunnable);
        this.mQueue.add(???);
        this.mWorkerRunningState = WorkerRunningState.QUEUING;
        i = 1;
        j = 1;
        try
        {
          this.mExecutor.execute(this.mWorker);
          if (this.mWorkerRunningState == WorkerRunningState.QUEUING) {
            j = 0;
          }
          if (j != 0) {
            return;
          }
          synchronized (this.mQueue)
          {
            if ((this.mWorkerRunCount == l) && (this.mWorkerRunningState == WorkerRunningState.QUEUING)) {
              this.mWorkerRunningState = WorkerRunningState.QUEUED;
            }
            return;
          }
          synchronized (this.mQueue)
          {
            if (((this.mWorkerRunningState == WorkerRunningState.IDLE) || (this.mWorkerRunningState == WorkerRunningState.QUEUING)) && (this.mQueue.removeLastOccurrence(???))) {
              j = i;
            } else {
              j = 0;
            }
            if (((paramRunnable instanceof RejectedExecutionException)) && (j == 0)) {
              return;
            }
            throw paramRunnable;
          }
        }
        catch (Error paramRunnable) {}catch (RuntimeException paramRunnable) {}
      }
      this.mQueue.add(paramRunnable);
      return;
    }
  }
  
  final class QueueWorker
    implements Runnable
  {
    QueueWorker() {}
    
    /* Error */
    private void workOnQueue()
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore_1
      //   2: iconst_0
      //   3: istore_2
      //   4: iload_2
      //   5: istore_3
      //   6: aload_0
      //   7: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   10: getfield 26	androidx/camera/core/impl/utils/executor/SequentialExecutor:mQueue	Ljava/util/Deque;
      //   13: astore 4
      //   15: iload_2
      //   16: istore_3
      //   17: aload 4
      //   19: monitorenter
      //   20: iload_1
      //   21: istore 5
      //   23: iload_1
      //   24: ifne +61 -> 85
      //   27: aload_0
      //   28: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   31: getfield 30	androidx/camera/core/impl/utils/executor/SequentialExecutor:mWorkerRunningState	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   34: getstatic 35	androidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState:RUNNING	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   37: if_acmpne +17 -> 54
      //   40: aload 4
      //   42: monitorexit
      //   43: iload_2
      //   44: ifeq +9 -> 53
      //   47: invokestatic 41	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   50: invokevirtual 44	java/lang/Thread:interrupt	()V
      //   53: return
      //   54: aload_0
      //   55: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   58: astore 6
      //   60: aload 6
      //   62: aload 6
      //   64: getfield 48	androidx/camera/core/impl/utils/executor/SequentialExecutor:mWorkerRunCount	J
      //   67: lconst_1
      //   68: ladd
      //   69: putfield 48	androidx/camera/core/impl/utils/executor/SequentialExecutor:mWorkerRunCount	J
      //   72: aload_0
      //   73: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   76: getstatic 35	androidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState:RUNNING	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   79: putfield 30	androidx/camera/core/impl/utils/executor/SequentialExecutor:mWorkerRunningState	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   82: iconst_1
      //   83: istore 5
      //   85: aload_0
      //   86: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   89: getfield 26	androidx/camera/core/impl/utils/executor/SequentialExecutor:mQueue	Ljava/util/Deque;
      //   92: invokeinterface 54 1 0
      //   97: checkcast 6	java/lang/Runnable
      //   100: astore 6
      //   102: aload 6
      //   104: ifnonnull +27 -> 131
      //   107: aload_0
      //   108: getfield 15	androidx/camera/core/impl/utils/executor/SequentialExecutor$QueueWorker:this$0	Landroidx/camera/core/impl/utils/executor/SequentialExecutor;
      //   111: getstatic 57	androidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState:IDLE	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   114: putfield 30	androidx/camera/core/impl/utils/executor/SequentialExecutor:mWorkerRunningState	Landroidx/camera/core/impl/utils/executor/SequentialExecutor$WorkerRunningState;
      //   117: aload 4
      //   119: monitorexit
      //   120: iload_2
      //   121: ifeq +9 -> 130
      //   124: invokestatic 41	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   127: invokevirtual 44	java/lang/Thread:interrupt	()V
      //   130: return
      //   131: aload 4
      //   133: monitorexit
      //   134: iload_2
      //   135: istore_3
      //   136: invokestatic 61	java/lang/Thread:interrupted	()Z
      //   139: istore 7
      //   141: iload_2
      //   142: iload 7
      //   144: ior
      //   145: istore_2
      //   146: iload_2
      //   147: istore_3
      //   148: aload 6
      //   150: invokeinterface 64 1 0
      //   155: iload 5
      //   157: istore_1
      //   158: goto -154 -> 4
      //   161: astore 8
      //   163: iload_2
      //   164: istore_3
      //   165: new 66	java/lang/StringBuilder
      //   168: astore 4
      //   170: iload_2
      //   171: istore_3
      //   172: aload 4
      //   174: invokespecial 67	java/lang/StringBuilder:<init>	()V
      //   177: iload_2
      //   178: istore_3
      //   179: aload 4
      //   181: ldc 69
      //   183: invokevirtual 73	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   186: pop
      //   187: iload_2
      //   188: istore_3
      //   189: aload 4
      //   191: aload 6
      //   193: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   196: pop
      //   197: iload_2
      //   198: istore_3
      //   199: ldc 78
      //   201: aload 4
      //   203: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   206: aload 8
      //   208: invokestatic 88	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   211: pop
      //   212: iload 5
      //   214: istore_1
      //   215: goto -211 -> 4
      //   218: astore 6
      //   220: aload 4
      //   222: monitorexit
      //   223: iload_2
      //   224: istore_3
      //   225: aload 6
      //   227: athrow
      //   228: astore 6
      //   230: iload_3
      //   231: ifeq +9 -> 240
      //   234: invokestatic 41	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   237: invokevirtual 44	java/lang/Thread:interrupt	()V
      //   240: aload 6
      //   242: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	243	0	this	QueueWorker
      //   1	214	1	i	int
      //   3	221	2	bool1	boolean
      //   5	226	3	j	int
      //   13	208	4	localObject1	Object
      //   21	192	5	k	int
      //   58	134	6	localObject2	Object
      //   218	8	6	localObject3	Object
      //   228	13	6	localObject4	Object
      //   139	6	7	bool2	boolean
      //   161	46	8	localRuntimeException	RuntimeException
      // Exception table:
      //   from	to	target	type
      //   148	155	161	java/lang/RuntimeException
      //   27	43	218	finally
      //   54	82	218	finally
      //   85	102	218	finally
      //   107	120	218	finally
      //   131	134	218	finally
      //   220	223	218	finally
      //   6	15	228	finally
      //   17	20	228	finally
      //   136	141	228	finally
      //   148	155	228	finally
      //   165	170	228	finally
      //   172	177	228	finally
      //   179	187	228	finally
      //   189	197	228	finally
      //   199	212	228	finally
      //   225	228	228	finally
    }
    
    public void run()
    {
      try
      {
        workOnQueue();
        return;
      }
      catch (Error localError)
      {
        synchronized (SequentialExecutor.this.mQueue)
        {
          SequentialExecutor.this.mWorkerRunningState = SequentialExecutor.WorkerRunningState.IDLE;
          throw localError;
        }
      }
    }
  }
  
  static enum WorkerRunningState
  {
    static
    {
      QUEUED = new WorkerRunningState("QUEUED", 2);
      WorkerRunningState localWorkerRunningState = new WorkerRunningState("RUNNING", 3);
      RUNNING = localWorkerRunningState;
      $VALUES = new WorkerRunningState[] { IDLE, QUEUING, QUEUED, localWorkerRunningState };
    }
    
    private WorkerRunningState() {}
  }
}
