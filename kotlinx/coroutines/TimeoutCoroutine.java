package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\020\002\n\000\b\002\030\000*\004\b\000\020\001*\n\b\001\020\002 \000*\002H\0012\b\022\004\022\002H\0020\0032\0060\004j\002`\005B\033\022\006\020\006\032\0020\007\022\f\020\b\032\b\022\004\022\0028\0000\t?\006\002\020\nJ\r\020\013\032\0020\fH\020?\006\002\b\rJ\b\020\016\032\0020\017H\026R\020\020\006\032\0020\0078\006X?\004?\006\002\n\000?\006\020"}, d2={"Lkotlinx/coroutines/TimeoutCoroutine;", "U", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "time", "", "uCont", "Lkotlin/coroutines/Continuation;", "(JLkotlin/coroutines/Continuation;)V", "nameString", "", "nameString$kotlinx_coroutines_core", "run", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class TimeoutCoroutine<U, T extends U>
  extends ScopeCoroutine<T>
  implements Runnable
{
  public final long time;
  
  public TimeoutCoroutine(long paramLong, Continuation<? super U> paramContinuation)
  {
    super(paramContinuation.getContext(), paramContinuation);
    this.time = paramLong;
  }
  
  public String nameString$kotlinx_coroutines_core()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.nameString$kotlinx_coroutines_core());
    localStringBuilder.append("(timeMillis=");
    localStringBuilder.append(this.time);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public void run()
  {
    cancelCoroutine((Throwable)TimeoutKt.TimeoutCancellationException(this.time, (Job)this));
  }
}
