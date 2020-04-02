package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

@Metadata(bv={1, 0, 3}, d1={"\000T\n\000\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\003\032)\020\000\032\0020\001*\006\022\002\b\0030\0022\016\b\002\020\003\032\b\022\004\022\0020\0010\004H?@?\001\000?\006\002\020\005\032?\001\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b*\0020\t2\b\b\002\020\n\032\0020\0132\b\b\002\020\f\032\0020\r2-\b\002\020\016\032'\022\025\022\023\030\0010\020?\006\f\b\021\022\b\b\022\022\004\b\b(\023\022\004\022\0020\001\030\0010\017j\004\030\001`\0242/\b\001\020\003\032)\b\001\022\n\022\b\022\004\022\002H\b0\002\022\n\022\b\022\004\022\0020\0010\026\022\006\022\004\030\0010\0270\025?\006\002\b\030H\007?\001\000?\006\002\020\031\032e\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b*\0020\t2\b\b\002\020\n\032\0020\0132\b\b\002\020\f\032\0020\r2/\b\001\020\003\032)\b\001\022\n\022\b\022\004\022\002H\b0\002\022\n\022\b\022\004\022\0020\0010\026\022\006\022\004\030\0010\0270\025?\006\002\b\030H\007?\001\000?\006\002\020\032?\002\004\n\002\b\031?\006\033"}, d2={"awaitClose", "", "Lkotlinx/coroutines/channels/ProducerScope;", "block", "Lkotlin/Function0;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produce", "Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ProduceKt
{
  public static final Object awaitClose(ProducerScope<?> paramProducerScope, Function0<Unit> paramFunction0, Continuation<? super Unit> paramContinuation)
  {
    if ((paramContinuation instanceof awaitClose.1))
    {
      local1 = (awaitClose.1)paramContinuation;
      if ((local1.label & 0x80000000) != 0)
      {
        local1.label += Integer.MIN_VALUE;
        break label45;
      }
    }
    ContinuationImpl local1 = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return ProduceKt.awaitClose(null, null, this);
      }
    };
    label45:
    Object localObject1 = local1.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = local1.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramProducerScope = (Function0)local1.L$1;
        paramFunction0 = (ProducerScope)local1.L$0;
        paramContinuation = paramProducerScope;
        try
        {
          ResultKt.throwOnFailure(localObject1);
        }
        finally
        {
          break label294;
        }
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      if ((Job)local1.getContext().get((CoroutineContext.Key)Job.Key) == paramProducerScope) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        break label303;
      }
      paramContinuation = paramFunction0;
      local1.L$0 = paramProducerScope;
      paramContinuation = paramFunction0;
      local1.L$1 = paramFunction0;
      paramContinuation = paramFunction0;
      local1.label = 1;
      paramContinuation = paramFunction0;
      Object localObject3 = (Continuation)local1;
      paramContinuation = paramFunction0;
      localObject1 = new kotlinx/coroutines/CancellableContinuationImpl;
      paramContinuation = paramFunction0;
      ((CancellableContinuationImpl)localObject1).<init>(IntrinsicsKt.intercepted((Continuation)localObject3), 1);
      paramContinuation = paramFunction0;
      localObject3 = (CancellableContinuation)localObject1;
      paramContinuation = paramFunction0;
      Lambda local11 = new kotlinx/coroutines/channels/ProduceKt$awaitClose$4$1;
      paramContinuation = paramFunction0;
      local11.<init>((CancellableContinuation)localObject3);
      paramContinuation = paramFunction0;
      paramProducerScope.invokeOnClose((Function1)local11);
      paramContinuation = paramFunction0;
      localObject1 = ((CancellableContinuationImpl)localObject1).getResult();
      paramContinuation = paramFunction0;
      if (localObject1 == IntrinsicsKt.getCOROUTINE_SUSPENDED())
      {
        paramContinuation = paramFunction0;
        DebugProbesKt.probeCoroutineSuspended((Continuation)local1);
      }
      paramProducerScope = paramFunction0;
      if (localObject1 == localObject2) {
        return localObject2;
      }
    }
    paramProducerScope.invoke();
    return Unit.INSTANCE;
    label294:
    paramContinuation.invoke();
    throw paramProducerScope;
    label303:
    throw ((Throwable)new IllegalStateException("awaitClose() can only be invoked from the producer context".toString()));
  }
  
  public static final <E> ReceiveChannel<E> produce(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, Function1<? super Throwable, Unit> paramFunction1, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$produce");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    Channel localChannel = ChannelKt.Channel(paramInt);
    paramCoroutineScope = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext), localChannel);
    if (paramFunction1 != null) {
      paramCoroutineScope.invokeOnCompletion(paramFunction1);
    }
    paramCoroutineScope.start(CoroutineStart.DEFAULT, paramCoroutineScope, paramFunction2);
    return (ReceiveChannel)paramCoroutineScope;
  }
  
  public static final <E> ReceiveChannel<E> produce(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, int paramInt, Function2<? super ProducerScope<? super E>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineScope, "$this$produce");
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    Channel localChannel = ChannelKt.Channel(paramInt);
    paramCoroutineScope = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(paramCoroutineScope, paramCoroutineContext), localChannel);
    paramCoroutineScope.start(CoroutineStart.DEFAULT, paramCoroutineScope, paramFunction2);
    return (ReceiveChannel)paramCoroutineScope;
  }
}
