package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;
import kotlinx.coroutines.sync.SemaphoreKt;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B5\022\022\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0040\004\022\006\020\005\032\0020\006\022\b\b\002\020\007\032\0020\b\022\b\b\002\020\t\032\0020\006?\006\002\020\nJ\b\020\013\032\0020\fH\026J\037\020\r\032\0020\0162\f\020\017\032\b\022\004\022\0028\0000\020H?@?\001\000?\006\002\020\021J\036\020\022\032\b\022\004\022\0028\0000\0022\006\020\007\032\0020\b2\006\020\t\032\0020\006H\024J\026\020\023\032\b\022\004\022\0028\0000\0242\006\020\017\032\0020\025H\026R\016\020\005\032\0020\006X?\004?\006\002\n\000R\032\020\003\032\016\022\n\022\b\022\004\022\0028\0000\0040\004X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\026"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowMerge;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "concurrency", "", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "(Lkotlinx/coroutines/flow/Flow;ILkotlin/coroutines/CoroutineContext;I)V", "additionalToStringProps", "", "collectTo", "", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChannelFlowMerge<T>
  extends ChannelFlow<T>
{
  private final int concurrency;
  private final Flow<Flow<T>> flow;
  
  public ChannelFlowMerge(Flow<? extends Flow<? extends T>> paramFlow, int paramInt1, CoroutineContext paramCoroutineContext, int paramInt2)
  {
    super(paramCoroutineContext, paramInt2);
    this.flow = paramFlow;
    this.concurrency = paramInt1;
  }
  
  public String additionalToStringProps()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("concurrency=");
    localStringBuilder.append(this.concurrency);
    localStringBuilder.append(", ");
    return localStringBuilder.toString();
  }
  
  protected Object collectTo(final ProducerScope<? super T> paramProducerScope, Continuation<? super Unit> paramContinuation)
  {
    final Semaphore localSemaphore = SemaphoreKt.Semaphore$default(this.concurrency, 0, 2, null);
    final SendingCollector localSendingCollector = new SendingCollector((SendChannel)paramProducerScope);
    Job localJob = (Job)paramContinuation.getContext().get((CoroutineContext.Key)Job.Key);
    paramProducerScope = this.flow.collect((FlowCollector)new FlowCollector()
    {
      public Object emit(final Object paramAnonymousObject, Continuation paramAnonymousContinuation)
      {
        Object localObject1;
        if ((paramAnonymousContinuation instanceof 1))
        {
          localObject1 = (1)paramAnonymousContinuation;
          if ((((1)localObject1).label & 0x80000000) != 0)
          {
            ((1)localObject1).label += Integer.MIN_VALUE;
            paramAnonymousContinuation = (Continuation)localObject1;
            break label48;
          }
        }
        paramAnonymousContinuation = new ContinuationImpl(paramAnonymousContinuation)
        {
          Object L$0;
          Object L$1;
          Object L$2;
          Object L$3;
          int label;
          
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            this.result = paramAnonymous2Object;
            this.label |= 0x80000000;
            return this.this$0.emit(null, this);
          }
        };
        label48:
        Object localObject2 = paramAnonymousContinuation.result;
        Object localObject3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = paramAnonymousContinuation.label;
        if (i != 0)
        {
          if (i == 1)
          {
            localObject1 = (Flow)paramAnonymousContinuation.L$3;
            paramAnonymousObject = (Continuation)paramAnonymousContinuation.L$2;
            paramAnonymousObject = paramAnonymousContinuation.L$1;
            paramAnonymousObject = (1)paramAnonymousContinuation.L$0;
            ResultKt.throwOnFailure(localObject2);
            paramAnonymousContinuation = (Continuation)localObject1;
          }
          else
          {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          }
        }
        else
        {
          ResultKt.throwOnFailure(localObject2);
          localObject2 = (Continuation)paramAnonymousContinuation;
          localObject1 = (Flow)paramAnonymousObject;
          Object localObject4 = this.$job$inlined;
          if (localObject4 != null) {
            JobKt.ensureActive((Job)localObject4);
          }
          localObject4 = localSemaphore;
          paramAnonymousContinuation.L$0 = this;
          paramAnonymousContinuation.L$1 = paramAnonymousObject;
          paramAnonymousContinuation.L$2 = localObject2;
          paramAnonymousContinuation.L$3 = localObject1;
          paramAnonymousContinuation.label = 1;
          if (((Semaphore)localObject4).acquire(paramAnonymousContinuation) == localObject3) {
            return localObject3;
          }
          paramAnonymousObject = this;
          paramAnonymousContinuation = (Continuation)localObject1;
        }
        BuildersKt.launch$default((CoroutineScope)paramProducerScope, null, null, (Function2)new SuspendLambda(paramAnonymousContinuation, null)
        {
          Object L$0;
          int label;
          private CoroutineScope p$;
          
          public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Continuation, "completion");
            paramAnonymous2Continuation = new 1(this.$inner, paramAnonymous2Continuation, paramAnonymousObject);
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
            if (i != 0) {
              if (i == 1) {
                localObject = (CoroutineScope)this.L$0;
              }
            }
            try
            {
              ResultKt.throwOnFailure(paramAnonymous2Object);
              break label98;
              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
              ResultKt.throwOnFailure(paramAnonymous2Object);
              CoroutineScope localCoroutineScope = this.p$;
              Flow localFlow = this.$inner;
              paramAnonymous2Object = (FlowCollector)paramAnonymousObject.$collector$inlined;
              this.L$0 = localCoroutineScope;
              this.label = 1;
              paramAnonymous2Object = localFlow.collect(paramAnonymous2Object, this);
              if (paramAnonymous2Object == localObject) {
                return localObject;
              }
              label98:
              return Unit.INSTANCE;
            }
            finally
            {
              paramAnonymousObject.$semaphore$inlined.release();
            }
          }
        }, 3, null);
        return Unit.INSTANCE;
      }
    }, paramContinuation);
    if (paramProducerScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramProducerScope;
    }
    return Unit.INSTANCE;
  }
  
  protected ChannelFlow<T> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ChannelFlowMerge(this.flow, this.concurrency, paramCoroutineContext, paramInt);
  }
  
  public ReceiveChannel<T> produceImpl(CoroutineScope paramCoroutineScope)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "scope");
    return FlowCoroutineKt.flowProduce(paramCoroutineScope, this.context, this.capacity, getCollectToFun$kotlinx_coroutines_core());
  }
}
