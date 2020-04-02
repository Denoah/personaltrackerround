package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\003\b`\030\0002\0020\001R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\002\020\004R\024\020\005\032\004\030\0010\006X¦\004?\006\006\032\004\b\007\020\b?\006\t"}, d2={"Lkotlinx/coroutines/Incomplete;", "", "isActive", "", "()Z", "list", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Incomplete
{
  public abstract NodeList getList();
  
  public abstract boolean isActive();
}
