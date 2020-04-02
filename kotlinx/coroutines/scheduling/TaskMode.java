package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004?\006\005"}, d2={"Lkotlinx/coroutines/scheduling/TaskMode;", "", "(Ljava/lang/String;I)V", "NON_BLOCKING", "PROBABLY_BLOCKING", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public enum TaskMode
{
  static
  {
    TaskMode localTaskMode1 = new TaskMode("NON_BLOCKING", 0);
    NON_BLOCKING = localTaskMode1;
    TaskMode localTaskMode2 = new TaskMode("PROBABLY_BLOCKING", 1);
    PROBABLY_BLOCKING = localTaskMode2;
    $VALUES = new TaskMode[] { localTaskMode1, localTaskMode2 };
  }
  
  private TaskMode() {}
}
