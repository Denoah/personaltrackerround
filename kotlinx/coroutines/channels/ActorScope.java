package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.CoroutineScope;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\bg\030\000*\004\b\000\020\0012\0020\0022\b\022\004\022\002H\0010\003R\030\020\004\032\b\022\004\022\0028\0000\005X¦\004?\006\006\032\004\b\006\020\007?\006\b"}, d2={"Lkotlinx/coroutines/channels/ActorScope;", "E", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "channel", "Lkotlinx/coroutines/channels/Channel;", "getChannel", "()Lkotlinx/coroutines/channels/Channel;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ActorScope<E>
  extends CoroutineScope, ReceiveChannel<E>
{
  public abstract Channel<E> getChannel();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
