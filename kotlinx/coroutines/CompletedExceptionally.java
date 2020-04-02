package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\003\n\000\n\002\020\013\n\002\b\005\n\002\020\016\n\002\b\005\n\002\020\000\b\020\030\0002\0020\017B\031\022\006\020\002\032\0020\001\022\b\b\002\020\004\032\0020\003?\006\004\b\005\020\006J\r\020\007\032\0020\003?\006\004\b\007\020\bJ\017\020\n\032\0020\tH\026?\006\004\b\n\020\013R\026\020\002\032\0020\0018\006@\007X?\004?\006\006\n\004\b\002\020\fR\023\020\004\032\0020\0038F@\006?\006\006\032\004\b\r\020\b?\006\016"}, d2={"Lkotlinx/coroutines/CompletedExceptionally;", "", "cause", "", "handled", "<init>", "(Ljava/lang/Throwable;Z)V", "makeHandled", "()Z", "", "toString", "()Ljava/lang/String;", "Ljava/lang/Throwable;", "getHandled", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
public class CompletedExceptionally
{
  private static final AtomicIntegerFieldUpdater _handled$FU = AtomicIntegerFieldUpdater.newUpdater(CompletedExceptionally.class, "_handled");
  private volatile int _handled;
  public final Throwable cause;
  
  public CompletedExceptionally(Throwable paramThrowable, boolean paramBoolean)
  {
    this.cause = paramThrowable;
    this._handled = paramBoolean;
  }
  
  public final boolean getHandled()
  {
    return this._handled;
  }
  
  public final boolean makeHandled()
  {
    return _handled$FU.compareAndSet(this, 0, 1);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('[');
    localStringBuilder.append(this.cause);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
