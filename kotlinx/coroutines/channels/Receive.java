package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\000\b\"\030\000*\006\b\000\020\001 \0002\0020\0022\b\022\004\022\002H\0010\003B\005?\006\002\020\004J\024\020\t\032\0020\n2\n\020\013\032\006\022\002\b\0030\fH&R\024\020\005\032\0020\0068VX?\004?\006\006\032\004\b\007\020\b?\006\r"}, d2={"Lkotlinx/coroutines/channels/Receive;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "()V", "offerResult", "", "getOfferResult", "()Ljava/lang/Object;", "resumeReceiveClosed", "", "closed", "Lkotlinx/coroutines/channels/Closed;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
abstract class Receive<E>
  extends LockFreeLinkedListNode
  implements ReceiveOrClosed<E>
{
  public Receive() {}
  
  public Object getOfferResult()
  {
    return AbstractChannelKt.OFFER_SUCCESS;
  }
  
  public abstract void resumeReceiveClosed(Closed<?> paramClosed);
}
