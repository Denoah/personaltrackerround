package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032;\020\006\032\0020\007*\006\022\002\b\0030\b2\b\020\t\032\004\030\0010\n2\006\020\013\032\0020\f2\b\b\002\020\r\032\0020\0072\f\020\016\032\b\022\004\022\0020\0200\017H?\b\032.\020\021\032\0020\020\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232\f\020\024\032\b\022\004\022\002H\0220\025H\007?\001\000?\006\002\020\026\032\022\020\027\032\0020\007*\b\022\004\022\0020\0200\bH\000\"\026\020\000\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\005\020\003?\002\004\n\002\b\031?\006\030"}, d2={"REUSABLE_CLAIMED", "Lkotlinx/coroutines/internal/Symbol;", "REUSABLE_CLAIMED$annotations", "()V", "UNDEFINED", "UNDEFINED$annotations", "executeUnconfined", "", "Lkotlinx/coroutines/DispatchedContinuation;", "contState", "", "mode", "", "doYield", "block", "Lkotlin/Function0;", "", "resumeCancellableWith", "T", "Lkotlin/coroutines/Continuation;", "result", "Lkotlin/Result;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "yieldUndispatched", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DispatchedContinuationKt
{
  public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");
  private static final Symbol UNDEFINED = new Symbol("UNDEFINED");
  
  private static final boolean executeUnconfined(DispatchedContinuation<?> paramDispatchedContinuation, Object paramObject, int paramInt, boolean paramBoolean, Function0<Unit> paramFunction0)
  {
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    boolean bool = false;
    if ((paramBoolean) && (localEventLoop.isUnconfinedQueueEmpty())) {
      return false;
    }
    if (localEventLoop.isUnconfinedLoopActive())
    {
      paramDispatchedContinuation._state = paramObject;
      paramDispatchedContinuation.resumeMode = paramInt;
      localEventLoop.dispatchUnconfined((DispatchedTask)paramDispatchedContinuation);
      paramBoolean = true;
    }
    else
    {
      paramDispatchedContinuation = (DispatchedTask)paramDispatchedContinuation;
      localEventLoop.incrementUseCount(true);
      try
      {
        paramFunction0.invoke();
        do
        {
          paramBoolean = localEventLoop.processUnconfinedEvent();
        } while (paramBoolean);
        InlineMarker.finallyStart(1);
      }
      finally {}
    }
    try
    {
      paramDispatchedContinuation.handleFatalException$kotlinx_coroutines_core(paramObject, null);
      InlineMarker.finallyStart(1);
      localEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
      paramBoolean = bool;
      return paramBoolean;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      localEventLoop.decrementUseCount(true);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final <T> void resumeCancellableWith(Continuation<? super T> paramContinuation, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "$this$resumeCancellableWith");
    Object localObject1;
    if ((paramContinuation instanceof DispatchedContinuation))
    {
      DispatchedContinuation localDispatchedContinuation = (DispatchedContinuation)paramContinuation;
      localObject1 = CompletedExceptionallyKt.toState(paramObject);
      if (localDispatchedContinuation.dispatcher.isDispatchNeeded(localDispatchedContinuation.getContext()))
      {
        localDispatchedContinuation._state = localObject1;
        localDispatchedContinuation.resumeMode = 1;
        localDispatchedContinuation.dispatcher.dispatch(localDispatchedContinuation.getContext(), (Runnable)localDispatchedContinuation);
      }
      else
      {
        paramContinuation = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (paramContinuation.isUnconfinedLoopActive())
        {
          localDispatchedContinuation._state = localObject1;
          localDispatchedContinuation.resumeMode = 1;
          paramContinuation.dispatchUnconfined((DispatchedTask)localDispatchedContinuation);
        }
        else
        {
          localObject1 = (DispatchedTask)localDispatchedContinuation;
          paramContinuation.incrementUseCount(true);
          try
          {
            Object localObject2 = (Job)localDispatchedContinuation.getContext().get((CoroutineContext.Key)Job.Key);
            Object localObject3;
            int i;
            if ((localObject2 != null) && (!((Job)localObject2).isActive()))
            {
              localObject3 = (Throwable)((Job)localObject2).getCancellationException();
              localObject2 = Result.Companion;
              localDispatchedContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject3)));
              i = 1;
            }
            else
            {
              i = 0;
            }
            if (i == 0)
            {
              localObject3 = localDispatchedContinuation.getContext();
              localObject2 = ThreadContextKt.updateThreadContext((CoroutineContext)localObject3, localDispatchedContinuation.countOrElement);
            }
            for (;;)
            {
              boolean bool;
              try
              {
                localDispatchedContinuation.continuation.resumeWith(paramObject);
                paramObject = Unit.INSTANCE;
                ThreadContextKt.restoreThreadContext((CoroutineContext)localObject3, localObject2);
              }
              finally
              {
                ThreadContextKt.restoreThreadContext((CoroutineContext)localObject3, localObject2);
              }
              if (!bool) {
                break;
              }
            }
          }
          finally {}
        }
      }
    }
    else
    {
      try
      {
        ((DispatchedTask)localObject1).handleFatalException$kotlinx_coroutines_core(paramObject, null);
        paramContinuation.decrementUseCount(true);
      }
      finally
      {
        paramContinuation.decrementUseCount(true);
      }
    }
  }
  
  public static final boolean yieldUndispatched(DispatchedContinuation<? super Unit> paramDispatchedContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramDispatchedContinuation, "$this$yieldUndispatched");
    Object localObject = Unit.INSTANCE;
    EventLoop localEventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
    boolean bool1 = localEventLoop.isUnconfinedQueueEmpty();
    boolean bool2 = false;
    if (!bool1) {
      if (localEventLoop.isUnconfinedLoopActive())
      {
        paramDispatchedContinuation._state = localObject;
        paramDispatchedContinuation.resumeMode = 1;
        localEventLoop.dispatchUnconfined((DispatchedTask)paramDispatchedContinuation);
        bool2 = true;
      }
      else
      {
        localObject = (DispatchedTask)paramDispatchedContinuation;
        localEventLoop.incrementUseCount(true);
        try
        {
          paramDispatchedContinuation.run();
          for (;;)
          {
            bool1 = localEventLoop.processUnconfinedEvent();
            if (!bool1) {
              break;
            }
          }
        }
        finally {}
      }
    }
    try
    {
      ((DispatchedTask)localObject).handleFatalException$kotlinx_coroutines_core(paramDispatchedContinuation, null);
      return bool2;
    }
    finally
    {
      localEventLoop.decrementUseCount(true);
    }
  }
}
