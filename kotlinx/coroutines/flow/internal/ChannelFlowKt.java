package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\b\004\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032[\020\000\032\002H\001\"\004\b\000\020\001\"\004\b\001\020\0022\006\020\003\032\0020\0042\b\b\002\020\005\032\0020\0062\"\020\007\032\036\b\001\022\004\022\002H\002\022\n\022\b\022\004\022\002H\0010\t\022\006\022\004\030\0010\0060\b2\006\020\n\032\002H\002H?@?\001\000?\006\002\020\013\032\036\020\f\032\b\022\004\022\002H\0010\r\"\004\b\000\020\001*\b\022\004\022\002H\0010\016H\000\032&\020\017\032\b\022\004\022\002H\0010\020\"\004\b\000\020\001*\b\022\004\022\002H\0010\0202\006\020\021\032\0020\004H\002?\002\004\n\002\b\031?\006\022"}, d2={"withContextUndispatched", "T", "V", "newContext", "Lkotlin/coroutines/CoroutineContext;", "countOrElement", "", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "value", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asChannelFlow", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "withUndispatchedContextCollector", "Lkotlinx/coroutines/flow/FlowCollector;", "emitContext", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ChannelFlowKt
{
  public static final <T> ChannelFlow<T> asChannelFlow(Flow<? extends T> paramFlow)
  {
    Intrinsics.checkParameterIsNotNull(paramFlow, "$this$asChannelFlow");
    if (!(paramFlow instanceof ChannelFlow)) {
      localObject = null;
    } else {
      localObject = paramFlow;
    }
    Object localObject = (ChannelFlow)localObject;
    if (localObject != null) {
      paramFlow = (Flow<? extends T>)localObject;
    } else {
      paramFlow = (ChannelFlow)new ChannelFlowOperatorImpl(paramFlow, null, 0, 6, null);
    }
    return paramFlow;
  }
  
  private static final <T> FlowCollector<T> withUndispatchedContextCollector(FlowCollector<? super T> paramFlowCollector, CoroutineContext paramCoroutineContext)
  {
    if ((!(paramFlowCollector instanceof SendingCollector)) && (!(paramFlowCollector instanceof NopCollector))) {
      paramFlowCollector = (FlowCollector)new UndispatchedContextCollector(paramFlowCollector, paramCoroutineContext);
    }
    return paramFlowCollector;
  }
}
