package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\020\000\n\002\b\t\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\0328\020\f\032\002H\r\"\004\b\000\020\r2\037\b\004\020\016\032\031\022\n\022\b\022\004\022\002H\r0\020\022\004\022\0020\0210\017?\006\002\b\022H?H?\001\000?\006\002\020\023\"\034\020\000\032\0020\0018\000X?\004?\006\016\n\000\022\004\b\002\020\003\032\004\b\004\020\005\"\026\020\006\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\007\020\003\"\026\020\b\032\0020\0018\002X?\004?\006\b\n\000\022\004\b\t\020\003\"\016\020\n\032\0020\013X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\024"}, d2={"ALREADY_SELECTED", "", "ALREADY_SELECTED$annotations", "()V", "getALREADY_SELECTED", "()Ljava/lang/Object;", "RESUMED", "RESUMED$annotations", "UNDECIDED", "UNDECIDED$annotations", "selectOpSequenceNumber", "Lkotlinx/coroutines/selects/SeqNumber;", "select", "R", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SelectKt
{
  private static final Object ALREADY_SELECTED = new Symbol("ALREADY_SELECTED");
  private static final Object RESUMED = new Symbol("RESUMED");
  private static final Object UNDECIDED = new Symbol("UNDECIDED");
  private static final SeqNumber selectOpSequenceNumber = new SeqNumber();
  
  public static final Object getALREADY_SELECTED()
  {
    return ALREADY_SELECTED;
  }
  
  /* Error */
  public static final <R> Object select(kotlin.jvm.functions.Function1<? super SelectBuilder<? super R>, kotlin.Unit> paramFunction1, kotlin.coroutines.Continuation<? super R> paramContinuation)
  {
    // Byte code:
    //   0: new 66	kotlinx/coroutines/selects/SelectBuilderImpl
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 69	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   8: astore_2
    //   9: aload_0
    //   10: aload_2
    //   11: invokeinterface 75 2 0
    //   16: pop
    //   17: goto +9 -> 26
    //   20: astore_0
    //   21: aload_2
    //   22: aload_0
    //   23: invokevirtual 79	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   26: aload_2
    //   27: invokevirtual 82	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   30: astore_0
    //   31: aload_0
    //   32: invokestatic 87	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   35: if_acmpne +7 -> 42
    //   38: aload_1
    //   39: invokestatic 92	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   42: aload_0
    //   43: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	44	0	paramFunction1	kotlin.jvm.functions.Function1<? super SelectBuilder<? super R>, kotlin.Unit>
    //   0	44	1	paramContinuation	kotlin.coroutines.Continuation<? super R>
    //   8	19	2	localSelectBuilderImpl	SelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   9	17	20	finally
  }
  
  /* Error */
  private static final Object select$$forInline(kotlin.jvm.functions.Function1 paramFunction1, kotlin.coroutines.Continuation paramContinuation)
  {
    // Byte code:
    //   0: iconst_0
    //   1: invokestatic 101	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   4: new 66	kotlinx/coroutines/selects/SelectBuilderImpl
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 69	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
    //   12: astore_2
    //   13: aload_0
    //   14: aload_2
    //   15: invokeinterface 75 2 0
    //   20: pop
    //   21: goto +9 -> 30
    //   24: astore_0
    //   25: aload_2
    //   26: aload_0
    //   27: invokevirtual 79	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
    //   30: aload_2
    //   31: invokevirtual 82	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
    //   34: astore_0
    //   35: aload_0
    //   36: invokestatic 87	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   39: if_acmpne +7 -> 46
    //   42: aload_1
    //   43: invokestatic 92	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
    //   46: iconst_1
    //   47: invokestatic 101	kotlin/jvm/internal/InlineMarker:mark	(I)V
    //   50: aload_0
    //   51: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	52	0	paramFunction1	kotlin.jvm.functions.Function1
    //   0	52	1	paramContinuation	kotlin.coroutines.Continuation
    //   12	19	2	localSelectBuilderImpl	SelectBuilderImpl
    // Exception table:
    //   from	to	target	type
    //   13	21	24	finally
  }
}
