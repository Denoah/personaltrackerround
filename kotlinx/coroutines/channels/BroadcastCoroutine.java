package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(bv={1, 0, 3}, d1={"\000\\\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\004\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\003\b\022\030\000*\004\b\000\020\0012\b\022\004\022\0020\0030\0022\b\022\004\022\002H\0010\0042\b\022\004\022\002H\0010\005B#\022\006\020\006\032\0020\007\022\f\020\b\032\b\022\004\022\0028\0000\005\022\006\020\t\032\0020\n?\006\002\020\013J\022\020\032\032\0020\n2\b\020\033\032\004\030\0010\034H\007J\026\020\032\032\0020\0032\016\020\033\032\n\030\0010\035j\004\030\001`\036J\020\020\037\032\0020\0032\006\020\033\032\0020\034H\026J\023\020 \032\0020\n2\b\020\033\032\004\030\0010\034H?\001J.\020!\032\0020\0032#\020\"\032\037\022\025\022\023\030\0010\034?\006\f\b$\022\b\b%\022\004\b\b(\033\022\004\022\0020\0030#H?\001J\026\020&\032\0020\n2\006\020'\032\0028\000H?\001?\006\002\020(J\030\020)\032\0020\0032\006\020\033\032\0020\0342\006\020*\032\0020\nH\024J\025\020+\032\0020\0032\006\020,\032\0020\003H\024?\006\002\020-J\017\020.\032\b\022\004\022\0028\0000/H?\001J\031\0200\032\0020\0032\006\020'\032\0028\000H?A?\001\000?\006\002\0201R\032\020\b\032\b\022\004\022\0028\0000\005X?\004?\006\b\n\000\032\004\b\f\020\rR\032\020\016\032\b\022\004\022\0028\0000\0178VX?\004?\006\006\032\004\b\020\020\021R\024\020\022\032\0020\n8VX?\004?\006\006\032\004\b\022\020\023R\024\020\024\032\0020\n8\026X?\005?\006\006\032\004\b\024\020\023R\024\020\025\032\0020\n8\026X?\005?\006\006\032\004\b\025\020\023R$\020\026\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000\0170\027X?\005?\006\006\032\004\b\030\020\031?\002\004\n\002\b\031?\0062"}, d2={"Lkotlinx/coroutines/channels/BroadcastCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "active", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/BroadcastChannel;Z)V", "get_channel", "()Lkotlinx/coroutines/channels/BroadcastChannel;", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "getChannel", "()Lkotlinx/coroutines/channels/SendChannel;", "isActive", "()Z", "isClosedForSend", "isFull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "offer", "element", "(Ljava/lang/Object;)Z", "onCancelled", "handled", "onCompleted", "value", "(Lkotlin/Unit;)V", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
class BroadcastCoroutine<E>
  extends AbstractCoroutine<Unit>
  implements ProducerScope<E>, BroadcastChannel<E>
{
  private final BroadcastChannel<E> _channel;
  
  public BroadcastCoroutine(CoroutineContext paramCoroutineContext, BroadcastChannel<E> paramBroadcastChannel, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramBoolean);
    this._channel = paramBroadcastChannel;
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
    this._channel.cancel(JobSupport.toCancellationException$default(this, paramThrowable, null, 1, null));
    cancelCoroutine(paramThrowable);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    return this._channel.close(paramThrowable);
  }
  
  public SendChannel<E> getChannel()
  {
    return (SendChannel)this;
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    return this._channel.getOnSend();
  }
  
  protected final BroadcastChannel<E> get_channel()
  {
    return this._channel;
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "handler");
    this._channel.invokeOnClose(paramFunction1);
  }
  
  public boolean isActive()
  {
    return super.isActive();
  }
  
  public boolean isClosedForSend()
  {
    return this._channel.isClosedForSend();
  }
  
  public boolean isFull()
  {
    return this._channel.isFull();
  }
  
  public boolean offer(E paramE)
  {
    return this._channel.offer(paramE);
  }
  
  protected void onCancelled(Throwable paramThrowable, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    if ((!this._channel.close(paramThrowable)) && (!paramBoolean)) {
      CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    }
  }
  
  protected void onCompleted(Unit paramUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramUnit, "value");
    SendChannel.DefaultImpls.close$default(this._channel, null, 1, null);
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    return this._channel.openSubscription();
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    return send$suspendImpl(this, paramE, paramContinuation);
  }
}
