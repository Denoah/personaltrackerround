package androidx.room;

import java.util.Set;
import java.util.concurrent.Callable;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\003\b\007\030\000 \0032\0020\001:\001\003B\007\b\002?\006\002\020\002?\002\004\n\002\b\031?\006\004"}, d2={"Landroidx/room/CoroutinesRoom;", "", "()V", "Companion", "room-ktx_release"}, k=1, mv={1, 1, 15})
public final class CoroutinesRoom
{
  public static final Companion Companion = new Companion(null);
  
  private CoroutinesRoom() {}
  
  @JvmStatic
  public static final <R> Flow<R> createFlow(RoomDatabase paramRoomDatabase, boolean paramBoolean, String[] paramArrayOfString, Callable<R> paramCallable)
  {
    return Companion.createFlow(paramRoomDatabase, paramBoolean, paramArrayOfString, paramCallable);
  }
  
  @JvmStatic
  public static final <R> Object execute(RoomDatabase paramRoomDatabase, boolean paramBoolean, Callable<R> paramCallable, Continuation<? super R> paramContinuation)
  {
    return Companion.execute(paramRoomDatabase, paramBoolean, paramCallable, paramContinuation);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\021\n\002\020\016\n\000\n\002\030\002\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002JJ\020\003\032\r\022\t\022\007H\005?\006\002\b\0060\004\"\004\b\000\020\0052\006\020\007\032\0020\b2\006\020\t\032\0020\n2\f\020\013\032\b\022\004\022\0020\r0\f2\f\020\016\032\b\022\004\022\002H\0050\017H\007?\006\002\020\020J5\020\021\032\002H\005\"\004\b\000\020\0052\006\020\007\032\0020\b2\006\020\t\032\0020\n2\f\020\016\032\b\022\004\022\002H\0050\017H?@?\001\000?\006\002\020\022?\002\004\n\002\b\031?\006\023"}, d2={"Landroidx/room/CoroutinesRoom$Companion;", "", "()V", "createFlow", "Lkotlinx/coroutines/flow/Flow;", "R", "Lkotlin/jvm/JvmSuppressWildcards;", "db", "Landroidx/room/RoomDatabase;", "inTransaction", "", "tableNames", "", "", "callable", "Ljava/util/concurrent/Callable;", "(Landroidx/room/RoomDatabase;Z[Ljava/lang/String;Ljava/util/concurrent/Callable;)Lkotlinx/coroutines/flow/Flow;", "execute", "(Landroidx/room/RoomDatabase;ZLjava/util/concurrent/Callable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "room-ktx_release"}, k=1, mv={1, 1, 15})
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final <R> Flow<R> createFlow(final RoomDatabase paramRoomDatabase, final boolean paramBoolean, String[] paramArrayOfString, final Callable<R> paramCallable)
    {
      Intrinsics.checkParameterIsNotNull(paramRoomDatabase, "db");
      Intrinsics.checkParameterIsNotNull(paramArrayOfString, "tableNames");
      Intrinsics.checkParameterIsNotNull(paramCallable, "callable");
      FlowKt.flow((Function2)new SuspendLambda(paramArrayOfString, paramBoolean)
      {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        private FlowCollector p$;
        
        public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
          paramAnonymousContinuation = new 1(this.$tableNames, paramBoolean, paramRoomDatabase, paramCallable, paramAnonymousContinuation);
          paramAnonymousContinuation.p$ = ((FlowCollector)paramAnonymousObject);
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
              localObject = (CoroutineDispatcher)this.L$4;
              localObject = (CoroutineContext)this.L$3;
              localObject = (observer.1)this.L$2;
              localObject = (Channel)this.L$1;
              localObject = (FlowCollector)this.L$0;
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
            final FlowCollector localFlowCollector = this.p$;
            final Channel localChannel = ChannelKt.Channel(-1);
            final InvalidationTracker.Observer local1 = new InvalidationTracker.Observer(localChannel)
            {
              public void onInvalidated(Set<String> paramAnonymous2Set)
              {
                Intrinsics.checkParameterIsNotNull(paramAnonymous2Set, "tables");
                localChannel.offer(Unit.INSTANCE);
              }
            };
            localChannel.offer(Unit.INSTANCE);
            final CoroutineContext localCoroutineContext1 = getContext();
            if (paramBoolean) {
              paramAnonymousObject = CoroutinesRoomKt.getTransactionDispatcher(paramRoomDatabase);
            } else {
              paramAnonymousObject = CoroutinesRoomKt.getQueryDispatcher(paramRoomDatabase);
            }
            CoroutineContext localCoroutineContext2 = (CoroutineContext)paramAnonymousObject;
            Function2 localFunction2 = (Function2)new SuspendLambda(localFlowCollector, local1)
            {
              Object L$0;
              Object L$1;
              Object L$2;
              Object L$3;
              int label;
              private CoroutineScope p$;
              
              public final Continuation<Unit> create(Object paramAnonymous2Object, Continuation<?> paramAnonymous2Continuation)
              {
                Intrinsics.checkParameterIsNotNull(paramAnonymous2Continuation, "completion");
                paramAnonymous2Continuation = new 1(this.this$0, localFlowCollector, local1, localChannel, localCoroutineContext1, paramAnonymous2Continuation);
                paramAnonymous2Continuation.p$ = ((CoroutineScope)paramAnonymous2Object);
                return paramAnonymous2Continuation;
              }
              
              public final Object invoke(Object paramAnonymous2Object1, Object paramAnonymous2Object2)
              {
                return ((1)create(paramAnonymous2Object1, (Continuation)paramAnonymous2Object2)).invokeSuspend(Unit.INSTANCE);
              }
              
              public final Object invokeSuspend(Object paramAnonymous2Object)
              {
                Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                Object localObject2;
                Object localObject5;
                if (i != 0) {
                  if (i != 1) {
                    if (i == 2)
                    {
                      localObject2 = (ChannelIterator)this.L$2;
                      localObject5 = (Unit)this.L$1;
                      localObject5 = (CoroutineScope)this.L$0;
                    }
                  }
                }
                try
                {
                  ResultKt.throwOnFailure(paramAnonymous2Object);
                  paramAnonymous2Object = localObject2;
                  break label141;
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  Object localObject6 = (ChannelIterator)this.L$1;
                  localObject5 = (CoroutineScope)this.L$0;
                  ResultKt.throwOnFailure(paramAnonymous2Object);
                  localObject2 = this;
                  Object localObject7 = paramAnonymous2Object;
                  break label198;
                  ResultKt.throwOnFailure(paramAnonymous2Object);
                  localObject5 = this.p$;
                  this.this$0.$db.getInvalidationTracker().addObserver((InvalidationTracker.Observer)local1);
                  paramAnonymous2Object = localChannel.iterator();
                  label141:
                  localObject2 = this;
                  localObject6 = paramAnonymous2Object;
                  for (;;)
                  {
                    paramAnonymous2Object = localObject2;
                    try
                    {
                      ((1)localObject2).L$0 = localObject5;
                      paramAnonymous2Object = localObject2;
                      ((1)localObject2).L$1 = localObject6;
                      paramAnonymous2Object = localObject2;
                      ((1)localObject2).label = 1;
                      paramAnonymous2Object = localObject2;
                      localObject7 = ((ChannelIterator)localObject6).hasNext((Continuation)localObject2);
                      if (localObject7 == localObject1) {
                        return localObject1;
                      }
                      label198:
                      paramAnonymous2Object = localObject2;
                      if (((Boolean)localObject7).booleanValue())
                      {
                        paramAnonymous2Object = localObject2;
                        Unit localUnit = (Unit)((ChannelIterator)localObject6).next();
                        paramAnonymous2Object = localObject2;
                        localObject7 = ((1)localObject2).this$0.$callable.call();
                        paramAnonymous2Object = localObject2;
                        CoroutineContext localCoroutineContext = localCoroutineContext1;
                        paramAnonymous2Object = localObject2;
                        Object localObject8 = new androidx/room/CoroutinesRoom$Companion$createFlow$1$1$1;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject8).<init>((1)localObject2, localObject7, null);
                        paramAnonymous2Object = localObject2;
                        localObject8 = (Function2)localObject8;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject2).L$0 = localObject5;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject2).L$1 = localUnit;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject2).L$2 = localObject6;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject2).L$3 = localObject7;
                        paramAnonymous2Object = localObject2;
                        ((1)localObject2).label = 2;
                        paramAnonymous2Object = localObject2;
                        localObject7 = BuildersKt.withContext(localCoroutineContext, (Function2)localObject8, (Continuation)localObject2);
                        if (localObject7 == localObject1) {
                          return localObject1;
                        }
                      }
                      else
                      {
                        ((1)localObject2).this$0.$db.getInvalidationTracker().removeObserver((InvalidationTracker.Observer)local1);
                        return Unit.INSTANCE;
                      }
                    }
                    finally {}
                  }
                  paramAnonymous2Object.this$0.$db.getInvalidationTracker().removeObserver((InvalidationTracker.Observer)local1);
                }
                finally
                {
                  paramAnonymous2Object = this;
                }
                throw localObject4;
              }
            };
            this.L$0 = localFlowCollector;
            this.L$1 = localChannel;
            this.L$2 = local1;
            this.L$3 = localCoroutineContext1;
            this.L$4 = paramAnonymousObject;
            this.label = 1;
            if (BuildersKt.withContext(localCoroutineContext2, localFunction2, this) == localObject) {
              return localObject;
            }
          }
          return Unit.INSTANCE;
        }
      });
    }
    
    @JvmStatic
    public final <R> Object execute(RoomDatabase paramRoomDatabase, boolean paramBoolean, Callable<R> paramCallable, Continuation<? super R> paramContinuation)
    {
      if ((paramRoomDatabase.isOpen()) && (paramRoomDatabase.inTransaction())) {
        return paramCallable.call();
      }
      Object localObject = (TransactionElement)paramContinuation.getContext().get((CoroutineContext.Key)TransactionElement.Key);
      if (localObject != null)
      {
        localObject = ((TransactionElement)localObject).getTransactionDispatcher$room_ktx_release();
        if (localObject != null)
        {
          paramRoomDatabase = (RoomDatabase)localObject;
          break label89;
        }
      }
      if (paramBoolean) {
        paramRoomDatabase = CoroutinesRoomKt.getTransactionDispatcher(paramRoomDatabase);
      } else {
        paramRoomDatabase = CoroutinesRoomKt.getQueryDispatcher(paramRoomDatabase);
      }
      paramRoomDatabase = (ContinuationInterceptor)paramRoomDatabase;
      label89:
      BuildersKt.withContext((CoroutineContext)paramRoomDatabase, (Function2)new SuspendLambda(paramCallable, null)
      {
        int label;
        private CoroutineScope p$;
        
        public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
          paramAnonymousContinuation = new 2(this.$callable, paramAnonymousContinuation);
          paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
          return paramAnonymousContinuation;
        }
        
        public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          return ((2)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
        }
        
        public final Object invokeSuspend(Object paramAnonymousObject)
        {
          IntrinsicsKt.getCOROUTINE_SUSPENDED();
          if (this.label == 0)
          {
            ResultKt.throwOnFailure(paramAnonymousObject);
            return this.$callable.call();
          }
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
      }, paramContinuation);
    }
  }
}
