package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\016\n\000\b?\002\030\0002\0020\0012\0020\002B\007\b\002?\006\002\020\003J\b\020\004\032\0020\005H\024J\b\020\006\032\0020\007H\026?\006\b"}, d2={"Lkotlin/time/MonotonicTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource;", "()V", "read", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class MonotonicTimeSource
  extends AbstractLongTimeSource
  implements TimeSource
{
  public static final MonotonicTimeSource INSTANCE = new MonotonicTimeSource();
  
  private MonotonicTimeSource()
  {
    super(TimeUnit.NANOSECONDS);
  }
  
  protected long read()
  {
    return System.nanoTime();
  }
  
  public String toString()
  {
    return "TimeSource(System.nanoTime())";
  }
}
