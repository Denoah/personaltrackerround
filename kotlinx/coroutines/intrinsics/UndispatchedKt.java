package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\003\n\002\020\013\n\000\n\002\030\002\n\000\0329\020\000\032\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\0042\032\020\005\032\026\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\006H?\b\032>\020\b\032\0020\001\"\004\b\000\020\002*\030\b\001\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0062\f\020\003\032\b\022\004\022\002H\0020\004H\000?\001\000?\006\002\020\t\032R\020\b\032\0020\001\"\004\b\000\020\n\"\004\b\001\020\002*\036\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0132\006\020\f\032\002H\n2\f\020\003\032\b\022\004\022\002H\0020\004H\000?\001\000?\006\002\020\r\032>\020\016\032\0020\001\"\004\b\000\020\002*\030\b\001\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0062\f\020\003\032\b\022\004\022\002H\0020\004H\000?\001\000?\006\002\020\t\032R\020\016\032\0020\001\"\004\b\000\020\n\"\004\b\001\020\002*\036\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\0132\006\020\f\032\002H\n2\f\020\003\032\b\022\004\022\002H\0020\004H\000?\001\000?\006\002\020\r\032Y\020\017\032\004\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\n*\b\022\004\022\002H\0020\0202\006\020\f\032\002H\n2'\020\005\032#\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\013?\006\002\b\021H\000?\001\000?\006\002\020\022\032Y\020\023\032\004\030\0010\007\"\004\b\000\020\002\"\004\b\001\020\n*\b\022\004\022\002H\0020\0202\006\020\f\032\002H\n2'\020\005\032#\b\001\022\004\022\002H\n\022\n\022\b\022\004\022\002H\0020\004\022\006\022\004\030\0010\0070\013?\006\002\b\021H\000?\001\000?\006\002\020\022\032?\020\024\032\004\030\0010\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0202\022\020\025\032\016\022\004\022\0020\026\022\004\022\0020\0270\0062\016\020\030\032\n\022\006\022\004\030\0010\0070\031H?\b?\002\004\n\002\b\031?\006\032"}, d2={"startDirect", "", "T", "completion", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function1;", "", "startCoroutineUndispatched", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "startCoroutineUnintercepted", "startUndispatchedOrReturn", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/internal/ScopeCoroutine;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "startUndispatchedOrReturnIgnoreTimeout", "undispatchedResult", "shouldThrow", "", "", "startBlock", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class UndispatchedKt
{
  public static final <T> void startCoroutineUndispatched(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "$this$startCoroutineUndispatched");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    Continuation localContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      CoroutineContext localCoroutineContext = paramContinuation.getContext();
      paramContinuation = ThreadContextKt.updateThreadContext(localCoroutineContext, null);
      return;
    }
    finally
    {
      try
      {
        paramFunction1 = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(localContinuation);
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
        if (paramFunction1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return;
        }
        paramContinuation = Result.Companion;
        localContinuation.resumeWith(Result.constructor-impl(paramFunction1));
      }
      finally
      {
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
      }
      paramContinuation = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramFunction1)));
    }
  }
  
  public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "$this$startCoroutineUndispatched");
    Intrinsics.checkParameterIsNotNull(paramContinuation, "completion");
    Continuation localContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      CoroutineContext localCoroutineContext = paramContinuation.getContext();
      paramContinuation = ThreadContextKt.updateThreadContext(localCoroutineContext, null);
      return;
    }
    finally
    {
      try
      {
        paramFunction2 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramR, localContinuation);
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
        if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return;
        }
        paramR = Result.Companion;
        localContinuation.resumeWith(Result.constructor-impl(paramFunction2));
      }
      finally
      {
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramContinuation);
      }
      paramFunction2 = Result.Companion;
      localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(paramR)));
    }
  }
  
  /* Error */
  public static final <T> void startCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 119
    //   3: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: ldc 49
    //   9: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_1
    //   13: invokestatic 55	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineCreated	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   16: astore_1
    //   17: aload_0
    //   18: iconst_1
    //   19: invokestatic 73	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   22: checkcast 75	kotlin/jvm/functions/Function1
    //   25: aload_1
    //   26: invokeinterface 79 2 0
    //   31: astore_2
    //   32: aload_2
    //   33: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   36: if_acmpeq +38 -> 74
    //   39: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   42: astore_0
    //   43: aload_1
    //   44: aload_2
    //   45: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   48: invokeinterface 102 2 0
    //   53: goto +21 -> 74
    //   56: astore_0
    //   57: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   60: astore_2
    //   61: aload_1
    //   62: aload_0
    //   63: invokestatic 108	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   66: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   69: invokeinterface 102 2 0
    //   74: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	paramFunction1	Function1<? super Continuation<? super T>, ? extends Object>
    //   0	75	1	paramContinuation	Continuation<? super T>
    //   31	30	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   17	32	56	finally
  }
  
  /* Error */
  public static final <R, T> void startCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 119
    //   3: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 49
    //   9: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_2
    //   13: invokestatic 55	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineCreated	(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    //   16: astore_2
    //   17: aload_0
    //   18: iconst_2
    //   19: invokestatic 73	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   22: checkcast 113	kotlin/jvm/functions/Function2
    //   25: aload_1
    //   26: aload_2
    //   27: invokeinterface 116 3 0
    //   32: astore_0
    //   33: aload_0
    //   34: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   37: if_acmpeq +38 -> 75
    //   40: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   43: astore_1
    //   44: aload_2
    //   45: aload_0
    //   46: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   49: invokeinterface 102 2 0
    //   54: goto +21 -> 75
    //   57: astore_0
    //   58: getstatic 95	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   61: astore_1
    //   62: aload_2
    //   63: aload_0
    //   64: invokestatic 108	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   67: invokestatic 98	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   70: invokeinterface 102 2 0
    //   75: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   0	76	1	paramR	R
    //   0	76	2	paramContinuation	Continuation<? super T>
    // Exception table:
    //   from	to	target	type
    //   17	33	57	finally
  }
  
  private static final <T> void startDirect(Continuation<? super T> paramContinuation, Function1<? super Continuation<? super T>, ? extends Object> paramFunction1)
  {
    paramContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    try
    {
      paramFunction1 = paramFunction1.invoke(paramContinuation);
      if (paramFunction1 != IntrinsicsKt.getCOROUTINE_SUSPENDED())
      {
        Result.Companion localCompanion = Result.Companion;
        paramContinuation.resumeWith(Result.constructor-impl(paramFunction1));
      }
      return;
    }
    finally
    {
      paramFunction1 = Result.Companion;
      paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(localThrowable)));
    }
  }
  
  /* Error */
  public static final <T, R> Object startUndispatchedOrReturn(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 123
    //   3: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 124
    //   9: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokevirtual 130	kotlinx/coroutines/internal/ScopeCoroutine:initParentJob$kotlinx_coroutines_core	()V
    //   16: aload_0
    //   17: checkcast 57	kotlin/coroutines/Continuation
    //   20: astore_3
    //   21: aload_2
    //   22: iconst_2
    //   23: invokestatic 73	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   26: checkcast 113	kotlin/jvm/functions/Function2
    //   29: aload_1
    //   30: aload_3
    //   31: invokeinterface 116 3 0
    //   36: astore_1
    //   37: goto +16 -> 53
    //   40: astore_1
    //   41: new 132	kotlinx/coroutines/CompletedExceptionally
    //   44: dup
    //   45: aload_1
    //   46: iconst_0
    //   47: iconst_2
    //   48: aconst_null
    //   49: invokespecial 136	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   52: astore_1
    //   53: aload_1
    //   54: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   57: if_acmpne +10 -> 67
    //   60: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   63: astore_0
    //   64: goto +57 -> 121
    //   67: aload_0
    //   68: aload_1
    //   69: invokevirtual 139	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   72: astore_1
    //   73: aload_1
    //   74: getstatic 145	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   77: if_acmpne +10 -> 87
    //   80: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   83: astore_0
    //   84: goto +37 -> 121
    //   87: aload_1
    //   88: instanceof 132
    //   91: ifeq +25 -> 116
    //   94: aload_1
    //   95: checkcast 132	kotlinx/coroutines/CompletedExceptionally
    //   98: astore_1
    //   99: aload_1
    //   100: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   103: astore_2
    //   104: aload_1
    //   105: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   108: aload_0
    //   109: getfield 152	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   112: invokestatic 158	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   115: athrow
    //   116: aload_1
    //   117: invokestatic 161	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   120: astore_0
    //   121: aload_0
    //   122: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	123	1	paramR	R
    //   0	123	2	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   20	11	3	localContinuation	Continuation
    // Exception table:
    //   from	to	target	type
    //   16	37	40	finally
  }
  
  /* Error */
  public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc -92
    //   3: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 124
    //   9: invokestatic 48	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokevirtual 130	kotlinx/coroutines/internal/ScopeCoroutine:initParentJob$kotlinx_coroutines_core	()V
    //   16: iconst_0
    //   17: istore_3
    //   18: aload_0
    //   19: checkcast 57	kotlin/coroutines/Continuation
    //   22: astore 4
    //   24: aload_2
    //   25: iconst_2
    //   26: invokestatic 73	kotlin/jvm/internal/TypeIntrinsics:beforeCheckcastToFunctionOfArity	(Ljava/lang/Object;I)Ljava/lang/Object;
    //   29: checkcast 113	kotlin/jvm/functions/Function2
    //   32: aload_1
    //   33: aload 4
    //   35: invokeinterface 116 3 0
    //   40: astore_1
    //   41: goto +16 -> 57
    //   44: astore_1
    //   45: new 132	kotlinx/coroutines/CompletedExceptionally
    //   48: dup
    //   49: aload_1
    //   50: iconst_0
    //   51: iconst_2
    //   52: aconst_null
    //   53: invokespecial 136	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   56: astore_1
    //   57: aload_1
    //   58: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   61: if_acmpne +10 -> 71
    //   64: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   67: astore_0
    //   68: goto +111 -> 179
    //   71: aload_0
    //   72: aload_1
    //   73: invokevirtual 139	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   76: astore_2
    //   77: aload_2
    //   78: getstatic 145	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   81: if_acmpne +10 -> 91
    //   84: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   87: astore_0
    //   88: goto +91 -> 179
    //   91: aload_2
    //   92: instanceof 132
    //   95: ifeq +79 -> 174
    //   98: aload_2
    //   99: checkcast 132	kotlinx/coroutines/CompletedExceptionally
    //   102: astore 4
    //   104: aload 4
    //   106: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   109: astore_2
    //   110: aload_2
    //   111: instanceof 166
    //   114: ifeq +14 -> 128
    //   117: aload_2
    //   118: checkcast 166	kotlinx/coroutines/TimeoutCancellationException
    //   121: getfield 170	kotlinx/coroutines/TimeoutCancellationException:coroutine	Lkotlinx/coroutines/Job;
    //   124: aload_0
    //   125: if_acmpeq +5 -> 130
    //   128: iconst_1
    //   129: istore_3
    //   130: iload_3
    //   131: ifne +30 -> 161
    //   134: aload_1
    //   135: instanceof 132
    //   138: ifne +8 -> 146
    //   141: aload_1
    //   142: astore_0
    //   143: goto +36 -> 179
    //   146: aload_1
    //   147: checkcast 132	kotlinx/coroutines/CompletedExceptionally
    //   150: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   153: aload_0
    //   154: getfield 152	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   157: invokestatic 158	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   160: athrow
    //   161: aload 4
    //   163: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   166: aload_0
    //   167: getfield 152	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   170: invokestatic 158	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   173: athrow
    //   174: aload_2
    //   175: invokestatic 161	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   178: astore_0
    //   179: aload_0
    //   180: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	181	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	181	1	paramR	R
    //   0	181	2	paramFunction2	Function2<? super R, ? super Continuation<? super T>, ? extends Object>
    //   17	114	3	i	int
    //   22	140	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   18	41	44	finally
  }
  
  /* Error */
  private static final <T> Object undispatchedResult(kotlinx.coroutines.internal.ScopeCoroutine<? super T> paramScopeCoroutine, Function1<? super Throwable, Boolean> paramFunction1, kotlin.jvm.functions.Function0<? extends Object> paramFunction0)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokeinterface 175 1 0
    //   6: astore_2
    //   7: goto +16 -> 23
    //   10: astore_2
    //   11: new 132	kotlinx/coroutines/CompletedExceptionally
    //   14: dup
    //   15: aload_2
    //   16: iconst_0
    //   17: iconst_2
    //   18: aconst_null
    //   19: invokespecial 136	kotlinx/coroutines/CompletedExceptionally:<init>	(Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
    //   22: astore_2
    //   23: aload_2
    //   24: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   27: if_acmpne +7 -> 34
    //   30: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   33: areturn
    //   34: aload_0
    //   35: aload_2
    //   36: invokevirtual 139	kotlinx/coroutines/internal/ScopeCoroutine:makeCompletingOnce$kotlinx_coroutines_core	(Ljava/lang/Object;)Ljava/lang/Object;
    //   39: astore_3
    //   40: aload_3
    //   41: getstatic 145	kotlinx/coroutines/JobSupportKt:COMPLETING_WAITING_CHILDREN	Lkotlinx/coroutines/internal/Symbol;
    //   44: if_acmpne +7 -> 51
    //   47: invokestatic 89	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   50: areturn
    //   51: aload_3
    //   52: instanceof 132
    //   55: ifeq +64 -> 119
    //   58: aload_3
    //   59: checkcast 132	kotlinx/coroutines/CompletedExceptionally
    //   62: astore_3
    //   63: aload_1
    //   64: aload_3
    //   65: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   68: invokeinterface 79 2 0
    //   73: checkcast 177	java/lang/Boolean
    //   76: invokevirtual 181	java/lang/Boolean:booleanValue	()Z
    //   79: ifne +28 -> 107
    //   82: aload_2
    //   83: instanceof 132
    //   86: ifne +6 -> 92
    //   89: goto +35 -> 124
    //   92: aload_2
    //   93: checkcast 132	kotlinx/coroutines/CompletedExceptionally
    //   96: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   99: aload_0
    //   100: getfield 152	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   103: invokestatic 158	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   106: athrow
    //   107: aload_3
    //   108: getfield 149	kotlinx/coroutines/CompletedExceptionally:cause	Ljava/lang/Throwable;
    //   111: aload_0
    //   112: getfield 152	kotlinx/coroutines/internal/ScopeCoroutine:uCont	Lkotlin/coroutines/Continuation;
    //   115: invokestatic 158	kotlinx/coroutines/internal/StackTraceRecoveryKt:recoverStackTrace	(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;
    //   118: athrow
    //   119: aload_3
    //   120: invokestatic 161	kotlinx/coroutines/JobSupportKt:unboxState	(Ljava/lang/Object;)Ljava/lang/Object;
    //   123: astore_2
    //   124: aload_2
    //   125: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	126	0	paramScopeCoroutine	kotlinx.coroutines.internal.ScopeCoroutine<? super T>
    //   0	126	1	paramFunction1	Function1<? super Throwable, Boolean>
    //   0	126	2	paramFunction0	kotlin.jvm.functions.Function0<? extends Object>
    //   39	81	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	7	10	finally
  }
}
