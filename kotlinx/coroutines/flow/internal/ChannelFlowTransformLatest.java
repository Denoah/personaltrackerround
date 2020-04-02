package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\004\b\000\030\000*\004\b\000\020\001*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\003Bn\022B\020\004\032>\b\001\022\n\022\b\022\004\022\0028\0010\006\022\023\022\0218\000?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\n\022\b\022\004\022\0020\0130\n\022\006\022\004\030\0010\f0\005?\006\002\b\r\022\f\020\016\032\b\022\004\022\0028\0000\017\022\b\b\002\020\020\032\0020\021\022\b\b\002\020\022\032\0020\023?\001\000?\006\002\020\024J\036\020\026\032\b\022\004\022\0028\0010\0272\006\020\020\032\0020\0212\006\020\022\032\0020\023H\024J\037\020\030\032\0020\0132\f\020\031\032\b\022\004\022\0028\0010\006H?@?\001\000?\006\002\020\032RO\020\004\032>\b\001\022\n\022\b\022\004\022\0028\0010\006\022\023\022\0218\000?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\n\022\b\022\004\022\0020\0130\n\022\006\022\004\030\0010\f0\005?\006\002\b\rX?\004?\001\000?\006\004\n\002\020\025?\002\004\n\002\b\031?\006\033"}, d2={"Lkotlinx/coroutines/flow/internal/ChannelFlowTransformLatest;", "T", "R", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "transform", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Lkotlin/jvm/functions/Function3;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;I)V", "Lkotlin/jvm/functions/Function3;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flowCollect", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ChannelFlowTransformLatest<T, R>
  extends ChannelFlowOperator<T, R>
{
  private final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> transform;
  
  public ChannelFlowTransformLatest(Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3, Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext, int paramInt)
  {
    super(paramFlow, paramCoroutineContext, paramInt);
    this.transform = paramFunction3;
  }
  
  protected ChannelFlow<R> create(CoroutineContext paramCoroutineContext, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return (ChannelFlow)new ChannelFlowTransformLatest(this.transform, this.flow, paramCoroutineContext, paramInt);
  }
  
  protected Object flowCollect(final FlowCollector<? super R> paramFlowCollector, Continuation<? super Unit> paramContinuation)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!Boxing.boxBoolean(paramFlowCollector instanceof SendingCollector).booleanValue())) {
      throw ((Throwable)new AssertionError());
    }
    paramFlowCollector = FlowCoroutineKt.flowScope((Function2)new SuspendLambda(paramFlowCollector, null)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 3(this.this$0, paramFlowCollector, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((3)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(final Object paramAnonymousObject)
      {
        Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0)
        {
          if (i == 1)
          {
            localObject = (Flow)this.L$2;
            localObject = (Ref.ObjectRef)this.L$1;
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
          paramAnonymousObject = this.p$;
          final Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
          localObjectRef.element = ((Job)null);
          Flow localFlow = this.this$0.flow;
          FlowCollector localFlowCollector = (FlowCollector)new FlowCollector()
          {
            public Object emit(Object paramAnonymous2Object, final Continuation paramAnonymous2Continuation)
            {
              if ((paramAnonymous2Continuation instanceof 1))
              {
                localObject1 = (1)paramAnonymous2Continuation;
                if ((((1)localObject1).label & 0x80000000) != 0)
                {
                  ((1)localObject1).label += Integer.MIN_VALUE;
                  paramAnonymous2Continuation = (Continuation)localObject1;
                  break label48;
                }
              }
              paramAnonymous2Continuation = new ContinuationImpl(paramAnonymous2Continuation)
              {
                Object L$0;
                Object L$1;
                Object L$2;
                Object L$3;
                Object L$4;
                Object L$5;
                int label;
                
                public final Object invokeSuspend(Object paramAnonymous3Object)
                {
                  this.result = paramAnonymous3Object;
                  this.label |= 0x80000000;
                  return this.this$0.emit(null, this);
                }
              };
              label48:
              Object localObject1 = paramAnonymous2Continuation.result;
              Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
              int i = paramAnonymous2Continuation.label;
              if (i != 0)
              {
                if (i == 1)
                {
                  paramAnonymous2Object = (Job)paramAnonymous2Continuation.L$5;
                  paramAnonymous2Object = (Job)paramAnonymous2Continuation.L$4;
                  paramAnonymous2Object = paramAnonymous2Continuation.L$3;
                  localObject2 = (Continuation)paramAnonymous2Continuation.L$2;
                  localObject2 = paramAnonymous2Continuation.L$1;
                  paramAnonymous2Continuation = (1)paramAnonymous2Continuation.L$0;
                  ResultKt.throwOnFailure(localObject1);
                }
                else
                {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
              }
              else
              {
                ResultKt.throwOnFailure(localObject1);
                localObject1 = (Continuation)paramAnonymous2Continuation;
                Job localJob = (Job)localObjectRef.element;
                if (localJob != null)
                {
                  localJob.cancel((CancellationException)new ChildCancelledException());
                  paramAnonymous2Continuation.L$0 = this;
                  paramAnonymous2Continuation.L$1 = paramAnonymous2Object;
                  paramAnonymous2Continuation.L$2 = localObject1;
                  paramAnonymous2Continuation.L$3 = paramAnonymous2Object;
                  paramAnonymous2Continuation.L$4 = localJob;
                  paramAnonymous2Continuation.L$5 = localJob;
                  paramAnonymous2Continuation.label = 1;
                  if (localJob.join(paramAnonymous2Continuation) == localObject2) {
                    return localObject2;
                  }
                }
                paramAnonymous2Continuation = this;
              }
              localObjectRef.element = BuildersKt.launch$default(paramAnonymousObject, null, CoroutineStart.UNDISPATCHED, (Function2)new SuspendLambda(paramAnonymous2Object, null)
              {
                Object L$0;
                int label;
                private CoroutineScope p$;
                
                public final Continuation<Unit> create(Object paramAnonymous3Object, Continuation<?> paramAnonymous3Continuation)
                {
                  Intrinsics.checkParameterIsNotNull(paramAnonymous3Continuation, "completion");
                  paramAnonymous3Continuation = new 1(this.$value, paramAnonymous3Continuation, paramAnonymous2Continuation);
                  paramAnonymous3Continuation.p$ = ((CoroutineScope)paramAnonymous3Object);
                  return paramAnonymous3Continuation;
                }
                
                public final Object invoke(Object paramAnonymous3Object1, Object paramAnonymous3Object2)
                {
                  return ((1)create(paramAnonymous3Object1, (Continuation)paramAnonymous3Object2)).invokeSuspend(Unit.INSTANCE);
                }
                
                public final Object invokeSuspend(Object paramAnonymous3Object)
                {
                  Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  int i = this.label;
                  if (i != 0)
                  {
                    if (i == 1)
                    {
                      localObject1 = (CoroutineScope)this.L$0;
                      ResultKt.throwOnFailure(paramAnonymous3Object);
                    }
                    else
                    {
                      throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                  }
                  else
                  {
                    ResultKt.throwOnFailure(paramAnonymous3Object);
                    paramAnonymous3Object = this.p$;
                    Function3 localFunction3 = ChannelFlowTransformLatest.access$getTransform$p(paramAnonymous2Continuation.this$0.this$0);
                    FlowCollector localFlowCollector = paramAnonymous2Continuation.this$0.$collector;
                    Object localObject2 = this.$value;
                    this.L$0 = paramAnonymous3Object;
                    this.label = 1;
                    if (localFunction3.invoke(localFlowCollector, localObject2, this) == localObject1) {
                      return localObject1;
                    }
                  }
                  return Unit.INSTANCE;
                }
              }, 1, null);
              return Unit.INSTANCE;
            }
          };
          this.L$0 = paramAnonymousObject;
          this.L$1 = localObjectRef;
          this.L$2 = localFlow;
          this.label = 1;
          if (localFlow.collect(localFlowCollector, this) == localObject) {
            return localObject;
          }
        }
        return Unit.INSTANCE;
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
}
