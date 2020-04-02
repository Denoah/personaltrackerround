package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ConcurrentKt;

@Metadata(bv={1, 0, 3}, d1={"\000f\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b \030\0002\0020\0012\0020\002B\005?\006\002\020\003J\b\020\006\032\0020\007H\026J\034\020\b\032\0020\0072\006\020\t\032\0020\n2\n\020\013\032\0060\fj\002`\rH\026J\023\020\016\032\0020\0052\b\020\017\032\004\030\0010\020H?\002J\b\020\021\032\0020\022H\026J\r\020\023\032\0020\007H\000?\006\002\b\024J\034\020\025\032\0020\0262\006\020\027\032\0020\0302\n\020\013\032\0060\fj\002`\rH\026J*\020\031\032\b\022\002\b\003\030\0010\0322\n\020\013\032\0060\fj\002`\r2\006\020\033\032\0020\0302\006\020\034\032\0020\035H\002J\036\020\036\032\0020\0072\006\020\027\032\0020\0302\f\020\037\032\b\022\004\022\0020\0070 H\026J\b\020!\032\0020\"H\026R\016\020\004\032\0020\005X?\016?\006\002\n\000?\006#"}, d2={"Lkotlinx/coroutines/ExecutorCoroutineDispatcherBase;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "()V", "removesFutureOnCancellation", "", "close", "", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "other", "", "hashCode", "", "initFutureCancellation", "initFutureCancellation$kotlinx_coroutines_core", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "scheduleBlock", "Ljava/util/concurrent/ScheduledFuture;", "time", "unit", "Ljava/util/concurrent/TimeUnit;", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class ExecutorCoroutineDispatcherBase
  extends ExecutorCoroutineDispatcher
  implements Delay
{
  private boolean removesFutureOnCancellation;
  
  public ExecutorCoroutineDispatcherBase() {}
  
  private final ScheduledFuture<?> scheduleBlock(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
  {
    localObject1 = null;
    try
    {
      Object localObject2 = getExecutor();
      localObject3 = localObject2;
      if (!(localObject2 instanceof ScheduledExecutorService)) {
        localObject3 = null;
      }
      localObject2 = (ScheduledExecutorService)localObject3;
      localObject3 = localObject1;
      if (localObject2 != null) {
        localObject3 = ((ScheduledExecutorService)localObject2).schedule(paramRunnable, paramLong, paramTimeUnit);
      }
    }
    catch (RejectedExecutionException paramRunnable)
    {
      for (;;)
      {
        Object localObject3 = localObject1;
      }
    }
    return localObject3;
  }
  
  public void close()
  {
    Executor localExecutor = getExecutor();
    Object localObject = localExecutor;
    if (!(localExecutor instanceof ExecutorService)) {
      localObject = null;
    }
    localObject = (ExecutorService)localObject;
    if (localObject != null) {
      ((ExecutorService)localObject).shutdown();
    }
  }
  
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay(this, paramLong, paramContinuation);
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    try
    {
      Executor localExecutor = getExecutor();
      paramCoroutineContext = TimeSourceKt.getTimeSource();
      if (paramCoroutineContext != null)
      {
        paramCoroutineContext = paramCoroutineContext.wrapTask(paramRunnable);
        if (paramCoroutineContext != null) {}
      }
      else
      {
        paramCoroutineContext = paramRunnable;
      }
      localExecutor.execute(paramCoroutineContext);
    }
    catch (RejectedExecutionException paramCoroutineContext)
    {
      paramCoroutineContext = TimeSourceKt.getTimeSource();
      if (paramCoroutineContext != null) {
        paramCoroutineContext.unTrackTask();
      }
      DefaultExecutor.INSTANCE.enqueue(paramRunnable);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ExecutorCoroutineDispatcherBase)) && (((ExecutorCoroutineDispatcherBase)paramObject).getExecutor() == getExecutor())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return System.identityHashCode(getExecutor());
  }
  
  public final void initFutureCancellation$kotlinx_coroutines_core()
  {
    this.removesFutureOnCancellation = ConcurrentKt.removeFutureOnCancel(getExecutor());
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    ScheduledFuture localScheduledFuture;
    if (this.removesFutureOnCancellation) {
      localScheduledFuture = scheduleBlock(paramRunnable, paramLong, TimeUnit.MILLISECONDS);
    } else {
      localScheduledFuture = null;
    }
    if (localScheduledFuture != null) {
      paramRunnable = (DisposableHandle)new DisposableFutureHandle((Future)localScheduledFuture);
    } else {
      paramRunnable = DefaultExecutor.INSTANCE.invokeOnTimeout(paramLong, paramRunnable);
    }
    return paramRunnable;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
    ScheduledFuture localScheduledFuture;
    if (this.removesFutureOnCancellation) {
      localScheduledFuture = scheduleBlock((Runnable)new ResumeUndispatchedRunnable((CoroutineDispatcher)this, paramCancellableContinuation), paramLong, TimeUnit.MILLISECONDS);
    } else {
      localScheduledFuture = null;
    }
    if (localScheduledFuture != null)
    {
      JobKt.cancelFutureOnCancellation(paramCancellableContinuation, (Future)localScheduledFuture);
      return;
    }
    DefaultExecutor.INSTANCE.scheduleResumeAfterDelay(paramLong, paramCancellableContinuation);
  }
  
  public String toString()
  {
    return getExecutor().toString();
  }
}
