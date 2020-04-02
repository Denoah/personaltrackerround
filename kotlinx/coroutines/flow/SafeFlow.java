package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.internal.SafeCollector;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\006\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B7\022-\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004?\006\002\b\t?\001\000?\006\002\020\nJ\037\020\f\032\0020\0072\f\020\r\032\b\022\004\022\0028\0000\005H?@?\001\000?\006\002\020\016R:\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004?\006\002\b\tX?\004?\001\000?\006\004\n\002\020\013?\002\004\n\002\b\031?\006\017"}, d2={"Lkotlinx/coroutines/flow/SafeFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "collect", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class SafeFlow<T>
  implements Flow<T>
{
  private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> block;
  
  public SafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    this.block = paramFunction2;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = this.block.invoke(new SafeCollector(paramFlowCollector, paramContinuation.getContext()), paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}
