package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\032\021\020\000\032\0020\001H?@?\001\000?\006\002\020\002\032\f\020\003\032\0020\001*\0020\004H\000?\002\004\n\002\b\031?\006\005"}, d2={"yield", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkCompletion", "Lkotlin/coroutines/CoroutineContext;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class YieldKt
{
  public static final void checkCompletion(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "$this$checkCompletion");
    paramCoroutineContext = (Job)paramCoroutineContext.get((CoroutineContext.Key)Job.Key);
    if ((paramCoroutineContext != null) && (!paramCoroutineContext.isActive())) {
      throw ((Throwable)paramCoroutineContext.getCancellationException());
    }
  }
  
  public static final Object yield(Continuation<? super Unit> paramContinuation)
  {
    CoroutineContext localCoroutineContext = paramContinuation.getContext();
    checkCompletion(localCoroutineContext);
    Object localObject1 = IntrinsicsKt.intercepted(paramContinuation);
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof DispatchedContinuation)) {
      localObject2 = null;
    }
    localObject2 = (DispatchedContinuation)localObject2;
    if (localObject2 != null)
    {
      if (((DispatchedContinuation)localObject2).dispatcher.isDispatchNeeded(localCoroutineContext))
      {
        ((DispatchedContinuation)localObject2).dispatchYield$kotlinx_coroutines_core(localCoroutineContext, Unit.INSTANCE);
      }
      else
      {
        localObject1 = new YieldContext();
        ((DispatchedContinuation)localObject2).dispatchYield$kotlinx_coroutines_core(localCoroutineContext.plus((CoroutineContext)localObject1), Unit.INSTANCE);
        if (((YieldContext)localObject1).dispatcherWasUnconfined)
        {
          if (DispatchedContinuationKt.yieldUndispatched((DispatchedContinuation)localObject2))
          {
            localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            break label122;
          }
          localObject2 = Unit.INSTANCE;
          break label122;
        }
      }
      localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
    else
    {
      localObject2 = Unit.INSTANCE;
    }
    label122:
    if (localObject2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    if (localObject2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return localObject2;
    }
    return Unit.INSTANCE;
  }
}
