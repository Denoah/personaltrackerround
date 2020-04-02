package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\020\b\n\002\b\007\n\002\020\013\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\003\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\032 \020\f\032\0020\r\"\004\b\000\020\016*\b\022\004\022\002H\0160\0172\006\020\020\032\0020\001H\000\032.\020\021\032\0020\r\"\004\b\000\020\016*\b\022\004\022\002H\0160\0172\f\020\022\032\b\022\004\022\002H\0160\0232\006\020\024\032\0020\001H\000\032\020\020\025\032\0020\r*\006\022\002\b\0030\017H\002\032\031\020\026\032\0020\r*\006\022\002\b\0030\0232\006\020\027\032\0020\030H?\b\032'\020\031\032\0020\r*\006\022\002\b\0030\0172\006\020\032\032\0020\0332\f\020\034\032\b\022\004\022\0020\r0\035H?\b\"\026\020\000\032\0020\0018\000X?T?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\000X?T?\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0018\000X?T?\006\b\n\000\022\004\b\007\020\003\"\030\020\b\032\0020\t*\0020\0018@X?\004?\006\006\032\004\b\b\020\n\"\030\020\013\032\0020\t*\0020\0018@X?\004?\006\006\032\004\b\013\020\n?\006\036"}, d2={"MODE_ATOMIC_DEFAULT", "", "MODE_ATOMIC_DEFAULT$annotations", "()V", "MODE_CANCELLABLE", "MODE_CANCELLABLE$annotations", "MODE_UNDISPATCHED", "MODE_UNDISPATCHED$annotations", "isCancellableMode", "", "(I)Z", "isDispatchedMode", "dispatch", "", "T", "Lkotlinx/coroutines/DispatchedTask;", "mode", "resume", "delegate", "Lkotlin/coroutines/Continuation;", "useMode", "resumeUnconfined", "resumeWithStackTrace", "exception", "", "runUnconfinedEventLoop", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "block", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DispatchedTaskKt
{
  public static final int MODE_ATOMIC_DEFAULT = 0;
  public static final int MODE_CANCELLABLE = 1;
  public static final int MODE_UNDISPATCHED = 2;
  
  public static final <T> void dispatch(DispatchedTask<? super T> paramDispatchedTask, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramDispatchedTask, "$this$dispatch");
    Object localObject = paramDispatchedTask.getDelegate$kotlinx_coroutines_core();
    if ((isDispatchedMode(paramInt)) && ((localObject instanceof DispatchedContinuation)) && (isCancellableMode(paramInt) == isCancellableMode(paramDispatchedTask.resumeMode)))
    {
      CoroutineDispatcher localCoroutineDispatcher = ((DispatchedContinuation)localObject).dispatcher;
      localObject = ((Continuation)localObject).getContext();
      if (localCoroutineDispatcher.isDispatchNeeded((CoroutineContext)localObject)) {
        localCoroutineDispatcher.dispatch((CoroutineContext)localObject, (Runnable)paramDispatchedTask);
      } else {
        resumeUnconfined(paramDispatchedTask);
      }
    }
    else
    {
      resume(paramDispatchedTask, (Continuation)localObject, paramInt);
    }
  }
  
  public static final boolean isCancellableMode(int paramInt)
  {
    boolean bool = true;
    if (paramInt != 1) {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isDispatchedMode(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2 = bool1;
    if (paramInt != 0) {
      if (paramInt == 1) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }
    }
    return bool2;
  }
  
  public static final <T> void resume(DispatchedTask<? super T> paramDispatchedTask, Continuation<? super T> paramContinuation, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramDispatchedTask, "$this$resume");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "delegate");
    Object localObject = paramDispatchedTask.takeState$kotlinx_coroutines_core();
    paramDispatchedTask = paramDispatchedTask.getExceptionalResult$kotlinx_coroutines_core(localObject);
    if (paramDispatchedTask != null) {
      paramDispatchedTask = StackTraceRecoveryKt.recoverStackTrace(paramDispatchedTask, paramContinuation);
    } else {
      paramDispatchedTask = null;
    }
    if (paramDispatchedTask != null)
    {
      localObject = Result.Companion;
      paramDispatchedTask = Result.constructor-impl(ResultKt.createFailure(paramDispatchedTask));
    }
    else
    {
      paramDispatchedTask = Result.Companion;
      paramDispatchedTask = Result.constructor-impl(localObject);
    }
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        DispatchedContinuation localDispatchedContinuation;
        if (paramInt == 2)
        {
          localDispatchedContinuation = (DispatchedContinuation)paramContinuation;
          localObject = localDispatchedContinuation.getContext();
          paramContinuation = ThreadContextKt.updateThreadContext((CoroutineContext)localObject, localDispatchedContinuation.countOrElement);
        }
        try
        {
          localDispatchedContinuation.continuation.resumeWith(paramDispatchedTask);
          paramDispatchedTask = Unit.INSTANCE;
          ThreadContextKt.restoreThreadContext((CoroutineContext)localObject, paramContinuation);
        }
        finally
        {
          ThreadContextKt.restoreThreadContext((CoroutineContext)localObject, paramContinuation);
        }
        paramDispatchedTask.append("Invalid mode ");
        paramDispatchedTask.append(paramInt);
        throw ((Throwable)new IllegalStateException(paramDispatchedTask.toString().toString()));
      }
      else
      {
        DispatchedContinuationKt.resumeCancellableWith(paramContinuation, paramDispatchedTask);
      }
    }
    else {
      paramContinuation.resumeWith(paramDispatchedTask);
    }
  }
  
  private static final void resumeUnconfined(DispatchedTask<?> paramDispatchedTask)
  {
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    if (localEventLoop.isUnconfinedLoopActive())
    {
      localEventLoop.dispatchUnconfined(paramDispatchedTask);
    }
    else
    {
      localEventLoop.incrementUseCount(true);
      try
      {
        resume(paramDispatchedTask, paramDispatchedTask.getDelegate$kotlinx_coroutines_core(), 2);
        for (;;)
        {
          boolean bool = localEventLoop.processUnconfinedEvent();
          if (!bool) {
            break;
          }
        }
      }
      finally {}
    }
    try
    {
      paramDispatchedTask.handleFatalException$kotlinx_coroutines_core(localThrowable, null);
      return;
    }
    finally
    {
      localEventLoop.decrementUseCount(true);
    }
  }
  
  public static final void resumeWithStackTrace(Continuation<?> paramContinuation, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "$this$resumeWithStackTrace");
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    Result.Companion localCompanion = Result.Companion;
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(paramThrowable, paramContinuation))));
  }
  
  public static final void runUnconfinedEventLoop(DispatchedTask<?> paramDispatchedTask, EventLoop paramEventLoop, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramDispatchedTask, "$this$runUnconfinedEventLoop");
    Intrinsics.checkParameterIsNotNull(paramEventLoop, "eventLoop");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    paramEventLoop.incrementUseCount(true);
    try
    {
      paramFunction0.invoke();
      boolean bool;
      do
      {
        bool = paramEventLoop.processUnconfinedEvent();
      } while (bool);
      InlineMarker.finallyStart(1);
    }
    finally {}
    try
    {
      paramDispatchedTask.handleFatalException$kotlinx_coroutines_core(paramFunction0, null);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
    }
  }
}
