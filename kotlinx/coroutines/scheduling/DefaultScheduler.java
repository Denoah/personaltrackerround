package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\016\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\007\032\0020\bH\026J\b\020\t\032\0020\nH\007J\b\020\013\032\0020\nH\026R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\005\020\006?\006\f"}, d2={"Lkotlinx/coroutines/scheduling/DefaultScheduler;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "()V", "IO", "Lkotlinx/coroutines/CoroutineDispatcher;", "getIO", "()Lkotlinx/coroutines/CoroutineDispatcher;", "close", "", "toDebugString", "", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class DefaultScheduler
  extends ExperimentalCoroutineDispatcher
{
  public static final DefaultScheduler INSTANCE;
  private static final CoroutineDispatcher IO;
  
  static
  {
    DefaultScheduler localDefaultScheduler = new DefaultScheduler();
    INSTANCE = localDefaultScheduler;
    IO = localDefaultScheduler.blocking(SystemPropsKt.systemProp$default("kotlinx.coroutines.io.parallelism", RangesKt.coerceAtLeast(64, SystemPropsKt.getAVAILABLE_PROCESSORS()), 0, 0, 12, null));
  }
  
  private DefaultScheduler()
  {
    super(0, 0, null, 7, null);
  }
  
  public void close()
  {
    throw ((Throwable)new UnsupportedOperationException("DefaultDispatcher cannot be closed"));
  }
  
  public final CoroutineDispatcher getIO()
  {
    return IO;
  }
  
  public final String toDebugString()
  {
    return super.toString();
  }
  
  public String toString()
  {
    return "DefaultDispatcher";
  }
}
