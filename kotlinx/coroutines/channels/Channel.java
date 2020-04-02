package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bf\030\000 \004*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\001\004?\006\005"}, d2={"Lkotlinx/coroutines/channels/Channel;", "E", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Factory", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Channel<E>
  extends SendChannel<E>, ReceiveChannel<E>
{
  public static final int BUFFERED = -2;
  public static final int CONFLATED = -1;
  public static final String DEFAULT_BUFFER_PROPERTY_NAME = "kotlinx.coroutines.channels.defaultBuffer";
  public static final Factory Factory = Factory.$$INSTANCE;
  public static final int OPTIONAL_CHANNEL = -3;
  public static final int RENDEZVOUS = 0;
  public static final int UNLIMITED = Integer.MAX_VALUE;
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\005\n\002\020\016\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\024\020\005\032\0020\004X?\004?\006\b\n\000\032\004\b\006\020\007R\016\020\b\032\0020\004X?T?\006\002\n\000R\016\020\t\032\0020\nX?T?\006\002\n\000R\016\020\013\032\0020\004X?T?\006\002\n\000R\016\020\f\032\0020\004X?T?\006\002\n\000R\016\020\r\032\0020\004X?T?\006\002\n\000?\006\016"}, d2={"Lkotlinx/coroutines/channels/Channel$Factory;", "", "()V", "BUFFERED", "", "CHANNEL_DEFAULT_CAPACITY", "getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core", "()I", "CONFLATED", "DEFAULT_BUFFER_PROPERTY_NAME", "", "OPTIONAL_CHANNEL", "RENDEZVOUS", "UNLIMITED", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Factory
  {
    public static final int BUFFERED = -2;
    private static final int CHANNEL_DEFAULT_CAPACITY = SystemPropsKt.systemProp("kotlinx.coroutines.channels.defaultBuffer", 64, 1, 2147483646);
    public static final int CONFLATED = -1;
    public static final String DEFAULT_BUFFER_PROPERTY_NAME = "kotlinx.coroutines.channels.defaultBuffer";
    public static final int OPTIONAL_CHANNEL = -3;
    public static final int RENDEZVOUS = 0;
    public static final int UNLIMITED = Integer.MAX_VALUE;
    
    private Factory() {}
    
    public final int getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core()
    {
      return CHANNEL_DEFAULT_CAPACITY;
    }
  }
}
