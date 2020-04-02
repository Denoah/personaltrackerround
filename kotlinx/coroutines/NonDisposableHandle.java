package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\n\002\020\002\n\000\n\002\020\016\n\000\b?\002\030\0002\0020\0012\0020\002B\007\b\002?\006\002\020\003J\020\020\004\032\0020\0052\006\020\006\032\0020\007H\026J\b\020\b\032\0020\tH\026J\b\020\n\032\0020\013H\026?\006\f"}, d2={"Lkotlinx/coroutines/NonDisposableHandle;", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/ChildHandle;", "()V", "childCancelled", "", "cause", "", "dispose", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class NonDisposableHandle
  implements DisposableHandle, ChildHandle
{
  public static final NonDisposableHandle INSTANCE = new NonDisposableHandle();
  
  private NonDisposableHandle() {}
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    return false;
  }
  
  public void dispose() {}
  
  public String toString()
  {
    return "NonDisposableHandle";
  }
}
