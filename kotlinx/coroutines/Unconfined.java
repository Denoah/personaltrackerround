package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\034\020\003\032\0020\0042\006\020\005\032\0020\0062\n\020\007\032\0060\bj\002`\tH\026J\020\020\n\032\0020\0132\006\020\005\032\0020\006H\026J\b\020\f\032\0020\rH\026?\006\016"}, d2={"Lkotlinx/coroutines/Unconfined;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "isDispatchNeeded", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class Unconfined
  extends CoroutineDispatcher
{
  public static final Unconfined INSTANCE = new Unconfined();
  
  private Unconfined() {}
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    paramCoroutineContext = (YieldContext)paramCoroutineContext.get((CoroutineContext.Key)YieldContext.Key);
    if (paramCoroutineContext != null)
    {
      paramCoroutineContext.dispatcherWasUnconfined = true;
      return;
    }
    throw ((Throwable)new UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. If you wrap Unconfined dispatcher in your code, make sure you properly delegate isDispatchNeeded and dispatch calls."));
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return false;
  }
  
  public String toString()
  {
    return "Unconfined";
  }
}
