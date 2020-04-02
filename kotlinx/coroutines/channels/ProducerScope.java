package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.CoroutineScope;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\bg\030\000*\006\b\000\020\001 \0002\0020\0022\b\022\004\022\002H\0010\003R\030\020\004\032\b\022\004\022\0028\0000\003X¦\004?\006\006\032\004\b\005\020\006?\006\007"}, d2={"Lkotlinx/coroutines/channels/ProducerScope;", "E", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlinx/coroutines/channels/SendChannel;", "channel", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ProducerScope<E>
  extends CoroutineScope, SendChannel<E>
{
  public abstract SendChannel<E> getChannel();
}
