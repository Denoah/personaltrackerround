package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\005?\006\002\020\003R\024\020\004\032\0020\0058DX?\004?\006\006\032\004\b\004\020\006R\024\020\007\032\0020\0058DX?\004?\006\006\032\004\b\007\020\006R\024\020\b\032\0020\0058DX?\004?\006\006\032\004\b\b\020\006R\024\020\t\032\0020\0058DX?\004?\006\006\032\004\b\t\020\006?\006\n"}, d2={"Lkotlinx/coroutines/channels/RendezvousChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "()V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class RendezvousChannel<E>
  extends AbstractChannel<E>
{
  public RendezvousChannel() {}
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return true;
  }
  
  protected final boolean isBufferEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferFull()
  {
    return true;
  }
}
