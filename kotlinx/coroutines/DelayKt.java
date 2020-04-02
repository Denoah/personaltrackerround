package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\t\n\002\b\002\032\031\020\000\032\0020\0052\006\020\006\032\0020\007H?@?\001\000?\006\002\020\b\"\030\020\000\032\0020\001*\0020\0028@X?\004?\006\006\032\004\b\003\020\004?\002\004\n\002\b\031?\006\t"}, d2={"delay", "Lkotlinx/coroutines/Delay;", "Lkotlin/coroutines/CoroutineContext;", "getDelay", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;", "", "timeMillis", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DelayKt
{
  public static final Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    if (paramLong <= 0L) {
      return Unit.INSTANCE;
    }
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    Object localObject = (CancellableContinuation)localCancellableContinuationImpl;
    getDelay(((CancellableContinuation)localObject).getContext()).scheduleResumeAfterDelay(paramLong, (CancellableContinuation)localObject);
    localObject = localCancellableContinuationImpl.getResult();
    if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return localObject;
  }
  
  public static final Delay getDelay(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "$this$delay");
    CoroutineContext.Element localElement = paramCoroutineContext.get((CoroutineContext.Key)ContinuationInterceptor.Key);
    paramCoroutineContext = localElement;
    if (!(localElement instanceof Delay)) {
      paramCoroutineContext = null;
    }
    paramCoroutineContext = (Delay)paramCoroutineContext;
    if (paramCoroutineContext == null) {
      paramCoroutineContext = DefaultExecutorKt.getDefaultDelay();
    }
    return paramCoroutineContext;
  }
}
