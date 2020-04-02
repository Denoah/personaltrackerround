package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;

@Deprecated(level=DeprecationLevel.ERROR, message="This is internal API and may be removed in the future releases")
@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\003\n\000\bg\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H'?\006\006"}, d2={"Lkotlinx/coroutines/ChildHandle;", "Lkotlinx/coroutines/DisposableHandle;", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ChildHandle
  extends DisposableHandle
{
  public abstract boolean childCancelled(Throwable paramThrowable);
}
