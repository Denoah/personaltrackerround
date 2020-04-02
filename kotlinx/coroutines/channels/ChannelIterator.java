package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\005\bf\030\000*\006\b\000\020\001 \0012\0020\002J\021\020\003\032\0020\004H¦B?\001\000?\006\002\020\005J\016\020\006\032\0028\000H¦\002?\006\002\020\007J\023\020\b\032\0028\000H?@?\001\000?\006\004\b\006\020\005?\002\004\n\002\b\031?\006\t"}, d2={"Lkotlinx/coroutines/channels/ChannelIterator;", "E", "", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "next", "()Ljava/lang/Object;", "next0", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ChannelIterator<E>
{
  public abstract Object hasNext(Continuation<? super Boolean> paramContinuation);
  
  public abstract E next();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
