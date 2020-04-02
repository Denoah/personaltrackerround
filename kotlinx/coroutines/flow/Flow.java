package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\bf\030\000*\006\b\000\020\001 \0012\0020\002J\037\020\003\032\0020\0042\f\020\005\032\b\022\004\022\0028\0000\006H§@?\001\000?\006\002\020\007?\002\004\n\002\b\031?\006\b"}, d2={"Lkotlinx/coroutines/flow/Flow;", "T", "", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Flow<T>
{
  public abstract Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation);
}
