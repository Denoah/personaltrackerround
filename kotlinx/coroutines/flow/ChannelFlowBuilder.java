package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.internal.ChannelFlow;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\007\n\002\020\016\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002BK\022-\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004?\006\002\b\t\022\b\b\002\020\n\032\0020\013\022\b\b\002\020\f\032\0020\r?\001\000?\006\002\020\016J\037\020\020\032\0020\0072\f\020\021\032\b\022\004\022\0028\0000\005H?@?\001\000?\006\002\020\022J\036\020\023\032\b\022\004\022\0028\0000\0022\006\020\n\032\0020\0132\006\020\f\032\0020\rH\024J\b\020\024\032\0020\025H\026R:\020\003\032)\b\001\022\n\022\b\022\004\022\0028\0000\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004?\006\002\b\tX?\004?\001\000?\006\004\n\002\020\017?\002\004\n\002\b\031?\006\026"}, d2={"Lkotlinx/coroutines/flow/ChannelFlowBuilder;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/CoroutineContext;I)V", "Lkotlin/jvm/functions/Function2;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ChannelFlowBuilder<T>
  extends ChannelFlow<T>
{
  private final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> block;
  
  public ChannelFlowBuilder(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramCoroutineContext, paramInt);
    this.block = paramFunction2;
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    paramProducerScope = this.block.invoke(paramProducerScope, paramContinuation);
    if (paramProducerScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramProducerScope;
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ChannelFlowBuilder(this.block, paramCoroutineContext, paramInt);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("block[");
    localStringBuilder.append(this.block);
    localStringBuilder.append("] -> ");
    localStringBuilder.append(super.toString());
    return localStringBuilder.toString();
  }
}
