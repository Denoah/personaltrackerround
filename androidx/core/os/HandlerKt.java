package androidx.core.os;

import android.os.Handler;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\t\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\002\n\002\b\003\0321\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\0042\n\b\002\020\005\032\004\030\0010\0062\016\b\004\020\007\032\b\022\004\022\0020\t0\bH?\b\0321\020\n\032\0020\001*\0020\0022\006\020\013\032\0020\0042\n\b\002\020\005\032\004\030\0010\0062\016\b\004\020\007\032\b\022\004\022\0020\t0\bH?\b?\006\f"}, d2={"postAtTime", "Ljava/lang/Runnable;", "Landroid/os/Handler;", "uptimeMillis", "", "token", "", "action", "Lkotlin/Function0;", "", "postDelayed", "delayInMillis", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class HandlerKt
{
  public static final Runnable postAtTime(Handler paramHandler, long paramLong, Object paramObject, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramHandler, "$this$postAtTime");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      public final void run()
      {
        this.$action.invoke();
      }
    };
    paramHandler.postAtTime(paramFunction0, paramObject, paramLong);
    return paramFunction0;
  }
  
  public static final Runnable postDelayed(Handler paramHandler, long paramLong, Object paramObject, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramHandler, "$this$postDelayed");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      public final void run()
      {
        this.$action.invoke();
      }
    };
    if (paramObject == null) {
      paramHandler.postDelayed(paramFunction0, paramLong);
    } else {
      HandlerCompat.postDelayed(paramHandler, paramFunction0, paramObject, paramLong);
    }
    return paramFunction0;
  }
}
