package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\004\n\002\020\016\n\000\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\n\032\0020\013H\026R\024\020\005\032\0020\0068VX?\004?\006\006\032\004\b\005\020\007R\024\020\002\032\0020\003X?\004?\006\b\n\000\032\004\b\b\020\t?\006\f"}, d2={"Lkotlinx/coroutines/InactiveNodeList;", "Lkotlinx/coroutines/Incomplete;", "list", "Lkotlinx/coroutines/NodeList;", "(Lkotlinx/coroutines/NodeList;)V", "isActive", "", "()Z", "getList", "()Lkotlinx/coroutines/NodeList;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class InactiveNodeList
  implements Incomplete
{
  private final NodeList list;
  
  public InactiveNodeList(NodeList paramNodeList)
  {
    this.list = paramNodeList;
  }
  
  public NodeList getList()
  {
    return this.list;
  }
  
  public boolean isActive()
  {
    return false;
  }
  
  public String toString()
  {
    String str;
    if (DebugKt.getDEBUG()) {
      str = getList().getString("New");
    } else {
      str = super.toString();
    }
    return str;
  }
}
