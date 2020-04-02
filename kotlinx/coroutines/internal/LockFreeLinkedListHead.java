package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\b\026\030\0002\0020\001B\005?\006\002\020\002J-\020\006\032\0020\007\"\016\b\000\020\b\030\001*\0060\001j\002`\t2\022\020\n\032\016\022\004\022\002H\b\022\004\022\0020\0070\013H?\bJ\006\020\f\032\0020\004J\r\020\r\032\0020\007H\000?\006\002\b\016R\021\020\003\032\0020\0048F?\006\006\032\004\b\003\020\005?\006\017"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "()V", "isEmpty", "", "()Z", "forEach", "", "T", "Lkotlinx/coroutines/internal/Node;", "block", "Lkotlin/Function1;", "remove", "validate", "validate$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class LockFreeLinkedListHead
  extends LockFreeLinkedListNode
{
  public LockFreeLinkedListHead() {}
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (getNext() == (LockFreeLinkedListHead)this) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean remove()
  {
    throw ((Throwable)new UnsupportedOperationException());
  }
  
  public final void validate$kotlinx_coroutines_core()
  {
    Object localObject1 = (LockFreeLinkedListNode)this;
    Object localObject2 = getNext();
    if (localObject2 != null)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode;
      for (localObject2 = (LockFreeLinkedListNode)localObject2; (Intrinsics.areEqual(localObject2, (LockFreeLinkedListHead)this) ^ true); localObject2 = localLockFreeLinkedListNode)
      {
        localLockFreeLinkedListNode = ((LockFreeLinkedListNode)localObject2).getNextNode();
        ((LockFreeLinkedListNode)localObject2).validateNode$kotlinx_coroutines_core((LockFreeLinkedListNode)localObject1, localLockFreeLinkedListNode);
        localObject1 = localObject2;
      }
      localObject2 = getNext();
      if (localObject2 != null)
      {
        validateNode$kotlinx_coroutines_core((LockFreeLinkedListNode)localObject1, (LockFreeLinkedListNode)localObject2);
        return;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
}
