package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\bg\030\0002\0020\001J\031\020\002\032\0020\0032\006\020\004\032\0020\005H?@?\001\000?\006\002\020\006J\034\020\007\032\0020\b2\006\020\t\032\0020\0052\n\020\n\032\0060\013j\002`\fH\026J\036\020\r\032\0020\0032\006\020\t\032\0020\0052\f\020\016\032\b\022\004\022\0020\0030\017H&?\002\004\n\002\b\031?\006\020"}, d2={"Lkotlinx/coroutines/Delay;", "", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Delay
{
  public abstract Object delay(long paramLong, Continuation<? super Unit> paramContinuation);
  
  public abstract DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable);
  
  public abstract void scheduleResumeAfterDelay(long paramLong, CancellableContinuation<? super Unit> paramCancellableContinuation);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static Object delay(Delay paramDelay, long paramLong, Continuation<? super Unit> paramContinuation)
    {
      if (paramLong <= 0L) {
        return Unit.INSTANCE;
      }
      CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
      paramDelay.scheduleResumeAfterDelay(paramLong, (CancellableContinuation)localCancellableContinuationImpl);
      paramDelay = localCancellableContinuationImpl.getResult();
      if (paramDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      return paramDelay;
    }
    
    public static DisposableHandle invokeOnTimeout(Delay paramDelay, long paramLong, Runnable paramRunnable)
    {
      Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
      return DefaultExecutorKt.getDefaultDelay().invokeOnTimeout(paramLong, paramRunnable);
    }
  }
}
