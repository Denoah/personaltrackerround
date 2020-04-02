package kotlinx.coroutines.selects;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\b\003\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\0328\020\000\032\002H\001\"\004\b\000\020\0012\037\b\004\020\002\032\031\022\n\022\b\022\004\022\002H\0010\004\022\004\022\0020\0050\003?\006\002\b\006H?H?\001\000?\006\002\020\007?\002\004\n\002\b\031?\006\b"}, d2={"selectUnbiased", "R", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SelectUnbiasedKt
{
  /* Error */
  public static final <R> Object selectUnbiased(kotlin.jvm.functions.Function1<? super SelectBuilder<? super R>, kotlin.Unit> paramFunction1, kotlin.coroutines.Continuation<? super R> paramContinuation)
  {
    // Byte code:
    //   0: new 27	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 31	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   8: astore_2
    //   9: aload_0
    //   10: aload_2
    //   11: invokeinterface 37 2 0
    //   16: pop
    //   17: goto +9 -> 26
    //   20: astore_0
    //   21: aload_2
    //   22: aload_0
    //   23: invokevirtual 41	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   26: aload_2
    //   27: invokevirtual 45	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:initSelectResult	()Ljava/lang/Object;
    //   30: astore_0
    //   31: aload_0
    //   32: invokestatic 50	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   35: if_acmpne +7 -> 42
    //   38: aload_1
    //   39: invokestatic 55	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   42: aload_0
    //   43: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	paramFunction1	kotlin.jvm.functions.Function1<? super SelectBuilder<? super R>, kotlin.Unit>
    //   0	44	1	paramContinuation	kotlin.coroutines.Continuation<? super R>
    //   8	19	2	localUnbiasedSelectBuilderImpl	UnbiasedSelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   9	17	20	finally
  }
  
  /* Error */
  private static final Object selectUnbiased$$forInline(kotlin.jvm.functions.Function1 paramFunction1, kotlin.coroutines.Continuation paramContinuation)
  {
    // Byte code:
    //   0: iconst_0
    //   1: invokestatic 65	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   4: new 27	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 31	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   12: astore_2
    //   13: aload_0
    //   14: aload_2
    //   15: invokeinterface 37 2 0
    //   20: pop
    //   21: goto +9 -> 30
    //   24: astore_0
    //   25: aload_2
    //   26: aload_0
    //   27: invokevirtual 41	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: invokevirtual 45	kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl:initSelectResult	()Ljava/lang/Object;
    //   34: astore_0
    //   35: aload_0
    //   36: invokestatic 50	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +7 -> 46
    //   42: aload_1
    //   43: invokestatic 55	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   46: iconst_1
    //   47: invokestatic 65	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   50: aload_0
    //   51: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	paramFunction1	kotlin.jvm.functions.Function1
    //   0	52	1	paramContinuation	kotlin.coroutines.Continuation
    //   12	19	2	localUnbiasedSelectBuilderImpl	UnbiasedSelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   13	21	24	finally
  }
}
