package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentQueue;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\b\n\002\b\004\n\002\020\002\n\002\b\005\n\002\020\t\n\000\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\006\n\002\030\002\n\002\030\002\b\002\030\0002\0020\0352\b\022\004\022\0020\0160\036B\027\022\006\020\002\032\0020\001\022\006\020\003\032\0020\001?\006\004\b\004\020\005J\023\020\007\032\0020\006H?@?\001\000?\006\004\b\007\020\bJ\023\020\t\032\0020\006H?@?\001\000?\006\004\b\t\020\bJ\r\020\n\032\0020\001?\006\004\b\n\020\013J!\020\020\032\0020\0162\006\020\r\032\0020\f2\b\020\017\032\004\030\0010\016H\026?\006\004\b\020\020\021J\017\020\022\032\0020\006H\026?\006\004\b\022\020\023J\017\020\025\032\0020\006H\000?\006\004\b\024\020\023J\017\020\027\032\0020\026H\026?\006\004\b\027\020\030R\026\020\032\032\0020\0018V@\026X?\004?\006\006\032\004\b\031\020\013R\026\020\002\032\0020\0018\002@\002X?\004?\006\006\n\004\b\002\020\033?\002\004\n\002\b\031?\006\034"}, d2={"Lkotlinx/coroutines/sync/SemaphoreImpl;", "", "permits", "acquiredPermits", "<init>", "(II)V", "", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addToQueueAndSuspend", "incPermits", "()I", "", "id", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "prev", "newSegment", "(JLkotlinx/coroutines/sync/SemaphoreSegment;)Lkotlinx/coroutines/sync/SemaphoreSegment;", "release", "()V", "resumeNextFromQueue$kotlinx_coroutines_core", "resumeNextFromQueue", "", "tryAcquire", "()Z", "getAvailablePermits", "availablePermits", "I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/sync/Semaphore;", "Lkotlinx/coroutines/internal/SegmentQueue;"}, k=1, mv={1, 1, 16})
final class SemaphoreImpl
  extends SegmentQueue<SemaphoreSegment>
  implements Semaphore
{
  private static final AtomicIntegerFieldUpdater _availablePermits$FU = AtomicIntegerFieldUpdater.newUpdater(SemaphoreImpl.class, "_availablePermits");
  private static final AtomicLongFieldUpdater deqIdx$FU = AtomicLongFieldUpdater.newUpdater(SemaphoreImpl.class, "deqIdx");
  static final AtomicLongFieldUpdater enqIdx$FU = AtomicLongFieldUpdater.newUpdater(SemaphoreImpl.class, "enqIdx");
  private volatile int _availablePermits;
  private volatile long deqIdx;
  volatile long enqIdx;
  private final int permits;
  
  public SemaphoreImpl(int paramInt1, int paramInt2)
  {
    this.permits = paramInt1;
    int i = 1;
    if (paramInt1 > 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    if (paramInt1 != 0)
    {
      paramInt1 = this.permits;
      if ((paramInt2 >= 0) && (paramInt1 >= paramInt2)) {
        paramInt1 = i;
      } else {
        paramInt1 = 0;
      }
      if (paramInt1 != 0)
      {
        this._availablePermits = (this.permits - paramInt2);
        this.enqIdx = 0L;
        this.deqIdx = 0L;
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("The number of acquired permits should be in 0..");
      localStringBuilder.append(this.permits);
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Semaphore should have at least 1 permit, but had ");
    localStringBuilder.append(this.permits);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  public Object acquire(Continuation<? super Unit> paramContinuation)
  {
    if (_availablePermits$FU.getAndDecrement(this) > 0) {
      return Unit.INSTANCE;
    }
    paramContinuation = addToQueueAndSuspend(paramContinuation);
    if (paramContinuation == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramContinuation;
    }
    return Unit.INSTANCE;
  }
  
  public int getAvailablePermits()
  {
    return Math.max(this._availablePermits, 0);
  }
  
  public final int incPermits()
  {
    int i;
    do
    {
      i = this._availablePermits;
      int j;
      if (i < this.permits) {
        j = 1;
      } else {
        j = 0;
      }
      if (j == 0) {
        break;
      }
    } while (!_availablePermits$FU.compareAndSet(this, i, i + 1));
    return i;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("The number of released permits cannot be greater than ");
    localStringBuilder.append(this.permits);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public SemaphoreSegment newSegment(long paramLong, SemaphoreSegment paramSemaphoreSegment)
  {
    return new SemaphoreSegment(paramLong, paramSemaphoreSegment);
  }
  
  public void release()
  {
    if (incPermits() >= 0) {
      return;
    }
    resumeNextFromQueue$kotlinx_coroutines_core();
  }
  
  public final void resumeNextFromQueue$kotlinx_coroutines_core()
  {
    do
    {
      long l;
      do
      {
        localObject1 = (SemaphoreSegment)getHead();
        l = deqIdx$FU.getAndIncrement(this);
        localObject1 = (SemaphoreSegment)getSegmentAndMoveHead((Segment)localObject1, l / SemaphoreKt.access$getSEGMENT_SIZE$p());
      } while (localObject1 == null);
      int i = (int)(l % SemaphoreKt.access$getSEGMENT_SIZE$p());
      localObject2 = SemaphoreKt.access$getRESUMED$p();
      localObject1 = ((SemaphoreSegment)localObject1).acquirers.getAndSet(i, localObject2);
      if (localObject1 == null) {
        return;
      }
    } while (localObject1 == SemaphoreKt.access$getCANCELLED$p());
    Object localObject1 = (Continuation)localObject1;
    Unit localUnit = Unit.INSTANCE;
    Object localObject2 = Result.Companion;
    ((Continuation)localObject1).resumeWith(Result.constructor-impl(localUnit));
  }
  
  public boolean tryAcquire()
  {
    int i;
    do
    {
      i = this._availablePermits;
      if (i <= 0) {
        return false;
      }
    } while (!_availablePermits$FU.compareAndSet(this, i, i - 1));
    return true;
  }
}
