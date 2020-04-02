package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\020\t\n\002\b\004\n\002\020\b\n\000\n\002\020\013\n\002\b\002\n\002\020\000\n\002\b\b\n\002\020\016\n\002\b\006\n\002\030\002\b\002\030\0002\b\022\004\022\0020\0000\033B\031\022\006\020\002\032\0020\001\022\b\020\003\032\004\030\0010\000?\006\004\b\004\020\005J\025\020\t\032\0020\b2\006\020\007\032\0020\006?\006\004\b\t\020\nJ,\020\016\032\0020\b2\006\020\007\032\0020\0062\b\020\f\032\004\030\0010\0132\b\020\r\032\004\030\0010\013H?\b?\006\004\b\016\020\017J\032\020\020\032\004\030\0010\0132\006\020\007\032\0020\006H?\b?\006\004\b\020\020\021J$\020\022\032\004\030\0010\0132\006\020\007\032\0020\0062\b\020\r\032\004\030\0010\013H?\b?\006\004\b\022\020\023J\017\020\025\032\0020\024H\026?\006\004\b\025\020\026R\026\020\031\032\0020\b8V@\026X?\004?\006\006\032\004\b\027\020\030?\006\032"}, d2={"Lkotlinx/coroutines/sync/SemaphoreSegment;", "", "id", "prev", "<init>", "(JLkotlinx/coroutines/sync/SemaphoreSegment;)V", "", "index", "", "cancel", "(I)Z", "", "expected", "value", "cas", "(ILjava/lang/Object;Ljava/lang/Object;)Z", "get", "(I)Ljava/lang/Object;", "getAndSet", "(ILjava/lang/Object;)Ljava/lang/Object;", "", "toString", "()Ljava/lang/String;", "getRemoved", "()Z", "removed", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/Segment;"}, k=1, mv={1, 1, 16})
final class SemaphoreSegment
  extends Segment<SemaphoreSegment>
{
  private static final AtomicIntegerFieldUpdater cancelledSlots$FU = AtomicIntegerFieldUpdater.newUpdater(SemaphoreSegment.class, "cancelledSlots");
  AtomicReferenceArray acquirers = new AtomicReferenceArray(SemaphoreKt.access$getSEGMENT_SIZE$p());
  private volatile int cancelledSlots = 0;
  
  public SemaphoreSegment(long paramLong, SemaphoreSegment paramSemaphoreSegment)
  {
    super(paramLong, (Segment)paramSemaphoreSegment);
  }
  
  public final boolean cancel(int paramInt)
  {
    Symbol localSymbol = SemaphoreKt.access$getCANCELLED$p();
    boolean bool;
    if (this.acquirers.getAndSet(paramInt, localSymbol) != SemaphoreKt.access$getRESUMED$p()) {
      bool = true;
    } else {
      bool = false;
    }
    if (cancelledSlots$FU.incrementAndGet(this) == SemaphoreKt.access$getSEGMENT_SIZE$p()) {
      remove();
    }
    return bool;
  }
  
  public final boolean cas(int paramInt, Object paramObject1, Object paramObject2)
  {
    return this.acquirers.compareAndSet(paramInt, paramObject1, paramObject2);
  }
  
  public final Object get(int paramInt)
  {
    return this.acquirers.get(paramInt);
  }
  
  public final Object getAndSet(int paramInt, Object paramObject)
  {
    return this.acquirers.getAndSet(paramInt, paramObject);
  }
  
  public boolean getRemoved()
  {
    boolean bool;
    if (this.cancelledSlots == SemaphoreKt.access$getSEGMENT_SIZE$p()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SemaphoreSegment[id=");
    localStringBuilder.append(getId());
    localStringBuilder.append(", hashCode=");
    localStringBuilder.append(hashCode());
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
