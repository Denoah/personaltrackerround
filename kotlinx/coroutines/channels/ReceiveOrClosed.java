package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\000\n\002\020\000\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\b`\030\000*\006\b\000\020\001 \0002\0020\002J\025\020\006\032\0020\0072\006\020\b\032\0028\000H&?\006\002\020\tJ'\020\n\032\004\030\0010\0132\006\020\b\032\0028\0002\016\020\f\032\n\030\0010\rj\004\030\001`\016H&?\006\002\020\017R\022\020\003\032\0020\002X¦\004?\006\006\032\004\b\004\020\005?\006\020"}, d2={"Lkotlinx/coroutines/channels/ReceiveOrClosed;", "E", "", "offerResult", "getOfferResult", "()Ljava/lang/Object;", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ReceiveOrClosed<E>
{
  public abstract void completeResumeReceive(E paramE);
  
  public abstract Object getOfferResult();
  
  public abstract Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp);
}
