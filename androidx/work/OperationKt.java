package androidx.work;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032\035\020\000\032\n \002*\004\030\0010\0010\001*\0020\003H?H?\001\000?\006\002\020\004?\002\004\n\002\b\031?\006\005"}, d2={"await", "Landroidx/work/Operation$State$SUCCESS;", "kotlin.jvm.PlatformType", "Landroidx/work/Operation;", "(Landroidx/work/Operation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "work-runtime-ktx_release"}, k=2, mv={1, 1, 16})
public final class OperationKt
{
  public static final Object await(Operation paramOperation, Continuation<? super Operation.State.SUCCESS> paramContinuation)
  {
    Object localObject = paramOperation.getResult();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "result");
    if (((ListenableFuture)localObject).isDone())
    {
      try
      {
        paramOperation = ((ListenableFuture)localObject).get();
      }
      catch (ExecutionException paramContinuation)
      {
        paramOperation = paramContinuation.getCause();
        if (paramOperation == null) {
          paramOperation = (Throwable)paramContinuation;
        }
        throw paramOperation;
      }
    }
    else
    {
      paramOperation = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
      ((ListenableFuture)localObject).addListener((Runnable)new OperationKt.await..inlined.suspendCancellableCoroutine.lambda.1((CancellableContinuation)paramOperation, (ListenableFuture)localObject), (Executor)DirectExecutor.INSTANCE);
      localObject = paramOperation.getResult();
      paramOperation = (Operation)localObject;
      if (localObject == IntrinsicsKt.getCOROUTINE_SUSPENDED())
      {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
        paramOperation = (Operation)localObject;
      }
    }
    return paramOperation;
  }
  
  private static final Object await$$forInline(Operation paramOperation, Continuation paramContinuation)
  {
    ListenableFuture localListenableFuture = paramOperation.getResult();
    Intrinsics.checkExpressionValueIsNotNull(localListenableFuture, "result");
    if (localListenableFuture.isDone())
    {
      try
      {
        paramOperation = localListenableFuture.get();
      }
      catch (ExecutionException paramContinuation)
      {
        paramOperation = paramContinuation.getCause();
        if (paramOperation == null) {
          paramOperation = (Throwable)paramContinuation;
        }
        throw paramOperation;
      }
    }
    else
    {
      InlineMarker.mark(0);
      paramOperation = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
      localListenableFuture.addListener((Runnable)new OperationKt.await..inlined.suspendCancellableCoroutine.lambda.1((CancellableContinuation)paramOperation, localListenableFuture), (Executor)DirectExecutor.INSTANCE);
      paramOperation = paramOperation.getResult();
      if (paramOperation == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended(paramContinuation);
      }
      InlineMarker.mark(1);
    }
    return paramOperation;
  }
}
