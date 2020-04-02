package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\020\016\n\000\b \030\000*\004\b\000\020\001*\004\b\001\020\0022\b\022\004\022\002H\0020\003B#\022\f\020\004\032\b\022\004\022\0028\0000\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t?\006\002\020\nJ\037\020\013\032\0020\f2\f\020\r\032\b\022\004\022\0028\0010\016H?@?\001\000?\006\002\020\017J\037\020\020\032\0020\f2\f\020\021\032\b\022\004\022\0028\0010\022H?@?\001\000?\006\002\020\023J'\020\024\032\0020\f2\f\020\r\032\b\022\004\022\0028\0010\0162\006\020\025\032\0020\007H?@?\001\000?\006\002\020\026J\037\020\027\032\0020\f2\f\020\r\032\b\022\004\022\0028\0010\016H¤@?\001\000?\006\002\020\017J\b\020\030\032\0020\031H\026R\026\020\004\032\b\022\004\022\0028\0000\0058\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\032"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "S", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;I)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectWithContextUndispatched", "newContext", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "flowCollect", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class ChannelFlowOperator<S, T>
  extends ChannelFlow<T>
{
  public final Flow<S> flow;
  
  public ChannelFlowOperator(Flow<? extends S> paramFlow, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramCoroutineContext, paramInt);
    this.flow = paramFlow;
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    return collect$suspendImpl(this, paramFlowCollector, paramContinuation);
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    return collectTo$suspendImpl(this, paramProducerScope, paramContinuation);
  }
  
  protected abstract Object flowCollect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation);
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.flow);
    localStringBuilder.append(" -> ");
    localStringBuilder.append(super.toString());
    return localStringBuilder.toString();
  }
}
