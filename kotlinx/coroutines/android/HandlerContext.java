package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

@Metadata(bv={1, 0, 3}, d1={"\000^\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\013\n\002\b\006\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\002\b\002\b\000\030\0002\0020\0012\0020\002B\033\b\026\022\006\020\003\032\0020\004\022\n\b\002\020\005\032\004\030\0010\006?\006\002\020\007B!\b\002\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006\022\006\020\b\032\0020\t?\006\002\020\nJ\034\020\017\032\0020\0202\006\020\021\032\0020\0222\n\020\023\032\0060\024j\002`\025H\026J\023\020\026\032\0020\t2\b\020\027\032\004\030\0010\030H?\002J\b\020\031\032\0020\032H\026J\034\020\033\032\0020\0342\006\020\035\032\0020\0362\n\020\023\032\0060\024j\002`\025H\026J\020\020\037\032\0020\t2\006\020\021\032\0020\022H\026J\036\020 \032\0020\0202\006\020\035\032\0020\0362\f\020!\032\b\022\004\022\0020\0200\"H\026J\b\020#\032\0020\006H\026R\020\020\013\032\004\030\0010\000X?\016?\006\002\n\000R\016\020\003\032\0020\004X?\004?\006\002\n\000R\024\020\f\032\0020\000X?\004?\006\b\n\000\032\004\b\r\020\016R\016\020\b\032\0020\tX?\004?\006\002\n\000R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006$"}, d2={"Lkotlinx/coroutines/android/HandlerContext;", "Lkotlinx/coroutines/android/HandlerDispatcher;", "Lkotlinx/coroutines/Delay;", "handler", "Landroid/os/Handler;", "name", "", "(Landroid/os/Handler;Ljava/lang/String;)V", "invokeImmediately", "", "(Landroid/os/Handler;Ljava/lang/String;Z)V", "_immediate", "immediate", "getImmediate", "()Lkotlinx/coroutines/android/HandlerContext;", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "isDispatchNeeded", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "kotlinx-coroutines-android"}, k=1, mv={1, 1, 16})
public final class HandlerContext
  extends HandlerDispatcher
  implements Delay
{
  private volatile HandlerContext _immediate;
  private final Handler handler;
  private final HandlerContext immediate;
  private final boolean invokeImmediately;
  private final String name;
  
  public HandlerContext(Handler paramHandler, String paramString)
  {
    this(paramHandler, paramString, false);
  }
  
  private HandlerContext(Handler paramHandler, String paramString, boolean paramBoolean)
  {
    super(null);
    this.handler = paramHandler;
    this.name = paramString;
    this.invokeImmediately = paramBoolean;
    paramHandler = localObject;
    if (paramBoolean) {
      paramHandler = this;
    }
    this._immediate = paramHandler;
    paramHandler = this._immediate;
    if (paramHandler == null)
    {
      paramHandler = new HandlerContext(this.handler, this.name, true);
      this._immediate = paramHandler;
    }
    this.immediate = paramHandler;
  }
  
  public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    this.handler.post(paramRunnable);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof HandlerContext)) && (((HandlerContext)paramObject).handler == this.handler)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public HandlerContext getImmediate()
  {
    return this.immediate;
  }
  
  public int hashCode()
  {
    return System.identityHashCode(this.handler);
  }
  
  public DisposableHandle invokeOnTimeout(long paramLong, final Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
    this.handler.postDelayed(paramRunnable, RangesKt.coerceAtMost(paramLong, 4611686018427387903L));
    (DisposableHandle)new DisposableHandle()
    {
      public void dispose()
      {
        HandlerContext.access$getHandler$p(this.this$0).removeCallbacks(paramRunnable);
      }
    };
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    boolean bool1 = this.invokeImmediately;
    boolean bool2 = true;
    boolean bool3 = bool2;
    if (bool1) {
      if ((Intrinsics.areEqual(Looper.myLooper(), this.handler.getLooper()) ^ true)) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
    }
    return bool3;
  }
  
  public void scheduleResumeAfterDelay(long paramLong, final CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
    final Runnable localRunnable = (Runnable)new Runnable()
    {
      public final void run()
      {
        paramCancellableContinuation.resumeUndispatched(this.this$0, Unit.INSTANCE);
      }
    };
    this.handler.postDelayed(localRunnable, RangesKt.coerceAtMost(paramLong, 4611686018427387903L));
    paramCancellableContinuation.invokeOnCancellation((Function1)new Lambda(localRunnable)
    {
      public final void invoke(Throwable paramAnonymousThrowable)
      {
        HandlerContext.access$getHandler$p(this.this$0).removeCallbacks(localRunnable);
      }
    });
  }
  
  public String toString()
  {
    Object localObject = this.name;
    if (localObject != null)
    {
      if (this.invokeImmediately)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.name);
        ((StringBuilder)localObject).append(" [immediate]");
        localObject = ((StringBuilder)localObject).toString();
      }
    }
    else
    {
      localObject = this.handler.toString();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "handler.toString()");
    }
    return localObject;
  }
}
