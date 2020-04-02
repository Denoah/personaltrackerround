package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\000\032\021\020\000\032\0020\0012\006\020\002\032\0020\001H?\b\032\030\020\003\032\0020\004*\0020\0052\n\020\006\032\006\022\002\b\0030\007H\000?\006\b"}, d2={"checkIndexOverflow", "", "index", "checkOwnership", "", "Lkotlinx/coroutines/flow/internal/AbortFlowException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class FlowExceptions_commonKt
{
  public static final int checkIndexOverflow(int paramInt)
  {
    if (paramInt >= 0) {
      return paramInt;
    }
    throw ((Throwable)new ArithmeticException("Index overflow has happened"));
  }
  
  public static final void checkOwnership(AbortFlowException paramAbortFlowException, FlowCollector<?> paramFlowCollector)
  {
    Intrinsics.checkParameterIsNotNull(paramAbortFlowException, "$this$checkOwnership");
    Intrinsics.checkParameterIsNotNull(paramFlowCollector, "owner");
    if (paramAbortFlowException.getOwner() == paramFlowCollector) {
      return;
    }
    throw ((Throwable)paramAbortFlowException);
  }
}
