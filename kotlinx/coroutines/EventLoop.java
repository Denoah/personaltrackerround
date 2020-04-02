package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ArrayQueue;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\t\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\n\b \030\0002\0020\001B\005?\006\002\020\002J\020\020\022\032\0020\0232\b\b\002\020\024\032\0020\004J\020\020\025\032\0020\n2\006\020\024\032\0020\004H\002J\022\020\026\032\0020\0232\n\020\027\032\006\022\002\b\0030\020J\020\020\030\032\0020\0232\b\b\002\020\024\032\0020\004J\b\020\031\032\0020\nH\026J\006\020\032\032\0020\004J\b\020\033\032\0020\004H\026J\b\020\034\032\0020\023H\024R\021\020\003\032\0020\0048F?\006\006\032\004\b\003\020\005R\024\020\006\032\0020\0048TX?\004?\006\006\032\004\b\006\020\005R\021\020\007\032\0020\0048F?\006\006\032\004\b\007\020\005R\021\020\b\032\0020\0048F?\006\006\032\004\b\b\020\005R\024\020\t\032\0020\n8TX?\004?\006\006\032\004\b\013\020\fR\016\020\r\032\0020\004X?\016?\006\002\n\000R\032\020\016\032\016\022\b\022\006\022\002\b\0030\020\030\0010\017X?\016?\006\002\n\000R\016\020\021\032\0020\nX?\016?\006\002\n\000?\006\035"}, d2={"Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "isActive", "", "()Z", "isEmpty", "isUnconfinedLoopActive", "isUnconfinedQueueEmpty", "nextTime", "", "getNextTime", "()J", "shared", "unconfinedQueue", "Lkotlinx/coroutines/internal/ArrayQueue;", "Lkotlinx/coroutines/DispatchedTask;", "useCount", "decrementUseCount", "", "unconfined", "delta", "dispatchUnconfined", "task", "incrementUseCount", "processNextEvent", "processUnconfinedEvent", "shouldBeProcessedFromContext", "shutdown", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class EventLoop
  extends CoroutineDispatcher
{
  private boolean shared;
  private ArrayQueue<DispatchedTask<?>> unconfinedQueue;
  private long useCount;
  
  public EventLoop() {}
  
  private final long delta(boolean paramBoolean)
  {
    long l;
    if (paramBoolean) {
      l = 4294967296L;
    } else {
      l = 1L;
    }
    return l;
  }
  
  public final void decrementUseCount(boolean paramBoolean)
  {
    long l = this.useCount - delta(paramBoolean);
    this.useCount = l;
    if (l > 0L) {
      return;
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (this.useCount == 0L) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    if (this.shared) {
      shutdown();
    }
  }
  
  public final void dispatchUnconfined(DispatchedTask<?> paramDispatchedTask)
  {
    Intrinsics.checkParameterIsNotNull(paramDispatchedTask, "task");
    ArrayQueue localArrayQueue = this.unconfinedQueue;
    if (localArrayQueue == null)
    {
      localArrayQueue = new ArrayQueue();
      this.unconfinedQueue = localArrayQueue;
    }
    localArrayQueue.addLast(paramDispatchedTask);
  }
  
  protected long getNextTime()
  {
    ArrayQueue localArrayQueue = this.unconfinedQueue;
    long l1 = Long.MAX_VALUE;
    long l2 = l1;
    if (localArrayQueue != null) {
      if (localArrayQueue.isEmpty()) {
        l2 = l1;
      } else {
        l2 = 0L;
      }
    }
    return l2;
  }
  
  public final void incrementUseCount(boolean paramBoolean)
  {
    this.useCount += delta(paramBoolean);
    if (!paramBoolean) {
      this.shared = true;
    }
  }
  
  public final boolean isActive()
  {
    boolean bool;
    if (this.useCount > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected boolean isEmpty()
  {
    return isUnconfinedQueueEmpty();
  }
  
  public final boolean isUnconfinedLoopActive()
  {
    long l = this.useCount;
    boolean bool = true;
    if (l < delta(true)) {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isUnconfinedQueueEmpty()
  {
    ArrayQueue localArrayQueue = this.unconfinedQueue;
    boolean bool;
    if (localArrayQueue != null) {
      bool = localArrayQueue.isEmpty();
    } else {
      bool = true;
    }
    return bool;
  }
  
  public long processNextEvent()
  {
    if (!processUnconfinedEvent()) {
      return Long.MAX_VALUE;
    }
    return getNextTime();
  }
  
  public final boolean processUnconfinedEvent()
  {
    Object localObject = this.unconfinedQueue;
    if (localObject != null)
    {
      localObject = (DispatchedTask)((ArrayQueue)localObject).removeFirstOrNull();
      if (localObject != null)
      {
        ((DispatchedTask)localObject).run();
        return true;
      }
    }
    return false;
  }
  
  public boolean shouldBeProcessedFromContext()
  {
    return false;
  }
  
  protected void shutdown() {}
}
