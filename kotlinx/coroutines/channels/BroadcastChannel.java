package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\003\n\002\020\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\bg\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002J\024\020\003\032\0020\0042\n\b\002\020\005\032\004\030\0010\006H'J\032\020\003\032\0020\0072\020\b\002\020\005\032\n\030\0010\bj\004\030\001`\tH&J\016\020\n\032\b\022\004\022\0028\0000\013H&?\006\f"}, d2={"Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "Lkotlinx/coroutines/channels/SendChannel;", "cancel", "", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface BroadcastChannel<E>
  extends SendChannel<E>
{
  public abstract void cancel(CancellationException paramCancellationException);
  
  public abstract ReceiveChannel<E> openSubscription();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
