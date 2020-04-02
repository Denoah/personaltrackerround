package kotlinx.coroutines.flow.internal;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\034\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B-\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0050\004\022\b\b\002\020\006\032\0020\007\022\b\b\002\020\b\032\0020\t?\006\002\020\nJ\037\020\013\032\0020\f2\f\020\r\032\b\022\004\022\0028\0000\016H?@?\001\000?\006\002\020\017J\036\020\020\032\b\022\004\022\0028\0000\0022\006\020\006\032\0020\0072\006\020\b\032\0020\tH\024J\026\020\021\032\b\022\004\022\0028\0000\0222\006\020\r\032\0020\023H\026R\032\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0050\004X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\024"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelLimitedFlowMerge;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flows", "", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Ljava/lang/Iterable;Lkotlin/coroutines/CoroutineContext;I)V", "collectTo", "", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChannelLimitedFlowMerge<T>
  extends ChannelFlow<T>
{
  private final Iterable<Flow<T>> flows;
  
  public ChannelLimitedFlowMerge(Iterable<? extends Flow<? extends T>> paramIterable, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramCoroutineContext, paramInt);
    this.flows = paramIterable;
  }
  
  protected Object collectTo(final ProducerScope<? super T> paramProducerScope, final Continuation<? super Unit> paramContinuation)
  {
    paramContinuation = new SendingCollector((SendChannel)paramProducerScope);
    Iterator localIterator = this.flows.iterator();
    while (localIterator.hasNext())
    {
      Flow localFlow = (Flow)localIterator.next();
      BuildersKt.launch$default((CoroutineScope)paramProducerScope, null, null, (Function2)new SuspendLambda(localFlow, null)
      {
        Object L$0;
        int label;
        private CoroutineScope p$;
        
        public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
          paramAnonymousContinuation = new 1(this.$flow, paramAnonymousContinuation, paramProducerScope, paramContinuation);
          paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
          return paramAnonymousContinuation;
        }
        
        public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
        }
        
        public final Object invokeSuspend(Object paramAnonymousObject)
        {
          Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
          int i = this.label;
          if (i != 0)
          {
            if (i == 1)
            {
              localObject = (CoroutineScope)this.L$0;
              ResultKt.throwOnFailure(paramAnonymousObject);
            }
            else
            {
              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
          }
          else
          {
            ResultKt.throwOnFailure(paramAnonymousObject);
            CoroutineScope localCoroutineScope = this.p$;
            paramAnonymousObject = this.$flow;
            FlowCollector localFlowCollector = (FlowCollector)paramContinuation;
            this.L$0 = localCoroutineScope;
            this.label = 1;
            if (paramAnonymousObject.collect(localFlowCollector, this) == localObject) {
              return localObject;
            }
          }
          return Unit.INSTANCE;
        }
      }, 3, null);
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ChannelLimitedFlowMerge(this.flows, paramCoroutineContext, paramInt);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "scope");
    return FlowCoroutineKt.flowProduce(paramCoroutineScope, this.context, this.capacity, getCollectToFun$kotlinx_coroutines_core());
  }
}
