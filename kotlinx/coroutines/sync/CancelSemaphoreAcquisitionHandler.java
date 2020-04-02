package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlinx.coroutines.CancelHandler;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007?\006\002\020\bJ\023\020\t\032\0020\n2\b\020\013\032\004\030\0010\fH?\002J\b\020\r\032\0020\016H\026R\016\020\006\032\0020\007X?\004?\006\002\n\000R\016\020\004\032\0020\005X?\004?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\017"}, d2={"Lkotlinx/coroutines/sync/CancelSemaphoreAcquisitionHandler;", "Lkotlinx/coroutines/CancelHandler;", "semaphore", "Lkotlinx/coroutines/sync/SemaphoreImpl;", "segment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "index", "", "(Lkotlinx/coroutines/sync/SemaphoreImpl;Lkotlinx/coroutines/sync/SemaphoreSegment;I)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class CancelSemaphoreAcquisitionHandler
  extends CancelHandler
{
  private final int index;
  private final SemaphoreSegment segment;
  private final SemaphoreImpl semaphore;
  
  public CancelSemaphoreAcquisitionHandler(SemaphoreImpl paramSemaphoreImpl, SemaphoreSegment paramSemaphoreSegment, int paramInt)
  {
    this.semaphore = paramSemaphoreImpl;
    this.segment = paramSemaphoreSegment;
    this.index = paramInt;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    if (this.semaphore.incPermits() >= 0) {
      return;
    }
    if (this.segment.cancel(this.index)) {
      return;
    }
    this.semaphore.resumeNextFromQueue$kotlinx_coroutines_core();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CancelSemaphoreAcquisitionHandler[");
    localStringBuilder.append(this.semaphore);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.segment);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.index);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
