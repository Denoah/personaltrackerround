package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\003\n\002\030\002\n\002\030\002\n\000\bg\030\000*\006\b\000\020\001 \0002\0020\002J\020\020\n\032\0020\0132\006\020\f\032\0020\rH&J\022\020\016\032\004\030\0010\0022\006\020\017\032\0020\020H&J\020\020\021\032\0020\0132\006\020\022\032\0020\023H&J\b\020\024\032\0020\bH&J\032\020\025\032\004\030\0010\0022\016\020\026\032\n\030\0010\027j\004\030\001`\030H&R\030\020\003\032\b\022\004\022\0028\0000\004X¦\004?\006\006\032\004\b\005\020\006R\022\020\007\032\0020\bX¦\004?\006\006\032\004\b\007\020\t?\006\031"}, d2={"Lkotlinx/coroutines/selects/SelectInstance;", "R", "", "completion", "Lkotlin/coroutines/Continuation;", "getCompletion", "()Lkotlin/coroutines/Continuation;", "isSelected", "", "()Z", "disposeOnSelect", "", "handle", "Lkotlinx/coroutines/DisposableHandle;", "performAtomicTrySelect", "desc", "Lkotlinx/coroutines/internal/AtomicDesc;", "resumeSelectWithException", "exception", "", "trySelect", "trySelectOther", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface SelectInstance<R>
{
  public abstract void disposeOnSelect(DisposableHandle paramDisposableHandle);
  
  public abstract Continuation<R> getCompletion();
  
  public abstract boolean isSelected();
  
  public abstract Object performAtomicTrySelect(AtomicDesc paramAtomicDesc);
  
  public abstract void resumeSelectWithException(Throwable paramThrowable);
  
  public abstract boolean trySelect();
  
  public abstract Object trySelectOther(LockFreeLinkedListNode.PrepareOp paramPrepareOp);
}
