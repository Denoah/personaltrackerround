package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\005\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\033\022\f\020\003\032\b\022\004\022\0028\0000\002\022\006\020\004\032\0020\005?\006\002\020\006J\031\020\016\032\0020\f2\006\020\017\032\0028\000H?@?\001\000?\006\002\020\020R\016\020\007\032\0020\bX?\004?\006\002\n\000R\016\020\004\032\0020\005X?\004?\006\002\n\000R/\020\t\032\036\b\001\022\004\022\0028\000\022\n\022\b\022\004\022\0020\f0\013\022\006\022\004\030\0010\b0\nX?\004?\001\000?\006\004\n\002\020\r?\002\004\n\002\b\031?\006\021"}, d2={"Lkotlinx/coroutines/flow/internal/UndispatchedContextCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "downstream", "emitContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "countOrElement", "", "emitRef", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/jvm/functions/Function2;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class UndispatchedContextCollector<T>
  implements FlowCollector<T>
{
  private final Object countOrElement;
  private final CoroutineContext emitContext;
  private final Function2<T, Continuation<? super Unit>, Object> emitRef;
  
  public UndispatchedContextCollector(FlowCollector<? super T> paramFlowCollector, CoroutineContext paramCoroutineContext)
  {
    this.emitContext = paramCoroutineContext;
    this.countOrElement = ThreadContextKt.threadContextElements(paramCoroutineContext);
    this.emitRef = ((Function2)new SuspendLambda(paramFlowCollector, null)
    {
      Object L$0;
      int label;
      private Object p$0;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.$downstream, paramAnonymousContinuation);
        paramAnonymousContinuation.p$0 = paramAnonymousObject;
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0)
        {
          if (i == 1) {
            ResultKt.throwOnFailure(paramAnonymousObject);
          } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          }
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          Object localObject2 = this.p$0;
          paramAnonymousObject = this.$downstream;
          this.L$0 = localObject2;
          this.label = 1;
          if (paramAnonymousObject.emit(localObject2, this) == localObject1) {
            return localObject1;
          }
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    paramT = ChannelFlowKt.withContextUndispatched(this.emitContext, this.countOrElement, this.emitRef, paramT, paramContinuation);
    if (paramT == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramT;
    }
    return Unit.INSTANCE;
  }
}
