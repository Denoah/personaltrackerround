package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\020\013\n\002\030\002\n\002\b\002\0322\020\000\032\0020\0012\037\b\004\020\002\032\031\022\n\022\b\022\004\022\0020\0050\004\022\004\022\0020\0010\003?\006\002\b\006H?H?\001\000?\006\002\020\007?\002\004\n\002\b\031?\006\b"}, d2={"whileSelect", "", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class WhileSelectKt
{
  /* Error */
  public static final Object whileSelect(kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit> paramFunction1, Continuation<? super kotlin.Unit> paramContinuation)
  {
    // Byte code:
    //   0: aload_1
    //   1: instanceof 6
    //   4: ifeq +34 -> 38
    //   7: aload_1
    //   8: checkcast 6	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1
    //   11: astore_2
    //   12: aload_2
    //   13: getfield 30	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   16: ldc 31
    //   18: iand
    //   19: ifeq +19 -> 38
    //   22: aload_2
    //   23: aload_2
    //   24: getfield 30	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   27: ldc 31
    //   29: iadd
    //   30: putfield 30	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   33: aload_2
    //   34: astore_1
    //   35: goto +12 -> 47
    //   38: new 6	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 35	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:<init>	(Lkotlin/coroutines/Continuation;)V
    //   46: astore_1
    //   47: aload_1
    //   48: getfield 39	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:result	Ljava/lang/Object;
    //   51: astore_2
    //   52: invokestatic 45	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   55: astore_3
    //   56: aload_1
    //   57: getfield 30	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   60: istore 4
    //   62: iload 4
    //   64: ifeq +34 -> 98
    //   67: iload 4
    //   69: iconst_1
    //   70: if_icmpne +18 -> 88
    //   73: aload_1
    //   74: getfield 48	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:L$0	Ljava/lang/Object;
    //   77: checkcast 50	kotlin/jvm/functions/Function1
    //   80: astore_0
    //   81: aload_2
    //   82: invokestatic 56	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   85: goto +86 -> 171
    //   88: new 58	java/lang/IllegalStateException
    //   91: dup
    //   92: ldc 60
    //   94: invokespecial 63	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   97: athrow
    //   98: aload_2
    //   99: invokestatic 56	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   102: aload_1
    //   103: aload_0
    //   104: putfield 48	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:L$0	Ljava/lang/Object;
    //   107: aload_1
    //   108: iconst_1
    //   109: putfield 30	kotlinx/coroutines/selects/WhileSelectKt$whileSelect$1:label	I
    //   112: aload_1
    //   113: checkcast 65	kotlin/coroutines/Continuation
    //   116: astore 5
    //   118: new 67	kotlinx/coroutines/selects/SelectBuilderImpl
    //   121: dup
    //   122: aload 5
    //   124: invokespecial 68	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   127: astore_2
    //   128: aload_0
    //   129: aload_2
    //   130: invokeinterface 72 2 0
    //   135: pop
    //   136: goto +11 -> 147
    //   139: astore 6
    //   141: aload_2
    //   142: aload 6
    //   144: invokevirtual 76	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   147: aload_2
    //   148: invokevirtual 79	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   151: astore_2
    //   152: aload_2
    //   153: invokestatic 45	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   156: if_acmpne +8 -> 164
    //   159: aload 5
    //   161: invokestatic 84	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   164: aload_2
    //   165: aload_3
    //   166: if_acmpne +5 -> 171
    //   169: aload_3
    //   170: areturn
    //   171: aload_2
    //   172: checkcast 86	java/lang/Boolean
    //   175: invokevirtual 90	java/lang/Boolean:booleanValue	()Z
    //   178: ifeq +6 -> 184
    //   181: goto -79 -> 102
    //   184: getstatic 96	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   187: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	paramFunction1	kotlin.jvm.functions.Function1<? super SelectBuilder<? super Boolean>, kotlin.Unit>
    //   0	188	1	paramContinuation	Continuation<? super kotlin.Unit>
    //   11	161	2	localObject1	Object
    //   55	115	3	localObject2	Object
    //   60	11	4	i	int
    //   116	44	5	localContinuation	Continuation
    //   139	4	6	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   128	136	139	finally
  }
  
  /* Error */
  private static final Object whileSelect$$forInline(kotlin.jvm.functions.Function1 paramFunction1, Continuation paramContinuation)
  {
    // Byte code:
    //   0: iconst_0
    //   1: invokestatic 106	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   4: new 67	kotlinx/coroutines/selects/SelectBuilderImpl
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 68	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   12: astore_2
    //   13: aload_0
    //   14: aload_2
    //   15: invokeinterface 72 2 0
    //   20: pop
    //   21: goto +9 -> 30
    //   24: astore_3
    //   25: aload_2
    //   26: aload_3
    //   27: invokevirtual 76	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: invokevirtual 79	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   34: astore_2
    //   35: aload_2
    //   36: invokestatic 45	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +7 -> 46
    //   42: aload_1
    //   43: invokestatic 84	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   46: iconst_1
    //   47: invokestatic 106	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   50: aload_2
    //   51: checkcast 86	java/lang/Boolean
    //   54: invokevirtual 90	java/lang/Boolean:booleanValue	()Z
    //   57: ifeq +6 -> 63
    //   60: goto -60 -> 0
    //   63: getstatic 96	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   66: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	paramFunction1	kotlin.jvm.functions.Function1
    //   0	67	1	paramContinuation	Continuation
    //   12	39	2	localObject	Object
    //   24	3	3	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   13	21	24	finally
  }
}
