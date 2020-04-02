package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\b\007\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\023\022\f\020\003\032\b\022\004\022\0028\0000\004?\006\002\020\005J\031\020\006\032\0020\0072\006\020\b\032\0028\000H?@?\001\000?\006\002\020\tR\024\020\003\032\b\022\004\022\0028\0000\004X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\n"}, d2={"Lkotlinx/coroutines/flow/internal/SendingCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/SendChannel;)V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class SendingCollector<T>
  implements FlowCollector<T>
{
  private final SendChannel<T> channel;
  
  public SendingCollector(SendChannel<? super T> paramSendChannel)
  {
    this.channel = paramSendChannel;
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    paramT = this.channel.send(paramT, paramContinuation);
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramT;
    }
    return Unit.INSTANCE;
  }
}
