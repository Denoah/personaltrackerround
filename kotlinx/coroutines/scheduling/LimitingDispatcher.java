package kotlinx.coroutines.scheduling;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000Z\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\b\002\030\0002\0020.2\0020/2\0020 B\037\022\006\020\002\032\0020\001\022\006\020\004\032\0020\003\022\006\020\006\032\0020\005?\006\004\b\007\020\bJ\017\020\n\032\0020\tH\026?\006\004\b\n\020\013J\017\020\f\032\0020\tH\026?\006\004\b\f\020\013J#\020\022\032\0020\t2\006\020\016\032\0020\r2\n\020\021\032\0060\017j\002`\020H\026?\006\004\b\022\020\023J#\020\022\032\0020\t2\n\020\021\032\0060\017j\002`\0202\006\020\025\032\0020\024H\002?\006\004\b\022\020\026J\033\020\030\032\0020\t2\n\020\027\032\0060\017j\002`\020H\026?\006\004\b\030\020\031J\017\020\033\032\0020\032H\026?\006\004\b\033\020\034R\031\020\002\032\0020\0018\006@\006?\006\f\n\004\b\002\020\035\032\004\b\036\020\037R\026\020#\032\0020 8V@\026X?\004?\006\006\032\004\b!\020\"R\031\020\004\032\0020\0038\006@\006?\006\f\n\004\b\004\020$\032\004\b%\020&R \020(\032\f\022\b\022\0060\017j\002`\0200'8\002@\002X?\004?\006\006\n\004\b(\020)R\034\020\006\032\0020\0058\026@\026X?\004?\006\f\n\004\b\006\020*\032\004\b+\020,?\006-"}, d2={"Lkotlinx/coroutines/scheduling/LimitingDispatcher;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "dispatcher", "", "parallelism", "Lkotlinx/coroutines/scheduling/TaskMode;", "taskMode", "<init>", "(Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;ILkotlinx/coroutines/scheduling/TaskMode;)V", "", "afterTask", "()V", "close", "Lkotlin/coroutines/CoroutineContext;", "context", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "", "fair", "(Ljava/lang/Runnable;Z)V", "command", "execute", "(Ljava/lang/Runnable;)V", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "getDispatcher", "()Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "executor", "I", "getParallelism", "()I", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "queue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lkotlinx/coroutines/scheduling/TaskMode;", "getTaskMode", "()Lkotlinx/coroutines/scheduling/TaskMode;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/scheduling/TaskContext;"}, k=1, mv={1, 1, 16})
final class LimitingDispatcher
  extends ExecutorCoroutineDispatcher
  implements TaskContext, Executor
{
  private static final AtomicIntegerFieldUpdater inFlightTasks$FU = AtomicIntegerFieldUpdater.newUpdater(LimitingDispatcher.class, "inFlightTasks");
  private final ExperimentalCoroutineDispatcher dispatcher;
  private volatile int inFlightTasks;
  private final int parallelism;
  private final ConcurrentLinkedQueue<Runnable> queue;
  private final TaskMode taskMode;
  
  public LimitingDispatcher(ExperimentalCoroutineDispatcher paramExperimentalCoroutineDispatcher, int paramInt, TaskMode paramTaskMode)
  {
    this.dispatcher = paramExperimentalCoroutineDispatcher;
    this.parallelism = paramInt;
    this.taskMode = paramTaskMode;
    this.queue = new ConcurrentLinkedQueue();
    this.inFlightTasks = 0;
  }
  
  private final void dispatch(Runnable paramRunnable, boolean paramBoolean)
  {
    do
    {
      if (inFlightTasks$FU.incrementAndGet(this) <= this.parallelism)
      {
        this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(paramRunnable, (TaskContext)this, paramBoolean);
        return;
      }
      this.queue.add(paramRunnable);
      if (inFlightTasks$FU.decrementAndGet(this) >= this.parallelism) {
        return;
      }
      paramRunnable = (Runnable)this.queue.poll();
    } while (paramRunnable != null);
  }
  
  public void afterTask()
  {
    Runnable localRunnable = (Runnable)this.queue.poll();
    if (localRunnable != null)
    {
      this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(localRunnable, (TaskContext)this, true);
      return;
    }
    inFlightTasks$FU.decrementAndGet(this);
    localRunnable = (Runnable)this.queue.poll();
    if (localRunnable != null) {
      dispatch(localRunnable, true);
    }
  }
  
  public void close()
  {
    throw ((Throwable)new IllegalStateException("Close cannot be invoked on LimitingBlockingDispatcher".toString()));
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    dispatch(paramRunnable, false);
  }
  
  public void execute(Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "command");
    dispatch(paramRunnable, false);
  }
  
  public final ExperimentalCoroutineDispatcher getDispatcher()
  {
    return this.dispatcher;
  }
  
  public Executor getExecutor()
  {
    return (Executor)this;
  }
  
  public final int getParallelism()
  {
    return this.parallelism;
  }
  
  public TaskMode getTaskMode()
  {
    return this.taskMode;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append("[dispatcher = ");
    localStringBuilder.append(this.dispatcher);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
