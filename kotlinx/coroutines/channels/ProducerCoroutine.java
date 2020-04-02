package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\002\b\005\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\033\022\006\020\004\032\0020\005\022\f\020\006\032\b\022\004\022\0028\0000\007?\006\002\020\bJ\030\020\f\032\0020\r2\006\020\016\032\0020\0172\006\020\020\032\0020\nH\024J\025\020\021\032\0020\r2\006\020\022\032\0020\rH\024?\006\002\020\023R\024\020\t\032\0020\n8VX?\004?\006\006\032\004\b\t\020\013?\006\024"}, d2={"Lkotlinx/coroutines/channels/ProducerCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ProducerScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;)V", "isActive", "", "()Z", "onCancelled", "", "cause", "", "handled", "onCompleted", "value", "(Lkotlin/Unit;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class ProducerCoroutine<E>
  extends ChannelCoroutine<E>
  implements ProducerScope<E>
{
  public ProducerCoroutine(CoroutineContext paramCoroutineContext, Channel<E> paramChannel)
  {
    super(paramCoroutineContext, paramChannel, true);
  }
  
  public boolean isActive()
  {
    return super.isActive();
  }
  
  protected void onCancelled(Throwable paramThrowable, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    if ((!get_channel().close(paramThrowable)) && (!paramBoolean)) {
      CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), paramThrowable);
    }
  }
  
  protected void onCompleted(Unit paramUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramUnit, "value");
    SendChannel.DefaultImpls.close$default(get_channel(), null, 1, null);
  }
}
