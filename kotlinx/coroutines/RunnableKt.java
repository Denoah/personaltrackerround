package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\000\032\035\020\000\032\0060\001j\002`\0022\016\b\004\020\003\032\b\022\004\022\0020\0050\004H?\b*\n\020\000\"\0020\0012\0020\001?\006\006"}, d2={"Runnable", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "Lkotlin/Function0;", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class RunnableKt
{
  public static final Runnable Runnable(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    (Runnable)new Runnable()
    {
      public final void run()
      {
        this.$block.invoke();
      }
    };
  }
}
