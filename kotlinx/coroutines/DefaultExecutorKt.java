package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\n\n\000\n\002\030\002\n\002\b\003\"\024\020\000\032\0020\001X?\004?\006\b\n\000\032\004\b\002\020\003?\006\004"}, d2={"DefaultDelay", "Lkotlinx/coroutines/Delay;", "getDefaultDelay", "()Lkotlinx/coroutines/Delay;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DefaultExecutorKt
{
  private static final Delay DefaultDelay = (Delay)DefaultExecutor.INSTANCE;
  
  public static final Delay getDefaultDelay()
  {
    return DefaultDelay;
  }
}
