package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\000\n\002\020\016\n\002\b\005\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\020\001\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\002\b\002\030\0002\0020\0012\0020\002B\033\022\b\020\003\032\004\030\0010\004\022\n\b\002\020\005\032\004\030\0010\006?\006\002\020\007J\031\020\013\032\0020\f2\006\020\r\032\0020\016H?@?\001\000?\006\002\020\017J\034\020\020\032\0020\0212\006\020\022\032\0020\0232\n\020\024\032\0060\025j\002`\026H\026J\034\020\027\032\0020\0302\006\020\031\032\0020\0162\n\020\024\032\0060\025j\002`\026H\026J\020\020\032\032\0020\0332\006\020\022\032\0020\023H\026J\b\020\034\032\0020\021H\002J\036\020\035\032\0020\0212\006\020\031\032\0020\0162\f\020\036\032\b\022\004\022\0020\f0\037H\026J\b\020 \032\0020\006H\026R\020\020\003\032\004\030\0010\004X?\004?\006\002\n\000R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000R\024\020\b\032\0020\0018VX?\004?\006\006\032\004\b\t\020\n?\002\004\n\002\b\031?\006!"}, d2={"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcher;", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "cause", "", "errorHint", "", "(Ljava/lang/Throwable;Ljava/lang/String;)V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "isDispatchNeeded", "", "missing", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class MissingMainCoroutineDispatcher
  extends MainCoroutineDispatcher
  implements Delay
{
  private final Throwable cause;
  private final String errorHint;
  
  public MissingMainCoroutineDispatcher(Throwable paramThrowable, String paramString)
  {
    this.cause = paramThrowable;
    this.errorHint = paramString;
  }
  
  private final Void missing()
  {
    if (this.cause != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Module with the Main dispatcher had failed to initialize");
      String str = this.errorHint;
      Object localObject;
      if (str != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(". ");
        ((StringBuilder)localObject).append(str);
        localObject = ((StringBuilder)localObject).toString();
        if (localObject != null) {}
      }
      else
      {
        localObject = "";
      }
      localStringBuilder.append(localObject);
      throw ((Throwable)new IllegalStateException(localStringBuilder.toString(), this.cause));
    }
    throw ((Throwable)new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'"));
  }
  
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    missing();
    throw null;
  }
  
  public Void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    missing();
    throw null;
  }
  
  public MainCoroutineDispatcher getImmediate()
  {
    return (MainCoroutineDispatcher)this;
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    missing();
    throw null;
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    missing();
    throw null;
  }
  
  public Void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
    missing();
    throw null;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Main[missing");
    Object localObject;
    if (this.cause != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(", cause=");
      ((StringBuilder)localObject).append(this.cause);
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
