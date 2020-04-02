package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\032\022\020\000\032\0020\0012\n\b\002\020\002\032\004\030\0010\003\032\031\020\004\032\0020\0032\n\b\002\020\002\032\004\030\0010\003H\007?\006\002\b\000\032@\020\005\032\002H\006\"\004\b\000\020\0062'\020\007\032#\b\001\022\004\022\0020\t\022\n\022\b\022\004\022\002H\0060\n\022\006\022\004\030\0010\0130\b?\006\002\b\fH?@?\001\000?\006\002\020\r?\002\004\n\002\b\031?\006\016"}, d2={"SupervisorJob", "Lkotlinx/coroutines/CompletableJob;", "parent", "Lkotlinx/coroutines/Job;", "SupervisorJob0", "supervisorScope", "R", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SupervisorKt
{
  public static final CompletableJob SupervisorJob(Job paramJob)
  {
    return (CompletableJob)new SupervisorJobImpl(paramJob);
  }
  
  public static final <R> Object supervisorScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> paramFunction2, Continuation<? super R> paramContinuation)
  {
    SupervisorCoroutine localSupervisorCoroutine = new SupervisorCoroutine(paramContinuation.getContext(), paramContinuation);
    paramFunction2 = UndispatchedKt.startUndispatchedOrReturn((ScopeCoroutine)localSupervisorCoroutine, localSupervisorCoroutine, paramFunction2);
    if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction2;
  }
}
