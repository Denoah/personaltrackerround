package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\0328\020\r\032\0020\006*#\022\025\022\023\030\0010\002?\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\0072\b\020\005\032\004\030\0010\002H?\b\":\020\000\032#\022\025\022\023\030\0010\002?\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\007*\0020\b8?\002X?\004?\006\006\032\004\b\t\020\n\":\020\000\032#\022\025\022\023\030\0010\002?\006\f\b\003\022\b\b\004\022\004\b\b(\005\022\004\022\0020\0060\001j\002`\007*\0020\0138?\002X?\004?\006\006\032\004\b\t\020\f?\006\016"}, d2={"asHandler", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlinx/coroutines/CancelHandlerBase;", "getAsHandler", "(Lkotlinx/coroutines/CancelHandlerBase;)Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/CompletionHandlerBase;", "(Lkotlinx/coroutines/CompletionHandlerBase;)Lkotlin/jvm/functions/Function1;", "invokeIt", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CompletionHandlerKt
{
  public static final Function1<Throwable, Unit> getAsHandler(CancelHandlerBase paramCancelHandlerBase)
  {
    Intrinsics.checkParameterIsNotNull(paramCancelHandlerBase, "$this$asHandler");
    return (Function1)paramCancelHandlerBase;
  }
  
  public static final Function1<Throwable, Unit> getAsHandler(CompletionHandlerBase paramCompletionHandlerBase)
  {
    Intrinsics.checkParameterIsNotNull(paramCompletionHandlerBase, "$this$asHandler");
    return (Function1)paramCompletionHandlerBase;
  }
  
  public static final void invokeIt(Function1<? super Throwable, Unit> paramFunction1, Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "$this$invokeIt");
    paramFunction1.invoke(paramThrowable);
  }
}
