package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b \030\0002\0020\001B\005?\006\002\020\002J\b\020\007\032\0020\bH&J\024\020\t\032\0020\b2\n\020\n\032\006\022\002\b\0030\013H&J\024\020\f\032\004\030\0010\r2\b\020\016\032\004\030\0010\017H&R\024\020\003\032\004\030\0010\004X¦\004?\006\006\032\004\b\005\020\006?\006\020"}, d2={"Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "()V", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class Send
  extends LockFreeLinkedListNode
{
  public Send() {}
  
  public abstract void completeResumeSend();
  
  public abstract Object getPollResult();
  
  public abstract void resumeSendClosed(Closed<?> paramClosed);
  
  public abstract Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp);
}
