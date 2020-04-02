package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\002\b\005\n\002\030\002\n\000\n\002\020\002\n\002\b\t\n\002\020\t\n\002\b\b\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\006\n\002\020\000\b\000\030\0002\0020*B\007?\006\004\b\001\020\002J!\020\007\032\004\030\0010\0032\006\020\004\032\0020\0032\b\b\002\020\006\032\0020\005?\006\004\b\007\020\bJ\031\020\t\032\004\030\0010\0032\006\020\004\032\0020\003H\002?\006\004\b\t\020\nJ\025\020\016\032\0020\r2\006\020\f\032\0020\013?\006\004\b\016\020\017J\017\020\020\032\004\030\0010\003?\006\004\b\020\020\021J\021\020\022\032\004\030\0010\003H\002?\006\004\b\022\020\021J\027\020\024\032\0020\0052\006\020\023\032\0020\013H\002?\006\004\b\024\020\025J\025\020\030\032\0020\0272\006\020\026\032\0020\000?\006\004\b\030\020\031J\025\020\032\032\0020\0272\006\020\026\032\0020\000?\006\004\b\032\020\031J\037\020\034\032\0020\0272\006\020\026\032\0020\0002\006\020\033\032\0020\005H\002?\006\004\b\034\020\035J\025\020\036\032\0020\r*\004\030\0010\003H\002?\006\004\b\036\020\037R\036\020!\032\n\022\006\022\004\030\0010\0030 8\002@\002X?\004?\006\006\n\004\b!\020\"R\026\020&\032\0020#8@@\000X?\004?\006\006\032\004\b$\020%R\026\020(\032\0020#8@@\000X?\004?\006\006\032\004\b'\020%?\006)"}, d2={"Lkotlinx/coroutines/scheduling/WorkQueue;", "<init>", "()V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "fair", "add", "(Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "addLast", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalQueue", "", "offloadAllWorkTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)V", "poll", "()Lkotlinx/coroutines/scheduling/Task;", "pollBuffer", "queue", "pollTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "victim", "", "tryStealBlockingFrom", "(Lkotlinx/coroutines/scheduling/WorkQueue;)J", "tryStealFrom", "blockingOnly", "tryStealLastScheduled", "(Lkotlinx/coroutines/scheduling/WorkQueue;Z)J", "decrementIfBlocking", "(Lkotlinx/coroutines/scheduling/Task;)V", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "buffer", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "", "getBufferSize$kotlinx_coroutines_core", "()I", "bufferSize", "getSize$kotlinx_coroutines_core", "size", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
public final class WorkQueue
{
  private static final AtomicIntegerFieldUpdater blockingTasksInBuffer$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "blockingTasksInBuffer");
  private static final AtomicIntegerFieldUpdater consumerIndex$FU;
  private static final AtomicReferenceFieldUpdater lastScheduledTask$FU = AtomicReferenceFieldUpdater.newUpdater(WorkQueue.class, Object.class, "lastScheduledTask");
  private static final AtomicIntegerFieldUpdater producerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "producerIndex");
  private volatile int blockingTasksInBuffer = 0;
  private final AtomicReferenceArray<Task> buffer = new AtomicReferenceArray(128);
  private volatile int consumerIndex = 0;
  private volatile Object lastScheduledTask = null;
  private volatile int producerIndex = 0;
  
  static
  {
    consumerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(WorkQueue.class, "consumerIndex");
  }
  
  public WorkQueue() {}
  
  private final Task addLast(Task paramTask)
  {
    if (paramTask.taskContext.getTaskMode() == TaskMode.PROBABLY_BLOCKING) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      blockingTasksInBuffer$FU.incrementAndGet(this);
    }
    if (getBufferSize$kotlinx_coroutines_core() == 127) {
      return paramTask;
    }
    int i = this.producerIndex & 0x7F;
    while (this.buffer.get(i) != null) {
      Thread.yield();
    }
    this.buffer.lazySet(i, paramTask);
    producerIndex$FU.incrementAndGet(this);
    return null;
  }
  
  private final void decrementIfBlocking(Task paramTask)
  {
    if (paramTask != null)
    {
      paramTask = paramTask.taskContext.getTaskMode();
      TaskMode localTaskMode = TaskMode.PROBABLY_BLOCKING;
      int i = 1;
      int j;
      if (paramTask == localTaskMode) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        j = blockingTasksInBuffer$FU.decrementAndGet(this);
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (j >= 0) {
            j = i;
          } else {
            j = 0;
          }
          if (j == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
      }
    }
  }
  
  private final Task pollBuffer()
  {
    Task localTask;
    do
    {
      int i;
      do
      {
        i = this.consumerIndex;
        if (i - this.producerIndex == 0) {
          return null;
        }
      } while (!consumerIndex$FU.compareAndSet(this, i, i + 1));
      localTask = (Task)this.buffer.getAndSet(i & 0x7F, null);
    } while (localTask == null);
    decrementIfBlocking(localTask);
    return localTask;
  }
  
  private final boolean pollTo(GlobalQueue paramGlobalQueue)
  {
    Task localTask = pollBuffer();
    if (localTask != null)
    {
      paramGlobalQueue.addLast(localTask);
      return true;
    }
    return false;
  }
  
  private final long tryStealLastScheduled(WorkQueue paramWorkQueue, boolean paramBoolean)
  {
    Task localTask;
    do
    {
      localTask = (Task)paramWorkQueue.lastScheduledTask;
      if (localTask == null) {
        break;
      }
      if (paramBoolean)
      {
        int i;
        if (localTask.taskContext.getTaskMode() == TaskMode.PROBABLY_BLOCKING) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          return -2L;
        }
      }
      long l = TasksKt.schedulerTimeSource.nanoTime() - localTask.submissionTime;
      if (l < TasksKt.WORK_STEALING_TIME_RESOLUTION_NS) {
        return TasksKt.WORK_STEALING_TIME_RESOLUTION_NS - l;
      }
    } while (!lastScheduledTask$FU.compareAndSet(paramWorkQueue, localTask, null));
    add$default(this, localTask, false, 2, null);
    return -1L;
    return -2L;
  }
  
  public final Task add(Task paramTask, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramTask, "task");
    if (paramBoolean) {
      return addLast(paramTask);
    }
    paramTask = (Task)lastScheduledTask$FU.getAndSet(this, paramTask);
    if (paramTask != null) {
      return addLast(paramTask);
    }
    return null;
  }
  
  public final int getBufferSize$kotlinx_coroutines_core()
  {
    return this.producerIndex - this.consumerIndex;
  }
  
  public final int getSize$kotlinx_coroutines_core()
  {
    int i;
    if (this.lastScheduledTask != null) {
      i = getBufferSize$kotlinx_coroutines_core() + 1;
    } else {
      i = getBufferSize$kotlinx_coroutines_core();
    }
    return i;
  }
  
  public final void offloadAllWorkTo(GlobalQueue paramGlobalQueue)
  {
    Intrinsics.checkParameterIsNotNull(paramGlobalQueue, "globalQueue");
    Task localTask = (Task)lastScheduledTask$FU.getAndSet(this, null);
    if (localTask != null) {
      paramGlobalQueue.addLast(localTask);
    }
    while (pollTo(paramGlobalQueue)) {}
  }
  
  public final Task poll()
  {
    Task localTask = (Task)lastScheduledTask$FU.getAndSet(this, null);
    if (localTask == null) {
      localTask = pollBuffer();
    }
    return localTask;
  }
  
  public final long tryStealBlockingFrom(WorkQueue paramWorkQueue)
  {
    Intrinsics.checkParameterIsNotNull(paramWorkQueue, "victim");
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (getBufferSize$kotlinx_coroutines_core() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    int i = paramWorkQueue.consumerIndex;
    int j = paramWorkQueue.producerIndex;
    AtomicReferenceArray localAtomicReferenceArray = paramWorkQueue.buffer;
    while (i != j)
    {
      int k = i & 0x7F;
      if (paramWorkQueue.blockingTasksInBuffer == 0) {
        break;
      }
      Task localTask = (Task)localAtomicReferenceArray.get(k);
      if (localTask != null)
      {
        int m;
        if (localTask.taskContext.getTaskMode() == TaskMode.PROBABLY_BLOCKING) {
          m = 1;
        } else {
          m = 0;
        }
        if ((m != 0) && (localAtomicReferenceArray.compareAndSet(k, localTask, null)))
        {
          blockingTasksInBuffer$FU.decrementAndGet(paramWorkQueue);
          add$default(this, localTask, false, 2, null);
          return -1L;
        }
      }
      i++;
    }
    return tryStealLastScheduled(paramWorkQueue, true);
  }
  
  public final long tryStealFrom(WorkQueue paramWorkQueue)
  {
    Intrinsics.checkParameterIsNotNull(paramWorkQueue, "victim");
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int i = 1;
    int j;
    if (bool)
    {
      if (getBufferSize$kotlinx_coroutines_core() == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    Task localTask = paramWorkQueue.pollBuffer();
    if (localTask != null)
    {
      paramWorkQueue = add$default(this, localTask, false, 2, null);
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        if (paramWorkQueue == null) {
          j = i;
        } else {
          j = 0;
        }
        if (j == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      return -1L;
    }
    return tryStealLastScheduled(paramWorkQueue, false);
  }
}
