package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000j\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\013\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\b\n\002\b\007\n\002\020\000\n\002\b\f\b\027\030\0002\0020D:\005KLMNOB\007?\006\004\b\001\020\002J\031\020\006\032\0020\0052\n\020\004\032\0060\000j\002`\003?\006\004\b\006\020\007J,\020\013\032\0020\t2\n\020\004\032\0060\000j\002`\0032\016\b\004\020\n\032\b\022\004\022\0020\t0\bH?\b?\006\004\b\013\020\fJ4\020\017\032\0020\t2\n\020\004\032\0060\000j\002`\0032\026\020\016\032\022\022\b\022\0060\000j\002`\003\022\004\022\0020\t0\rH?\b?\006\004\b\017\020\020JD\020\021\032\0020\t2\n\020\004\032\0060\000j\002`\0032\026\020\016\032\022\022\b\022\0060\000j\002`\003\022\004\022\0020\t0\r2\016\b\004\020\n\032\b\022\004\022\0020\t0\bH?\b?\006\004\b\021\020\022J'\020\024\032\0020\t2\n\020\004\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\003H\001?\006\004\b\024\020\025J\031\020\026\032\0020\t2\n\020\004\032\0060\000j\002`\003?\006\004\b\026\020\027J-\020\033\032\n\030\0010\000j\004\030\001`\0032\n\020\030\032\0060\000j\002`\0032\b\020\032\032\004\030\0010\031H\002?\006\004\b\033\020\034J)\020\037\032\b\022\004\022\0028\0000\036\"\f\b\000\020\035*\0060\000j\002`\0032\006\020\004\032\0028\000?\006\004\b\037\020 J\027\020\"\032\f\022\b\022\0060\000j\002`\0030!?\006\004\b\"\020#J\023\020$\032\0060\000j\002`\003H\002?\006\004\b$\020%J\033\020&\032\0020\0052\n\020\023\032\0060\000j\002`\003H\002?\006\004\b&\020\007J\033\020'\032\0020\0052\n\020\023\032\0060\000j\002`\003H\002?\006\004\b'\020\007J\017\020(\032\0020\005H\001?\006\004\b(\020\002J\r\020)\032\0020\005?\006\004\b)\020\002J,\020+\032\0020*2\n\020\004\032\0060\000j\002`\0032\016\b\004\020\n\032\b\022\004\022\0020\t0\bH?\b?\006\004\b+\020,J\023\020-\032\0060\000j\002`\003H\002?\006\004\b-\020%J\017\020.\032\0020\tH\026?\006\004\b.\020/J\032\0200\032\004\030\0018\000\"\006\b\000\020\035\030\001H?\b?\006\004\b0\0201J.\0202\032\004\030\0018\000\"\006\b\000\020\035\030\0012\022\020\016\032\016\022\004\022\0028\000\022\004\022\0020\t0\rH?\b?\006\004\b2\0203J\025\0204\032\n\030\0010\000j\004\030\001`\003?\006\004\b4\020%J\017\0206\032\00205H\002?\006\004\b6\0207J\017\0209\032\00208H\026?\006\004\b9\020:J/\020=\032\0020<2\n\020\004\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\0032\006\020;\032\0020*H\001?\006\004\b=\020>J'\020B\032\0020\0052\n\020?\032\0060\000j\002`\0032\n\020\023\032\0060\000j\002`\003H\000?\006\004\b@\020AR\023\020C\032\0020\t8F@\006?\006\006\032\004\bC\020/R\023\020\023\032\0020D8F@\006?\006\006\032\004\bE\0201R\027\020G\032\0060\000j\002`\0038F@\006?\006\006\032\004\bF\020%R\023\020?\032\0020D8F@\006?\006\006\032\004\bH\0201R\027\020J\032\0060\000j\002`\0038F@\006?\006\006\032\004\bI\020%?\006P"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "<init>", "()V", "Lkotlinx/coroutines/internal/Node;", "node", "", "addLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlin/Function0;", "", "condition", "addLastIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Z", "Lkotlin/Function1;", "predicate", "addLastIfPrev", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;)Z", "addLastIfPrevAndIf", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Z", "next", "addNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "addOneIfEmpty", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "_prev", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "correctPrev", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "T", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "describeAddLast", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "describeRemoveFirst", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "findHead", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "finishAdd", "finishRemove", "helpDelete", "helpRemove", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "makeCondAddOp", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlin/jvm/functions/Function0;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "markPrev", "remove", "()Z", "removeFirstIfIsInstanceOf", "()Ljava/lang/Object;", "removeFirstIfIsInstanceOfOrPeekIf", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "removeFirstOrNull", "Lkotlinx/coroutines/internal/Removed;", "removed", "()Lkotlinx/coroutines/internal/Removed;", "", "toString", "()Ljava/lang/String;", "condAdd", "", "tryCondAddNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;)I", "prev", "validateNode$kotlinx_coroutines_core", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "validateNode", "isRemoved", "", "getNext", "getNextNode", "nextNode", "getPrev", "getPrevNode", "prevNode", "AbstractAtomicDesc", "AddLastDesc", "CondAddOp", "PrepareOp", "RemoveFirstDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class LockFreeLinkedListNode
{
  static final AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_next");
  static final AtomicReferenceFieldUpdater _prev$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_prev");
  private static final AtomicReferenceFieldUpdater _removedRef$FU = AtomicReferenceFieldUpdater.newUpdater(LockFreeLinkedListNode.class, Object.class, "_removedRef");
  volatile Object _next = this;
  volatile Object _prev = this;
  private volatile Object _removedRef = null;
  
  public LockFreeLinkedListNode() {}
  
  private final LockFreeLinkedListNode correctPrev(LockFreeLinkedListNode paramLockFreeLinkedListNode, OpDescriptor paramOpDescriptor)
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)null;
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = localLockFreeLinkedListNode1;
    Object localObject2;
    label142:
    label152:
    do
    {
      for (;;)
      {
        Object localObject1 = paramLockFreeLinkedListNode._next;
        if (localObject1 == paramOpDescriptor) {
          return paramLockFreeLinkedListNode;
        }
        if ((localObject1 instanceof OpDescriptor))
        {
          ((OpDescriptor)localObject1).perform(paramLockFreeLinkedListNode);
        }
        else
        {
          if ((localObject1 instanceof Removed))
          {
            if (localLockFreeLinkedListNode2 != null)
            {
              paramLockFreeLinkedListNode.markPrev();
              _next$FU.compareAndSet(localLockFreeLinkedListNode2, paramLockFreeLinkedListNode, ((Removed)localObject1).ref);
              paramLockFreeLinkedListNode = localLockFreeLinkedListNode2;
              break;
            }
            paramLockFreeLinkedListNode = LockFreeLinkedListKt.unwrap(paramLockFreeLinkedListNode._prev);
            continue;
          }
          localObject2 = this._prev;
          if ((localObject2 instanceof Removed)) {
            return null;
          }
          if (localObject1 == (LockFreeLinkedListNode)this) {
            break label152;
          }
          if (localObject1 == null) {
            break label142;
          }
          localObject1 = (LockFreeLinkedListNode)localObject1;
          localLockFreeLinkedListNode2 = paramLockFreeLinkedListNode;
          paramLockFreeLinkedListNode = (LockFreeLinkedListNode)localObject1;
        }
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
      if (localObject2 == paramLockFreeLinkedListNode) {
        return null;
      }
    } while ((!_prev$FU.compareAndSet(this, localObject2, paramLockFreeLinkedListNode)) || ((paramLockFreeLinkedListNode._prev instanceof Removed)));
    return null;
  }
  
  private final LockFreeLinkedListNode findHead()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)this;
    Object localObject = localLockFreeLinkedListNode1;
    for (;;)
    {
      if ((localObject instanceof LockFreeLinkedListHead)) {
        return localObject;
      }
      LockFreeLinkedListNode localLockFreeLinkedListNode2 = ((LockFreeLinkedListNode)localObject).getNextNode();
      localObject = localLockFreeLinkedListNode2;
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (localLockFreeLinkedListNode2 != localLockFreeLinkedListNode1) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          break;
        }
        localObject = localLockFreeLinkedListNode2;
      }
    }
    throw ((Throwable)new AssertionError());
  }
  
  private final void finishAdd(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    Object localObject;
    do
    {
      localObject = paramLockFreeLinkedListNode._prev;
      if (((localObject instanceof Removed)) || (getNext() != paramLockFreeLinkedListNode)) {
        break;
      }
    } while (!_prev$FU.compareAndSet(paramLockFreeLinkedListNode, localObject, this));
    if ((getNext() instanceof Removed)) {
      if (localObject != null) {
        paramLockFreeLinkedListNode.correctPrev((LockFreeLinkedListNode)localObject, null);
      } else {
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
      }
    }
  }
  
  private final void finishRemove(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    helpDelete();
    paramLockFreeLinkedListNode.correctPrev(LockFreeLinkedListKt.unwrap(this._prev), null);
  }
  
  private final LockFreeLinkedListNode markPrev()
  {
    Object localObject1;
    Object localObject2;
    do
    {
      localObject1 = this._prev;
      if ((localObject1 instanceof Removed)) {
        return ((Removed)localObject1).ref;
      }
      if (localObject1 == (LockFreeLinkedListNode)this)
      {
        localObject2 = findHead();
      }
      else
      {
        if (localObject1 == null) {
          break;
        }
        localObject2 = (LockFreeLinkedListNode)localObject1;
      }
      localObject2 = ((LockFreeLinkedListNode)localObject2).removed();
    } while (!_prev$FU.compareAndSet(this, localObject1, localObject2));
    return (LockFreeLinkedListNode)localObject1;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  private final Removed removed()
  {
    Removed localRemoved = (Removed)this._removedRef;
    if (localRemoved == null)
    {
      localRemoved = new Removed(this);
      _removedRef$FU.lazySet(this, localRemoved);
    }
    return localRemoved;
  }
  
  public final void addLast(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    Object localObject;
    do
    {
      localObject = getPrev();
      if (localObject == null) {
        break;
      }
    } while (!((LockFreeLinkedListNode)localObject).addNext(paramLockFreeLinkedListNode, this));
    return;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final boolean addLastIf(final LockFreeLinkedListNode paramLockFreeLinkedListNode, Function0<Boolean> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "condition");
    CondAddOp localCondAddOp = (CondAddOp)new CondAddOp(paramFunction0)
    {
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
        if (((Boolean)this.$condition.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
    int i;
    do
    {
      paramFunction0 = getPrev();
      if (paramFunction0 == null) {
        break label66;
      }
      i = ((LockFreeLinkedListNode)paramFunction0).tryCondAddNext(paramLockFreeLinkedListNode, this, localCondAddOp);
      if (i == 1) {
        break;
      }
    } while (i != 2);
    return false;
    return true;
    label66:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final boolean addLastIfPrev(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function1<? super LockFreeLinkedListNode, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Object localObject;
    do
    {
      localObject = getPrev();
      if (localObject == null) {
        break;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if (!((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return false;
      }
    } while (!((LockFreeLinkedListNode)localObject).addNext(paramLockFreeLinkedListNode, this));
    return true;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final boolean addLastIfPrevAndIf(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function1<? super LockFreeLinkedListNode, Boolean> paramFunction1, Function0<Boolean> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "condition");
    paramFunction0 = (CondAddOp)new CondAddOp(paramFunction0)
    {
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
        if (((Boolean)this.$condition.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
    int i;
    do
    {
      Object localObject = getPrev();
      if (localObject == null) {
        break label98;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if (!((Boolean)paramFunction1.invoke(localObject)).booleanValue()) {
        return false;
      }
      i = ((LockFreeLinkedListNode)localObject).tryCondAddNext(paramLockFreeLinkedListNode, this, paramFunction0);
      if (i == 1) {
        break;
      }
    } while (i != 2);
    return false;
    return true;
    label98:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final boolean addNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "node");
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
    _prev$FU.lazySet(paramLockFreeLinkedListNode1, this);
    _next$FU.lazySet(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
    if (!_next$FU.compareAndSet(this, paramLockFreeLinkedListNode2, paramLockFreeLinkedListNode1)) {
      return false;
    }
    paramLockFreeLinkedListNode1.finishAdd(paramLockFreeLinkedListNode2);
    return true;
  }
  
  public final boolean addOneIfEmpty(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    _prev$FU.lazySet(paramLockFreeLinkedListNode, this);
    _next$FU.lazySet(paramLockFreeLinkedListNode, this);
    do
    {
      if (getNext() != (LockFreeLinkedListNode)this) {
        return false;
      }
    } while (!_next$FU.compareAndSet(this, this, paramLockFreeLinkedListNode));
    paramLockFreeLinkedListNode.finishAdd(this);
    return true;
  }
  
  public final <T extends LockFreeLinkedListNode> AddLastDesc<T> describeAddLast(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "node");
    return new AddLastDesc(this, paramT);
  }
  
  public final RemoveFirstDesc<LockFreeLinkedListNode> describeRemoveFirst()
  {
    return new RemoveFirstDesc(this);
  }
  
  public final Object getNext()
  {
    for (;;)
    {
      Object localObject = this._next;
      if (!(localObject instanceof OpDescriptor)) {
        return localObject;
      }
      ((OpDescriptor)localObject).perform(this);
    }
  }
  
  public final LockFreeLinkedListNode getNextNode()
  {
    return LockFreeLinkedListKt.unwrap(getNext());
  }
  
  public final Object getPrev()
  {
    for (;;)
    {
      Object localObject = this._prev;
      if ((localObject instanceof Removed)) {
        return localObject;
      }
      if (localObject == null) {
        break;
      }
      LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)localObject;
      if (localLockFreeLinkedListNode.getNext() == (LockFreeLinkedListNode)this) {
        return localObject;
      }
      correctPrev(localLockFreeLinkedListNode, null);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final LockFreeLinkedListNode getPrevNode()
  {
    return LockFreeLinkedListKt.unwrap(getPrev());
  }
  
  public final void helpDelete()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = (LockFreeLinkedListNode)null;
    Object localObject1 = markPrev();
    Object localObject2 = this._next;
    if (localObject2 != null)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode2 = ((Removed)localObject2).ref;
      localObject2 = localLockFreeLinkedListNode1;
      label157:
      label167:
      do
      {
        for (;;)
        {
          Object localObject3 = localLockFreeLinkedListNode2.getNext();
          if ((localObject3 instanceof Removed))
          {
            localLockFreeLinkedListNode2.markPrev();
            localLockFreeLinkedListNode2 = ((Removed)localObject3).ref;
          }
          else
          {
            localObject3 = ((LockFreeLinkedListNode)localObject1).getNext();
            if ((localObject3 instanceof Removed))
            {
              if (localObject2 != null)
              {
                ((LockFreeLinkedListNode)localObject1).markPrev();
                _next$FU.compareAndSet(localObject2, localObject1, ((Removed)localObject3).ref);
                localObject1 = localObject2;
                break;
              }
              localObject1 = LockFreeLinkedListKt.unwrap(((LockFreeLinkedListNode)localObject1)._prev);
              continue;
            }
            if (localObject3 == (LockFreeLinkedListNode)this) {
              break label167;
            }
            if (localObject3 == null) {
              break label157;
            }
            localObject3 = (LockFreeLinkedListNode)localObject3;
            if (localObject3 == localLockFreeLinkedListNode2) {
              return;
            }
            localObject2 = localObject1;
            localObject1 = localObject3;
          }
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
      } while (!_next$FU.compareAndSet(localObject1, this, localLockFreeLinkedListNode2));
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Removed");
  }
  
  public final void helpRemove()
  {
    Object localObject1 = getNext();
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof Removed)) {
      localObject2 = null;
    }
    localObject2 = (Removed)localObject2;
    if (localObject2 != null)
    {
      finishRemove(((Removed)localObject2).ref);
      return;
    }
    throw ((Throwable)new IllegalStateException("Must be invoked on a removed node".toString()));
  }
  
  public final boolean isRemoved()
  {
    return getNext() instanceof Removed;
  }
  
  public final CondAddOp makeCondAddOp(LockFreeLinkedListNode paramLockFreeLinkedListNode, Function0<Boolean> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "node");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "condition");
    (CondAddOp)new CondAddOp(paramFunction0)
    {
      public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
        if (((Boolean)this.$condition.invoke()).booleanValue()) {
          paramAnonymousLockFreeLinkedListNode = null;
        } else {
          paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
        }
        return paramAnonymousLockFreeLinkedListNode;
      }
    };
  }
  
  public boolean remove()
  {
    Object localObject;
    LockFreeLinkedListNode localLockFreeLinkedListNode;
    Removed localRemoved;
    do
    {
      localObject = getNext();
      if ((localObject instanceof Removed)) {
        return false;
      }
      if (localObject == (LockFreeLinkedListNode)this) {
        return false;
      }
      if (localObject == null) {
        break;
      }
      localLockFreeLinkedListNode = (LockFreeLinkedListNode)localObject;
      localRemoved = localLockFreeLinkedListNode.removed();
    } while (!_next$FU.compareAndSet(this, localObject, localRemoved));
    finishRemove(localLockFreeLinkedListNode);
    return true;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final LockFreeLinkedListNode removeFirstOrNull()
  {
    for (;;)
    {
      Object localObject = getNext();
      if (localObject == null) {
        break;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if (localObject == (LockFreeLinkedListNode)this) {
        return null;
      }
      if (((LockFreeLinkedListNode)localObject).remove()) {
        return localObject;
      }
      ((LockFreeLinkedListNode)localObject).helpDelete();
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append('@');
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    return localStringBuilder.toString();
  }
  
  public final int tryCondAddNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2, CondAddOp paramCondAddOp)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "node");
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
    Intrinsics.checkParameterIsNotNull(paramCondAddOp, "condAdd");
    _prev$FU.lazySet(paramLockFreeLinkedListNode1, this);
    _next$FU.lazySet(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
    paramCondAddOp.oldNext = paramLockFreeLinkedListNode2;
    if (!_next$FU.compareAndSet(this, paramLockFreeLinkedListNode2, paramCondAddOp)) {
      return 0;
    }
    int i;
    if (paramCondAddOp.perform(this) == null) {
      i = 1;
    } else {
      i = 2;
    }
    return i;
  }
  
  public final void validateNode$kotlinx_coroutines_core(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "prev");
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int i = 1;
    int j;
    if (bool)
    {
      if (paramLockFreeLinkedListNode1 == this._prev) {
        j = 1;
      } else {
        j = 0;
      }
      if (j == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramLockFreeLinkedListNode2 == this._next) {
        j = i;
      } else {
        j = 0;
      }
      if (j == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\002\b\002\b&\030\0002\0020\001B\005?\006\002\020\002J\034\020\n\032\0020\0132\n\020\f\032\006\022\002\b\0030\r2\b\020\016\032\004\030\0010\017J\026\020\016\032\004\030\0010\0172\n\020\020\032\0060\004j\002`\005H\024J \020\021\032\0020\0132\n\020\020\032\0060\004j\002`\0052\n\020\022\032\0060\004j\002`\005H$J\020\020\023\032\0020\0132\006\020\024\032\0020\025H&J\022\020\026\032\004\030\0010\0172\006\020\024\032\0020\025H\026J\024\020\027\032\004\030\0010\0172\n\020\f\032\006\022\002\b\0030\rJ\034\020\030\032\0020\0312\n\020\020\032\0060\004j\002`\0052\006\020\022\032\0020\017H\024J\030\020\032\032\n\030\0010\004j\004\030\001`\0052\006\020\f\032\0020\033H\024J \020\034\032\0020\0172\n\020\020\032\0060\004j\002`\0052\n\020\022\032\0060\004j\002`\005H$R\032\020\003\032\n\030\0010\004j\004\030\001`\005X¤\004?\006\006\032\004\b\006\020\007R\032\020\b\032\n\030\0010\004j\004\030\001`\005X¤\004?\006\006\032\004\b\t\020\007?\006\035"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "()V", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "originalNext", "getOriginalNext", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "", "affected", "finishOnSuccess", "next", "finishPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "onPrepare", "prepare", "retry", "", "takeAffectedNode", "Lkotlinx/coroutines/internal/OpDescriptor;", "updatedNext", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static abstract class AbstractAtomicDesc
    extends AtomicDesc
  {
    public AbstractAtomicDesc() {}
    
    public final void complete(AtomicOp<?> paramAtomicOp, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramAtomicOp, "op");
      int i;
      if (paramObject == null) {
        i = 1;
      } else {
        i = 0;
      }
      LockFreeLinkedListNode localLockFreeLinkedListNode1 = getAffectedNode();
      if (localLockFreeLinkedListNode1 != null)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode2 = getOriginalNext();
        if (localLockFreeLinkedListNode2 != null)
        {
          if (i != 0) {
            paramObject = updatedNext(localLockFreeLinkedListNode1, localLockFreeLinkedListNode2);
          } else {
            paramObject = localLockFreeLinkedListNode2;
          }
          if ((LockFreeLinkedListNode._next$FU.compareAndSet(localLockFreeLinkedListNode1, paramAtomicOp, paramObject)) && (i != 0)) {
            finishOnSuccess(localLockFreeLinkedListNode1, localLockFreeLinkedListNode2);
          }
          return;
        }
        paramAtomicOp = (AbstractAtomicDesc)this;
        if ((DebugKt.getASSERTIONS_ENABLED()) && ((i ^ 0x1) == 0)) {
          throw ((Throwable)new AssertionError());
        }
        return;
      }
      paramAtomicOp = (AbstractAtomicDesc)this;
      if ((DebugKt.getASSERTIONS_ENABLED()) && ((i ^ 0x1) == 0)) {
        throw ((Throwable)new AssertionError());
      }
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      return null;
    }
    
    protected abstract void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2);
    
    public abstract void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp);
    
    protected abstract LockFreeLinkedListNode getAffectedNode();
    
    protected abstract LockFreeLinkedListNode getOriginalNext();
    
    public Object onPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Intrinsics.checkParameterIsNotNull(paramPrepareOp, "prepareOp");
      finishPrepare(paramPrepareOp);
      return null;
    }
    
    public final Object prepare(AtomicOp<?> paramAtomicOp)
    {
      Intrinsics.checkParameterIsNotNull(paramAtomicOp, "op");
      for (;;)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode = takeAffectedNode((OpDescriptor)paramAtomicOp);
        if (localLockFreeLinkedListNode == null) {
          break label218;
        }
        Object localObject1 = localLockFreeLinkedListNode._next;
        if (localObject1 == paramAtomicOp) {
          return null;
        }
        if (paramAtomicOp.isDecided()) {
          return null;
        }
        if ((localObject1 instanceof OpDescriptor))
        {
          localObject1 = (OpDescriptor)localObject1;
          if (paramAtomicOp.isEarlierThan((OpDescriptor)localObject1)) {
            return AtomicKt.RETRY_ATOMIC;
          }
          ((OpDescriptor)localObject1).perform(localLockFreeLinkedListNode);
        }
        else
        {
          Object localObject2 = failure(localLockFreeLinkedListNode);
          if (localObject2 != null) {
            return localObject2;
          }
          if (!retry(localLockFreeLinkedListNode, localObject1)) {
            if (localObject1 != null)
            {
              localObject2 = new LockFreeLinkedListNode.PrepareOp(localLockFreeLinkedListNode, (LockFreeLinkedListNode)localObject1, this);
              if (!LockFreeLinkedListNode._next$FU.compareAndSet(localLockFreeLinkedListNode, localObject1, localObject2)) {
                continue;
              }
              try
              {
                Object localObject3 = ((LockFreeLinkedListNode.PrepareOp)localObject2).perform(localLockFreeLinkedListNode);
                if (localObject3 != LockFreeLinkedList_commonKt.REMOVE_PREPARED)
                {
                  if (DebugKt.getASSERTIONS_ENABLED())
                  {
                    int i;
                    if (localObject3 == null) {
                      i = 1;
                    } else {
                      i = 0;
                    }
                    if (i == 0)
                    {
                      paramAtomicOp = new java/lang/AssertionError;
                      paramAtomicOp.<init>();
                      throw ((Throwable)paramAtomicOp);
                    }
                  }
                  return null;
                }
              }
              finally
              {
                LockFreeLinkedListNode._next$FU.compareAndSet(localLockFreeLinkedListNode, localObject2, localObject1);
              }
            }
          }
        }
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
      label218:
      return AtomicKt.RETRY_ATOMIC;
    }
    
    protected boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      Intrinsics.checkParameterIsNotNull(paramObject, "next");
      return false;
    }
    
    protected LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramOpDescriptor, "op");
      paramOpDescriptor = getAffectedNode();
      if (paramOpDescriptor == null) {
        Intrinsics.throwNpe();
      }
      return paramOpDescriptor;
    }
    
    protected abstract Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\f\n\002\030\002\b\026\030\000*\f\b\000\020\003*\0060\001j\002`\0022\0020!B\033\022\n\020\004\032\0060\001j\002`\002\022\006\020\005\032\0028\000?\006\004\b\006\020\007J'\020\013\032\0020\n2\n\020\b\032\0060\001j\002`\0022\n\020\t\032\0060\001j\002`\002H\024?\006\004\b\013\020\007J\027\020\016\032\0020\n2\006\020\r\032\0020\fH\026?\006\004\b\016\020\017J#\020\022\032\0020\0212\n\020\b\032\0060\001j\002`\0022\006\020\t\032\0020\020H\024?\006\004\b\022\020\023J\037\020\026\032\n\030\0010\001j\004\030\001`\0022\006\020\025\032\0020\024H\004?\006\004\b\026\020\027J'\020\030\032\0020\0202\n\020\b\032\0060\001j\002`\0022\n\020\t\032\0060\001j\002`\002H\024?\006\004\b\030\020\031R\036\020\034\032\n\030\0010\001j\004\030\001`\0028D@\004X?\004?\006\006\032\004\b\032\020\033R\026\020\005\032\0028\0008\006@\007X?\004?\006\006\n\004\b\005\020\035R\036\020\037\032\n\030\0010\001j\004\030\001`\0028D@\004X?\004?\006\006\032\004\b\036\020\033R\032\020\004\032\0060\001j\002`\0028\006@\007X?\004?\006\006\n\004\b\004\020\035?\006 "}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "T", "queue", "node", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "next", "", "finishOnSuccess", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", "", "retry", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getOriginalNext", "originalNext", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k=1, mv={1, 1, 16})
  public static class AddLastDesc<T extends LockFreeLinkedListNode>
    extends LockFreeLinkedListNode.AbstractAtomicDesc
  {
    private static final AtomicReferenceFieldUpdater _affectedNode$FU = AtomicReferenceFieldUpdater.newUpdater(AddLastDesc.class, Object.class, "_affectedNode");
    private volatile Object _affectedNode;
    public final T node;
    public final LockFreeLinkedListNode queue;
    
    public AddLastDesc(LockFreeLinkedListNode paramLockFreeLinkedListNode, T paramT)
    {
      this.queue = paramLockFreeLinkedListNode;
      this.node = paramT;
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        paramLockFreeLinkedListNode = this.node._next;
        paramT = this.node;
        int i;
        if ((paramLockFreeLinkedListNode == paramT) && (paramT._prev == this.node)) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      this._affectedNode = null;
    }
    
    protected void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "affected");
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
      LockFreeLinkedListNode.access$finishAdd(this.node, this.queue);
    }
    
    public void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Intrinsics.checkParameterIsNotNull(paramPrepareOp, "prepareOp");
      _affectedNode$FU.compareAndSet(this, null, paramPrepareOp.affected);
    }
    
    protected final LockFreeLinkedListNode getAffectedNode()
    {
      return (LockFreeLinkedListNode)this._affectedNode;
    }
    
    protected final LockFreeLinkedListNode getOriginalNext()
    {
      return this.queue;
    }
    
    protected boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      Intrinsics.checkParameterIsNotNull(paramObject, "next");
      boolean bool;
      if (paramObject != this.queue) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    protected final LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramOpDescriptor, "op");
      Object localObject1;
      do
      {
        Object localObject3;
        for (;;)
        {
          localObject1 = this.queue._prev;
          if (localObject1 == null) {
            break label97;
          }
          localObject1 = (LockFreeLinkedListNode)localObject1;
          Object localObject2 = ((LockFreeLinkedListNode)localObject1)._next;
          localObject3 = this.queue;
          if (localObject2 == localObject3) {
            return localObject1;
          }
          if (localObject2 == paramOpDescriptor) {
            return localObject1;
          }
          if (!(localObject2 instanceof OpDescriptor)) {
            break;
          }
          localObject3 = (OpDescriptor)localObject2;
          if (paramOpDescriptor.isEarlierThan((OpDescriptor)localObject3)) {
            return null;
          }
          ((OpDescriptor)localObject3).perform(localObject1);
        }
        localObject1 = LockFreeLinkedListNode.access$correctPrev((LockFreeLinkedListNode)localObject3, (LockFreeLinkedListNode)localObject1, paramOpDescriptor);
      } while (localObject1 == null);
      return localObject1;
      label97:
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    
    protected Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "affected");
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
      paramLockFreeLinkedListNode2 = this.node;
      LockFreeLinkedListNode._prev$FU.compareAndSet(paramLockFreeLinkedListNode2, this.node, paramLockFreeLinkedListNode1);
      paramLockFreeLinkedListNode1 = this.node;
      LockFreeLinkedListNode._next$FU.compareAndSet(paramLockFreeLinkedListNode1, this.node, this.queue);
      return this.node;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\000\n\000\b!\030\0002\f\022\b\022\0060\002j\002`\0030\001B\021\022\n\020\004\032\0060\002j\002`\003?\006\002\020\005J\036\020\007\032\0020\b2\n\020\t\032\0060\002j\002`\0032\b\020\n\032\004\030\0010\013H\026R\024\020\004\032\0060\002j\002`\0038\006X?\004?\006\002\n\000R\032\020\006\032\n\030\0010\002j\004\030\001`\0038\006@\006X?\016?\006\002\n\000?\006\f"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "newNode", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "oldNext", "complete", "", "affected", "failure", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static abstract class CondAddOp
    extends AtomicOp<LockFreeLinkedListNode>
  {
    public final LockFreeLinkedListNode newNode;
    public LockFreeLinkedListNode oldNext;
    
    public CondAddOp(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      this.newNode = paramLockFreeLinkedListNode;
    }
    
    public void complete(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      int i;
      if (paramObject == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramObject = this.newNode;
      } else {
        paramObject = this.oldNext;
      }
      if ((paramObject != null) && (LockFreeLinkedListNode._next$FU.compareAndSet(paramLockFreeLinkedListNode, this, paramObject)) && (i != 0))
      {
        paramObject = this.newNode;
        paramLockFreeLinkedListNode = this.oldNext;
        if (paramLockFreeLinkedListNode == null) {
          Intrinsics.throwNpe();
        }
        LockFreeLinkedListNode.access$finishAdd(paramObject, paramLockFreeLinkedListNode);
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\016\n\000\030\0002\0020\001B%\022\n\020\002\032\0060\003j\002`\004\022\n\020\005\032\0060\003j\002`\004\022\006\020\006\032\0020\007?\006\002\020\bJ\006\020\r\032\0020\016J\024\020\017\032\004\030\0010\0202\b\020\002\032\004\030\0010\020H\026J\b\020\021\032\0020\022H\026R\024\020\002\032\0060\003j\002`\0048\006X?\004?\006\002\n\000R\030\020\t\032\006\022\002\b\0030\n8VX?\004?\006\006\032\004\b\013\020\fR\020\020\006\032\0020\0078\006X?\004?\006\002\n\000R\024\020\005\032\0060\003j\002`\0048\006X?\004?\006\002\n\000?\006\023"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "next", "desc", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "finishPrepare", "", "perform", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class PrepareOp
    extends OpDescriptor
  {
    public final LockFreeLinkedListNode affected;
    public final LockFreeLinkedListNode.AbstractAtomicDesc desc;
    public final LockFreeLinkedListNode next;
    
    public PrepareOp(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2, LockFreeLinkedListNode.AbstractAtomicDesc paramAbstractAtomicDesc)
    {
      this.affected = paramLockFreeLinkedListNode1;
      this.next = paramLockFreeLinkedListNode2;
      this.desc = paramAbstractAtomicDesc;
    }
    
    public final void finishPrepare()
    {
      this.desc.finishPrepare(this);
    }
    
    public AtomicOp<?> getAtomicOp()
    {
      return this.desc.getAtomicOp();
    }
    
    public Object perform(Object paramObject)
    {
      boolean bool1 = DebugKt.getASSERTIONS_ENABLED();
      boolean bool2 = true;
      if (bool1)
      {
        int i;
        if (paramObject == this.affected) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      if (paramObject != null)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode = (LockFreeLinkedListNode)paramObject;
        paramObject = this.desc.onPrepare(this);
        if (paramObject == LockFreeLinkedList_commonKt.REMOVE_PREPARED)
        {
          paramObject = LockFreeLinkedListNode.access$removed(this.next);
          if (LockFreeLinkedListNode._next$FU.compareAndSet(localLockFreeLinkedListNode, this, paramObject)) {
            localLockFreeLinkedListNode.helpDelete();
          }
          return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
        }
        if (paramObject != null) {
          getAtomicOp().decide(paramObject);
        } else {
          bool2 = getAtomicOp().isDecided();
        }
        if (bool2) {
          paramObject = this.next;
        } else {
          paramObject = getAtomicOp();
        }
        LockFreeLinkedListNode._next$FU.compareAndSet(localLockFreeLinkedListNode, this, paramObject);
        return null;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PrepareOp(op=");
      localStringBuilder.append(getAtomicOp());
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\021\n\002\030\002\b\026\030\000*\004\b\000\020\0012\0020(B\023\022\n\020\004\032\0060\002j\002`\003?\006\004\b\005\020\006J\035\020\t\032\004\030\0010\b2\n\020\007\032\0060\002j\002`\003H\024?\006\004\b\t\020\nJ'\020\r\032\0020\f2\n\020\007\032\0060\002j\002`\0032\n\020\013\032\0060\002j\002`\003H\004?\006\004\b\r\020\016J\027\020\021\032\0020\f2\006\020\020\032\0020\017H\026?\006\004\b\021\020\022J#\020\024\032\0020\0232\n\020\007\032\0060\002j\002`\0032\006\020\013\032\0020\bH\004?\006\004\b\024\020\025J\037\020\030\032\n\030\0010\002j\004\030\001`\0032\006\020\027\032\0020\026H\004?\006\004\b\030\020\031J'\020\032\032\0020\b2\n\020\007\032\0060\002j\002`\0032\n\020\013\032\0060\002j\002`\003H\004?\006\004\b\032\020\033R\036\020\036\032\n\030\0010\002j\004\030\001`\0038D@\004X?\004?\006\006\032\004\b\034\020\035R\036\020 \032\n\030\0010\002j\004\030\001`\0038D@\004X?\004?\006\006\032\004\b\037\020\035R\032\020\004\032\0060\002j\002`\0038\006@\007X?\004?\006\006\n\004\b\004\020!R\031\020&\032\0028\0008F@\006?\006\f\022\004\b$\020%\032\004\b\"\020#?\006'"}, d2={"Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "T", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "queue", "<init>", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "affected", "", "failure", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "next", "", "finishOnSuccess", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "prepareOp", "finishPrepare", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "", "retry", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/OpDescriptor;", "op", "takeAffectedNode", "(Lkotlinx/coroutines/internal/OpDescriptor;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "updatedNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Ljava/lang/Object;", "getAffectedNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "affectedNode", "getOriginalNext", "originalNext", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "getResult", "()Ljava/lang/Object;", "result$annotations", "()V", "result", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AbstractAtomicDesc;"}, k=1, mv={1, 1, 16})
  public static class RemoveFirstDesc<T>
    extends LockFreeLinkedListNode.AbstractAtomicDesc
  {
    private static final AtomicReferenceFieldUpdater _affectedNode$FU = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_affectedNode");
    private static final AtomicReferenceFieldUpdater _originalNext$FU = AtomicReferenceFieldUpdater.newUpdater(RemoveFirstDesc.class, Object.class, "_originalNext");
    private volatile Object _affectedNode;
    private volatile Object _originalNext;
    public final LockFreeLinkedListNode queue;
    
    public RemoveFirstDesc(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      this.queue = paramLockFreeLinkedListNode;
      this._affectedNode = null;
      this._originalNext = null;
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      if (paramLockFreeLinkedListNode == this.queue) {
        paramLockFreeLinkedListNode = LockFreeLinkedListKt.getLIST_EMPTY();
      } else {
        paramLockFreeLinkedListNode = null;
      }
      return paramLockFreeLinkedListNode;
    }
    
    protected final void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "affected");
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
      LockFreeLinkedListNode.access$finishRemove(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
    }
    
    public void finishPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Intrinsics.checkParameterIsNotNull(paramPrepareOp, "prepareOp");
      _affectedNode$FU.compareAndSet(this, null, paramPrepareOp.affected);
      _originalNext$FU.compareAndSet(this, null, paramPrepareOp.next);
    }
    
    protected final LockFreeLinkedListNode getAffectedNode()
    {
      return (LockFreeLinkedListNode)this._affectedNode;
    }
    
    protected final LockFreeLinkedListNode getOriginalNext()
    {
      return (LockFreeLinkedListNode)this._originalNext;
    }
    
    public final T getResult()
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = getAffectedNode();
      if (localLockFreeLinkedListNode == null) {
        Intrinsics.throwNpe();
      }
      return (Object)localLockFreeLinkedListNode;
    }
    
    protected final boolean retry(LockFreeLinkedListNode paramLockFreeLinkedListNode, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      Intrinsics.checkParameterIsNotNull(paramObject, "next");
      if (!(paramObject instanceof Removed)) {
        return false;
      }
      paramLockFreeLinkedListNode.helpDelete();
      return true;
    }
    
    protected final LockFreeLinkedListNode takeAffectedNode(OpDescriptor paramOpDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramOpDescriptor, "op");
      LockFreeLinkedListNode localLockFreeLinkedListNode = this.queue;
      Object localObject;
      for (;;)
      {
        localObject = localLockFreeLinkedListNode._next;
        if (!(localObject instanceof OpDescriptor)) {
          break;
        }
        localObject = (OpDescriptor)localObject;
        if (paramOpDescriptor.isEarlierThan((OpDescriptor)localObject)) {
          return null;
        }
        ((OpDescriptor)localObject).perform(this.queue);
      }
      if (localObject != null) {
        return (LockFreeLinkedListNode)localObject;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    
    protected final Object updatedNext(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "affected");
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
      return LockFreeLinkedListNode.access$removed(paramLockFreeLinkedListNode2);
    }
  }
}
