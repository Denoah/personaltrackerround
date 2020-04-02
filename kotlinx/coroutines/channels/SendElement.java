package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\002\n\002\b\006\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\000\030\0002\0020\001B\035\022\b\020\002\032\004\030\0010\003\022\f\020\004\032\b\022\004\022\0020\0060\005?\006\002\020\007J\b\020\n\032\0020\006H\026J\024\020\013\032\0020\0062\n\020\f\032\006\022\002\b\0030\rH\026J\b\020\016\032\0020\017H\026J\024\020\020\032\004\030\0010\0212\b\020\022\032\004\030\0010\023H\026R\026\020\004\032\b\022\004\022\0020\0060\0058\006X?\004?\006\002\n\000R\026\020\002\032\004\030\0010\003X?\004?\006\b\n\000\032\004\b\b\020\t?\006\024"}, d2={"Lkotlinx/coroutines/channels/SendElement;", "Lkotlinx/coroutines/channels/Send;", "pollResult", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class SendElement
  extends Send
{
  public final CancellableContinuation<Unit> cont;
  private final Object pollResult;
  
  public SendElement(Object paramObject, CancellableContinuation<? super Unit> paramCancellableContinuation)
  {
    this.pollResult = paramObject;
    this.cont = paramCancellableContinuation;
  }
  
  public void completeResumeSend()
  {
    this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
  }
  
  public Object getPollResult()
  {
    return this.pollResult;
  }
  
  public void resumeSendClosed(Closed<?> paramClosed)
  {
    Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
    Continuation localContinuation = (Continuation)this.cont;
    Throwable localThrowable = paramClosed.getSendException();
    paramClosed = Result.Companion;
    localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(localThrowable)));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SendElement@");
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    localStringBuilder.append('(');
    localStringBuilder.append(getPollResult());
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
  {
    CancellableContinuation localCancellableContinuation = this.cont;
    Unit localUnit = Unit.INSTANCE;
    if (paramPrepareOp != null) {
      localObject = paramPrepareOp.desc;
    } else {
      localObject = null;
    }
    Object localObject = localCancellableContinuation.tryResume(localUnit, localObject);
    if (localObject != null)
    {
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (localObject == CancellableContinuationImplKt.RESUME_TOKEN) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      if (paramPrepareOp != null) {
        paramPrepareOp.finishPrepare();
      }
      return CancellableContinuationImplKt.RESUME_TOKEN;
    }
    return null;
  }
}
