package androidx.work;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\020\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\b?\001\030\0002\b\022\004\022\0020\0000\0012\0020\002B\007\b\002?\006\002\020\003J\020\020\004\032\0020\0052\006\020\006\032\0020\007H\026J\b\020\b\032\0020\tH\026j\002\b\n?\006\013"}, d2={"Landroidx/work/DirectExecutor;", "", "Ljava/util/concurrent/Executor;", "(Ljava/lang/String;I)V", "execute", "", "command", "Ljava/lang/Runnable;", "toString", "", "INSTANCE", "work-runtime-ktx_release"}, k=1, mv={1, 1, 16})
public enum DirectExecutor
  implements Executor
{
  static
  {
    DirectExecutor localDirectExecutor = new DirectExecutor("INSTANCE", 0);
    INSTANCE = localDirectExecutor;
    $VALUES = new DirectExecutor[] { localDirectExecutor };
  }
  
  private DirectExecutor() {}
  
  public void execute(Runnable paramRunnable)
  {
    Intrinsics.checkParameterIsNotNull(paramRunnable, "command");
    paramRunnable.run();
  }
  
  public String toString()
  {
    return "DirectExecutor";
  }
}
