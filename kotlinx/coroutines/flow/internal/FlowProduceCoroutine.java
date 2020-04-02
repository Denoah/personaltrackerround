package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ProducerCoroutine;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0028\0000\006?\006\002\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026?\006\f"}, d2={"Lkotlinx/coroutines/flow/internal/FlowProduceCoroutine;", "T", "Lkotlinx/coroutines/channels/ProducerCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;)V", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class FlowProduceCoroutine<T>
  extends ProducerCoroutine<T>
{
  public FlowProduceCoroutine(CoroutineContext paramCoroutineContext, Channel<T> paramChannel)
  {
    super(paramCoroutineContext, paramChannel);
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    if ((paramThrowable instanceof ChildCancelledException)) {
      return true;
    }
    return cancelImpl$kotlinx_coroutines_core(paramThrowable);
  }
}