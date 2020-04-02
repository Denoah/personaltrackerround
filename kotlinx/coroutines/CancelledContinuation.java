package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\013\n\002\b\006\n\002\030\002\b\000\030\0002\0020\fB%\022\n\020\002\032\006\022\002\b\0030\001\022\b\020\004\032\004\030\0010\003\022\006\020\006\032\0020\005?\006\004\b\007\020\bJ\r\020\t\032\0020\005?\006\004\b\t\020\n?\006\013"}, d2={"Lkotlinx/coroutines/CancelledContinuation;", "Lkotlin/coroutines/Continuation;", "continuation", "", "cause", "", "handled", "<init>", "(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;Z)V", "makeResumed", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/CompletedExceptionally;"}, k=1, mv={1, 1, 16})
public final class CancelledContinuation
  extends CompletedExceptionally
{
  private static final AtomicIntegerFieldUpdater _resumed$FU = AtomicIntegerFieldUpdater.newUpdater(CancelledContinuation.class, "_resumed");
  private volatile int _resumed = 0;
  
  public CancelledContinuation(Continuation<?> paramContinuation, Throwable paramThrowable, boolean paramBoolean)
  {
    super(paramThrowable, paramBoolean);
  }
  
  public final boolean makeResumed()
  {
    return _resumed$FU.compareAndSet(this, 0, 1);
  }
}
