package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectBuilder;

@Metadata(bv={1, 0, 3}, d1={"\000l\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\021\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\003\032\b\020\000\032\0020\001H\000\032n\020\002\032\b\022\004\022\002H\0040\003\"\004\b\000\020\005\"\004\b\001\020\006\"\004\b\002\020\0042\f\020\007\032\b\022\004\022\002H\0050\0032\f\020\b\032\b\022\004\022\002H\0060\0032(\020\t\032$\b\001\022\004\022\002H\005\022\004\022\002H\006\022\n\022\b\022\004\022\002H\0040\013\022\006\022\004\030\0010\f0\nH\000?\001\000?\006\002\020\r\032\036\020\016\032\b\022\004\022\0020\f0\017*\0020\0202\n\020\007\032\006\022\002\b\0030\003H\002\032\036\020\021\032\b\022\004\022\0020\f0\017*\0020\0202\n\020\007\032\006\022\002\b\0030\003H\002\032?\001\020\022\032\0020\023\"\004\b\000\020\004\"\004\b\001\020\024*\b\022\004\022\002H\0040\0252\024\020\026\032\020\022\f\b\001\022\b\022\004\022\002H\0240\0030\0272\024\020\030\032\020\022\f\022\n\022\006\022\004\030\001H\0240\0270\03129\020\t\0325\b\001\022\n\022\b\022\004\022\002H\0040\025\022\n\022\b\022\004\022\002H\0240\027\022\n\022\b\022\004\022\0020\0230\013\022\006\022\004\030\0010\f0\n?\006\002\b\032H?@?\001\000?\006\002\020\033\032?\001\020\034\032\0020\023\"\004\b\000\020\005\"\004\b\001\020\006\"\004\b\002\020\004*\b\022\004\022\002H\0040\0252\f\020\035\032\b\022\004\022\002H\0050\0032\f\020\036\032\b\022\004\022\002H\0060\0032W\020\t\032S\b\001\022\n\022\b\022\004\022\002H\0040\025\022\023\022\021H\005?\006\f\b \022\b\b!\022\004\b\b(\"\022\023\022\021H\006?\006\f\b \022\b\b!\022\004\b\b(#\022\n\022\b\022\004\022\0020\0230\013\022\006\022\004\030\0010\f0\037?\006\002\b\032H?@?\001\000?\006\002\020$\032v\020%\032\0020\023*\b\022\004\022\0020\0230&2\006\020'\032\0020(2\f\020)\032\b\022\004\022\0020\f0\0172\016\b\004\020*\032\b\022\004\022\0020\0230\03123\b\b\020%\032-\b\001\022\023\022\0210\f?\006\f\b \022\b\b!\022\004\b\b(,\022\n\022\b\022\004\022\0020\0230\013\022\006\022\004\030\0010\f0+H?\b?\001\000?\006\002\020-?\002\004\n\002\b\031?\006."}, d2={"getNull", "Lkotlinx/coroutines/internal/Symbol;", "zipImpl", "Lkotlinx/coroutines/flow/Flow;", "R", "T1", "T2", "flow", "flow2", "transform", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "asChannel", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "asFairChannel", "combineInternal", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "flows", "", "arrayFactory", "Lkotlin/Function0;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;[Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "combineTransformInternal", "first", "second", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "a", "b", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceive", "Lkotlinx/coroutines/selects/SelectBuilder;", "isClosed", "", "channel", "onClosed", "Lkotlin/Function2;", "value", "(Lkotlinx/coroutines/selects/SelectBuilder;ZLkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class CombineKt
{
  private static final ReceiveChannel<Object> asChannel(CoroutineScope paramCoroutineScope, Flow<?> paramFlow)
  {
    ProduceKt.produce$default(paramCoroutineScope, null, 0, (Function2)new SuspendLambda(paramFlow, null)
    {
      Object L$0;
      Object L$1;
      int label;
      private ProducerScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.$flow, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((ProducerScope)paramAnonymousObject);
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
            localObject = (Flow)this.L$1;
            localObject = (ProducerScope)this.L$0;
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
          ProducerScope localProducerScope = this.p$;
          paramAnonymousObject = this.$flow;
          FlowCollector localFlowCollector = (FlowCollector)new FlowCollector()
          {
            public Object emit(Object paramAnonymous2Object, Continuation paramAnonymous2Continuation)
            {
              SendChannel localSendChannel = this.$this_produce$inlined.getChannel();
              if (paramAnonymous2Object == null) {
                paramAnonymous2Object = NullSurrogateKt.NULL;
              }
              paramAnonymous2Object = localSendChannel.send(paramAnonymous2Object, paramAnonymous2Continuation);
              if (paramAnonymous2Object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return paramAnonymous2Object;
              }
              return Unit.INSTANCE;
            }
          };
          this.L$0 = localProducerScope;
          this.L$1 = paramAnonymousObject;
          this.label = 1;
          if (paramAnonymousObject.collect(localFlowCollector, this) == localObject) {
            return localObject;
          }
        }
        return Unit.INSTANCE;
      }
    }, 3, null);
  }
  
  private static final ReceiveChannel<Object> asFairChannel(CoroutineScope paramCoroutineScope, Flow<?> paramFlow)
  {
    ProduceKt.produce$default(paramCoroutineScope, null, 0, (Function2)new SuspendLambda(paramFlow, null)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      private ProducerScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.$flow, paramAnonymousContinuation);
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
        if (i != 0)
        {
          if (i == 1)
          {
            localObject1 = (Flow)this.L$2;
            localObject1 = (ChannelCoroutine)this.L$1;
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
          Object localObject2 = paramAnonymousObject.getChannel();
          if (localObject2 == null) {
            break label150;
          }
          localObject2 = (ChannelCoroutine)localObject2;
          Flow localFlow = this.$flow;
          FlowCollector localFlowCollector = (FlowCollector)new FlowCollector()
          {
            public Object emit(Object paramAnonymous2Object, Continuation paramAnonymous2Continuation)
            {
              ChannelCoroutine localChannelCoroutine = this.$channel$inlined;
              if (paramAnonymous2Object == null) {
                paramAnonymous2Object = NullSurrogateKt.NULL;
              }
              paramAnonymous2Object = localChannelCoroutine.sendFair(paramAnonymous2Object, paramAnonymous2Continuation);
              if (paramAnonymous2Object == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return paramAnonymous2Object;
              }
              return Unit.INSTANCE;
            }
          };
          this.L$0 = paramAnonymousObject;
          this.L$1 = localObject2;
          this.L$2 = localFlow;
          this.label = 1;
          if (localFlow.collect(localFlowCollector, this) == localObject1) {
            return localObject1;
          }
        }
        return Unit.INSTANCE;
        label150:
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ChannelCoroutine<kotlin.Any>");
      }
    }, 3, null);
  }
  
  public static final <R, T> Object combineInternal(FlowCollector<? super R> paramFlowCollector, final Flow<? extends T>[] paramArrayOfFlow, final Function0<T[]> paramFunction0, final Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> paramFunction3, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = CoroutineScopeKt.coroutineScope((Function2)new SuspendLambda(paramFlowCollector, paramArrayOfFlow)
    {
      int I$0;
      Object L$0;
      Object L$1;
      Object L$2;
      Object L$3;
      Object L$4;
      Object L$5;
      Object L$6;
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 2(this.$this_combineInternal, paramArrayOfFlow, paramFunction0, paramFunction3, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((2)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      /* Error */
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        // Byte code:
        //   0: invokestatic 124	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
        //   3: astore_2
        //   4: aload_0
        //   5: getfield 126	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:label	I
        //   8: istore_3
        //   9: iload_3
        //   10: ifeq +105 -> 115
        //   13: iload_3
        //   14: iconst_1
        //   15: if_icmpne +90 -> 105
        //   18: aload_0
        //   19: getfield 128	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$6	Ljava/lang/Object;
        //   22: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2
        //   25: astore 4
        //   27: aload_0
        //   28: getfield 130	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$5	Ljava/lang/Object;
        //   31: checkcast 132	kotlin/jvm/internal/Ref$IntRef
        //   34: astore 5
        //   36: aload_0
        //   37: getfield 134	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$4	Ljava/lang/Object;
        //   40: checkcast 132	kotlin/jvm/internal/Ref$IntRef
        //   43: astore 6
        //   45: aload_0
        //   46: getfield 136	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$3	Ljava/lang/Object;
        //   49: checkcast 138	[Ljava/lang/Boolean;
        //   52: astore 4
        //   54: aload_0
        //   55: getfield 140	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$2	Ljava/lang/Object;
        //   58: checkcast 142	[Ljava/lang/Object;
        //   61: astore 7
        //   63: aload_0
        //   64: getfield 144	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$1	Ljava/lang/Object;
        //   67: checkcast 146	[Lkotlinx/coroutines/channels/ReceiveChannel;
        //   70: astore 8
        //   72: aload_0
        //   73: getfield 148	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:I$0	I
        //   76: istore_3
        //   77: aload_0
        //   78: getfield 150	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$0	Ljava/lang/Object;
        //   81: checkcast 101	kotlinx/coroutines/CoroutineScope
        //   84: astore 9
        //   86: aload_1
        //   87: invokestatic 156	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   90: aload 5
        //   92: astore_1
        //   93: aload_0
        //   94: astore 5
        //   96: aload_2
        //   97: astore 10
        //   99: aload 6
        //   101: astore_2
        //   102: goto +720 -> 822
        //   105: new 158	java/lang/IllegalStateException
        //   108: dup
        //   109: ldc -96
        //   111: invokespecial 163	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
        //   114: athrow
        //   115: aload_1
        //   116: invokestatic 156	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   119: aload_0
        //   120: getfield 103	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:p$	Lkotlinx/coroutines/CoroutineScope;
        //   123: astore 9
        //   125: aload_0
        //   126: getfield 79	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:$flows	[Lkotlinx/coroutines/flow/Flow;
        //   129: arraylength
        //   130: istore 11
        //   132: iload 11
        //   134: anewarray 165	kotlinx/coroutines/channels/ReceiveChannel
        //   137: astore 8
        //   139: iconst_0
        //   140: istore_3
        //   141: iload_3
        //   142: iload 11
        //   144: if_icmpge +37 -> 181
        //   147: iload_3
        //   148: invokestatic 171	kotlin/coroutines/jvm/internal/Boxing:boxInt	(I)Ljava/lang/Integer;
        //   151: checkcast 173	java/lang/Number
        //   154: invokevirtual 177	java/lang/Number:intValue	()I
        //   157: istore 12
        //   159: aload 8
        //   161: iload_3
        //   162: aload 9
        //   164: aload_0
        //   165: getfield 79	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:$flows	[Lkotlinx/coroutines/flow/Flow;
        //   168: iload 12
        //   170: aaload
        //   171: invokestatic 181	kotlinx/coroutines/flow/internal/CombineKt:access$asFairChannel	(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/Flow;)Lkotlinx/coroutines/channels/ReceiveChannel;
        //   174: aastore
        //   175: iinc 3 1
        //   178: goto -37 -> 141
        //   181: iload 11
        //   183: anewarray 183	java/lang/Object
        //   186: astore 7
        //   188: iload 11
        //   190: anewarray 185	java/lang/Boolean
        //   193: astore 4
        //   195: iconst_0
        //   196: istore_3
        //   197: iload_3
        //   198: iload 11
        //   200: if_icmpge +28 -> 228
        //   203: iload_3
        //   204: invokestatic 171	kotlin/coroutines/jvm/internal/Boxing:boxInt	(I)Ljava/lang/Integer;
        //   207: checkcast 173	java/lang/Number
        //   210: invokevirtual 177	java/lang/Number:intValue	()I
        //   213: pop
        //   214: aload 4
        //   216: iload_3
        //   217: iconst_0
        //   218: invokestatic 189	kotlin/coroutines/jvm/internal/Boxing:boxBoolean	(Z)Ljava/lang/Boolean;
        //   221: aastore
        //   222: iinc 3 1
        //   225: goto -28 -> 197
        //   228: new 132	kotlin/jvm/internal/Ref$IntRef
        //   231: dup
        //   232: invokespecial 192	kotlin/jvm/internal/Ref$IntRef:<init>	()V
        //   235: astore 6
        //   237: aload 6
        //   239: iload 11
        //   241: putfield 195	kotlin/jvm/internal/Ref$IntRef:element	I
        //   244: new 132	kotlin/jvm/internal/Ref$IntRef
        //   247: dup
        //   248: invokespecial 192	kotlin/jvm/internal/Ref$IntRef:<init>	()V
        //   251: astore_1
        //   252: aload_1
        //   253: iload 11
        //   255: putfield 195	kotlin/jvm/internal/Ref$IntRef:element	I
        //   258: aload_0
        //   259: astore 5
        //   261: iload 11
        //   263: istore_3
        //   264: aload_2
        //   265: astore 10
        //   267: aload 6
        //   269: astore_2
        //   270: aload_2
        //   271: getfield 195	kotlin/jvm/internal/Ref$IntRef:element	I
        //   274: ifeq +551 -> 825
        //   277: aload 5
        //   279: aload 9
        //   281: putfield 150	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$0	Ljava/lang/Object;
        //   284: aload 5
        //   286: iload_3
        //   287: putfield 148	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:I$0	I
        //   290: aload 5
        //   292: aload 8
        //   294: putfield 144	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$1	Ljava/lang/Object;
        //   297: aload 5
        //   299: aload 7
        //   301: putfield 140	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$2	Ljava/lang/Object;
        //   304: aload 5
        //   306: aload 4
        //   308: putfield 136	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$3	Ljava/lang/Object;
        //   311: aload 5
        //   313: aload_2
        //   314: putfield 134	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$4	Ljava/lang/Object;
        //   317: aload 5
        //   319: aload_1
        //   320: putfield 130	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$5	Ljava/lang/Object;
        //   323: aload 5
        //   325: aload 5
        //   327: putfield 128	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:L$6	Ljava/lang/Object;
        //   330: aload 5
        //   332: iconst_1
        //   333: putfield 126	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2:label	I
        //   336: aload 5
        //   338: checkcast 107	kotlin/coroutines/Continuation
        //   341: astore 6
        //   343: new 197	kotlinx/coroutines/selects/SelectBuilderImpl
        //   346: dup
        //   347: aload 6
        //   349: invokespecial 200	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
        //   352: astore 13
        //   354: aload 13
        //   356: astore 14
        //   358: aload 6
        //   360: astore 15
        //   362: aload 5
        //   364: astore 16
        //   366: aload 9
        //   368: astore 17
        //   370: iload_3
        //   371: istore 11
        //   373: aload 8
        //   375: astore 18
        //   377: aload 7
        //   379: astore 19
        //   381: aload 4
        //   383: astore 20
        //   385: aload_2
        //   386: astore 21
        //   388: aload_1
        //   389: astore 22
        //   391: aload 13
        //   393: checkcast 202	kotlinx/coroutines/selects/SelectBuilder
        //   396: astore 23
        //   398: iconst_0
        //   399: istore 12
        //   401: iload 12
        //   403: iload_3
        //   404: if_icmpge +284 -> 688
        //   407: aload 13
        //   409: astore 14
        //   411: aload 6
        //   413: astore 15
        //   415: aload 5
        //   417: astore 16
        //   419: aload 9
        //   421: astore 17
        //   423: iload_3
        //   424: istore 11
        //   426: aload 8
        //   428: astore 18
        //   430: aload 7
        //   432: astore 19
        //   434: aload 4
        //   436: astore 20
        //   438: aload_2
        //   439: astore 21
        //   441: aload_1
        //   442: astore 22
        //   444: aload 4
        //   446: iload 12
        //   448: aaload
        //   449: invokevirtual 206	java/lang/Boolean:booleanValue	()Z
        //   452: istore 24
        //   454: aload 8
        //   456: iload 12
        //   458: aaload
        //   459: astore 25
        //   461: aload 13
        //   463: astore 14
        //   465: aload 6
        //   467: astore 15
        //   469: aload 5
        //   471: astore 16
        //   473: aload 9
        //   475: astore 17
        //   477: iload_3
        //   478: istore 11
        //   480: aload 8
        //   482: astore 18
        //   484: aload 7
        //   486: astore 19
        //   488: aload 4
        //   490: astore 20
        //   492: aload_2
        //   493: astore 21
        //   495: aload_1
        //   496: astore 22
        //   498: new 14	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1
        //   501: astore 26
        //   503: aload 13
        //   505: astore 18
        //   507: aload 6
        //   509: astore 20
        //   511: aload 5
        //   513: astore 19
        //   515: aload 9
        //   517: astore 21
        //   519: iload_3
        //   520: istore 11
        //   522: aload 8
        //   524: astore 22
        //   526: aload 7
        //   528: astore 13
        //   530: aload 4
        //   532: astore 6
        //   534: aload_2
        //   535: astore 9
        //   537: aload 26
        //   539: iload 12
        //   541: aconst_null
        //   542: aload 5
        //   544: iload_3
        //   545: aload 4
        //   547: aload 8
        //   549: aload 7
        //   551: aload_1
        //   552: aload_2
        //   553: invokespecial 209	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1:<init>	(ILkotlin/coroutines/Continuation;Lkotlinx/coroutines/flow/internal/CombineKt$combineInternal$2;I[Ljava/lang/Boolean;[Lkotlinx/coroutines/channels/ReceiveChannel;[Ljava/lang/Object;Lkotlin/jvm/internal/Ref$IntRef;Lkotlin/jvm/internal/Ref$IntRef;)V
        //   556: aload 26
        //   558: checkcast 7	kotlin/jvm/functions/Function2
        //   561: astore_2
        //   562: iload 24
        //   564: ifeq +6 -> 570
        //   567: goto +56 -> 623
        //   570: aload 25
        //   572: invokeinterface 213 1 0
        //   577: astore 8
        //   579: new 16	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$2
        //   582: astore 7
        //   584: aload_1
        //   585: astore 4
        //   587: aload 7
        //   589: aload_2
        //   590: aconst_null
        //   591: iload 12
        //   593: aload 19
        //   595: iload 11
        //   597: aload 6
        //   599: aload 22
        //   601: aload 13
        //   603: aload_1
        //   604: aload 9
        //   606: invokespecial 216	kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$2:<init>	(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;ILkotlinx/coroutines/flow/internal/CombineKt$combineInternal$2;I[Ljava/lang/Boolean;[Lkotlinx/coroutines/channels/ReceiveChannel;[Ljava/lang/Object;Lkotlin/jvm/internal/Ref$IntRef;Lkotlin/jvm/internal/Ref$IntRef;)V
        //   609: aload 23
        //   611: aload 8
        //   613: aload 7
        //   615: checkcast 7	kotlin/jvm/functions/Function2
        //   618: invokeinterface 219 3 0
        //   623: iinc 12 1
        //   626: aload 19
        //   628: astore 5
        //   630: iload 11
        //   632: istore_3
        //   633: aload 22
        //   635: astore 8
        //   637: aload 13
        //   639: astore 7
        //   641: aload 6
        //   643: astore 4
        //   645: aload 9
        //   647: astore_2
        //   648: aload 18
        //   650: astore 13
        //   652: aload 20
        //   654: astore 6
        //   656: aload 21
        //   658: astore 9
        //   660: goto -259 -> 401
        //   663: astore_2
        //   664: aload 4
        //   666: astore_1
        //   667: aload 13
        //   669: astore 7
        //   671: aload 6
        //   673: astore 4
        //   675: goto +89 -> 764
        //   678: astore_2
        //   679: aload_1
        //   680: astore 4
        //   682: aload 22
        //   684: astore_1
        //   685: goto +61 -> 746
        //   688: aload 13
        //   690: astore 18
        //   692: aload 5
        //   694: astore 19
        //   696: aload 9
        //   698: astore 21
        //   700: iload_3
        //   701: istore 11
        //   703: aload 8
        //   705: astore 22
        //   707: goto +70 -> 777
        //   710: astore_2
        //   711: aload 18
        //   713: astore_1
        //   714: aload 19
        //   716: astore 13
        //   718: aload 20
        //   720: astore 6
        //   722: aload 21
        //   724: astore 9
        //   726: aload 17
        //   728: astore 21
        //   730: aload 15
        //   732: astore 20
        //   734: aload 16
        //   736: astore 19
        //   738: aload 22
        //   740: astore 4
        //   742: aload 14
        //   744: astore 18
        //   746: aload 4
        //   748: astore 8
        //   750: aload 6
        //   752: astore 4
        //   754: aload 13
        //   756: astore 7
        //   758: aload_1
        //   759: astore 22
        //   761: aload 8
        //   763: astore_1
        //   764: aload 18
        //   766: aload_2
        //   767: invokevirtual 223	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
        //   770: aload 9
        //   772: astore_2
        //   773: aload 20
        //   775: astore 6
        //   777: aload 18
        //   779: invokevirtual 226	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
        //   782: astore 8
        //   784: aload 8
        //   786: invokestatic 124	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
        //   789: if_acmpne +8 -> 797
        //   792: aload 6
        //   794: invokestatic 231	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
        //   797: aload 8
        //   799: aload 10
        //   801: if_acmpne +6 -> 807
        //   804: aload 10
        //   806: areturn
        //   807: aload 19
        //   809: astore 5
        //   811: aload 21
        //   813: astore 9
        //   815: iload 11
        //   817: istore_3
        //   818: aload 22
        //   820: astore 8
        //   822: goto -552 -> 270
        //   825: getstatic 115	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   828: areturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	829	0	this	2
        //   0	829	1	paramAnonymousObject	Object
        //   3	645	2	localObject1	Object
        //   663	1	2	localObject2	Object
        //   678	1	2	localObject3	Object
        //   710	57	2	localThrowable	Throwable
        //   772	1	2	localObject4	Object
        //   8	810	3	i	int
        //   25	728	4	localObject5	Object
        //   34	776	5	localObject6	Object
        //   43	750	6	localObject7	Object
        //   61	696	7	localObject8	Object
        //   70	751	8	localObject9	Object
        //   84	730	9	localObject10	Object
        //   97	708	10	localObject11	Object
        //   130	686	11	j	int
        //   157	467	12	k	int
        //   352	403	13	localObject12	Object
        //   356	387	14	localObject13	Object
        //   360	371	15	localObject14	Object
        //   364	371	16	localObject15	Object
        //   368	359	17	localObject16	Object
        //   375	403	18	localObject17	Object
        //   379	429	19	localObject18	Object
        //   383	391	20	localObject19	Object
        //   386	426	21	localObject20	Object
        //   389	430	22	localObject21	Object
        //   396	214	23	localSelectBuilder	SelectBuilder
        //   452	111	24	bool	boolean
        //   459	112	25	localObject22	Object
        //   501	56	26	local1	invokeSuspend..inlined.select.lambda.1
        // Exception table:
        //   from	to	target	type
        //   587	623	663	finally
        //   537	562	678	finally
        //   570	584	678	finally
        //   391	398	710	finally
        //   444	454	710	finally
        //   498	503	710	finally
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
  
  public static final <T1, T2, R> Object combineTransformInternal(FlowCollector<? super R> paramFlowCollector, final Flow<? extends T1> paramFlow, final Flow<? extends T2> paramFlow1, final Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> paramFunction4, Continuation<? super Unit> paramContinuation)
  {
    paramFlowCollector = CoroutineScopeKt.coroutineScope((Function2)new SuspendLambda(paramFlowCollector, paramFlow)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      Object L$3;
      Object L$4;
      Object L$5;
      Object L$6;
      Object L$7;
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 2(this.$this_combineTransformInternal, paramFlow, paramFlow1, paramFunction4, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((2)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      /* Error */
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        // Byte code:
        //   0: invokestatic 128	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
        //   3: astore_2
        //   4: aload_0
        //   5: getfield 130	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:label	I
        //   8: istore_3
        //   9: iload_3
        //   10: ifeq +109 -> 119
        //   13: iload_3
        //   14: iconst_1
        //   15: if_icmpne +94 -> 109
        //   18: aload_0
        //   19: getfield 132	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$7	Ljava/lang/Object;
        //   22: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2
        //   25: astore 4
        //   27: aload_0
        //   28: getfield 134	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$6	Ljava/lang/Object;
        //   31: checkcast 136	kotlin/jvm/internal/Ref$BooleanRef
        //   34: astore 5
        //   36: aload_0
        //   37: getfield 138	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$5	Ljava/lang/Object;
        //   40: checkcast 136	kotlin/jvm/internal/Ref$BooleanRef
        //   43: astore 6
        //   45: aload_0
        //   46: getfield 140	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$4	Ljava/lang/Object;
        //   49: checkcast 142	kotlin/jvm/internal/Ref$ObjectRef
        //   52: astore 7
        //   54: aload_0
        //   55: getfield 144	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$3	Ljava/lang/Object;
        //   58: checkcast 142	kotlin/jvm/internal/Ref$ObjectRef
        //   61: astore 8
        //   63: aload_0
        //   64: getfield 146	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$2	Ljava/lang/Object;
        //   67: checkcast 148	kotlinx/coroutines/channels/ReceiveChannel
        //   70: astore 9
        //   72: aload_0
        //   73: getfield 150	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$1	Ljava/lang/Object;
        //   76: checkcast 148	kotlinx/coroutines/channels/ReceiveChannel
        //   79: astore 4
        //   81: aload_0
        //   82: getfield 152	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$0	Ljava/lang/Object;
        //   85: checkcast 105	kotlinx/coroutines/CoroutineScope
        //   88: astore 10
        //   90: aload_1
        //   91: invokestatic 158	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   94: aload 8
        //   96: astore_1
        //   97: aload_0
        //   98: astore 8
        //   100: aload_2
        //   101: astore 11
        //   103: aload 9
        //   105: astore_2
        //   106: goto +487 -> 593
        //   109: new 160	java/lang/IllegalStateException
        //   112: dup
        //   113: ldc -94
        //   115: invokespecial 165	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
        //   118: athrow
        //   119: aload_1
        //   120: invokestatic 158	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   123: aload_0
        //   124: getfield 107	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:p$	Lkotlinx/coroutines/CoroutineScope;
        //   127: astore 10
        //   129: aload 10
        //   131: aload_0
        //   132: getfield 83	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:$first	Lkotlinx/coroutines/flow/Flow;
        //   135: invokestatic 169	kotlinx/coroutines/flow/internal/CombineKt:access$asFairChannel	(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/Flow;)Lkotlinx/coroutines/channels/ReceiveChannel;
        //   138: astore 9
        //   140: aload 10
        //   142: aload_0
        //   143: getfield 85	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:$second	Lkotlinx/coroutines/flow/Flow;
        //   146: invokestatic 169	kotlinx/coroutines/flow/internal/CombineKt:access$asFairChannel	(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/Flow;)Lkotlinx/coroutines/channels/ReceiveChannel;
        //   149: astore 4
        //   151: new 142	kotlin/jvm/internal/Ref$ObjectRef
        //   154: dup
        //   155: invokespecial 172	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
        //   158: astore_1
        //   159: aload_1
        //   160: aconst_null
        //   161: putfield 175	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
        //   164: new 142	kotlin/jvm/internal/Ref$ObjectRef
        //   167: dup
        //   168: invokespecial 172	kotlin/jvm/internal/Ref$ObjectRef:<init>	()V
        //   171: astore 7
        //   173: aload 7
        //   175: aconst_null
        //   176: putfield 175	kotlin/jvm/internal/Ref$ObjectRef:element	Ljava/lang/Object;
        //   179: new 136	kotlin/jvm/internal/Ref$BooleanRef
        //   182: dup
        //   183: invokespecial 176	kotlin/jvm/internal/Ref$BooleanRef:<init>	()V
        //   186: astore 6
        //   188: aload 6
        //   190: iconst_0
        //   191: putfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   194: new 136	kotlin/jvm/internal/Ref$BooleanRef
        //   197: dup
        //   198: invokespecial 176	kotlin/jvm/internal/Ref$BooleanRef:<init>	()V
        //   201: astore 5
        //   203: aload 5
        //   205: iconst_0
        //   206: putfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   209: aload_2
        //   210: astore 11
        //   212: aload_0
        //   213: astore 8
        //   215: aload 4
        //   217: astore_2
        //   218: aload 9
        //   220: astore 4
        //   222: aload 6
        //   224: getfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   227: ifeq +18 -> 245
        //   230: aload 5
        //   232: getfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   235: ifne +6 -> 241
        //   238: goto +7 -> 245
        //   241: getstatic 119	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   244: areturn
        //   245: aload 8
        //   247: aload 10
        //   249: putfield 152	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$0	Ljava/lang/Object;
        //   252: aload 8
        //   254: aload 4
        //   256: putfield 150	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$1	Ljava/lang/Object;
        //   259: aload 8
        //   261: aload_2
        //   262: putfield 146	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$2	Ljava/lang/Object;
        //   265: aload 8
        //   267: aload_1
        //   268: putfield 144	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$3	Ljava/lang/Object;
        //   271: aload 8
        //   273: aload 7
        //   275: putfield 140	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$4	Ljava/lang/Object;
        //   278: aload 8
        //   280: aload 6
        //   282: putfield 138	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$5	Ljava/lang/Object;
        //   285: aload 8
        //   287: aload 5
        //   289: putfield 134	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$6	Ljava/lang/Object;
        //   292: aload 8
        //   294: aload 8
        //   296: putfield 132	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:L$7	Ljava/lang/Object;
        //   299: aload 8
        //   301: iconst_1
        //   302: putfield 130	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2:label	I
        //   305: aload 8
        //   307: checkcast 111	kotlin/coroutines/Continuation
        //   310: astore 12
        //   312: new 181	kotlinx/coroutines/selects/SelectBuilderImpl
        //   315: dup
        //   316: aload 12
        //   318: invokespecial 184	kotlinx/coroutines/selects/SelectBuilderImpl:<init>	(Lkotlin/coroutines/Continuation;)V
        //   321: astore 13
        //   323: aload 13
        //   325: checkcast 186	kotlinx/coroutines/selects/SelectBuilder
        //   328: astore 14
        //   330: aload 6
        //   332: getfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   335: istore 15
        //   337: new 14	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$1
        //   340: astore 16
        //   342: aload 8
        //   344: astore 17
        //   346: aload 4
        //   348: astore 9
        //   350: aload_2
        //   351: astore 18
        //   353: aload_1
        //   354: astore 19
        //   356: aload 16
        //   358: aconst_null
        //   359: aload 8
        //   361: aload 6
        //   363: aload 4
        //   365: aload_1
        //   366: aload 7
        //   368: aload 5
        //   370: aload 18
        //   372: invokespecial 189	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$1:<init>	(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;)V
        //   375: aload 16
        //   377: checkcast 7	kotlin/jvm/functions/Function2
        //   380: astore 20
        //   382: iload 15
        //   384: ifeq +6 -> 390
        //   387: goto +53 -> 440
        //   390: aload 9
        //   392: invokeinterface 193 1 0
        //   397: astore 16
        //   399: new 16	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$2
        //   402: astore 21
        //   404: aload 21
        //   406: aload 20
        //   408: aconst_null
        //   409: aload 17
        //   411: aload 6
        //   413: aload 9
        //   415: aload 19
        //   417: aload 7
        //   419: aload 5
        //   421: aload 18
        //   423: invokespecial 196	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$2:<init>	(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;)V
        //   426: aload 14
        //   428: aload 16
        //   430: aload 21
        //   432: checkcast 7	kotlin/jvm/functions/Function2
        //   435: invokeinterface 199 3 0
        //   440: aload 7
        //   442: astore 16
        //   444: aload 5
        //   446: getfield 179	kotlin/jvm/internal/Ref$BooleanRef:element	Z
        //   449: istore 15
        //   451: new 18	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$3
        //   454: astore 21
        //   456: aload 21
        //   458: aconst_null
        //   459: aload 17
        //   461: aload 6
        //   463: aload 9
        //   465: aload 19
        //   467: aload 16
        //   469: aload 5
        //   471: aload 18
        //   473: invokespecial 200	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$3:<init>	(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;)V
        //   476: aload 21
        //   478: checkcast 7	kotlin/jvm/functions/Function2
        //   481: astore 20
        //   483: iload 15
        //   485: ifeq +6 -> 491
        //   488: goto +75 -> 563
        //   491: aload 18
        //   493: invokeinterface 193 1 0
        //   498: astore 22
        //   500: new 20	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$4
        //   503: astore 21
        //   505: aload 21
        //   507: aload 20
        //   509: aconst_null
        //   510: aload 17
        //   512: aload 6
        //   514: aload 9
        //   516: aload 19
        //   518: aload 16
        //   520: aload 5
        //   522: aload 18
        //   524: invokespecial 201	kotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2$invokeSuspend$$inlined$select$lambda$4:<init>	(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/flow/internal/CombineKt$combineTransformInternal$2;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$BooleanRef;Lkotlinx/coroutines/channels/ReceiveChannel;)V
        //   527: aload 14
        //   529: aload 22
        //   531: aload 21
        //   533: checkcast 7	kotlin/jvm/functions/Function2
        //   536: invokeinterface 199 3 0
        //   541: goto +22 -> 563
        //   544: astore 9
        //   546: goto +10 -> 556
        //   549: astore 9
        //   551: goto +5 -> 556
        //   554: astore 9
        //   556: aload 13
        //   558: aload 9
        //   560: invokevirtual 205	kotlinx/coroutines/selects/SelectBuilderImpl:handleBuilderException	(Ljava/lang/Throwable;)V
        //   563: aload 13
        //   565: invokevirtual 208	kotlinx/coroutines/selects/SelectBuilderImpl:getResult	()Ljava/lang/Object;
        //   568: astore 9
        //   570: aload 9
        //   572: invokestatic 128	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
        //   575: if_acmpne +8 -> 583
        //   578: aload 12
        //   580: invokestatic 213	kotlin/coroutines/jvm/internal/DebugProbesKt:probeCoroutineSuspended	(Lkotlin/coroutines/Continuation;)V
        //   583: aload 9
        //   585: aload 11
        //   587: if_acmpne +6 -> 593
        //   590: aload 11
        //   592: areturn
        //   593: goto -371 -> 222
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	596	0	this	2
        //   0	596	1	paramAnonymousObject	Object
        //   3	348	2	localObject1	Object
        //   8	8	3	i	int
        //   25	339	4	localObject2	Object
        //   34	487	5	localBooleanRef1	Ref.BooleanRef
        //   43	470	6	localBooleanRef2	Ref.BooleanRef
        //   52	389	7	localObjectRef	Ref.ObjectRef
        //   61	299	8	localObject3	Object
        //   70	445	9	localObject4	Object
        //   544	1	9	localObject5	Object
        //   549	1	9	localObject6	Object
        //   554	5	9	localThrowable	Throwable
        //   568	16	9	localObject7	Object
        //   88	160	10	localCoroutineScope	CoroutineScope
        //   101	490	11	localObject8	Object
        //   310	269	12	localContinuation	Continuation
        //   321	243	13	localSelectBuilderImpl	kotlinx.coroutines.selects.SelectBuilderImpl
        //   328	200	14	localSelectBuilder	SelectBuilder
        //   335	149	15	bool	boolean
        //   340	179	16	localObject9	Object
        //   344	167	17	localObject10	Object
        //   351	172	18	localObject11	Object
        //   354	163	19	localObject12	Object
        //   380	128	20	localFunction2	Function2
        //   402	130	21	localObject13	Object
        //   498	32	22	localSelectClause1	kotlinx.coroutines.selects.SelectClause1
        // Exception table:
        //   from	to	target	type
        //   404	440	544	finally
        //   444	483	544	finally
        //   491	541	544	finally
        //   356	382	549	finally
        //   390	404	549	finally
        //   323	342	554	finally
      }
    }, paramContinuation);
    if (paramFlowCollector == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramFlowCollector;
    }
    return Unit.INSTANCE;
  }
  
  public static final Symbol getNull()
  {
    return NullSurrogateKt.NULL;
  }
  
  private static final void onReceive(SelectBuilder<? super Unit> paramSelectBuilder, boolean paramBoolean, ReceiveChannel<? extends Object> paramReceiveChannel, Function0<Unit> paramFunction0, final Function2<Object, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    if (paramBoolean) {
      return;
    }
    paramSelectBuilder.invoke(paramReceiveChannel.getOnReceiveOrNull(), (Function2)new SuspendLambda(paramFunction0, paramFunction2)
    {
      Object L$0;
      int label;
      private Object p$0;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.$onClosed, paramFunction2, paramAnonymousContinuation);
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
          if (localObject2 == null)
          {
            this.$onClosed.invoke();
          }
          else
          {
            paramAnonymousObject = paramFunction2;
            this.L$0 = localObject2;
            this.label = 1;
            if (paramAnonymousObject.invoke(localObject2, this) == localObject1) {
              return localObject1;
            }
          }
        }
        return Unit.INSTANCE;
      }
      
      public final Object invokeSuspend$$forInline(Object paramAnonymousObject)
      {
        Object localObject = this.p$0;
        if (localObject == null)
        {
          this.$onClosed.invoke();
        }
        else
        {
          paramAnonymousObject = paramFunction2;
          InlineMarker.mark(0);
          paramAnonymousObject.invoke(localObject, this);
          InlineMarker.mark(2);
          InlineMarker.mark(1);
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final <T1, T2, R> Flow<R> zipImpl(Flow<? extends T1> paramFlow, final Flow<? extends T2> paramFlow1, final Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramFlow, "flow");
    Intrinsics.checkParameterIsNotNull(paramFlow1, "flow2");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "transform");
    (Flow)new Flow()
    {
      public Object collect(FlowCollector paramAnonymousFlowCollector, Continuation paramAnonymousContinuation)
      {
        paramAnonymousFlowCollector = CoroutineScopeKt.coroutineScope((Function2)new SuspendLambda(paramAnonymousFlowCollector, null)
        {
          Object L$0;
          Object L$1;
          Object L$10;
          Object L$11;
          Object L$12;
          Object L$2;
          Object L$3;
          Object L$4;
          Object L$5;
          Object L$6;
          Object L$7;
          Object L$8;
          Object L$9;
          int label;
          private CoroutineScope p$;
          
          public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Continuation, "completion");
            paramAnonymous2Continuation = new 1(this.$this_unsafeFlow, paramAnonymous2Continuation, jdField_this);
            paramAnonymous2Continuation.p$ = ((CoroutineScope)paramAnonymous2Object);
            return paramAnonymous2Continuation;
          }
          
          public final Object invoke(Object paramAnonymous2Object1, Object paramAnonymous2Object2)
          {
            return ((1)create(paramAnonymous2Object1, (Continuation)paramAnonymous2Object2)).invokeSuspend(Unit.INSTANCE);
          }
          
          /* Error */
          public final Object invokeSuspend(Object paramAnonymous2Object)
          {
            // Byte code:
            //   0: invokestatic 105	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
            //   3: astore_2
            //   4: aload_0
            //   5: getfield 107	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:label	I
            //   8: istore_3
            //   9: iload_3
            //   10: ifeq +627 -> 637
            //   13: iload_3
            //   14: iconst_1
            //   15: if_icmpeq +466 -> 481
            //   18: iload_3
            //   19: iconst_2
            //   20: if_icmpeq +317 -> 337
            //   23: iload_3
            //   24: iconst_3
            //   25: if_icmpeq +145 -> 170
            //   28: iload_3
            //   29: iconst_4
            //   30: if_icmpne +130 -> 160
            //   33: aload_0
            //   34: getfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   37: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   40: astore 4
            //   42: aload_0
            //   43: getfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   46: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   49: astore 5
            //   51: aload_0
            //   52: getfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   55: checkcast 119	java/lang/Throwable
            //   58: astore 6
            //   60: aload_0
            //   61: getfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   64: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   67: astore 7
            //   69: aload_0
            //   70: getfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   73: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1
            //   76: astore 8
            //   78: aload_0
            //   79: getfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   82: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   85: astore 9
            //   87: aload_0
            //   88: getfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   91: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   94: astore 10
            //   96: aload_0
            //   97: getfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   100: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   103: astore 11
            //   105: aload_0
            //   106: getfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   109: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   112: astore 12
            //   114: aload_0
            //   115: getfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   118: checkcast 79	kotlinx/coroutines/CoroutineScope
            //   121: astore 13
            //   123: aload 7
            //   125: astore 14
            //   127: aload 11
            //   129: astore 15
            //   131: aload_1
            //   132: invokestatic 139	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   135: aload 11
            //   137: astore_1
            //   138: aload_0
            //   139: astore 16
            //   141: aload 13
            //   143: astore 15
            //   145: aload 5
            //   147: astore 11
            //   149: aload 7
            //   151: astore 14
            //   153: aload 8
            //   155: astore 7
            //   157: goto +1313 -> 1470
            //   160: new 141	java/lang/IllegalStateException
            //   163: dup
            //   164: ldc -113
            //   166: invokespecial 146	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
            //   169: athrow
            //   170: aload_0
            //   171: getfield 148	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$12	Ljava/lang/Object;
            //   174: checkcast 150	kotlinx/coroutines/flow/FlowCollector
            //   177: astore 17
            //   179: aload_0
            //   180: getfield 152	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$11	Ljava/lang/Object;
            //   183: astore 14
            //   185: aload_0
            //   186: getfield 154	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$10	Ljava/lang/Object;
            //   189: astore 8
            //   191: aload_0
            //   192: getfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   195: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   198: astore 10
            //   200: aload_0
            //   201: getfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   204: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   207: astore 18
            //   209: aload_0
            //   210: getfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   213: checkcast 119	java/lang/Throwable
            //   216: astore 6
            //   218: aload_0
            //   219: getfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   222: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   225: astore 5
            //   227: aload_0
            //   228: getfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   231: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1
            //   234: astore 7
            //   236: aload_0
            //   237: getfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   240: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   243: astore 19
            //   245: aload_0
            //   246: getfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   249: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   252: astore 16
            //   254: aload_0
            //   255: getfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   258: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   261: astore 11
            //   263: aload_0
            //   264: getfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   267: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   270: astore 12
            //   272: aload_0
            //   273: getfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   276: checkcast 79	kotlinx/coroutines/CoroutineScope
            //   279: astore 15
            //   281: aload_1
            //   282: invokestatic 139	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   285: aload_0
            //   286: astore 9
            //   288: aload_1
            //   289: astore 13
            //   291: aload_2
            //   292: astore 4
            //   294: aload 14
            //   296: astore 20
            //   298: aload 11
            //   300: astore_1
            //   301: aload 18
            //   303: astore 11
            //   305: aload 5
            //   307: astore_2
            //   308: aload 9
            //   310: astore 14
            //   312: aload 19
            //   314: astore 9
            //   316: aload 8
            //   318: astore 5
            //   320: goto +1017 -> 1337
            //   323: astore_2
            //   324: aload 11
            //   326: astore_1
            //   327: aload 5
            //   329: astore 15
            //   331: aload_2
            //   332: astore 11
            //   334: goto +286 -> 620
            //   337: aload_0
            //   338: getfield 152	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$11	Ljava/lang/Object;
            //   341: astore 8
            //   343: aload_0
            //   344: getfield 154	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$10	Ljava/lang/Object;
            //   347: astore 5
            //   349: aload_0
            //   350: getfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   353: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   356: astore 18
            //   358: aload_0
            //   359: getfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   362: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   365: astore 20
            //   367: aload_0
            //   368: getfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   371: checkcast 119	java/lang/Throwable
            //   374: astore 4
            //   376: aload_0
            //   377: getfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   380: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   383: astore 7
            //   385: aload_0
            //   386: getfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   389: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1
            //   392: astore 6
            //   394: aload_0
            //   395: getfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   398: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   401: astore 10
            //   403: aload_0
            //   404: getfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   407: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   410: astore 16
            //   412: aload_0
            //   413: getfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   416: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   419: astore 11
            //   421: aload_0
            //   422: getfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   425: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   428: astore 12
            //   430: aload_0
            //   431: getfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   434: checkcast 79	kotlinx/coroutines/CoroutineScope
            //   437: astore 13
            //   439: aload 7
            //   441: astore 14
            //   443: aload 11
            //   445: astore 15
            //   447: aload_1
            //   448: invokestatic 139	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   451: aload_1
            //   452: astore 17
            //   454: aload_0
            //   455: astore_1
            //   456: aload 11
            //   458: astore 14
            //   460: aload_2
            //   461: astore 9
            //   463: aload 18
            //   465: astore 15
            //   467: aload 20
            //   469: astore 11
            //   471: aload 7
            //   473: astore_2
            //   474: aload 13
            //   476: astore 7
            //   478: goto +559 -> 1037
            //   481: aload_0
            //   482: getfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   485: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   488: astore 8
            //   490: aload_0
            //   491: getfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   494: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   497: astore 13
            //   499: aload_0
            //   500: getfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   503: checkcast 119	java/lang/Throwable
            //   506: astore 4
            //   508: aload_0
            //   509: getfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   512: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   515: astore 16
            //   517: aload_0
            //   518: getfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   521: checkcast 2	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1
            //   524: astore 6
            //   526: aload_0
            //   527: getfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   530: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   533: astore 10
            //   535: aload_0
            //   536: getfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   539: checkcast 111	kotlinx/coroutines/channels/ChannelIterator
            //   542: astore 5
            //   544: aload_0
            //   545: getfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   548: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   551: astore 11
            //   553: aload_0
            //   554: getfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   557: checkcast 115	kotlinx/coroutines/channels/ReceiveChannel
            //   560: astore 12
            //   562: aload_0
            //   563: getfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   566: checkcast 79	kotlinx/coroutines/CoroutineScope
            //   569: astore 9
            //   571: aload 16
            //   573: astore 14
            //   575: aload 11
            //   577: astore 15
            //   579: aload_1
            //   580: invokestatic 139	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   583: aload_1
            //   584: astore 7
            //   586: aload_0
            //   587: astore_1
            //   588: aload 9
            //   590: astore 14
            //   592: aload_2
            //   593: astore 9
            //   595: aload 16
            //   597: astore 15
            //   599: aload 5
            //   601: astore 16
            //   603: aload_1
            //   604: astore_2
            //   605: aload 11
            //   607: astore_1
            //   608: goto +285 -> 893
            //   611: astore 11
            //   613: aload 15
            //   615: astore_1
            //   616: aload 14
            //   618: astore 15
            //   620: aload_0
            //   621: astore_2
            //   622: aload_1
            //   623: astore 14
            //   625: aload 15
            //   627: astore 7
            //   629: aload_2
            //   630: astore_1
            //   631: aload 14
            //   633: astore_2
            //   634: goto +1027 -> 1661
            //   637: aload_1
            //   638: invokestatic 139	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
            //   641: aload_0
            //   642: getfield 81	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:p$	Lkotlinx/coroutines/CoroutineScope;
            //   645: astore 5
            //   647: aload 5
            //   649: aload_0
            //   650: getfield 61	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:this$0	Lkotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1;
            //   653: getfield 158	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1:$flow$inlined	Lkotlinx/coroutines/flow/Flow;
            //   656: invokestatic 164	kotlinx/coroutines/flow/internal/CombineKt:access$asChannel	(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/Flow;)Lkotlinx/coroutines/channels/ReceiveChannel;
            //   659: astore 16
            //   661: aload 5
            //   663: aload_0
            //   664: getfield 61	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:this$0	Lkotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1;
            //   667: getfield 167	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1:$flow2$inlined	Lkotlinx/coroutines/flow/Flow;
            //   670: invokestatic 164	kotlinx/coroutines/flow/internal/CombineKt:access$asChannel	(Lkotlinx/coroutines/CoroutineScope;Lkotlinx/coroutines/flow/Flow;)Lkotlinx/coroutines/channels/ReceiveChannel;
            //   673: astore 14
            //   675: aload 14
            //   677: ifnull +1129 -> 1806
            //   680: aload 14
            //   682: checkcast 169	kotlinx/coroutines/channels/SendChannel
            //   685: new 14	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1$1
            //   688: dup
            //   689: aload_0
            //   690: aload 16
            //   692: invokespecial 172	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1$1:<init>	(Lkotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1;Lkotlinx/coroutines/channels/ReceiveChannel;)V
            //   695: checkcast 174	kotlin/jvm/functions/Function1
            //   698: invokeinterface 178 2 0
            //   703: aload 14
            //   705: invokeinterface 182 1 0
            //   710: astore 8
            //   712: aconst_null
            //   713: checkcast 119	java/lang/Throwable
            //   716: astore 4
            //   718: aload 16
            //   720: invokeinterface 182 1 0
            //   725: astore 10
            //   727: aload_0
            //   728: astore 7
            //   730: aload 7
            //   732: astore 15
            //   734: aload 16
            //   736: astore 6
            //   738: aload 6
            //   740: astore_1
            //   741: aload_1
            //   742: astore 9
            //   744: aload_2
            //   745: astore 11
            //   747: aload 15
            //   749: aload 5
            //   751: putfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   754: aload 15
            //   756: aload 16
            //   758: putfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   761: aload 15
            //   763: aload 14
            //   765: putfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   768: aload 15
            //   770: aload 8
            //   772: putfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   775: aload 15
            //   777: aload 6
            //   779: putfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   782: aload 15
            //   784: aload 7
            //   786: putfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   789: aload 15
            //   791: aload_1
            //   792: putfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   795: aload 15
            //   797: aload 4
            //   799: putfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   802: aload 15
            //   804: aload 9
            //   806: putfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   809: aload 15
            //   811: aload 10
            //   813: putfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   816: aload 15
            //   818: iconst_1
            //   819: putfield 107	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:label	I
            //   822: aload 10
            //   824: aload 7
            //   826: invokeinterface 186 2 0
            //   831: astore_2
            //   832: aload_2
            //   833: aload 11
            //   835: if_acmpne +6 -> 841
            //   838: aload 11
            //   840: areturn
            //   841: aload 16
            //   843: astore 12
            //   845: aload_1
            //   846: astore 17
            //   848: aload 7
            //   850: astore 13
            //   852: aload 8
            //   854: astore 16
            //   856: aload 10
            //   858: astore 8
            //   860: aload_2
            //   861: astore 7
            //   863: aload 14
            //   865: astore_1
            //   866: aload 15
            //   868: astore_2
            //   869: aload 17
            //   871: astore 15
            //   873: aload 6
            //   875: astore 10
            //   877: aload 13
            //   879: astore 6
            //   881: aload 9
            //   883: astore 13
            //   885: aload 5
            //   887: astore 14
            //   889: aload 11
            //   891: astore 9
            //   893: aload 7
            //   895: checkcast 188	java/lang/Boolean
            //   898: invokevirtual 192	java/lang/Boolean:booleanValue	()Z
            //   901: ifeq +669 -> 1570
            //   904: aload 8
            //   906: invokeinterface 195 1 0
            //   911: astore 5
            //   913: aload_2
            //   914: aload 14
            //   916: putfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   919: aload_2
            //   920: aload 12
            //   922: putfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   925: aload_2
            //   926: aload_1
            //   927: putfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   930: aload_2
            //   931: aload 16
            //   933: putfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   936: aload_2
            //   937: aload 10
            //   939: putfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   942: aload_2
            //   943: aload 6
            //   945: putfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   948: aload_2
            //   949: aload 15
            //   951: putfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   954: aload_2
            //   955: aload 4
            //   957: putfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   960: aload_2
            //   961: aload 13
            //   963: putfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   966: aload_2
            //   967: aload 8
            //   969: putfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   972: aload_2
            //   973: aload 5
            //   975: putfield 154	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$10	Ljava/lang/Object;
            //   978: aload_2
            //   979: aload 5
            //   981: putfield 152	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$11	Ljava/lang/Object;
            //   984: aload_2
            //   985: iconst_2
            //   986: putfield 107	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:label	I
            //   989: aload 16
            //   991: aload_2
            //   992: invokeinterface 186 2 0
            //   997: astore 17
            //   999: aload 17
            //   1001: aload 9
            //   1003: if_acmpne +6 -> 1009
            //   1006: aload 9
            //   1008: areturn
            //   1009: aload 5
            //   1011: astore 20
            //   1013: aload 14
            //   1015: astore 7
            //   1017: aload_1
            //   1018: astore 14
            //   1020: aload_2
            //   1021: astore_1
            //   1022: aload 15
            //   1024: astore_2
            //   1025: aload 13
            //   1027: astore 11
            //   1029: aload 8
            //   1031: astore 15
            //   1033: aload 20
            //   1035: astore 8
            //   1037: aload 17
            //   1039: checkcast 188	java/lang/Boolean
            //   1042: invokevirtual 192	java/lang/Boolean:booleanValue	()Z
            //   1045: ifne +71 -> 1116
            //   1048: aload 15
            //   1050: astore 8
            //   1052: aload 10
            //   1054: astore 5
            //   1056: aload 16
            //   1058: astore 20
            //   1060: aload 11
            //   1062: astore 16
            //   1064: aload 6
            //   1066: astore 15
            //   1068: aload_2
            //   1069: astore 17
            //   1071: aload 12
            //   1073: astore 13
            //   1075: aload 9
            //   1077: astore_2
            //   1078: aload 8
            //   1080: astore 11
            //   1082: aload 7
            //   1084: astore 10
            //   1086: aload 20
            //   1088: astore 8
            //   1090: aload 5
            //   1092: astore 6
            //   1094: aload 17
            //   1096: astore 12
            //   1098: aload 16
            //   1100: astore 9
            //   1102: aload 13
            //   1104: astore 16
            //   1106: aload 15
            //   1108: astore 7
            //   1110: aload_1
            //   1111: astore 15
            //   1113: goto +410 -> 1523
            //   1116: aload_1
            //   1117: getfield 59	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:$this_unsafeFlow	Lkotlinx/coroutines/flow/FlowCollector;
            //   1120: astore 20
            //   1122: aload_1
            //   1123: getfield 61	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:this$0	Lkotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1;
            //   1126: getfield 199	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1:$transform$inlined	Lkotlin/jvm/functions/Function3;
            //   1129: astore 19
            //   1131: aload 8
            //   1133: getstatic 205	kotlinx/coroutines/flow/internal/NullSurrogateKt:NULL	Lkotlinx/coroutines/internal/Symbol;
            //   1136: if_acmpne +9 -> 1145
            //   1139: aconst_null
            //   1140: astore 13
            //   1142: goto +7 -> 1149
            //   1145: aload 8
            //   1147: astore 13
            //   1149: getstatic 205	kotlinx/coroutines/flow/internal/NullSurrogateKt:NULL	Lkotlinx/coroutines/internal/Symbol;
            //   1152: astore 21
            //   1154: aload 16
            //   1156: invokeinterface 195 1 0
            //   1161: astore 18
            //   1163: aload 18
            //   1165: astore 17
            //   1167: aload 18
            //   1169: aload 21
            //   1171: if_acmpne +6 -> 1177
            //   1174: aconst_null
            //   1175: astore 17
            //   1177: aload_1
            //   1178: aload 7
            //   1180: putfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   1183: aload_1
            //   1184: aload 12
            //   1186: putfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   1189: aload_1
            //   1190: aload 14
            //   1192: putfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   1195: aload_1
            //   1196: aload 16
            //   1198: putfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   1201: aload_1
            //   1202: aload 10
            //   1204: putfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   1207: aload_1
            //   1208: aload 6
            //   1210: putfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   1213: aload_1
            //   1214: aload_2
            //   1215: putfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   1218: aload_1
            //   1219: aload 4
            //   1221: putfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   1224: aload_1
            //   1225: aload 11
            //   1227: putfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   1230: aload_1
            //   1231: aload 15
            //   1233: putfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   1236: aload_1
            //   1237: aload 5
            //   1239: putfield 154	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$10	Ljava/lang/Object;
            //   1242: aload_1
            //   1243: aload 8
            //   1245: putfield 152	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$11	Ljava/lang/Object;
            //   1248: aload_1
            //   1249: aload 20
            //   1251: putfield 148	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$12	Ljava/lang/Object;
            //   1254: aload_1
            //   1255: iconst_3
            //   1256: putfield 107	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:label	I
            //   1259: aload 19
            //   1261: aload 13
            //   1263: aload 17
            //   1265: aload_1
            //   1266: invokeinterface 210 4 0
            //   1271: astore 18
            //   1273: aload 9
            //   1275: astore 13
            //   1277: aload 18
            //   1279: aload 13
            //   1281: if_acmpne +6 -> 1287
            //   1284: aload 13
            //   1286: areturn
            //   1287: aload 20
            //   1289: astore 17
            //   1291: aload 8
            //   1293: astore 20
            //   1295: aload 7
            //   1297: astore 8
            //   1299: aload 10
            //   1301: astore 9
            //   1303: aload 14
            //   1305: astore 10
            //   1307: aload_1
            //   1308: astore 14
            //   1310: aload 6
            //   1312: astore 7
            //   1314: aload 4
            //   1316: astore 6
            //   1318: aload 10
            //   1320: astore_1
            //   1321: aload 15
            //   1323: astore 10
            //   1325: aload 8
            //   1327: astore 15
            //   1329: aload 13
            //   1331: astore 4
            //   1333: aload 18
            //   1335: astore 13
            //   1337: aload 14
            //   1339: aload 15
            //   1341: putfield 133	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$0	Ljava/lang/Object;
            //   1344: aload 14
            //   1346: aload 12
            //   1348: putfield 131	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$1	Ljava/lang/Object;
            //   1351: aload 14
            //   1353: aload_1
            //   1354: putfield 129	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$2	Ljava/lang/Object;
            //   1357: aload 14
            //   1359: aload 16
            //   1361: putfield 127	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$3	Ljava/lang/Object;
            //   1364: aload 14
            //   1366: aload 9
            //   1368: putfield 125	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$4	Ljava/lang/Object;
            //   1371: aload 14
            //   1373: aload 7
            //   1375: putfield 123	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$5	Ljava/lang/Object;
            //   1378: aload 14
            //   1380: aload_2
            //   1381: putfield 121	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$6	Ljava/lang/Object;
            //   1384: aload 14
            //   1386: aload 6
            //   1388: putfield 117	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$7	Ljava/lang/Object;
            //   1391: aload 14
            //   1393: aload 11
            //   1395: putfield 113	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$8	Ljava/lang/Object;
            //   1398: aload 14
            //   1400: aload 10
            //   1402: putfield 109	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$9	Ljava/lang/Object;
            //   1405: aload 14
            //   1407: aload 5
            //   1409: putfield 154	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$10	Ljava/lang/Object;
            //   1412: aload 14
            //   1414: aload 20
            //   1416: putfield 152	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:L$11	Ljava/lang/Object;
            //   1419: aload 14
            //   1421: iconst_4
            //   1422: putfield 107	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:label	I
            //   1425: aload 17
            //   1427: aload 13
            //   1429: aload 14
            //   1431: invokeinterface 214 3 0
            //   1436: astore 8
            //   1438: aload 8
            //   1440: aload 4
            //   1442: if_acmpne +6 -> 1448
            //   1445: aload 4
            //   1447: areturn
            //   1448: aload 4
            //   1450: astore 8
            //   1452: aload 10
            //   1454: astore 4
            //   1456: aload 16
            //   1458: astore 10
            //   1460: aload 14
            //   1462: astore 16
            //   1464: aload_2
            //   1465: astore 14
            //   1467: aload 8
            //   1469: astore_2
            //   1470: aload 4
            //   1472: astore 5
            //   1474: aload 15
            //   1476: astore 13
            //   1478: aload 6
            //   1480: astore 4
            //   1482: aload 14
            //   1484: astore 6
            //   1486: aload_1
            //   1487: astore 14
            //   1489: aload 9
            //   1491: astore_1
            //   1492: aload 16
            //   1494: astore 15
            //   1496: aload 12
            //   1498: astore 16
            //   1500: aload 11
            //   1502: astore 9
            //   1504: aload 6
            //   1506: astore 12
            //   1508: aload_1
            //   1509: astore 6
            //   1511: aload 10
            //   1513: astore 8
            //   1515: aload 13
            //   1517: astore 10
            //   1519: aload 5
            //   1521: astore 11
            //   1523: aload 11
            //   1525: astore_1
            //   1526: aload_2
            //   1527: astore 11
            //   1529: aload 10
            //   1531: astore 5
            //   1533: aload_1
            //   1534: astore 10
            //   1536: aload 12
            //   1538: astore_1
            //   1539: goto -792 -> 747
            //   1542: astore 11
            //   1544: aload_1
            //   1545: astore 15
            //   1547: aload_2
            //   1548: astore 7
            //   1550: aload 14
            //   1552: astore_1
            //   1553: aload 15
            //   1555: astore_2
            //   1556: goto +105 -> 1661
            //   1559: astore 11
            //   1561: aload_2
            //   1562: astore 7
            //   1564: aload 14
            //   1566: astore_2
            //   1567: goto +94 -> 1661
            //   1570: getstatic 93	kotlin/Unit:INSTANCE	Lkotlin/Unit;
            //   1573: astore 14
            //   1575: aload_2
            //   1576: astore 9
            //   1578: aload_1
            //   1579: astore 16
            //   1581: aload_2
            //   1582: astore 14
            //   1584: aload_1
            //   1585: astore 6
            //   1587: aload 15
            //   1589: aload 4
            //   1591: invokestatic 220	kotlinx/coroutines/channels/ChannelsKt:cancelConsumed	(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
            //   1594: aload_1
            //   1595: invokeinterface 223 1 0
            //   1600: ifne +165 -> 1765
            //   1603: new 99	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1606: dup
            //   1607: aload_2
            //   1608: getfield 59	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:$this_unsafeFlow	Lkotlinx/coroutines/flow/FlowCollector;
            //   1611: invokespecial 226	kotlinx/coroutines/flow/internal/AbortFlowException:<init>	(Lkotlinx/coroutines/flow/FlowCollector;)V
            //   1614: astore_2
            //   1615: aload_1
            //   1616: aload_2
            //   1617: checkcast 228	java/util/concurrent/CancellationException
            //   1620: invokeinterface 232 2 0
            //   1625: goto +140 -> 1765
            //   1628: astore 11
            //   1630: aload_1
            //   1631: astore 14
            //   1633: goto -1008 -> 625
            //   1636: astore 11
            //   1638: aload_1
            //   1639: astore 7
            //   1641: aload 15
            //   1643: astore_1
            //   1644: aload 14
            //   1646: astore_2
            //   1647: goto +14 -> 1661
            //   1650: astore 11
            //   1652: aload_0
            //   1653: astore_2
            //   1654: aload 16
            //   1656: astore 15
            //   1658: goto -1033 -> 625
            //   1661: aload 11
            //   1663: athrow
            //   1664: astore 15
            //   1666: aload_1
            //   1667: astore 9
            //   1669: aload_2
            //   1670: astore 16
            //   1672: aload_1
            //   1673: astore 14
            //   1675: aload_2
            //   1676: astore 6
            //   1678: aload 7
            //   1680: aload 11
            //   1682: invokestatic 220	kotlinx/coroutines/channels/ChannelsKt:cancelConsumed	(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
            //   1685: aload_1
            //   1686: astore 9
            //   1688: aload_2
            //   1689: astore 16
            //   1691: aload_1
            //   1692: astore 14
            //   1694: aload_2
            //   1695: astore 6
            //   1697: aload 15
            //   1699: athrow
            //   1700: astore_2
            //   1701: aload 16
            //   1703: astore_1
            //   1704: goto +20 -> 1724
            //   1707: astore_1
            //   1708: aload_0
            //   1709: astore_2
            //   1710: aload 14
            //   1712: astore 6
            //   1714: goto +59 -> 1773
            //   1717: astore_2
            //   1718: aload_0
            //   1719: astore 9
            //   1721: aload 14
            //   1723: astore_1
            //   1724: aload 9
            //   1726: astore 14
            //   1728: aload_1
            //   1729: astore 6
            //   1731: aload_2
            //   1732: aload 9
            //   1734: getfield 59	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:$this_unsafeFlow	Lkotlinx/coroutines/flow/FlowCollector;
            //   1737: invokestatic 238	kotlinx/coroutines/flow/internal/FlowExceptions_commonKt:checkOwnership	(Lkotlinx/coroutines/flow/internal/AbortFlowException;Lkotlinx/coroutines/flow/FlowCollector;)V
            //   1740: aload_1
            //   1741: invokeinterface 223 1 0
            //   1746: ifne +19 -> 1765
            //   1749: new 99	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1752: dup
            //   1753: aload 9
            //   1755: getfield 59	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:$this_unsafeFlow	Lkotlinx/coroutines/flow/FlowCollector;
            //   1758: invokespecial 226	kotlinx/coroutines/flow/internal/AbortFlowException:<init>	(Lkotlinx/coroutines/flow/FlowCollector;)V
            //   1761: astore_2
            //   1762: goto -147 -> 1615
            //   1765: getstatic 93	kotlin/Unit:INSTANCE	Lkotlin/Unit;
            //   1768: areturn
            //   1769: astore_1
            //   1770: aload 14
            //   1772: astore_2
            //   1773: aload 6
            //   1775: invokeinterface 223 1 0
            //   1780: ifne +24 -> 1804
            //   1783: aload 6
            //   1785: new 99	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1788: dup
            //   1789: aload_2
            //   1790: getfield 59	kotlinx/coroutines/flow/internal/CombineKt$zipImpl$$inlined$unsafeFlow$1$lambda$1:$this_unsafeFlow	Lkotlinx/coroutines/flow/FlowCollector;
            //   1793: invokespecial 226	kotlinx/coroutines/flow/internal/AbortFlowException:<init>	(Lkotlinx/coroutines/flow/FlowCollector;)V
            //   1796: checkcast 228	java/util/concurrent/CancellationException
            //   1799: invokeinterface 232 2 0
            //   1804: aload_1
            //   1805: athrow
            //   1806: new 240	kotlin/TypeCastException
            //   1809: dup
            //   1810: ldc -14
            //   1812: invokespecial 243	kotlin/TypeCastException:<init>	(Ljava/lang/String;)V
            //   1815: athrow
            // Local variable table:
            //   start	length	slot	name	signature
            //   0	1816	0	this	1
            //   0	1816	1	paramAnonymous2Object	Object
            //   3	305	2	localObject1	Object
            //   323	138	2	localObject2	Object
            //   473	1222	2	localObject3	Object
            //   1700	1	2	localAbortFlowException1	AbortFlowException
            //   1709	1	2	local1	1
            //   1717	15	2	localAbortFlowException2	AbortFlowException
            //   1761	29	2	localObject4	Object
            //   8	23	3	i	int
            //   40	1550	4	localObject5	Object
            //   49	1483	5	localObject6	Object
            //   58	1726	6	localObject7	Object
            //   67	1612	7	localObject8	Object
            //   76	1438	8	localObject9	Object
            //   85	1669	9	localObject10	Object
            //   94	1441	10	localObject11	Object
            //   103	503	11	localObject12	Object
            //   611	1	11	localObject13	Object
            //   745	783	11	localObject14	Object
            //   1542	1	11	localObject15	Object
            //   1559	1	11	localObject16	Object
            //   1628	1	11	localObject17	Object
            //   1636	1	11	localObject18	Object
            //   1650	31	11	localThrowable	Throwable
            //   112	1425	12	localObject19	Object
            //   121	1395	13	localObject20	Object
            //   125	1646	14	localObject21	Object
            //   129	1528	15	localObject22	Object
            //   1664	34	15	localObject23	Object
            //   139	1563	16	localObject24	Object
            //   177	1249	17	localObject25	Object
            //   207	1127	18	localObject26	Object
            //   243	1017	19	localObject27	Object
            //   296	1119	20	localObject28	Object
            //   1152	18	21	localSymbol	Symbol
            // Exception table:
            //   from	to	target	type
            //   281	285	323	finally
            //   131	135	611	finally
            //   447	451	611	finally
            //   579	583	611	finally
            //   1337	1438	1542	finally
            //   1037	1048	1559	finally
            //   1116	1139	1559	finally
            //   1149	1163	1559	finally
            //   1177	1273	1559	finally
            //   893	999	1628	finally
            //   1570	1575	1628	finally
            //   747	832	1636	finally
            //   718	727	1650	finally
            //   1661	1664	1664	finally
            //   1587	1594	1700	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1678	1685	1700	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1697	1700	1700	kotlinx/coroutines/flow/internal/AbortFlowException
            //   712	718	1707	finally
            //   712	718	1717	kotlinx/coroutines/flow/internal/AbortFlowException
            //   1587	1594	1769	finally
            //   1678	1685	1769	finally
            //   1697	1700	1769	finally
            //   1731	1740	1769	finally
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
