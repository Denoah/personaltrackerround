package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\032#\020\000\032\0020\0012\n\020\002\032\006\022\002\b\0030\0032\f\020\004\032\b\022\004\022\0020\0010\005H?\b\032\036\020\006\032\0020\001*\b\022\004\022\0020\0010\0032\n\020\007\032\006\022\002\b\0030\003H\000\032>\020\006\032\0020\001\"\004\b\000\020\b*\030\b\001\022\n\022\b\022\004\022\002H\b0\003\022\006\022\004\030\0010\n0\t2\f\020\002\032\b\022\004\022\002H\b0\003H\007?\001\000?\006\002\020\013\032R\020\006\032\0020\001\"\004\b\000\020\f\"\004\b\001\020\b*\036\b\001\022\004\022\002H\f\022\n\022\b\022\004\022\002H\b0\003\022\006\022\004\030\0010\n0\r2\006\020\016\032\002H\f2\f\020\002\032\b\022\004\022\002H\b0\003H\000?\001\000?\006\002\020\017?\002\004\n\002\b\031?\006\020"}, d2={"runSafely", "", "completion", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function0;", "startCoroutineCancellable", "fatalCompletion", "T", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CancellableKt
{
  /* Error */
  private static final void runSafely(kotlin.coroutines.Continuation<?> paramContinuation, kotlin.jvm.functions.Function0<kotlin.Unit> paramFunction0)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 39 1 0
    //   6: pop
    //   7: goto +21 -> 28
    //   10: astore_2
    //   11: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   14: astore_1
    //   15: aload_0
    //   16: aload_2
    //   17: invokestatic 51	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   20: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   23: invokeinterface 61 2 0
    //   28: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	29	0	paramContinuation	kotlin.coroutines.Continuation<?>
    //   0	29	1	paramFunction0	kotlin.jvm.functions.Function0<kotlin.Unit>
    //   10	7	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
  
  /* Error */
  public static final void startCoroutineCancellable(kotlin.coroutines.Continuation<? super kotlin.Unit> paramContinuation, kotlin.coroutines.Continuation<?> paramContinuation1)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 67
    //   3: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: ldc 74
    //   9: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokestatic 80	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   16: astore_2
    //   17: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   20: astore_0
    //   21: aload_2
    //   22: getstatic 86	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   25: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   28: invokestatic 92	kotlinx/coroutines/DispatchedContinuationKt:resumeCancellableWith	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V
    //   31: goto +21 -> 52
    //   34: astore_0
    //   35: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   38: astore_2
    //   39: aload_1
    //   40: aload_0
    //   41: invokestatic 51	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   44: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   47: invokeinterface 61 2 0
    //   52: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	paramContinuation	kotlin.coroutines.Continuation<? super kotlin.Unit>
    //   0	53	1	paramContinuation1	kotlin.coroutines.Continuation<?>
    //   16	23	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	31	34	finally
  }
  
  /* Error */
  public static final <T> void startCoroutineCancellable(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends Object> paramFunction1, kotlin.coroutines.Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 67
    //   3: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: ldc 94
    //   9: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: aload_1
    //   14: invokestatic 98	kotlin/coroutines/intrinsics/IntrinsicsKt:createCoroutineUnintercepted	(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   17: invokestatic 80	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   20: astore_2
    //   21: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   24: astore_0
    //   25: aload_2
    //   26: getstatic 86	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   29: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   32: invokestatic 92	kotlinx/coroutines/DispatchedContinuationKt:resumeCancellableWith	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V
    //   35: goto +21 -> 56
    //   38: astore_0
    //   39: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   42: astore_2
    //   43: aload_1
    //   44: aload_0
    //   45: invokestatic 51	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   48: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   51: invokeinterface 61 2 0
    //   56: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	paramFunction1	kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends Object>
    //   0	57	1	paramContinuation	kotlin.coroutines.Continuation<? super T>
    //   20	23	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	35	38	finally
  }
  
  /* Error */
  public static final <R, T> void startCoroutineCancellable(kotlin.jvm.functions.Function2<? super R, ? super kotlin.coroutines.Continuation<? super T>, ? extends Object> paramFunction2, R paramR, kotlin.coroutines.Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 67
    //   3: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 94
    //   9: invokestatic 73	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: aload_1
    //   14: aload_2
    //   15: invokestatic 102	kotlin/coroutines/intrinsics/IntrinsicsKt:createCoroutineUnintercepted	(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   18: invokestatic 80	kotlin/coroutines/intrinsics/IntrinsicsKt:intercepted	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   21: astore_1
    //   22: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   25: astore_0
    //   26: aload_1
    //   27: getstatic 86	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   30: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   33: invokestatic 92	kotlinx/coroutines/DispatchedContinuationKt:resumeCancellableWith	(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V
    //   36: goto +21 -> 57
    //   39: astore_1
    //   40: getstatic 45	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   43: astore_0
    //   44: aload_2
    //   45: aload_1
    //   46: invokestatic 51	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   49: invokestatic 55	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   52: invokeinterface 61 2 0
    //   57: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	paramFunction2	kotlin.jvm.functions.Function2<? super R, ? super kotlin.coroutines.Continuation<? super T>, ? extends Object>
    //   0	58	1	paramR	R
    //   0	58	2	paramContinuation	kotlin.coroutines.Continuation<? super T>
    // Exception table:
    //   from	to	target	type
    //   12	36	39	finally
  }
}
