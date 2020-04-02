package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\000\n\000\b\002\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006?\006\002\020\007J\022\020\b\032\0020\t2\b\020\n\032\004\030\0010\013H\024?\006\f"}, d2={"Lkotlinx/coroutines/UndispatchedCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "afterResume", "", "state", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class UndispatchedCoroutine<T>
  extends ScopeCoroutine<T>
{
  public UndispatchedCoroutine(CoroutineContext paramCoroutineContext, Continuation<? super T> paramContinuation)
  {
    super(paramCoroutineContext, paramContinuation);
  }
  
  protected void afterResume(Object paramObject)
  {
    Object localObject1 = CompletedExceptionallyKt.recoverResult(paramObject, this.uCont);
    CoroutineContext localCoroutineContext = this.uCont.getContext();
    paramObject = ThreadContextKt.updateThreadContext(localCoroutineContext, null);
    try
    {
      this.uCont.resumeWith(localObject1);
      localObject1 = Unit.INSTANCE;
      return;
    }
    finally
    {
      ThreadContextKt.restoreThreadContext(localCoroutineContext, paramObject);
    }
  }
}
