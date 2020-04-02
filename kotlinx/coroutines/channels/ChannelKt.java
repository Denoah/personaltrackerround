package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\032\034\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\b\b\002\020\003\032\0020\004?\006\005"}, d2={"Channel", "Lkotlinx/coroutines/channels/Channel;", "E", "capacity", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ChannelKt
{
  public static final <E> Channel<E> Channel(int paramInt)
  {
    Channel localChannel;
    if (paramInt != -2)
    {
      if (paramInt != -1)
      {
        if (paramInt != 0)
        {
          if (paramInt != Integer.MAX_VALUE) {
            localChannel = (Channel)new ArrayChannel(paramInt);
          } else {
            localChannel = (Channel)new LinkedListChannel();
          }
        }
        else {
          localChannel = (Channel)new RendezvousChannel();
        }
      }
      else {
        localChannel = (Channel)new ConflatedChannel();
      }
    }
    else {
      localChannel = (Channel)new ArrayChannel(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core());
    }
    return localChannel;
  }
}
