package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020\002\n\000\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032/\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\f\020\005\032\b\022\004\022\0020\0010\006H?@?\001\000?\006\002\020\007\032/\020\b\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\f\020\005\032\b\022\004\022\0020\0010\006H?@?\001\000?\006\002\020\007\0324\020\t\032\b\022\004\022\0020\0010\n2\006\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\013\032\0020\f2\b\b\002\020\r\032\0020\016H\007?\002\004\n\002\b\031?\006\017"}, d2={"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class TickerChannelsKt
{
  public static final ReceiveChannel<Unit> ticker(final long paramLong1, long paramLong2, CoroutineContext paramCoroutineContext, TickerMode paramTickerMode)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramTickerMode, "mode");
    int i = 1;
    int j;
    if (paramLong1 >= 0L) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (paramLong2 >= 0L) {
        j = i;
      } else {
        j = 0;
      }
      if (j != 0) {
        ProduceKt.produce((CoroutineScope)GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(paramCoroutineContext), 0, (Function2)new SuspendLambda(paramTickerMode, paramLong1)
        {
          Object L$0;
          int label;
          private ProducerScope p$;
          
          public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
            paramAnonymousContinuation = new 3(this.$mode, paramLong1, this.$initialDelayMillis, paramAnonymousContinuation);
            paramAnonymousContinuation.p$ = ((ProducerScope)paramAnonymousObject);
            return paramAnonymousContinuation;
          }
          
          public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
          {
            return ((3)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
          }
          
          public final Object invokeSuspend(Object paramAnonymousObject)
          {
            Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0)
            {
              if ((i == 1) || (i == 2))
              {
                localObject1 = (ProducerScope)this.L$0;
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
              Object localObject2 = this.$mode;
              i = TickerChannelsKt.WhenMappings.$EnumSwitchMapping$0[localObject2.ordinal()];
              long l1;
              long l2;
              if (i != 1)
              {
                if (i == 2)
                {
                  l1 = paramLong1;
                  l2 = this.$initialDelayMillis;
                  localObject2 = paramAnonymousObject.getChannel();
                  this.L$0 = paramAnonymousObject;
                  this.label = 2;
                  if (TickerChannelsKt.fixedDelayTicker(l1, l2, (SendChannel)localObject2, this) == localObject1) {
                    return localObject1;
                  }
                }
              }
              else
              {
                l2 = paramLong1;
                l1 = this.$initialDelayMillis;
                localObject2 = paramAnonymousObject.getChannel();
                this.L$0 = paramAnonymousObject;
                this.label = 1;
                if (TickerChannelsKt.fixedPeriodTicker(l2, l1, (SendChannel)localObject2, this) == localObject1) {
                  return localObject1;
                }
              }
            }
            return Unit.INSTANCE;
          }
        });
      }
      paramCoroutineContext = new StringBuilder();
      paramCoroutineContext.append("Expected non-negative initial delay, but has ");
      paramCoroutineContext.append(paramLong2);
      paramCoroutineContext.append(" ms");
      throw ((Throwable)new IllegalArgumentException(paramCoroutineContext.toString().toString()));
    }
    paramCoroutineContext = new StringBuilder();
    paramCoroutineContext.append("Expected non-negative delay, but has ");
    paramCoroutineContext.append(paramLong1);
    paramCoroutineContext.append(" ms");
    throw ((Throwable)new IllegalArgumentException(paramCoroutineContext.toString().toString()));
  }
}
