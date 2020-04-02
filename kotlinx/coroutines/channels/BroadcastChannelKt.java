package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\032\034\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\0020\004H\007?\006\005"}, d2={"BroadcastChannel", "Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "capacity", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class BroadcastChannelKt
{
  public static final <E> BroadcastChannel<E> BroadcastChannel(int paramInt)
  {
    BroadcastChannel localBroadcastChannel;
    if (paramInt != -2)
    {
      if (paramInt != -1)
      {
        if (paramInt != 0)
        {
          if (paramInt != Integer.MAX_VALUE) {
            localBroadcastChannel = (BroadcastChannel)new ArrayBroadcastChannel(paramInt);
          } else {
            throw ((Throwable)new IllegalArgumentException("Unsupported UNLIMITED capacity for BroadcastChannel"));
          }
        }
        else {
          throw ((Throwable)new IllegalArgumentException("Unsupported 0 capacity for BroadcastChannel"));
        }
      }
      else {
        localBroadcastChannel = (BroadcastChannel)new ConflatedBroadcastChannel();
      }
    }
    else {
      localBroadcastChannel = (BroadcastChannel)new ArrayBroadcastChannel(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core());
    }
    return localBroadcastChannel;
  }
}
