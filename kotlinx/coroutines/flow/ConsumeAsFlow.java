package kotlinx.coroutines.flow;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.SendingCollector;

@Metadata(bv={1, 0, 3}, d1={"\000T\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\b\b\002\030\000*\004\b\000\020\0012\b\022\004\022\0028\0000\034B)\022\f\020\003\032\b\022\004\022\0028\0000\002\022\b\b\002\020\005\032\0020\004\022\b\b\002\020\007\032\0020\006?\006\004\b\b\020\tJ\017\020\013\032\0020\nH\026?\006\004\b\013\020\fJ%\020\022\032\b\022\004\022\0028\0000\0212\006\020\016\032\0020\r2\006\020\020\032\0020\017H\026?\006\004\b\022\020\023J!\020\027\032\0020\0262\f\020\025\032\b\022\004\022\0028\0000\024H?@?\001\000?\006\004\b\027\020\030J!\020\032\032\0020\0262\f\020\016\032\b\022\004\022\0028\0000\031H?@?\001\000?\006\004\b\032\020\033J%\020\035\032\b\022\004\022\0028\0000\0342\006\020\005\032\0020\0042\006\020\007\032\0020\006H\024?\006\004\b\035\020\036J\017\020\037\032\0020\026H\002?\006\004\b\037\020 J\035\020!\032\b\022\004\022\0028\0000\0022\006\020\016\032\0020\rH\026?\006\004\b!\020\"R\034\020\003\032\b\022\004\022\0028\0000\0028\002@\002X?\004?\006\006\n\004\b\003\020#?\002\004\n\002\b\031?\006$"}, d2={"Lkotlinx/coroutines/flow/ConsumeAsFlow;", "T", "Lkotlinx/coroutines/channels/ReceiveChannel;", "channel", "Lkotlin/coroutines/CoroutineContext;", "context", "", "capacity", "<init>", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;I)V", "", "additionalToStringProps", "()Ljava/lang/String;", "Lkotlinx/coroutines/CoroutineScope;", "scope", "Lkotlinx/coroutines/CoroutineStart;", "start", "Lkotlinx/coroutines/channels/BroadcastChannel;", "broadcastImpl", "(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/CoroutineStart;)Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "", "collect", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ProducerScope;", "collectTo", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "create", "(Lkotlin/coroutines/CoroutineContext;I)Lkotlinx/coroutines/flow/internal/ChannelFlow;", "markConsumed", "()V", "produceImpl", "(Lkotlinx/coroutines/CoroutineScope;)Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ConsumeAsFlow<T>
  extends ChannelFlow<T>
{
  private static final AtomicIntegerFieldUpdater consumed$FU = AtomicIntegerFieldUpdater.newUpdater(ConsumeAsFlow.class, "consumed");
  private final ReceiveChannel<T> channel;
  private volatile int consumed;
  
  public ConsumeAsFlow(ReceiveChannel<? extends T> paramReceiveChannel, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramCoroutineContext, paramInt);
    this.channel = paramReceiveChannel;
    this.consumed = 0;
  }
  
  private final void markConsumed()
  {
    AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = consumed$FU;
    int i = 1;
    if (localAtomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
      i = 0;
    }
    if (i != 0) {
      return;
    }
    throw ((Throwable)new IllegalStateException("ReceiveChannel.consumeAsFlow can be collected just once".toString()));
  }
  
  public String additionalToStringProps()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("channel=");
    localStringBuilder.append(this.channel);
    localStringBuilder.append(", ");
    return localStringBuilder.toString();
  }
  
  public BroadcastChannel<T> broadcastImpl(CoroutineScope paramCoroutineScope, CoroutineStart paramCoroutineStart)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "scope");
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    markConsumed();
    return super.broadcastImpl(paramCoroutineScope, paramCoroutineStart);
  }
  
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    if (this.capacity == -3)
    {
      markConsumed();
      paramFlowCollector = FlowKt.emitAll(paramFlowCollector, this.channel, paramContinuation);
      if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramFlowCollector;
      }
    }
    else
    {
      paramFlowCollector = super.collect(paramFlowCollector, paramContinuation);
      if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramFlowCollector;
      }
    }
    return Unit.INSTANCE;
  }
  
  protected Object collectTo(ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    paramProducerScope = FlowKt.emitAll((FlowCollector)new SendingCollector((SendChannel)paramProducerScope), this.channel, paramContinuation);
    if (paramProducerScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramProducerScope;
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ConsumeAsFlow(this.channel, paramCoroutineContext, paramInt);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "scope");
    markConsumed();
    if (this.capacity == -3) {
      paramCoroutineScope = this.channel;
    } else {
      paramCoroutineScope = super.produceImpl(paramCoroutineScope);
    }
    return paramCoroutineScope;
  }
}
