package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\003\032\0020\004H\026?\006\005"}, d2={"Lkotlinx/coroutines/Active;", "Lkotlinx/coroutines/NotCompleted;", "()V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class Active
  implements NotCompleted
{
  public static final Active INSTANCE = new Active();
  
  private Active() {}
  
  public String toString()
  {
    return "Active";
  }
}
