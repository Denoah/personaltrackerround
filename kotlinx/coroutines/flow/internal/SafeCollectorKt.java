package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\002\b\002\032N\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022/\b\005\020\003\032)\b\001\022\n\022\b\022\004\022\002H\0020\005\022\n\022\b\022\004\022\0020\0070\006\022\006\022\004\030\0010\b0\004?\006\002\b\tH?\b?\001\000?\006\002\020\n?\002\004\n\002\b\031?\006\013"}, d2={"unsafeFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class SafeCollectorKt
{
  public static final <T> Flow<T> unsafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    (Flow)new Flow()
    {
      public Object collect(FlowCollector<? super T> paramAnonymousFlowCollector, Continuation<? super Unit> paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = this.$block.invoke(paramAnonymousFlowCollector, paramAnonymousContinuation);
        if (paramAnonymousFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousFlowCollector;
        }
        return Unit.INSTANCE;
      }
      
      public Object collect$$forInline(FlowCollector paramAnonymousFlowCollector, Continuation paramAnonymousContinuation)
      {
        InlineMarker.mark(4);
        new ContinuationImpl(paramAnonymousContinuation)
        {
          int label;
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            this.result = paramAnonymous2Object;
            this.label |= 0x80000000;
            return this.this$0.collect(null, this);
          }
        };
        InlineMarker.mark(5);
        this.$block.invoke(paramAnonymousFlowCollector, paramAnonymousContinuation);
        return Unit.INSTANCE;
      }
    };
  }
}
