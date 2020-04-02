package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\002\n\002\b\003\bf\030\000*\006\b\000\020\001 \0002\0020\002J\031\020\003\032\0020\0042\006\020\005\032\0028\000H¦@?\001\000?\006\002\020\006?\002\004\n\002\b\031?\006\007"}, d2={"Lkotlinx/coroutines/flow/FlowCollector;", "T", "", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface FlowCollector<T>
{
  public abstract Object emit(T paramT, Continuation<? super Unit> paramContinuation);
}
