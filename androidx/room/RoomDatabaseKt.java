package androidx.room;

import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.Job.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\002\032\035\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\004H?@?\001\000?\006\002\020\005\032\025\020\006\032\0020\007*\0020\bH?@?\001\000?\006\002\020\t\0329\020\n\032\002H\013\"\004\b\000\020\013*\0020\b2\034\020\f\032\030\b\001\022\n\022\b\022\004\022\002H\0130\016\022\006\022\004\030\0010\0170\rH?@?\001\000?\006\002\020\020?\002\004\n\002\b\031?\006\021"}, d2={"acquireTransactionThread", "Lkotlin/coroutines/ContinuationInterceptor;", "Ljava/util/concurrent/Executor;", "controlJob", "Lkotlinx/coroutines/Job;", "(Ljava/util/concurrent/Executor;Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createTransactionContext", "Lkotlin/coroutines/CoroutineContext;", "Landroidx/room/RoomDatabase;", "(Landroidx/room/RoomDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withTransaction", "R", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Landroidx/room/RoomDatabase;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "room-ktx_release"}, k=2, mv={1, 1, 15})
public final class RoomDatabaseKt
{
  public static final <R> Object withTransaction(RoomDatabase paramRoomDatabase, final Function1<? super Continuation<? super R>, ? extends Object> paramFunction1, Continuation<? super R> paramContinuation)
  {
    if ((paramContinuation instanceof withTransaction.1))
    {
      localObject1 = (withTransaction.1)paramContinuation;
      if ((((withTransaction.1)localObject1).label & 0x80000000) != 0)
      {
        ((withTransaction.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super R>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return RoomDatabaseKt.withTransaction(null, null, this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i != 1)
      {
        if (i == 2)
        {
          paramRoomDatabase = (CoroutineContext)paramContinuation.L$2;
          paramRoomDatabase = (Function1)paramContinuation.L$1;
          paramRoomDatabase = (RoomDatabase)paramContinuation.L$0;
          ResultKt.throwOnFailure(localObject1);
          return localObject1;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
      paramFunction1 = (Function1)paramContinuation.L$1;
      paramRoomDatabase = (RoomDatabase)paramContinuation.L$0;
      ResultKt.throwOnFailure(localObject1);
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      localObject1 = (TransactionElement)paramContinuation.getContext().get((CoroutineContext.Key)TransactionElement.Key);
      if (localObject1 != null)
      {
        localObject1 = ((TransactionElement)localObject1).getTransactionDispatcher$room_ktx_release();
        if (localObject1 != null)
        {
          localObject1 = (CoroutineContext)localObject1;
          break label230;
        }
      }
      paramContinuation.L$0 = paramRoomDatabase;
      paramContinuation.L$1 = paramFunction1;
      paramContinuation.label = 1;
      localObject3 = createTransactionContext(paramRoomDatabase, paramContinuation);
      localObject1 = localObject3;
      if (localObject3 == localObject2) {
        return localObject2;
      }
    }
    localObject1 = (CoroutineContext)localObject1;
    label230:
    Object localObject3 = (Function2)new SuspendLambda(paramRoomDatabase, paramFunction1)
    {
      Object L$0;
      Object L$1;
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 2(this.$this_withTransaction, paramFunction1, paramAnonymousContinuation);
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
        //   0: invokestatic 99	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
        //   3: astore_2
        //   4: aload_0
        //   5: getfield 101	androidx/room/RoomDatabaseKt$withTransaction$2:label	I
        //   8: istore_3
        //   9: iload_3
        //   10: ifeq +52 -> 62
        //   13: iload_3
        //   14: iconst_1
        //   15: if_icmpne +37 -> 52
        //   18: aload_0
        //   19: getfield 103	androidx/room/RoomDatabaseKt$withTransaction$2:L$1	Ljava/lang/Object;
        //   22: checkcast 105	androidx/room/TransactionElement
        //   25: astore_2
        //   26: aload_0
        //   27: getfield 107	androidx/room/RoomDatabaseKt$withTransaction$2:L$0	Ljava/lang/Object;
        //   30: checkcast 76	kotlinx/coroutines/CoroutineScope
        //   33: astore 4
        //   35: aload_2
        //   36: astore 4
        //   38: aload_1
        //   39: invokestatic 113	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   42: aload_1
        //   43: astore 5
        //   45: goto +111 -> 156
        //   48: astore_2
        //   49: goto +137 -> 186
        //   52: new 115	java/lang/IllegalStateException
        //   55: dup
        //   56: ldc 117
        //   58: invokespecial 120	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
        //   61: athrow
        //   62: aload_1
        //   63: invokestatic 113	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
        //   66: aload_0
        //   67: getfield 78	androidx/room/RoomDatabaseKt$withTransaction$2:p$	Lkotlinx/coroutines/CoroutineScope;
        //   70: astore 4
        //   72: aload 4
        //   74: invokeinterface 124 1 0
        //   79: getstatic 128	androidx/room/TransactionElement:Key	Landroidx/room/TransactionElement$Key;
        //   82: checkcast 130	kotlin/coroutines/CoroutineContext$Key
        //   85: invokeinterface 136 2 0
        //   90: astore_1
        //   91: aload_1
        //   92: ifnonnull +6 -> 98
        //   95: invokestatic 140	kotlin/jvm/internal/Intrinsics:throwNpe	()V
        //   98: aload_1
        //   99: checkcast 105	androidx/room/TransactionElement
        //   102: astore_1
        //   103: aload_1
        //   104: invokevirtual 143	androidx/room/TransactionElement:acquire	()V
        //   107: aload_0
        //   108: getfield 56	androidx/room/RoomDatabaseKt$withTransaction$2:$this_withTransaction	Landroidx/room/RoomDatabase;
        //   111: invokevirtual 148	androidx/room/RoomDatabase:beginTransaction	()V
        //   114: aload_0
        //   115: getfield 58	androidx/room/RoomDatabaseKt$withTransaction$2:$block	Lkotlin/jvm/functions/Function1;
        //   118: astore 5
        //   120: aload_0
        //   121: aload 4
        //   123: putfield 107	androidx/room/RoomDatabaseKt$withTransaction$2:L$0	Ljava/lang/Object;
        //   126: aload_0
        //   127: aload_1
        //   128: putfield 103	androidx/room/RoomDatabaseKt$withTransaction$2:L$1	Ljava/lang/Object;
        //   131: aload_0
        //   132: iconst_1
        //   133: putfield 101	androidx/room/RoomDatabaseKt$withTransaction$2:label	I
        //   136: aload 5
        //   138: aload_0
        //   139: invokeinterface 152 2 0
        //   144: astore 5
        //   146: aload 5
        //   148: aload_2
        //   149: if_acmpne +5 -> 154
        //   152: aload_2
        //   153: areturn
        //   154: aload_1
        //   155: astore_2
        //   156: aload_2
        //   157: astore 4
        //   159: aload_0
        //   160: getfield 56	androidx/room/RoomDatabaseKt$withTransaction$2:$this_withTransaction	Landroidx/room/RoomDatabase;
        //   163: invokevirtual 155	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   166: aload_2
        //   167: astore_1
        //   168: aload_0
        //   169: getfield 56	androidx/room/RoomDatabaseKt$withTransaction$2:$this_withTransaction	Landroidx/room/RoomDatabase;
        //   172: invokevirtual 158	androidx/room/RoomDatabase:endTransaction	()V
        //   175: aload_2
        //   176: invokevirtual 161	androidx/room/TransactionElement:release	()V
        //   179: aload 5
        //   181: areturn
        //   182: astore_2
        //   183: aload_1
        //   184: astore 4
        //   186: aload 4
        //   188: astore_1
        //   189: aload_0
        //   190: getfield 56	androidx/room/RoomDatabaseKt$withTransaction$2:$this_withTransaction	Landroidx/room/RoomDatabase;
        //   193: invokevirtual 158	androidx/room/RoomDatabase:endTransaction	()V
        //   196: aload 4
        //   198: astore_1
        //   199: aload_2
        //   200: athrow
        //   201: astore_2
        //   202: goto +4 -> 206
        //   205: astore_2
        //   206: aload_1
        //   207: invokevirtual 161	androidx/room/TransactionElement:release	()V
        //   210: aload_2
        //   211: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	212	0	this	2
        //   0	212	1	paramAnonymousObject	Object
        //   3	33	2	localObject1	Object
        //   48	105	2	localObject2	Object
        //   155	21	2	localObject3	Object
        //   182	18	2	localObject4	Object
        //   201	1	2	localObject5	Object
        //   205	6	2	localObject6	Object
        //   8	8	3	i	int
        //   33	164	4	localObject7	Object
        //   43	137	5	localObject8	Object
        // Exception table:
        //   from	to	target	type
        //   38	42	48	finally
        //   159	166	48	finally
        //   114	146	182	finally
        //   168	175	201	finally
        //   189	196	201	finally
        //   199	201	201	finally
        //   107	114	205	finally
      }
    };
    paramContinuation.L$0 = paramRoomDatabase;
    paramContinuation.L$1 = paramFunction1;
    paramContinuation.L$2 = localObject1;
    paramContinuation.label = 2;
    paramRoomDatabase = BuildersKt.withContext((CoroutineContext)localObject1, (Function2)localObject3, paramContinuation);
    localObject1 = paramRoomDatabase;
    if (paramRoomDatabase == localObject2) {
      return localObject2;
    }
    return localObject1;
  }
}
