package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(bv={1, 0, 3}, d1={"\000d\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\013\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\r\b\020\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\0022\b\022\004\022\002H\0010\004B#\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\004\022\0028\0000\004\022\006\020\b\032\0020\t?\006\002\020\nJ\b\020\"\032\0020\003H\026J\022\020\"\032\0020\t2\b\020#\032\004\030\0010$H\007J\026\020\"\032\0020\0032\016\020#\032\n\030\0010%j\004\030\001`&J\020\020'\032\0020\0032\006\020#\032\0020$H\026J\023\020(\032\0020\t2\b\020#\032\004\030\0010$H?\001J.\020)\032\0020\0032#\020*\032\037\022\025\022\023\030\0010$?\006\f\b,\022\b\b-\022\004\b\b(#\022\004\022\0020\0030+H?\001J\017\020.\032\b\022\004\022\0028\0000/H?\003J\026\0200\032\0020\t2\006\0201\032\0028\000H?\001?\006\002\0202J\020\0203\032\004\030\0018\000H?\001?\006\002\0204J\021\0205\032\0028\000H?A?\001\000?\006\002\0206J\032\0207\032\b\022\004\022\0028\0000\031H?A?\001\000?\001\000?\006\002\0206J\023\0208\032\004\030\0018\000H?A?\001\000?\006\002\0206J\031\0209\032\0020\0032\006\0201\032\0028\000H?A?\001\000?\006\002\020:J\031\020;\032\0020\0032\006\0201\032\0028\000H?@?\001\000?\006\002\020:R\032\020\007\032\b\022\004\022\0028\0000\004X?\004?\006\b\n\000\032\004\b\013\020\fR\027\020\r\032\b\022\004\022\0028\0000\0048F?\006\006\032\004\b\016\020\fR\024\020\017\032\0020\t8\026X?\005?\006\006\032\004\b\017\020\020R\024\020\021\032\0020\t8\026X?\005?\006\006\032\004\b\021\020\020R\024\020\022\032\0020\t8\026X?\005?\006\006\032\004\b\022\020\020R\024\020\023\032\0020\t8\026X?\005?\006\006\032\004\b\023\020\020R\030\020\024\032\b\022\004\022\0028\0000\025X?\005?\006\006\032\004\b\026\020\027R#\020\030\032\016\022\n\022\b\022\004\022\0028\0000\0310\0258\026X?\005?\001\000?\006\006\032\004\b\032\020\027R\034\020\033\032\n\022\006\022\004\030\0018\0000\0258\026X?\005?\006\006\032\004\b\034\020\027R$\020\035\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0370\036X?\005?\006\006\032\004\b \020!?\002\004\n\002\b\031?\006<"}, d2={"Lkotlinx/coroutines/channels/ChannelCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/Channel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "active", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Z)V", "get_channel", "()Lkotlinx/coroutines/channels/Channel;", "channel", "getChannel", "isClosedForReceive", "()Z", "isClosedForSend", "isEmpty", "isFull", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveOrClosed", "Lkotlinx/coroutines/channels/ValueOrClosed;", "getOnReceiveOrClosed", "onReceiveOrNull", "getOnReceiveOrNull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "offer", "element", "(Ljava/lang/Object;)Z", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOrClosed", "receiveOrNull", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendFair", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class ChannelCoroutine<E>
  extends AbstractCoroutine<Unit>
  implements Channel<E>
{
  private final Channel<E> _channel;
  
  public ChannelCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramBoolean);
    this._channel = paramChannel;
  }
  
  public final void cancel(CancellationException paramCancellationException)
  {
    if (paramCancellationException != null)
    {
      paramCancellationException = (Throwable)paramCancellationException;
    }
    else
    {
      paramCancellationException = (String)null;
      paramCancellationException = (Throwable)null;
      paramCancellationException = (Throwable)new JobCancellationException(JobSupport.access$cancellationExceptionMessage(this), paramCancellationException, (Job)this);
    }
    cancelInternal(paramCancellationException);
  }
  
  public void cancelInternal(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    paramThrowable = JobSupport.toCancellationException$default(this, paramThrowable, null, 1, null);
    this._channel.cancel(paramThrowable);
    cancelCoroutine((Throwable)paramThrowable);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    return this._channel.close(paramThrowable);
  }
  
  public final Channel<E> getChannel()
  {
    return (Channel)this;
  }
  
  public SelectClause1<E> getOnReceive()
  {
    return this._channel.getOnReceive();
  }
  
  public SelectClause1<ValueOrClosed<E>> getOnReceiveOrClosed()
  {
    return this._channel.getOnReceiveOrClosed();
  }
  
  public SelectClause1<E> getOnReceiveOrNull()
  {
    return this._channel.getOnReceiveOrNull();
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    return this._channel.getOnSend();
  }
  
  protected final Channel<E> get_channel()
  {
    return this._channel;
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "handler");
    this._channel.invokeOnClose(paramFunction1);
  }
  
  public boolean isClosedForReceive()
  {
    return this._channel.isClosedForReceive();
  }
  
  public boolean isClosedForSend()
  {
    return this._channel.isClosedForSend();
  }
  
  public boolean isEmpty()
  {
    return this._channel.isEmpty();
  }
  
  public boolean isFull()
  {
    return this._channel.isFull();
  }
  
  public ChannelIterator<E> iterator()
  {
    return this._channel.iterator();
  }
  
  public boolean offer(E paramE)
  {
    return this._channel.offer(paramE);
  }
  
  public E poll()
  {
    return this._channel.poll();
  }
  
  public Object receive(Continuation<? super E> paramContinuation)
  {
    return receive$suspendImpl(this, paramContinuation);
  }
  
  public Object receiveOrClosed(Continuation<? super ValueOrClosed<? extends E>> paramContinuation)
  {
    return receiveOrClosed$suspendImpl(this, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Deprecated in favor of receiveOrClosed and receiveOrNull extension", replaceWith=@ReplaceWith(expression="receiveOrNull", imports={"kotlinx.coroutines.channels.receiveOrNull"}))
  public Object receiveOrNull(Continuation<? super E> paramContinuation)
  {
    return receiveOrNull$suspendImpl(this, paramContinuation);
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    return send$suspendImpl(this, paramE, paramContinuation);
  }
  
  public final Object sendFair(E paramE, Continuation<? super Unit> paramContinuation)
  {
    Channel localChannel = this._channel;
    if (localChannel != null)
    {
      paramE = ((AbstractSendChannel)localChannel).sendFair$kotlinx_coroutines_core(paramE, paramContinuation);
      if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramE;
      }
      return Unit.INSTANCE;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.AbstractSendChannel<E>");
  }
}
