package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\003\032\0020\004H\026?\006\005"}, d2={"Lkotlinx/coroutines/scheduling/NanoTimeSource;", "Lkotlinx/coroutines/scheduling/TimeSource;", "()V", "nanoTime", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class NanoTimeSource
  extends TimeSource
{
  public static final NanoTimeSource INSTANCE = new NanoTimeSource();
  
  private NanoTimeSource() {}
  
  public long nanoTime()
  {
    return System.nanoTime();
  }
}
