package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\000\030\000*\004\b\000\020\0012\016\022\004\022\002H\001\022\004\022\002H\0010\002B'\022\f\020\003\032\b\022\004\022\0028\0000\004\022\b\b\002\020\005\032\0020\006\022\b\b\002\020\007\032\0020\b?\006\002\020\tJ\036\020\n\032\b\022\004\022\0028\0000\0132\006\020\005\032\0020\0062\006\020\007\032\0020\bH\024J\037\020\f\032\0020\r2\f\020\016\032\b\022\004\022\0028\0000\017H?@?\001\000?\006\002\020\020?\002\004\n\002\b\031?\006\021"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowOperatorImpl;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;I)V", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flowCollect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChannelFlowOperatorImpl<T>
  extends ChannelFlowOperator<T, T>
{
  public ChannelFlowOperatorImpl(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramFlow, paramCoroutineContext, paramInt);
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ChannelFlowOperatorImpl(this.flow, paramCoroutineContext, paramInt);
  }
  
  protected Object flowCollect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = this.flow.collect(paramFlowCollector, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}
