package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\030\002\n\002\b\002\032B\020\000\032\002H\001\"\004\b\000\020\0012)\b\001\020\002\032#\b\001\022\004\022\0020\004\022\n\022\b\022\004\022\002H\0010\005\022\006\022\004\030\0010\0060\003?\006\002\b\007H?@?\001\000?\006\002\020\b\032S\020\t\032\b\022\004\022\002H\0010\n\"\004\b\000\020\00125\b\001\020\002\032/\b\001\022\004\022\0020\004\022\n\022\b\022\004\022\002H\0010\f\022\n\022\b\022\004\022\0020\r0\005\022\006\022\004\030\0010\0060\013?\006\002\b\007H\000?\001\000?\006\002\020\016\032c\020\017\032\b\022\004\022\002H\0210\020\"\004\b\000\020\021*\0020\0042\006\020\022\032\0020\0232\b\b\002\020\024\032\0020\0252/\b\001\020\002\032)\b\001\022\n\022\b\022\004\022\002H\0210\026\022\n\022\b\022\004\022\0020\r0\005\022\006\022\004\030\0010\0060\003?\006\002\b\007H\000?\001\000?\006\002\020\027?\002\004\n\002\b\031?\006\030"}, d2={"flowScope", "R", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "scopedFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "(Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "flowProduce", "Lkotlinx/coroutines/channels/ReceiveChannel;", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class FlowCoroutineKt
{
  public static final <T> ReceiveChannel<T> flowProduce(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$flowProduce");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    Channel localChannel = ChannelKt.Channel(paramInt);
    paramCoroutineScope = new FlowProduceCoroutine(CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext), localChannel);
    paramCoroutineScope.start(CoroutineStart.DEFAULT, paramCoroutineScope, paramFunction2);
    return (ReceiveChannel)paramCoroutineScope;
  }
  
  public static final <R> Object flowScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> paramFunction2, Continuation<? super R> paramContinuation)
  {
    FlowCoroutine localFlowCoroutine = new FlowCoroutine(paramContinuation.getContext(), paramContinuation);
    paramFunction2 = UndispatchedKt.startUndispatchedOrReturn((ScopeCoroutine)localFlowCoroutine, localFlowCoroutine, paramFunction2);
    if (paramFunction2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(paramContinuation);
    }
    return paramFunction2;
  }
  
  public static final <R> Flow<R> scopedFlow(Function3<? super CoroutineScope, ? super FlowCollector<? super R>, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction3, "block");
    (Flow)new Flow()
    {
      public Object collect(FlowCollector paramAnonymousFlowCollector, Continuation paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = FlowCoroutineKt.flowScope((Function2)new SuspendLambda(paramAnonymousFlowCollector, null)
        {
          Object L$0;
          int label;
          private CoroutineScope p$;
          
          public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Continuation, "completion");
            paramAnonymous2Continuation = new 1(this.$collector, paramAnonymous2Continuation, jdField_this);
            paramAnonymous2Continuation.p$ = ((CoroutineScope)paramAnonymous2Object);
            return paramAnonymous2Continuation;
          }
          
          public final Object invoke(Object paramAnonymous2Object1, Object paramAnonymous2Object2)
          {
            return ((1)create(paramAnonymous2Object1, (Continuation)paramAnonymous2Object2)).invokeSuspend(Unit.INSTANCE);
          }
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0)
            {
              if (i == 1)
              {
                localObject = (CoroutineScope)this.L$0;
                ResultKt.throwOnFailure(paramAnonymous2Object);
              }
              else
              {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
              }
            }
            else
            {
              ResultKt.throwOnFailure(paramAnonymous2Object);
              CoroutineScope localCoroutineScope = this.p$;
              paramAnonymous2Object = jdField_this.$block$inlined;
              FlowCollector localFlowCollector = this.$collector;
              this.L$0 = localCoroutineScope;
              this.label = 1;
              if (paramAnonymous2Object.invoke(localCoroutineScope, localFlowCollector, this) == localObject) {
                return localObject;
              }
            }
            return Unit.INSTANCE;
          }
        }, paramAnonymousContinuation);
        if (paramAnonymousFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
          return paramAnonymousFlowCollector;
        }
        return Unit.INSTANCE;
      }
    };
  }
}
