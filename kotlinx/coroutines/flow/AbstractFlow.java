package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.flow.internal.SafeCollector;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\b'\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\005?\006\002\020\003J\037\020\004\032\0020\0052\f\020\006\032\b\022\004\022\0028\0000\007H?@?\001\000?\006\002\020\bJ\037\020\t\032\0020\0052\f\020\006\032\b\022\004\022\0028\0000\007H¦@?\001\000?\006\002\020\b?\002\004\n\002\b\031?\006\n"}, d2={"Lkotlinx/coroutines/flow/AbstractFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "()V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectSafely", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class AbstractFlow<T>
  implements Flow<T>
{
  public AbstractFlow() {}
  
  public final Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = collectSafely((FlowCollector)new SafeCollector(paramFlowCollector, paramContinuation.getContext()), paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
  
  public abstract Object collectSafely(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation);
}
