package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\b\002\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006?\006\002\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026?\006\f"}, d2={"Lkotlinx/coroutines/SupervisorCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class SupervisorCoroutine<T>
  extends ScopeCoroutine<T>
{
  public SupervisorCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, paramContinuation);
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    return false;
  }
}
