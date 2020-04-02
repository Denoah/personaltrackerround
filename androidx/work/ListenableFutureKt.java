package androidx.work;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\b\002\n\002\030\002\n\002\b\002\032!\020\000\032\002H\001\"\004\b\000\020\001*\b\022\004\022\002H\0010\002H?H?\001\000?\006\002\020\003?\002\004\n\002\b\031?\006\004"}, d2={"await", "R", "Lcom/google/common/util/concurrent/ListenableFuture;", "(Lcom/google/common/util/concurrent/ListenableFuture;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "work-runtime-ktx_release"}, k=2, mv={1, 1, 16})
public final class ListenableFutureKt
{
  public static final <R> Object await(final ListenableFuture<R> paramListenableFuture, Continuation<? super R> paramContinuation)
  {
    if (paramListenableFuture.isDone()) {
      try
      {
        paramListenableFuture = paramListenableFuture.get();
        return paramListenableFuture;
      }
      catch (ExecutionException paramContinuation)
      {
        paramListenableFuture = paramContinuation.getCause();
        if (paramListenableFuture == null) {
          paramListenableFuture = (Throwable)paramContinuation;
        }
        throw paramListenableFuture;
      }
    }
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    paramListenableFuture.addListener((Runnable)new Runnable()
    {
      /* Error */
      public final void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   4: checkcast 43	kotlin/coroutines/Continuation
        //   7: astore_1
        //   8: aload_0
        //   9: getfield 37	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$this_await$inlined	Lcom/google/common/util/concurrent/ListenableFuture;
        //   12: invokeinterface 49 1 0
        //   17: astore_2
        //   18: getstatic 55	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   21: astore_3
        //   22: aload_1
        //   23: aload_2
        //   24: invokestatic 59	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   27: invokeinterface 63 2 0
        //   32: goto +64 -> 96
        //   35: astore_2
        //   36: aload_2
        //   37: invokevirtual 69	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
        //   40: astore_1
        //   41: aload_1
        //   42: ifnull +6 -> 48
        //   45: goto +5 -> 50
        //   48: aload_2
        //   49: astore_1
        //   50: aload_2
        //   51: instanceof 71
        //   54: ifeq +17 -> 71
        //   57: aload_0
        //   58: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   61: aload_1
        //   62: invokeinterface 77 2 0
        //   67: pop
        //   68: goto +28 -> 96
        //   71: aload_0
        //   72: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   75: checkcast 43	kotlin/coroutines/Continuation
        //   78: astore_3
        //   79: getstatic 55	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   82: astore_2
        //   83: aload_3
        //   84: aload_1
        //   85: invokestatic 83	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
        //   88: invokestatic 59	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   91: invokeinterface 63 2 0
        //   96: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	1
        //   7	78	1	localObject1	Object
        //   17	7	2	localObject2	Object
        //   35	16	2	localObject3	Object
        //   82	1	2	localCompanion	kotlin.Result.Companion
        //   21	63	3	localObject4	Object
        // Exception table:
        //   from	to	target	type
        //   0	32	35	finally
      }
    }, (Executor)DirectExecutor.INSTANCE);
    paramListenableFuture = localCancellableContinuationImpl.getResult();
    if (paramListenableFuture == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramListenableFuture;
  }
  
  private static final Object await$$forInline(ListenableFuture paramListenableFuture, Continuation paramContinuation)
  {
    if (paramListenableFuture.isDone()) {
      try
      {
        paramListenableFuture = paramListenableFuture.get();
        return paramListenableFuture;
      }
      catch (ExecutionException paramContinuation)
      {
        paramListenableFuture = paramContinuation.getCause();
        if (paramListenableFuture == null) {
          paramListenableFuture = (Throwable)paramContinuation;
        }
        throw paramListenableFuture;
      }
    }
    InlineMarker.mark(0);
    CancellableContinuationImpl localCancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(paramContinuation), 1);
    paramListenableFuture.addListener((Runnable)new Runnable()
    {
      /* Error */
      public final void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   4: checkcast 43	kotlin/coroutines/Continuation
        //   7: astore_1
        //   8: aload_0
        //   9: getfield 37	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$this_await$inlined	Lcom/google/common/util/concurrent/ListenableFuture;
        //   12: invokeinterface 49 1 0
        //   17: astore_2
        //   18: getstatic 55	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   21: astore_3
        //   22: aload_1
        //   23: aload_2
        //   24: invokestatic 59	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   27: invokeinterface 63 2 0
        //   32: goto +64 -> 96
        //   35: astore_2
        //   36: aload_2
        //   37: invokevirtual 69	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
        //   40: astore_1
        //   41: aload_1
        //   42: ifnull +6 -> 48
        //   45: goto +5 -> 50
        //   48: aload_2
        //   49: astore_1
        //   50: aload_2
        //   51: instanceof 71
        //   54: ifeq +17 -> 71
        //   57: aload_0
        //   58: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   61: aload_1
        //   62: invokeinterface 77 2 0
        //   67: pop
        //   68: goto +28 -> 96
        //   71: aload_0
        //   72: getfield 35	androidx/work/ListenableFutureKt$await$$inlined$suspendCancellableCoroutine$lambda$1:$cancellableContinuation	Lkotlinx/coroutines/CancellableContinuation;
        //   75: checkcast 43	kotlin/coroutines/Continuation
        //   78: astore_3
        //   79: getstatic 55	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   82: astore_2
        //   83: aload_3
        //   84: aload_1
        //   85: invokestatic 83	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
        //   88: invokestatic 59	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   91: invokeinterface 63 2 0
        //   96: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	1
        //   7	78	1	localObject1	Object
        //   17	7	2	localObject2	Object
        //   35	16	2	localObject3	Object
        //   82	1	2	localCompanion	kotlin.Result.Companion
        //   21	63	3	localObject4	Object
        // Exception table:
        //   from	to	target	type
        //   0	32	35	finally
      }
    }, (Executor)DirectExecutor.INSTANCE);
    paramListenableFuture = localCancellableContinuationImpl.getResult();
    if (paramListenableFuture == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    InlineMarker.mark(1);
    return paramListenableFuture;
  }
}
