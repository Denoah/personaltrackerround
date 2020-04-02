package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.ExceptionsKt;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\003\n\000\n\002\020\002\n\002\b\002\b\022\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B#\022\006\020\004\032\0020\005\022\f\020\006\032\b\022\004\022\0028\0000\007\022\006\020\b\032\0020\t?\006\002\020\nJ\020\020\013\032\0020\t2\006\020\f\032\0020\rH\024J\022\020\016\032\0020\0172\b\020\020\032\004\030\0010\rH\024?\006\021"}, d2={"Lkotlinx/coroutines/channels/ActorCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ActorScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Z)V", "handleJobException", "exception", "", "onCancelling", "", "cause", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
class ActorCoroutine<E>
  extends ChannelCoroutine<E>
  implements ActorScope<E>
{
  public ActorCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel, boolean paramBoolean)
  {
    super(paramCoroutineContext, paramChannel, paramBoolean);
  }
  
  protected boolean handleJobException(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    return true;
  }
  
  protected void onCancelling(Throwable paramThrowable)
  {
    Channel localChannel = get_channel();
    Object localObject1 = null;
    Object localObject2 = null;
    if (paramThrowable != null)
    {
      if (!(paramThrowable instanceof CancellationException)) {
        localObject1 = localObject2;
      } else {
        localObject1 = paramThrowable;
      }
      localObject1 = (CancellationException)localObject1;
      if (localObject1 == null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(DebugStringsKt.getClassSimpleName(this));
        ((StringBuilder)localObject1).append(" was cancelled");
        localObject1 = ExceptionsKt.CancellationException(((StringBuilder)localObject1).toString(), paramThrowable);
      }
    }
    localChannel.cancel((CancellationException)localObject1);
  }
}
