package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\003\n\000\b\000\030\0002\0060\001j\002`\002B\005?\006\002\020\003J\b\020\004\032\0020\005H\026?\006\006"}, d2={"Lkotlinx/coroutines/flow/internal/ChildCancelledException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "()V", "fillInStackTrace", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChildCancelledException
  extends CancellationException
{
  public ChildCancelledException()
  {
    super("Child of the scoped flow was cancelled");
  }
  
  public Throwable fillInStackTrace()
  {
    if (DebugKt.getDEBUG()) {
      super.fillInStackTrace();
    }
    return (Throwable)this;
  }
}
