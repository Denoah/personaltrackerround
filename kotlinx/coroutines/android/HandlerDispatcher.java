package kotlinx.coroutines.android;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.Delay.DefaultImpls;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\b6\030\0002\0020\0012\0020\002B\007\b\002?\006\002\020\003R\022\020\004\032\0020\000X¦\004?\006\006\032\004\b\005\020\006?\001\001\007?\006\b"}, d2={"Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/android/HandlerContext;", "kotlinx-coroutines-android"}, k=1, mv={1, 1, 16})
public abstract class HandlerDispatcher
  extends MainCoroutineDispatcher
  implements Delay
{
  private HandlerDispatcher() {}
  
  public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
  {
    return Delay.DefaultImpls.delay(this, paramLong, paramContinuation);
  }
  
  public abstract HandlerDispatcher getImmediate();
  
  public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    return Delay.DefaultImpls.invokeOnTimeout(this, paramLong, paramRunnable);
  }
}
