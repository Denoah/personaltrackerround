package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\020\001\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b?\002\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003J\037\020\004\032\0020\0052\f\020\006\032\b\022\004\022\0020\0020\007H?@?\001\000?\006\002\020\b?\002\004\n\002\b\031?\006\t"}, d2={"Lkotlinx/coroutines/flow/EmptyFlow;", "Lkotlinx/coroutines/flow/Flow;", "", "()V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class EmptyFlow
  implements Flow
{
  public static final EmptyFlow INSTANCE = new EmptyFlow();
  
  private EmptyFlow() {}
  
  public Object collect(FlowCollector<?> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    return Unit.INSTANCE;
  }
}
