package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\020\002\n\000\n\002\020\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\007\bf\030\000*\006\b\000\020\001 \0012\0020\002J\b\020\025\032\0020\026H\027J\024\020\025\032\0020\0042\n\b\002\020\027\032\004\030\0010\030H'J\032\020\025\032\0020\0262\020\b\002\020\027\032\n\030\0010\031j\004\030\001`\032H&J\017\020\033\032\b\022\004\022\0028\0000\034H¦\002J\017\020\035\032\004\030\0018\000H&?\006\002\020\036J\021\020\037\032\0028\000H¦@?\001\000?\006\002\020 J\032\020!\032\b\022\004\022\0028\0000\017H§@?\001\000?\001\000?\006\002\020 J\023\020\"\032\004\030\0018\000H§@?\001\000?\006\002\020 R\032\020\003\032\0020\0048&X§\004?\006\f\022\004\b\005\020\006\032\004\b\003\020\007R\032\020\b\032\0020\0048&X§\004?\006\f\022\004\b\t\020\006\032\004\b\b\020\007R\030\020\n\032\b\022\004\022\0028\0000\013X¦\004?\006\006\032\004\b\f\020\rR)\020\016\032\016\022\n\022\b\022\004\022\0028\0000\0170\0138&X§\004?\001\000?\006\f\022\004\b\020\020\006\032\004\b\021\020\rR\"\020\022\032\n\022\006\022\004\030\0018\0000\0138&X§\004?\006\f\022\004\b\023\020\006\032\004\b\024\020\r?\002\004\n\002\b\031?\006#"}, d2={"Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "", "isClosedForReceive", "", "isClosedForReceive$annotations", "()V", "()Z", "isEmpty", "isEmpty$annotations", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveOrClosed", "Lkotlinx/coroutines/channels/ValueOrClosed;", "onReceiveOrClosed$annotations", "getOnReceiveOrClosed", "onReceiveOrNull", "onReceiveOrNull$annotations", "getOnReceiveOrNull", "cancel", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOrClosed", "receiveOrNull", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ReceiveChannel<E>
{
  public abstract void cancel(CancellationException paramCancellationException);
  
  public abstract SelectClause1<E> getOnReceive();
  
  public abstract SelectClause1<ValueOrClosed<E>> getOnReceiveOrClosed();
  
  public abstract SelectClause1<E> getOnReceiveOrNull();
  
  public abstract boolean isClosedForReceive();
  
  public abstract boolean isEmpty();
  
  public abstract ChannelIterator<E> iterator();
  
  public abstract E poll();
  
  public abstract Object receive(Continuation<? super E> paramContinuation);
  
  public abstract Object receiveOrClosed(Continuation<? super ValueOrClosed<? extends E>> paramContinuation);
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Deprecated in favor of receiveOrClosed and receiveOrNull extension", replaceWith=@ReplaceWith(expression="receiveOrNull", imports={"kotlinx.coroutines.channels.receiveOrNull"}))
  public abstract Object receiveOrNull(Continuation<? super E> paramContinuation);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
