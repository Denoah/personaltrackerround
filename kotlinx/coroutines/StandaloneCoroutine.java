package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\003\n\000\b\022\030\0002\b\022\004\022\0020\0020\001B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006?\006\002\020\007J\020\020\b\032\0020\0062\006\020\t\032\0020\nH\024?\006\013"}, d2={"Lkotlinx/coroutines/StandaloneCoroutine;", "Lkotlinx/coroutines/AbstractCoroutine;", "", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Z)V", "handleJobException", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
class StandaloneCoroutine
  extends AbstractCoroutine<Unit>
{
  public StandaloneCoroutine(CoroutineContext paramCoroutineContext, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramBoolean);
  }
  
  protected boolean handleJobException(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    return true;
  }
}
