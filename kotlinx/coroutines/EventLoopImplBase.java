package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

@Metadata(bv={1, 0, 3}, d1={"\000Z\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\t\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\023\n\002\030\002\n\002\030\002\b \030\0002\002092\0020::\0044567B\007?\006\004\b\001\020\002J\017\020\004\032\0020\003H\002?\006\004\b\004\020\002J\027\020\007\032\n\030\0010\005j\004\030\001`\006H\002?\006\004\b\007\020\bJ!\020\f\032\0020\0032\006\020\n\032\0020\t2\n\020\013\032\0060\005j\002`\006?\006\004\b\f\020\rJ\031\020\017\032\0020\0032\n\020\016\032\0060\005j\002`\006?\006\004\b\017\020\020J\033\020\022\032\0020\0212\n\020\016\032\0060\005j\002`\006H\002?\006\004\b\022\020\023J\017\020\025\032\0020\024H\026?\006\004\b\025\020\026J\017\020\027\032\0020\003H\002?\006\004\b\027\020\002J\017\020\030\032\0020\003H\004?\006\004\b\030\020\002J\035\020\034\032\0020\0032\006\020\031\032\0020\0242\006\020\033\032\0020\032?\006\004\b\034\020\035J\037\020\037\032\0020\0362\006\020\031\032\0020\0242\006\020\033\032\0020\032H\002?\006\004\b\037\020 J#\020#\032\0020\"2\006\020!\032\0020\0242\n\020\013\032\0060\005j\002`\006H\004?\006\004\b#\020$J%\020'\032\0020\0032\006\020!\032\0020\0242\f\020&\032\b\022\004\022\0020\0030%H\026?\006\004\b'\020(J\027\020)\032\0020\0212\006\020\016\032\0020\032H\002?\006\004\b)\020*J\017\020+\032\0020\003H\024?\006\004\b+\020\002R$\020-\032\0020\0212\006\020,\032\0020\0218B@BX?\016?\006\f\032\004\b-\020.\"\004\b/\0200R\026\0201\032\0020\0218T@\024X?\004?\006\006\032\004\b1\020.R\026\0203\032\0020\0248T@\024X?\004?\006\006\032\004\b2\020\026?\0068"}, d2={"Lkotlinx/coroutines/EventLoopImplBase;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", "context", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", "value", "isCompleted", "()Z", "setCompleted", "(Z)V", "isEmpty", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;"}, k=1, mv={1, 1, 16})
public abstract class EventLoopImplBase
  extends EventLoopImplPlatform
  implements Delay
{
  private static final AtomicReferenceFieldUpdater _delayed$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_delayed");
  private static final AtomicReferenceFieldUpdater _queue$FU = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_queue");
  private volatile Object _delayed = null;
  private volatile int _isCompleted = 0;
  private volatile Object _queue = null;
  
  public EventLoopImplBase() {}
  
  private final void closeQueue()
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!isCompleted())) {
      throw ((Throwable)new AssertionError());
    }
    Object localObject;
    LockFreeTaskQueueCore localLockFreeTaskQueueCore;
    do
    {
      do
      {
        localObject = this._queue;
        if (localObject != null) {
          break;
        }
      } while (!_queue$FU.compareAndSet(this, null, EventLoop_commonKt.access$getCLOSED_EMPTY$p()));
      return;
      if ((localObject instanceof LockFreeTaskQueueCore))
      {
        ((LockFreeTaskQueueCore)localObject).close();
        return;
      }
      if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
        return;
      }
      localLockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
      if (localObject == null) {
        break;
      }
      localLockFreeTaskQueueCore.addLast((Runnable)localObject);
    } while (!_queue$FU.compareAndSet(this, localObject, localLockFreeTaskQueueCore));
    return;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
  }
  
  private final Runnable dequeue()
  {
    Object localObject1;
    label70:
    do
    {
      for (;;)
      {
        localObject1 = this._queue;
        if (localObject1 == null) {
          return null;
        }
        if (!(localObject1 instanceof LockFreeTaskQueueCore)) {
          break label70;
        }
        if (localObject1 == null) {
          break;
        }
        LockFreeTaskQueueCore localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)localObject1;
        Object localObject2 = localLockFreeTaskQueueCore.removeFirstOrNull();
        if (localObject2 != LockFreeTaskQueueCore.REMOVE_FROZEN) {
          return (Runnable)localObject2;
        }
        _queue$FU.compareAndSet(this, localObject1, localLockFreeTaskQueueCore.next());
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
      if (localObject1 == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
        return null;
      }
    } while (!_queue$FU.compareAndSet(this, localObject1, null));
    if (localObject1 != null) {
      return (Runnable)localObject1;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
  }
  
  private final boolean enqueueImpl(Runnable paramRunnable)
  {
    Object localObject;
    LockFreeTaskQueueCore localLockFreeTaskQueueCore;
    label95:
    label105:
    do
    {
      for (;;)
      {
        localObject = this._queue;
        if (isCompleted()) {
          return false;
        }
        if (localObject == null)
        {
          if (_queue$FU.compareAndSet(this, null, paramRunnable)) {
            return true;
          }
        }
        else
        {
          if (!(localObject instanceof LockFreeTaskQueueCore)) {
            break label105;
          }
          if (localObject == null) {
            break label95;
          }
          localLockFreeTaskQueueCore = (LockFreeTaskQueueCore)localObject;
          int i = localLockFreeTaskQueueCore.addLast(paramRunnable);
          if (i == 0) {
            break;
          }
          if (i != 1)
          {
            if (i == 2) {
              return false;
            }
          }
          else {
            _queue$FU.compareAndSet(this, localObject, localLockFreeTaskQueueCore.next());
          }
        }
      }
      return true;
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
      if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
        return false;
      }
      localLockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
      if (localObject == null) {
        break;
      }
      localLockFreeTaskQueueCore.addLast((Runnable)localObject);
      localLockFreeTaskQueueCore.addLast(paramRunnable);
    } while (!_queue$FU.compareAndSet(this, localObject, localLockFreeTaskQueueCore));
    return true;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
  }
  
  private final boolean isCompleted()
  {
    return this._isCompleted;
  }
  
  private final void rescheduleAllDelayed()
  {
    Object localObject = TimeSourceKt.getTimeSource();
    long l;
    if (localObject != null) {
      l = ((TimeSource)localObject).nanoTime();
    } else {
      l = System.nanoTime();
    }
    for (;;)
    {
      localObject = (DelayedTaskQueue)this._delayed;
      if (localObject == null) {
        break;
      }
      localObject = (DelayedTask)((DelayedTaskQueue)localObject).removeFirstOrNull();
      if (localObject == null) {
        break;
      }
      reschedule(l, (DelayedTask)localObject);
    }
  }
  
  private final int scheduleImpl(long paramLong, DelayedTask paramDelayedTask)
  {
    if (isCompleted()) {
      return 1;
    }
    Object localObject = (DelayedTaskQueue)this._delayed;
    if (localObject == null)
    {
      localObject = (EventLoopImplBase)this;
      _delayed$FU.compareAndSet(localObject, null, new DelayedTaskQueue(paramLong));
      localObject = ((EventLoopImplBase)localObject)._delayed;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      localObject = (DelayedTaskQueue)localObject;
    }
    return paramDelayedTask.scheduleTask(paramLong, (DelayedTaskQueue)localObject, this);
  }
  
  private final void setCompleted(boolean paramBoolean)
  {
    this._isCompleted = paramBoolean;
  }
  
  private final boolean shouldUnpark(DelayedTask paramDelayedTask)
  {
    Object localObject = (DelayedTaskQueue)this._delayed;
    if (localObject != null) {
      localObject = (DelayedTask)((DelayedTaskQueue)localObject).peek();
    } else {
      localObject = null;
    }
    boolean bool;
    if (localObject == paramDelayedTask) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay(this, paramLong, paramContinuation);
  }
  
  public final void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    enqueue(paramRunnable);
  }
  
  public final void enqueue(Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "task");
    if (enqueueImpl(paramRunnable)) {
      unpark();
    } else {
      DefaultExecutor.INSTANCE.enqueue(paramRunnable);
    }
  }
  
  protected long getNextTime()
  {
    if (super.getNextTime() == 0L) {
      return 0L;
    }
    Object localObject = this._queue;
    if (localObject != null)
    {
      if (!(localObject instanceof LockFreeTaskQueueCore)) {
        break label108;
      }
      if (!((LockFreeTaskQueueCore)localObject).isEmpty()) {
        return 0L;
      }
    }
    localObject = (DelayedTaskQueue)this._delayed;
    if (localObject != null)
    {
      localObject = (DelayedTask)((DelayedTaskQueue)localObject).peek();
      if (localObject != null)
      {
        long l1 = ((DelayedTask)localObject).nanoTime;
        localObject = TimeSourceKt.getTimeSource();
        long l2;
        if (localObject != null) {
          l2 = ((TimeSource)localObject).nanoTime();
        } else {
          l2 = System.nanoTime();
        }
        return RangesKt.coerceAtLeast(l1 - l2, 0L);
      }
    }
    return Long.MAX_VALUE;
    label108:
    if (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
      return Long.MAX_VALUE;
    }
    return 0L;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    return Delay.DefaultImpls.invokeOnTimeout(this, paramLong, paramRunnable);
  }
  
  protected boolean isEmpty()
  {
    boolean bool1 = isUnconfinedQueueEmpty();
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    Object localObject = (DelayedTaskQueue)this._delayed;
    if ((localObject != null) && (!((DelayedTaskQueue)localObject).isEmpty())) {
      return false;
    }
    localObject = this._queue;
    if (localObject == null) {}
    do
    {
      bool2 = true;
      break;
      if ((localObject instanceof LockFreeTaskQueueCore))
      {
        bool2 = ((LockFreeTaskQueueCore)localObject).isEmpty();
        break;
      }
    } while (localObject == EventLoop_commonKt.access$getCLOSED_EMPTY$p());
    return bool2;
  }
  
  public long processNextEvent()
  {
    if (processUnconfinedEvent()) {
      return getNextTime();
    }
    DelayedTaskQueue localDelayedTaskQueue = (DelayedTaskQueue)this._delayed;
    if ((localDelayedTaskQueue != null) && (!localDelayedTaskQueue.isEmpty()))
    {
      Object localObject1 = TimeSourceKt.getTimeSource();
      long l;
      if (localObject1 != null) {
        l = ((TimeSource)localObject1).nanoTime();
      } else {
        l = System.nanoTime();
      }
      for (;;)
      {
        try
        {
          ThreadSafeHeapNode localThreadSafeHeapNode = localDelayedTaskQueue.firstImpl();
          DelayedTask localDelayedTask = null;
          localObject1 = null;
          if (localThreadSafeHeapNode != null)
          {
            localDelayedTask = (DelayedTask)localThreadSafeHeapNode;
            boolean bool;
            if (localDelayedTask.timeToExecute(l)) {
              bool = enqueueImpl((Runnable)localDelayedTask);
            } else {
              bool = false;
            }
            if (bool) {
              localObject1 = localDelayedTaskQueue.removeAtImpl(0);
            }
          }
          else
          {
            localObject1 = localDelayedTask;
          }
          if ((DelayedTask)localObject1 == null) {
            localRunnable = dequeue();
          }
        }
        finally {}
      }
    }
    Runnable localRunnable;
    if (localRunnable != null) {
      localRunnable.run();
    }
    return getNextTime();
  }
  
  protected final void resetAll()
  {
    this._queue = null;
    this._delayed = null;
  }
  
  public final void schedule(long paramLong, DelayedTask paramDelayedTask)
  {
    Intrinsics.checkParameterIsNotNull(paramDelayedTask, "delayedTask");
    int i = scheduleImpl(paramLong, paramDelayedTask);
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2) {
          throw ((Throwable)new IllegalStateException("unexpected result".toString()));
        }
      }
      else {
        reschedule(paramLong, paramDelayedTask);
      }
    }
    else if (shouldUnpark(paramDelayedTask)) {
      unpark();
    }
  }
  
  protected final DisposableHandle scheduleInvokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    long l = EventLoop_commonKt.delayToNanos(paramLong);
    if (l < 4611686018427387903L)
    {
      TimeSource localTimeSource = TimeSourceKt.getTimeSource();
      if (localTimeSource != null) {
        paramLong = localTimeSource.nanoTime();
      } else {
        paramLong = System.nanoTime();
      }
      paramRunnable = new DelayedRunnableTask(l + paramLong, paramRunnable);
      schedule(paramLong, (DelayedTask)paramRunnable);
      paramRunnable = (DisposableHandle)paramRunnable;
    }
    else
    {
      paramRunnable = (DisposableHandle)NonDisposableHandle.INSTANCE;
    }
    return paramRunnable;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
    long l = EventLoop_commonKt.delayToNanos(paramLong);
    if (l < 4611686018427387903L)
    {
      Object localObject = TimeSourceKt.getTimeSource();
      if (localObject != null) {
        paramLong = ((TimeSource)localObject).nanoTime();
      } else {
        paramLong = System.nanoTime();
      }
      localObject = new DelayedResumeTask(l + paramLong, paramCancellableContinuation);
      CancellableContinuationKt.disposeOnCancellation(paramCancellableContinuation, (DisposableHandle)localObject);
      schedule(paramLong, (DelayedTask)localObject);
    }
  }
  
  protected void shutdown()
  {
    ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
    setCompleted(true);
    closeQueue();
    while (processNextEvent() <= 0L) {}
    rescheduleAllDelayed();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\020\016\n\000\b?\004\030\0002\0020\001B\033\022\006\020\002\032\0020\003\022\f\020\004\032\b\022\004\022\0020\0060\005?\006\002\020\007J\b\020\b\032\0020\006H\026J\b\020\t\032\0020\nH\026R\024\020\004\032\b\022\004\022\0020\0060\005X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private final class DelayedResumeTask
    extends EventLoopImplBase.DelayedTask
  {
    private final CancellableContinuation<Unit> cont;
    
    public DelayedResumeTask(CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      super();
      this.cont = localObject;
    }
    
    public void run()
    {
      this.cont.resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(super.toString());
      localStringBuilder.append(this.cont.toString());
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\031\022\006\020\002\032\0020\003\022\n\020\004\032\0060\005j\002`\006?\006\002\020\007J\b\020\b\032\0020\tH\026J\b\020\n\032\0020\013H\026R\022\020\004\032\0060\005j\002`\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class DelayedRunnableTask
    extends EventLoopImplBase.DelayedTask
  {
    private final Runnable block;
    
    public DelayedRunnableTask(long paramLong, Runnable paramRunnable)
    {
      super();
      this.block = paramRunnable;
    }
    
    public void run()
    {
      this.block.run();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(super.toString());
      localStringBuilder.append(this.block.toString());
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\017\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\006\n\002\020\b\n\002\b\007\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\000\b \030\0002\0060\001j\002`\0022\b\022\004\022\0020\0000\0032\0020\0042\0020\005B\r\022\006\020\006\032\0020\007?\006\002\020\bJ\021\020\030\032\0020\0232\006\020\031\032\0020\000H?\002J\006\020\032\032\0020\033J\036\020\034\032\0020\0232\006\020\035\032\0020\0072\006\020\036\032\0020\0372\006\020 \032\0020!J\016\020\"\032\0020#2\006\020\035\032\0020\007J\b\020$\032\0020%H\026R\020\020\t\032\004\030\0010\nX?\016?\006\002\n\000R0\020\r\032\b\022\002\b\003\030\0010\f2\f\020\013\032\b\022\002\b\003\030\0010\f8V@VX?\016?\006\f\032\004\b\016\020\017\"\004\b\020\020\021R\032\020\022\032\0020\023X?\016?\006\016\n\000\032\004\b\024\020\025\"\004\b\026\020\027R\022\020\006\032\0020\0078\006@\006X?\016?\006\002\n\000?\006&"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static abstract class DelayedTask
    implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode
  {
    private Object _heap;
    private int index;
    public long nanoTime;
    
    public DelayedTask(long paramLong)
    {
      this.nanoTime = paramLong;
      this.index = -1;
    }
    
    public int compareTo(DelayedTask paramDelayedTask)
    {
      Intrinsics.checkParameterIsNotNull(paramDelayedTask, "other");
      boolean bool = this.nanoTime - paramDelayedTask.nanoTime < 0L;
      int i;
      if (bool) {
        bool = true;
      } else if (bool) {
        i = -1;
      } else {
        i = 0;
      }
      return i;
    }
    
    public final void dispose()
    {
      try
      {
        Object localObject1 = this._heap;
        Object localObject2 = EventLoop_commonKt.access$getDISPOSED_TASK$p();
        if (localObject1 == localObject2) {
          return;
        }
        localObject2 = localObject1;
        if (!(localObject1 instanceof EventLoopImplBase.DelayedTaskQueue)) {
          localObject2 = null;
        }
        localObject2 = (EventLoopImplBase.DelayedTaskQueue)localObject2;
        if (localObject2 != null) {
          ((EventLoopImplBase.DelayedTaskQueue)localObject2).remove((ThreadSafeHeapNode)this);
        }
        this._heap = EventLoop_commonKt.access$getDISPOSED_TASK$p();
        return;
      }
      finally {}
    }
    
    public ThreadSafeHeap<?> getHeap()
    {
      Object localObject1 = this._heap;
      Object localObject2 = localObject1;
      if (!(localObject1 instanceof ThreadSafeHeap)) {
        localObject2 = null;
      }
      return (ThreadSafeHeap)localObject2;
    }
    
    public int getIndex()
    {
      return this.index;
    }
    
    /* Error */
    public final int scheduleTask(long paramLong, EventLoopImplBase.DelayedTaskQueue paramDelayedTaskQueue, EventLoopImplBase paramEventLoopImplBase)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_3
      //   3: ldc 103
      //   5: invokestatic 83	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
      //   8: aload 4
      //   10: ldc 104
      //   12: invokestatic 83	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
      //   15: aload_0
      //   16: getfield 85	kotlinx/coroutines/EventLoopImplBase$DelayedTask:_heap	Ljava/lang/Object;
      //   19: astore 5
      //   21: invokestatic 91	kotlinx/coroutines/EventLoop_commonKt:access$getDISPOSED_TASK$p	()Lkotlinx/coroutines/internal/Symbol;
      //   24: astore 6
      //   26: aload 5
      //   28: aload 6
      //   30: if_acmpne +7 -> 37
      //   33: aload_0
      //   34: monitorexit
      //   35: iconst_2
      //   36: ireturn
      //   37: aload_0
      //   38: checkcast 13	kotlinx/coroutines/internal/ThreadSafeHeapNode
      //   41: astore 5
      //   43: aload_3
      //   44: monitorenter
      //   45: aload_3
      //   46: invokevirtual 108	kotlinx/coroutines/internal/ThreadSafeHeap:firstImpl	()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      //   49: checkcast 2	kotlinx/coroutines/EventLoopImplBase$DelayedTask
      //   52: astore 6
      //   54: aload 4
      //   56: invokestatic 112	kotlinx/coroutines/EventLoopImplBase:access$isCompleted$p	(Lkotlinx/coroutines/EventLoopImplBase;)Z
      //   59: istore 7
      //   61: iload 7
      //   63: ifeq +9 -> 72
      //   66: aload_3
      //   67: monitorexit
      //   68: aload_0
      //   69: monitorexit
      //   70: iconst_1
      //   71: ireturn
      //   72: aload 6
      //   74: ifnonnull +11 -> 85
      //   77: aload_3
      //   78: lload_1
      //   79: putfield 115	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   82: goto +41 -> 123
      //   85: aload 6
      //   87: getfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   90: lstore 8
      //   92: lload 8
      //   94: lload_1
      //   95: lsub
      //   96: lconst_0
      //   97: lcmp
      //   98: iflt +6 -> 104
      //   101: goto +6 -> 107
      //   104: lload 8
      //   106: lstore_1
      //   107: lload_1
      //   108: aload_3
      //   109: getfield 115	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   112: lsub
      //   113: lconst_0
      //   114: lcmp
      //   115: ifle +8 -> 123
      //   118: aload_3
      //   119: lload_1
      //   120: putfield 115	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   123: aload_0
      //   124: getfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   127: aload_3
      //   128: getfield 115	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   131: lsub
      //   132: lconst_0
      //   133: lcmp
      //   134: ifge +11 -> 145
      //   137: aload_0
      //   138: aload_3
      //   139: getfield 115	kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue:timeNow	J
      //   142: putfield 69	kotlinx/coroutines/EventLoopImplBase$DelayedTask:nanoTime	J
      //   145: aload_3
      //   146: aload 5
      //   148: invokevirtual 119	kotlinx/coroutines/internal/ThreadSafeHeap:addImpl	(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V
      //   151: aload_3
      //   152: monitorexit
      //   153: aload_0
      //   154: monitorexit
      //   155: iconst_0
      //   156: ireturn
      //   157: astore 4
      //   159: aload_3
      //   160: monitorexit
      //   161: aload 4
      //   163: athrow
      //   164: astore_3
      //   165: aload_0
      //   166: monitorexit
      //   167: aload_3
      //   168: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	169	0	this	DelayedTask
      //   0	169	1	paramLong	long
      //   0	169	3	paramDelayedTaskQueue	EventLoopImplBase.DelayedTaskQueue
      //   0	169	4	paramEventLoopImplBase	EventLoopImplBase
      //   19	128	5	localObject1	Object
      //   24	62	6	localObject2	Object
      //   59	3	7	bool	boolean
      //   90	15	8	l	long
      // Exception table:
      //   from	to	target	type
      //   45	61	157	finally
      //   77	82	157	finally
      //   85	92	157	finally
      //   107	123	157	finally
      //   123	145	157	finally
      //   145	151	157	finally
      //   2	26	164	finally
      //   37	45	164	finally
      //   66	68	164	finally
      //   151	153	164	finally
      //   159	164	164	finally
    }
    
    public void setHeap(ThreadSafeHeap<?> paramThreadSafeHeap)
    {
      int i;
      if (this._heap != EventLoop_commonKt.access$getDISPOSED_TASK$p()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this._heap = paramThreadSafeHeap;
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    public void setIndex(int paramInt)
    {
      this.index = paramInt;
    }
    
    public final boolean timeToExecute(long paramLong)
    {
      boolean bool;
      if (paramLong - this.nanoTime >= 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Delayed[nanos=");
      localStringBuilder.append(this.nanoTime);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\002\b\002\b\000\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004?\006\002\020\005R\022\020\003\032\0020\0048\006@\006X?\016?\006\002\n\000?\006\006"}, d2={"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class DelayedTaskQueue
    extends ThreadSafeHeap<EventLoopImplBase.DelayedTask>
  {
    public long timeNow;
    
    public DelayedTaskQueue(long paramLong)
    {
      this.timeNow = paramLong;
    }
  }
}
