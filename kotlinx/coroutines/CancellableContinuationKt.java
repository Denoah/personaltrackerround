package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\000\032\"\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\004H\000\0323\020\005\032\002H\002\"\004\b\000\020\0022\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007H?H?\001\000?\006\002\020\n\032=\020\005\032\002H\002\"\004\b\000\020\0022\b\b\002\020\013\032\0020\f2\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007H?H?\001\000?\006\002\020\r\0323\020\016\032\002H\002\"\004\b\000\020\0022\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007H?H?\001\000?\006\002\020\n\0323\020\017\032\002H\002\"\004\b\000\020\0022\032\b\004\020\006\032\024\022\n\022\b\022\004\022\002H\0020\b\022\004\022\0020\t0\007H?H?\001\000?\006\002\020\n\032\030\020\020\032\0020\t*\006\022\002\b\0030\b2\006\020\021\032\0020\022H\007\032\030\020\023\032\0020\t*\006\022\002\b\0030\b2\006\020\024\032\0020\025H\000?\002\004\n\002\b\031?\006\026"}, d2={"getOrCreateCancellableContinuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "delegate", "Lkotlin/coroutines/Continuation;", "suspendAtomicCancellableCoroutine", "block", "Lkotlin/Function1;", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "holdCancellability", "", "(ZLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suspendAtomicCancellableCoroutineReusable", "suspendCancellableCoroutine", "disposeOnCancellation", "handle", "Lkotlinx/coroutines/DisposableHandle;", "removeOnCancellation", "node", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CancellableContinuationKt
{
  public static final void disposeOnCancellation(CancellableContinuation<?> paramCancellableContinuation, DisposableHandle paramDisposableHandle)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "$this$disposeOnCancellation");
    Intrinsics.checkParameterIsNotNull(paramDisposableHandle, "handle");
    paramCancellableContinuation.invokeOnCancellation((Function1)new DisposeOnCancel(paramDisposableHandle));
  }
  
  public static final <T> CancellableContinuationImpl<T> getOrCreateCancellableContinuation(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "delegate");
    if (!(paramContinuation instanceof DispatchedContinuation)) {
      return new CancellableContinuationImpl(paramContinuation, 0);
    }
    CancellableContinuationImpl localCancellableContinuationImpl = ((DispatchedContinuation)paramContinuation).claimReusableCancellableContinuation();
    if (localCancellableContinuationImpl != null)
    {
      if (!localCancellableContinuationImpl.resetState$kotlinx_coroutines_core()) {
        localCancellableContinuationImpl = null;
      }
      if (localCancellableContinuationImpl != null) {
        return localCancellableContinuationImpl;
      }
    }
    return new CancellableContinuationImpl(paramContinuation, 0);
  }
  
  public static final void removeOnCancellation(CancellableContinuation<?> paramCancellableContinuation, LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "$this$removeOnCancellation");
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    paramCancellableContinuation.invokeOnCancellation((Function1)new RemoveOnCancel(paramLockFreeLinkedListNode));
  }
  
  public static final <T> Object suspendAtomicCancellableCoroutine(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 0);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  @Deprecated(message="holdCancellability parameter is deprecated and is no longer used", replaceWith=@ReplaceWith(expression="suspendAtomicCancellableCoroutine(block)", imports={}))
  public static final <T> Object suspendAtomicCancellableCoroutine(boolean paramBoolean, Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 0);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  private static final Object suspendAtomicCancellableCoroutine$$forInline(Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 0);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
  
  @Deprecated(message="holdCancellability parameter is deprecated and is no longer used", replaceWith=@ReplaceWith(expression="suspendAtomicCancellableCoroutine(block)", imports={}))
  private static final Object suspendAtomicCancellableCoroutine$$forInline(boolean paramBoolean, Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 0);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
  
  public static final <T> Object suspendAtomicCancellableCoroutineReusable(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  private static final Object suspendAtomicCancellableCoroutineReusable$$forInline(Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(paramContinuation));
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
  
  public static final <T> Object suspendCancellableCoroutine(Function1<? super CancellableContinuation<? super T>, Unit> paramFunction1, Continuation<? super T> paramContinuation)
  {
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction1;
  }
  
  private static final Object suspendCancellableCoroutine$$forInline(Function1 paramFunction1, Continuation paramContinuation)
  {
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    paramFunction1.invoke(localCancellableContinuationImpl);
    paramFunction1 = localCancellableContinuationImpl.getResult();
    if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramFunction1;
  }
}
