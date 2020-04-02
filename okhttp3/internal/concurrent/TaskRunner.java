package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\004\n\002\020!\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\t\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\r\030\000 #2\0020\001:\003\"#$B\r\022\006\020\002\032\0020\003?\006\002\020\004J\f\020\023\032\b\022\004\022\0020\t0\024J\030\020\025\032\0020\0262\006\020\027\032\0020\0302\006\020\031\032\0020\rH\002J\b\020\032\032\004\030\0010\030J\020\020\033\032\0020\0262\006\020\027\032\0020\030H\002J\b\020\034\032\0020\026H\002J\025\020\035\032\0020\0262\006\020\036\032\0020\tH\000?\006\002\b\037J\006\020 \032\0020\tJ\020\020!\032\0020\0262\006\020\027\032\0020\030H\002R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006R\024\020\007\032\b\022\004\022\0020\t0\bX?\004?\006\002\n\000R\016\020\n\032\0020\013X?\016?\006\002\n\000R\016\020\f\032\0020\rX?\016?\006\002\n\000R\016\020\016\032\0020\017X?\016?\006\002\n\000R\024\020\020\032\b\022\004\022\0020\t0\bX?\004?\006\002\n\000R\016\020\021\032\0020\022X?\004?\006\002\n\000?\006%"}, d2={"Lokhttp3/internal/concurrent/TaskRunner;", "", "backend", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "(Lokhttp3/internal/concurrent/TaskRunner$Backend;)V", "getBackend", "()Lokhttp3/internal/concurrent/TaskRunner$Backend;", "busyQueues", "", "Lokhttp3/internal/concurrent/TaskQueue;", "coordinatorWaiting", "", "coordinatorWakeUpAt", "", "nextQueueName", "", "readyQueues", "runnable", "Ljava/lang/Runnable;", "activeQueues", "", "afterRun", "", "task", "Lokhttp3/internal/concurrent/Task;", "delayNanos", "awaitTaskToRun", "beforeRun", "cancelAll", "kickCoordinator", "taskQueue", "kickCoordinator$okhttp", "newQueue", "runTask", "Backend", "Companion", "RealBackend", "okhttp"}, k=1, mv={1, 1, 16})
public final class TaskRunner
{
  public static final Companion Companion = new Companion(null);
  public static final TaskRunner INSTANCE;
  private static final Logger logger;
  private final Backend backend;
  private final List<TaskQueue> busyQueues;
  private boolean coordinatorWaiting;
  private long coordinatorWakeUpAt;
  private int nextQueueName;
  private final List<TaskQueue> readyQueues;
  private final Runnable runnable;
  
  static
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(Util.okHttpName);
    ((StringBuilder)localObject).append(" TaskRunner");
    INSTANCE = new TaskRunner((Backend)new RealBackend(Util.threadFactory(((StringBuilder)localObject).toString(), true)));
    localObject = Logger.getLogger(TaskRunner.class.getName());
    Intrinsics.checkExpressionValueIsNotNull(localObject, "Logger.getLogger(TaskRunner::class.java.name)");
    logger = (Logger)localObject;
  }
  
  public TaskRunner(Backend paramBackend)
  {
    this.backend = paramBackend;
    this.nextQueueName = 10000;
    this.busyQueues = ((List)new ArrayList());
    this.readyQueues = ((List)new ArrayList());
    this.runnable = ((Runnable)new Runnable()
    {
      public void run()
      {
        label0:
        synchronized (this.this$0)
        {
          Task localTask = this.this$0.awaitTaskToRun();
          long l1;
          boolean bool;
          if (localTask != null)
          {
            ??? = localTask.getQueue$okhttp();
            if (??? == null) {
              Intrinsics.throwNpe();
            }
            l1 = -1L;
            bool = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
            if (bool)
            {
              l1 = ((TaskQueue)???).getTaskRunner$okhttp().getBackend().nanoTime();
              TaskLoggerKt.access$log(localTask, (TaskQueue)???, "starting");
            }
            try
            {
              TaskRunner.access$runTask(this.this$0, localTask);
            }
            finally
            {
              try
              {
                Object localObject3 = Unit.INSTANCE;
                if (!bool) {
                  break label0;
                }
                l2 = ((TaskQueue)???).getTaskRunner$okhttp().getBackend().nanoTime();
                localObject3 = new StringBuilder();
                ((StringBuilder)localObject3).append("finished run in ");
                ((StringBuilder)localObject3).append(TaskLoggerKt.formatDuration(l2 - l1));
                TaskLoggerKt.access$log(localTask, (TaskQueue)???, ((StringBuilder)localObject3).toString());
                break label0;
              }
              finally
              {
                if (!bool) {
                  break label233;
                }
                long l2 = ((TaskQueue)???).getTaskRunner$okhttp().getBackend().nanoTime();
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("failed a run in ");
                localStringBuilder.append(TaskLoggerKt.formatDuration(l2 - l1));
                TaskLoggerKt.access$log(localTask, (TaskQueue)???, localStringBuilder.toString());
              }
              localObject4 = finally;
              this.this$0.getBackend().execute((Runnable)this);
              throw localObject4;
            }
          }
          label233:
          return;
        }
      }
    });
  }
  
  private final void afterRun(Task paramTask, long paramLong)
  {
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      paramTask = new StringBuilder();
      paramTask.append("Thread ");
      localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      paramTask.append(((Thread)localObject).getName());
      paramTask.append(" MUST hold lock on ");
      paramTask.append(this);
      throw ((Throwable)new AssertionError(paramTask.toString()));
    }
    Object localObject = paramTask.getQueue$okhttp();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    int i;
    if (((TaskQueue)localObject).getActiveTask$okhttp() == paramTask) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      boolean bool = ((TaskQueue)localObject).getCancelActiveTask$okhttp();
      ((TaskQueue)localObject).setCancelActiveTask$okhttp(false);
      ((TaskQueue)localObject).setActiveTask$okhttp((Task)null);
      this.busyQueues.remove(localObject);
      if ((paramLong != -1L) && (!bool) && (!((TaskQueue)localObject).getShutdown$okhttp())) {
        ((TaskQueue)localObject).scheduleAndDecide$okhttp(paramTask, paramLong, true);
      }
      if ((((Collection)((TaskQueue)localObject).getFutureTasks$okhttp()).isEmpty() ^ true)) {
        this.readyQueues.add(localObject);
      }
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  private final void beforeRun(Task paramTask)
  {
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      paramTask = new StringBuilder();
      paramTask.append("Thread ");
      localObject = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Thread.currentThread()");
      paramTask.append(((Thread)localObject).getName());
      paramTask.append(" MUST hold lock on ");
      paramTask.append(this);
      throw ((Throwable)new AssertionError(paramTask.toString()));
    }
    paramTask.setNextExecuteNanoTime$okhttp(-1L);
    Object localObject = paramTask.getQueue$okhttp();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    ((TaskQueue)localObject).getFutureTasks$okhttp().remove(paramTask);
    this.readyQueues.remove(localObject);
    ((TaskQueue)localObject).setActiveTask$okhttp(paramTask);
    this.busyQueues.add(localObject);
  }
  
  private final void cancelAll()
  {
    for (int i = this.busyQueues.size() - 1; i >= 0; i--) {
      ((TaskQueue)this.readyQueues.get(i)).cancelAllAndDecide$okhttp();
    }
    for (i = this.readyQueues.size() - 1; i >= 0; i--)
    {
      TaskQueue localTaskQueue = (TaskQueue)this.readyQueues.get(i);
      localTaskQueue.cancelAllAndDecide$okhttp();
      if (localTaskQueue.getFutureTasks$okhttp().isEmpty()) {
        this.readyQueues.remove(i);
      }
    }
  }
  
  /* Error */
  private final void runTask(Task paramTask)
  {
    // Byte code:
    //   0: getstatic 161	okhttp3/internal/Util:assertionsEnabled	Z
    //   3: ifeq +76 -> 79
    //   6: aload_0
    //   7: invokestatic 167	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifne +6 -> 16
    //   13: goto +66 -> 79
    //   16: new 74	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 76	java/lang/StringBuilder:<init>	()V
    //   23: astore_2
    //   24: aload_2
    //   25: ldc -87
    //   27: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   34: astore_1
    //   35: aload_1
    //   36: ldc -81
    //   38: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   41: aload_2
    //   42: aload_1
    //   43: invokevirtual 176	java/lang/Thread:getName	()Ljava/lang/String;
    //   46: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload_2
    //   51: ldc_w 266
    //   54: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: pop
    //   58: aload_2
    //   59: aload_0
    //   60: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: new 183	java/lang/AssertionError
    //   67: dup
    //   68: aload_2
    //   69: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   72: invokespecial 186	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   75: checkcast 188	java/lang/Throwable
    //   78: athrow
    //   79: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   82: astore_3
    //   83: aload_3
    //   84: ldc_w 267
    //   87: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   90: aload_3
    //   91: invokevirtual 176	java/lang/Thread:getName	()Ljava/lang/String;
    //   94: astore_2
    //   95: aload_3
    //   96: aload_1
    //   97: invokevirtual 268	okhttp3/internal/concurrent/Task:getName	()Ljava/lang/String;
    //   100: invokevirtual 271	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   103: aload_1
    //   104: invokevirtual 275	okhttp3/internal/concurrent/Task:runOnce	()J
    //   107: lstore 4
    //   109: aload_0
    //   110: monitorenter
    //   111: aload_0
    //   112: aload_1
    //   113: lload 4
    //   115: invokespecial 277	okhttp3/internal/concurrent/TaskRunner:afterRun	(Lokhttp3/internal/concurrent/Task;J)V
    //   118: getstatic 282	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   121: astore_1
    //   122: aload_0
    //   123: monitorexit
    //   124: aload_3
    //   125: aload_2
    //   126: invokevirtual 271	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   129: return
    //   130: astore_1
    //   131: aload_0
    //   132: monitorexit
    //   133: aload_1
    //   134: athrow
    //   135: astore 6
    //   137: aload_0
    //   138: monitorenter
    //   139: aload_0
    //   140: aload_1
    //   141: ldc2_w 218
    //   144: invokespecial 277	okhttp3/internal/concurrent/TaskRunner:afterRun	(Lokhttp3/internal/concurrent/Task;J)V
    //   147: getstatic 282	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   150: astore_1
    //   151: aload_0
    //   152: monitorexit
    //   153: aload_3
    //   154: aload_2
    //   155: invokevirtual 271	java/lang/Thread:setName	(Ljava/lang/String;)V
    //   158: aload 6
    //   160: athrow
    //   161: astore_1
    //   162: aload_0
    //   163: monitorexit
    //   164: aload_1
    //   165: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	this	TaskRunner
    //   0	166	1	paramTask	Task
    //   23	132	2	localObject1	Object
    //   82	72	3	localThread	Thread
    //   107	7	4	l	long
    //   135	24	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   111	122	130	finally
    //   103	109	135	finally
    //   139	151	161	finally
  }
  
  public final List<TaskQueue> activeQueues()
  {
    try
    {
      List localList = CollectionsKt.plus((Collection)this.busyQueues, (Iterable)this.readyQueues);
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final Task awaitTaskToRun()
  {
    // Byte code:
    //   0: getstatic 161	okhttp3/internal/Util:assertionsEnabled	Z
    //   3: ifeq +75 -> 78
    //   6: aload_0
    //   7: invokestatic 167	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifeq +6 -> 16
    //   13: goto +65 -> 78
    //   16: new 74	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 76	java/lang/StringBuilder:<init>	()V
    //   23: astore_1
    //   24: aload_1
    //   25: ldc -87
    //   27: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: invokestatic 173	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   34: astore_2
    //   35: aload_2
    //   36: ldc -81
    //   38: invokestatic 122	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   41: aload_1
    //   42: aload_2
    //   43: invokevirtual 176	java/lang/Thread:getName	()Ljava/lang/String;
    //   46: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload_1
    //   51: ldc -78
    //   53: invokevirtual 86	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload_1
    //   58: aload_0
    //   59: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: new 183	java/lang/AssertionError
    //   66: dup
    //   67: aload_1
    //   68: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   71: invokespecial 186	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   74: checkcast 188	java/lang/Throwable
    //   77: athrow
    //   78: aload_0
    //   79: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   82: invokeinterface 262 1 0
    //   87: ifeq +5 -> 92
    //   90: aconst_null
    //   91: areturn
    //   92: aload_0
    //   93: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   96: invokeinterface 297 1 0
    //   101: lstore_3
    //   102: ldc2_w 298
    //   105: lstore 5
    //   107: aconst_null
    //   108: checkcast 190	okhttp3/internal/concurrent/Task
    //   111: astore_2
    //   112: aload_0
    //   113: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   116: invokeinterface 303 1 0
    //   121: astore 7
    //   123: aload 7
    //   125: invokeinterface 308 1 0
    //   130: ifeq +72 -> 202
    //   133: aload 7
    //   135: invokeinterface 312 1 0
    //   140: checkcast 199	okhttp3/internal/concurrent/TaskQueue
    //   143: invokevirtual 230	okhttp3/internal/concurrent/TaskQueue:getFutureTasks$okhttp	()Ljava/util/List;
    //   146: iconst_0
    //   147: invokeinterface 258 2 0
    //   152: checkcast 190	okhttp3/internal/concurrent/Task
    //   155: astore_1
    //   156: lconst_0
    //   157: aload_1
    //   158: invokevirtual 315	okhttp3/internal/concurrent/Task:getNextExecuteNanoTime$okhttp	()J
    //   161: lload_3
    //   162: lsub
    //   163: invokestatic 321	java/lang/Math:max	(JJ)J
    //   166: lstore 8
    //   168: lload 8
    //   170: lconst_0
    //   171: lcmp
    //   172: ifle +15 -> 187
    //   175: lload 8
    //   177: lload 5
    //   179: invokestatic 324	java/lang/Math:min	(JJ)J
    //   182: lstore 5
    //   184: goto -61 -> 123
    //   187: aload_2
    //   188: ifnull +9 -> 197
    //   191: iconst_1
    //   192: istore 10
    //   194: goto +11 -> 205
    //   197: aload_1
    //   198: astore_2
    //   199: goto -76 -> 123
    //   202: iconst_0
    //   203: istore 10
    //   205: aload_2
    //   206: ifnull +52 -> 258
    //   209: aload_0
    //   210: aload_2
    //   211: invokespecial 326	okhttp3/internal/concurrent/TaskRunner:beforeRun	(Lokhttp3/internal/concurrent/Task;)V
    //   214: iload 10
    //   216: ifne +27 -> 243
    //   219: aload_0
    //   220: getfield 328	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   223: ifne +33 -> 256
    //   226: aload_0
    //   227: getfield 143	okhttp3/internal/concurrent/TaskRunner:readyQueues	Ljava/util/List;
    //   230: checkcast 232	java/util/Collection
    //   233: invokeinterface 235 1 0
    //   238: iconst_1
    //   239: ixor
    //   240: ifeq +16 -> 256
    //   243: aload_0
    //   244: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   247: aload_0
    //   248: getfield 150	okhttp3/internal/concurrent/TaskRunner:runnable	Ljava/lang/Runnable;
    //   251: invokeinterface 332 2 0
    //   256: aload_2
    //   257: areturn
    //   258: aload_0
    //   259: getfield 328	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   262: ifeq +27 -> 289
    //   265: lload 5
    //   267: aload_0
    //   268: getfield 334	okhttp3/internal/concurrent/TaskRunner:coordinatorWakeUpAt	J
    //   271: lload_3
    //   272: lsub
    //   273: lcmp
    //   274: ifge +13 -> 287
    //   277: aload_0
    //   278: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   281: aload_0
    //   282: invokeinterface 337 2 0
    //   287: aconst_null
    //   288: areturn
    //   289: aload_0
    //   290: iconst_1
    //   291: putfield 328	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   294: aload_0
    //   295: lload_3
    //   296: lload 5
    //   298: ladd
    //   299: putfield 334	okhttp3/internal/concurrent/TaskRunner:coordinatorWakeUpAt	J
    //   302: aload_0
    //   303: getfield 132	okhttp3/internal/concurrent/TaskRunner:backend	Lokhttp3/internal/concurrent/TaskRunner$Backend;
    //   306: aload_0
    //   307: lload 5
    //   309: invokeinterface 341 4 0
    //   314: aload_0
    //   315: iconst_0
    //   316: putfield 328	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   319: goto -241 -> 78
    //   322: astore_2
    //   323: goto +11 -> 334
    //   326: astore_2
    //   327: aload_0
    //   328: invokespecial 343	okhttp3/internal/concurrent/TaskRunner:cancelAll	()V
    //   331: goto -17 -> 314
    //   334: aload_0
    //   335: iconst_0
    //   336: putfield 328	okhttp3/internal/concurrent/TaskRunner:coordinatorWaiting	Z
    //   339: aload_2
    //   340: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	341	0	this	TaskRunner
    //   23	175	1	localObject1	Object
    //   34	223	2	localObject2	Object
    //   322	1	2	localObject3	Object
    //   326	14	2	localInterruptedException	InterruptedException
    //   101	195	3	l1	long
    //   105	203	5	l2	long
    //   121	13	7	localIterator	java.util.Iterator
    //   166	10	8	l3	long
    //   192	23	10	i	int
    // Exception table:
    //   from	to	target	type
    //   302	314	322	finally
    //   327	331	322	finally
    //   302	314	326	java/lang/InterruptedException
  }
  
  public final Backend getBackend()
  {
    return this.backend;
  }
  
  public final void kickCoordinator$okhttp(TaskQueue paramTaskQueue)
  {
    Intrinsics.checkParameterIsNotNull(paramTaskQueue, "taskQueue");
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(this)))
    {
      paramTaskQueue = new StringBuilder();
      paramTaskQueue.append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
      paramTaskQueue.append(localThread.getName());
      paramTaskQueue.append(" MUST hold lock on ");
      paramTaskQueue.append(this);
      throw ((Throwable)new AssertionError(paramTaskQueue.toString()));
    }
    if (paramTaskQueue.getActiveTask$okhttp() == null) {
      if ((((Collection)paramTaskQueue.getFutureTasks$okhttp()).isEmpty() ^ true)) {
        Util.addIfAbsent(this.readyQueues, paramTaskQueue);
      } else {
        this.readyQueues.remove(paramTaskQueue);
      }
    }
    if (this.coordinatorWaiting) {
      this.backend.coordinatorNotify(this);
    } else {
      this.backend.execute(this.runnable);
    }
  }
  
  public final TaskQueue newQueue()
  {
    try
    {
      int i = this.nextQueueName;
      this.nextQueueName = (i + 1);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append('Q');
      localStringBuilder.append(i);
      return new TaskQueue(this, localStringBuilder.toString());
    }
    finally {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\020\020\006\032\0020\0032\006\020\004\032\0020\005H&J\030\020\007\032\0020\0032\006\020\004\032\0020\0052\006\020\b\032\0020\tH&J\020\020\n\032\0020\0032\006\020\013\032\0020\fH&J\b\020\r\032\0020\tH&?\006\016"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Backend;", "", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "okhttp"}, k=1, mv={1, 1, 16})
  public static abstract interface Backend
  {
    public abstract void beforeTask(TaskRunner paramTaskRunner);
    
    public abstract void coordinatorNotify(TaskRunner paramTaskRunner);
    
    public abstract void coordinatorWait(TaskRunner paramTaskRunner, long paramLong);
    
    public abstract void execute(Runnable paramRunnable);
    
    public abstract long nanoTime();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\020\020\003\032\0020\0048\006X?\004?\006\002\n\000R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\007\020\b?\006\t"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Companion;", "", "()V", "INSTANCE", "Lokhttp3/internal/concurrent/TaskRunner;", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final Logger getLogger()
    {
      return TaskRunner.access$getLogger$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\003\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\013\032\0020\b2\006\020\t\032\0020\nH\026J\030\020\f\032\0020\b2\006\020\t\032\0020\n2\006\020\r\032\0020\016H\026J\020\020\017\032\0020\b2\006\020\020\032\0020\021H\026J\b\020\022\032\0020\016H\026J\006\020\023\032\0020\bR\016\020\005\032\0020\006X?\004?\006\002\n\000?\006\024"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$RealBackend;", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "(Ljava/util/concurrent/ThreadFactory;)V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "shutdown", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class RealBackend
    implements TaskRunner.Backend
  {
    private final ThreadPoolExecutor executor;
    
    public RealBackend(ThreadFactory paramThreadFactory)
    {
      this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, (BlockingQueue)new SynchronousQueue(), paramThreadFactory);
    }
    
    public void beforeTask(TaskRunner paramTaskRunner)
    {
      Intrinsics.checkParameterIsNotNull(paramTaskRunner, "taskRunner");
    }
    
    public void coordinatorNotify(TaskRunner paramTaskRunner)
    {
      Intrinsics.checkParameterIsNotNull(paramTaskRunner, "taskRunner");
      ((Object)paramTaskRunner).notify();
    }
    
    public void coordinatorWait(TaskRunner paramTaskRunner, long paramLong)
      throws InterruptedException
    {
      Intrinsics.checkParameterIsNotNull(paramTaskRunner, "taskRunner");
      long l = paramLong / 1000000L;
      if ((l > 0L) || (paramLong > 0L)) {
        ((Object)paramTaskRunner).wait(l, (int)(paramLong - 1000000L * l));
      }
    }
    
    public void execute(Runnable paramRunnable)
    {
      Intrinsics.checkParameterIsNotNull(paramRunnable, "runnable");
      this.executor.execute(paramRunnable);
    }
    
    public long nanoTime()
    {
      return System.nanoTime();
    }
    
    public final void shutdown()
    {
      this.executor.shutdown();
    }
  }
}
