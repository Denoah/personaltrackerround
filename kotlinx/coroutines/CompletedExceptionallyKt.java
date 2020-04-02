package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\0324\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\b\020\003\032\004\030\0010\0042\f\020\005\032\b\022\004\022\002H\0020\006H\000?\001\000?\006\002\020\007\032\"\020\b\032\004\030\0010\004\"\004\b\000\020\002*\b\022\004\022\002H\0020\001H\000?\001\000?\006\002\020\t\032.\020\b\032\004\030\0010\004\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\n\020\n\032\006\022\002\b\0030\013H\000?\001\000?\006\002\020\f?\002\004\n\002\b\031?\006\r"}, d2={"recoverResult", "Lkotlin/Result;", "T", "state", "", "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toState", "(Ljava/lang/Object;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CompletedExceptionallyKt
{
  public static final <T> Object recoverResult(Object paramObject, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramContinuation, "uCont");
    if ((paramObject instanceof CompletedExceptionally))
    {
      Result.Companion localCompanion = Result.Companion;
      paramObject = Result.constructor-impl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(((CompletedExceptionally)paramObject).cause, paramContinuation)));
    }
    else
    {
      paramContinuation = Result.Companion;
      paramObject = Result.constructor-impl(paramObject);
    }
    return paramObject;
  }
  
  public static final <T> Object toState(Object paramObject)
  {
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramObject = new CompletedExceptionally(localThrowable, false, 2, null);
    }
    return paramObject;
  }
  
  public static final <T> Object toState(Object paramObject, CancellableContinuation<?> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "caller");
    Throwable localThrowable = Result.exceptionOrNull-impl(paramObject);
    if (localThrowable != null) {
      paramObject = new CompletedExceptionally(StackTraceRecoveryKt.recoverStackTrace(localThrowable, (Continuation)paramCancellableContinuation), false, 2, null);
    }
    return paramObject;
  }
}
