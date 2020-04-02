package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\003\030\0002\0060\001j\002`\0022\b\022\004\022\0020\0000\003B\017\b\020\022\006\020\004\032\0020\005?\006\002\020\006B\031\b\000\022\006\020\004\032\0020\005\022\b\020\007\032\004\030\0010\b?\006\002\020\tJ\n\020\n\032\004\030\0010\000H\026R\022\020\007\032\004\030\0010\b8\000X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/TimeoutCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "Lkotlinx/coroutines/CopyableThrowable;", "message", "", "(Ljava/lang/String;)V", "coroutine", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Lkotlinx/coroutines/Job;)V", "createCopy", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class TimeoutCancellationException
  extends CancellationException
  implements CopyableThrowable<TimeoutCancellationException>
{
  public final Job coroutine;
  
  public TimeoutCancellationException(String paramString)
  {
    this(paramString, null);
  }
  
  public TimeoutCancellationException(String paramString, Job paramJob)
  {
    super(paramString);
    this.coroutine = paramJob;
  }
  
  public TimeoutCancellationException createCopy()
  {
    Object localObject = getMessage();
    if (localObject == null) {
      localObject = "";
    }
    localObject = new TimeoutCancellationException((String)localObject, this.coroutine);
    ((TimeoutCancellationException)localObject).initCause((Throwable)this);
    return localObject;
  }
}
