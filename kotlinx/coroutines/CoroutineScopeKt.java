package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\000\n\002\020\013\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\016\n\000\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\003\032\016\020\006\032\0020\0022\006\020\007\032\0020\b\032\006\020\t\032\0020\002\032@\020\n\032\002H\013\"\004\b\000\020\0132'\020\f\032#\b\001\022\004\022\0020\002\022\n\022\b\022\004\022\002H\0130\016\022\006\022\004\030\0010\0170\r?\006\002\b\020H?@?\001\000?\006\002\020\021\032\036\020\022\032\0020\023*\0020\0022\006\020\024\032\0020\0252\n\b\002\020\026\032\004\030\0010\027\032\034\020\022\032\0020\023*\0020\0022\020\b\002\020\026\032\n\030\0010\030j\004\030\001`\031\032\n\020\032\032\0020\023*\0020\002\032\025\020\033\032\0020\002*\0020\0022\006\020\007\032\0020\bH?\002\"\033\020\000\032\0020\001*\0020\0028F?\006\f\022\004\b\003\020\004\032\004\b\000\020\005?\002\004\n\002\b\031?\006\034"}, d2={"isActive", "", "Lkotlinx/coroutines/CoroutineScope;", "isActive$annotations", "(Lkotlinx/coroutines/CoroutineScope;)V", "(Lkotlinx/coroutines/CoroutineScope;)Z", "CoroutineScope", "context", "Lkotlin/coroutines/CoroutineContext;", "MainScope", "coroutineScope", "R", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancel", "", "message", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "ensureActive", "plus", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CoroutineScopeKt
{
  public static final CoroutineScope CoroutineScope(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    if (paramCoroutineContext.get((CoroutineContext.Key)Job.Key) == null) {
      paramCoroutineContext = paramCoroutineContext.plus((CoroutineContext)JobKt.Job$default(null, 1, null));
    }
    return (CoroutineScope)new ContextScope(paramCoroutineContext);
  }
  
  public static final CoroutineScope MainScope()
  {
    return (CoroutineScope)new ContextScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus((CoroutineContext)Dispatchers.getMain()));
  }
  
  public static final void cancel(CoroutineScope paramCoroutineScope, String paramString, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$cancel");
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    cancel(paramCoroutineScope, ExceptionsKt.CancellationException(paramString, paramThrowable));
  }
  
  public static final void cancel(CoroutineScope paramCoroutineScope, CancellationException paramCancellationException)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$cancel");
    Job localJob = (Job)paramCoroutineScope.getCoroutineContext().get((CoroutineContext.Key)Job.Key);
    if (localJob != null)
    {
      localJob.cancel(paramCancellationException);
      return;
    }
    paramCancellationException = new StringBuilder();
    paramCancellationException.append("Scope cannot be cancelled because it does not have a job: ");
    paramCancellationException.append(paramCoroutineScope);
    throw ((Throwable)new IllegalStateException(paramCancellationException.toString().toString()));
  }
  
  public static final <R> Object coroutineScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> paramFunction2, Continuation<? super R> paramContinuation)
  {
    ScopeCoroutine localScopeCoroutine = new ScopeCoroutine(paramContinuation.getContext(), paramContinuation);
    paramFunction2 = UndispatchedKt.startUndispatchedOrReturn(localScopeCoroutine, localScopeCoroutine, paramFunction2);
    if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction2;
  }
  
  public static final void ensureActive(CoroutineScope paramCoroutineScope)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$ensureActive");
    JobKt.ensureActive(paramCoroutineScope.getCoroutineContext());
  }
  
  public static final boolean isActive(CoroutineScope paramCoroutineScope)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$isActive");
    paramCoroutineScope = (Job)paramCoroutineScope.getCoroutineContext().get((CoroutineContext.Key)Job.Key);
    boolean bool;
    if (paramCoroutineScope != null) {
      bool = paramCoroutineScope.isActive();
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final CoroutineScope plus(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (CoroutineScope)new ContextScope(paramCoroutineScope.getCoroutineContext().plus(paramCoroutineContext));
  }
}
