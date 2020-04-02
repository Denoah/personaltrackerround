package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\003\n\000\b\002\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003?\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026?\006\t"}, d2={"Lkotlinx/coroutines/SupervisorJobImpl;", "Lkotlinx/coroutines/JobImpl;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "childCancelled", "", "cause", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class SupervisorJobImpl
  extends JobImpl
{
  public SupervisorJobImpl(Job paramJob)
  {
    super(paramJob);
  }
  
  public boolean childCancelled(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
    return false;
  }
}
