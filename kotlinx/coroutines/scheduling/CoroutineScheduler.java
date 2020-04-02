package kotlinx.coroutines.scheduling;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.Random.Default;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.TimeSourceKt;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000b\n\002\030\002\n\002\020\b\n\002\b\002\n\002\020\t\n\000\n\002\020\016\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\002\b\006\n\002\020\002\n\002\b\004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b.\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\b\000\030\000 Z2\0020^2\0020_:\003Z[\\B+\022\006\020\002\032\0020\001\022\006\020\003\032\0020\001\022\b\b\002\020\005\032\0020\004\022\b\b\002\020\007\032\0020\006?\006\004\b\b\020\tJ\027\020\r\032\0020\f2\006\020\013\032\0020\nH\002?\006\004\b\r\020\016J\030\020\020\032\0020\0012\006\020\017\032\0020\004H?\b?\006\004\b\020\020\021J\030\020\022\032\0020\0012\006\020\017\032\0020\004H?\b?\006\004\b\022\020\021J\017\020\024\032\0020\023H\026?\006\004\b\024\020\025J\017\020\026\032\0020\001H\002?\006\004\b\026\020\027J#\020\037\032\0020\n2\n\020\032\032\0060\030j\002`\0312\006\020\034\032\0020\033H\000?\006\004\b\035\020\036J\030\020 \032\0020\0012\006\020\017\032\0020\004H?\b?\006\004\b \020\021J\025\020\"\032\b\030\0010!R\0020\000H\002?\006\004\b\"\020#J\020\020$\032\0020\023H?\b?\006\004\b$\020\025J\020\020%\032\0020\001H?\b?\006\004\b%\020\027J-\020'\032\0020\0232\n\020\032\032\0060\030j\002`\0312\b\b\002\020\034\032\0020\0332\b\b\002\020&\032\0020\f?\006\004\b'\020(J\033\020*\032\0020\0232\n\020)\032\0060\030j\002`\031H\026?\006\004\b*\020+J\020\020,\032\0020\004H?\b?\006\004\b,\020-J\020\020.\032\0020\001H?\b?\006\004\b.\020\027J\033\0200\032\0020\0012\n\020/\032\0060!R\0020\000H\002?\006\004\b0\0201J\025\0202\032\b\030\0010!R\0020\000H\002?\006\004\b2\020#J\033\0205\032\0020\f2\n\020/\032\0060!R\0020\000H\000?\006\004\b3\0204J+\020:\032\0020\0232\n\020/\032\0060!R\0020\0002\006\0206\032\0020\0012\006\0207\032\0020\001H\000?\006\004\b8\0209J\020\020;\032\0020\004H?\b?\006\004\b;\020-J\025\020<\032\0020\0232\006\020\013\032\0020\n?\006\004\b<\020=J\025\020?\032\0020\0232\006\020>\032\0020\004?\006\004\b?\020@J\017\020A\032\0020\023H\002?\006\004\bA\020\025J\017\020C\032\0020\023H\000?\006\004\bB\020\025J!\020D\032\004\030\0010\n2\006\020\013\032\0020\n2\006\020&\032\0020\fH\002?\006\004\bD\020EJ\017\020F\032\0020\006H\026?\006\004\bF\020GJ\020\020H\032\0020\fH?\b?\006\004\bH\020IJ\031\020J\032\0020\f2\b\b\002\020\017\032\0020\004H\002?\006\004\bJ\020KJ\017\020L\032\0020\fH\002?\006\004\bL\020IR\027\020\020\032\0020\0018?\002@\002X?\004?\006\006\032\004\bM\020\027R\026\020\002\032\0020\0018\006@\007X?\004?\006\006\n\004\b\002\020NR\027\020 \032\0020\0018?\002@\002X?\004?\006\006\032\004\bO\020\027R\026\020Q\032\0020P8\006@\007X?\004?\006\006\n\004\bQ\020RR\026\020S\032\0020P8\006@\007X?\004?\006\006\n\004\bS\020RR\026\020\005\032\0020\0048\006@\007X?\004?\006\006\n\004\b\005\020TR\023\020U\032\0020\f8F@\006?\006\006\032\004\bU\020IR\026\020\003\032\0020\0018\006@\007X?\004?\006\006\n\004\b\003\020NR\026\020\007\032\0020\0068\006@\007X?\004?\006\006\n\004\b\007\020VR\"\020X\032\016\022\n\022\b\030\0010!R\0020\0000W8\006@\007X?\004?\006\006\n\004\bX\020Y?\006]"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "", "corePoolSize", "maxPoolSize", "", "idleWorkerKeepAliveNs", "", "schedulerName", "<init>", "(IIJLjava/lang/String;)V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "addToGlobalQueue", "(Lkotlinx/coroutines/scheduling/Task;)Z", "state", "availableCpuPermits", "(J)I", "blockingTasks", "", "close", "()V", "createNewWorker", "()I", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "Lkotlinx/coroutines/scheduling/TaskContext;", "taskContext", "createTask$kotlinx_coroutines_core", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;)Lkotlinx/coroutines/scheduling/Task;", "createTask", "createdWorkers", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "currentWorker", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "decrementBlockingTasks", "decrementCreatedWorkers", "fair", "dispatch", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;Z)V", "command", "execute", "(Ljava/lang/Runnable;)V", "incrementBlockingTasks", "()J", "incrementCreatedWorkers", "worker", "parkedWorkersStackNextIndex", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)I", "parkedWorkersStackPop", "parkedWorkersStackPush$kotlinx_coroutines_core", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)Z", "parkedWorkersStackPush", "oldIndex", "newIndex", "parkedWorkersStackTopUpdate$kotlinx_coroutines_core", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V", "parkedWorkersStackTopUpdate", "releaseCpuPermit", "runSafely", "(Lkotlinx/coroutines/scheduling/Task;)V", "timeout", "shutdown", "(J)V", "signalBlockingWork", "signalCpuWork$kotlinx_coroutines_core", "signalCpuWork", "submitToLocalQueue", "(Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "toString", "()Ljava/lang/String;", "tryAcquireCpuPermit", "()Z", "tryCreateWorker", "(J)Z", "tryUnpark", "getAvailableCpuPermits", "I", "getCreatedWorkers", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalBlockingQueue", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalCpuQueue", "J", "isTerminated", "Ljava/lang/String;", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "workers", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "Companion", "Worker", "WorkerState", "kotlinx-coroutines-core", "Ljava/util/concurrent/Executor;", "Ljava/io/Closeable;"}, k=1, mv={1, 1, 16})
public final class CoroutineScheduler
  implements Executor, Closeable
{
  private static final long BLOCKING_MASK = 4398044413952L;
  private static final int BLOCKING_SHIFT = 21;
  private static final int CLAIMED = 0;
  private static final long CPU_PERMITS_MASK = 9223367638808264704L;
  private static final int CPU_PERMITS_SHIFT = 42;
  private static final long CREATED_MASK = 2097151L;
  public static final Companion Companion = new Companion(null);
  public static final int MAX_SUPPORTED_POOL_SIZE = 2097150;
  public static final int MIN_SUPPORTED_POOL_SIZE = 1;
  public static final Symbol NOT_IN_STACK = new Symbol("NOT_IN_STACK");
  private static final int PARKED = -1;
  private static final long PARKED_INDEX_MASK = 2097151L;
  private static final long PARKED_VERSION_INC = 2097152L;
  private static final long PARKED_VERSION_MASK = -2097152L;
  private static final int TERMINATED = 1;
  private static final AtomicIntegerFieldUpdater _isTerminated$FU = AtomicIntegerFieldUpdater.newUpdater(CoroutineScheduler.class, "_isTerminated");
  static final AtomicLongFieldUpdater controlState$FU;
  private static final AtomicLongFieldUpdater parkedWorkersStack$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "parkedWorkersStack");
  private volatile int _isTerminated;
  volatile long controlState;
  public final int corePoolSize;
  public final GlobalQueue globalBlockingQueue;
  public final GlobalQueue globalCpuQueue;
  public final long idleWorkerKeepAliveNs;
  public final int maxPoolSize;
  private volatile long parkedWorkersStack;
  public final String schedulerName;
  public final AtomicReferenceArray<Worker> workers;
  
  static
  {
    controlState$FU = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "controlState");
  }
  
  public CoroutineScheduler(int paramInt1, int paramInt2, long paramLong, String paramString)
  {
    this.corePoolSize = paramInt1;
    this.maxPoolSize = paramInt2;
    this.idleWorkerKeepAliveNs = paramLong;
    this.schedulerName = paramString;
    if (paramInt1 >= 1) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 != 0)
    {
      if (this.maxPoolSize >= this.corePoolSize) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
      if (paramInt1 != 0)
      {
        if (this.maxPoolSize <= 2097150) {
          paramInt1 = 1;
        } else {
          paramInt1 = 0;
        }
        if (paramInt1 != 0)
        {
          if (this.idleWorkerKeepAliveNs > 0L) {
            paramInt1 = 1;
          } else {
            paramInt1 = 0;
          }
          if (paramInt1 != 0)
          {
            this.globalCpuQueue = new GlobalQueue();
            this.globalBlockingQueue = new GlobalQueue();
            this.parkedWorkersStack = 0L;
            this.workers = new AtomicReferenceArray(this.maxPoolSize + 1);
            this.controlState = (this.corePoolSize << 42);
            this._isTerminated = 0;
            return;
          }
          paramString = new StringBuilder();
          paramString.append("Idle worker keep alive time ");
          paramString.append(this.idleWorkerKeepAliveNs);
          paramString.append(" must be positive");
          throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
        }
        paramString = new StringBuilder();
        paramString.append("Max pool size ");
        paramString.append(this.maxPoolSize);
        paramString.append(" should not exceed maximal supported number of threads 2097150");
        throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
      }
      paramString = new StringBuilder();
      paramString.append("Max pool size ");
      paramString.append(this.maxPoolSize);
      paramString.append(" should be greater than or equals to core pool size ");
      paramString.append(this.corePoolSize);
      throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
    }
    paramString = new StringBuilder();
    paramString.append("Core pool size ");
    paramString.append(this.corePoolSize);
    paramString.append(" should be at least 1");
    throw ((Throwable)new IllegalArgumentException(paramString.toString().toString()));
  }
  
  private final boolean addToGlobalQueue(Task paramTask)
  {
    int i;
    if (paramTask.taskContext.getTaskMode() == TaskMode.PROBABLY_BLOCKING) {
      i = 1;
    } else {
      i = 0;
    }
    boolean bool;
    if (i != 0) {
      bool = this.globalBlockingQueue.addLast(paramTask);
    } else {
      bool = this.globalCpuQueue.addLast(paramTask);
    }
    return bool;
  }
  
  private final int blockingTasks(long paramLong)
  {
    return (int)((paramLong & 0x3FFFFE00000) >> 21);
  }
  
  private final int createNewWorker()
  {
    synchronized (this.workers)
    {
      boolean bool = isTerminated();
      if (bool) {
        return -1;
      }
      long l = this.controlState;
      int i = (int)(l & 0x1FFFFF);
      int j = (int)((l & 0x3FFFFE00000) >> 21);
      int k = 0;
      j = RangesKt.coerceAtLeast(i - j, 0);
      int m = this.corePoolSize;
      if (j >= m) {
        return 0;
      }
      m = this.maxPoolSize;
      if (i >= m) {
        return 0;
      }
      m = (int)(this.controlState & 0x1FFFFF) + 1;
      if ((m > 0) && (this.workers.get(m) == null)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localObject1 = new kotlinx/coroutines/scheduling/CoroutineScheduler$Worker;
        ((Worker)localObject1).<init>(this, m);
        this.workers.set(m, localObject1);
        i = k;
        if (m == (int)(0x1FFFFF & controlState$FU.incrementAndGet(this))) {
          i = 1;
        }
        if (i != 0)
        {
          ((Worker)localObject1).start();
          return j + 1;
        }
        localObject1 = new java/lang/IllegalArgumentException;
        ((IllegalArgumentException)localObject1).<init>("Failed requirement.".toString());
        throw ((Throwable)localObject1);
      }
      Object localObject1 = new java/lang/IllegalArgumentException;
      ((IllegalArgumentException)localObject1).<init>("Failed requirement.".toString());
      throw ((Throwable)localObject1);
    }
  }
  
  private final int createdWorkers(long paramLong)
  {
    return (int)(paramLong & 0x1FFFFF);
  }
  
  private final Worker currentWorker()
  {
    Object localObject1 = Thread.currentThread();
    boolean bool = localObject1 instanceof Worker;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    Worker localWorker = (Worker)localObject1;
    localObject1 = localObject2;
    if (localWorker != null)
    {
      localObject1 = localObject2;
      if (Intrinsics.areEqual(localWorker.this$0, (CoroutineScheduler)this)) {
        localObject1 = localWorker;
      }
    }
    return localObject1;
  }
  
  private final void decrementBlockingTasks()
  {
    controlState$FU.addAndGet(this, -2097152L);
  }
  
  private final int decrementCreatedWorkers()
  {
    return (int)(controlState$FU.getAndDecrement(this) & 0x1FFFFF);
  }
  
  private final int getAvailableCpuPermits()
  {
    return (int)((this.controlState & 0x7FFFFC0000000000) >> 42);
  }
  
  private final int getCreatedWorkers()
  {
    return (int)(this.controlState & 0x1FFFFF);
  }
  
  private final long incrementBlockingTasks()
  {
    return controlState$FU.addAndGet(this, 2097152L);
  }
  
  private final int incrementCreatedWorkers()
  {
    return (int)(controlState$FU.incrementAndGet(this) & 0x1FFFFF);
  }
  
  private final int parkedWorkersStackNextIndex(Worker paramWorker)
  {
    for (paramWorker = paramWorker.getNextParkedWorker();; paramWorker = paramWorker.getNextParkedWorker())
    {
      if (paramWorker == NOT_IN_STACK) {
        return -1;
      }
      if (paramWorker == null) {
        return 0;
      }
      paramWorker = (Worker)paramWorker;
      int i = paramWorker.getIndexInArray();
      if (i != 0) {
        return i;
      }
    }
  }
  
  private final Worker parkedWorkersStackPop()
  {
    long l;
    int i;
    Worker localWorker;
    do
    {
      l = this.parkedWorkersStack;
      i = (int)(0x1FFFFF & l);
      localWorker = (Worker)this.workers.get(i);
      if (localWorker == null) {
        break;
      }
      i = parkedWorkersStackNextIndex(localWorker);
    } while ((i < 0) || (!parkedWorkersStack$FU.compareAndSet(this, l, i | 2097152L + l & 0xFFFFFFFFFFE00000)));
    localWorker.setNextParkedWorker(NOT_IN_STACK);
    return localWorker;
    return null;
  }
  
  private final long releaseCpuPermit()
  {
    return controlState$FU.addAndGet(this, 4398046511104L);
  }
  
  private final void signalBlockingWork()
  {
    long l = controlState$FU.addAndGet(this, 2097152L);
    if (tryUnpark()) {
      return;
    }
    if (tryCreateWorker(l)) {
      return;
    }
    tryUnpark();
  }
  
  private final Task submitToLocalQueue(Task paramTask, boolean paramBoolean)
  {
    Worker localWorker = currentWorker();
    Task localTask = paramTask;
    if (localWorker != null)
    {
      if (localWorker.state == WorkerState.TERMINATED) {
        return paramTask;
      }
      if ((paramTask.taskContext.getTaskMode() == TaskMode.NON_BLOCKING) && (localWorker.state == WorkerState.BLOCKING)) {
        return paramTask;
      }
      localWorker.mayHaveLocalTasks = true;
      localTask = localWorker.localQueue.add(paramTask, paramBoolean);
    }
    return localTask;
  }
  
  private final boolean tryAcquireCpuPermit()
  {
    long l;
    do
    {
      l = this.controlState;
      if ((int)((0x7FFFFC0000000000 & l) >> 42) == 0) {
        return false;
      }
    } while (!controlState$FU.compareAndSet(this, l, l - 4398046511104L));
    return true;
  }
  
  private final boolean tryCreateWorker(long paramLong)
  {
    if (RangesKt.coerceAtLeast((int)(0x1FFFFF & paramLong) - (int)((paramLong & 0x3FFFFE00000) >> 21), 0) < this.corePoolSize)
    {
      int i = createNewWorker();
      if ((i == 1) && (this.corePoolSize > 1)) {
        createNewWorker();
      }
      if (i > 0) {
        return true;
      }
    }
    return false;
  }
  
  private final boolean tryUnpark()
  {
    Worker localWorker;
    do
    {
      localWorker = parkedWorkersStackPop();
      if (localWorker == null) {
        break;
      }
    } while (!Worker.workerCtl$FU.compareAndSet(localWorker, -1, 0));
    LockSupport.unpark((Thread)localWorker);
    return true;
    return false;
  }
  
  public final int availableCpuPermits(long paramLong)
  {
    return (int)((paramLong & 0x7FFFFC0000000000) >> 42);
  }
  
  public void close()
  {
    shutdown(10000L);
  }
  
  public final Task createTask$kotlinx_coroutines_core(Runnable paramRunnable, TaskContext paramTaskContext)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    Intrinsics.checkParameterIsNotNull(paramTaskContext, "taskContext");
    long l = TasksKt.schedulerTimeSource.nanoTime();
    if ((paramRunnable instanceof Task))
    {
      paramRunnable = (Task)paramRunnable;
      paramRunnable.submissionTime = l;
      paramRunnable.taskContext = paramTaskContext;
      return paramRunnable;
    }
    return (Task)new TaskImpl(paramRunnable, l, paramTaskContext);
  }
  
  public final void dispatch(Runnable paramRunnable, TaskContext paramTaskContext, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    Intrinsics.checkParameterIsNotNull(paramTaskContext, "taskContext");
    kotlinx.coroutines.TimeSource localTimeSource = TimeSourceKt.getTimeSource();
    if (localTimeSource != null) {
      localTimeSource.trackTask();
    }
    paramRunnable = createTask$kotlinx_coroutines_core(paramRunnable, paramTaskContext);
    paramTaskContext = submitToLocalQueue(paramRunnable, paramBoolean);
    if ((paramTaskContext != null) && (!addToGlobalQueue(paramTaskContext)))
    {
      paramRunnable = new StringBuilder();
      paramRunnable.append(this.schedulerName);
      paramRunnable.append(" was terminated");
      throw ((Throwable)new RejectedExecutionException(paramRunnable.toString()));
    }
    if (paramRunnable.taskContext.getTaskMode() == TaskMode.NON_BLOCKING) {
      signalCpuWork$kotlinx_coroutines_core();
    } else {
      signalBlockingWork();
    }
  }
  
  public void execute(Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "command");
    dispatch$default(this, paramRunnable, null, false, 6, null);
  }
  
  public final boolean isTerminated()
  {
    return this._isTerminated;
  }
  
  public final boolean parkedWorkersStackPush$kotlinx_coroutines_core(Worker paramWorker)
  {
    Intrinsics.checkParameterIsNotNull(paramWorker, "worker");
    if (paramWorker.getNextParkedWorker() != NOT_IN_STACK) {
      return false;
    }
    long l;
    int j;
    do
    {
      l = this.parkedWorkersStack;
      int i = (int)(0x1FFFFF & l);
      j = paramWorker.getIndexInArray();
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int k;
        if (j != 0) {
          k = 1;
        } else {
          k = 0;
        }
        if (k == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      paramWorker.setNextParkedWorker(this.workers.get(i));
    } while (!parkedWorkersStack$FU.compareAndSet(this, l, j | 2097152L + l & 0xFFFFFFFFFFE00000));
    return true;
  }
  
  public final void parkedWorkersStackTopUpdate$kotlinx_coroutines_core(Worker paramWorker, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramWorker, "worker");
    long l;
    int j;
    do
    {
      l = this.parkedWorkersStack;
      int i = (int)(0x1FFFFF & l);
      j = i;
      if (i == paramInt1) {
        if (paramInt2 == 0) {
          j = parkedWorkersStackNextIndex(paramWorker);
        } else {
          j = paramInt2;
        }
      }
    } while ((j < 0) || (!parkedWorkersStack$FU.compareAndSet(this, l, j | 2097152L + l & 0xFFFFFFFFFFE00000)));
  }
  
  /* Error */
  public final void runSafely(Task paramTask)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 501
    //   4: invokestatic 196	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_1
    //   8: invokevirtual 504	kotlinx/coroutines/scheduling/Task:run	()V
    //   11: invokestatic 468	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   14: astore_1
    //   15: aload_1
    //   16: ifnull +46 -> 62
    //   19: aload_1
    //   20: invokeinterface 507 1 0
    //   25: goto +37 -> 62
    //   28: astore_2
    //   29: invokestatic 348	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   32: astore_1
    //   33: aload_1
    //   34: ldc_w 509
    //   37: invokestatic 512	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   40: aload_1
    //   41: invokevirtual 516	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   44: aload_1
    //   45: aload_2
    //   46: invokeinterface 522 3 0
    //   51: invokestatic 468	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   54: astore_1
    //   55: aload_1
    //   56: ifnull +6 -> 62
    //   59: goto -40 -> 19
    //   62: return
    //   63: astore_1
    //   64: invokestatic 468	kotlinx/coroutines/TimeSourceKt:getTimeSource	()Lkotlinx/coroutines/TimeSource;
    //   67: astore_2
    //   68: aload_2
    //   69: ifnull +9 -> 78
    //   72: aload_2
    //   73: invokeinterface 507 1 0
    //   78: aload_1
    //   79: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	CoroutineScheduler
    //   0	80	1	paramTask	Task
    //   28	18	2	localThrowable	Throwable
    //   67	6	2	localTimeSource	kotlinx.coroutines.TimeSource
    // Exception table:
    //   from	to	target	type
    //   7	11	28	finally
    //   29	51	63	finally
  }
  
  public final void shutdown(long paramLong)
  {
    ??? = _isTerminated$FU;
    int i = 0;
    if (!((AtomicIntegerFieldUpdater)???).compareAndSet(this, 0, 1)) {
      return;
    }
    Worker localWorker1 = currentWorker();
    synchronized (this.workers)
    {
      long l = this.controlState;
      int j = (int)(l & 0x1FFFFF);
      int k;
      if (1 <= j) {
        for (k = 1;; k++)
        {
          ??? = this.workers.get(k);
          if (??? == null) {
            Intrinsics.throwNpe();
          }
          Worker localWorker2 = (Worker)???;
          if (localWorker2 != localWorker1)
          {
            while (localWorker2.isAlive())
            {
              LockSupport.unpark((Thread)localWorker2);
              localWorker2.join(paramLong);
            }
            ??? = localWorker2.state;
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              int m;
              if (??? == WorkerState.TERMINATED) {
                m = 1;
              } else {
                m = 0;
              }
              if (m == 0) {
                throw ((Throwable)new AssertionError());
              }
            }
            localWorker2.localQueue.offloadAllWorkTo(this.globalBlockingQueue);
          }
          if (k == j) {
            break;
          }
        }
      }
      this.globalBlockingQueue.close();
      this.globalCpuQueue.close();
      for (;;)
      {
        if (localWorker1 != null)
        {
          ??? = localWorker1.findTask(true);
          if (??? != null) {}
        }
        else
        {
          ??? = (Task)this.globalCpuQueue.removeFirstOrNull();
        }
        if (??? == null) {
          ??? = (Task)this.globalBlockingQueue.removeFirstOrNull();
        }
        if (??? == null) {
          break;
        }
        runSafely((Task)???);
      }
      if (localWorker1 != null) {
        localWorker1.tryReleaseCpu$kotlinx_coroutines_core(WorkerState.TERMINATED);
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        k = i;
        if ((int)((this.controlState & 0x7FFFFC0000000000) >> 42) == this.corePoolSize) {
          k = 1;
        }
        if (k == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      this.parkedWorkersStack = 0L;
      this.controlState = 0L;
      return;
    }
  }
  
  public final void signalCpuWork$kotlinx_coroutines_core()
  {
    if (tryUnpark()) {
      return;
    }
    if (tryCreateWorker$default(this, 0L, 1, null)) {
      return;
    }
    tryUnpark();
  }
  
  public String toString()
  {
    ArrayList localArrayList = new ArrayList();
    int i = this.workers.length();
    int j = 0;
    int k = 0;
    int m = k;
    int n = m;
    int i1 = n;
    int i2 = 1;
    while (i2 < i)
    {
      localObject1 = (Worker)this.workers.get(i2);
      int i3 = j;
      int i4 = k;
      int i5 = m;
      int i6 = n;
      int i7 = i1;
      if (localObject1 != null)
      {
        int i8 = ((Worker)localObject1).localQueue.getSize$kotlinx_coroutines_core();
        localObject1 = ((Worker)localObject1).state;
        i6 = CoroutineScheduler.WhenMappings.$EnumSwitchMapping$0[localObject1.ordinal()];
        if (i6 != 1)
        {
          Object localObject2;
          if (i6 != 2)
          {
            if (i6 != 3)
            {
              if (i6 != 4)
              {
                if (i6 != 5)
                {
                  i3 = j;
                  i4 = k;
                  i5 = m;
                  i6 = n;
                  i7 = i1;
                }
                else
                {
                  i7 = i1 + 1;
                  i3 = j;
                  i4 = k;
                  i5 = m;
                  i6 = n;
                }
              }
              else
              {
                n++;
                i3 = j;
                i4 = k;
                i5 = m;
                i6 = n;
                i7 = i1;
                if (i8 > 0)
                {
                  localObject2 = (Collection)localArrayList;
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append(String.valueOf(i8));
                  ((StringBuilder)localObject1).append("d");
                  ((Collection)localObject2).add(((StringBuilder)localObject1).toString());
                  i3 = j;
                  i4 = k;
                  i5 = m;
                  i6 = n;
                  i7 = i1;
                }
              }
            }
            else
            {
              i3 = j + 1;
              localObject1 = (Collection)localArrayList;
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append(String.valueOf(i8));
              ((StringBuilder)localObject2).append("c");
              ((Collection)localObject1).add(((StringBuilder)localObject2).toString());
              i4 = k;
              i5 = m;
              i6 = n;
              i7 = i1;
            }
          }
          else
          {
            i4 = k + 1;
            localObject1 = (Collection)localArrayList;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(String.valueOf(i8));
            ((StringBuilder)localObject2).append("b");
            ((Collection)localObject1).add(((StringBuilder)localObject2).toString());
            i3 = j;
            i5 = m;
            i6 = n;
            i7 = i1;
          }
        }
        else
        {
          i5 = m + 1;
          i7 = i1;
          i6 = n;
          i4 = k;
          i3 = j;
        }
      }
      i2++;
      j = i3;
      k = i4;
      m = i5;
      n = i6;
      i1 = i7;
    }
    long l = this.controlState;
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(this.schedulerName);
    ((StringBuilder)localObject1).append('@');
    ((StringBuilder)localObject1).append(DebugStringsKt.getHexAddress(this));
    ((StringBuilder)localObject1).append('[');
    ((StringBuilder)localObject1).append("Pool Size {");
    ((StringBuilder)localObject1).append("core = ");
    ((StringBuilder)localObject1).append(this.corePoolSize);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("max = ");
    ((StringBuilder)localObject1).append(this.maxPoolSize);
    ((StringBuilder)localObject1).append("}, ");
    ((StringBuilder)localObject1).append("Worker States {");
    ((StringBuilder)localObject1).append("CPU = ");
    ((StringBuilder)localObject1).append(j);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("blocking = ");
    ((StringBuilder)localObject1).append(k);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("parked = ");
    ((StringBuilder)localObject1).append(m);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("dormant = ");
    ((StringBuilder)localObject1).append(n);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("terminated = ");
    ((StringBuilder)localObject1).append(i1);
    ((StringBuilder)localObject1).append("}, ");
    ((StringBuilder)localObject1).append("running workers queues = ");
    ((StringBuilder)localObject1).append(localArrayList);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("global CPU queue size = ");
    ((StringBuilder)localObject1).append(this.globalCpuQueue.getSize());
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("global blocking queue size = ");
    ((StringBuilder)localObject1).append(this.globalBlockingQueue.getSize());
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("Control State {");
    ((StringBuilder)localObject1).append("created workers= ");
    ((StringBuilder)localObject1).append((int)(0x1FFFFF & l));
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("blocking tasks = ");
    ((StringBuilder)localObject1).append((int)((0x3FFFFE00000 & l) >> 21));
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append("CPUs acquired = ");
    ((StringBuilder)localObject1).append(this.corePoolSize - (int)((0x7FFFFC0000000000 & l) >> 42));
    ((StringBuilder)localObject1).append("}]");
    return ((StringBuilder)localObject1).toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\002\b\007\n\002\030\002\n\002\b\006\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?T?\006\002\n\000R\016\020\007\032\0020\006X?T?\006\002\n\000R\016\020\b\032\0020\004X?T?\006\002\n\000R\016\020\t\032\0020\006X?T?\006\002\n\000R\016\020\n\032\0020\004X?T?\006\002\n\000R\016\020\013\032\0020\006X?T?\006\002\n\000R\016\020\f\032\0020\006X?T?\006\002\n\000R\020\020\r\032\0020\0168\006X?\004?\006\002\n\000R\016\020\017\032\0020\006X?T?\006\002\n\000R\016\020\020\032\0020\004X?T?\006\002\n\000R\016\020\021\032\0020\004X?T?\006\002\n\000R\016\020\022\032\0020\004X?T?\006\002\n\000R\016\020\023\032\0020\006X?T?\006\002\n\000?\006\024"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Companion;", "", "()V", "BLOCKING_MASK", "", "BLOCKING_SHIFT", "", "CLAIMED", "CPU_PERMITS_MASK", "CPU_PERMITS_SHIFT", "CREATED_MASK", "MAX_SUPPORTED_POOL_SIZE", "MIN_SUPPORTED_POOL_SIZE", "NOT_IN_STACK", "Lkotlinx/coroutines/internal/Symbol;", "PARKED", "PARKED_INDEX_MASK", "PARKED_VERSION_INC", "PARKED_VERSION_MASK", "TERMINATED", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000V\n\002\030\002\n\002\020\b\n\002\b\004\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\024\n\002\030\002\n\002\b\r\n\002\030\002\n\002\b\004\n\002\020\t\n\002\b\002\n\002\020\000\n\002\b\007\n\002\030\002\n\002\b\007\n\002\030\002\b?\004\030\0002\0020KB\021\b\026\022\006\020\002\032\0020\001?\006\004\b\003\020\004B\t\b\002?\006\004\b\003\020\005J\027\020\t\032\0020\b2\006\020\007\032\0020\006H\002?\006\004\b\t\020\nJ\027\020\013\032\0020\b2\006\020\007\032\0020\006H\002?\006\004\b\013\020\nJ\027\020\016\032\0020\b2\006\020\r\032\0020\fH\002?\006\004\b\016\020\017J\031\020\022\032\004\030\0010\f2\006\020\021\032\0020\020H\002?\006\004\b\022\020\023J\027\020\024\032\004\030\0010\f2\006\020\021\032\0020\020?\006\004\b\024\020\023J\027\020\026\032\0020\b2\006\020\025\032\0020\006H\002?\006\004\b\026\020\nJ\017\020\027\032\0020\020H\002?\006\004\b\027\020\030J\027\020\034\032\0020\0012\006\020\031\032\0020\001H\000?\006\004\b\032\020\033J\017\020\035\032\0020\bH\002?\006\004\b\035\020\036J\021\020\037\032\004\030\0010\fH\002?\006\004\b\037\020 J\017\020!\032\0020\bH\026?\006\004\b!\020\036J\017\020\"\032\0020\bH\002?\006\004\b\"\020\036J\017\020#\032\0020\020H\002?\006\004\b#\020\030J\017\020$\032\0020\bH\002?\006\004\b$\020\036J\027\020)\032\0020\0202\006\020&\032\0020%H\000?\006\004\b'\020(J\031\020+\032\004\030\0010\f2\006\020*\032\0020\020H\002?\006\004\b+\020\023J\017\020,\032\0020\bH\002?\006\004\b,\020\036R*\020-\032\0020\0012\006\020\002\032\0020\0018\006@FX?\016?\006\022\n\004\b-\020.\032\004\b/\0200\"\004\b1\0202R\026\0204\032\002038\006@\007X?\004?\006\006\n\004\b4\0205R\026\0206\032\0020\0208\006@\006X?\016?\006\006\n\004\b6\0207R\026\0209\032\002088\002@\002X?\016?\006\006\n\004\b9\020:R$\020<\032\004\030\0010;8\006@\006X?\016?\006\022\n\004\b<\020=\032\004\b>\020?\"\004\b@\020AR\026\020B\032\0020\0018\002@\002X?\016?\006\006\n\004\bB\020.R\024\020F\032\0020C8?\002@\006?\006\006\032\004\bD\020ER\026\020G\032\0020%8\006@\006X?\016?\006\006\n\004\bG\020HR\026\020I\032\002088\002@\002X?\016?\006\006\n\004\bI\020:?\006J"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "", "index", "<init>", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;)V", "Lkotlinx/coroutines/scheduling/TaskMode;", "taskMode", "", "afterTask", "(Lkotlinx/coroutines/scheduling/TaskMode;)V", "beforeTask", "Lkotlinx/coroutines/scheduling/Task;", "task", "executeTask", "(Lkotlinx/coroutines/scheduling/Task;)V", "", "scanLocalQueue", "findAnyTask", "(Z)Lkotlinx/coroutines/scheduling/Task;", "findTask", "mode", "idleReset", "inStack", "()Z", "upperBound", "nextInt$kotlinx_coroutines_core", "(I)I", "nextInt", "park", "()V", "pollGlobalQueues", "()Lkotlinx/coroutines/scheduling/Task;", "run", "runWorker", "tryAcquireCpuPermit", "tryPark", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "newState", "tryReleaseCpu$kotlinx_coroutines_core", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;)Z", "tryReleaseCpu", "blockingOnly", "trySteal", "tryTerminateWorker", "indexInArray", "I", "getIndexInArray", "()I", "setIndexInArray", "(I)V", "Lkotlinx/coroutines/scheduling/WorkQueue;", "localQueue", "Lkotlinx/coroutines/scheduling/WorkQueue;", "mayHaveLocalTasks", "Z", "", "minDelayUntilStealableTaskNs", "J", "", "nextParkedWorker", "Ljava/lang/Object;", "getNextParkedWorker", "()Ljava/lang/Object;", "setNextParkedWorker", "(Ljava/lang/Object;)V", "rngState", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "getScheduler", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "scheduler", "state", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "terminationDeadline", "kotlinx-coroutines-core", "Ljava/lang/Thread;"}, k=1, mv={1, 1, 16})
  public final class Worker
    extends Thread
  {
    static final AtomicIntegerFieldUpdater workerCtl$FU = AtomicIntegerFieldUpdater.newUpdater(Worker.class, "workerCtl");
    private volatile int indexInArray;
    public final WorkQueue localQueue;
    public boolean mayHaveLocalTasks;
    private long minDelayUntilStealableTaskNs;
    private volatile Object nextParkedWorker;
    private int rngState;
    public CoroutineScheduler.WorkerState state;
    private long terminationDeadline;
    volatile int workerCtl;
    
    private Worker()
    {
      setDaemon(true);
      this.localQueue = new WorkQueue();
      this.state = CoroutineScheduler.WorkerState.DORMANT;
      this.workerCtl = 0;
      this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
      this.rngState = Random.Default.nextInt();
    }
    
    public Worker()
    {
      this();
      int i;
      setIndexInArray(i);
    }
    
    private final void afterTask(TaskMode paramTaskMode)
    {
      if (paramTaskMode == TaskMode.NON_BLOCKING) {
        return;
      }
      paramTaskMode = this.this$0;
      CoroutineScheduler.controlState$FU.addAndGet(paramTaskMode, -2097152L);
      paramTaskMode = this.state;
      if (paramTaskMode != CoroutineScheduler.WorkerState.TERMINATED)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i;
          if (paramTaskMode == CoroutineScheduler.WorkerState.BLOCKING) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
        this.state = CoroutineScheduler.WorkerState.DORMANT;
      }
    }
    
    private final void beforeTask(TaskMode paramTaskMode)
    {
      if (paramTaskMode == TaskMode.NON_BLOCKING) {
        return;
      }
      if (tryReleaseCpu$kotlinx_coroutines_core(CoroutineScheduler.WorkerState.BLOCKING)) {
        this.this$0.signalCpuWork$kotlinx_coroutines_core();
      }
    }
    
    private final void executeTask(Task paramTask)
    {
      TaskMode localTaskMode = paramTask.taskContext.getTaskMode();
      idleReset(localTaskMode);
      beforeTask(localTaskMode);
      this.this$0.runSafely(paramTask);
      afterTask(localTaskMode);
    }
    
    private final Task findAnyTask(boolean paramBoolean)
    {
      Task localTask;
      if (paramBoolean)
      {
        int i;
        if (nextInt$kotlinx_coroutines_core(this.this$0.corePoolSize * 2) == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          localTask = pollGlobalQueues();
          if (localTask != null) {
            return localTask;
          }
        }
        localTask = this.localQueue.poll();
        if (localTask != null) {
          return localTask;
        }
        if (i == 0)
        {
          localTask = pollGlobalQueues();
          if (localTask != null) {
            return localTask;
          }
        }
      }
      else
      {
        localTask = pollGlobalQueues();
        if (localTask != null) {
          return localTask;
        }
      }
      return trySteal(false);
    }
    
    private final void idleReset(TaskMode paramTaskMode)
    {
      this.terminationDeadline = 0L;
      if (this.state == CoroutineScheduler.WorkerState.PARKING)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i;
          if (paramTaskMode == TaskMode.PROBABLY_BLOCKING) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
        this.state = CoroutineScheduler.WorkerState.BLOCKING;
      }
    }
    
    private final boolean inStack()
    {
      boolean bool;
      if (this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private final void park()
    {
      if (this.terminationDeadline == 0L) {
        this.terminationDeadline = (System.nanoTime() + this.this$0.idleWorkerKeepAliveNs);
      }
      LockSupport.parkNanos(this.this$0.idleWorkerKeepAliveNs);
      if (System.nanoTime() - this.terminationDeadline >= 0L)
      {
        this.terminationDeadline = 0L;
        tryTerminateWorker();
      }
    }
    
    private final Task pollGlobalQueues()
    {
      if (nextInt$kotlinx_coroutines_core(2) == 0)
      {
        localTask = (Task)this.this$0.globalCpuQueue.removeFirstOrNull();
        if (localTask != null) {
          return localTask;
        }
        return (Task)this.this$0.globalBlockingQueue.removeFirstOrNull();
      }
      Task localTask = (Task)this.this$0.globalBlockingQueue.removeFirstOrNull();
      if (localTask != null) {
        return localTask;
      }
      return (Task)this.this$0.globalCpuQueue.removeFirstOrNull();
    }
    
    private final void runWorker()
    {
      int i = 0;
      for (;;)
      {
        if ((this.this$0.isTerminated()) || (this.state == CoroutineScheduler.WorkerState.TERMINATED)) {
          break label105;
        }
        Task localTask = findTask(this.mayHaveLocalTasks);
        if (localTask != null)
        {
          this.minDelayUntilStealableTaskNs = 0L;
          executeTask(localTask);
          break;
        }
        this.mayHaveLocalTasks = false;
        if (this.minDelayUntilStealableTaskNs != 0L)
        {
          if (i == 0)
          {
            i = 1;
            continue;
          }
          tryReleaseCpu$kotlinx_coroutines_core(CoroutineScheduler.WorkerState.PARKING);
          Thread.interrupted();
          LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
          this.minDelayUntilStealableTaskNs = 0L;
          break;
        }
        tryPark();
      }
      label105:
      tryReleaseCpu$kotlinx_coroutines_core(CoroutineScheduler.WorkerState.TERMINATED);
    }
    
    private final boolean tryAcquireCpuPermit()
    {
      Object localObject = this.state;
      CoroutineScheduler.WorkerState localWorkerState = CoroutineScheduler.WorkerState.CPU_ACQUIRED;
      boolean bool = false;
      if (localObject == localWorkerState) {}
      for (;;)
      {
        bool = true;
        break;
        localObject = this.this$0;
        long l;
        do
        {
          l = ((CoroutineScheduler)localObject).controlState;
          if ((int)((0x7FFFFC0000000000 & l) >> 42) == 0)
          {
            i = 0;
            break;
          }
        } while (!CoroutineScheduler.controlState$FU.compareAndSet(localObject, l, l - 4398046511104L));
        int i = 1;
        if (i == 0) {
          break;
        }
        this.state = CoroutineScheduler.WorkerState.CPU_ACQUIRED;
      }
      return bool;
    }
    
    private final void tryPark()
    {
      if (!inStack())
      {
        this.this$0.parkedWorkersStackPush$kotlinx_coroutines_core(this);
        return;
      }
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      this.workerCtl = -1;
      while ((inStack()) && (!this.this$0.isTerminated()) && (this.state != CoroutineScheduler.WorkerState.TERMINATED))
      {
        tryReleaseCpu$kotlinx_coroutines_core(CoroutineScheduler.WorkerState.PARKING);
        Thread.interrupted();
        park();
      }
    }
    
    private final Task trySteal(boolean paramBoolean)
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      int j = CoroutineScheduler.access$getCreatedWorkers$p(this.this$0);
      if (j < 2) {
        return null;
      }
      int i = nextInt$kotlinx_coroutines_core(j);
      int k = 0;
      long l2;
      for (long l1 = Long.MAX_VALUE; k < j; l1 = l2)
      {
        int m = i + 1;
        i = m;
        if (m > j) {
          i = 1;
        }
        Worker localWorker = (Worker)this.this$0.workers.get(i);
        l2 = l1;
        if (localWorker != null)
        {
          l2 = l1;
          if (localWorker != (Worker)this)
          {
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              if (this.localQueue.getSize$kotlinx_coroutines_core() == 0) {
                m = 1;
              } else {
                m = 0;
              }
              if (m == 0) {
                throw ((Throwable)new AssertionError());
              }
            }
            long l3;
            if (paramBoolean) {
              l3 = this.localQueue.tryStealBlockingFrom(localWorker.localQueue);
            } else {
              l3 = this.localQueue.tryStealFrom(localWorker.localQueue);
            }
            if (l3 == -1L) {
              return this.localQueue.poll();
            }
            l2 = l1;
            if (l3 > 0L) {
              l2 = Math.min(l1, l3);
            }
          }
        }
        k++;
      }
      if (l1 == Long.MAX_VALUE) {
        l1 = 0L;
      }
      this.minDelayUntilStealableTaskNs = l1;
      return null;
    }
    
    private final void tryTerminateWorker()
    {
      synchronized (this.this$0.workers)
      {
        boolean bool = this.this$0.isTerminated();
        if (bool) {
          return;
        }
        int i = CoroutineScheduler.access$getCreatedWorkers$p(this.this$0);
        int j = this.this$0.corePoolSize;
        if (i <= j) {
          return;
        }
        bool = workerCtl$FU.compareAndSet(this, -1, 1);
        if (!bool) {
          return;
        }
        j = this.indexInArray;
        setIndexInArray(0);
        this.this$0.parkedWorkersStackTopUpdate$kotlinx_coroutines_core(this, j, 0);
        Object localObject1 = this.this$0;
        i = (int)(CoroutineScheduler.controlState$FU.getAndDecrement(localObject1) & 0x1FFFFF);
        if (i != j)
        {
          localObject1 = this.this$0.workers.get(i);
          if (localObject1 == null) {
            Intrinsics.throwNpe();
          }
          localObject1 = (Worker)localObject1;
          this.this$0.workers.set(j, localObject1);
          ((Worker)localObject1).setIndexInArray(j);
          this.this$0.parkedWorkersStackTopUpdate$kotlinx_coroutines_core((Worker)localObject1, i, j);
        }
        this.this$0.workers.set(i, null);
        localObject1 = Unit.INSTANCE;
        this.state = CoroutineScheduler.WorkerState.TERMINATED;
        return;
      }
    }
    
    public final Task findTask(boolean paramBoolean)
    {
      if (tryAcquireCpuPermit()) {
        return findAnyTask(paramBoolean);
      }
      Task localTask;
      if (paramBoolean)
      {
        localTask = this.localQueue.poll();
        if (localTask == null) {
          localTask = (Task)this.this$0.globalBlockingQueue.removeFirstOrNull();
        }
      }
      else
      {
        localTask = (Task)this.this$0.globalBlockingQueue.removeFirstOrNull();
      }
      if (localTask == null) {
        localTask = trySteal(true);
      }
      return localTask;
    }
    
    public final int getIndexInArray()
    {
      return this.indexInArray;
    }
    
    public final Object getNextParkedWorker()
    {
      return this.nextParkedWorker;
    }
    
    public final CoroutineScheduler getScheduler()
    {
      return this.this$0;
    }
    
    public final int nextInt$kotlinx_coroutines_core(int paramInt)
    {
      int i = this.rngState;
      i ^= i << 13;
      i ^= i >> 17;
      i ^= i << 5;
      this.rngState = i;
      int j = paramInt - 1;
      if ((j & paramInt) == 0) {
        return i & j;
      }
      return (i & 0x7FFFFFFF) % paramInt;
    }
    
    public void run()
    {
      runWorker();
    }
    
    public final void setIndexInArray(int paramInt)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.this$0.schedulerName);
      localStringBuilder.append("-worker-");
      String str;
      if (paramInt == 0) {
        str = "TERMINATED";
      } else {
        str = String.valueOf(paramInt);
      }
      localStringBuilder.append(str);
      setName(localStringBuilder.toString());
      this.indexInArray = paramInt;
    }
    
    public final void setNextParkedWorker(Object paramObject)
    {
      this.nextParkedWorker = paramObject;
    }
    
    public final boolean tryReleaseCpu$kotlinx_coroutines_core(CoroutineScheduler.WorkerState paramWorkerState)
    {
      Intrinsics.checkParameterIsNotNull(paramWorkerState, "newState");
      CoroutineScheduler.WorkerState localWorkerState = this.state;
      boolean bool;
      if (localWorkerState == CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        CoroutineScheduler localCoroutineScheduler = this.this$0;
        CoroutineScheduler.controlState$FU.addAndGet(localCoroutineScheduler, 4398046511104L);
      }
      if (localWorkerState != paramWorkerState) {
        this.state = paramWorkerState;
      }
      return bool;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\007\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007?\006\b"}, d2={"Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "", "(Ljava/lang/String;I)V", "CPU_ACQUIRED", "BLOCKING", "PARKING", "DORMANT", "TERMINATED", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static enum WorkerState
  {
    static
    {
      WorkerState localWorkerState1 = new WorkerState("CPU_ACQUIRED", 0);
      CPU_ACQUIRED = localWorkerState1;
      WorkerState localWorkerState2 = new WorkerState("BLOCKING", 1);
      BLOCKING = localWorkerState2;
      WorkerState localWorkerState3 = new WorkerState("PARKING", 2);
      PARKING = localWorkerState3;
      WorkerState localWorkerState4 = new WorkerState("DORMANT", 3);
      DORMANT = localWorkerState4;
      WorkerState localWorkerState5 = new WorkerState("TERMINATED", 4);
      TERMINATED = localWorkerState5;
      $VALUES = new WorkerState[] { localWorkerState1, localWorkerState2, localWorkerState3, localWorkerState4, localWorkerState5 };
    }
    
    private WorkerState() {}
  }
}
