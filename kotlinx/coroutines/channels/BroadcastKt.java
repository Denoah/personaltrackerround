package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(bv={1, 0, 3}, d1={"\000V\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\000\032?\001\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\t2-\b\002\020\n\032'\022\025\022\023\030\0010\f?\006\f\b\r\022\b\b\016\022\004\b\b(\017\022\004\022\0020\020\030\0010\013j\004\030\001`\0212/\b\001\020\022\032)\b\001\022\n\022\b\022\004\022\002H\0020\024\022\n\022\b\022\004\022\0020\0200\025\022\006\022\004\030\0010\0260\023?\006\002\b\027?\001\000?\006\002\020\030\0320\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0312\b\b\002\020\006\032\0020\0072\b\b\002\020\b\032\0020\t?\002\004\n\002\b\031?\006\032"}, d2={"broadcast", "Lkotlinx/coroutines/channels/BroadcastChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/BroadcastChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class BroadcastKt
{
  public static final <E> BroadcastChannel<E> broadcast(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, CoroutineStart paramCoroutineStart, Function1<? super Throwable, Unit> paramFunction1, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$broadcast");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    paramCoroutineContext = CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext);
    paramCoroutineScope = BroadcastChannelKt.BroadcastChannel(paramInt);
    if (paramCoroutineStart.isLazy()) {
      paramCoroutineScope = (BroadcastCoroutine)new LazyBroadcastCoroutine(paramCoroutineContext, paramCoroutineScope, paramFunction2);
    } else {
      paramCoroutineScope = new BroadcastCoroutine(paramCoroutineContext, paramCoroutineScope, true);
    }
    if (paramFunction1 != null) {
      paramCoroutineScope.invokeOnCompletion(paramFunction1);
    }
    paramCoroutineScope.start(paramCoroutineStart, paramCoroutineScope, paramFunction2);
    return (BroadcastChannel)paramCoroutineScope;
  }
  
  public static final <E> BroadcastChannel<E> broadcast(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, CoroutineStart paramCoroutineStart)
  {
    Intrinsics.checkParameterIsNotNull(paramReceiveChannel, "$this$broadcast");
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    broadcast((CoroutineScope)GlobalScope.INSTANCE, (CoroutineContext)Dispatchers.getUnconfined(), paramInt, paramCoroutineStart, ChannelsKt.consumes(paramReceiveChannel), (Function2)new SuspendLambda(paramReceiveChannel, null)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      private ProducerScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.$this_broadcast, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((ProducerScope)paramAnonymousObject);
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
        Object localObject2;
        Object localObject3;
        if (i != 0)
        {
          if (i != 1)
          {
            if (i == 2)
            {
              localObject2 = (ChannelIterator)this.L$2;
              localObject3 = (ProducerScope)this.L$0;
              ResultKt.throwOnFailure(paramAnonymousObject);
            }
            else
            {
              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
          }
          else
          {
            localObject4 = (ChannelIterator)this.L$1;
            localObject2 = (ProducerScope)this.L$0;
            ResultKt.throwOnFailure(paramAnonymousObject);
            localObject3 = this;
            break label158;
          }
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = this.p$;
          localObject2 = this.$this_broadcast.iterator();
        }
        paramAnonymousObject = this;
        Object localObject4 = localObject2;
        for (;;)
        {
          paramAnonymousObject.L$0 = localObject3;
          paramAnonymousObject.L$1 = localObject4;
          paramAnonymousObject.label = 1;
          Object localObject5 = ((ChannelIterator)localObject4).hasNext(paramAnonymousObject);
          if (localObject5 == localObject1) {
            return localObject1;
          }
          localObject2 = localObject3;
          localObject3 = paramAnonymousObject;
          paramAnonymousObject = localObject5;
          label158:
          if (!((Boolean)paramAnonymousObject).booleanValue()) {
            break;
          }
          paramAnonymousObject = ((ChannelIterator)localObject4).next();
          ((1)localObject3).L$0 = localObject2;
          ((1)localObject3).L$1 = paramAnonymousObject;
          ((1)localObject3).L$2 = localObject4;
          ((1)localObject3).label = 2;
          if (((ProducerScope)localObject2).send(paramAnonymousObject, (Continuation)localObject3) == localObject1) {
            return localObject1;
          }
          paramAnonymousObject = localObject3;
          localObject3 = localObject2;
        }
        return Unit.INSTANCE;
      }
    });
  }
}
