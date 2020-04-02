package kotlinx.coroutines.selects;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\n\002\020\000\b\000\030\0002\0020\007B\007?\006\004\b\001\020\002J\r\020\004\032\0020\003?\006\004\b\004\020\005?\006\006"}, d2={"Lkotlinx/coroutines/selects/SeqNumber;", "<init>", "()V", "", "next", "()J", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
public final class SeqNumber
{
  private static final AtomicLongFieldUpdater number$FU = AtomicLongFieldUpdater.newUpdater(SeqNumber.class, "number");
  private volatile long number = 1L;
  
  public SeqNumber() {}
  
  public final long next()
  {
    return number$FU.incrementAndGet(this);
  }
}
