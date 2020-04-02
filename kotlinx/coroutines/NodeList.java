package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\016\n\002\b\003\b\000\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\016\020\n\032\0020\0132\006\020\f\032\0020\013J\b\020\r\032\0020\013H\026R\024\020\004\032\0020\0058VX?\004?\006\006\032\004\b\004\020\006R\024\020\007\032\0020\0008VX?\004?\006\006\032\004\b\b\020\t?\006\016"}, d2={"Lkotlinx/coroutines/NodeList;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/Incomplete;", "()V", "isActive", "", "()Z", "list", "getList", "()Lkotlinx/coroutines/NodeList;", "getString", "", "state", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class NodeList
  extends LockFreeLinkedListHead
  implements Incomplete
{
  public NodeList() {}
  
  public NodeList getList()
  {
    return this;
  }
  
  public final String getString(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "state");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("List{");
    localStringBuilder.append(paramString);
    localStringBuilder.append("}[");
    paramString = getNext();
    if (paramString != null)
    {
      paramString = (LockFreeLinkedListNode)paramString;
      int j;
      for (int i = 1; (Intrinsics.areEqual(paramString, (LockFreeLinkedListHead)this) ^ true); i = j)
      {
        j = i;
        if ((paramString instanceof JobNode))
        {
          JobNode localJobNode = (JobNode)paramString;
          if (i != 0) {
            i = 0;
          } else {
            localStringBuilder.append(", ");
          }
          localStringBuilder.append(localJobNode);
          j = i;
        }
        paramString = paramString.getNextNode();
      }
      localStringBuilder.append("]");
      paramString = localStringBuilder.toString();
      Intrinsics.checkExpressionValueIsNotNull(paramString, "StringBuilder().apply(builderAction).toString()");
      return paramString;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public boolean isActive()
  {
    return true;
  }
  
  public String toString()
  {
    String str;
    if (DebugKt.getDEBUG()) {
      str = getString("Active");
    } else {
      str = super.toString();
    }
    return str;
  }
}
